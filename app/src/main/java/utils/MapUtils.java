package utils;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;

public class MapUtils {

	public static CameraUpdate getCameraUpdate(List<LatLng> latLngList) {
		LatLngBounds.Builder builder = new LatLngBounds.Builder();
		for (LatLng latLng : latLngList) {
			builder.include(latLng);
		}
		return CameraUpdateFactory.newLatLngBounds(builder.build(), 100);
	}

	public static CameraUpdate getCameraUpdate2(List<LatLng> latLngList, int width, int height) {
		LatLngBounds.Builder builder = new LatLngBounds.Builder();
		for (LatLng latLng : latLngList) {
			builder.include(latLng);
		}
		return CameraUpdateFactory.newLatLngBounds(builder.build(), width, height, 100);
	}

}
