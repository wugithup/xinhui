package com.shuto.mam.portal.model;

/**
 * html 中select中 option属性
 * @author liuyc
 *
 */
public class Option {
	private String id; //option ID
	private String name; //option 名称
	private String text; //option 描述
	private boolean selected; //option 是否是缺省值
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	

}
