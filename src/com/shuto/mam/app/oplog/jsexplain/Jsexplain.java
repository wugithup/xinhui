package com.shuto.mam.app.oplog.jsexplain;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;

/**
 * 
 com.shuto.mam.app.oplog.jsexplain.Jsexplain 河北分公司（曹妃甸）
 * 
 * @author songdd songdd@shuoto.cn
 * @date 2017年5月5日 上午11:05:18
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */

public class Jsexplain extends Mbo implements JsexplainRemote {
	public Jsexplain(MboSet ms) throws RemoteException {
		super(ms);
	}

	public void init() throws MXException {
		super.init();
		String status = getMboValue("STATUS").getString();

		if (!(status.equalsIgnoreCase("已关闭")))
			return;
		setFieldFlag("DESCRIPTION", 7L, true);
		setFieldFlag("JDNEIRONG", 7L, true);
		setFieldFlag("YXDATE", 7L, true);
		setFieldFlag("DEPARTMENT", 7L, true);
		setFieldFlag("TYPE", 7L, true);
		setFieldFlag("ZHUANGYE", 7L, true);
	}

	public void save() {
		try {
			super.save();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}
	}
}