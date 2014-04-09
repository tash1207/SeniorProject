package co.tashawych.misc;

import java.io.ByteArrayOutputStream;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View.MeasureSpec;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import co.tashawych.collector.R;

public class Utility {
	public static final String cat_book = "Books";
	public static final String cat_card = "Cards";
	public static final String cat_coin = "Coins";
	public static final String cat_electronic = "Electronics";
	public static final String cat_figurine = "Figurines";
	public static final String cat_media = "Media";
	public static final String cat_stamp = "Stamps";
	
	public static final String URL = "http://tashawych.co:8080";
	
	public static SharedPreferences prefs(Context context) {
		return context.getSharedPreferences("Collector", Context.MODE_PRIVATE);
	}
	
	public static String getUsername(Context context) {
		return prefs(context).getString("username", "");
	}
	
	public static int getListViewHeight(ListView list) {
		ListAdapter adapter = list.getAdapter();
		list.measure(MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED), 
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		return list.getMeasuredHeight() * adapter.getCount() + (list.getDividerHeight() * adapter.getCount());
	}
	
	public static int getScreenWidth(Activity activity) {
		 DisplayMetrics metrics = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
			return metrics.widthPixels;
	}
	
	public static int getPixels(Context context, float dips) {
		int pixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
	            dips, context.getResources().getDisplayMetrics());
		return pixels;
	}
	
	public static int getPictureForCategory(String cat) {
		if (cat.equals(Utility.cat_book)) {
			return R.drawable.ic_book;
		}
		else if (cat.equals(Utility.cat_card)) {
			return R.drawable.ic_card;
		}
		else if (cat.equals(Utility.cat_coin)) {
			return R.drawable.ic_coin;
		}
		else if (cat.equals(Utility.cat_electronic)) {
			return R.drawable.ic_electronic;
		}
		else if (cat.equals(Utility.cat_figurine)) {
			return R.drawable.ic_figurine;
		}
		else if (cat.equals(Utility.cat_media)) {
			return R.drawable.ic_media;
		}
		else if (cat.equals(Utility.cat_stamp)) {
			return R.drawable.ic_stamp;
		}
		else {
			return R.drawable.logo_no_bg;
		}
	}

	public static boolean doCrop(Context context, Uri mImageCaptureUri,
			int outputX, int outputY, int aspectX, int aspectY, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setType("image/*");

		List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent, 0);

		int size = list.size();

		if (size == 0) {
			Toast.makeText(context, "Cannot find photo cropper", Toast.LENGTH_SHORT).show();
			return false;
		}
		else {
			intent.setData(mImageCaptureUri);
			intent.putExtra("outputX", outputX);
			intent.putExtra("outputY", outputY);
			intent.putExtra("aspectX", aspectX);
			intent.putExtra("aspectY", aspectY);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);

			try {
				Intent i = new Intent(intent);
				ResolveInfo res = list.get(0);
				i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
				((Activity) context).startActivityForResult(i, requestCode);
				return true;
			} catch (Exception e) {
				Toast.makeText(context, "Problem with photo cropper", Toast.LENGTH_SHORT).show();
				return false;
			}
		}
	}

	public static String getBitmapAsBase64String(Bitmap bitmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 100, outputStream);
		byte[] b = outputStream.toByteArray();
		return Base64.encodeToString(b, Base64.DEFAULT);
	}

	public static Bitmap getBitmapFromString(String imgString) {
		byte[] b = Base64.decode(imgString, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(b, 0, b.length);
	}

}
