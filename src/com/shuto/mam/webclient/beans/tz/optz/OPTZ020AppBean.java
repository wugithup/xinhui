package com.shuto.mam.webclient.beans.tz.optz;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
/** 
 * @author  lzq
 * @date 创建时间：2017-3-24 上午9:31:27 
 * @since  原华南台账相关类
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
public class OPTZ020AppBean extends OPTZAppBean
{
  private String[] attrs1 = { 
    "全水Mt", 
    "内水Mad", 
    "空气干燥基灰分Aad", 
    "空气干燥基挥发分Vad", 
    "固定碳FCad", 
    "空气干燥基全硫St，ad", 
    "弹筒发热量Qb，ad", 
    "高位发热量Qgr，ad", 
    "收到基低位发热量Qnet，ar", 
    "收到基低位发热量Qnet，ar" };

  private String[] attrs2 = { 
    "%", 
    "%", 
    "%", 
    "%", 
    "%", 
    "%", 
    "MJ/kg", 
    "MJ/kg", 
    "Cal/g", 
    "MJ/kg" };

  public int INSERT()
    throws MXException, RemoteException
  {
    super.INSERT();

    MboSetRemote lines = getMbo().getMboSet("tz_optzline");
    for (int i = 0; i < this.attrs1.length; ++i) {
      MboRemote line = lines.addAtEnd();
      line.setValue("optzid", getMbo().getInt("tz_optzid"));
      line.setValue("vc01", this.attrs1[i]);
      line.setValue("vc02", this.attrs2[i]);
    }
    refreshTable();
    return 1;
  }
}