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
public class FldKhdl extends MboValueAdapter
{
  public FldKhdl(MboValue mbovalue)
    throws MXException
  {
    super(mbovalue);
  }

  public void action() throws MXException, RemoteException
  {
    super.action();

    MboRemote mbo = getMboValue().getMbo();

    double zdl = mbo.getDouble("ZDL");
    double txdl = mbo.getDouble("TXDL");

    double khdl = zdl - txdl;

    mbo.setValue("KHDL", khdl);
  }
}