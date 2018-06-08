package utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import java.util.List;

public class LocationUtils {

	public static final int LOCATION_UPDATE_MIN_DISTANCE = 10;
	public static final int LOCATION_UPDATE_MIN_TIME = 5000;

	public static List<Address> getLocationFromGeocoder(Context context, final double latitude, final double longitude, int count) {
		final Geocoder ge = new Geocoder(context);

		List<android.location.Address> addresses = null;
		try {
			addresses = ge.getFromLocation(latitude, longitude, count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return addresses;

	}

	@SuppressLint("MissingPermission")
	public static Location getCurrentLocation(LocationManager mLocationManager, LocationListener mLocationListener) {
		boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		Location location = null;
		if (!(isGPSEnabled || isNetworkEnabled)) {
			return null;
		} else {
			if (isNetworkEnabled) {
				mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
						LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
				location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			}

			if (isGPSEnabled) {
				mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
						LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
				location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			}
		}
		if (location != null) {
			return location;
		} else {
			return null;
		}
	}

}
