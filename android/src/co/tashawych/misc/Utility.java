package co.tashawych.misc;

import android.view.View.MeasureSpec;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Utility {
	
	public static int getListViewHeight(ListView list) {
		ListAdapter adapter = list.getAdapter();
		list.measure(MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED), 
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		return list.getMeasuredHeight() * adapter.getCount() + (list.getDividerHeight() * adapter.getCount());
	}

}
