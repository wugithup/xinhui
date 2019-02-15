package com.shuto.mam.webclient.beans.bzlog;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXAccessException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import java.rmi.RemoteException;

/**
 * @author wuchang
 * @version V1.0
 * @Title: BzLogDataBean
 * @Package com.shuto.mam.webclient.beans.bzlog
 * @date 2019/1/13 13:33
 */
public class BzLogDataBean extends DataBean {

    @Override
    public int toggledeleterow() throws MXException {
        int row = this.getCurrentRow();
        MboSetRemote bzLogSet = null;


        // 获得表集合
        try {
            bzLogSet = getMboSet();

            //当前登陆用户
            String userName = bzLogSet.getOwner().getUserName();

            if (bzLogSet.getMbo(0)!=null)
            {
                MboRemote bzLogMbo = bzLogSet.getMbo(row);
                String name = bzLogMbo.getString("CREATEBY");
                if (!userName.equals(name))
                {
                    throw new MXAccessException("BZLOG","errordelete");
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return super.toggledeleterow();
    }
}
