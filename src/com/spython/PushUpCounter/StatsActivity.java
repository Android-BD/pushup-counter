package com.spython.PushUpCounter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.ArrayList;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphViewSeries;

public class StatsActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stats);
		PushUpData pushUpData = new PushUpData(getApplicationContext());
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
		TextView total = (TextView) findViewById(R.id.total);
		TextView average = (TextView) findViewById(R.id.average);
		TextView speed = (TextView) findViewById(R.id.speed);
		
		List data = new ArrayList();
		data = pushUpData.getData();
		GraphViewData[] gData = new GraphViewData[data.size()];
		
		int totalCount = 0;
		long averageSpeed = 0;
		int i;
		
		for (i = 0; i < data.size(); i++) {
			List temp = (ArrayList) data.get(i);
			int count = (Integer) temp.get(0);
			totalCount += count;
			averageSpeed += (Long) temp.get(2) / count;
			int[] tmp = (int[]) temp.get(1);
			gData[i] = new GraphViewData(tmp[2], (double) count);
		}
		
		GraphViewSeries series = new GraphViewSeries(gData);
		GraphView graphView = new BarGraphView(this, "Push ups");
		graphView.addSeries(series);
		
		if (i == 0) {
			i = 1;
		}
		
		int averageCount = totalCount / i;
		averageSpeed /= i;
		
		float averageSpeedSec = averageSpeed / 1000f;
		String measure = averageSpeedSec >= 2f ? " seconds" : " second";
		
		total.setText("Total push ups made: " +
			String.valueOf(totalCount));
		average.setText("Average push ups per approach: " +
			String.valueOf(averageCount));
		speed.setText("Average time needed to make a push up: " +
			String.valueOf(averageSpeedSec) + measure);
		
		layout.addView(graphView);
	}
}
