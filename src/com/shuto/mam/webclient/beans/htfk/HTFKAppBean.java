package com.shuto.mam.webclient.beans.htfk;

import com.shuto.mam.webclient.beans.base.CAppBean;
import java.io.PrintStream;
import java.rmi.RemoteException;
import psdi.util.MXException;
/**
* @author       lzq liuzq@shuoto.cn
* @Description  华南合同
* @date         2017-6-7 上午11:12:31
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class HTFKAppBean extends CAppBean
{
  private String ATTR_ID;

public HTFKAppBean()
  {
    this.OWNERTABLE = "HTFK";
    this.ATTR_ID = (this.OWNERTABLE + "ID");
  }

  public int ROUTEWF() throws MXException, RemoteException {
    System.out.println(" --------------------- defind ROUTEWF ");
    super.SAVE();
    return super.ROUTEWF();
  }
}