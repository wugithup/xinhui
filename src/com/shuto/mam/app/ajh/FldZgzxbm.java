package com.shuto.mam.app.ajh;
/**  
com.shuto.mam.app.ajh.FldZgzxbm 华南
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年7月5日 下午5:49:03
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldZgzxbm extends MAXTableDomain
{
  public FldZgzxbm(MboValue mbv)
    throws MXException
  {
    super(mbv);
    setRelationship("department", "1=1");
    String[] strFrom = { "depnum" };
    String[] strTo = { getMboValue().getAttributeName() };
    setLookupKeyMapInOrder(strTo, strFrom);
  }

  public MboSetRemote getList() throws MXException, RemoteException
  {
    String site = getMboValue().getMbo().getString("siteid");
    setListCriteria("siteid = '" + site + "'");
    return super.getList();
  }
}