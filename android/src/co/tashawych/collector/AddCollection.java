package co.tashawych.collector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import co.tashawych.datatypes.Collection;
import co.tashawych.db.DatabaseHelper;

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
		EditText edit_id = (EditText) findViewById(R.id.edit_id);
		EditText edit_title = (EditText) findViewById(R.id.edit_title);
		EditText edit_desc = (EditText) findViewById(R.id.edit_description);
		Button edit_category = (Button) findViewById(R.id.edit_category);

		if (edit_id.getText().toString().equals("") || edit_title.getText().toString().equals("")) {
			Toast.makeText(this, "You need to enter an id and a title", Toast.LENGTH_SHORT).show();
			return;
		}

		int id = Integer.valueOf(edit_id.getText().toString());
		String title = edit_title.getText().toString();
		String description = edit_desc.getText().toString();
		String category = edit_category.getText().toString();
		if (category.equals("Other")) category = "";

		// Send the collection to the server to get a Collection id, then store
		// client-side
		Collection col = new Collection(id, 1, title, description, category, false, null);

		// Store this collection client-side
		DatabaseHelper.getHelper(this).insertCollection(col);
		finish();
	}

}
