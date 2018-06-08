package com.papax.ag.papax;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

	private Button signUpBtn;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activty_layout);
		Button signUpBtn = findViewById(R.id.sign_up_btn);
		signUpBtn.setOnClickListener(v -> {
			Intent intent = new Intent(this, SignUpActivity.class);
			startActivity(intent);
		});
		Button loginUpBtn = findViewById(R.id.login_btn);
		loginUpBtn.setOnClickListener(v -> {
			Intent intent = new Intent(this, MainTabActivity.class);
			startActivity(intent);
		});

	}

}
