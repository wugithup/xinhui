package com.shuto.mam.app.operation.oplogassetinfo;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class FldLowerLimit extends MboValueAdapter
{
  public FldLowerLimit(MboValue mv)
    throws MXException, RemoteException
  {
    super(mv);
  }

  public void validate()
    throws MXException, RemoteException
  {
    super.validate();

    if (super.getMboValue("up").isNull())
      return;
    double upperlimit = super.getMboValue("up").getDouble();

    double lowerlimit = super.getMboValue("low").getDouble();

    if (upperlimit >= lowerlimit)
      return;
    throw new MXApplicationException("patrolitem", "lowerlimit");
  }
}