package com.papax.ag.papax;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import api.MapsApi;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import model.Directions;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.MapUtils;
import utils.ScreenUtils;

public class CreateRouteActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

	private LatLng startLocation;
	private TextView startLocationText;
	private TextView endLocationText;
	private Handler handler = new Handler();
	private List<String> directionsList;
	private GoogleMap googleMap;
	private AppCompatButton addStopButton;
	private Marker draggableMarker;
	private ImageView markerChooser;
	private AppCompatButton setButton;
	private LatLng startLoc, endLoc;
	private List<LatLng> wayPointsList;

	private Retrofit retrofit = new Retrofit.Builder()
			.baseUrl("https://maps.googleapis.com/maps/")
			.client(new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.addConverterFactory(GsonConverterFactory.create()).build();


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_route_actvity);
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);

		startLocation = new LatLng(getIntent().getDoubleExtra(Constants.ORIGIN_LATITUDE, -1),
				getIntent().getDoubleExtra(Constants.ORIGIN_LONGITUDE, -1));

		startLocationText = findViewById(R.id.start_location);
		endLocationText = findViewById(R.id.end_location);

		startLocationText.setText(getIntent().getStringExtra(Constants.START_LOCATION_NAME));
		endLocationText.setText(getIntent().getStringExtra(Constants.END_LOCATION_NAME));

		findViewById(R.id.back_button).setOnClickListener(v -> {
			onBackPressed();
		});

		TabLayout tabLayout = findViewById(R.id.sliding_tabs);
		tabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.colorWhite70), ContextCompat.getColor(this, R.color.colorWhite));
		for (String tab : getIntent().getStringArrayListExtra("tabs")) {
			tabLayout.addTab(tabLayout.newTab().setText(tab));
		}

		wayPointsList = new ArrayList<>();

		directionsList = getIntent().getStringArrayListExtra(Constants.POLYLINE_PATH);

		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				if (googleMap != null) {
					googleMap.clear();

					wayPointsList.clear();

					List<LatLng> polyList = PolyUtil.decode(directionsList.get(tab.getPosition()));

					googleMap.addMarker(new MarkerOptions().position(polyList.get(0)).title("From").icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker_bitmap))).showInfoWindow();
					googleMap.addMarker(new MarkerOptions().position(polyList.get(polyList.size() - 1)).title("To").icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker_bitmap)));

					googleMap.addPolyline(new PolylineOptions().addAll(polyList)).setColor(Color.parseColor("#42BFEA"));
					googleMap.moveCamera(MapUtils.getCameraUpdate2(polyList,
							ScreenUtils.screenWidth(CreateRouteActivity.this),
							ScreenUtils.screenHeight(CreateRouteActivity.this)));
				}
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});

		addStopButton = findViewById(R.id.add_stop_btn);
		addStopButton.setOnClickListener(v -> {
			findViewById(R.id.from_to_container).setVisibility(View.GONE);
			findViewById(R.id.pickup_text).setVisibility(View.VISIBLE);
			findViewById(R.id.bottom_buttons_group).setVisibility(View.GONE);
			setButton.setVisibility(View.VISIBLE);
			markerChooser.setVisibility(View.VISIBLE);
		});

		markerChooser = findViewById(R.id.marker_chooser);
		setButton = findViewById(R.id.set_btn);

		List<LatLng> latLngList = PolyUtil.decode(directionsList.get(0));

		startLoc = latLngList.get(0);
		endLoc = latLngList.get(latLngList.size() - 1);

		MapsApi mapsApi = retrofit.create(MapsApi.class);

		setButton.setOnClickListener(v -> {
			LatLng latLng = googleMap.getCameraPosition().target;
			wayPointsList.add(latLng);
			mapsApi.getDirectionsWithWayPoints(startLoc.latitude + "," + startLoc.longitude, endLoc.latitude + "," + endLoc.longitude, getStringWayPoints(wayPointsList)).subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(directions -> {
						googleMap.clear();
						if (directions != null && directions.routes != null && !directions.routes.isEmpty()) {
							googleMap.addPolyline(new PolylineOptions().addAll(PolyUtil.decode(directions.routes.get(0).polyLine.points))).setColor(Color.parseColor("#42BFEA"));
							googleMap.addMarker(new MarkerOptions().position(latLngList.get(0)).title("From").icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker_bitmap))).showInfoWindow();
							googleMap.addMarker(new MarkerOptions().position(latLngList.get(latLngList.size() - 1)).title("To").icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker_bitmap)));
							googleMap.moveCamera(MapUtils.getCameraUpdate(latLngList));
							for (LatLng l : wayPointsList) {
								googleMap.addMarker(new MarkerOptions().position(l).draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker_bitmap)));
							}
						}
					});
			findViewById(R.id.set_btn).setVisibility(View.GONE);
			findViewById(R.id.bottom_buttons_group).setVisibility(View.VISIBLE);
			markerChooser.setVisibility(View.GONE);
		});

	}

	private String getStringWayPoints(List<LatLng> latLngList) {
		List<String> wayPointsString = new ArrayList<>();
		for (LatLng latLng : latLngList) {
			wayPointsString.add(String.valueOf(latLng.latitude) + "," + String.valueOf(latLng.longitude));
		}
		return TextUtils.join("|", wayPointsString);
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		this.googleMap = googleMap;
		List<LatLng> latLngList = PolyUtil.decode(directionsList.get(0));

		googleMap.addMarker(new MarkerOptions().position(latLngList.get(0)).title("From").icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker_bitmap))).showInfoWindow();
		googleMap.addMarker(new MarkerOptions().position(latLngList.get(latLngList.size() - 1)).title("To").icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker_bitmap)));
		googleMap.addPolyline(new PolylineOptions().addAll(latLngList)).setColor(Color.parseColor("#42BFEA"));
		googleMap.moveCamera(MapUtils.getCameraUpdate2(latLngList, ScreenUtils.screenWidth(CreateRouteActivity.this), ScreenUtils.screenHeight(CreateRouteActivity.this)));
	}


	@Override
	public void onMapClick(LatLng latLng) {

	}

	@Override
	public void onMapLongClick(LatLng latLng) {

	}
}
