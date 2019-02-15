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
public class OPTZ004AppBean extends OPTZAppBean
{
  private String[] attrs = { 
    "#1主变变高主变侧", "#1主变变高GIS侧", "#1主变中性点", "#2主变变高主变侧", "#2主变变高GIS侧", "#2主变中性点", "500kV小纵甲线", 
    "500kV小纵甲线高抗中性点", "500kV小纵乙线", "500kV小纵甲线高抗中性点", "备用变高压侧", "润吉线" };

  public int INSERT()
    throws MXException, RemoteException
  {
    super.INSERT();

    MboSetRemote lines = getMbo().getMboSet("tz_optzline");
    for (int i = 0; i < this.attrs.length; ++i) {
      MboRemote line = lines.addAtEnd();
      line.setValue("optzid", getMbo().getInt("tz_optzid"));
      line.setValue("vc01", this.attrs[i]);
    }
    refreshTable();
    return 1;
  }
}