package com.shuto.mam.webclient.beans.tz.operation.oplogtjb;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
/** 
 * @author  lzq
 * @date 创建时间：2017-3-24 上午9:31:27 
 * @since  原河南台账相关类
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
public class RunfhlzAppBean extends AppBean
{
  public boolean hasAuth()
    throws MXException, RemoteException
  {
    MboRemote mbo = getMbo();
    String status = mbo.getString("STATUS");
    String userid = mbo.getUserInfo().getPersonId();
    String createby = mbo.getString("HYR");
    if ("maxadmin".equalsIgnoreCase(userid))
      return true;
    if (("新建".equals(status)) && (createby.equals(userid)))
      return true;
    MboSetRemote wfinstanceMboSet = mbo.getMboSet("WFINSTANCE");
    if ((wfinstanceMboSet != null) && (wfinstanceMboSet.count() > 0)) {
      MboSetRemote wfassignmentMboSet = mbo.getMboSet("WFASSIGNMENT");
      wfassignmentMboSet.setWhere("assigncode=:&USERNAME&");
      return !wfassignmentMboSet.isEmpty();
    }
    return false;
  }

  public int SAVE()
    throws MXException, RemoteException
  {
    if (!hasAuth()) {
      throw new MXApplicationException("system", "SYSSAVE2");
    }

    return super.SAVE();
  }
}