package com.shuto.mam.app.kaizen;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboConstants;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * KAIZEN项目提案       YX_REGISTR
 * com.shuto.mam.app.kaizen.FldApprovePerson 江苏
 *  @author       liwc   liwc@shuoto.cn
 *  @date         2017年5月17日 上午9:18:04
 *  @Copyright:   2017 Shuto版权所有
 *  @version      V1.0 
 */
public class FldApprovePerson extends MAXTableDomain {

    public FldApprovePerson(MboValue mbv) throws RemoteException, MXException {
        super(mbv);
        // TODO Auto-generated constructor stub
        setRelationship("PERSON", "PERSONID=:" + this.getMboValue().getAttributeName());
        setLookupKeyMapInOrder(new String[]{getMboValue().getName()}, new String[]{"personid"});
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        // TODO Auto-generated method stub
        String userName = this.getMboValue().getMbo().getThisMboSet().getUserInfo().getPersonId();
        String siteid = this.getMboValue().getMbo().getString("siteid");
        MboSetRemote personidset = this.getMboValue().getMbo().getMboSet("$person", "person", "personid = '" + userName + "' and locationsite = '" + siteid + "'");
        if (personidset.isEmpty()){
            return  super.getList();
        }
        String DEPARTMENT = personidset.getMbo(0).getString("DEPARTMENT");
        String whereClause = null;
        String RYZ = "";
        if ("DEPT05".equals(DEPARTMENT)) {
            RYZ = "HX_KZ_ZHB";
        } else if ("DEPT02".equals(DEPARTMENT)) {
            RYZ = "HX_KZ_JSB";
        } else if ("DEPT03".equals(DEPARTMENT)) {
            RYZ = "HX_KZ_FDB";
        } else if ("DEPT04".equals(DEPARTMENT)) {
            RYZ = "HX_KZ_EHS";
        }
        whereClause = "personid in (select RESPPARTYGROUP from PERSONGROUPTEAM where PERSONGROUP='" + RYZ + "')and locationsite = '" + siteid + "'";
        setListCriteria(whereClause);
        return super.getList();
    }

    @Override
    public void init() throws MXException, RemoteException {
        // TODO Auto-generated method stub
        super.init();
        String siteid = this.getMboValue().getMbo().getString("siteid");
        if ("XZHP000".equals(siteid)) {
            this.getMboValue().setFlag(MboConstants.READONLY, false);
            this.getMboValue().setFlag(MboConstants.REQUIRED, true);
        } else {
            this.getMboValue().setFlag(MboConstants.READONLY, true);
        }

    }

}
