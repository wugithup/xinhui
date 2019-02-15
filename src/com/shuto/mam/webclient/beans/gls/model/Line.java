package com.shuto.mam.webclient.beans.gls.model;

/**
 * 钥匙箱连接线对象 
 * @author zhangjq
 *
 */
public class Line {

	private String type ;  //类型
	private String from ;   //连接的开始节点
	private String to  ;  //连接的结束节点
	private String name ; //描述
	private boolean marked ;  //表示是否已被用橙色标注
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isMarked() {
		return marked;
	}
	public void setMarked(boolean marked) {
		this.marked = marked;
	}
	
	
}
