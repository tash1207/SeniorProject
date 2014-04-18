package co.tashawych.collector;

import android.content.Intent;
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
	ViewCollectionAdapter adapter;
	boolean my_collection;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_collection);

		id = getIntent().getStringExtra("id");
		col = DatabaseHelper.getHelper(this).getCollection(id);
		my_collection = col.getUsername().equals(Utility.getUsername(this));

		// If this is the user's collection, allow editing
		if (my_collection) {
			findViewById(R.id.btn_add_item).setVisibility(View.VISIBLE);
			findViewById(R.id.btn_edit_collection).setVisibility(View.VISIBLE);
		}

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

	public void onStart() {
		super.onStart();
		updateGridView();
	}

	public void updateGridView() {
		GridView gridView = (GridView) findViewById(R.id.gridview);
		adapter = new ViewCollectionAdapter(this, R.layout.gridview_layout, col.getCategory(),
				DatabaseHelper.getHelper(this).getItemsByCollectionId(id), new String[] {}, new int[] {}, 0, my_collection);
		gridView.setAdapter(adapter);
	}

	public void editCollection(View v) {
		Intent edit = new Intent(this, AddCollection.class);
		edit.putExtra("col_id", col.getId());
		startActivity(edit);
	}

	public void addItem(View v) {
		Intent add_item = new Intent(this, AddItem.class);
		add_item.putExtra("col_id", col.getId());
		startActivity(add_item);
	}

	public void backClicked(View v) {
		super.onBackPressed();
	}

}
