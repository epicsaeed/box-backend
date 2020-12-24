package com.boxcinemas;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.jmx.snmp.Timestamp;

//Logger object that logs events to log.txt 
//Any event that interacts with a database or a servlet is logged
public class Logger {
	private String path = "/Users/saeedalnuaimi/eclipse-workspace/BOXCinemas/WebContent/log.txt";
	public void log(String ToWrite) {
		Date date = new Date(); // This object contains the current date value
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String timeStamp = formatter.format(date);
		String logged = timeStamp + ": " + ToWrite + "\r\n";
		try {
			FileWriter writer = new FileWriter(path, true);
			writer.write(logged);
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
