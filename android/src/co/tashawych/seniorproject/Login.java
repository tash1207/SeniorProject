package co.tashawych.seniorproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import co.tashawych.collector.R;

public class Login extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	public void btn_login_clicked(View v) {
		EditText edit_username = (EditText) findViewById(R.id.edit_username);
		EditText edit_password = (EditText) findViewById(R.id.edit_password);
		
		String username = edit_username.getText().toString();
		String password = edit_password.getText().toString();
		
		Toast.makeText(this, "Username: " + username + "\nPassword: " + password, Toast.LENGTH_SHORT).show();
		
		Intent profile = new Intent(this, Profile.class);
		startActivity(profile);
	}

	public void btn_signup_clicked(View v) {
		Toast.makeText(this, "Display Sign Up fields instead", Toast.LENGTH_SHORT).show();
	}

	public void btn_browse_clicked(View v) {
		Toast.makeText(this, "Enter Browse Collections screen", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
