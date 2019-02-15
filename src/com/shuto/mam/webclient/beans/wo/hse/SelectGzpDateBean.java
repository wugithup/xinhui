/**  
 * @Description  此类为HSE维护清单弹出工作票的DataBean类
 * @author       QiTT qitt@shuto.cn
 * @date         2013-8-8 上午10:22:23
 * @Copyright:   2013 Shuto版权所有
 * @version      V1.0  
 */
package com.shuto.mam.webclient.beans.wo.hse;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * <p>
 * 此类为HSE维护清单弹出工作票的DataBean类，用于实现在HSE维护清单中每条记录弹出所相对应的工作票 钥匙箱占用情况相关记录查询
 * 
 * @author QiTT
 * @date 2013-8-8 上午10:22:23
 * @version Ver 1.0
 */
public class SelectGzpDateBean extends DataBean {
	protected MboSetRemote getMboSetRemote() throws MXException,
			RemoteException {
		// TODO Auto-generated method stub
		// 获得当前记录的Mbo
		MboRemote thismbo = app.getAppBean().getMbo();
		String barcodeid = thismbo.getString("barcodeid");
		MboSetRemote mboset = MXServer.getMXServer().getMboSet("workorder",
				MXServer.getMXServer().getUserInfo("maxadmin"));
		mboset.setWhere(" wonum in (select wonum  from hse_tagout where ysxid='"
				+ barcodeid + "'   )");

		return mboset;
	}
}