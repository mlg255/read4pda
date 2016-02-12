package com.example.fourpdareader;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is used through the application because:
 * 1) it outputs additional information like time and thread name;
 * 2) all logging in the application may be controlled via this class;
 * 3) in particular, all logging may be disabled by setting ENABLED to false.
 */
public class Log {
	private final static boolean ENABLED = true;

	/**
	 * Print a String to the log.
	 * The tag will include "BipTest", thread name, and timestamp.
	 * @param s
	 */
	public static void d(String s) {
		if(ENABLED) {
			android.util.Log.d(tag(), s);
		}
	}
	static private String tag() {
		return "4pda[" + Thread.currentThread().getName()+"]"+getTimestamp();
	}
	private static String getTimestamp() {
		long time = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss.SSS");
		String date = sdf.format(new Date(time)).toString();
		return date;
	}

	/**
	 * Print a Throwable to the log.
	 * The tag will include "BipTest", thread name, and timestamp.
	 * @param t
	 */
	public static void d(Throwable t) {
		if (ENABLED) {
			d("---...--- " + t + " ---...---");
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			PrintWriter pw = new PrintWriter(bos);
			t.printStackTrace(pw);
			pw.close();
			d(bos.toString());
			d("---'''--- " + t + " ---'''---");
		}
	}
}
