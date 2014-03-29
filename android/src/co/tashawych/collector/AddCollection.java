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
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import co.tashawych.datatypes.Collection;
import co.tashawych.db.DatabaseHelper;
import co.tashawych.http.HttpRequest;
import co.tashawych.misc.Utility;

public class AddCollection extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_collection);
	}

	public void selectCategory(View v) {
		final Button edit_category = (Button) findViewById(R.id.edit_category);
		final String[] categories = getResources().getStringArray(R.array.collection_categories);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.category).setItems(R.array.collection_categories,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						edit_category.setText(categories[which]);
					}
				});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void addCollection(View v) {
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

		Collection col = new Collection(Utility.prefs(AddCollection.this).getString("username", ""), 
				title, description, category, false, "");
		
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

}
