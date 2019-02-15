package com.shuto.mam.app.wo.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.wo.workorder.FldBzWonum 工作票选择标准工作票 根据票种
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年7月6日 下午10:10:18
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldBzWonum extends MAXTableDomain {
	public FldBzWonum(MboValue mbv) {
		super(mbv);
		setRelationship("workorder", "");
		setLookupKeyMapInOrder(new String[] { "bzwonum" },
				new String[] { "wonum" });
	}

	public MboSetRemote getList() throws MXException, RemoteException {

		String worktype = getMboValue("S_ORDERTYPE").getString();
		String siteid = getMboValue("siteid").getString();
		String location = getMboValue("location").getString();// location='" +
																// location + "'
																// and
		setListCriteria(" siteid = '" + siteid + "' and  S_ORDERTYPE = '"
				+ worktype + "' and worktype = '标准工作票' ");
		MboSetRemote listset = super.getList();
		return listset;
	}

	/**
	 * 将标准工作票信息写入到工作票中
	 */
	public void action() throws MXException, RemoteException {

		super.action();

		MboRemote mainMbo = this.getMboValue().getMbo();
		String bzwonum = mainMbo.getString("bzwonum");
		if ("".equals(bzwonum)) {
			return;
		}

		// 获取标准工作票信息
		MboSetRemote oldWos = mainMbo.getMboSet("bzwonum");
		if (oldWos.isEmpty()) {
			return;
		}
		// 插入主表信息
		MboRemote oldWo = oldWos.getMbo(0);
		mainMbo.setValue("LOCATION", oldWo.getString("LOCATION"));//设备编码
		mainMbo.setValue("OPLOG_DELAYCAUSE", oldWo.getString("OPLOG_DELAYCAUSE"));//设备名称
		mainMbo.setValue("DESCRIPTION", oldWo.getString("DESCRIPTION"));//工作内容
		mainMbo.setValue("WORKSITE", oldWo.getString("WORKSITE"));//工作地点

		insertWoSafetyLink(oldWo);
		System.out.println("调用完毕！");
	}

	/**
	 * 插入隔离点数据
	 * 
	 * @param oldMbo
	 *            标准工作票的Mbo
	 * @throws MXException
	 * @throws RemoteException
	 */
	public void insertWoSafetyLink(MboRemote oldMbo) throws RemoteException,
			MXException {

		MboRemote mainMbo = this.getMboValue().getMbo();// 获取当前工作票的Mbo
		MboSetRemote woMboSet = oldMbo.getMboSet("WOSAFETYLINK");
		MboRemote newMbo = null;
		if(!woMboSet.isEmpty()){
			for(int i = 0; i < woMboSet.count(); i++){
				newMbo = woMboSet.getMbo(i).copy();
				newMbo.setValue("WONUM", mainMbo.getString("WONUM"),11L);
				newMbo.getThisMboSet().save();
			}
			newMbo.getThisMboSet().save();
		}
		
		

	}


}