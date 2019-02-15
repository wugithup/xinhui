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
public class FldTxdl1 extends MboValueAdapter
{
  public FldTxdl1(MboValue mbovalue)
    throws MXException
  {
    super(mbovalue);
  }

  public void action() throws MXException, RemoteException
  {
    super.action();

    MboRemote mbo = getMboValue().getMbo();
    double SWDL2 = mbo.getDouble("SWDL2");
    double YBYNBHGDL1 = mbo.getDouble("YBYNBHGDL1");
    double YBYSBHGDL1 = mbo.getDouble("YBYSBHGDL1");

    double txdl1 = SWDL2 - YBYNBHGDL1 - YBYSBHGDL1;

    mbo.setValue("TXDL1", txdl1);
  }
}