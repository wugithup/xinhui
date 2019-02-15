package com.shuto.mam.app.wo.workorder;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author wuchang
 * @version V1.0
 * @Title: FldKeyboxNum
 * @Package com.shuto.mam.app.wo.workorder
 * @date 2019/1/13 9:55
 */
public class FldKeyboxNum extends MAXTableDomain {

    public FldKeyboxNum(MboValue mbv) {
        super(mbv);
        this.setRelationship("KEYBOXNUM", "");
        String[] target = new String[]{this.getMboValue().getName()};
        String[] source = new String[]{"KEYBOXNUM"};
        this.setLookupKeyMapInOrder(target, source);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        this.setListCriteria("KEYBOXNUM NOT IN (SELECT BOXCODE FROM WORKORDER WHERE STATUS='已许可' AND BOXCODE IS NOT NULL AND BOXCODE <> '无' )");
        this.setListOrderBy("KEYBOXNUM");
        return super.getList();
    }

}
