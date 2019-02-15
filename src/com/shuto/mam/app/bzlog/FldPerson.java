package com.shuto.mam.app.bzlog;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author wuchang
 * @version V1.0
 * @Title: FldPerson
 * @Package com.shuto.mam.app.bzlog
 * @date 2019/1/10 18:31
 */
public class FldPerson extends MAXTableDomain {

    public FldPerson(MboValue mbv) {
        super(mbv);
        // 获得当前字段名
        String thisAtt = getMboValue().getName();
        // 设置要获取数据表的关系
        setRelationship("person", "personid=:" + thisAtt);
        // 目标字段名变量
        String[] strTo = { thisAtt,"GW","PHONENUM"};
        // 获取值 字段名变更
        String[] strFrom = { "personid","POST","MOBILEPHONE" };

        // 此方法将获取到的值设置到目标字段 中
        setLookupKeyMapInOrder(strTo, strFrom);

    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        Mbo mbo = getMboValue().getMbo();
        String siteid = mbo.getString("siteid");
        if (mbo.isZombie()) {
            // 过滤器中的Mbo
            if (!"MAXADMIN".equalsIgnoreCase(mbo.getThisMboSet().getUserInfo()
                                                .getPersonId())) {
                siteid = mbo.getThisMboSet().getProfile().getDefaultSite();
                this.setListCriteria(" status  = '活动' and locationsite='"
                        + siteid
                        + "'   and   personid not in ('DEFLT','DEFLTREG','MAXADMIN','SYSADM','WORKFLOW','MXINTADM','MAXREG','RPTADMIN') ");
            }
        } else {
            this.setListCriteria(" status  = '活动' and locationsite='"
                    + siteid
                    + "'   and   personid not in ('DEFLT','DEFLTREG','MAXADMIN','SYSADM','WORKFLOW','MXINTADM','MAXREG','RPTADMIN') ");
        }
        return super.getList();
    }
}
