package com.shuto.mam.app.om.txytj;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年4月17日
 * @since:阜阳台账
 */
public class FldTxdl extends MboValueAdapter
{
  public FldTxdl(MboValue mbovalue)
    throws MXException
  {
    super(mbovalue);
  }

  public void action() throws MXException, RemoteException
  {
    super.action();

    MboRemote mbo = getMboValue().getMbo();
    double SWDL1 = mbo.getDouble("SWDL1");
    double YBYNBHGDL = mbo.getDouble("YBYNBHGDL");
    double YBYSBHGDL = mbo.getDouble("YBYSBHGDL");

    double txdl = SWDL1 - YBYNBHGDL - YBYSBHGDL;

    mbo.setValue("TXDL", txdl);
  }
}