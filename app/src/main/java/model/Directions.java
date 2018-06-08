package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Directions {

	@SerializedName("routes")
	public List<Route> routes;

	@SerializedName("status")
	public String status;

}
