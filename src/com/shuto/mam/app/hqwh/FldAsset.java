package com.shuto.mam.app.hqwh;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author zhaowei
 * @email zhaowei@shuto.cn
 * @create 2018-01-23 16:07
 * @desc
 * @class com.shuto.mam.app.hqwh.FldAsset
 * @Copyright: 2018 Shuto版权所有
 **/

public class FldAsset  extends MAXTableDomain {
    public FldAsset(MboValue mbv) {
        super(mbv);
        setRelationship("ASSET", "1=1");
        setListCriteria("1=1");
        String[] target = { this.getMboValue().getName() };
        String[] source = { "ASSETNUM" };
        setLookupKeyMapInOrder(target, source);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        MboRemote mainMbo = this.getMboValue().getMbo();
        String siteid = mainMbo.getString("SITEID");
        setListCriteria(" siteid = '" + siteid + "' and bm='ZHB'");
        return super.getList();
    }

    @Override
    public void action() throws MXException, RemoteException {
        super.action();
        MboRemote zmbo = getMboValue().getMbo();
        String siteid = getMboValue("SITEID").getString();
        String assetnum = getMboValue("ASSETNUM").getString();// 位置
        MboSetRemote assetSet = zmbo.getMboSet("$ASSET", "ASSET", "ASSETNUM = '" + assetnum + "' AND siteid = '" + siteid + "'");
        if (!assetSet.isEmpty()) {
            zmbo.setValue("LOCATION", assetSet.getMbo(0).getString("LOCATION"), 11l);
            assetSet.close();
        } else {
            zmbo.setValueNull("LOCATION", 11L);
        }
    }
}
