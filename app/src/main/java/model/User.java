package model;

import com.google.gson.annotations.SerializedName;

public class User {

	public static final String PASSENGER = "passenger";

	@SerializedName("_id")
	String userId;

	@SerializedName("phone")
	String phone;

	@SerializedName("email")
	String email;

	@SerializedName("photo")
	String photoUrl;

	@SerializedName("home_location")
	HomeLocation homeLocation;

	@SerializedName("type")
	String type;

	@SerializedName("isActive")
	boolean isActive;

	@SerializedName("car")
	Car car;

	public boolean isDriver() {
		return PASSENGER.equals(type);
	}

}
