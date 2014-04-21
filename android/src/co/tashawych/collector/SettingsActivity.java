package co.tashawych.collector;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import co.tashawych.datatypes.User;
import co.tashawych.db.DatabaseHelper;
import co.tashawych.http.HttpRequest;
import co.tashawych.misc.Utility;

public class SettingsActivity extends BaseActivity {
	User user;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setupSideMenu();

		user = DatabaseHelper.getHelper(this).getUser(Utility.getUsername(this));

		ImageView picture = (ImageView) findViewById(R.id.settings_picture);
		if (user.getPicture() != null && !user.getPicture().equals("")) {
			picture.setImageBitmap(Utility.getBitmapFromString(user.getPicture()));
		}

		EditText edit_name = (EditText) findViewById(R.id.settings_display_name);
		EditText edit_email = (EditText) findViewById(R.id.settings_email);

		edit_name.setText(user.getDisplayName());
		edit_email.setText(user.getEmail());
	}

	public void menuClicked(View v) {
		menu.showMenu();
	}

	public void uploadPicture(View v) {
		Intent uploadPic = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(Intent.createChooser(uploadPic, "Upload photo using:"), UPLOAD_PIC);
	}

	public void saveChanges(View v) {
		if (!Utility.hasInternetAccess(this)) {
			Toast.makeText(this, "You need to have internet access!", Toast.LENGTH_SHORT).show();
			return;
		}
		EditText edit_name = (EditText) findViewById(R.id.settings_display_name);
		EditText edit_email = (EditText) findViewById(R.id.settings_email);

		String display_name = edit_name.getText().toString();
		String email = edit_email.getText().toString();

		user.setDisplayName(display_name);
		user.setEmail(email);

		new edit_user(user).execute();
	}

	public void removeData(View v) {
		// TODO Create a db command that removes all collections and items that don't
		// belong to the current user (client-side only)
	}

	private class edit_user extends AsyncTask<Void, Void, String> {
		final User user;

		private edit_user(User user) {
			this.user = user;
		}

		@Override
		protected String doInBackground(Void... params) {
			try {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
				nameValuePairs.add(new BasicNameValuePair("username", user.getUsername()));
				nameValuePairs.add(new BasicNameValuePair("display_name", user.getDisplayName()));
				nameValuePairs.add(new BasicNameValuePair("email", user.getEmail()));
				nameValuePairs.add(new BasicNameValuePair("picture", user.getPicture()));

				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs);

				return HttpRequest.POST(Utility.URL + "/editUser", entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String s) {
			if (s != null) {
				Toast.makeText(SettingsActivity.this, s, Toast.LENGTH_SHORT).show();
				DatabaseHelper.getHelper(SettingsActivity.this).updateUser(user);
			} else {
				Toast.makeText(SettingsActivity.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
			}
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, final Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == UPLOAD_PIC && data.getData() != null) {
				Uri selectedImage = data.getData();

				// Check if user has photo cropper
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setType("image/*");
				List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
				int size = list.size();

				// If not, upload image without cropping it
				if (size == 0) {
					String[] filePathColumn = { MediaStore.Images.Media.DATA };
					Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
					cursor.moveToFirst();

					int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
					String filePath = cursor.getString(columnIndex);
					cursor.close();

					Bitmap uploaded_photo = BitmapFactory.decodeFile(filePath);
					final ImageView edit_picture = (ImageView) findViewById(R.id.settings_picture);
					edit_picture.setImageBitmap(uploaded_photo);
				}

				else {
					Utility.doCrop(this, selectedImage, 150, 150, 1, 1, AFTER_CROP);
				}
			}
			// AFTER CROP
			else if (requestCode == AFTER_CROP) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					Bitmap uploaded_photo = extras.getParcelable("data");
					user.setPicture(Utility.getBitmapAsBase64String(uploaded_photo));
					final ImageView edit_picture = (ImageView) findViewById(R.id.settings_picture);
					edit_picture.setImageBitmap(uploaded_photo);
				}
			}

		}
	}
}
