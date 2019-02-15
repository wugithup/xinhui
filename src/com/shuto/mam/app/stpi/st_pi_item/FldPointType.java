package com.shuto.mam.app.stpi.st_pi_item;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class FldPointType extends MboValueAdapter
{
  public FldPointType(MboValue mbv)
  {
    super(mbv);
  }

  public void action() throws MXException, RemoteException {
    super.action();
    MboRemote pi_item = getMboValue().getMbo();
    String point_type = getMboValue().getString();
    if ("CZ".equals(point_type)) {
        pi_item.setFieldFlag("SHAKE_TYPE", 7L, false);
        pi_item.setFieldFlag("SHAKE_TYPE", 128L, true);
      } else {
        pi_item.setValue("SHAKE_TYPE", "",11L);
        pi_item.setFieldFlag("SHAKE_TYPE", 128L, false);
        pi_item.setFieldFlag("SHAKE_TYPE", 7L, true);
      }

      if ("GC".equals(point_type)) {
        pi_item.setValue("HIGHER_LIMIT", "",11L);
        pi_item.setValue("LOWER_LIMIT", "",11L);
        pi_item.setFieldFlag("HIGHER_LIMIT", 7L, true);
        pi_item.setFieldFlag("LOWER_LIMIT", 7L, true);
        pi_item.setFieldFlag("HIGHER_LIMIT", 128L, false);
        pi_item.setFieldFlag("LOWER_LIMIT", 128L, false);
      }
      else {
        pi_item.setFieldFlag("HIGHER_LIMIT", 7L, false);
        pi_item.setFieldFlag("LOWER_LIMIT", 7L, false);
        pi_item.setFieldFlag("HIGHER_LIMIT", 128L, true);
        pi_item.setFieldFlag("LOWER_LIMIT", 128L, true);
      }
      if ("GC".equals(point_type)) {
    	pi_item.setValue("CHECK_METHOD", "观察",11L);
      }else if ("CW".equals(point_type)) {
    	  pi_item.setValue("CHECK_METHOD", "测温",11L);
      }else if ("CZ".equals(point_type)) {
    	  pi_item.setValue("CHECK_METHOD", "测振",11L);
      }else if ("JL".equals(point_type)) {
    	  pi_item.setValue("CHECK_METHOD", "记录",11L);
      }else{
    	  pi_item.setValue("CHECK_METHOD", "",11L);
      }
      }
}
