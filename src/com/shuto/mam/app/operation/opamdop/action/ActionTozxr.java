package com.shuto.mam.app.operation.opamdop.action;

import java.rmi.RemoteException;
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
public class ActionTozxr
  implements ActionCustomClass
{
  public void applyCustomAction(MboRemote arg0, Object[] arg1)
    throws MXException, RemoteException
  {
    MboRemote mbo = arg0;
    String zxr = mbo.getString("PERSONID02");
    if ((zxr == null) || (zxr.equals("")))
      throw new MXApplicationException("opamdop", "请先选择执行人再发送流程!");
  }
}