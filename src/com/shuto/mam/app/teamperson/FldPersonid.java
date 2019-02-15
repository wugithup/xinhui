package com.shuto.mam.app.teamperson;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * 文件名： com.shuto.mam.app.teamperson.FldPersonid.java
 * 说明：teamperson 表 班组人员子表选择人员
 * 创建日期： 2017年8月28日
 * 修改历史 :
 * 1. [2017年8月28日下午10:07:10] 创建文件 by lull lull@shuto.cn
 */
public class FldPersonid extends MAXTableDomain {
    //com.shuto.mam.app.teamperson.FldPersonid

    /**
     * Title: Description:
     *
     * @param mbv
     */
    public FldPersonid(MboValue mbv) {
        super(mbv);
        setRelationship("person", "1=1");
        String[] strFrom = new String[]{"personid"};
        String thisAttr = getMboValue().getAttributeName();
        String strTo[] = {thisAttr};
        setLookupKeyMapInOrder(strTo, strFrom);

    }

    /**
     * <p>
     * Title: getList
     * <p>
     * Description:
     *
     * @return
     * @throws MXException
     * @throws RemoteException
     * @see psdi.mbo.MAXTableDomain#getList()
     */
    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        String siteid = getMboValue("siteid").getString();
        setListCriteria("status = '活动'  and locationsite = '" + siteid + "' ");
        return super.getList();
    }

    @Override
    public void action() throws MXException, RemoteException {
        super.action();
        Mbo mainmbo = getMboValue().getMbo();
        String personid = mainmbo.getString("personid");
        String siteid = mainmbo.getString("siteid");
        MboSetRemote personmbo = mainmbo.getMboSet("$person", "person", "personid = '" + personid + "' and locationsite='" + siteid + "'");
        personmbo.getMbo(0).setValue("banzu", mainmbo.getOwner().getString("TEAMNUM"), 11L);
    }
}
