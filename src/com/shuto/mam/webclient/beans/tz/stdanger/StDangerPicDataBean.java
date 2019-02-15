package com.shuto.mam.webclient.beans.tz.stdanger;

import java.rmi.RemoteException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.WebClientEvent;
import psdi.webclient.system.runtime.WebClientRuntime;
import psdi.webclient.system.session.WebClientSession;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:控股台账
 */
public class StDangerPicDataBean extends DataBean
{
  public int selectrecord()
    throws MXException, RemoteException
  {
    gotoAppLink(getMbo().getUniqueIDValue(), "SAFEIMG");
    return 1;
  }

  public void gotoAppLink(long paramLong, String paramString) throws MXException, RemoteException
  {
    WebClientEvent localWebClientEvent = this.sessionContext.getCurrentEvent();
    WebClientSession localWebClientSession = localWebClientEvent.getWebClientSession();
    String str1 = localWebClientEvent.getAdditionalEvent();
    String str2 = localWebClientEvent.getAdditionalEventValue();

    String str3 = "?event=gotoapp&value=" + paramString + "";
    if (!WebClientRuntime.isNull(str1)) {
      str3 = str3 + "&additionalevent=" + str1;
      if (!WebClientRuntime.isNull(str2)) {
        str3 = str3 + "&additionaleventvalue=" + str2;
      }
    }
    str3 = str3 + "&uniqueid=" + paramLong;

    localWebClientSession.getCurrentApp().put("forcereload", "true");
    localWebClientSession.gotoApplink(str3);
    localWebClientEvent.cancelRender();
  }
}