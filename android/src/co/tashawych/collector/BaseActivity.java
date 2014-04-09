package co.tashawych.collector;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import co.tashawych.misc.Utility;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class BaseActivity extends Activity {
	SlidingMenu menu;
	
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
	
	public void setupSideMenu() {
		// Side Menu Setup
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setBehindScrollScale(0);
		menu.setBehindWidth(Utility.getScreenWidth(this) - Utility.getPixels(this, 57));
		menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		menu.setMenu(R.layout.menu_layout);
		// menu.setShadowDrawable(R.drawable.shadow_left);
		// menu.setShadowWidth(Utility.getPixels(this, 10));
		
		TextView username = (TextView) findViewById(R.id.menu_username);
		if (Utility.getUsername(this).equals("")) {
			username.setText("Login");
			username.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent login = new Intent(BaseActivity.this, Login.class);
					login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(login);
				}
			});
		}
		else {
			username.setText("Logged in as " + Utility.getUsername(this));
		}
	}
	
	public void menuProfile(View v) {
		menu.showContent();
		Intent profile = new Intent(this, Profile.class);
		profile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(profile);
	}
	
	public void menuBrowse(View v) {
		menu.showContent();
		Intent browse = new Intent(this, BrowseCollections.class);
		browse.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(browse);
	}

}
