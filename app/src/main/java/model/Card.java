package model;

import com.google.gson.annotations.SerializedName;

public class Card {
	public static final String ROUTE_SUGGETION_TYPE = "route_suggestion_type";
	public static final String DRIVER_INFO_CARD = "driver_info_card";


	@SerializedName("type")
	public String type;


}
