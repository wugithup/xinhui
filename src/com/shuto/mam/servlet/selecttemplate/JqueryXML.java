package com.shuto.mam.servlet.selecttemplate;

import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class JqueryXML {
	Element root = null;
	Document doc = null;
	private Element selectTemplate = null;

	public JqueryXML() {
		// TODO Auto-generated constructor stub
		String xmlName = "";
		// System.out.println("JqueryXML");
		URL fileURL = getClass().getResource("/config/selectTemplate.xml");
		if (fileURL != null) {
			xmlName = fileURL.getPath();
		}
		System.out.println("xmlName=" + xmlName);
		readXml(xmlName);

	}

	public void readXml(String xmlName) {
		// 读取XML文件
		SAXReader reader = new SAXReader();
		try {
			doc = reader.read(xmlName);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 获取XML根元素
		root = doc.getRootElement();
	}

	public boolean setSelectTemplate(String selectTemplate) {
		// TODO Auto-generated method stub
		// this.selectTemplate=selectTemplate;
		List<Element> sts = root.elements("selecttemplate");
		for (Element element : sts) {
			String name = element.attributeValue("name");
			// System.out.println("name="+name);
			if (name != null && name.equals(selectTemplate)) {
				this.selectTemplate = element;
				return true;
			}
		}
		return false;

	}

	public String getSql() {
		// TODO Auto-generated method stub
		Element sql = selectTemplate.element("sql");
		return sql != null ? sql.getText() : null;
	}

	public Map getWhere() {
		Map<String, String> map = new Hashtable<String, String>();
		Element where = selectTemplate.element("where");
		if (where != null) {
			List<Element> parameters = where.elements("parameter");
			for (Element element : parameters) {
				String name = element.attributeValue("name");
				if (name != null && !name.isEmpty()) {
					String text = element.getText();
					map.put(name, text);
					// System.out.println("name="+name+",text="+text);
				}
			}
		}
		return map;
	}

}
