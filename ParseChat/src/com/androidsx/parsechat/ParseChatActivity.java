package com.androidsx.parsechat;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.PushService;

public class ParseChatActivity extends Activity {
	public static final String USER_NAME_KEY = "userName";

	private static final String TAG = ParseChatActivity.class.getName();
	private static final int MAX_CHAT_MESSAGES_TO_SHOW = 4;

	private static String username;

	private EditText txtMessage;
	private Button btnSend;
	private ListView chatListView;
	private ArrayAdapter<String> adapter;

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
		btnSend = (Button) findViewById(R.id.btnSend);
		chatListView = (ListView) findViewById(R.id.chatList);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		chatListView.setAdapter(adapter);
		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String data = txtMessage.getText().toString();
				ParseObject message = new ParseObject("Messages");
				message.put(USER_NAME_KEY, username);
				message.put("message", data);
				message.saveInBackground();
				createPushNotifications(data);
				txtMessage.setText("");
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
		JSONObject object = new JSONObject();
		try {
			object.put("alert", message);
			object.put("title", "Chat");
			object.put("action", "MyAction");
			ParsePush pushNotification = new ParsePush();
			pushNotification.setData(object);
			pushNotification.setChannel("Prueba");
			pushNotification.sendInBackground();
		} catch (JSONException e) {
			Log.e(TAG, "Could not parse the push notification", e);
		}
	}

	private void receiveMessage() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Messages");
		query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);
		query.orderByDescending("createdAt");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> messages, ParseException e) {
				if (e == null) {
					adapter.clear();
					
					StringBuilder builder = new StringBuilder();
					for (int i = messages.size() - 1; i >= 0; i--) {
						builder.append(messages.get(i).getString(USER_NAME_KEY)
								+ ": " + messages.get(i).getString("message") + "\n");
					}
					addItemstoListView(builder.toString());
				} else {
					Log.d("message", "Error: " + e.getMessage());
				}
			}
		});
	}
	
	public void addItemstoListView(String message) {
		adapter.add(message);
        adapter.notifyDataSetChanged();
        chatListView.invalidate();
    }


}
