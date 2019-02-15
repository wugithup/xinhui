package com.shuto.mam.webclient.beans.wo.woticket;

import java.rmi.RemoteException;

import psdi.mbo.MboData;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * @author SumMer
 */
public class WOTagOutDataBean extends DataBean {

	@Override
	public int addrow() throws MXException {
		super.addrow();
		try {
			getMbo().setValue("APPLYSEQ", getMboSet().count());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 1;
	}

	public int selall() throws RemoteException {
		try {
			MboRemote mbo = this.app.getAppBean().getMbo();
			MboSetRemote mbozb = null;
			MboRemote mbois = null;

			mbozb = mbo.getMboSet("WOSAFETYLINK001");
			int sum = 0;
			if (mbozb.count() > 0) {
				for (int i = 0; i < mbozb.count(); i++) {
					mbois = mbozb.getMbo(i);
					sum += mbois.getFloat("S_ISZX");
				}
				if (sum == mbozb.count()) {
					for (int i = 0; i < mbozb.count(); i++) {
						mbois = mbozb.getMbo(i);
						mbois.setValue("S_ISZX", 0, 2L);
						mbois.setValue("S_ISZX", false, 11L);

					}
				} else {
					for (int i = 0; i < mbozb.count(); i++) {
						mbois = mbozb.getMbo(i);
						mbois.setValue("S_ISZX", true, 11L);
					}
				}
			}
			this.app.getAppBean().reloadTable();
			this.app.getAppBean().refreshTable();
			super.save();
		} catch (MXException e) {
			e.printStackTrace();
		}
		return 1;
	}

	public int selall1() throws RemoteException {
		try {
			MboRemote mbo = this.app.getAppBean().getMbo();
			MboSetRemote mbozb = null;
			MboRemote mbois = null;
			int sum = 0;
			mbozb = mbo.getMboSet("WOSAFETYLINK001");

			if (mbozb.count() > 0) {
				for (int i = 0; i < mbozb.count(); i++) {
					mbois = mbozb.getMbo(i);
					sum += mbois.getFloat("S_ISHF");
				}
				if (sum == mbozb.count()) {
					for (int i = 0; i < mbozb.count(); i++) {
						mbois = mbozb.getMbo(i);
						mbois.setValue("S_ISHF", 0, 2L);
						mbois.setValue("S_ISHF", false, 11L);

					}
				} else {
					for (int i = 0; i < mbozb.count(); i++) {
						mbois = mbozb.getMbo(i);
						mbois.setValue("S_ISHF", true, 11L);
					}
				}
			}

			super.save();
			this.app.getAppBean().reloadTable();
			this.app.getAppBean().refreshTable();
		} catch (MXException e) {
			e.printStackTrace();
		}
		return 1;
	}

	public int selall2() throws RemoteException {
		try {
			MboRemote mbo = this.app.getAppBean().getMbo();
			MboSetRemote mbozb = null;
			MboRemote mbois = null;

			mbozb = mbo.getMboSet("WOSAFETYLINK002");
			int sum = 0;
			if (mbozb.count() > 0) {
				for (int i = 0; i < mbozb.count(); i++) {
					mbois = mbozb.getMbo(i);
					sum += mbois.getFloat("S_ISZX");
				}
				if (sum == mbozb.count()) {
					for (int i = 0; i < mbozb.count(); i++) {
						mbois = mbozb.getMbo(i);
						mbois.setValue("S_ISZX", 0, 2L);
						mbois.setValue("S_ISZX", false, 11L);

					}
				} else {
					for (int i = 0; i < mbozb.count(); i++) {
						mbois = mbozb.getMbo(i);
						mbois.setValue("S_ISZX", true, 11L);
					}
				}
			}
			this.app.getAppBean().reloadTable();
			this.app.getAppBean().refreshTable();
			super.save();
		} catch (MXException e) {
			e.printStackTrace();
		}
		return 1;
	}

	public int selall3() throws RemoteException {
		try {
			MboRemote mbo = this.app.getAppBean().getMbo();
			MboSetRemote mbozb = null;
			MboRemote mbois = null;
			int sum = 0;
			mbozb = mbo.getMboSet("WOSAFETYLINK002");

			if (mbozb.count() > 0) {
				for (int i = 0; i < mbozb.count(); i++) {
					mbois = mbozb.getMbo(i);
					sum += mbois.getFloat("S_ISHF");
				}
				if (sum == mbozb.count()) {
					for (int i = 0; i < mbozb.count(); i++) {
						mbois = mbozb.getMbo(i);
						mbois.setValue("S_ISHF", 0, 2L);
						mbois.setValue("S_ISHF", false, 11L);

					}
				} else {
					for (int i = 0; i < mbozb.count(); i++) {
						mbois = mbozb.getMbo(i);
						mbois.setValue("S_ISHF", true, 11L);
					}
				}
			}
			this.app.getAppBean().reloadTable();
			this.app.getAppBean().refreshTable();
			super.save();
		} catch (MXException e) {
			e.printStackTrace();
		}
		return 1;
	}

	@Override
    public int toggledeleterow() throws MXException {
        WebClientEvent event = this.clientSession.getCurrentEvent();
        try {
            int row = getRowIndexFromEvent(event);

            MboData md = (MboData) this.tableData.get(getCacheRowIndex(row));

            if (md.toBeDeleted()) {
                MboSetRemote steps = getMboSet();
                MboRemote mbo = null;

                steps.setOrderBy("APPLYSEQ");

                for (int i = getCurrentRow() + 1; i < steps.count(); i++) {
                    mbo = steps.getMbo(i);

                    mbo.setValue("APPLYSEQ", i + 1);
                }
                undelete(row);
            } else if (saveYesNoCheck()) {
                setRemoveOnCancel(row);
                setNewRowUnedited(true);
                this.tableStateFlags.setFlag(64L, true);

                MboSetRemote steps = getMboSet();
                MboRemote mbo = null;

                steps.setOrderBy("APPLYSEQ");

                for (int i = 0; i < steps.count(); i++) {
                    mbo = steps.getMbo(i);

                    if (mbo.getInt("APPLYSEQ") <= getCurrentRow() + 1)
                    {
                        continue;
                    }

                    mbo.setValue("APPLYSEQ", mbo.getInt("APPLYSEQ") - 1);
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