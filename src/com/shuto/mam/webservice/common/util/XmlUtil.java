package com.shuto.mam.webservice.common.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class XmlUtil {
	
	/**
	 * 判断是否XML格式
	 * @Title: isXML  
	 * @Description: TODO  
	 * @param value
	 * @return boolean
	 * @throws
	 */
	public static boolean isXML(String value) { 
		try { 
			DocumentHelper.parseText(value); 
		} catch (DocumentException e) { 
			return false; 
		} 
		return true; 
	} 

	/** 
     * xml转map 不带属性 
     * @param xmlStr 
     * @param needRootKey 是否需要在返回的map里加根节点键 
     * @return 
     * @throws DocumentException 
     */  
    public static Map xml2map(String xmlStr, boolean needRootKey) throws DocumentException {  
        Document doc = DocumentHelper.parseText(xmlStr);  
        Element root = doc.getRootElement();  
        Map<String, Object> map = (Map<String, Object>) xml2map(root);  
        if(root.elements().size()==0 && root.attributes().size()==0){  
            return map;  
        }  
        if(needRootKey){  
            //在返回的map里加根节点键（如果需要）  
            Map<String, Object> rootMap = new HashMap<String, Object>();  
            rootMap.put(root.getName(), map);  
            return rootMap;  
        }  
        return map;  
    }  
  
    /** 
     * xml转map 带属性 
     * @param xmlStr 
     * @param needRootKey 是否需要在返回的map里加根节点键 
     * @return 
     * @throws DocumentException 
     */  
    public static Map xml2mapWithAttr(String xmlStr, boolean needRootKey) throws DocumentException {  
        Document doc = DocumentHelper.parseText(xmlStr);  
        Element root = doc.getRootElement();  
        Map<String, Object> map = (Map<String, Object>) xml2mapWithAttr(root);  
        if(root.elements().size()==0 && root.attributes().size()==0){  
            return map; //根节点只有一个文本内容  
        }  
        if(needRootKey){  
            //在返回的map里加根节点键（如果需要）  
            Map<String, Object> rootMap = new HashMap<String, Object>();  
            rootMap.put(root.getName(), map);  
            return rootMap;  
        }  
        return map;  
    }  
  
    /** 
     * xml转map 不带属性 
     * @param e 
     * @return 
     */  
    private static Map xml2map(Element e) {  
        Map map = new LinkedHashMap();  
        List list = e.elements();  
        if (list.size() > 0) {  
            for (int i = 0; i < list.size(); i++) {  
                Element iter = (Element) list.get(i);  
                List mapList = new ArrayList();  
  
                if (iter.elements().size() > 0) {  
                    Map m = xml2map(iter);  
                    if (map.get(iter.getName()) != null) {  
                        Object obj = map.get(iter.getName());  
                        if (!(obj instanceof List)) {  
                            mapList = new ArrayList();  
                            mapList.add(obj);  
                            mapList.add(m);  
                        }  
                        if (obj instanceof List) {  
                            mapList = (List) obj;  
                            mapList.add(m);  
                        }  
                        map.put(iter.getName(), mapList);  
                    } else  
                        map.put(iter.getName(), m);  
                } else {  
                    if (map.get(iter.getName()) != null) {  
                        Object obj = map.get(iter.getName());  
                        if (!(obj instanceof List)) {  
                            mapList = new ArrayList();  
                            mapList.add(obj);  
                            mapList.add(iter.getText());  
                        }  
                        if (obj instanceof List) {  
                            mapList = (List) obj;  
                            mapList.add(iter.getText());  
                        }  
                        map.put(iter.getName(), mapList);  
                    } else  
                        map.put(iter.getName(), iter.getText());  
                }  
            }  
        } else  
            map.put(e.getName(), e.getText());  
        return map;  
    }  
  
    /** 
     * xml转map 带属性 带转换特殊字符
     * @param e 
     * @return 
     */  
    private static Map xml2mapWithAttr(Element element) {  
        Map<String, Object> map = new LinkedHashMap<String, Object>();  
  
        List<Element> list = element.elements();  
        List<Attribute> listAttr0 = element.attributes(); // 当前节点的所有属性的list  
        for (Attribute attr : listAttr0) {  
            map.put("@" + attr.getName(), attr.getValue());  
        }  
        if (list.size() > 0) {  
  
            for (int i = 0; i < list.size(); i++) {  
                Element iter = list.get(i);  
                List mapList = new ArrayList();  
  
                if (iter.elements().size() > 0) {  
                    Map m = xml2mapWithAttr(iter);  
                    if (map.get(iter.getName()) != null) {  
                        Object obj = map.get(iter.getName());  
                        if (!(obj instanceof List)) {  
                            mapList = new ArrayList();  
                            mapList.add(obj);  
                            mapList.add(m);  
                        }  
                        if (obj instanceof List) {  
                            mapList = (List) obj;  
                            mapList.add(m);  
                        }  
                        map.put(iter.getName(), mapList);  
                    } else  
                        map.put(iter.getName(), m);  
                } else {
                    List<Attribute> listAttr = iter.attributes(); // 当前节点的所有属性的list  
                    Map<String, Object> attrMap = null;  
                    boolean hasAttributes = false;  
                    if (listAttr.size() > 0) {  
                        hasAttributes = true;  
                        attrMap = new LinkedHashMap<String, Object>();  
                        for (Attribute attr : listAttr) {  
                            attrMap.put("@" + attr.getName(), attr.getValue());  
                        }  
                    }  
  
                    if (map.get(iter.getName()) != null) {  
                        Object obj = map.get(iter.getName());  
                        if (!(obj instanceof List)) {  
                            mapList = new ArrayList();  
                            mapList.add(obj);  
                            // mapList.add(iter.getText());  
                            if (hasAttributes) {  
                                attrMap.put("#text", replaceSpecialChar(iter.getText()));  
                                mapList.add(attrMap);  
                            } else {  
                                mapList.add(replaceSpecialChar(iter.getText()));  
                            }  
                        }  
                        if (obj instanceof List) {  
                            mapList = (List) obj;  
                            // mapList.add(iter.getText());  
                            if (hasAttributes) {  
                                attrMap.put("#text", replaceSpecialChar(iter.getText()));  
                                mapList.add(attrMap);  
                            } else {  
                                mapList.add(replaceSpecialChar(iter.getText()));  
                            }  
                        }  
                        map.put(iter.getName(), mapList);  
                    } else {  
                        // map.put(iter.getName(), iter.getText());  
                        if (hasAttributes) {  
                            attrMap.put("#text", replaceSpecialChar(iter.getText()));  
                            map.put(iter.getName(), attrMap);  
                        } else {  
                            map.put(iter.getName(), replaceSpecialChar(iter.getText()));  
                        }  
                    }  
                }  
            }  
        } else {  
            // 根节点的  
            if (listAttr0.size() > 0) {  
                map.put("#text", replaceSpecialChar(element.getText()));  
            } else {  
                map.put(element.getName(), replaceSpecialChar(element.getText()));  
            }  
        }  
        return map;  
    }  
      
    /** 
     * map转xml map中没有根节点的键 
     * @param map 
     * @param rootName 
     * @throws DocumentException 
     * @throws IOException 
     */  
    public static Document map2xml(Map<String, Object> map, String rootName) throws DocumentException, IOException  {  
        Document doc = DocumentHelper.createDocument();  
        Element root = DocumentHelper.createElement(rootName);  
        doc.add(root);  
        map2xml(map, root);  
        return doc;  
    }  
      
    /** 
     * map转xml map中含有根节点的键 
     * @param map 
     * @throws DocumentException 
     * @throws IOException 
     */  
    public static Document map2xml(Map<String, Object> map) throws DocumentException, IOException  {  
        Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();  
        if(entries.hasNext()){ //获取第一个键创建根节点  
            Map.Entry<String, Object> entry = entries.next();  
            Document doc = DocumentHelper.createDocument();  
            Element root = DocumentHelper.createElement(entry.getKey());  
            doc.add(root);  
            map2xml((Map<String, Object>)entry.getValue(), root);  
            return doc;  
        }  
        return null;  
    }  
      
    /** 
     * map转xml 
     * @param map 
     * @param body xml元素 
     * @return 
     */  
    private static Element map2xml(Map<String, Object> map, Element body) {  
        Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();  
        while (entries.hasNext()) {  
            Map.Entry<String, Object> entry = entries.next();  
            String key = entry.getKey();  
            Object value = entry.getValue();  
            if(key.startsWith("@")){    //属性  
                body.addAttribute(key.substring(1, key.length()), value.toString());  
            } else if(key.equals("#text")){ //有属性时的文本  
                body.setText(value.toString());  
            } else {  
                if(value instanceof java.util.List ){  
                    List list = (List)value;  
                    Object obj;  
                    for(int i=0; i<list.size(); i++){  
                        obj = list.get(i);  
                        //list里是map或String，不会存在list里直接是list的，  
                        if(obj instanceof java.util.Map){  
                            Element parentElement = null;
                            if (body.isRootElement() && key.equals(body.getName())) {
                        		parentElement = body;
                        	} else {
                        		parentElement = body.addElement(key);
                        	}
                            map2xml((Map)obj, parentElement);  
                        } else {
                        	if (body != null) {
                        		body.addElement(key).setText((String)list.get(i));  
                        	}
                        }  
                    }  
                } else if(value instanceof java.util.Map ){  
                    if (body != null) {
                    	Element subElement = body.addElement(key);  
                        map2xml((Map)value, subElement); 
                	} 
                } else {
                	if (body != null) {
                		body.addElement(key).setText(value!=null?value.toString():"");  
                	} 
                }  
            }
        }  
        return body;  
    }
      
    /** 
     * 格式化输出xml 
     * @param xmlStr 
     * @return 
     * @throws DocumentException 
     * @throws IOException 
     */  
    public static String formatXml(String xmlStr) throws DocumentException, IOException  {  
        Document document = DocumentHelper.parseText(xmlStr);  
        return formatXml(document);  
    }  
      
    /** 
     * 格式化输出xml 
     * @param document 
     * @return 
     * @throws DocumentException 
     * @throws IOException 
     */  
    public static String formatXml(Document document) throws DocumentException, IOException  {  
        // 格式化输出格式  
        OutputFormat format = OutputFormat.createPrettyPrint();  
        //format.setEncoding("UTF-8");  
        StringWriter writer = new StringWriter();  
        // 格式化输出流  
        XMLWriter xmlWriter = new XMLWriter(writer, format);  
        // 将document写入到输出流  
        xmlWriter.write(document);  
        xmlWriter.close();  
        return writer.toString();  
    }
    
    /**
     * 格式化输出xml(不带xml头)
     * @param document
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public static String formatXmlNoHead(Document document) throws DocumentException, IOException  {  
        return document.getRootElement().asXML();  
    }
    
    /**
     * 替换xml中特殊字符
     * @param valuestr
     * @return
     */
    public static String replaceSpecialChar(String valuestr) {
    	String newstr = null;
    	if (valuestr != null) {
    		newstr = valuestr.replace("&lt;", "<");
    		newstr = newstr.replace("&gt;", ">");
    		newstr = newstr.replace("&amp;", "&");
    		newstr = newstr.replace("&apos;", "\'");
    		newstr = newstr.replace("&quot;", "\"");
    	}
    	return newstr;
    }

    public static void main(String[] args) {
    	Map<String, Object> map = new LinkedHashMap<String, Object>();
    	Map<String, Object> map2 = new LinkedHashMap<String, Object>();
    	List<String> list = new LinkedList<String>();
    	map.put("result", list);
    	for (int i=1;i<10;i++) {
    		map2.put("A"+i, i);
    		list.add("A"+i);
    	}
    	
    	try {
			String xmlstr = "<query><servicename>接口服务名</servicename><partnerid>接口系统标识</partnerid><sign>接口签名</sign><pagesize>每页记录数</pagesize><pagenum>当前页码</pagenum><orgid>组织ID</orgid></query>";
			map = XmlUtil.xml2map(xmlstr, true);
			System.out.println(map);
			System.out.println(((Map<String, Object>)map.get("query")).get("servicename"));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
}
