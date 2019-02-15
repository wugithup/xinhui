package com.shuto.mam.workflow.asset;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

/**
 * com.shuto.mam.workflow.asset.YdScbh 设备异动生成编号
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年5月12日 上午10:34:01
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class YdScbh implements ActionCustomClass {

	@Override
	public void applyCustomAction(MboRemote mainmbo, Object[] aobj)
			throws MXException, RemoteException {
		String orgid = mainmbo.getString("orgid");
		String siteid = mainmbo.getString("siteid");
		String PROFESSION = mainmbo.getString("PROFESSION");
		String XMNUM = mainmbo.getString("XMNUM");
		MboSetRemote professionSet = mainmbo.getMboSet("$profession",
				"profession", " orgid='" + orgid + "' and siteid='" + siteid
						+ "' and professionnum='"+PROFESSION+"'");
		String projc= professionSet.getMbo(0).getString("PROFESSIONABBR");
		professionSet.close();

		Date date = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy");
		String time = dateformat.format(date);
		NumberFormat num = new DecimalFormat("0000");// 流水号格式化
		if (XMNUM.isEmpty()) {
			MboSetRemote line = mainmbo.getMboSet("$bhwh", "bhwh", "  orgid='"
					+ orgid + "'  and  siteid='" + siteid
					+ "'  and  PROFESSIONNUM ='" + PROFESSION + "' and  year='"
					+ time + "'");
			if (line.isEmpty()) {
				MboRemote mboRemote = line.addAtEnd();
				mboRemote.setValue("orgid", orgid, 11l);
				mboRemote.setValue("siteid", siteid, 11l);
				mboRemote.setValue("PROFESSIONNUM", PROFESSION, 11l);
				mboRemote.setValue("year", time, 11l);
				mboRemote.setValue("num", "0001", 11l);
				mboRemote.setValue("appname", "SB_YD", 11l);
				mainmbo.setValue("xmnum", orgid + "-" + siteid + "-"
						+ projc + "-" + time + "-" + "0001", 11l);
			} else {
				MboRemote mboRemote = line.getMbo(0);
				String no = line.getMbo(0).getString("num");
				String newno = no.substring(no.length() - 4, no.length());//
				int sum = Integer.parseInt(newno);//
				sum = sum + 1;
				mainmbo.setValue("xmnum", orgid + "-" + siteid + "-"
						+ projc + "-" + time + "-" + num.format(sum), 11l);
				mboRemote.setValue("num", num.format(sum), 11l);
			}
			line.close();
		}

	}

}