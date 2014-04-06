package co.tashawych.collector;

import co.tashawych.db.CollectionDB;
import co.tashawych.misc.Utility;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
	        if (cat.equals(Utility.cat_book)) {
	        	holder.picture.setImageResource(R.drawable.ic_book);
	        }
	        else if (cat.equals(Utility.cat_card)) {
	        	holder.picture.setImageResource(R.drawable.ic_card);
	        }
	        else if (cat.equals(Utility.cat_coin)) {
	        	holder.picture.setImageResource(R.drawable.ic_coin);
	        }
	        else if (cat.equals(Utility.cat_electronic)) {
	        	holder.picture.setImageResource(R.drawable.ic_electronic);
	        }
	        else if (cat.equals(Utility.cat_figurine)) {
	        	holder.picture.setImageResource(R.drawable.ic_figurine);
	        }
	        else if (cat.equals(Utility.cat_media)) {
	        	holder.picture.setImageResource(R.drawable.ic_media);
	        }
	        else if (cat.equals(Utility.cat_stamp)) {
	        	holder.picture.setImageResource(R.drawable.ic_stamp);
	        }
	        else {
	        	holder.picture.setImageResource(R.drawable.logo_no_bg);
	        }
        }
		
		return convertView;
	}

}
