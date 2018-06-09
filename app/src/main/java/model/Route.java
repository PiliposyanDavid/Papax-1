package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Route {

	@SerializedName("overview_polyline")
	public OverViewPolyLine polyLine;

	@SerializedName("legs")
	public List<Legs> legs;


}
