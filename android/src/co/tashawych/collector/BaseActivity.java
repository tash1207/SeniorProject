package co.tashawych.collector;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import co.tashawych.misc.Utility;

public class BaseActivity extends Activity {
	protected static final int UPLOAD_PIC = 1;
	protected static final int AFTER_CROP = 2;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.logout) {
			Utility.prefs(this).edit().putString("username", "").commit();
			startActivity(new Intent(this, Login.class));
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
