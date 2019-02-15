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
 * @create 2018-01-23 15:54
 * @desc 工作票批准人
 * @class com.shuto.mam.app.wo.workorder.FldPzPerson
 * @Copyright: 2018 Shuto版权所有
 **/

public class FldPzPerson extends MAXTableDomain {
    public FldPzPerson(MboValue mbv) {
        super(mbv);
        setRelationship("person", "1=1");
        String[] target = {getMboValue().getName()};
        String[] source = {"DISPLAYNAME"};
        setLookupKeyMapInOrder(target, source);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        String app = getMboValue().getMbo().getThisMboSet().getApp();
        MboRemote mainMbo = getMboValue().getMbo();
        String siteid = mainMbo.getString("SITEID");
        String wheresql = "profession Is Not Null And DEPARTMENT = 'XHDC1001' and locationsite = '" + siteid
                + "' and status = '活动'";
        setListCriteria(wheresql);
        return super.getList();
    }
}
