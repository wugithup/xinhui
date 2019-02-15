package com.shuto.mam.app.wo.hse;

import java.rmi.RemoteException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.wo.hse.FldAln2 工作票检修箱钥匙号 不可以输入汉子 只可以输入英文和数字
 * 
 * @author shanbh 2016-7-18
 */
public class FldAln2 extends MboValueAdapter {

	public FldAln2(MboValue mbv) {
		super(mbv);
	}

	public void validate() throws MXException, RemoteException {
		if (getMboValue().isNull())
			return;
		MboValue Aln2 = getMboValue("Aln2");
		if (!Aln2.isNull()) {
			String ysx = getMboValue("Aln2").getString();
			int count = 0;
			String regEx = "[\\u4e00-\\u9fa5]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(ysx);
			while (m.find()) {
				System.out.println("共有 " + count + "个 ");
				throw new MXApplicationException("workorder", "jxx");

			}

		}
	}

}
