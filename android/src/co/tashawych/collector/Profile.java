package co.tashawych.collector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import co.tashawych.datatypes.User;
import co.tashawych.db.DatabaseHelper;
import co.tashawych.misc.Utility;

public class Profile extends BaseActivity {
	
	ListView lvw_my_collections;
	MyCollectionsAdapter adapter;
	User user;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		setupSideMenu();
	}
	
	public void onStart() {
		super.onStart();
		user = DatabaseHelper.getHelper(this).getUser(Utility.getUsername(this));

		lvw_my_collections = (ListView) findViewById(R.id.lvw_my_collections);
		adapter = new MyCollectionsAdapter(this, R.layout.lvw_my_collections,
				DatabaseHelper.getHelper(this).getCollectionsByUserId(user.getUsername()), 
				new String[] {}, new int[] {}, 0);
		lvw_my_collections.setAdapter(adapter);
		lvw_my_collections.getLayoutParams().height = Utility.getListViewHeight(lvw_my_collections);

		ImageView picture = (ImageView) findViewById(R.id.profile_picture);
		if (user.getPicture() != null && !user.getPicture().equals("")) {
			picture.setImageBitmap(Utility.getBitmapFromString(user.getPicture()));
		}

		TextView name = (TextView) findViewById(R.id.profile_name);
		TextView num = (TextView) findViewById(R.id.profile_num_collections);

		String display_name = user.getDisplayName().equals("") ? user.getUsername() : user.getDisplayName();
		name.setText(display_name);
		num.setText(String.valueOf(adapter.getCount()));
	}

	public void menuClicked(View v) {
		menu.showMenu();
	}

	public void createCollection(View v) {
		Intent add_collection = new Intent(this, AddCollection.class);
		startActivity(add_collection);
	}
}
