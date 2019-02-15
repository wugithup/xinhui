package com.shuto.mam.app.stpdindicator;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
/**
 * 生产指标录入
 com.shuto.mam.app.stpdindicator.stpdindicatorRemote 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午8:29:37
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public abstract interface stpdindicatorRemote extends MboRemote
{
  public abstract void save()
    throws MXException, RemoteException;

  public abstract void add()
    throws MXException, RemoteException;
}