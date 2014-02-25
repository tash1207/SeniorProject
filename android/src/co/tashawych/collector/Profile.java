package co.tashawych.collector;

import co.tashawych.datatypes.Collection;
import co.tashawych.db.DatabaseHelper;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class Profile extends Activity {
	
	ListView lvw_my_collections;
	MyCollectionsAdapter adapter;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		TextView name = (TextView) findViewById(R.id.profile_name);
		TextView num = (TextView) findViewById(R.id.profile_num_collections);
		
		name.setText("Natalya Dominika Hankewych");
		num.setText("1");
		
		lvw_my_collections = (ListView) findViewById(R.id.lvw_my_collections);
		adapter = new MyCollectionsAdapter(this, R.layout.lvw_my_collections, 
				DatabaseHelper.getHelper(this).getCollectionsByUserId(1), new String[]{}, new int[]{}, 0);
		lvw_my_collections.setAdapter(adapter);
	}

	public void btn_create_collection_clicked(View v) {
		Collection collection = new Collection(1, 1, "Stamps", "A collection of stamps from all countries!", 
				"stamp", false, "");
		DatabaseHelper.getHelper(this).insertCollection(collection);
		
	}
}
