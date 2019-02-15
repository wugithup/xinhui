package com.shuto.mam.webclient.beans.wo.woticket;

import java.rmi.RemoteException;
import java.util.ArrayList;

import psdi.mbo.MboData;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * com.shuto.mam.webclient.beans.wo.woticket.ajhxmDateBean 安健环技术交底检查卡 删除时排序问题
 * 
 * @author shanbh 2016-6-21
 */
public class ajhxmDateBean extends DataBean {

	public int toggledeleterow() throws MXException {
		WebClientEvent event = this.clientSession.getCurrentEvent();
		try {
			// 获得当前行
			int row = getRowIndexFromEvent(event);
			// 获得集合
			MboData md = (MboData) this.tableData.get(getCacheRowIndex(row));
			// 如果将该行删除
			if (md.toBeDeleted()) {
				// 获得表集合
				MboSetRemote steps = getMboSet();
				MboRemote mbo = null;
				// 按序号排序
				steps.setOrderBy("SN");
				// 便利集合
				for (int i = getCurrentRow() + 1; i < steps.count(); i++) {
					// 得到每条数据的MBO
					mbo = steps.getMbo(i);
					// 给序号赋值
					mbo.setValue("SN", i + 1);
				}
				undelete(row);
			} else if (saveYesNoCheck()) {
				setRemoveOnCancel(row);
				setNewRowUnedited(true);
				this.tableStateFlags.setFlag(64L, true);
				// 获得表集合
				MboSetRemote steps = getMboSet();
				MboRemote mbo = null;
				// 按序号排序
				steps.setOrderBy("SN");
				// 便利集合
				for (int i = 0; i < steps.count(); i++) {
					// 得到每条数据的MBO
					mbo = steps.getMbo(i);
					// 如果集合中的序号大于当前选中行
					if (mbo.getInt("SN") > getCurrentRow() + 1)
						// 序号赋值
						mbo.setValue("SN", mbo.getInt("SN") - 1);
				}
				// 删除
				delete(row);
				// 保存
				save();
			}

			refreshTable();
		} catch (RemoteException e) {
			handleRemoteException(e);
		}
		return 1;
	}

	public boolean saveYesNoCheck() throws MXException {
		// 用于得到当前页面的事件
		WebClientEvent event = this.clientSession.getCurrentEvent();
		// 获取返回值点确认返回8，点取消返回16
		int msgRet = event.getMessageReturn();
		if (msgRet < 0)
			// 弹出继续执行前是否要保存变更？BMXAA2300I
			throw new MXApplicationYesNoCancelException("savecontinueid",
					"jspmessages", "savecontinue");
		if (msgRet == 8) {
			return true;
		}
		return msgRet != 16;
	}

}
