package com.shuto.mam.app.wo.workorder;
/**  
com.shuto.mam.app.wo.workorder.FldJdx_Czp 华中
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年7月28日 下午4:48:02
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldJdx_Czp extends MAXTableDomain
{
  public FldJdx_Czp(MboValue paramMboValue)
  {
    super(paramMboValue);
    setRelationship("workorder", "1=1");

    String[] arrayOfString1 = { "OPTICKETNUM" };

    String[] arrayOfString2 = { "wonum" };
    setLookupKeyMapInOrder(arrayOfString1, arrayOfString2);
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    Mbo localMbo = getMboValue().getMbo();
    String siteid=localMbo.getString("SITEID");
    MboSetRemote localMboSetRemote = null;
    localMboSetRemote = super.getList();

    localMboSetRemote.setWhere("worktype = '工作票' and siteid='"+siteid+"'");
    return localMboSetRemote;
  }
}