package fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.papax.ag.papax.CreateRouteActivity;
import com.papax.ag.papax.R;
import com.papax.ag.papax.view.LocationSelectView;

import java.util.ArrayList;
import java.util.List;

import api.MapsApi;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import model.Directions;
import model.MainTabResponse;
import model.Route;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.LocationUtils;

import static android.content.Context.LOCATION_SERVICE;
import static utils.MapUtils.getCameraUpdate;

@SuppressLint("CheckResult")
public class DriverWorkTabFragment extends Fragment implements OnMapReadyCallback, TabFragmentDataInterface {

	private static final int OPEN_ROUTE_CHOOSE_ACTIVITY = 1111;

	private MapsApi mapsApi;
	private LocationSelectView startLocation, endLocation;
	private TextView minusBtn;
	private Retrofit retrofit = new Retrofit.Builder()
			.baseUrl("https://maps.googleapis.com/maps/")
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.addConverterFactory(GsonConverterFactory.create()).build();


	private ProgressBar progressBar;
	private TextView plusButton, seatsCount;
	private Location currentLocation;
	private String firstRoutePolyLine;
	private Directions directions;
	private int seatsCounter;

//	private LocationSelectView selectStarLocationView;

	private int tabPosition;

	private final LocationListener mLocationListener = new LocationListener() {

		@Override
		public void onLocationChanged(final Location location) {
			currentLocation = location;
			if (startLocation != null) {
				Single.just(LocationUtils.getLocationFromGeocoder(
						getActivity(),
						location.getLatitude(),
						location.getLongitude(), 1).get(0)).subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(o -> {
							if (startLocation != null) {
								startLocation.setProgressMode(false);
								startLocation.setLocationText(o.getThoroughfare() + (o.getSubThoroughfare() == null ? "" : ", " + o.getSubThoroughfare()));
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
				this.directions = directions;
				firstRoutePolyLine = directions.routes.get(0).polyLine.points;
				List<LatLng> latLngList = PolyUtil.decode(directions.routes.get(0).polyLine.points);

				googleMap.clear();
				googleMap.addPolyline(new PolylineOptions().addAll(latLngList).color(Color.parseColor("#42BFEA")));
				googleMap.moveCamera(getCameraUpdate(latLngList));

				googleMap.addMarker(new MarkerOptions().position(latLngList.get(0)))
						.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker_bitmap));
				googleMap.addMarker(new MarkerOptions().position(latLngList.get(latLngList.size() - 1)))
						.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker_bitmap));

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
		return inflater.inflate(R.layout.driver_work_tab_fragment_layout, container, false);
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

		seatsCount = view.findViewById(R.id.seat_count);
		plusButton = view.findViewById(R.id.plus_btn);
		minusBtn = view.findViewById(R.id.minus_btn);

		minusBtn.setOnClickListener(v -> {
			if (seatsCounter > 0) {
				seatsCounter--;
				seatsCount.setText(String.valueOf(seatsCounter));
			}
		});

		plusButton.setOnClickListener(v -> {
			seatsCounter++;
			seatsCount.setText(String.valueOf(seatsCounter));
		});
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		this.googleMap = googleMap;
		googleMap.getUiSettings().setScrollGesturesEnabled(false);
		googleMap.setOnMapClickListener(latLng -> {

			ArrayList<String> directionsPolyLines = new ArrayList<>();
			ArrayList<String> tabsArrayList = new ArrayList<>();
			for (Route route : directions.routes) {
				directionsPolyLines.add(route.polyLine.points);
				tabsArrayList.add(route.legs.get(0).duration.duration);
			}

			Intent intent = new Intent(getActivity(), CreateRouteActivity.class);
			intent.putExtra(Constants.ORIGIN_LATITUDE, currentLocation.getLatitude());
			intent.putExtra(Constants.ORIGIN_LONGITUDE, currentLocation.getAltitude());
			intent.putStringArrayListExtra(Constants.POLYLINE_PATH, directionsPolyLines);
			intent.putExtra(Constants.START_LOCATION_NAME, startLocation.getLocationText());
			intent.putStringArrayListExtra("tabs", tabsArrayList);
			intent.putExtra(Constants.END_LOCATION_NAME, endLocation.getLocationText());
			startActivityForResult(intent, OPEN_ROUTE_CHOOSE_ACTIVITY);

		});
	}


	@Override
	public void onDataUpdated(MainTabResponse mainTabResponse) {
		if (getView() != null) {
			getView().post(() -> {
				if (mainTabResponse != null) {
					switch (tabPosition) {
						case 0: {
							if (mainTabResponse.getToWorkData() != null) {
//								generateRideSuggestionsFrom(mainTabResponse.getToWorkData());
							} else {
//								generateNoData();
							}
							break;
						}
						case 1: {
							if (mainTabResponse.getLunchData() != null) {
//								generateRideSuggestionsFrom(mainTabResponse.getLunchData());
							} else {
//								generateNoData();
							}
							break;
						}
						case 2: {
							if (mainTabResponse.getLunchData() != null) {
//								generateRideSuggestionsFrom(mainTabResponse.getLunchData());
							} else {
//								generateNoData();
							}
							break;
						}
					}
				}

				LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
				LocationUtils.getCurrentLocation(locationManager, mLocationListener);
			});
		}

	}

	public void generateRideSuggestionsFrom() {

	}

	public void setTabPosition(int pos) {
		this.tabPosition = pos;
	}

	@Override
	public void setUserTypeIsPassanger(boolean isPassanger) {

	}
}