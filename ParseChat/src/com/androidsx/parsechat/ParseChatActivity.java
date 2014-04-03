package com.androidsx.parsechat;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.PushService;

public class ParseChatActivity extends Activity {

	private EditText txtMessage;
	private TextView chatTxt;
	private Button btnSend;
	private static String username;
	private String prueba;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parse_hello_world);
		setupUI();
		PushService.setDefaultPushCallback(this, ParseChatActivity.class);
		// ParseAnalytics.trackAppOpened(getIntent());
		// Parse.initialize(this, Constants.APP_ID,Constants.CLIENT_KEY);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		PushService.subscribe(this, "Prueba", ParseChatActivity.class);
		chatTxt.setText("");
		Intent intent = getIntent();
		username = intent.getStringExtra("user");
	}

	public void setupUI() {
		txtMessage = (EditText) findViewById(R.id.etMensaje);
		chatTxt = (TextView) findViewById(R.id.chatParse);
		btnSend = (Button) findViewById(R.id.btnSend);
		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String data = txtMessage.getText().toString();
				ParseObject message = new ParseObject("Messages");
				message.put("userName", username);
				message.put("message", data);
				message.saveInBackground();
				receiveMessage();
				//chatTxt.setText(username + ": " + data + "\n");
				// createPushNotifications();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.parse_hello_world, menu);
		return true;
	}

	// public void createPushNotifications (){
	// ParsePush push = new ParsePush();
	// push.setChannel("Prueba");
	// push.setMessage(ParseChatActivity.message);
	// push.sendInBackground();
	// }

	private void receiveMessage() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Messages");
		query.whereNotEqualTo("userName", "pepito");
		query.setLimit(3);
		query.orderByDescending("message");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> messages, ParseException e) {
				if (e == null) {
					for (int i = 0; i < messages.size(); i++) {
						prueba += (messages.get(i).getString("userName")+": "+messages.get(i).getString("message")+"\n");
					}
					chatTxt.setText(prueba);
					prueba="";
				} else {
					Log.d("message", "Error: " + e.getMessage());
				}
			}
		});
	}

}
