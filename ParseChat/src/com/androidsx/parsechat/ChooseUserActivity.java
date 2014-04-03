package com.androidsx.parsechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.androidsx.hellowordparse.R;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ChooseUserActivity extends Activity {

	private Button btnLogin;
	private EditText userName;
	private EditText userPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_user);
		setupUI();
	}
	
	private void setupUI(){
		userName = (EditText)findViewById(R.id.loginUsername);
		userPassword = (EditText)findViewById(R.id.loginpass);
		btnLogin = (Button) findViewById(R.id.buttonLogin);	
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean loginResult = checkLogin();
				
				if (loginResult){
					openParseChatActivity(v);
				}else{
					
				}

			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_user, menu);
		return true;
	}
	
	public void openParseChatActivity(View view){
		Intent i = new Intent(this , ParseChatActivity.class);
		
		i.putExtra("user", userName.getText().toString());
		startActivity(i);
	}
	
	public boolean checkLogin(){
		
		String name = userName.getText().toString(); 
		String password = userPassword.getText().toString();
		
		// Create a query - name and pass check
		ParseQuery<ParseObject> query = ParseQuery.getQuery("GameScore");
		query.getInBackground(name, null);
	
		return false;
	}

}
