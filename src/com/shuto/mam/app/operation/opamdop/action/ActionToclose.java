package com.shuto.mam.app.operation.opamdop.action;

import java.rmi.RemoteException;
import java.util.Date;
import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年6月24日
 *@since:湖南B停送电联系单流程
 */
public class ActionToclose
  implements ActionCustomClass
{
  public void applyCustomAction(MboRemote arg0, Object[] arg1)
    throws MXException, RemoteException
  {
    MboRemote mbo = arg0;
    Date startDate = mbo.getDate("TIME04");
    Date endDate = mbo.getDate("TIME05");
    if ((startDate == null) || (endDate == null))
      throw new MXApplicationException("opamdop", "操作开始、结束时间不能为空!");
    if (endDate.before(startDate))
      throw new MXApplicationException("opamdop", "操作结束时间不能早于开始时间!");
  }
}