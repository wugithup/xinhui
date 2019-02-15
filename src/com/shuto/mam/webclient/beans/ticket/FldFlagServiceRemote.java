package com.shuto.mam.webclient.beans.ticket;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.server.AppServiceRemote;
import psdi.util.MXException;
/**
 * 逻辑（定值）申请               LJDZ
 com.shuto.mam.webclient.beans.ticket.FldFlagServiceRemote 湖南
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年6月21日 下午11:16:20
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public abstract interface FldFlagServiceRemote extends AppServiceRemote
{
  public abstract void setFieldsFlags(String paramString1, MboRemote paramMboRemote, String paramString2)
    throws MXException, RemoteException;

  public abstract void checkRequiredFields(String paramString1, MboRemote paramMboRemote, String paramString2)
    throws MXException, RemoteException;
}