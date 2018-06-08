package com.papax.ag.papax;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class SignUpActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_acitivity_layout);
		Button signUp = findViewById(R.id.sign_up_btn);
		signUp.setOnClickListener(v -> {
			Intent intent = new Intent(this, ModeChooserActivity.class);
			startActivity(intent);
		});
	}
}
