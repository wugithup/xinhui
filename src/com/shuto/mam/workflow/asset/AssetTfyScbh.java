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
 * com.shuto.mam.workflow.asset.AssetTfyScbh 设备停复役编号生成
 * 
 * @author zhaowei zhaowei@shuoto.cn
 * @date 2017年5月12日 下午9:33:31
 * @Copyright: 2017 Shuto版权所有1111111
 * @version V1.0
 */
public class AssetTfyScbh implements ActionCustomClass {

	@Override
	public void applyCustomAction(MboRemote mainMbo, Object[] arg1) throws MXException, RemoteException {
		String orgid = mainMbo.getString("orgid");
		String siteid = mainMbo.getString("siteid");
		String profession = mainMbo.getString("PROFESSIONNUM");
		String assetType = mainMbo.getString("S_ASSETTYPE");
		String asset_tfynum = mainMbo.getString("ASSET_TFYNUM");
		if (assetType.equals("调度管辖设备")) {
			assetType = "WD";
		} else if (assetType.equals("值长管辖设备")) {
			assetType = "ZC";
		}
		Date date = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy");
		SimpleDateFormat dateformat2 = new SimpleDateFormat("MM");
		String year = dateformat.format(date);
		String month = dateformat2.format(date);
		NumberFormat num = new DecimalFormat("0000");
		if (asset_tfynum.isEmpty()) {
			MboSetRemote line = mainMbo.getMboSet("$bhwh", "bhwh",
					"  orgid='" + orgid + "'  and  siteid='" + siteid + "'  and  PROFESSIONNUM ='" + profession
							+ "' and  year='" + year + "' and month='" + month + "' and type='" + assetType + "'");
			if (line.isEmpty()) {
				MboRemote mboRemote = line.addAtEnd();
				mboRemote.setValue("orgid", orgid, 11l);
				mboRemote.setValue("siteid", siteid, 11l);
				mboRemote.setValue("PROFESSIONNUM", profession, 11l);
				mboRemote.setValue("year", year, 11l);
				mboRemote.setValue("month", month, 11l);
				mboRemote.setValue("type", assetType, 11l);
				mboRemote.setValue("num", "0001", 11l);
				mboRemote.setValue("appname", "ASSET_TFY", 11l);
				mainMbo.setValue("ASSET_TFYNUM", orgid + "-" + siteid + "-" + profession + "-" + assetType + "-" + year
						+ "-" + month + "-" + "0001", 11L);
			} else {
				MboRemote mboRemote = line.getMbo(0);
				String no = line.getMbo(0).getString("num");
				String newno = no.substring(no.length() - 4, no.length());
				int sum = Integer.parseInt(newno);
				sum = sum + 1;
				mainMbo.setValue("ASSET_TFYNUM", orgid + "-" + siteid + "-" + profession + "-" + assetType + "-" + year
						+ "-" + month + "-" + num.format(sum), 11L);
				mboRemote.setValue("num", num.format(sum), 11l);
			}
			line.close();
		}
	}

}
