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
public class OPTZ0024AppBean extends OPTZAppBean
{
  private String[] attrs_kks1 = { 
    "外观", 
    "密度（20℃）", 
    "运动粘度（40℃）", 
    "开口闪点", 
    "颗粒污染度(NAS1638)", 
    "水份", 
    "酸值", 
    "倾点", 
    "自燃点", 
    "含氯量", 
    "起泡性试验，24℃", 
    "起泡性试验，93.5℃", 
    "起泡性试验，后24℃", 
    "电阻率（20℃）", 
    "矿物油含量", 
    "空气释放值(50℃)" };

  private String[] attrs_kks2 = { 
    "", 
    "g/cm3", 
    "mm2/s", 
    "℃", 
    "级", 
    "mg/L", 
    "mgKOH/g", 
    "℃", 
    "℃", 
    "mg/kg", 
    "mL/mL", 
    "mL/mL", 
    "mL/mL", 
    "Ω·cm", 
    "%", 
    "min" };

  private String[] attrs_desc = { 
    "透明", 
    "1.13～1.17", 
    "39.1～52.9", 
    "≥235", 
    "≤6", 
    "≤1000", 
    "≤0.15", 
    "≤-18", 
    "≥530", 
    "≤100", 
    "≤200/0", 
    "≤40/0", 
    "≤200/0", 
    "≥6×109", 
    "≤4", 
    "≤10" };

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