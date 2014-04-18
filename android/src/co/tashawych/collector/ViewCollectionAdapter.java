package co.tashawych.collector;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import co.tashawych.db.ItemDB;
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

		cursor.moveToPosition(position);
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
					Toast.makeText(context, "Deleting items: coming soon", Toast.LENGTH_SHORT).show();
					return true;
				}
			});
		}
		return convertView;
	}

}
