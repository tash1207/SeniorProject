package co.tashawych.collector;

import java.util.List;

import co.tashawych.misc.Utility;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

public class SettingsActivity extends BaseActivity {
	String picture = "";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setupSideMenu();
	}
	
	public void menuClicked(View v) {
		menu.showMenu();
	}

	public void uploadPicture(View v) {
		Intent uploadPic = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(Intent.createChooser(uploadPic, "Upload photo using:"), UPLOAD_PIC);
	}

	public void saveChanges(View v) {

	}
	
	public void removeData(View v) {

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
					picture = Utility.getBitmapAsBase64String(uploaded_photo);
					ImageView photo = (ImageView) findViewById(R.id.edit_picture);
					photo.setImageBitmap(uploaded_photo);
				}
			}

		}
	}
}
