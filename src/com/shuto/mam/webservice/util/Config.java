package com.shuto.mam.webservice.util;

import java.io.IOException;
import java.util.Properties;

public class Config extends Properties {
	public static Config dao = new Config();
	private static final long serialVersionUID = 1L;

	public Config() {
		try {
			load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized void load() throws IOException {
		load(Config.class.getResourceAsStream("/mxws.properties"));
	}
}