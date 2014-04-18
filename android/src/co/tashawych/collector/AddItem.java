package co.tashawych.collector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
import co.tashawych.datatypes.Item;
import co.tashawych.db.DatabaseHelper;
import co.tashawych.http.HttpRequest;
import co.tashawych.misc.Utility;


public class AddItem extends BaseActivity {
	Item item;
	String item_id;
	String col_id;
	String picture = "";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_item);

		col_id = getIntent().getStringExtra("col_id");
	}

	public void uploadPicture(View v) {
		Intent uploadPic = new Intent(Intent.ACTION_PICK, 
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(Intent.createChooser(uploadPic, "Upload photo using:"), UPLOAD_PIC);
	}

	public void addItem(View v) {
		EditText edit_title = (EditText) findViewById(R.id.edit_title);
		EditText edit_desc = (EditText) findViewById(R.id.edit_description);

		String title = edit_title.getText().toString();
		String description = edit_desc.getText().toString();

		if (picture.equals("") && title.equals("")) {
			Toast.makeText(this, "You need add a picture or a title for the item!", Toast.LENGTH_SHORT).show();
			return;
		}

		item = new Item(col_id, title, description, picture);
		item_id = "";

		try {
			// Send the item to the server to get an Item id
			item_id = new add_item(item).execute().get();
			if (item_id.equals("")) {
				Toast.makeText(this, "An error occurred. Please try again!", Toast.LENGTH_SHORT).show();
			}
			else {
				// Store this item client-side
				item.setId(item_id);
				DatabaseHelper.getHelper(this).insertItem(item);
				finish();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Toast.makeText(this, "An error occurred. Please try again!", Toast.LENGTH_SHORT).show();
		} catch (ExecutionException e) {
			e.printStackTrace();
			Toast.makeText(this, "An error occurred. Please try again!", Toast.LENGTH_SHORT).show();
		}

	}

	private class add_item extends AsyncTask<Void, Void, String> {
		final Item item;

		private add_item(Item item) {
			this.item = item;
		}

		@Override
		protected String doInBackground(Void... params) {
			try {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
				nameValuePairs.add(new BasicNameValuePair("col_id", col_id));
	            nameValuePairs.add(new BasicNameValuePair("title", item.getTitle()));
	            nameValuePairs.add(new BasicNameValuePair("description", item.getDescription()));
	            nameValuePairs.add(new BasicNameValuePair("picture", item.getPicture()));

	            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs);

				return HttpRequest.POST(Utility.URL + "/addItem", entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
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
					final ImageView edit_picture = (ImageView) findViewById(R.id.edit_picture);
					edit_picture.setImageBitmap(uploaded_photo);
				}

				else {
					Utility.doCrop(this, selectedImage, 175, 175, 1, 1, AFTER_CROP);
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
