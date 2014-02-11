package co.tashawych.seniorproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Profile extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		TextView name = (TextView) findViewById(R.id.profile_name);
		TextView num = (TextView) findViewById(R.id.profile_num_collections);
		
		name.setText("Natalya Dominika Hankewych");
		num.setText("2");
	}

	public void btn_create_collection_clicked(View v) {
		
	}
}
