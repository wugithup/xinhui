package com.shuto.mam.webclient.beans.rqreport;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RqGetProperty {
	public String getProperty(String properName) {
		Properties proper = new Properties();
		InputStream read = super.getClass().getResourceAsStream("/shuto.properties");
		try {
			proper.load(read);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return proper.getProperty(properName);
	}
}