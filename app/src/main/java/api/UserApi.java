package api;


import org.json.JSONObject;

import model.Car;
import model.HomeLocation;
import model.UserResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserApi {

	@FormUrlEncoded
	@POST("login")
	Call<UserResult> login(@Field("phone") String phone, @Field("password") String password);

	@FormUrlEncoded
	@POST("driver/signup")
	Call<UserResult> driverSignUp(@Field("photo") String photoUrl, @Field("phone") String phone,
	                              @Field("email") String email, @Field("password") String passWord,
	                              @Field("home_location") HomeLocation homeLocation, @Field("car") Car car);

	@FormUrlEncoded
	@POST("passenger/signup")
	Call<UserResult> passengerSignUp(@Field("photo") String photoUrl, @Field("name") String userName, @Field("email") String email, @Field("password") String passWord, @Field("phone") String phone, @Part("home_location") HomeLocation homeLocation);

}
