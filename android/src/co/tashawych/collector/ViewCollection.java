package co.tashawych.collector;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import co.tashawych.datatypes.Collection;
import co.tashawych.db.DatabaseHelper;
import co.tashawych.misc.Utility;

public class ViewCollection extends BaseActivity {
	String id;
	Collection col;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_collection);
		
		id = getIntent().getStringExtra("id");
		col = DatabaseHelper.getHelper(this).getCollection(id);
		
		ImageView picture = (ImageView) findViewById(R.id.collection_picture);
		TextView title = (TextView) findViewById(R.id.collection_title);
		TextView username = (TextView) findViewById(R.id.collection_username);
		
		if (!col.getPicture().equals("")) {
			picture.setImageBitmap(Utility.getBitmapFromString(col.getPicture()));
		}
		else {
			picture.setImageResource(Utility.getPictureForCategory(col.getCategory()));
		}
		
		title.setText(col.getTitle());
		username.setText("by " + col.getUsername());
	}

}
