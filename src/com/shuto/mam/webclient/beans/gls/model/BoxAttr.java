package com.shuto.mam.webclient.beans.gls.model;

/**
 * 每个钥匙箱的属性
 * @author Liuyc
 *
 */
public class BoxAttr {
	private String width="";
	private String height="";
	private String x="";
	private String y="";
	private String link="";
	private String color="";
	private String id="";
	private String name="";
	
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSet(){
		return "<set x='"+x+"' y='"+y+"' width='"+width+"' height='"+height+"' name='"+name+"' color='"+color+"' id='"+id+"' link='javascript:noteClick(\""+id+"\")'/>";
	}
	

}
