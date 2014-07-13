package com.spython.PushUpCounter;

import android.content.Context;

import android.os.Environment;

import java.io.IOException;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

public class PushUpData {
	private String sdCardPath;
	private String csvFileDirectoryPath;
	private String csvFileName;
	private File csvFile;
	private Context context;
	
	public PushUpData(Context ctx) {
		context = ctx;
		
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
	
	private List<String> readLines() throws IOException {
		List<String> lines = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(
				new FileReader(csvFile));
			String line;
			
				while ((line = br.readLine()) != null) {
					lines.add(line);
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return lines;
	}
	
	public List getData() {
		List data = new ArrayList();
		List<String> fileData = new ArrayList<String>();
		try {
			fileData = readLines();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String str : fileData) {
			String splitted[] = str.split(",");
			int count = Integer.parseInt(splitted[0]);
			int year = Integer.parseInt(splitted[1]);
			int month = Integer.parseInt(splitted[2]);
			int day = Integer.parseInt(splitted[3]);
			int hour = Integer.parseInt(splitted[4]);
			int minute = Integer.parseInt(splitted[5]);
			int date[] = {year, month, day, hour, minute};
			long time = Long.parseLong(splitted[6]);
			
			List temp = new ArrayList();
			temp.add(count);
			temp.add(date);
			temp.add(time);
			data.add(temp);
		}
		
		return data;
	}
	
	public void writeData(int count, long time) throws IOException {
		FileOutputStream fos = new FileOutputStream(csvFile, true);
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		
		Calendar currentDateTime = Calendar.getInstance();
		int year = currentDateTime.get(Calendar.YEAR);
		int month = currentDateTime.get(Calendar.MONTH) + 1;
		int day = currentDateTime.get(Calendar.DAY_OF_MONTH);
		int hour = currentDateTime.get(Calendar.HOUR_OF_DAY);
		int minute = currentDateTime.get(Calendar.MINUTE);
		
		String date = year + "," + month + "," + day + "," + hour + "," + minute;
		
		String line = count + "," + date + "," + time + "\n";
		
		osw.write(line);
		osw.close();
	}
}
