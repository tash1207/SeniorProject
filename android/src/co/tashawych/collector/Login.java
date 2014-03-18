package co.tashawych.collector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Login extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	public void btn_login_clicked(View v) {
		EditText edit_username = (EditText) findViewById(R.id.edit_username);
		//EditText edit_password = (EditText) findViewById(R.id.edit_password);
		
		String username = edit_username.getText().toString();
		//String password = edit_password.getText().toString();
		
		Toast.makeText(this, "Username: " + username, Toast.LENGTH_SHORT).show();
		
		Intent profile = new Intent(this, Profile.class);
		startActivity(profile);
	}

	public void btn_signup_clicked(View v) {
		//EditText edit_username = (EditText) findViewById(R.id.edit_username);
		//EditText edit_password = (EditText) findViewById(R.id.edit_password);
		EditText edit_email = (EditText) findViewById(R.id.edit_email);
		
		//String username = edit_username.getText().toString();
		//String password = edit_password.getText().toString();
		String email = edit_email.getText().toString();
		
		Toast.makeText(this, "Email: " + email, Toast.LENGTH_SHORT).show();
		
		// TODO from signup, pass a first_time boolean to profile to show mini tutorial
		Intent profile = new Intent(this, Profile.class);
		startActivity(profile);
	}

	public void btn_browse_clicked(View v) {
		Toast.makeText(this, "Enter Browse Collections screen", Toast.LENGTH_SHORT).show();
	}
	
	public void btn_bottom_signup_clicked(View v) {
		final LinearLayout login_layout = (LinearLayout) findViewById(R.id.layout_login);
		final LinearLayout signup_layout = (LinearLayout) findViewById(R.id.layout_signup);
		
		final Button btn_login = (Button) findViewById(R.id.btn_bottom_login);
		final Button btn_signup = (Button) findViewById(R.id.btn_bottom_signup);
		
		btn_login.setVisibility(View.VISIBLE);
		btn_signup.setVisibility(View.GONE);

		login_layout.setVisibility(View.GONE);
		Animation fade_in = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
		signup_layout.startAnimation(fade_in);
		signup_layout.setVisibility(View.VISIBLE);
	}
	
	public void btn_bottom_login_clicked(View v) {
		final LinearLayout login_layout = (LinearLayout) findViewById(R.id.layout_login);
		final LinearLayout signup_layout = (LinearLayout) findViewById(R.id.layout_signup);
		
		final Button btn_login = (Button) findViewById(R.id.btn_bottom_login);
		final Button btn_signup = (Button) findViewById(R.id.btn_bottom_signup);
		
		btn_login.setVisibility(View.GONE);
		btn_signup.setVisibility(View.VISIBLE);

		signup_layout.setVisibility(View.INVISIBLE);
		Animation fade_in = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
		login_layout.startAnimation(fade_in);
		login_layout.setVisibility(View.VISIBLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
