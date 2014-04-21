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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import co.tashawych.db.CollectionDB;
import co.tashawych.db.DatabaseHelper;
import co.tashawych.http.HttpRequest;
import co.tashawych.misc.Utility;

public class MyCollectionsAdapter extends SimpleCursorAdapter {
	Context context;
	int layout;
	Cursor cursor;

	public MyCollectionsAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		this.context = context;
		this.layout = layout;
		this.cursor = c;
	}
	
	public void updateCursor(Cursor c) {
		this.cursor = c;
		notifyDataSetChanged();
	}
	
	static class ViewHolder {
		protected ImageView picture;
		protected TextView title;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, layout, null);
            
            holder = new ViewHolder();
            holder.picture = (ImageView) convertView.findViewById(R.id.lvw_my_collections_picture);
            holder.title = (TextView) convertView.findViewById(R.id.lvw_my_collections_text);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        cursor.moveToPosition(position);
        holder.title.setText(cursor.getString(cursor.getColumnIndex(CollectionDB.COL_TITLE)));
        
        String imgString = cursor.getString(cursor.getColumnIndex(CollectionDB.COL_PICTURE));
        if (!imgString.equals("")) {
        	holder.picture.setImageBitmap(Utility.getBitmapFromString(imgString));
        }
        else {
        	String cat = cursor.getString(cursor.getColumnIndex(CollectionDB.COL_CATEGORY));
        	holder.picture.setImageResource(Utility.getPictureForCategory(cat));
        }

		OnClickListener listener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent view_collection = new Intent(context, ViewCollection.class);
				cursor.moveToPosition(position);
				view_collection.putExtra("id", cursor.getString(cursor.getColumnIndex(CollectionDB.COL_ID)));
				context.startActivity(view_collection);
			}
		};

		convertView.setOnClickListener(listener);

		OnLongClickListener longListener = new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage("Do you want to delete this collection?");
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (!Utility.hasInternetAccess(context)) {
							Toast.makeText(context,"You need to have internet access!", Toast.LENGTH_SHORT).show();
							return;
						}
						cursor.moveToPosition(position);
						String col_id = cursor.getString(cursor.getColumnIndex(CollectionDB.COL_ID));
						// Delete collection server-side
						new remove_collection(col_id).execute();
						// Delete collection client-side
						DatabaseHelper.getHelper(context).removeCollection(col_id);
						swapCursor(DatabaseHelper.getHelper(context).getCollectionsByUserId(Utility.getUsername(context)));
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
		};

		convertView.setOnLongClickListener(longListener);

		return convertView;
	}

	private class remove_collection extends AsyncTask<Void, Void, String> {
		final String col_id;

		private remove_collection(String col_id) {
			this.col_id = col_id;
		}

		@Override
		protected String doInBackground(Void... params) {
			try {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
				nameValuePairs.add(new BasicNameValuePair("col_id", col_id));

	            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs);

				return HttpRequest.POST(Utility.URL + "/removeCollection", entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

}
