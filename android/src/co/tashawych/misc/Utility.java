package co.tashawych.misc;

import android.view.View.MeasureSpec;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Utility {
	public static final String cat_book = "Books";
	public static final String cat_card = "Cards";
	public static final String cat_coin = "Coins";
	public static final String cat_electronic = "Electronics";
	public static final String cat_figurine = "Figurines";
	public static final String cat_media = "Media";
	public static final String cat_stamp = "Stamps";
	
	public static final String URL = "http://tashawych.co:8080";
	
	public static int getListViewHeight(ListView list) {
		ListAdapter adapter = list.getAdapter();
		list.measure(MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED), 
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		return list.getMeasuredHeight() * adapter.getCount() + (list.getDividerHeight() * adapter.getCount());
	}

}
