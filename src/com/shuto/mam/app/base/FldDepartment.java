package com.shuto.mam.app.base;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * com.shuto.mam.app.base.FldDepartment 选择部门类
 *
 * @author shanbh shanbh@shuoto.cn
 * @version V1.0
 * @date 2017年5月11日 上午11:24:25
 * @Copyright: 2017 Shuto版权所有
 */
public class FldDepartment extends MAXTableDomain {
    public FldDepartment(MboValue mbv) {
        super(mbv);
        setRelationship("DEPARTMENT", "1=1");
        String[] strFrom = new String[]{"DEPNUM"};
        String thisAttr = getMboValue().getAttributeName();
        String strTo[] = {thisAttr};
        setLookupKeyMapInOrder(strTo, strFrom);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        String siteid = getMboValue("siteid").getString();
        if (siteid.isEmpty()) {
            siteid = getMboValue().getMbo().getInsertSite();
        }
        setListCriteria("status = '活动'  and  siteid = '" + siteid + "'");
        return super.getList();
    }

    @Override
    public void action() throws MXException, RemoteException {
        super.action();

    }
}
