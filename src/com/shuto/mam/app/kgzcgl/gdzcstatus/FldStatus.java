package com.shuto.mam.app.kgzcgl.gdzcstatus;

import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月30日
 * @since:控股台账
 */
public class FldStatus extends MboValueAdapter
{
  public FldStatus(MboValue paramMboValue)
  {
    super(paramMboValue);
  }

  public void init()
    throws MXException, RemoteException
  {
    super.init();
    Mbo localMbo = getMboValue().getMbo();

    String str = localMbo.getString("isnew");
    if (("0".equals(str)) || ("N".equals(str)))
      localMbo.setFlag(7L, true);
  }
}