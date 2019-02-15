package com.shuto.mam.app.receapply;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author SumMer
 * @Title: FldZdType
 * @ProjectName xheam
 * @Description:
 * @date 2019-01-14 01419:13
 */

public class FldZdType extends MAXTableDomain {

    public FldZdType(MboValue mbv) {

        super(mbv);
        setRelationship("JDBZ", "1=1");
        setListCriteria("1=1");
        String[] target = {this.getMboValue().getName()};
        String[] source = {"VALUE"};
        setLookupKeyMapInOrder(target, source);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {

        //获取字段类所绑定的字段
        String attr = this.getMboValue().getName();
        MboRemote mbo = this.getMboValue().getMbo();
        String sql = null;
        if ("ZDTYPE".equalsIgnoreCase(attr)) {
            sql = "KEY=0";
        } else if ("FYBZ".equalsIgnoreCase(attr)) {
            String zdtype = mbo.getString("ZDTYPE");
            sql = "KEY=(SELECT ZDBZID FROM JDBZ WHERE VALUE = '" + zdtype + "')";
        }
        MboSetRemote msr = mbo.getMboSet("$JDBZ" + attr, "JDBZ", sql);
        msr.reset();
        return msr;

    }
}
