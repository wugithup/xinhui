package com.shuto.mam.webclient.beans.tz.suggestion;

import java.rmi.RemoteException;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.util.MXException;

/**
 * @author lzq
 * @date 创建时间：2017-3-24 上午9:31:27
 * @since 原华南台账相关类
 */
public class SuggestionAppBean extends CAppBean {
	private String ATTR_ID;

	public SuggestionAppBean() {
		this.OWNERTABLE = "HLHJY";
		this.ATTR_ID = (this.OWNERTABLE + "ID");
	}

	protected void initialize() throws MXException, RemoteException {
	super.initialize();
	MboRemote thisMbo = this.app.getAppBean().getMbo();
	if (!thisMbo.getString("STATUS").equalsIgnoreCase("等待批准"))
		return;
	setTableFlag(7L, true);
}
}