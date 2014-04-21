package co.tashawych.collector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import co.tashawych.datatypes.Collection;
import co.tashawych.db.DatabaseHelper;
import co.tashawych.http.HttpRequest;
import co.tashawych.misc.Utility;

public class AddCollection extends BaseActivity {
	Collection col;
	String picture = "";
	boolean edit_collection;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_collection);

		String col_id = getIntent().getStringExtra("col_id");
		col = DatabaseHelper.getHelper(this).getCollection(col_id);
		edit_collection = col == null ? false : true;

		if (edit_collection) {
			final ImageView edit_picture = (ImageView) findViewById(R.id.edit_picture);
			final EditText edit_title = (EditText) findViewById(R.id.edit_title);
			final EditText edit_desc = (EditText) findViewById(R.id.edit_description);
			final Button edit_category = (Button) findViewById(R.id.edit_category);
			final Button btn_edit = (Button) findViewById(R.id.btn_add_collection);

			if (col.getPicture().equals("")) {
				edit_picture.setImageResource(Utility.getPictureForCategory(col.getCategory()));
			}
			else {
				picture = col.getPicture();
				edit_picture.setImageBitmap(Utility.getBitmapFromString(col.getPicture()));
			}
			edit_title.setText(col.getTitle());
			edit_desc.setText(col.getDescription());
			edit_category.setText(col.getCategory());
			btn_edit.setText("Edit Collection");
		}
	}
	
	public void uploadPicture(View v) {
		Intent uploadPic = new Intent(Intent.ACTION_PICK, 
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(Intent.createChooser(uploadPic, "Upload photo using:"), UPLOAD_PIC);
	}

	public void selectCategory(View v) {
		final ImageView edit_picture = (ImageView) findViewById(R.id.edit_picture);
		final Button edit_category = (Button) findViewById(R.id.edit_category);
		final String[] categories = getResources().getStringArray(R.array.collection_categories);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.category).setItems(R.array.collection_categories,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						edit_category.setText(categories[which]);
						if (picture.equals("")) {
							switch (which) {
							case 0:
								edit_picture.setImageResource(R.drawable.ic_book);
								break;
							case 1:
								edit_picture.setImageResource(R.drawable.ic_card);
								break;
							case 2:
								edit_picture.setImageResource(R.drawable.ic_coin);
								break;
							case 3:
								edit_picture.setImageResource(R.drawable.ic_electronic);
								break;
							case 4:
								edit_picture.setImageResource(R.drawable.ic_figurine);
								break;
							case 5:
								edit_picture.setImageResource(R.drawable.ic_media);
								break;
							case 6:
								edit_picture.setImageResource(R.drawable.ic_stamp);
								break;
							case 7:
								edit_picture.setImageResource(R.drawable.logo_no_bg);
								break;
							default:
								break;
							}
						}
					}
				});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void addCollection(View v) {
		if (!Utility.hasInternetAccess(this)) {
			Toast.makeText(this, "You need to have internet access!", Toast.LENGTH_SHORT).show();
			return;
		}
		EditText edit_title = (EditText) findViewById(R.id.edit_title);
		EditText edit_desc = (EditText) findViewById(R.id.edit_description);
		Button edit_category = (Button) findViewById(R.id.edit_category);

		if (edit_title.getText().toString().equals("")) {
			Toast.makeText(this, "You need to enter a title", Toast.LENGTH_SHORT).show();
			return;
		}

		String title = edit_title.getText().toString();
		String description = edit_desc.getText().toString();
		String category = edit_category.getText().toString();
		if (category.equals("Other")) category = "";
		
		if (!edit_collection) {
			// Create a new collection
			col = new Collection(Utility.prefs(AddCollection.this).getString("username", ""), 
					title, description, category, false, picture);
			String col_id = "";

			try {
				// Send the collection to the server to get a Collection id
				col_id = new create_collection(col).execute().get();
				if (col_id.equals("")) {
					Toast.makeText(this, "An error occurred. Please try again!", Toast.LENGTH_SHORT).show();
				}
				else {
					// Store this collection client-side
					col.setId(col_id);
					DatabaseHelper.getHelper(this).insertCollection(col);
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
		else {
			// Edit existing collection
			col.setTitle(title);
			col.setDescription(description);
			col.setCategory(category);
			col.setPicture(picture);

			try {
				String response = new edit_collection(col).execute().get();
				if (response.equals("")) {
					Toast.makeText(this, "An error occurred. Please try again!", Toast.LENGTH_SHORT).show();
				}
				else {
					// Store this collection client-side
					DatabaseHelper.getHelper(this).updateCollection(col);
					finish();
				}
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(this, "An error occurred. Please try again!", Toast.LENGTH_SHORT).show();
			}
		}

	}
	
	private class create_collection extends AsyncTask<Void, Void, String> {
		final Collection col;
		
		private create_collection(Collection col) {
			this.col = col;
		}

		@Override
		protected String doInBackground(Void... params) {
			try {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
	            nameValuePairs.add(new BasicNameValuePair("title", col.getTitle()));
	            nameValuePairs.add(new BasicNameValuePair("description", col.getDescription()));
	            nameValuePairs.add(new BasicNameValuePair("category", col.getCategory()));
	            nameValuePairs.add(new BasicNameValuePair("picture", col.getPicture()));
	            nameValuePairs.add(new BasicNameValuePair("username", col.getUsername()));

	            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs);

				return HttpRequest.POST(Utility.URL + "/addCollection", entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	private class edit_collection extends AsyncTask<Void, Void, String> {
		final Collection col;
		
		private edit_collection(Collection col) {
			this.col = col;
		}

		@Override
		protected String doInBackground(Void... params) {
			try {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
				nameValuePairs.add(new BasicNameValuePair("_id", col.getId()));
	            nameValuePairs.add(new BasicNameValuePair("title", col.getTitle()));
	            nameValuePairs.add(new BasicNameValuePair("description", col.getDescription()));
	            nameValuePairs.add(new BasicNameValuePair("category", col.getCategory()));
	            nameValuePairs.add(new BasicNameValuePair("picture", col.getPicture()));

	            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs);

				return HttpRequest.POST(Utility.URL + "/editCollection", entity);
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
