package co.tashawych.collector;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import co.tashawych.db.CollectionDB;
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
		
		return convertView;
	}

}
