package co.tashawych.collector;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class ViewCollectionAdapter extends ArrayAdapter<Integer> {
	Context context;
	int layout;
	List<Integer> ints;

	public ViewCollectionAdapter(Context context, int layout, int view, List<Integer> ints) {
		super(context, layout, view, ints);
		this.context = context;
		this.layout = layout;
		this.ints = ints;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(layout, parent, false);

		ImageView image = (ImageView) convertView.findViewById(R.id.gridview_item);
		image.setImageResource(ints.get(position));

		return convertView;
	}

}
