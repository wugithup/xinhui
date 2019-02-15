package com.shuto.mam.app.oplog.number;

import java.rmi.RemoteException;

import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
/**
 * 停送电联系单
 com.shuto.mam.app.oplog.number.NumberSetRemote 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午3:32:27
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public abstract interface NumberSetRemote extends MboSetRemote
{
  public abstract int getCurNumber(String paramString1, String paramString2, String paramString3, String paramString4)
    throws RemoteException, MXException;

  public abstract void setFlag(boolean paramBoolean)
    throws RemoteException, MXException;
}