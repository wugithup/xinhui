/**  
 * <p>  工作票中选择工作成员按钮弹出框的绑定类
 * @author       Lilf  lilf@shuto.cn
 * @date         2012-10-11 下午03:17:49
 * 
 * @Copyright:   2012 Shuto版权所有
 * @version      V1.0  
 */
package com.shuto.mam.webclient.beans.wo.woticket;

import java.rmi.RemoteException;
import java.util.Vector;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * <p>
 * 此类工作票中选择工作成员dialog的绑定类，实现在弹出框里复选人员后，把选定的人员复制到teamcy表里并赋上相关信息
 * 
 * @author Lilf lilf@shuto.cn
 * @Date 2012-10-11 下午03:17:49
 * 
 * @version Ver 1.1
 * @since CodingExample Ver 1.1
 */
public class SelectPersonDataBean extends DataBean {
	/**
	 * <p>
	 * 此方法是构造方法
	 * 
	 * @return MboSetRemote 返回 MboSetRemote类型值
	 * @throws RemoteException
	 * @throws MXException
	 */
	public MboSetRemote getMboSetRemote() throws RemoteException, MXException {
		super.getMboSetRemote();
		MboRemote Mbo = this.app.getAppBean().getMbo();
		String teamname = Mbo.getString("TEAMNUM");
		String siteid = Mbo.getString("siteid");
		MboSetRemote personset = Mbo.getMboSet("$person", "person",
				" banzu  is  not null  and LOCATIONSITE = '" + siteid
						+ "' and STATUS='活动' ");
		personset.setOrderBy("DEPARTMENT,banzu,PROFESSION");
		return personset;
	}

	/**
	 * <p>
	 * 确定时触发，把选择的人员加到teamcy表中并写入相关信息
	 * 
	 * @return int 返回 int类型值
	 * @throws MXException
	 * @throws RemoteException
	 */
	public int execute() throws MXException, RemoteException {
		super.execute();
		// 复选中的信息集合
		Vector selecteLines = getMboSet().getSelection();
		// 取到工作票wonum
		String wonum = this.app.getAppBean().getMbo().getString("wonum");
		// 遍历选中集合
		for (int i = 0; i < selecteLines.size(); i++) {
			MboRemote persongroupteam = (MboRemote) selecteLines.elementAt(i);
			// 取到teamcy表集合
			MboSetRemote teamcySetRemote = app.getAppBean().getMbo()
					.getMboSet("TEAMCY");
			// 新增一条teamcy记录
			MboRemote teamcyRemote = teamcySetRemote.addAtEnd();
			// 写入人员id和工作票wonum
			teamcyRemote.setValue("woby",
					persongroupteam.getString("personid"), 11L);
			teamcyRemote.setValue("wonum", wonum, 11L);
			teamcyRemote.setValue("siteid", this.app.getAppBean().getMbo()
					.getString("siteid"));
			teamcyRemote.setValue("orgid", this.app.getAppBean().getMbo()
					.getString("orgid"));
		}
		// 刷新table
		app.getAppBean().reloadTable();
		app.getAppBean().refreshTable();

		return 1;
	}
}