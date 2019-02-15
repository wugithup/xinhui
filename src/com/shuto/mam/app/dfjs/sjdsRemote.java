package com.shuto.mam.app.dfjs;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
/**
 * 电费结算
 com.shuto.mam.app.dfjs.sjdsRemote 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午8:48:56
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public abstract interface sjdsRemote extends MboRemote
{
  public abstract void save()
    throws MXException, RemoteException;

  public abstract void add()
    throws MXException, RemoteException;
}