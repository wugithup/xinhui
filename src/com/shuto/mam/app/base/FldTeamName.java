/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.base;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.SqlFormat;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

import java.rmi.RemoteException;

public class FldTeamName extends MAXTableDomain {

    public FldTeamName(MboValue mbv) {

        super(mbv);
        String attName = getMboValue().getName();
        setRelationship("CLASSTEAM", "siteid=:siteid");
        String[] strTo = {attName};

        String[] strFrom = {"TEAMNAME"};
        setLookupKeyMapInOrder(strTo, strFrom);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {

        StringBuilder whereSB;
        Mbo mbo = getMboValue().getMbo();
        String siteid = getMboValue().getMbo().getString("SITEID");
        //查询列表
        if ("常熟项目".equals(siteid)) {
            if (mbo.isZombie()) {
                whereSB =
                        new StringBuilder("teamname in(select teamname from SR group by teamname)");
                setListCriteria(whereSB.toString());
            } else {
                whereSB = new StringBuilder(
                        "teamname in(select teamname from GZPRYWHZB where wotickettype = '所有检修班组负责人' and  type='负责人' group by teamname)");
                setListCriteria(whereSB.toString());
            }
        } else {
            if (mbo.isZombie()) {
                whereSB =
                        new StringBuilder("teamname in(select teamname from SR group by teamname)");
                setListCriteria(whereSB.toString());
            } else {
                whereSB = new StringBuilder(
                        "teamname in(select teamname from GZPRYWHZB where type='负责人' group by teamname)");
                setListCriteria(whereSB.toString());
            }
        }
        return super.getList();
    }

    @Override
    public void validate() throws MXException, RemoteException {

        super.validate();
        if (getMboValue().isNull()) {
            return;
        }
        MboSetRemote list = getList();
        SqlFormat sql = new SqlFormat("TEAMNAME=:1");
        sql.setObject(1, "GZPRYWHZB", "TEAMNAME", getMboValue().getString());
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
