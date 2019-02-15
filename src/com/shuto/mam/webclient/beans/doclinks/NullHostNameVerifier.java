package com.shuto.mam.webclient.beans.doclinks;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class NullHostNameVerifier implements HostnameVerifier {
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.net.ssl.HostnameVerifier#verify(java.lang.String,
	 * javax.net.ssl.SSLSession)
	 */

	@Override
	public boolean verify(String arg0, SSLSession arg1) {
		
		return false;
	}
}





	