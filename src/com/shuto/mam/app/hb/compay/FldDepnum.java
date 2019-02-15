package com.shuto.mam.app.hb.compay;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;
/**
 * 创新申请
 com.shuto.mam.app.hb.compay.FldDepnum 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午9:03:01
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class FldDepnum extends MAXTableDomain
{
  public FldDepnum(MboValue mbv)
  {
    super(mbv);
    String thisAtt = getMboValue().getName();
    setRelationship("hbdep", "");
    String[] strFrom = { "depnum" };
    String[] strTo = { thisAtt };
    setLookupKeyMapInOrder(strTo, strFrom); }

  public MboSetRemote getList() throws MXException, RemoteException {
    return super.getList(); }

  public void action() throws RemoteException, MXException {
    super.action();

    String description = "";

    if (!(getMboValue().isNull()))
    {
      MboSetRemote comsignSet = getMboSet("depnum='" + 
        getMboValue().getString() + "'");
      if (!(comsignSet.isEmpty())) {
        description = comsignSet.getMbo(0).getString("description");
      }
      getMboValue("USEDEPDESC").setValue(description, 11L);
    }
    if (getMboValue().isNull())
      getMboValue("USEDEPDESC").setValue(description, 11L);
  }
}