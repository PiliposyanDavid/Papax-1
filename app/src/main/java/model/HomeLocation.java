package model;

import com.google.gson.annotations.SerializedName;

public class HomeLocation {

	@SerializedName("latitude")
	public String latitude;

	@SerializedName("longitude")
	public String longitude;

	public HomeLocation(String latitude, String longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
