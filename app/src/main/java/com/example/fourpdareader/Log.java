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

	/**
	 * Make a string to be used as the tag for android.util.Log.d(tag,message)
	 * @return a tag
	 */
	static private String tag() {
		return "4pda[" + Thread.currentThread().getName()+"]"+getTimestamp();
	}

	/**
	 * @return current time as a string
	 */
	private static String getTimestamp() {
		long time = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
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
	/**
	 * Get the name of method specified by depth in the call stack.
	 * @param depth depth in the call stack (0 means current method, 1 means calling method, etc.)
	 * @return class name . method name ()
	 */
	public static String getMethodInfo(final int depth)
	{
		final StackTraceElement[] ste = new Throwable().getStackTrace();
		return ste[depth].getClassName()+"."+ste[depth].getMethodName()+"()";
	}

	/**
	 * Print the class name and the method name of the caller to the log.
	 */
	public static void f() {
		d("### "+ getMethodInfo(2));
	}
	/**
	 * Print the class and the method names of the caller and the caller's caller to the log.
	 */
    public static void ff() {
        d("### "+ getMethodInfo(2));
        d("### <"+ getMethodInfo(3));
    }
	/**
	 * Print the class and method names of the last 3 functions on the call stack to the log.
	 */
    public static void fff() {
        d("### "+ getMethodInfo(2));
        d("### <"+ getMethodInfo(3));
        d("### <<"+ getMethodInfo(4));
    }
}
