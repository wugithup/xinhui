package com.shuto.mam.workflow.asset.assettfy;

/**  
 com.shuto.mam.workflow.asset.assettfy.AssetTfyScbh 设备停复役 编号生成
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年5月16日 上午10:11:22
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

import com.shuto.mam.app.expand.AutoDateSiteNum;

public class AssetTfyScbh implements ActionCustomClass {
	public void applyCustomAction(MboRemote mainMbo, Object[] arg1) throws MXException, RemoteException {
		String orgid = mainMbo.getString("orgid");
		String siteid = mainMbo.getString("siteid");
		String profession = mainMbo.getString("PROFESSIONNUM");
		String assetType = mainMbo.getString("S_ASSETTYPE");
		String asset_tfynum = mainMbo.getString("ASSET_TFYNUM");
		MboSetRemote professionSet = mainMbo.getMboSet("$profession", "profession",
				" orgid='" + orgid + "' and siteid='" + siteid + "' and professionnum='" + profession + "'");
		String projc = professionSet.getMbo(0).getString("PROFESSIONABBR");
		professionSet.close();
		if (assetType.equals("调度管辖设备"))
			assetType = "WD";
		else if (assetType.equals("值长管辖设备")) {
			assetType = "ZC";
		}
		Date date = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy");
		SimpleDateFormat dateformat2 = new SimpleDateFormat("MM");
		String year = dateformat.format(date);
		String month = dateformat2.format(date);
		if (asset_tfynum.isEmpty()) {
			NumberFormat num = new DecimalFormat("0000");
			AutoDateSiteNum autoDateSiteNum = new AutoDateSiteNum(mainMbo.getThisMboSet());
			int sum = autoDateSiteNum.getNextAutoDateSiteNum(orgid, siteid, "ASSET_TFY", profession, assetType);
			mainMbo.setValue("ASSET_TFYNUM",
					orgid + "-" + siteid + "-" + projc + "-" + assetType + "-" + year + month + num.format(sum), 11L);
		}

		// if (asset_tfynum.isEmpty()) {
		// MboSetRemote line = mainMbo.getMboSet("$bhwh", "bhwh", " orgid='" +
		// orgid + "' and siteid='" + siteid +
		// "' and PROFESSIONNUM ='" + profession + "' and year='" +
		// year + "' and month='" + month + "' and type='" + assetType + "'");
		// if (line.isEmpty()) {
		// MboRemote mboRemote = line.addAtEnd();
		// mboRemote.setValue("orgid", orgid, 11L);
		// mboRemote.setValue("siteid", siteid, 11L);
		// mboRemote.setValue("PROFESSIONNUM", profession, 11L);
		// mboRemote.setValue("year", year, 11L);
		// mboRemote.setValue("month", month, 11L);
		// mboRemote.setValue("type", assetType, 11L);
		// mboRemote.setValue("num", "0001", 11L);
		// mboRemote.setValue("appname", "ASSET_TFY", 11L);
		// mainMbo.setValue("ASSET_TFYNUM", orgid + "-" + siteid + "-" +
		// profession + "-" + assetType + "-" + year + "-" + month + "-" +
		// "0001", 11L);
		// } else {
		// MboRemote mboRemote = line.getMbo(0);
		// String no = line.getMbo(0).getString("num");
		// String newno = no.substring(no.length() - 4, no.length());
		// int sum = Integer.parseInt(newno);
		// sum++;
		// mainMbo.setValue("ASSET_TFYNUM", orgid + "-" + siteid + "-" +
		// profession + "-" + assetType + "-" + year + "-" + month + "-" +
		// num.format(sum), 11L);
		// mboRemote.setValue("num", num.format(sum), 11L);
		// }
		// line.close();
		// }
	}
}