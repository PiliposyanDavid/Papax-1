package utils;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import model.User;

public class UserUtil {
	static UserUtil userUtil = new UserUtil();
	private User user;
	private boolean isRegister;

	private UserUtil(){

	}


	public static UserUtil getInstance() {
		return userUtil ;
	}

	public void writeToFile(String data,Context context) {
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
			outputStreamWriter.write(data);
			isRegister = true;
			outputStreamWriter.close();
		}
		catch (IOException e) {
		}
	}

	public User getUser(Context context) {

		String ret = "";

		try {
			InputStream inputStream = context.openFileInput("config.txt");

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String receiveString = "";
				StringBuilder stringBuilder = new StringBuilder();

				while ((receiveString = bufferedReader.readLine()) != null) {
					stringBuilder.append(receiveString);
				}

				inputStream.close();
				ret = stringBuilder.toString();
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		return new Gson().fromJson(ret, User.class);
	}

	public boolean isRegister() {
		return isRegister;
	}
}
