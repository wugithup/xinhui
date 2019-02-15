package com.shuto.mam.app.workorder;

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
 * @date 创建时间：2017年5月22日 上午11:39:28
 * @Copyright: 2017 Shuto版权所有
 * @since
 */
public class FldProfession extends MAXTableDomain {

    public FldProfession(MboValue mbv) throws RemoteException, MXException {
        super(mbv);
        setRelationship("profession", "1=1");
        String[] target = {this.getMboValue().getName(), "WOJO1"};
        String[] source = {"professionnum", "DESCRIPTION"};
        setLookupKeyMapInOrder(target, source);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        MboRemote mainmbo = getMboValue().getMbo();
        String siteid;
        siteid = mainmbo.getString("siteid");
        if (siteid.isEmpty()) {
            siteid = mainmbo.getInsertSite();
        }
        setListCriteria("SITEID = '" + siteid + "' and STATUS='活动'");
        setListOrderBy("PROFESSIONNUM");
        return super.getList();
    }

    @Override
    public void action() throws MXException, RemoteException {
        super.action();
        MboRemote mainMbo = this.getMboValue().getMbo();
        // 地点
        String siteid = mainMbo.getString("siteid");
        // 专业
        String profession = mainMbo.getString("PROFESSION");

        MboSetRemote professionmst = mainMbo.getMboSet("$PROFESSION", "PROFESSION",
                "professionnum ='" + profession + "' and siteid='" + siteid + "'");
        if (!professionmst.isEmpty()) {
            // 父级专业
            String profession_parent = professionmst.getMbo(0).getString("parentnum");
            String description = professionmst.getMbo(0).getString("description");
            mainMbo.setValue("PROFESSION_PARENT", profession_parent, 11L);
            mainMbo.setValue("WOJO1", description, 11L);
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
