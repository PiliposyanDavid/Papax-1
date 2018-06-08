package com.papax.ag.papax;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import api.UserApi;
import model.UserResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.NetworkUtils;

public class LoginActivity extends AppCompatActivity {

	private String somethingIsWrongMessage;
	private String requiredMessage;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activty_layout);

		somethingIsWrongMessage = getResources().getString(R.string.something_is_wrong);
		requiredMessage = getResources().getString(R.string.required_text);

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(NetworkUtils.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		UserApi userApi = retrofit.create(UserApi.class);

		Button signUpBtn = findViewById(R.id.sign_up_btn);
		EditText phoneNumber = findViewById(R.id.user_phone);
		EditText passWord = findViewById(R.id.user_password);

		signUpBtn.setOnClickListener(v -> {
			Intent intent = new Intent(this, SignUpActivity.class);
			startActivity(intent);
		});
		Button loginUpBtn = findViewById(R.id.login_btn);
		loginUpBtn.setOnClickListener(v -> {
			if (TextUtils.isEmpty(phoneNumber.getText()) || TextUtils.isEmpty(passWord.getText())) {
				Toast.makeText(this, requiredMessage, Toast.LENGTH_SHORT).show();
				return;
			}
			Call<UserResult> messages = userApi.login(phoneNumber.getText().toString(), passWord.getText().toString());
			messages.enqueue(new Callback<UserResult>() {
				@Override
				public void onResponse(Call<UserResult> call, Response<UserResult> response) {
					if (response.body() != null) {
						if (response.body().isSuccessful()) {
							openMainTabActivity();
						} else {
							Toast.makeText(LoginActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
						}
					}
				}

				@Override
				public void onFailure(Call<UserResult> call, Throwable t) {
					Toast.makeText(LoginActivity.this, somethingIsWrongMessage, Toast.LENGTH_SHORT).show();
				}
			});
		});
	}

	private void openMainTabActivity() {
		Intent intent = new Intent(this, MainTabActivity.class);
		startActivity(intent);
	}

}
