package api;

import io.reactivex.Single;
import model.Directions;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapsApi {

	@GET("api/directions/json?sensor=false&key=AIzaSyAIuSAJRJ8Lry7ArTN5xINXLgazRNUYe0g")
	Single<Directions> getDirections(@Query("origin") String origin, @Query("destination") String dest);

}
