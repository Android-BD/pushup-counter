package com.spython.PushUpCounter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;
import java.util.ArrayList;

public class StatsActivity extends Activity {
	private PushUpData pushUpData;
	private TextView total;
	private TextView average;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stats);
		pushUpData = new PushUpData(getApplicationContext());
		
		total = (TextView) findViewById(R.id.total);
		average = (TextView) findViewById(R.id.average);
		
		List data = new ArrayList();
		data = pushUpData.getData();
		
		int totalCount = 0;
		int i;
		
		for (i = 0; i < data.size(); i++) {
			List temp = (ArrayList) data.get(i);
			totalCount += (Integer) temp.get(0);
		}
		
		if (i == 0) {
			i = 1;
		}
		
		int averageCount = totalCount / i;
		
		total.setText("Total push ups made: " +
			String.valueOf(totalCount));
		average.setText("Average push ups per approach: " +
			String.valueOf(averageCount));
	}
}
