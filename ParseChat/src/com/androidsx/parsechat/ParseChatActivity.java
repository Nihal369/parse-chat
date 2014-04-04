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
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.PushService;

public class ParseChatActivity extends Activity {

	private EditText txtMessage;
	private TextView txtChat;
	private Button btnSend;
	private Button btnRefreshMsg;
	private static String username;
	private String chatData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parse_hello_world);
		setupUI();

		PushService.subscribe(this, "Prueba", ParseChatActivity.class);
		PushService.setDefaultPushCallback(this, ParseChatActivity.class);
		receiveMessage();

		Intent intent = getIntent();
		username = intent.getStringExtra("user");
	}

	public void setupUI() {
		txtMessage = (EditText) findViewById(R.id.etMensaje);
		txtChat = (TextView) findViewById(R.id.chatParse);
		btnSend = (Button) findViewById(R.id.btnSend);
		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String data = txtMessage.getText().toString();
				ParseObject message = new ParseObject("Messages");
				message.put("userName", username);
				message.put("message", data);
				message.saveInBackground();

				createPushNotifications(data);
				txtMessage.setText("");
			}
		});

		btnRefreshMsg = (Button) findViewById(R.id.btnRefreshMsg);
		btnRefreshMsg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				receiveMessage();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.parse_hello_world, menu);
		return true;
	}

	public void createPushNotifications(String message) {
		ParsePush push = new ParsePush();
		push.setChannel("Prueba");
		push.setMessage(message);
		push.sendInBackground();
	}

	private void receiveMessage() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Messages");
		query.setLimit(6);
		query.orderByAscending("createAt");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> messages, ParseException e) {
				if (e == null) {
					for (int i = 0; i < messages.size(); i++) {
						chatData += (messages.get(i).getString("userName")
								+ ": " + messages.get(i).getString("message") + "\n");
					}
					txtChat.setText(chatData);
					chatData = "";
				} else {
					Log.d("message", "Error: " + e.getMessage());
				}
			}
		});
	}

}
