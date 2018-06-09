package fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.papax.ag.papax.Constants;
import com.papax.ag.papax.CreateRouteActivity;
import com.papax.ag.papax.R;
import com.papax.ag.papax.view.LocationSelectView;

import java.util.ArrayList;
import java.util.List;

import adapter.TabCardViewAdapter;
import api.MapsApi;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import model.MainTabResponse;
import model.Directions;
import model.Route;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.LocationUtils;

import static android.content.Context.LOCATION_SERVICE;
import static utils.MapUtils.getCameraUpdate;

@SuppressLint("CheckResult")
public class WorkTabFragment extends Fragment implements TabFragmentDataInterface {

	private RecyclerView recyclerView;
	private ProgressBar progressBar;
	private RecyclerViewItemDecoration itemDecoration = new RecyclerViewItemDecoration();
	private int tabPosition;


	@SuppressLint("MissingPermission")
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.work_tab_fragment_layout, container, false);
	}


	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		recyclerView = view.findViewById(R.id.tab_recycler_view);
		recyclerView.removeItemDecoration(itemDecoration);
		recyclerView.addItemDecoration(itemDecoration);
		progressBar = view.findViewById(R.id.progress);
		progressBar.setVisibility(View.VISIBLE);
//		noDataDriverRideRootView.setVisibility(View.GONE);
		recyclerView.setVisibility(View.GONE);
//		selectStarLocationView = view.findViewById(R.id.select_location);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setAdapter(new TabCardViewAdapter());
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
				recyclerView.setVisibility(View.VISIBLE);
//				selectStarLocationView.setVisibility(View.VISIBLE);
//				noDataDriverRideRootView.setVisibility(View.GONE);
				progressBar.setVisibility(View.GONE);

				/*if (mainTabResponse != null && mainTabResponse.getToWorkData() != null) {
					recyclerView.setVisibility(View.VISIBLE);
					noDataDriverRideRootView.setVisibility(View.GONE);
					selectStarLocationView.setVisibility(View.VISIBLE);
					progressBar.setVisibility(View.GONE);
					recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
					recyclerView.setAdapter(new TabCardViewAdapter());
				} else {
					LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
					LocationUtils.getCurrentLocation(locationManager, mLocationListener);
				}*/
			});
		}

	}

	@Override
	public void setUserTypeIsPassanger(boolean isPassanger) {

	}

	public void setTabPosition(int pos) {
		this.tabPosition = pos;
	}
}