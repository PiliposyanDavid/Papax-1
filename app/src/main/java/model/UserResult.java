package model;

import com.google.gson.annotations.SerializedName;

public class UserResult extends Status {

	@SerializedName("data")
	public User user;
}
