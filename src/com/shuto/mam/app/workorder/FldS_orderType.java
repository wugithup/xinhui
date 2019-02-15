package com.shuto.mam.app.workorder;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.util.Arrays;

/**
 * @author lull lull@shuto.cn
 * @ClassName: FldS_orderType
 * @Description: 工作票类型字段类
 * @date 2017年6月6日 下午2:52:44
 */

public class FldS_orderType extends MAXTableDomain {
    public FldS_orderType(MboValue mbv) {
        super(mbv);
        setRelationship("ALNDOMAIN", "1=1");
        String[] target = {this.getMboValue().getName()};
        String[] source = {"DESCRIPTION"};
        setLookupKeyMapInOrder(target, source);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        String app = getMboValue().getMbo().getThisMboSet().getApp();
        if ("HDWOTICKET".equalsIgnoreCase(app)||"H_WOTRACK".equalsIgnoreCase(app)) {
            setListCriteria("DOMAINID = 'S_ORDERTYPE' and  Value not in ('一级动火工作票','二级动火工作票')");
        } else {
            setListCriteria("DOMAINID = 'S_ORDERTYPE' and  Value in ('一级动火工作票','二级动火工作票')");
        }
        return super.getList();
    }

//    @Override
//    public void action() throws MXException, RemoteException {
//        super.action();
//        MboRemote mainMbo = getMboValue().getMbo();
//        String location = mainMbo.getString("LOCATION");
//        String odrertype = getMboValue().getString();
//        MboSetRemote hazards = mainMbo.getMboSet("WOSLTAGENABLED");
//        if (!hazards.isEmpty()) {
//            for (int i = 0; i < hazards.count(); i++) {
//                hazards.getMbo(i).getMboSet("WOSAFETYLINKTAG").deleteAndRemoveAll();
//            }
//            hazards.deleteAndRemoveAll();
//        }
//        MboSetRemote acfl = mainMbo.getMboSet("WOSAFETYLINK");
//        if (!acfl.isEmpty()) {
//            MboSetRemote fl = mainMbo.getMboSet("WOHAZARD");
//            MboSetRemote WOTAGOUT = mainMbo.getMboSet("WOTAGOUT");
//            fl.deleteAll();
//            fl.commit();
//            fl.save();
//
//            WOTAGOUT.deleteAll();
//            WOTAGOUT.commit();
//            WOTAGOUT.save();
//
//            acfl.deleteAll();
//            acfl.commit();
//            acfl.save();
//        }
//
//        MboRemote haz = hazards.add();
//        haz.setValue("hazardid", "YXBXAC");
//        haz.setValue("location", location);
//        haz.setValue("applyseq", "1");
//
//        haz = hazards.add();
//        haz.setValue("hazardid", "JXBXAC");
//        haz.setValue("location", location);
//        haz.setValue("applyseq", "2");
//
//        haz = hazards.add();
//        haz.setValue("hazardid", "BCAC");
//        haz.setValue("location", location);
//        haz.setValue("applyseq", "3");
//
//    }
}