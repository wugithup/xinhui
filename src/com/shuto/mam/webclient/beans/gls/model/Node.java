package com.shuto.mam.webclient.beans.gls.model;

/**
 * 钥匙箱节点 对象
 * @author zhangjq
 *
 */
public class Node {
	private String name ;  //节点描述
	private int left ;  //左距离
	private int top ;  //上距离
	private String type ; //类型
	private int width ;  //宽度 
	private int height ;  //高度
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	

}
