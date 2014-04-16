package co.tashawych.collector;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import co.tashawych.db.ItemDB;
import co.tashawych.misc.Utility;

public class ViewCollectionAdapter extends SimpleCursorAdapter {
	Context context;
	int layout;
	Cursor cursor;
	boolean my_collection;

	public ViewCollectionAdapter(Context context, int layout, Cursor c, 
			String[] from, int[] to, int flags, boolean my_collection) {
		super(context, layout, c, from, to, flags);
		this.context = context;
		this.layout = layout;
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
		image.setImageBitmap(Utility.getBitmapFromString(cursor.getString(cursor.getColumnIndex(ItemDB.ITEM_PICTURE))));

		return convertView;
	}

}
