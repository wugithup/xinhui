package com.shuto.mam.app.pm;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author 作者 E-mail: xiamy@shuto.cn
 * @version 1.0
 * @date 创建时间：2017年7月4日 下午3:50:39
 * @Copyright: 2017 Shuto版权所有
 * @since
 */
public class FldPmAsset extends MAXTableDomain {

    public FldPmAsset(MboValue mbv) {

        super(mbv);
        setRelationship("ASSET", "1=1");
        setListCriteria("1=1");
        String[] target = {this.getMboValue().getName()};
        String[] source = {"ASSETNUM"};
        setLookupKeyMapInOrder(target, source);
    }

    public MboSetRemote getList() throws MXException, RemoteException {

        MboRemote mainMbo = this.getMboValue().getMbo();
        String siteid = mainMbo.getString("SITEID");
        setListCriteria(" siteid = '" + siteid + "'");
        return super.getList();
    }

    public void action() throws MXException, RemoteException {

        super.action();
    }

}
