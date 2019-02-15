package com.shuto.mam.webclient.beans.carexpense;


import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;


public class CarExpenseAppBean
  extends AppBean
{
  public int SAVE()
    throws MXException, RemoteException
  {
	  super.SAVE();
    MboRemote mainMbo = this.app.getAppBean().getMbo();
    
    MboSetRemote mboSet = mainMbo.getMboSet("CAREXPENSELINE2");
    int count = mboSet.count();
    float price = 0.0F;
    for (int i = 0; i < count; i++)
    {
      MboRemote mbo = mboSet.getMbo(i);
      price += mbo.getFloat("WXMONEY");
    }
    mainMbo.setValue("SQMONEY", price);
    
    return 0;
  }
  
  protected void setCurrentRecordData(MboRemote mainMbo)
    throws MXException, RemoteException
  {
    String status = mainMbo.getString("status");
    if ("待总经理审批".equals(status))
    {
      System.out.println("进入总经理审批");
      mainMbo.setFieldFlag("SPMONEY", 7L, false);
      mainMbo.setFieldFlag("SPMONEY", 128L, true);
    }
    else if (("已关闭".equals(status)) || ("已作废".equals(status)) || ("已取消".equals(status)))
    {
      mainMbo.setFlag(7L, true);
    }
    super.setCurrentRecordData(mainMbo);
  }
}