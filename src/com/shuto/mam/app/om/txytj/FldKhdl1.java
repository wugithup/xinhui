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

public class FldKhdl1 extends MboValueAdapter
{
  public FldKhdl1(MboValue mbovalue)
    throws MXException
  {
    super(mbovalue);
  }

  public void action() throws MXException, RemoteException
  {
    super.action();

    MboRemote mbo = getMboValue().getMbo();

    double zdl = mbo.getDouble("ZDL1");
    double txdl = mbo.getDouble("TXDL1");

    double khdl = zdl - txdl;

    mbo.setValue("KHDL1", khdl);
  }
}
