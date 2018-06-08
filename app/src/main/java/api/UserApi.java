package api;


import model.UserResult;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserApi {

	@FormUrlEncoded
	@POST("login")
	 Call<UserResult> login(@Field("phone") String phone, @Field("password") String password);

	@FormUrlEncoded
	@POST("passenger/signup")
	Call<UserResult> signUp();
}
