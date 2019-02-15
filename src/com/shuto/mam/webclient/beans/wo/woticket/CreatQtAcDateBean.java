package com.shuto.mam.webclient.beans.wo.woticket;

import java.rmi.RemoteException;
import java.util.ArrayList;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * 工作票 其他安错 选择框
 * 
 * @author shanbh
 * @date 2015-11-6下午07:43:10
 */
public class CreatQtAcDateBean extends DataBean {
	protected MboSetRemote getMboSetRemote() throws MXException, RemoteException {
		// 得到当前的对象的mbo
		MboRemote mbo = this.app.getDataBean().getMbo();

		String siteid = mbo.getString("siteid");
		String wonum = mbo.getString("wonum");

		MboSetRemote alndomains = mbo.getMboSet("$LSAC", "lsac", " siteid = '" + siteid
				+ "'  and   description not in  (select description from SUPPLEMENT  where wonum ='" + wonum + "')");
		return alndomains;
	}

	/**
	 * <p>
	 * 并将勾选工单带回，然后将数据保存。
	 * 
	 * @return int 返回int类型值
	 * @throws MXException
	 * @throws RemoteException
	 */

	public int execute() throws MXException, RemoteException {
		super.execute();
		// 当前mbo
		MboRemote mbo = this.app.getDataBean().getMbo();
		// 获得当前dialog中的结果集
		MboSetRemote selectLines = getMboSet();
		// 获得dialog中选中的数据的结果集
		selectLines.resetWithSelection();

		// 得到选中集合
		MboRemote sqsonmbo = null;
		ArrayList<MboRemote> wonumlist = new ArrayList<MboRemote>();

		int selectnum = selectLines.count();
		for (int i = 0; i < selectnum; i++) {

			sqsonmbo = selectLines.getMbo(i);
			wonumlist.add(sqsonmbo);
		}
		MboRemote sqsonmbowo = null;
		if (wonumlist.size() > 0) {
			for (int j = 0; j < wonumlist.size(); j++) {
				sqsonmbowo = wonumlist.get(j);
				String sn = "";
				CreatePlans(sqsonmbowo, sn);
			}
		}
		selectLines.save();
		app.getAppBean().save();
		return 1;
	}

	private void CreatePlans(MboRemote sqsonmbowo, String sn) throws RemoteException, MXException {
		MboRemote projectsq = this.app.getDataBean().getMbo();
		String wonum = projectsq.getString("wonum");
		// 得到字表mboset
		MboSetRemote projectplanset = projectsq.getMboSet("SUPPLEMENT");
		MboRemote projectplan = null;

		if (sqsonmbowo != null) {
			projectplan = projectplanset.add();
			// 插入描述
			projectplan.setValue("description", sqsonmbowo.getString("description"), 11l);
			projectplan.setValue("wonum", wonum, 11l);
			projectplan.setValue("sn", projectplanset.count() + "", 11l);
			projectplanset.save();

		}

	}

}
