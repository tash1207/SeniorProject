package co.tashawych.collector;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import co.tashawych.db.DatabaseHelper;
import co.tashawych.db.ItemDB;
import co.tashawych.http.HttpRequest;
import co.tashawych.misc.Utility;

public class ViewCollectionAdapter extends SimpleCursorAdapter {
	Context context;
	int layout;
	String col_category;
	Cursor cursor;
	boolean my_collection;

	public ViewCollectionAdapter(Context context, int layout, String col_category, Cursor c, 
			String[] from, int[] to, int flags, boolean my_collection) {
		super(context, layout, c, from, to, flags);
		this.context = context;
		this.layout = layout;
		this.col_category = col_category;
		this.cursor = c;
		this.my_collection = my_collection;
	}

	public void updateCursor(Cursor c) {
		this.cursor = c;
		notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(layout, parent, false);

		if (cursor.moveToPosition(position)) {
		ImageView image = (ImageView) convertView.findViewById(R.id.gridview_item);
		if (cursor.getString(cursor.getColumnIndex(ItemDB.ITEM_PICTURE)).equals("")) {
			image.setImageResource(Utility.getPictureForCategory(col_category));
		}
		else {
			image.setImageBitmap(Utility.getBitmapFromString(cursor.getString(cursor.getColumnIndex(ItemDB.ITEM_PICTURE))));
		}

		convertView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				cursor.moveToPosition(position);
				Intent view_item = new Intent(context, ViewItem.class);
				view_item.putExtra("item_id", cursor.getString(cursor.getColumnIndex(ItemDB.ITEM_ID)));
				context.startActivity(view_item);
			}
		});
		if (my_collection) {
			convertView.setOnLongClickListener(new View.OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					cursor.moveToPosition(position);
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setMessage("Do you want to delete this collection?");
					builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (!Utility.hasInternetAccess(context)) {
								Toast.makeText(context,"You need to have internet access!", Toast.LENGTH_SHORT).show();
								return;
							}
							String id = cursor.getString(cursor.getColumnIndex(ItemDB.ITEM_ID));
							String col_id = cursor.getString(cursor.getColumnIndex(ItemDB.ITEM_COL_ID));
							// Delete item server-side
							new remove_item(id).execute();
							// Delete item client-side
							DatabaseHelper.getHelper(context).removeItem(id);
							cursor = DatabaseHelper.getHelper(context).getItemsByCollectionId(col_id);
							swapCursor(cursor);
							dialog.dismiss();
						}
					});

					builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});

					AlertDialog dialog = builder.create();
					dialog.show();
					return true;
				}
			});
		}
		}
		return convertView;
	}

	private class remove_item extends AsyncTask<Void, Void, String> {
		final String id;

		private remove_item(String id) {
			this.id = id;
		}

		@Override
		protected String doInBackground(Void... params) {
			try {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
				nameValuePairs.add(new BasicNameValuePair("id", id));

	            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs);

				return HttpRequest.POST(Utility.URL + "/removeItem", entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

}
