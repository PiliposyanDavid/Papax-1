package com.papax.ag.papax;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import utils.NetworkUtils;
import utils.UserUtil;

public class SplashScreenActivity extends Activity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen_activity_layout);
		final Intent intent;
		if (UserUtil.getInstance().getUser(this) != null) {
			intent = new Intent(this, MainTabActivity.class);
		} else {
			intent = new Intent(this, LoginActivity.class);
		}
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(intent);
				finish();
			}
		},1000);
	}
}
