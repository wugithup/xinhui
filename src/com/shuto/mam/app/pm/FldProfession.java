package com.shuto.mam.app.pm;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.SqlFormat;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author 作者 E-mail: xiamy@shuto.cn
 * @version 1.0
 * @date 创建时间：2017年7月7日 下午4:21:37
 * @Copyright: 2017 Shuto版权所有
 * @since
 */
public class FldProfession extends MAXTableDomain {

    public FldProfession(MboValue mbv) throws RemoteException, MXException {
        super(mbv);
        setRelationship("PROFESSION", "1=1");
        String[] target = {this.getMboValue().getName()};
        String[] source = {"professionnum"};
        setLookupKeyMapInOrder(target, source);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        MboRemote mainmbo = getMboValue().getMbo();
        String siteid = mainmbo.getString("siteid");
        String appname = mainmbo.getThisMboSet().getApp();
        if ("".equals(siteid)) {
            siteid = mainmbo.getInsertSite();
        }
        if ("PMORDER".equals(appname)) {
            setListCriteria("SITEID = '" + siteid + "' and STATUS='活动' or STATUS='运行'");
        } else {
            setListCriteria("SITEID = '" + siteid + "' and STATUS='活动'");
        }
        return super.getList();
    }

    @Override
    public void action() throws MXException, RemoteException {
        super.action();
    }

    @Override
    public void validate() throws MXException, RemoteException {
        // TODO Auto-generated method stub
        super.validate();
        if (getMboValue().isNull()) {
            return;
        }
        MboSetRemote list = getList();
        SqlFormat sql = new SqlFormat("PROFESSIONNUM=:1");
        sql.setObject(1, "PROFESSION", "PROFESSIONNUM", getMboValue().getString());
        String appWhere = list.getAppWhere();
        list.setAppWhere(sql.format());
        list.reset();
        int count = list.count();
        list.setAppWhere(appWhere);
        list.reset();
        if (count == 0) {
            throw new MXApplicationException("该值在选择列表中不存在", "");
        }
    }
}
