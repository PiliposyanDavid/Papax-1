package model;

import com.google.gson.annotations.SerializedName;

public class Car {

	@SerializedName("model")
	String carModel;

	@SerializedName("color")
	String carColor;

	@SerializedName("number")
	String carNumber;

	@SerializedName("seats_count")
	int seatsCount;

	public Car(String carModel, String carColor, String carNumber, int seatsCount) {
		this.carModel = carModel;
		this.carColor = carColor;
		this.carNumber = carNumber;
		this.seatsCount = seatsCount;
	}

	public Car() {
	}
}
