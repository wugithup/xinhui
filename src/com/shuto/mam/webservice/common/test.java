package com.shuto.mam.webservice.common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.shuto.mam.webservice.common.service.impl.CommonServiceImpl;
import com.shuto.mam.webservice.common.util.Base64Util;
import com.shuto.mam.webservice.common.util.XmlUtil;


public class test {

	/** 
	 * @Title: main  
	 * @Description: TODO  
	 * @param args void
	 * @throws 
	 */
	public static void main(String[] args) {
		try {
            String encoding="UTF-8";
            File file=new File("C:/Users/ITROBOT/Desktop/更新类/base64/headers.txt");
            StringBuffer str = new StringBuffer();
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){   
                    str.append(lineTxt);
                }
                read.close();
//                System.out.println(str.toString());
                String text = Base64Util.encode(str.toString());
                System.out.println("==========================================");
//                System.out.println(text);
                
                File outfile = new File("C:/Users/ITROBOT/Desktop/更新类/base64/headers_base64.txt");
                PrintStream ps = new PrintStream(new FileOutputStream(outfile));
                ps.println(text);// 往文件里写入字符串
                ps.close();
                
                String text2 = Base64Util.decode(text);
                File outfile2 = new File("C:/Users/ITROBOT/Desktop/更新类/base64/headers_base64_2.txt");
                PrintStream ps2 = new PrintStream(new FileOutputStream(outfile2));
                ps2.println(text2);// 往文件里写入字符串
                ps2.close();
	    }else{
	        System.out.println("找不到指定的文件");
	    }
	    } catch (Exception e) {
	        System.out.println("读取文件内容出错");
	        e.printStackTrace();
	    }
	}

}
