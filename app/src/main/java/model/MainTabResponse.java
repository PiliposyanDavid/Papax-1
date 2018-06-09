package model;

import android.support.annotation.Nullable;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainTabResponse {

	@SerializedName("message")
	public String message;

	@SerializedName("data")
	public List<JsonObject> data;

	@Nullable
	public JsonObject getToWorkData() {
		if (data == null) {
			return null;
		}
		return data.get(0);
	}@Nullable
	public JsonObject getLunchData() {
		if (data == null || data.size() < 1) {
			return null;
		}
		return data.get(1);
	}@Nullable
	public JsonObject getFromWorkData() {
		if (data == null || data.size() < 2) {
			return null;
		}
		return data.get(2);
	}
}
