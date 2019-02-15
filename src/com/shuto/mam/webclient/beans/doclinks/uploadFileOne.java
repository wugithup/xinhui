package com.shuto.mam.webclient.beans.doclinks;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import javax.crypto.Cipher;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import psdi.webclient.beans.doclinks.AddDocLinksDrBean;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManager;

public class uploadFileOne extends AddDocLinksDrBean {

	static String docId;

	static final String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC7JL0DcaMUHumSdhxXTxqiABBC\r\n"
			+ "DERhRJIsAPB++zx1INgSEKPGbexDt1ojcNAc0fI+G/yTuQcgH1EW8posgUni0mcT\r\n"
			+ "E6CnjkVbv8ILgCuhy+4eu+2lApDwQPD9Tr6J8k21Ruu2sWV5Z1VRuQFqGm/c5vaT\r\n" + "OQE5VFOIXPVTaa25mQIDAQAB";

	// 服务器配置和帐号信息
	private String ip;

	// 保存userid和tokenid鉴权信息，用来发送后续的请求
	private String userid;
	private String tokenid;

	private static String RSAEncode(String pass) throws IOException, GeneralSecurityException {
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(new BASE64Decoder().decodeBuffer(pubKey)));

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return new BASE64Encoder().encode(cipher.doFinal(pass.getBytes("UTF-8"))).replace("\r\n", "\n");
	}

	public void SetServerIP(String ip) {
		this.ip = ip;
	}

	public void logins() throws GeneralSecurityException, IOException {
		String account = "XHDC";
		String password = "111111";
		String encopassword = RSAEncode(password);
		String result = "";
		JSONObject user = new JSONObject();
		user.put("account", account);
		user.put("password", encopassword);
		String user2 = user.toString();
		String urls = "http://" + this.ip + ":9998/v1/auth1?method=getnew";
		URL url = new URL(urls);// 创建连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestMethod("POST"); // 设置请求方式
		connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
		connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
		connection.connect();
		OutputStreamWriter outs = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
		outs.append(user2);
		outs.flush();
		outs.close();
		// 读取响应
		int length = (int) connection.getContentLength();// 获取长度
		InputStream is = connection.getInputStream();
		if (length != -1) {
			byte[] data = new byte[length];
			byte[] temp = new byte[512];
			int readLen = 0;
			int destPos = 0;
			while ((readLen = is.read(temp)) > 0) {
				System.arraycopy(temp, 0, data, destPos, readLen);
				destPos += readLen;
			}
			result = new String(data, "UTF-8"); // utf-8编码
			JSONObject retunjson = JSONObject.fromObject(result);
			this.userid = (String) retunjson.get("userid");
			this.tokenid = (String) retunjson.get("tokenid");
		}
	}

	// 获取文件的gns
	public ArrayList<ASEntryDoc> getFilesGns() throws IOException {
		ArrayList<ASEntryDoc> entryDocs = new ArrayList<ASEntryDoc>();
		String result = "";
		String urls = "http://" + this.ip + ":9998/v1/entrydoc?method=get&userid=" + this.userid + "&tokenid="
				+ this.tokenid;
		URL url = new URL(urls);// 创建连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestMethod("POST"); // 设置请求方式
		connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
		connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
		connection.connect();
		OutputStreamWriter outs = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
		outs.append("");
		outs.flush();
		outs.close();
		// 读取响应
		int length = (int) connection.getContentLength();// 获取长度
		InputStream is = connection.getInputStream();
		if (length != -1) {
			byte[] data = new byte[length];
			byte[] temp = new byte[512];
			int readLen = 0;
			int destPos = 0;
			while ((readLen = is.read(temp)) > 0) {
				System.arraycopy(temp, 0, data, destPos, readLen);
				destPos += readLen;
			}
			result = new String(data, "UTF-8"); // utf-8编码
			JSONObject jsonObj = JSONObject.fromObject(result);
			JSONArray jsonDocInfos = jsonObj.getJSONArray("docinfos");

			for (int i = 0; i < jsonDocInfos.size(); i++) {
				JSONObject docObj = jsonDocInfos.getJSONObject(i);
				ASEntryDoc tmpDoc = new ASEntryDoc();
				tmpDoc.docId = docObj.getString("docid");
				tmpDoc.docName = docObj.getString("docname");
				tmpDoc.docType = docObj.getString("doctype");
				entryDocs.add(tmpDoc);
			}
		}

		return entryDocs;
	}

	static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	} };




	// 根据路径获取gns
	public void demo1(String gns, long fileLength, String name, int ondup) throws Exception {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("docid", gns);
		jsonObj.put("length", fileLength);
		jsonObj.put("name", name);
		jsonObj.put("ondup", ondup);
		String bodyStr = jsonObj.toString();
		String urls = "http://" + this.ip + ":9123/v1/file?method=osbeginupload&userid=" + this.userid + "&tokenid="
				+ this.tokenid;
		String result = "";
		URL url = new URL(urls);// 创建连接
		// SslUtils.ignoreSsl();
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestMethod("POST"); // 设置请求方式
		connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
		connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
		connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		connection.connect();
		OutputStreamWriter outs = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
		outs.append(bodyStr);
		outs.flush();
		outs.close();
		// 读取响应
		int length = (int) connection.getContentLength();// 获取长度
		InputStream is = connection.getInputStream();
		if (length != -1) {
			byte[] data = new byte[length];
			byte[] temp = new byte[512];
			int readLen = 0;
			int destPos = 0;
			while ((readLen = is.read(temp)) > 0) {
				System.arraycopy(temp, 0, data, destPos, readLen);
				destPos += readLen;
			}
			result = new String(data, "UTF-8"); // utf-8编码
			JSONObject retunjson = JSONObject.fromObject(result);
			this.userid = (String) retunjson.get("userid");
			this.tokenid = (String) retunjson.get("tokenid");
		}
	}

	public ASBeginUploadResult beginUploadFile(String gns, long fileLength, String name, int ondup) throws Exception {
		ASBeginUploadResult requst = new ASBeginUploadResult();
		SslUtils.ignoreSsl();
		String result = "";
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("docid", gns);
		jsonObj.put("length", fileLength);
		jsonObj.put("name", name);
		jsonObj.put("ondup", ondup);
		String bodyStr = jsonObj.toString();
		String urls = "http://" + this.ip + ":9123/v1/file?method=osbeginupload&userid=" + this.userid + "&tokenid="
				+ this.tokenid;
		URL url = new URL(urls);// 创建连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestMethod("POST"); // 设置请求方式
		connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
		connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
		connection.connect();
		OutputStreamWriter outs = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
		outs.append(bodyStr);
		// outs.write(bodyStr);
		outs.flush();
		outs.close();
		// 读取响应
		int length = (int) connection.getContentLength();// 获取长度

		InputStream is = connection.getInputStream();
		if (length != -1) {
			byte[] data = new byte[length];
			byte[] temp = new byte[512];
			int readLen = 0;
			int destPos = 0;
			while ((readLen = is.read(temp)) > 0) {
				System.arraycopy(temp, 0, data, destPos, readLen);
				destPos += readLen;
			}
			result = new String(data, "UTF-8"); // utf-8编码
			JSONObject retunjson = JSONObject.fromObject(result);
			JSONArray authRequestArray = retunjson.getJSONArray("authrequest");
			requst.url = authRequestArray.getString(1);
			requst.headers = new HashMap<String, String>();
			for (int i = 2; i <= 4; ++i) {
				String[] stringArray = authRequestArray.getString(i).split(":", 2);
				String key = stringArray[0].trim();
				String value = stringArray[1].trim();
				requst.headers.put(key, value);
			}
			requst.docId = retunjson.getString("docid");
			requst.name = retunjson.getString("name");
			requst.rev = retunjson.getString("rev");
		}
		return requst;
	}


	// 通过返回的url传输文件数据

	public void UploadPuts(String urls, HashMap<String, String> headers, String uploadPath) throws Exception {
		// 2.设置系统属性：
		System.setProperty("https.protocols", "TLSv1");
		SslUtils.ignoreSsl();
//			 HttpsURLConnection.setDefaultHostnameVerifier(new uploadFileOne().new NullHostNameVerifier());
//		        SSLContext sc = SSLContext.getInstance("TLS");
//		        sc.init(null, trustAllCerts, new SecureRandom());
		File file = new File(uploadPath);
		URL url = new URL(urls);// 创建连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestMethod("PUT");
		for (String key : headers.keySet()) {
			connection.setRequestProperty(key, headers.get(key));
		}
		connection.connect();
		OutputStreamWriter outs = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
		OutputStream out1 = connection.getOutputStream();

		// 读取本地图片文件流
		FileInputStream inputStream = new FileInputStream(file);
		byte[] datas = new byte[2048];
		int len = 0;
		int sum = 0;
		while ((len = inputStream.read(datas)) != -1) {
			// 将读取到的本地文件流读取到HttpsURLConnection,进行上传
			out1.write(datas, 0, len);
			sum = len + sum;
		}
		out1.flush();
		inputStream.close();
		out1.close();
		outs.flush();
		outs.close();
		// 读取响应
		int length = (int) connection.getContentLength();// 获取长度
		InputStream is = connection.getInputStream();
		if (length != -1) {
			byte[] data = new byte[length];
			byte[] temp = new byte[1024];
			int readLen = 0;
			int destPos = 0;
			while ((readLen = is.read(temp)) > 0) {
				System.arraycopy(temp, 0, data, destPos, readLen);
				destPos += readLen;
			}
	

		}

	}

//上传文件完成协议
	public JSONObject uploadFileEnd(String gns, String rev) throws IOException {
		JSONObject retunjson = null;
		String urlStr = "http://" + this.ip + ":9123/v1/file?method=osendupload&userid=" + this.userid + "&tokenid="
				+ this.tokenid;
		JSONObject dir = new JSONObject();
		dir.put("docid", gns);
		dir.put("rev", rev);
		String strdir = dir.toString();
		URL url = new URL(urlStr);// 创建连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestMethod("POST"); // 设置请求方式
		connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
		connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
		connection.connect();
		OutputStreamWriter outs = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
		outs.append(strdir);
		outs.flush();
		outs.close();
		// 读取响应
		int length = (int) connection.getContentLength();// 获取长度
		InputStream is = connection.getInputStream();
		if (length != -1) {
			byte[] data = new byte[length];
			byte[] temp = new byte[512];
			int readLen = 0;
			int destPos = 0;
			while ((readLen = is.read(temp)) > 0) {
				System.arraycopy(temp, 0, data, destPos, readLen);
				destPos += readLen;
			}
		}
		return retunjson;

		// 自定义错误信息
	}

	// 文件下载协议
	public String fileDowns(String fileGns) {
		JSONObject jsonObj = null;
		try {
			JSONObject reqbody = new JSONObject();
			reqbody.put("docid", fileGns);
			reqbody.put("authtype", "QUERY_STRING");
			String reqstr = reqbody.toString();
			String result = "";
			String urls = "http://" + this.ip + ":9123/v1/file?method=osdownload&userid=" + this.userid + "&tokenid="
					+ this.tokenid;
			URL url = new URL(urls);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
			connection.connect();
			OutputStreamWriter outs = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
			outs.append(reqstr);
			outs.flush();
			outs.close();
			// 读取响应
			int length = (int) connection.getContentLength();// 获取长度
			InputStream is = connection.getInputStream();
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				result = new String(data, "UTF-8"); // utf-8编码
				jsonObj = JSONObject.fromObject(result);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 自定义错误信息
		return jsonObj.getString("docid");
	}

	// 创建文件夹返回gns
	public String createMuenDir(String parentgns, String serverpath) {
		JSONObject jsonObj = null;
		try {
			JSONObject reqbody = new JSONObject();
			reqbody.put("docid", parentgns);
			reqbody.put("path", serverpath);
			String reqstr = reqbody.toString();
			String result = "";
			String urls = "http://" + this.ip + ":9123/v1/dir?method=createmultileveldir&userid=" + this.userid
					+ "&tokenid=" + this.tokenid;
			URL url = new URL(urls);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
			connection.connect();
			OutputStreamWriter outs = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
			outs.append(reqstr);
			outs.flush();
			outs.close();
			// 读取响应
			int length = (int) connection.getContentLength();// 获取长度
			InputStream is = connection.getInputStream();
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				result = new String(data, "UTF-8"); // utf-8编码
				jsonObj = JSONObject.fromObject(result);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 自定义错误信息
		System.out.println("jsonObj.getString(\"docid\");"+jsonObj.getString("docid"));
		return jsonObj.getString("docid");
	}

	public ASBeginUploadResult uploadName(String Url, String UrlType , String Gns) throws Exception {
		String ip = "10.42.5.226";
		String  uploadgns  = null ; 
		SetServerIP(ip);
		ASBeginUploadResult result=null;
		logins();
		ArrayList<ASEntryDoc> entryDocs = getFilesGns();
		String userDocGnsPath = "";
		for (int i = 0; i < entryDocs.size(); i++) {
			ASEntryDoc tmpObj = entryDocs.get(i);
			if (tmpObj.docType.equals("customdoc")) {
				userDocGnsPath = tmpObj.docId;
			}
		}
		if (userDocGnsPath.equals("")) {
			;
		}
		File file = new File(Url);
		String fileName = file.getName();
		long length = file.length();
		if(Gns.length()==0) {
			uploadgns=Gns;
			}
		else {
			uploadgns= createMuenDir(userDocGnsPath, UrlType);
		}
		result = beginUploadFile(uploadgns, length, fileName, 1); // 这里如果有重名文件，抛出异常
		// 根据返回的url通过xxx上传文件
		UploadPuts(result.url, result.headers, Url);
		// 文件上传完成协议
		uploadFileEnd(result.docId, result.rev);
		result.createUrl=uploadgns;
		return result;
	}


}
