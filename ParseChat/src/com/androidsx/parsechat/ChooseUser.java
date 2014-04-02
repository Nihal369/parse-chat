package com.androidsx.parsechat;

import com.androidsx.hellowordparse.R;
import com.androidsx.hellowordparse.R.layout;
import com.androidsx.hellowordparse.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ChooseUser extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_user);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_user, menu);
		return true;
	}

}
