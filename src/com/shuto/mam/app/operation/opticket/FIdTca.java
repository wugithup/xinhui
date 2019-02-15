package com.shuto.mam.app.operation.opticket;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author xiamy
 * @email xiamy@shuto.cn
 * @create 2017-10-27 9:46
 * @desc 登封操作票填充信息查询
 * @class com.shuto.mam.app.operation.opticket.FIdTca
 * @Copyright: 2017 Shuto版权所有
 **/

public class FIdTca extends MAXTableDomain {
    public FIdTca(MboValue mbv) {
        super(mbv);
        setRelationship("OPTICKETLINE", "1=1");
        String[] target = {getMboValue().getName(), "TCB"};
        String[] source = {"OPTICKETPROJECT", "YUKONG"};
        setLookupKeyMapInOrder(target, source);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        MboRemote mainMbo = getMboValue().getMbo();
        String siteid = mainMbo.getInsertSite();
        setListCriteria(" opticketnum= 'TCXX' and siteid = '" + siteid + "'");
        return super.getList();
    }
}
