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

public class ChooseUser extends Activity {

	private Button login;
	private EditText user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_user);
		
		user = (EditText)findViewById(R.id.loginUsername);
		login = (Button) findViewById(R.id.buttonLogin);
		
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				openParseChatActivity(v);
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
		i.putExtra("user", user.getText().toString());
		startActivity(i);
	}

}
