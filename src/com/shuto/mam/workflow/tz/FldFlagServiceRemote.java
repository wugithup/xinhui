package com.shuto.mam.workflow.tz;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.server.AppServiceRemote;
import psdi.util.MXException;
/** 
 * @author  lzq
 * @date 创建时间：2017-3-24 上午9:31:27 
 * @since  原华南台账相关类
 */
public abstract interface FldFlagServiceRemote extends AppServiceRemote
{
  public abstract void setFieldsFlags(String paramString1, MboRemote paramMboRemote, String paramString2)
    throws MXException, RemoteException;

  public abstract void checkRequiredFields(String paramString1, MboRemote paramMboRemote, String paramString2)
    throws MXException, RemoteException;
}