package com.shuto.mam.webclient.beans.workorder;

import java.rmi.RemoteException;
import java.util.Vector;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * com.shuto.mam.webclient.beans.workorder.SelectZxPersonDataBean 华东大区 阜阳项目
 * 
 * @author shanbh shanbh@shuoto.cn工作票动火票动火执行人选择
 * @date 2017年4月27日 下午3:56:43
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class SelectZxPersonDataBean extends DataBean {

	public MboSetRemote getMboSetRemote() throws RemoteException, MXException {

		MboSetRemote retpersons = super.getMboSetRemote();
		String WONUM = this.app.getAppBean().getMbo().getString("WONUM");

		retpersons
				.setWhere("ismb='1'  	and bh  not in  (select  bh from  ZXRZLK where  ismb=0 and  wonum='"
						+ WONUM + "') ");
		return retpersons;
	}

	public int execute() throws MXException, RemoteException {
		super.execute();

		Vector selecteLines = getMboSet().getSelection();
		String wonum = this.app.getAppBean().getMbo().getString("wonum");// 工单编号
		for (int i = 0; i < selecteLines.size(); i++) {
			MboRemote PERSONGROUPTEAM = (MboRemote) selecteLines.elementAt(i);

			MboSetRemote COMPAYZBSetRemote = app.getAppBean().getMbo()
					.getMboSet("ZXRZLK");
			MboRemote COMPAYZBRemote = COMPAYZBSetRemote.addAtEnd();
			COMPAYZBRemote.setValue("zxr", PERSONGROUPTEAM.getString("zxr"),
					11L);
			COMPAYZBRemote.setValue("bh", PERSONGROUPTEAM.getString("bh"), 11L);
			COMPAYZBRemote.setValue("ismb", "0", 11L);
			COMPAYZBRemote.setValue("wonum", wonum, 11L);
		}
		app.getAppBean().reloadTable();
		app.getAppBean().refreshTable();

		return 1;
	}
}