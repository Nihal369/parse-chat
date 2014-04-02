package com.androidsx.parsechat;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.androidsx.hellowordparse.R;
import com.parse.Parse;

public class ParseChatActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parse_hello_world);

		Parse.initialize(this, "mMjR5lvou6mzMhjymYbEh39RCsqGQkvNLQqDQ47u",
				"d8rT5X0HVKSS297euA4koJgsAdJaG1HEIlYnvgPM");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.parse_hello_world, menu);
		return true;
	}

}
