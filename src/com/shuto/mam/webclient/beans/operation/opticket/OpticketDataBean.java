package com.shuto.mam.webclient.beans.operation.opticket;

import java.rmi.RemoteException;

import psdi.mbo.MboData;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * 
* @ClassName: OpticketDataBean 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author lull lull@shuto.cn
* @date 2017年6月26日 下午2:38:22 
*
 */
public class OpticketDataBean extends DataBean {
	String previousValue;

	public int addStepRow() throws MXException, RemoteException {
		System.out.println("============================");
		super.addrow();

		getMbo().setValue("ordernum", getMboSet().count());
		System.out.println("-------------------------");
		return 1;
	}

	public int addStepRowD() throws MXException, RemoteException {

		super.addrow();

		getMbo().setValue("ordernum", getMboSet().count());

		return 1;
	}

	public int selall() throws RemoteException {
		try {
			MboRemote mbo = this.app.getAppBean().getMbo();
			MboSetRemote mbozb = null;
			MboRemote mbois = null;

			mbozb = mbo.getMboSet("opticketnum_opticketline");

			if (mbozb.count() > 0) {
				for (int i = 0; i < mbozb.count(); i++) {
					mbois = mbozb.getMbo(i);

					mbois.setValue("isop", true, 11L);
				}
			}
			super.save();
		} catch (MXException e) {
			e.printStackTrace();
		}
		return 1;
	}

	public int insertstep() throws RemoteException, MXException {
		validate();
		try {
			MboSetRemote steps = getMboSet();

			steps.setOrderBy("ordernum");

			for (int i = 0; i < steps.count(); i++) {
				MboRemote mbo = steps.getMbo(i);

				if (mbo.getInt("ordernum") <= getCurrentRow())
					continue;
				mbo.setValue("ordernum", mbo.getInt("ordernum") + 1);
			}

			insert(getCurrentRow());

			steps.getMbo(this.currentRow).setValue("ordernum", this.currentRow + 1);
			setRemoveOnCancel(this.currentRow);
			setNewRowUnedited(true);
			this.tableStateFlags.setFlag(64L, true);

			refreshTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	public int insertstepD() throws RemoteException, MXException {
		validate();
		try {
			MboSetRemote steps = getMboSet();
			MboRemote mbo = null;

			steps.setOrderBy("ordernum");

			for (int i = 0; i < steps.count(); i++) {
				mbo = steps.getMbo(i);

				if (mbo.getInt("ordernum") <= getCurrentRow())
					continue;
				mbo.setValue("ordernum", mbo.getInt("ordernum") + 1);
			}

			insert(getCurrentRow());

			steps.getMbo(this.currentRow).setValue("ordernum", this.currentRow + 1);
			setRemoveOnCancel(this.currentRow);
			setNewRowUnedited(true);
			this.tableStateFlags.setFlag(64L, true);

			refreshTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	public int toggledeleterow() throws MXException {
		WebClientEvent event = this.clientSession.getCurrentEvent();
		try {
			int row = getRowIndexFromEvent(event);

			MboData md = (MboData) this.tableData.get(getCacheRowIndex(row));

			if (md.toBeDeleted()) {
				MboSetRemote steps = getMboSet();
				MboRemote mbo = null;

				steps.setOrderBy("ordernum");

				for (int i = getCurrentRow() + 1; i < steps.count(); i++) {
					mbo = steps.getMbo(i);

					mbo.setValue("ordernum", i + 1);
				}
				undelete(row);
			} else if (saveYesNoCheck()) {
				setRemoveOnCancel(row);
				setNewRowUnedited(true);
				this.tableStateFlags.setFlag(64L, true);

				MboSetRemote steps = getMboSet();
				MboRemote mbo = null;

				steps.setOrderBy("ordernum");

				for (int i = 0; i < steps.count(); i++) {
					mbo = steps.getMbo(i);

					if (mbo.getInt("ordernum") <= getCurrentRow() + 1)
						continue;
					mbo.setValue("ordernum", mbo.getInt("ordernum") - 1);
				}

				delete(row);

				save();
			}

			refreshTable();
		} catch (RemoteException e) {
			handleRemoteException(e);
		}
		return 1;
	}

	public boolean saveYesNoCheck() throws MXException {
		WebClientEvent event = this.clientSession.getCurrentEvent();

		int msgRet = event.getMessageReturn();
		if (msgRet < 0) {
			throw new MXApplicationYesNoCancelException("savecontinueid", "jspmessages", "savecontinue");
		}
		if (msgRet == 8) {
			return true;
		}
		return msgRet != 16;
	}
}