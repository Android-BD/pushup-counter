package com.spython.PushUpCounter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.content.Intent;
import android.content.Context;
import android.content.res.AssetManager;
import android.widget.TextView;
import android.view.View;

import java.io.IOException;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

class PushUpData {
	private String sdCardPath;
	private String csvFileDirectoryPath;
	private String csvFileName;
	private File csvFile;
	
	public PushUpData() {
		sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		csvFileDirectoryPath = sdCardPath + "/.PushUpCounter";
		File csvFileDirectory = new File(csvFileDirectoryPath);
		csvFileName = "data.csv";
		
		if (!csvFileDirectory.exists()) {
			csvFileDirectory.mkdir();
		}
		
		csvFile = new File(csvFileDirectoryPath, csvFileName);
		
		if (!csvFile.exists()) {
			try {
				csvFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<String> readLines(Context context, String filename) throws IOException {
		List<String> lines = new ArrayList<String>();
		AssetManager assets = context.getAssets();
		BufferedReader reader = new BufferedReader(
			new InputStreamReader(assets.open(filename)));
		while(true) {
			String line = reader.readLine();
			if(line == null) {
				break;
			}
			lines.add(line);
		}
		return lines;
	}
	
	public void writeData(String count, String date) throws IOException {
		FileOutputStream fos = new FileOutputStream(csvFile, true);
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		
		String line = count + "," + date + "\n";
		
		osw.write(line);
		osw.close();
	}
}

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
