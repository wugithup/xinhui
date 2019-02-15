package com.shuto.mam.webclient.beans.fmh;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.Utility;

/**
 * com.shuto.mam.webclient.beans.fmh.FmhxsAppBean
 * 
 * 华东大区 阜阳项目 副产品销售 页面类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月7日 下午2:51:37
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FmhxsAppBean extends CAppBean {
	@Override
	public int SAVE() throws MXException, RemoteException {
		super.SAVE();

		MboRemote fmhmbo = getMbo();
		String type = fmhmbo.getString("TYPE");

		MboSetRemote FMHXSJLMX = fmhmbo.getMboSet("FMHXSJLMX");
		MboSetRemote fmhjgs = fmhmbo.getMboSet("$fmhjgb", "fmhjgb", "type='" + type + "'  order by fmhjgbnum desc");
		if (FMHXSJLMX.count() > 0) {
			Date cjsh = FMHXSJLMX.getMbo(0).getDate("ZCCZSJ");
			if (!fmhjgs.isEmpty()) {
				MboRemote fmhjg = fmhjgs.getMbo(0);
				Date bdsj = fmhjg.getDate("BDDATE");
				Double yjg = Double.valueOf(fmhjg.getDouble("YJG"));
				String status = fmhjg.getString("STATUS");
				Double xjg = Double.valueOf(fmhjg.getDouble("XJG"));
				System.out.println(yjg + "++++++++++++==");
				float sjc = cjsh.getTime() - bdsj.getTime();

				if (sjc >= 0.0F) {
					if (status.equals("已批准")) {
						fmhmbo.setValue("UNITCOST", xjg.doubleValue(), 11L);
					} else {
						fmhmbo.setValue("UNITCOST", yjg.doubleValue(), 11L);
					}
				} else {
					fmhmbo.setValue("UNITCOST", yjg.doubleValue(), 11L);
				}
			}
		}
		Double dj = fmhmbo.getDouble("UNITCOST");
		if (!(dj.equals("") || dj.equals(null))) {
			fmhmbo.setValue("ZJE", dj * fmhmbo.getDouble("JZ"), 11L);
			if (FMHXSJLMX.count() > 0) {
				FMHXSJLMX.getMbo(0).setValue("DJ", dj);
			}
		}
		Date cdate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(cdate);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 2);
		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		System.out.println(dayBefore + "#############" + "+++++++++++++++++++++++");
		save();
		return 1;
	}

	public int xgch() throws MXException, RemoteException {

		String personid = this.sessionContext.getUserInfo().getPersonId();
		Date xtsj = new Date();
		MboRemote fmhmbo = getMbo();
		String num = fmhmbo.getString("FMHXSNUM");
		String status = fmhmbo.getString("STATUS");
		String ch = fmhmbo.getString("CH");

		MboSetRemote xgchjl = fmhmbo.getMboSet("FMHXSXGCHLL");
		if (status.equals("计量空车重量")) {
			MboRemote xgchjlMbo = xgchjl.add();
			xgchjlMbo.setValue("CH", ch, 7L);
			xgchjlMbo.setValue("PERSONID", personid, 7L);
			xgchjlMbo.setValue("XGSJ", xtsj, 7L);
			xgchjlMbo.setValue("FMHXSNUM", num, 7L);

			fmhmbo.setFieldFlag("CH", 7l, false);
		} else {
			Utility.showMessageBox(this.clientSession.getCurrentEvent(), "提示", " 您没有执行此操作的权限！ ", 2);
		}
		super.SAVE();
		return 1;
	}
}