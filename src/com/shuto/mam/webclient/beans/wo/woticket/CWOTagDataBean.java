package com.shuto.mam.webclient.beans.wo.woticket;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import java.rmi.RemoteException;

/**
 * @author wuchang
 * @version V1.0
 * @Title: CWOTagDataBean
 * @Package com.shuto.mam.webclient.beans.wo.woticket
 * @Description: 用于添加安措信息
 * @date 2018/12/26 17:19
 */
public class CWOTagDataBean extends DataBean {
    @Override
    public int addrow() throws MXException {
        super.addrow();
        try {
            if (!getMbo().getOwner().getOwner().isZombie()) {
                String location = getMbo().getOwner().getOwner().getString("location");
                setValue("TAGOUTLOCATION", location);
            }

        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

        return 1;
    }

}
