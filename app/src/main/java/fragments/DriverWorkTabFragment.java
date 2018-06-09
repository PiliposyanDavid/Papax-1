package fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.papax.ag.papax.Constants;
import com.papax.ag.papax.R;
import com.papax.ag.papax.view.LocationSelectView;

import java.util.List;

import api.MapsApi;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import model.MainTabResponse;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.LocationUtils;

import static android.content.Context.LOCATION_SERVICE;

@SuppressLint("CheckResult")
public class DriverWorkTabFragment extends Fragment implements OnMapReadyCallback, TabFragmentDataInterface {

	private MapsApi mapsApi;
	private LocationSelectView startLocation, endLocation;
	private TextView minusBtn;
	private Retrofit retrofit = new Retrofit.Builder()
			.baseUrl("https://maps.googleapis.com/maps/")
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.addConverterFactory(GsonConverterFactory.create()).build();


	private ProgressBar progressBar;
//	private LocationSelectView selectStarLocationView;

	private int tabPosition;

	private final LocationListener mLocationListener = new LocationListener() {

		@Override
		public void onLocationChanged(final Location location) {
			if (startLocation != null) {
				Single.just(LocationUtils.getLocationFromGeocoder(
						getActivity(),
						location.getLatitude(),
						location.getLongitude(), 1).get(0)).subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(o -> {
							if (startLocation != null) {
								startLocation.setProgressMode(false);
								startLocation.setLocationText(o.getThoroughfare() + ", " + o.getSubThoroughfare());
							}

							if (googleMap != null) {
								googleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())))
										.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker_bitmap));
								googleMap.addMarker(new MarkerOptions().position(new LatLng(Constants.PICSART_LATITUDE, Constants.PICSART_LONGITUDE)))
										.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker_bitmap));
								getDirections(location);
							}
						});
			}
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		@Override
		public void onProviderEnabled(String provider) {

		}

		@Override
		public void onProviderDisabled(String provider) {

		}
	};

	private void getDirections(Location location) {
		mapsApi.getDirections(location.getLatitude() + "," + location.getLongitude(),
				Constants.PICSART_LATITUDE + "," + Constants.PICSART_LONGITUDE).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread()).subscribe(directions -> {
			if (directions != null && directions.routes != null && !directions.routes.isEmpty() && directions.routes.get(0) != null) {
				List<LatLng> latLngList = PolyUtil.decode(directions.routes.get(0).polyLine.points);
				googleMap.addPolyline(new PolylineOptions().addAll(latLngList).color(Color.parseColor("#42BFEA")));
				googleMap.moveCamera(getCameraUpdate(latLngList));
			}
		});
	}

	private CameraUpdate getCameraUpdate(List<LatLng> latLngList) {
		LatLngBounds.Builder builder = new LatLngBounds.Builder();
		for (LatLng latLng : latLngList) {
			builder.include(latLng);
		}
		return CameraUpdateFactory.newLatLngBounds(builder.build(), 100);
	}

	private GoogleMap googleMap;

	@SuppressLint("MissingPermission")
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mapsApi = retrofit.create(MapsApi.class);

	}


	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.work_tab_fragment_layout, container, false);
	}


	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		progressBar = view.findViewById(R.id.progress);
		progressBar.setVisibility(View.VISIBLE);
		startLocation = view.findViewById(R.id.start_location);
//		selectStarLocationView = view.findViewById(R.id.selec)
		endLocation = view.findViewById(R.id.end_location);
		endLocation.setLocationText("Picsart");
		endLocation.setEditable(false);
		startLocation.setProgressMode(true);
		startLocation.setOnEditClickListener(v -> {

		});

		endLocation.setOnEditClickListener(v -> {

		});
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		this.googleMap = googleMap;
		googleMap.getUiSettings().setScrollGesturesEnabled(false);
		googleMap.setOnMapClickListener(latLng -> {

		});
	}


	@Override
	public void onDataUpdated(MainTabResponse mainTabResponse) {
		if (getView() != null) {
			getView().post(() -> {

				LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
				LocationUtils.getCurrentLocation(locationManager, mLocationListener);
			});
		}

	}

	public void setTabPosition(int pos) {
		this.tabPosition = pos;
	}

	@Override
	public void setUserTypeIsPassanger(boolean isPassanger) {

	}
}