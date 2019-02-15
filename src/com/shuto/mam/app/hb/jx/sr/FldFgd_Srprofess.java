package com.shuto.mam.app.hb.jx.sr;

import java.rmi.RemoteException;

import psdi.mbo.ALNDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;
/**
 * 创新申请
 com.shuto.mam.app.hb.jx.sr.FldFgd_Srprofess 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午9:01:48
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class FldFgd_Srprofess extends ALNDomain
{
  public FldFgd_Srprofess(MboValue mbovalue)
    throws MXException
  {
    super(mbovalue);
    setDomainId("SRPROFESS");
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    MboSetRemote Set = super.getList();
    String Where = "";
    MboRemote Mbo = getMboValue().getMbo();
    if (Mbo != null) {
      String siteid = Mbo.getUserInfo().getInsertSite();
      String depnum = Mbo.getString("DEPNUM");
      if (!("HB".equals(siteid))) {
        if (!("1000600".equals(depnum)))
          Where = " and value  != '热力'  and description like '%" + siteid + "%'";
        else if ("1000600".equals(depnum)) {
          Where = " and value = '热力'";
        }
      }
    }
    String SetWhere = Set.getRelationship();
    Set.setRelationship(SetWhere + Where);
    System.out.println(SetWhere + Where);
    return Set;
  }
}