package com.shuto.mam.webclient.beans.doclinks;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class doclinkAppBean extends AppBean {
	static String docId;

	static final String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC7JL0DcaMUHumSdhxXTxqiABBC\r\n"
			+ "DERhRJIsAPB++zx1INgSEKPGbexDt1ojcNAc0fI+G/yTuQcgH1EW8posgUni0mcT\r\n"
			+ "E6CnjkVbv8ILgCuhy+4eu+2lApDwQPD9Tr6J8k21Ruu2sWV5Z1VRuQFqGm/c5vaT\r\n" + "OQE5VFOIXPVTaa25mQIDAQAB";

	// 服务器配置和帐号信息
	private String ip = "10.42.5.226";

	// 保存userid和tokenid鉴权信息，用来发送后续的请求
	private String userid;
	private String tokenid;

	// 附件删除
	public int instantdelete() throws MXException {
		try {
			MboRemote mbo = this.app.getAppBean().getMbo();

			MboSetRemote mbos = mbo.getMboSet("DOCLINKS");
			MboRemote docinfo = mbos.getMbo(this.currentRow);
			String description = docinfo.getString("DESCRIPTION");
			MboSetRemote docinfoSet = mbo.getMboSet("$docinfo" + (int) (Math.random() * 10000.0D), "docinfo",
					" description='" + description + "'");
			MboRemote docinfoMbo = docinfoSet.getMbo(0);
			String filenames = docinfoMbo.getString("FILEGNS");
			String files = docinfoMbo.getString("URLNAME");
			//判断是否为之前遗留的附件
			if(filenames.length()==0) {
				super.instantdelete();
			}else {
			// 获取密匙
			logins();
			// 删除云盘文件
			DeletFile(filenames);
			File file = new File(files);
			if (file.exists() && file.isFile()) {
				file.delete();
			}
			super.instantdelete();
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 1;
	}

	// 附件下载

	public void fileDown() throws Exception {
		try {
			MboRemote mbo = this.app.getAppBean().getMbo();

			MboSetRemote mbos = mbo.getMboSet("DOCLINKS");
			MboRemote docinfo = mbos.getMbo(this.currentRow);
			String description = docinfo.getString("DESCRIPTION");
			MboSetRemote docinfoSet = mbo.getMboSet("$docinfo" + (int) (Math.random() * 10000.0D), "docinfo",
					" description='" + description + "'");
			MboRemote docinfoMbo = docinfoSet.getMbo(0);
			String filenames = docinfoMbo.getString("FILEGNS");
			String urlName = docinfoMbo.getString("URLNAME");
			String fileType = docinfoMbo.getString("DOCTYPE");
			File file = new File(urlName);
			String fileName = file.getName();
			System.out.println("fileName:" + fileName);
			String urls = "http://10.42.5.239:7016/doclinks/Attachments/" + fileType + "/" + fileName;
			System.out.println("urls" + urls);
			if (filenames.length() == 0) {
				this.app.openURL(urls, true);
			}
			else {
				// 获取密匙
				logins();
				ASBeginUploadResult request = Osdownload(filenames);
				// 获取到文件下载的路径

				this.app.openURL(request.url, true);
			}

			// http://10.42.5.239:7016/doclinks/Attachments/woticket/1.xls

		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public void SetServerIP(String ip) {
		this.ip = ip;
	}

	private static String RSAEncode(String pass) throws IOException, GeneralSecurityException {
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(new BASE64Decoder().decodeBuffer(pubKey)));

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return new BASE64Encoder().encode(cipher.doFinal(pass.getBytes("UTF-8"))).replace("\r\n", "\n");
	}

	public void logins() throws Exception {
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
		System.setProperty("https.protocols", "TLSv1");
		SslUtils.ignoreSsl();
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

	// 下载文件协议
	public ASBeginUploadResult Osdownload(String gns) throws Exception {
		ASBeginUploadResult requst = new ASBeginUploadResult();
		SslUtils.ignoreSsl();
		String result = "";
		JSONObject dir = new JSONObject();
		dir.put("docid", gns);
		dir.put("authtype", "QUERY_STRING");
		String strdir = dir.toString();
		System.setProperty("https.protocols", "TLSv1");
		SslUtils.ignoreSsl();
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
		outs.append(strdir);
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
			requst.docId = gns;
			requst.name = retunjson.getString("name");
			requst.rev = retunjson.getString("rev");
		}
		return requst;
	}

	// 删除云盘文件
	public void DeletFile(String gns) throws Exception {
		SslUtils.ignoreSsl();
		JSONObject dir = new JSONObject();
		dir.put("docid", gns);
		String strdir = dir.toString();
		System.setProperty("https.protocols", "TLSv1");
		SslUtils.ignoreSsl();
		String urls = "http://" + this.ip + ":9123/v1/file?method=delete&userid=" + this.userid + "&tokenid="
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
		outs.append(strdir);
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

		}

	}

}
