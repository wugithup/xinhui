package com.shuto.mam.app.ajh;
/**  
com.shuto.mam.app.ajh.FldZyfzr 华南
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年7月5日 下午10:39:55
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.app.person.FldPersonID;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.SqlFormat;
import psdi.util.MXException;

public class FldZyfzr extends FldPersonID
{
  public FldZyfzr(MboValue paramMboValue)
    throws MXException
  {
    super(paramMboValue);
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    Mbo localMbo = getMboValue().getMbo();
    String str = localMbo.getString("siteid");
    String sql = " status  = '活动' and locationsite='" + str + 
      "' and personid in(select RESPPARTYGROUP from PERSONGROUPTEAM where PERSONGROUP='HZ_AJH_ZGZRZG')";
    setListCriteria(sql);
    return super.getList();
  }


  
}
