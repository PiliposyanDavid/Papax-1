package model;

import com.google.gson.annotations.SerializedName;

public class Status {

	public static final String BASE_URL = "success";

	@SerializedName("status")
	public String status;

	@SerializedName("message")
	public String message;

	public boolean isSuccessful(){
		return BASE_URL.equals(status);
	}
}
