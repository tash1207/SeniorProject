package co.tashawych.collector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import co.tashawych.db.DatabaseHelper;
import co.tashawych.misc.Utility;

public class Profile extends Activity {
	
	ListView lvw_my_collections;
	MyCollectionsAdapter adapter;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
	}
	
	public void onStart() {
		super.onStart();
		lvw_my_collections = (ListView) findViewById(R.id.lvw_my_collections);
		adapter = new MyCollectionsAdapter(this, R.layout.lvw_my_collections, 
				DatabaseHelper.getHelper(this).getCollectionsByUserId(Utility.getUsername(this)), 
				new String[]{}, new int[]{}, 0);
		lvw_my_collections.setAdapter(adapter);
		lvw_my_collections.getLayoutParams().height = Utility.getListViewHeight(lvw_my_collections);
		
		TextView name = (TextView) findViewById(R.id.profile_name);
		TextView num = (TextView) findViewById(R.id.profile_num_collections);
		
		name.setText("Natalya Dominika Hankewych");
		num.setText(String.valueOf(adapter.getCount()));
	}

	public void btn_create_collection_clicked(View v) {
		Intent add_collection = new Intent(this, AddCollection.class);
		startActivity(add_collection);
	}
}
