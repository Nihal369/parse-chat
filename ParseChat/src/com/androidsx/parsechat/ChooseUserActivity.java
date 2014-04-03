package com.androidsx.parsechat;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidsx.hellowordparse.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ChooseUserActivity extends Activity {

	private Button btnLogin;
	private Spinner spinnerUsers;
	private boolean checkLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_user);
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

				boolean loginResult = checkLogin(name);

				if (loginResult) {
					showToast("Welcome user!");
					openParseChatActivity(v, name);
				} else {
					showToast("Invalid username");
				}

			}
		});

	}


	public void openParseChatActivity(View view, String name) {
		Intent i = new Intent(this, ParseChatActivity.class);
		i.putExtra("user", name);
		startActivity(i);
	}
	
	public void getParseUsers(){	
		ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> usersList, ParseException e) {
				if (usersList != null) {
					ArrayList<String> nameUsers = new ArrayList<String>();
					for	(int i = 0; i < usersList.size(); i++){
						nameUsers.add(usersList.get(i).getString("username"));					
					}	
					mountSpinnerUsers(nameUsers);
					showToast(nameUsers.get(0));
				} 
			}
		});		
	}
	
	private void mountSpinnerUsers(ArrayList<String> names){
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names);
		spinnerUsers.setAdapter(adapter);
	}

	public boolean checkLogin(String name) {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
		query.whereEqualTo("username", name);
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> scoreList, ParseException e) {
				if (scoreList != null) {
					checkLogin = true;
				} else {
					checkLogin = false;
				}
			}
		});

		return checkLogin;
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
