package com.spython.PushUpCounter;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.content.DialogInterface;
import android.widget.NumberPicker;

import java.util.List;
import java.util.ArrayList;

import java.io.IOException;

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
	
	public void addRecord(View v) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
		alert.setTitle("Add record manually");
		alert.setMessage("Enter how many push ups You made");
		
		final NumberPicker count = new NumberPicker(this);
		count.setMinValue(0);
		count.setMaxValue(250);
		alert.setView(count);
		
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				PushUpData pData = new PushUpData(getApplicationContext());
				
				int countValue = count.getValue();
				List data = (ArrayList) pData.getData();
				
				long time = 0;
				int i;
				
				for (i = 0; i < data.size(); i++) {
					List temp = (ArrayList) data.get(i);
					time += (Long) temp.get(2);
				}
				
				time /= i;
				
				try {
					pData.writeData(countValue, time);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
				
			}
		});
		
		alert.show();
	}
}
