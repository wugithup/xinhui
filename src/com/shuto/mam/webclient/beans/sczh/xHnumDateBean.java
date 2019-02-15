package com.shuto.mam.webclient.beans.sczh;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import java.rmi.RemoteException;

/**
 * @Auther: NieMingKun
 * @Date: 2018/10/26 11:37
 * @Description:
 */
public class xHnumDateBean  extends DataBean {

	
    public int addrow() throws MXException {
        super.addrow();
        try {
            getMbo().setValue("XH", getMboSet().count());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
