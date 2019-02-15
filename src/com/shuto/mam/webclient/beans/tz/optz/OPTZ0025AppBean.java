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
public class OPTZ0025AppBean extends OPTZAppBean
{
  private String[] attrs_kks1 = { 
    "外观", 
    "颜色", 
    "机械杂质", 
    "水分（定性）", 
    "水分（定量）" };

  private String[] attrs_kks2 = { 
    "", 
    "", 
    "", 
    "", 
    "mg/L" };

  private String[] attrs_desc = { 
    "透明", 
    "", 
    "无", 
    "无", 
    "" };

  public int INSERT()
    throws MXException, RemoteException
  {
    super.INSERT();

    MboSetRemote lines = getMbo().getMboSet("tz_optzline");
    for (int i = 0; i < this.attrs_kks1.length; ++i) {
      MboRemote line = lines.addAtEnd();
      line.setValue("optzid", getMbo().getInt("tz_optzid"));
      line.setValue("vc01", this.attrs_kks1[i]);
      line.setValue("vc02", this.attrs_kks2[i]);
      line.setValue("vc03", this.attrs_desc[i]);
    }
    refreshTable();
    return 1;
  }
}