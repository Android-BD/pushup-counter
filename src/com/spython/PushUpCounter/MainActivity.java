package com.spython.PushUpCounter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class MainActivity extends Activity {
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	
	public void onClick(View v) {
		Intent i = new Intent(getApplicationContext(), CounterActivity.class);
		i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
	
	public void showStats(View v) {
		Intent i = new Intent(getApplicationContext(), StatsActivity.class);
		startActivity(i);
	}
}
