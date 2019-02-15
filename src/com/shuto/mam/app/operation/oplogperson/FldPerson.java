package com.shuto.mam.app.operation.oplogperson;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author xiamy
 * @email xiamy@shuto.cn
 * @create 2017-11-03 22:26
 * @desc
 * @class com.shuto.mam.app.operation.oplogperson.FldPerson
 * @Copyright: 2017 Shuto版权所有
 **/

public class FldPerson extends MAXTableDomain {
    public FldPerson(MboValue mbv) {
        super(mbv);
        setRelationship("person", "");
        String[] strFrom = {"personid"};
        String[] strTo = {"personid"};
        setLookupKeyMapInOrder(strTo, strFrom);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        setListCriteria("locationsite = '" + getMboValue("siteid").getString() + "' and status = '活动'");
        return super.getList();
    }

    @Override
    public void action() throws MXException, RemoteException {
        super.action();
        MboRemote mainMbo = this.getMboValue().getMbo();
        //应用名
        String appname = mainMbo.getOwner().getName();
        //选取的personid
        String personid = mainMbo.getString("personid");
        if ("OPLOGCFG".equals(appname)) {
            mainMbo.setValue("PRIMARYCALNUM", mainMbo.getOwner().getString("PRIMARYCALNUM"));
            //获取交接班的安全组mbo
            MboSetRemote maxgroupMsr = mainMbo.getMboSet("$MAXGROUP", "MAXGROUP",
                    "GROUPNAME = 'XH_YX002' and DESCRIPTION like '%交接班%'");
            //判断安全组是否存在
            if (!maxgroupMsr.isEmpty()) {
                //获取安全组名
                String groupname = maxgroupMsr.getMbo(0).getString("GROUPNAME");
                MboSetRemote groupuserMsr = mainMbo.getMboSet("$GROUPUSER", "GROUPUSER",
                        "GROUPNAME = '" + groupname + "' and USERID = '" + personid + "'");
                if (groupuserMsr.isEmpty()) {
                    MboRemote groupuserMbo = groupuserMsr.add();
                    groupuserMbo.setValue("GROUPNAME", groupname);
                    groupuserMbo.setValue("USERID", personid);
                }
            }
        }
    }
}
