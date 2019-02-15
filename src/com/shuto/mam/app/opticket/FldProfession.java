package com.shuto.mam.app.opticket;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.SqlFormat;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

import java.rmi.RemoteException;

public class FldProfession extends MAXTableDomain {

    public FldProfession(MboValue mbv) throws RemoteException, MXException {
        super(mbv);
        setRelationship("profession", "1=1");
        String[] target = {this.getMboValue().getName()};
        String[] source = {"professionnum"};
        setLookupKeyMapInOrder(target, source);
    }

    public MboSetRemote getList() throws MXException, RemoteException {
        MboRemote mainmbo = getMboValue().getMbo();
        String siteid = mainmbo.getString("siteid");
        if ("".equals(siteid)) {
            siteid = mainmbo.getInsertSite();
        }
        setListCriteria("SITEID = '" + siteid + "' and STATUS='活动'");
        return super.getList();
    }

    public void action() throws MXException, RemoteException {
        super.action();
        MboRemote mainMbo = getMboValue().getMbo();
        String siteid = mainMbo.getString("siteid");
        String profession = mainMbo.getString("PROFESSION");
        MboSetRemote professionmst = mainMbo.getMboSet("$PROFESSION", "PROFESSION",
                "professionnum ='" + profession + "' and siteid='" + siteid + "'");
        if (!professionmst.isEmpty()) {
            String profession_parent = professionmst.getMbo(0).getString("parentnum");
            mainMbo.setValue("PROFESSION_PARENT", profession_parent, 11L);
        }
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
            throw new MXApplicationException("该专业在选择列表中不存在", "");
        }
    }
}