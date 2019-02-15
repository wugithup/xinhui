package com.shuto.mam.webclient.beans.asset.assetyd;

import com.shuto.mam.webclient.beans.base.CAppBean;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * com.shuto.mam.webclient.beans.asset.assetyd.SbydAppBean SB_YD设备异动类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年5月22日 下午8:30:58
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class SbydAppBean extends CAppBean {

	public int ROUTEWF() throws MXException, RemoteException {
		if (!hasAuth()) {
			throw new MXApplicationException("system", "noauth");
		}
		MboRemote mainmbo = getMbo();
		String status = mainmbo.getString("status");
		if (status.equalsIgnoreCase("待申请部门专业会签")) {
			String personid = mainmbo.getUserName();
			String ASSET_YDNUM = mainmbo.getString("ASSET_YDNUM");
			Date date = new Date();
			SimpleDateFormat dateformat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String time = dateformat.format(date);
			MboSetRemote asset_ydLineSet = mainmbo.getMboSet("$ASSET_YDLINE",
					"ASSET_YDLINE", "type='申请专业会签'  and ASSET_YDNUM='"
							+ ASSET_YDNUM + "' and personid='" + personid
							+ "' ");
			if (!asset_ydLineSet.isEmpty()) {
				MboRemote mbo = asset_ydLineSet.getMbo(0);
				mbo.setValue("SPTIME", time, 11L);
			}
		} else if (status.equalsIgnoreCase("待部门会签")) {
			String personid = mainmbo.getUserName();
			String ASSET_YDNUM = mainmbo.getString("ASSET_YDNUM");
			Date date = new Date();
			SimpleDateFormat dateformat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String time = dateformat.format(date);
			MboSetRemote asset_ydLineSet = mainmbo.getMboSet("$ASSET_YDLINE",
					"ASSET_YDLINE", "type='申请部门会签'  and ASSET_YDNUM='"
							+ ASSET_YDNUM + "' and personid='" + personid
							+ "' ");
			if (!asset_ydLineSet.isEmpty()) {
				MboRemote mbo = asset_ydLineSet.getMbo(0);
				mbo.setValue("SPTIME", time, 11L);
			}
		} else if (status.equalsIgnoreCase("待执行部门专业会签")) {
			String personid = mainmbo.getUserName();
			String ASSET_YDNUM = mainmbo.getString("ASSET_YDNUM");
			Date date = new Date();
			SimpleDateFormat dateformat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String time = dateformat.format(date);
			MboSetRemote asset_ydLineSet = mainmbo.getMboSet("$ASSET_YDLINE",
					"ASSET_YDLINE", "type='竣工专业会签'  and ASSET_YDNUM='"
							+ ASSET_YDNUM + "' and personid='" + personid
							+ "' ");
			if (!asset_ydLineSet.isEmpty()) {
				MboRemote mbo = asset_ydLineSet.getMbo(0);
				mbo.setValue("SPTIME", time, 11L);
			}
		} else if (status.equalsIgnoreCase("待执行部门部门会签")) {
			String personid = mainmbo.getUserName();
			String ASSET_YDNUM = mainmbo.getString("ASSET_YDNUM");
			Date date = new Date();
			SimpleDateFormat dateformat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String time = dateformat.format(date);
			MboSetRemote asset_ydLineSet = mainmbo.getMboSet("$ASSET_YDLINE",
					"ASSET_YDLINE", "type='竣工部门会签'  and ASSET_YDNUM='"
							+ ASSET_YDNUM + "' and personid='" + personid
							+ "' ");
			if (!asset_ydLineSet.isEmpty()) {
				MboRemote mbo = asset_ydLineSet.getMbo(0);
				mbo.setValue("SPTIME", time, 11L);
			}
		}

		return super.ROUTEWF();
	}

	public int DUPLICATE() throws MXException, RemoteException {
		int i = super.DUPLICATE();
		MboRemote mbo = this.app.getAppBean().getMbo();
		mbo.setValue("XMNUM", "", 11L);
		mbo.setValue("SQ_JSBZG", "", 11L);
		mbo.setValue("SQ_JSBBZ", "", 11L);
		mbo.setValue("SQ_SCFZ", "", 11L);
		mbo.setValue("SQ_JSBZGSJ", "", 11L);
		mbo.setValue("SQ_JSBBZSJ", "", 11L);
		mbo.setValue("SQ_SCFZSJ", "", 11L);
		mbo.setValue("SQ_JSBZGYJ", "", 11L);
		mbo.setValue("SQ_JSBBZYJ", "", 11L);
		mbo.setValue("SQ_SCFZYJ", "", 11L);
		mbo.setValue("STARTDATE", "", 11L);
		mbo.setValue("ENDDATE", "", 11L);
		mbo.setValue("status", "新建", 11L);
		this.app.getAppBean().refreshTable();
		this.app.getAppBean().reloadTable();
		return i;
	}

}
