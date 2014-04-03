package com.androidsx.parsechat;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidsx.hellowordparse.R;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ChooseUserActivity extends Activity {

	private Button btnLogin;
	private Spinner spinnerUsers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_user);

		Parse.initialize(this, "mMjR5lvou6mzMhjymYbEh39RCsqGQkvNLQqDQ47u",
				"d8rT5X0HVKSS297euA4koJgsAdJaG1HEIlYnvgPM");

		setupUI();
		getParseUsers();
	}

	private void setupUI() {
		spinnerUsers = (Spinner) findViewById(R.id.spinnerUsers);
		btnLogin = (Button) findViewById(R.id.buttonLogin);
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = spinnerUsers.getSelectedItem().toString();
				openParseChatActivity(v, name);
			}
		});
	}

	public void getParseUsers() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> usersList, ParseException e) {
				ParseQuery<ParseUser> query = ParseUser.getQuery();
				query.findInBackground(new FindCallback<ParseUser>() {
					
					public void done(List<ParseUser> usersList, ParseException e) {
						if (e == null) {
							ArrayList<String> nameUsers = new ArrayList<String>();
							for (int i = 0; i < usersList.size(); i++) {
								nameUsers.add(usersList.get(i).getString(
										"username"));
							}
							mountSpinnerUsers(nameUsers);
						} else {
							showToast("No users to load");
						}
					}
				});

			}
		});
	}


	private void mountSpinnerUsers(ArrayList<String> names) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, names);
		spinnerUsers.setAdapter(adapter);
	}

	public void openParseChatActivity(View view, String name) {
		Intent i = new Intent(this, ParseChatActivity.class);
		i.putExtra("user", name);
		startActivity(i);
	}

	public void showToast(String text) {
		Toast toast = Toast.makeText(this, text, 3000);
		toast.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_user, menu);
		return true;
	}

}
