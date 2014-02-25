package co.tashawych.collector;

import co.tashawych.db.CollectionDB;
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
        
        if (cursor.getString(cursor.getColumnIndex(CollectionDB.COL_CATEGORY)).equals("coin")) {
        	holder.picture.setImageResource(R.drawable.ic_coin);
        }
        else if (cursor.getString(cursor.getColumnIndex(CollectionDB.COL_CATEGORY)).equals("stamp")) {
        	holder.picture.setImageResource(R.drawable.ic_stamp);
        }
		
		return convertView;
	}

}
