package com.papax.ag.papax;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.transition.TransitionManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;

import api.UserApi;
import model.Car;
import model.HomeLocation;
import model.UserResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.NetworkUtils;

public class ModeChooserActivity extends Activity {

	private EditText userName;
	private TextView userAddres;
	private EditText carNumber;
	private EditText carColor;
	private EditText carModel;
	private EditText carSeatsCount;
	private TextView title;

	private String userEmal;
	private String userPhone;
	private String userCompany;
	private String userPassword;

	private String requiredMessage;
	private String somethingIsWrongMessage;

	private ConstraintLayout passengerTab;
	private ConstraintLayout driverTab;
	private ConstraintLayout aoutContainer;

	private Button startCarpool;

	private int currentPage;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mode_chooser_activity);

		driverTab = findViewById(R.id.driver_tab);
		passengerTab = findViewById(R.id.passenger_tab);
		aoutContainer = findViewById(R.id.auth_container);

		driverTab.setOnClickListener(tabSwitchListener);
		passengerTab.setOnClickListener(tabSwitchListener);
		driverTab.setSelected(true);


		requiredMessage = getResources().getString(R.string.required_text);
		somethingIsWrongMessage = getResources().getString(R.string.something_is_wrong);

		Intent intent = getIntent();
		userEmal = intent.getStringExtra(SignUpActivity.USER_MAIL_KEY);
		userPhone = intent.getStringExtra(SignUpActivity.USER_PHONE_KEY);
		userCompany = intent.getStringExtra(SignUpActivity.USER_COMPANY_KEY);
		userPassword = intent.getStringExtra(SignUpActivity.USER_PASSWORD_KEY);

		userName = findViewById(R.id.user_name);
		userAddres = findViewById(R.id.add_location);
		carNumber = findViewById(R.id.car_number);
		carColor = findViewById(R.id.car_color);
		carModel = findViewById(R.id.car_model);
		carSeatsCount = findViewById(R.id.car_seats_count);

		userName.addTextChangedListener(textWatcher);
		userAddres.addTextChangedListener(textWatcher);
		carNumber.addTextChangedListener(textWatcher);
		carColor.addTextChangedListener(textWatcher);
		carSeatsCount.addTextChangedListener(textWatcher);

		title = findViewById(R.id.textView);

		SimpleDraweeView userPhoto = findViewById(R.id.user_photo);
		userPhoto.setImageURI("https://images.pexels.com/photos/67636/rose-blue-flower-rose-blooms-67636.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(NetworkUtils.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		UserApi userApi = retrofit.create(UserApi.class);
		HomeLocation homeLocation = new HomeLocation("111111", "222222");

		startCarpool = findViewById(R.id.start_carpool_btn);
		startCarpool.setEnabled(false);
		startCarpool.setOnClickListener(v -> {
			if (currentPage == 0) {
				Car car = new Car(carModel.getText().toString(),
						carColor.getText().toString(), carNumber.getText().toString(),
						Integer.valueOf(carSeatsCount.getText().toString()));
				Call<UserResult> messages = userApi.driverSignUp("blank", userPhone, userEmal, userPassword, homeLocation, car);
				messages.enqueue(new Callback<UserResult>() {
					@Override
					public void onResponse(Call<UserResult> call, Response<UserResult> response) {
						if (response.body() != null) {
							if (response.body().isSuccessful()) {
								openMainTabActivity();
							} else {
								Toast.makeText(ModeChooserActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
							}
						}
					}

					@Override
					public void onFailure(Call<UserResult> call, Throwable t) {
						Toast.makeText(ModeChooserActivity.this, somethingIsWrongMessage, Toast.LENGTH_SHORT).show();
					}
				});
			} else {
				Call<UserResult> messages = userApi.passengerSignUp("blank", userName.getText().toString(), userEmal, userPassword, userPhone, homeLocation);
				messages.enqueue(new Callback<UserResult>() {
					@Override
					public void onResponse(Call<UserResult> call, Response<UserResult> response) {
						if (response.body() != null) {
							if (response.body().isSuccessful()) {
								openMainTabActivity();
							} else {
								Toast.makeText(ModeChooserActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
							}
						}
					}

					@Override
					public void onFailure(Call<UserResult> call, Throwable t) {
						Toast.makeText(ModeChooserActivity.this, somethingIsWrongMessage, Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}

	private boolean isDriverFieldsEmpty() {
		return TextUtils.isEmpty(userName.getText()) || TextUtils.isEmpty(userAddres.getText()) ||
				TextUtils.isEmpty(carNumber.getText()) || TextUtils.isEmpty(carColor.getText()) ||
				TextUtils.isEmpty(carModel.getText()) || TextUtils.isEmpty(carSeatsCount.getText());
	}

	private boolean isPessangerFieldsEmpty() {
		return TextUtils.isEmpty(userName.getText()) || TextUtils.isEmpty(userAddres.getText());
	}

	private void openMainTabActivity() {
		Intent intent = new Intent(this, MainTabActivity.class);
		startActivity(intent);
	}

	private void switchTab(int id) {
		userName.getText().clear();
		userAddres.setText(getResources().getString(R.string.add_regular_address));
		if (id == R.id.driver_tab) {
			title.setVisibility(View.VISIBLE);
			carSeatsCount.setVisibility(View.VISIBLE);
			carModel.setVisibility(View.VISIBLE);
			carNumber.setVisibility(View.VISIBLE);
			carColor.setVisibility(View.VISIBLE);
			driverTab.setSelected(true);
			passengerTab.setSelected(false);
			currentPage = 0;
		} else {
			title.setVisibility(View.GONE);
			carSeatsCount.setVisibility(View.GONE);
			carModel.setVisibility(View.GONE);
			carNumber.setVisibility(View.GONE);
			carColor.setVisibility(View.GONE);
			driverTab.setSelected(false);
			passengerTab.setSelected(true);
			currentPage = 1;
		}
		TransitionManager.beginDelayedTransition(aoutContainer);
	}

	View.OnClickListener tabSwitchListener = v -> {
		switchTab(v.getId());
	};

	private TextWatcher textWatcher = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (currentPage == 0) {
				startCarpool.setEnabled(!isDriverFieldsEmpty());
			} else {
				startCarpool.setEnabled(!isPessangerFieldsEmpty());
			}
		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};
}
