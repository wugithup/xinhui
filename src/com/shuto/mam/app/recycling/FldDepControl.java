package com.shuto.mam.app.recycling;
/**  
com.shuto.mam.app.recycling.FldDepControl 华中
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月10日 下午2:01:23
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldDepControl extends MAXTableDomain
{
  public FldDepControl(MboValue mboValue)
  {
    super(mboValue);
    String s = getMboValue().getAttributeName();
    setRelationship("department", "depnum= :" + s);
    setLookupKeyMapInOrder(new String[] { 
      s }, 
      new String[] { 
      "depnum" });
  }

  public MboSetRemote getList()
		    throws MXException, RemoteException
		  {
		    Mbo parent = getMboValue().getMbo();
		    String sit = parent.getString("SITEID");

		    setListCriteria("siteid ='" + sit + "' and status = '活动' ");

		    return super.getList();
		  }
}
