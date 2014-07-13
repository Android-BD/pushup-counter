package com.spython.PushUpCounter;

import android.app.Activity;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.content.Intent;
import android.content.Context;
import android.widget.TextView;
import android.view.View;

import java.io.IOException;

import java.util.Calendar;

public class CounterActivity extends Activity implements SensorEventListener {
	private SensorManager sensorManager;
	private TextView tvCounter;
	private int count = 0;
	private boolean justStarted = true;
	private Calendar dateStarted;
	private long timeElapsed = 0;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.counter);
		
		tvCounter = (TextView) findViewById(R.id.tvcounter);
		
		/** Get system service to interact with sensors */
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		/** Find default proximity sensor */
		Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		sensorManager.registerListener(this,
			sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
			SensorManager.SENSOR_DELAY_UI
		);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		sensorManager.unregisterListener(this);
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (justStarted) {
			justStarted = false;
			dateStarted = Calendar.getInstance();
			return;
		}
		
		int val = (int) event.values[0];
		
		if (val > 1)
			val = 1;
		
		count += val;
		
		tvCounter.setText(
			String.valueOf(
				count));
	}
	
	public void onClick(View v) {
		count += 1;
		
		tvCounter.setText(
			String.valueOf(
				count
			)
		);
	}
	
	public void finishCounting(View v) {
		PushUpData data = new PushUpData(getApplicationContext());
		
		Calendar currentDate = Calendar.getInstance();
		
		timeElapsed = currentDate.getTimeInMillis() -
			dateStarted.getTimeInMillis();
		
		try {
			data.writeData(count, timeElapsed);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
}
