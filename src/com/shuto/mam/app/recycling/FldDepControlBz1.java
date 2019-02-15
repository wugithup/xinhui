package com.shuto.mam.app.recycling;
/**  
com.shuto.mam.app.recycling.FldDepControlBz1 华中
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月10日 下午3:42:14
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldDepControlBz1 extends MAXTableDomain
{
  public FldDepControlBz1(MboValue mboValue)
  {
    super(mboValue);
    String s = getMboValue().getAttributeName();
    setRelationship("TEAM", "TEAMNUM= :" + s);
    setLookupKeyMapInOrder(new String[] { 
      s }, 
      new String[] {  
      "TEAMNUM" });
  }
  
  public MboSetRemote getList()
		    throws MXException, RemoteException
		  {
		    Mbo parent = getMboValue().getMbo();
//		    String bm = parent.getString("DEPARTMENT");
		    String sit = parent.getString("siteid");

//		    if (!"".equalsIgnoreCase(bm)) {
//		      setListCriteria("  status='活动'  and siteid ='" + sit + "' and  DEPARTMENTSID='" + bm + "'");
//		    }
		    setListCriteria("  status='活动'  and siteid ='" + sit + "'");
		    return super.getList();
		  }    
     
}

