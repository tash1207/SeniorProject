package co.tashawych.collector;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import co.tashawych.db.DatabaseHelper;

public class BrowseCollections extends BaseActivity {
	
	ListView lvw_browse_collections;
	MyCollectionsAdapter adapter;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_collections);
		setupSideMenu();
		
		lvw_browse_collections = (ListView) findViewById(R.id.lvw_browse_collections);
		adapter = new MyCollectionsAdapter(this, R.layout.lvw_my_collections, 
				DatabaseHelper.getHelper(this).getCollections(), new String[]{}, new int[]{}, 0);
		lvw_browse_collections.setAdapter(adapter);
	}
	
	public void menuClicked(View v) {
		menu.showMenu();
	}
	
	public void searchClicked(View v) {
		Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show();
	}

}
