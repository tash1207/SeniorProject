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
			username.setBackgroundResource(R.drawable.selector_blue);
			username.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent login = new Intent(BaseActivity.this, Login.class);
					login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(login);
				}
			});
			findViewById(R.id.menu_profile).setVisibility(View.GONE);
			findViewById(R.id.menu_profile_divider).setVisibility(View.GONE);
			findViewById(R.id.menu_settings).setVisibility(View.GONE);
			findViewById(R.id.menu_settings_divider).setVisibility(View.GONE);
			findViewById(R.id.menu_logout).setVisibility(View.GONE);
			findViewById(R.id.menu_logout_divider).setVisibility(View.GONE);
		}
		else {
			username.setText("Logged in as " + Utility.getUsername(this));
		}
	}
	
	public void menuProfile(View v) {
		menu.showContent();
		Intent profile = new Intent(this, Profile.class);
		profile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		profile.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		profile.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(profile);
		overridePendingTransition(0, 0);
	}
	
	public void menuBrowse(View v) {
		menu.showContent();
		Intent browse = new Intent(this, BrowseCollections.class);
		browse.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		browse.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		browse.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(browse);
		overridePendingTransition(0, 0);
	}
	
	public void menuSettings(View v) {
		menu.showContent();
		Intent settings = new Intent(this, SettingsActivity.class);
		settings.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		settings.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		settings.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(settings);
		overridePendingTransition(0, 0);
	}

	public void menuLogout(View v) {
		Utility.prefs(this).edit().putString("username", "").commit();
		startActivity(new Intent(this, Login.class));
		finish();
	}

}
