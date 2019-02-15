package com.shuto.mam.app.wo.workorder;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author xiamy
 * @email xiamy@shuto.cn
 * @create 2018-01-24 09:27
 * @desc 动火工作票合格范围
 * @class com.shuto.mam.app.wo.workorder.FldHgfw
 * @Copyright: 2018 Shuto版权所有
 **/

public class FldHgfw extends MAXTableDomain {
    public FldHgfw(MboValue mbv) {
        super(mbv);
        setRelationship("ALNDOMAIN", "");
        String[] target = {getMboValue().getName()};
        String[] source = {"DESCRIPTION"};
        setLookupKeyMapInOrder(target, source);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        MboRemote mainMbo = getMboValue().getMbo();
        String siteid = mainMbo.getString("SITEID");
        String wheresql = " DOMAINID = 'HGFW' and siteid ='" + siteid + "'";
        setListCriteria(wheresql);
        return super.getList();
    }
}
