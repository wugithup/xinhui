package com.shuto.mam.webclient.beans.workorder;

import java.rmi.RemoteException;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

public class WxNumDataBean extends DataBean {
	public int addrow() throws MXException {
        super.addrow();
        try {
            getMbo().setValue("NUM", getMboSet().count());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 1;
    }

}
