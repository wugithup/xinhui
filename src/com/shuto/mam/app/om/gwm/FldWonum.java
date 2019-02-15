package com.shuto.mam.app.om.gwm;
/**  
com.shuto.mam.app.om.gwm.FldWonum 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月15日 下午5:37:10
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldWonum extends MAXTableDomain
{
  public FldWonum(MboValue mbovalue)
    throws MXException
  {
    super(mbovalue);
    setRelationship("WORKORDER", "");
    String[] strFrom = { "WONUM" };
    String[] strTo = { "WONUM" };
    setLookupKeyMapInOrder(strTo, strFrom);
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    MboRemote Mbo = getMboValue().getMbo().getOwner();
    String siteid=Mbo.getString("siteid");
    setListCriteria("WORKTYPE='工作票' and siteid='"+siteid+"'");
   

    return super.getList();
  }
}
