package com.shuto.mam.webclient.beans.bhtt;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import psdi.mbo.MboRemote;
import psdi.util.MXException;

import com.shuto.mam.webclient.beans.base.CAppBean;

/**   
 * @Title: BhttAppBean.java 
 * @Package com.shuto.mam.webclient.beans.bhtt 
 * @Description: (保护投退申请AppBean) 
 * @author wuqi   
 * @date 2017-6-16 上午11:16:04 
 * @version V1.0   
 */
public class BhttAppBean  extends CAppBean{
	
	@Override
	public int SAVE() throws MXException, RemoteException {
//		MboRemote mbo = getMbo();
		
		return super.SAVE();
	}

}
