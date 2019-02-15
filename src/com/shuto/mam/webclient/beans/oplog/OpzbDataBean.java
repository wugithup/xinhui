package com.shuto.mam.webclient.beans.oplog;


import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:阜阳台账
 */
public class OpzbDataBean extends DataBean
{
  public void tj()
    throws RemoteException, MXException
  {
    DataBean zb = this.app.getDataBean("1357439077836");
    MboSetRemote zbSet = zb.getMboSet();
    MboRemote zbMbo = zbSet.getMbo(zbSet.getCurrentPosition());
    String status = zbMbo.getString("STATUS");
    if ("新建".equals(status)) {
        zbMbo.setValue("STATUS", "关闭", 7L);
        zbSet.save();
      }

    this.app.getDataBean("1357439077836").reset();
    this.app.getDataBean("1357439077836").save();
    this.app.getAppBean().save();
  }
}