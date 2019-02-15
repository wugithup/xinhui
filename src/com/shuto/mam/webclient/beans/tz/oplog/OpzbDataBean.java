package com.shuto.mam.webclient.beans.tz.oplog;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
/** 
* @author  lzq
* @date 创建时间：2017-3-22 下午2:02:52 
* @since  原阜阳项目台账类
* 
* 
* getDataBean("1357439077836");
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