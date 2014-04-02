package com.androidsx.parsechat;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.androidsx.hellowordparse.R;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseChatActivity extends Activity {

	private EditText txtMessage;
	private Button btnSend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parse_hello_world);

		Parse.initialize(this, "mMjR5lvou6mzMhjymYbEh39RCsqGQkvNLQqDQ47u",
				"d8rT5X0HVKSS297euA4koJgsAdJaG1HEIlYnvgPM");
		
		setupUI();
	}

	public void setupUI() {
		txtMessage = (EditText) findViewById(R.id.etMensaje);
		btnSend = (Button) findViewById(R.id.btnSend);
		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String data = txtMessage.getText().toString();
				ParseObject message = new ParseObject("Messages");
				message.put("userName", "Lucas");
				message.put("message", data);
				message.saveInBackground();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.parse_hello_world, menu);
		return true;
	}

}
