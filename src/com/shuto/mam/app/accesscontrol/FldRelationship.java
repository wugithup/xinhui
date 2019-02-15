package com.shuto.mam.app.accesscontrol;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author xiamy
 * @email xiamy@shuto.cn
 * @create 2018-02-02 09:47
 * @desc 关联关系字段类
 * @class com.shuto.mam.app.accesscontrol.FldRelationship
 * @Copyright: 2018 Shuto版权所有
 **/

public class FldRelationship extends MAXTableDomain {
    public FldRelationship(MboValue mbv) {
        super(mbv);
        setRelationship("MAXRELATIONSHIP", "1=1");
        String[] strTo = {getMboValue().getAttributeName()};
        String[] strFrom = {"name"};
        setLookupKeyMapInOrder(strTo, strFrom);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        MboRemote mbo = getMboValue().getMbo();
        //获取流程名
        String accesscontrol = mbo.getString("PROCESSNAME");
        String sql = "Parent In (Select objectname From WFPROCESS Where enabled=1 And PROCESSNAME = '" + accesscontrol + "')";
        setListCriteria(sql);
        return super.getList();
    }

    @Override
    public void action() throws MXException, RemoteException {
        getMboValue("description").setValueNull();
        super.action();
    }
}
