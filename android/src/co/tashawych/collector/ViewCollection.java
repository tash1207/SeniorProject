package co.tashawych.collector;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
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

		updateGridView();
	}

	public void updateGridView() {
		ArrayList<Integer> ints = new ArrayList<Integer>();
		ints.add(R.drawable.ic_stamp);
		ints.add(R.drawable.ic_card);
		ints.add(R.drawable.ic_figurine);
		ints.add(R.drawable.ic_book);
		ints.add(R.drawable.ic_media);
		ints.add(R.drawable.ic_electronic);
		ints.add(R.drawable.ic_coin);

		GridView gridView = (GridView) findViewById(R.id.gridview);
		gridView.setAdapter(new ViewCollectionAdapter(this, R.layout.gridview_layout, R.id.gridview_item, ints));
	}

	public void backClicked(View v) {
		super.onBackPressed();
	}

}
