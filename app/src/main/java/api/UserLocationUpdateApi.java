package api;

import com.google.gson.JsonObject;

import model.Status;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserLocationUpdateApi {
	@FormUrlEncoded
	@POST("update_location")
	Call<Status> updateLocation(String userId, JsonObject location);
}
