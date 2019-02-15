package com.shuto.mam.webclient.beans.wo.hse;
/**  
 * @Description  此类为位置(HSE)弹出hse维护清单相关记录的DataBean类
 * @author       QiTT qitt@shuto.cn
 * @date         2013-8-12 下午15:21:33
 * @Copyright:   2013 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * <p> 			此类为位置(HSE)弹出hse维护清单相关记录的DataBean类，用于实现位置(HSE)弹出相关联的hse维护清单记录
 * @author      QiTT
 * @date        2013-8-12 下午15:21:33
 * @version     Ver 1.0
 */
public class SelectHsegdDateBean extends DataBean {
	protected MboSetRemote getMboSetRemote() throws MXException,RemoteException {
		// TODO Auto-generated method stub
		// 获得当前记录的Mbo
		MboRemote thismbo = app.getAppBean().getMbo();
		//获得当前工作票编号
		String location = thismbo.getString("location");
		//获得当前siteid
		String siteid = thismbo.getString("siteid");
		//获得v_activity_wo的mboset
		MboSetRemote mboset = MXServer.getMXServer().getMboSet("v_activity_wo", MXServer.getMXServer().getUserInfo("maxadmin"));

		mboset.setWhere("siteid = '"+siteid+"' and wonum in (select wonum from wosafetylink where tagoutid is not null and location = '"+location+"') and tagoutid is not null");
		
		return mboset;
	}
}
