package co.tashawych.collector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import co.tashawych.datatypes.User;
import co.tashawych.db.DatabaseHelper;
import co.tashawych.http.HttpRequest;
import co.tashawych.misc.Utility;

public class Login extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		if (!Utility.getUsername(this).equals("")) {
			Intent profile = new Intent(this, Profile.class);
			startActivity(profile);
			finish();
		}
	}

	public void btn_login_clicked(View v) {
		if (!Utility.hasInternetAccess(this)) {
			Toast.makeText(this, "You need to have internet access!", Toast.LENGTH_SHORT).show();
			return;
		}
		EditText edit_username = (EditText) findViewById(R.id.edit_username_login);
		EditText edit_password = (EditText) findViewById(R.id.edit_password_login);

		String username = edit_username.getText().toString();
		String password = edit_password.getText().toString();

		String response = "";
		JSONObject json;
		User user = null;

		try {
			response = new login_post(username, password).execute().get();
			json = new JSONObject(response);
			user = new User(json);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (user != null) {
			// TODO from signup, pass a first_time boolean to profile to show mini tutorial
			DatabaseHelper.getHelper(this).insertUser(user);
			Utility.prefs(this).edit().putString("username", user.getUsername()).commit();
			Intent profile = new Intent(this, Profile.class);
			startActivity(profile);
		}
		else {
			Toast.makeText(this, "Login failed.", Toast.LENGTH_SHORT).show();
		}
	}

	public void btn_signup_clicked(View v) {
		if (!Utility.hasInternetAccess(this)) {
			Toast.makeText(this, "You need to have internet access!", Toast.LENGTH_SHORT).show();
			return;
		}
		EditText edit_username = (EditText) findViewById(R.id.edit_username_signup);
		EditText edit_password = (EditText) findViewById(R.id.edit_password_signup);
		EditText edit_email = (EditText) findViewById(R.id.edit_email_signup);

		String username = edit_username.getText().toString();
		String password = edit_password.getText().toString();
		String email = edit_email.getText().toString();

		if (username.length() < 3 || password.length() < 3) {
			Toast.makeText(this, "Username and Password must be at least 3 characters", Toast.LENGTH_SHORT).show();
			return;
		}

		String response = "";

		try {
			response = new signup_post(username, password, email).execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		if (!response.equals("")) {
			// TODO from signup, pass a first_time boolean to profile to show mini tutorial
			Utility.prefs(this).edit().putString("username", response).commit();
			Intent profile = new Intent(this, Profile.class);
			startActivity(profile);
		}
		else {
			Toast.makeText(this, "Signup failed.", Toast.LENGTH_SHORT).show();
		}
	}

	public void btn_browse_clicked(View v) {
		Intent browse = new Intent(this, BrowseCollections.class);
		startActivity(browse);
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

	private class login_post extends AsyncTask<Void, Void, String> {
		ProgressDialog dialog = new ProgressDialog(Login.this);
		final String username;
		final String password;

		private login_post(String username, String password) {
			this.username = username;
			this.password = password;
		}

		@Override
		protected void onPreExecute() {
			try {
				dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				dialog.setMessage("Logging In...");
				dialog.setCancelable(true);
				dialog.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		protected String doInBackground(Void... params) {
			try {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("username", username));
				nameValuePairs.add(new BasicNameValuePair("password", password));
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs);

				return HttpRequest.POST(Utility.URL + "/login", entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
		}

	}

	private class signup_post extends AsyncTask<Void, Void, String> {
		ProgressDialog dialog = new ProgressDialog(Login.this);
		final String username;
		final String password;
		final String email;

		private signup_post(String username, String password, String email) {
			this.username = username;
			this.password = password;
			this.email = email;
		}

		@Override
		protected void onPreExecute() {
			try {
				dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				dialog.setMessage("Signing Up...");
				dialog.setCancelable(true);
				dialog.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		protected String doInBackground(Void... params) {
			try {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
				nameValuePairs.add(new BasicNameValuePair("username", username));
				nameValuePairs.add(new BasicNameValuePair("password", password));
				nameValuePairs.add(new BasicNameValuePair("email", email));
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs);

				return HttpRequest.POST(Utility.URL + "/signup", entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
		}

	}

}
