package com.shuto.mam.app.asset.assettfy;

/**  
 com.shuto.mam.app.asset.assettfy.FldTfyStatus 设备停复役
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年5月11日 上午11:25:02
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class FldTfyStatus extends MboValueAdapter {
	public FldTfyStatus(MboValue paramMboValue) throws MXException {
		super(paramMboValue);
	}

	public void init() throws MXException, RemoteException {
		String str1 = getMboValue().getString();
		Mbo localMbo = getMboValue().getMbo();
		String str2 = getMboValue("siteid").getString();
		if ("温州电厂".equals(str2)) {
			String[] arrayOfString1 = { "DESCRIPTION", "TFYTYPE", "LOCATION",
					"ASSETNUM", "SQUNIT", "RESPONSE_PERSON", "SQUNIT_FZR",
					"JSZCB_ZY", "WORKCONTENT", "AQCS", "JHJXSTARTDATE",
					"JHJXENDDATE", "RUNMODE", "CONTROLLER_01",
					"DDJPZ_STARTDATE", "DDJPZ_STOPDATE", "CONTROLLER_03",
					"REAL_STARTDATE", "CONTROLLER_04", "YQDATE",
					"CONTROLLER_05", "REAL_STOPDATE" };

			String[] arrayOfString2 = { "CONTROLLER_01", "DDJPZ_STARTDATE",
					"DDJPZ_STOPDATE", "CONTROLLER_03", "REAL_STARTDATE",
					"CONTROLLER_04", "YQDATE", "CONTROLLER_05", "REAL_STOPDATE" };

			if ((!"WAPPR".equals(str1)) && (!"UCP".equals(str1))) {
				localMbo.setFieldFlag(arrayOfString1, 7L, true);
			}
			if ("FDSH".equals(str1)) {
				localMbo.setFieldFlag("RUNMODE", 7L, false);
			}
			if ("ZZSH".equals(str1)) {
				localMbo.setFieldFlag(arrayOfString2, 7L, false);
			}
		}
		super.init();
	}

	@Override
	public void action() throws MXException, RemoteException {
		MboRemote mainRemote = this.getMboValue().getMbo();
		String status = mainRemote.getString("STATUS");
		if (status.equals("退回修改")) {
			mainRemote.setValue("YXSPPERSON", "", 11L);
			mainRemote.setValue("YXSPTIME", "", 11L);
			mainRemote.setValue("JSBSPPERSON", "", 11L);
			mainRemote.setValue("JSBSPTIME", "", 11L);
			mainRemote.setValue("JSBLEADPERSON", "", 11L);
			mainRemote.setValue("JSBLEADTIME", "", 11L);
			mainRemote.setValue("FGLEADPERSON", "", 11L);
			mainRemote.setValue("FGLEADTIME", "", 11L);
			mainRemote.setValue("ZZSTOPPERSON", "", 11L);
			mainRemote.setValue("ZZSTOPYSTIME", "", 11L);
			mainRemote.setValue("ZZPZRECOVERPERSON", "", 11L);
			mainRemote.setValue("ZZPZRECOVERTIME", "", 11L);
		}else if(status.equals("已批准")||status.equals("已作废")){
			mainRemote.setFlag(7L, true);
		}
		
		super.action();
	}
}
