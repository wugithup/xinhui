package com.shuto.mam.webservice.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: Element
 * @Description: 解析XML
 * @author lull lull@shuto.cn
 * @date 2017年5月18日 下午10:50:42
 *
 */
public class Element {

	private String key;
	private String value = "";

	public Element() {
	}

	public Element(String key, Element value) {
		super();
		this.key = key;
		this.value = value.toString();
	}

	public Element(String key, Element[] values) {
		super();
		this.key = key;
		for (Element value : values) {
			this.value += value.toString();
		}
	}

	public Element(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static List<Element> parseElements(String value) throws Exception {
		value = value.replaceAll("<!--[\\w\\W\r\\n]*?-->", "").replaceAll("<\\?xml[\\w\\W\r\\n]*?\\?>", "");
		ArrayList<Element> eles = new ArrayList<Element>();
		if (value.indexOf("<") < 0) {
			eles.add(new Element(null, value));
		} else {
			boolean find = true;
			while (find) {
				if (value.indexOf(">", value.indexOf("<")) > value.indexOf("<")) {
					String key = value.substring(value.indexOf("<") + 1, value.indexOf(">", value.indexOf("<"))).trim();// .replaceAll("(^\\s{1,})|(\\s{1,}$)",
																														// "");
					int c = getCloseIndex(key, value);
					eles.add(new Element(key, value.substring(value.indexOf(">") + 1, c)));
					value = value.substring(value.indexOf(">", c) + 1);
				} else {
					find = false;
				}
			}

		}
		return eles;
	}

	public static Map<String, String> parseElementsMap(String value) throws Exception {
		Map<String, String> eles = new HashMap<String, String>();
		List<Element> list = parseElements(value);
		for (Element e : list) {
			eles.put(e.getKey(), e.getValue());
		}
		return eles;
	}

	private static int getCloseIndex(String key, String value) throws Exception {
		int index = 0;
		boolean find = true;
		while (find) {
			if (value.indexOf(">", value.indexOf("</", index)) > value.indexOf("</", index)) {
				String close = value
						.substring(value.indexOf("</", index) + 2, value.indexOf(">", value.indexOf("</", index)))
						.trim();
				index = value.indexOf("</", index);
				if (close.equalsIgnoreCase(key)) {
					find = false;
				} else {
					index += 2;
				}
			} else {
				throw new Exception("element error:unclosed element>" + key);
			}
		}

		return index;
	}

	public static Element parseElement(String value) throws Exception {
		value = value.replaceAll("<!--[\\w\\W\r\\n]*?-->", "").replaceAll("<\\?xml[\\w\\W\r\\n]*?\\?>", "");
		if (value.indexOf("<") < 0) {
			return new Element(null, value);
		} else {
			if (value.indexOf(">") > value.indexOf("<")) {
				String key = value.substring(value.indexOf("<") + 1, value.indexOf(">")).trim();
				if (value.lastIndexOf(">") > value.lastIndexOf("</")
						&& value.substring(value.lastIndexOf("</") + 2, value.lastIndexOf(">")).trim().equals(key)) {
					return new Element(key, value.substring(value.indexOf(">") + 1, value.lastIndexOf("<")));
				} else {
					throw new Exception("element error:unclosed element>" + key);
				}
			} else {
				throw new Exception("element error:not found element at value:" + value);
			}
		}
	}

	@Override
	public String toString() {
		if (key == null) {
			return value;
		} else {
			return "<" + key + ">" + value + "</" + key + ">";
		}
	}

}
