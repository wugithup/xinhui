package com.shuto.mam.expand.logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class PrintLogs {
	public int level = 5;

	public void setLevel(int level) {
		this.level = level;
	}

	public static void init() {
	}

	public void refreshLogger() {
	}

	public void loadLog4j() {
	}

	public void setClass(Class cls) {
	}

	public void debug(Object message, int level) {
		System.out.println(message);
	}

	public void setDebugTime(boolean debugTime) {
	}

	public void debugTime(Object message) {
	}

	public void debug(Object message) {
		System.out.println(message);
	}

	public void error(Object message) {
		System.out.println(message);
	}

	public void println() {
		System.out.println();
	}

	public void println(Object message) {
		System.out.println(message);
	}

	public static String getTrace(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		return buffer.toString();
	}

	public void error(Object message, Exception e) {
		System.out.println(message + ":" + getTrace(e));
	}

	public String toukeiJikann(long timeOne, long timeTwo) {
		long daysapart = (timeTwo - timeOne) / 60000L;
		long daysaparts = (timeTwo - timeOne) / 1000L % 60L;
		long daysapartms = (timeTwo - timeOne) % 1000L;
		String s = daysapart + "分" + daysaparts + "秒" + daysapartms + "毫秒";
		return s;
	}

	public void reloadLogLevelProperties() throws IOException {
	}

	private static void autoReadClassLogLevelProperties() {
	}

	private static void initAllFlag() {
	}
}