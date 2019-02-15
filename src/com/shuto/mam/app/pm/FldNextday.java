package com.shuto.mam.app.pm;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author 作者 E-mail: huangpf@shuto.cn
 * @date 创建时间：2017年8月11日 下午8:53:37
 * @since
 * @version 1.0
 * @Copyright: 2017 Shuto版权所有
 */
public class FldNextday extends MboValueAdapter {

	public FldNextday(MboValue mbv) {
		super(mbv);
	}

	@Override
	public void action() throws MXException, RemoteException {
		super.action();
		MboRemote mainmbo = getMboValue().getMbo().getOwner();
		String type = mainmbo.getString("worktype");
		if (type != null && "运行定期工作".equalsIgnoreCase(type)) {
			Date nextdate = getMboValue().getDate();
			if (nextdate != null) {
				Date mainnextdate = mainmbo.getDate("nextdate");
				if (mainnextdate == null) {
					mainmbo.setValue("nextdate", nextdate, 11L);
				}
			}
		}
	}
}
