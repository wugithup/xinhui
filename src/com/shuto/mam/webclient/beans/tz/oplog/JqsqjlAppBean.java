package com.shuto.mam.webclient.beans.tz.oplog;
/**  
com.shuto.mam.webclient.beans.tz.oplog.JqsqjlAppBean 阜阳
TZ_JQSQJL胶球收球记录
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年6月15日 下午6:00:41
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;

import psdi.util.MXException;

import com.shuto.mam.webclient.beans.base.CAppBean;

public class JqsqjlAppBean extends CAppBean
{
  public void TJ()
    throws RemoteException, MXException
  {
    getMbo().setValue("STATUS", "已关闭", 11L);
    super.SAVE();
  }
}
