package com.papax.ag.papax;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {


	public static final String USER_MAIL_KEY = "user_mail_key";
	public static final String USER_PHONE_KEY = "user_phone_key";
	public static final String USER_COMPANY_KEY = "user_company_key";
	public static final String USER_PASSWORD_KEY = "user_password_key";

	private EditText email;
	private EditText phone;
	private EditText company;
	private EditText passWord;

	private String requiredMessage;
	private String noPicsartEmployee;
	private Button signUp;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_acitivity_layout);

		requiredMessage = getResources().getString(R.string.required_text);
		noPicsartEmployee = getResources().getString(R.string.no_picsart_employee);

		email = findViewById(R.id.user_email);
		phone = findViewById(R.id.user_phone);
		company = findViewById(R.id.user_company);
		passWord = findViewById(R.id.user_password);

		signUp = findViewById(R.id.sign_up_btn);
		signUp.setOnClickListener(v -> {
			if (isFieldsEmpty()) {
				Toast.makeText(this, requiredMessage, Toast.LENGTH_SHORT).show();
				return;
			} else if (!isEmailValid()) {
				Toast.makeText(this, noPicsartEmployee, Toast.LENGTH_SHORT).show();
				return;
			}
			Intent intent = new Intent(getBaseContext(), ModeChooserActivity.class);
			intent.putExtra(USER_MAIL_KEY, email.getText().toString());
			intent.putExtra(USER_PHONE_KEY, phone.getText().toString());
			intent.putExtra(USER_COMPANY_KEY, company.getText().toString());
			intent.putExtra(USER_PASSWORD_KEY, passWord.getText().toString());
			startActivity(intent);
		});
	}

	private boolean isFieldsEmpty() {
		return TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(phone.getText()) || TextUtils.isEmpty(passWord.getText());
	}

	private boolean isEmailValid() {
		return email.getText().toString().contains("@picsart.com");
	}
}
