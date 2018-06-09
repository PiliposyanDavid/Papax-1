package utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class ScreenUtils {

	public static int screenWidth(Activity context) {
		DisplayMetrics metrics = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.widthPixels;
	}

	public static int screenHeight(Activity context) {
		DisplayMetrics metrics = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;
	}
}
