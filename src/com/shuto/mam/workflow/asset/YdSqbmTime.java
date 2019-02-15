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
 * com.shuto.mam.workflow.asset.YdSqbmTime 申请部门会签插入时间
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年5月12日 下午10:29:13
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class YdSqbmTime implements ActionCustomClass {

	@Override
	public void applyCustomAction(MboRemote mainmbo, Object[] aobj)
			throws MXException, RemoteException {

		String personid = mainmbo.getUserInfo().getPersonId();
		String ASSET_YDNUM = mainmbo.getString("ASSET_YDNUM");
		Date date = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = dateformat.format(date);
		MboSetRemote asset_ydLineSet = mainmbo.getMboSet("$ASSET_YDLINE",
				"ASSET_YDLINE", "type='申请部门会签' and ASSET_YDNUM='"
						+ ASSET_YDNUM + "' and personid='" + personid + "' ");
		if (!asset_ydLineSet.isEmpty()) {
			MboRemote mbo = asset_ydLineSet.getMbo(0);
			mbo.setValue("SPTIME", time, 11L);
			String spyj = mbo.getString("spyj");
			if (spyj == null || spyj.length() < 1) {
				mbo.setValue("spyj", "同意", 11L);
			}
		}
		asset_ydLineSet.close();
	}

}