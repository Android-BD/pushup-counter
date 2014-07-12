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

public class CounterActivity extends Activity implements SensorEventListener {
	private SensorManager sensorManager;
	private TextView tvCounter;
	private boolean timePassed = true;
	
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
		if (timePassed) {
			int val = (int) event.values[0];
			
			if (val > 1)
				val = 1;
			
			tvCounter.setText(
				String.valueOf(
					Integer.parseInt(tvCounter.getText().toString())+
					val
				)
			);
		}
		timePassed = false;
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timePassed = true;
	}
	
	public void onClick(View v) {
		tvCounter.setText(
			String.valueOf(
				Integer.parseInt(tvCounter.getText().toString())+1
			)
		);
	}
	
	public void finishCounting(View v) {
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
}
