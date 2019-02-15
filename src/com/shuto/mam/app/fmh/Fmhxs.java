package com.shuto.mam.app.fmh;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.fmh.Fmhxs 华东大区 阜阳项目 粉煤灰销售对象 （副产品销售功能）设置只读必填
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月17日 下午4:29:15
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class Fmhxs extends Mbo implements FmhxsRemote {
	public Fmhxs(MboSet mboSet) throws MXException, RemoteException {
		super(mboSet);
	}

	public void init() throws MXException {

		super.init();
		try {
			MboRemote fmhmbo = this;
			String status = fmhmbo.getString("STATUS");
			String tableId = fmhmbo.getName() + "id";
			long codeid = fmhmbo.getLong(tableId);
			String personid = fmhmbo.getUserInfo().getPersonId();
			String sql = "OWNERID='" + codeid + "' AND OWNERTABLE='" + fmhmbo.getName()
					+ "' AND ASSIGNSTATUS='活动' AND ASSIGNCODE='" + personid + "'";
			// 查WFASSIGNMENT表中是否有这个personid，如为空，则证明没有权限生成。
			MboSetRemote mbosetremote = fmhmbo.getMboSet("$ASSIGNCODE", "WFASSIGNMENT", sql);

			if (!fmhmbo.isNew()) {
				if (!status.equals("新建")) {
					fmhmbo.setFieldFlag("COMPANY", 7L, true);
					fmhmbo.setFieldFlag("CH", 7L, true);
					fmhmbo.setFieldFlag("RCDATE", 7L, true);
					fmhmbo.setFieldFlag("TYPE", 7L, true);
				}
				// else{
				// fmhmbo.setFieldFlag("COMPANY", 7L, false);
				// fmhmbo.setFieldFlag("CH", 7L, false);
				// fmhmbo.setFieldFlag("RCDATE", 7L, false);
				// fmhmbo.setFieldFlag("TYPE", 7L, false);
				// // }
			}
			// if(status.equalsIgnoreCase("灰库装料")&&(!mbosetremote.isEmpty())||(personid.equalsIgnoreCase("maxadmin")&&("灰库检查".equals(status)))){
			// fmhmbo.setFieldFlag("TYPE", 128L, true);
			// }
			if (status.equalsIgnoreCase("计量空车重量") && (!mbosetremote.isEmpty())
					|| (personid.equalsIgnoreCase("maxadmin") && ("计量空车重量".equals(status)))) {
				fmhmbo.setFieldFlag("CH", 7L, false);
				fmhmbo.setFieldFlag("CH", 11L, true);
			}
			if (status.equalsIgnoreCase("门岗登记") && (!mbosetremote.isEmpty())
					|| (personid.equalsIgnoreCase("maxadmin") && ("门岗登记".equals(status)))) {

				fmhmbo.setValue("CCDATE", MXServer.getMXServer().getDate());
			}

			if (status.equals("已关闭")) {
				fmhmbo.setFlag(7L, true);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}