package co.tashawych.collector;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import co.tashawych.datatypes.Item;
import co.tashawych.db.DatabaseHelper;
import co.tashawych.misc.Utility;

public class ViewItem extends Activity {
	Item item;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_item);

		String item_id = getIntent().getStringExtra("item_id");
		item = DatabaseHelper.getHelper(this).getItem(item_id);

		ImageView picture = (ImageView) findViewById(R.id.item_picture);
		TextView title = (TextView) findViewById(R.id.item_title);
		TextView description = (TextView) findViewById(R.id.item_description);

		picture.setImageBitmap(Utility.getBitmapFromString(item.getPicture()));
		title.setText(item.getTitle());
		description.setText(item.getDescription());
	}

	public void shadeClicked(View v) {
		finish();
	}

}
