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
public class OPTZ0023AppBean extends OPTZAppBean
{
  private String[] attrs_kks1 = { 
    "外状", 
    "水溶性酸（PH）值", 
    "酸值", 
    "闪点（闭口）", 
    "水分", 
    "氢（H2）", 
    "一氧化碳（CO）", 
    "二氧化碳（CO2）", 
    "甲烷（CH4）", 
    "乙烷（C2H6）", 
    "乙烯（C2H4）", 
    "乙炔（C2H2）", 
    "总烃", 
    "界面张力（25℃ ）", 
    "介质损耗因数（90℃）", 
    "击穿电压（2.5mm间隙）", 
    "体积电阻率（90℃）", 
    "油中含气量（体积分数）", 
    "油泥与沉淀物（质量分数）" };

  private String[] attrs_kks2 = { 
    "", 
    "", 
    "mgKOH/g", 
    "℃", 
    "mg/L", 
    "μL/L", 
    "μL/L", 
    "μL/L", 
    "μL/L", 
    "μL/L", 
    "μL/L", 
    "μL/L", 
    "μL/L", 
    "mN/m", 
    "", 
    "kV", 
    "Ω·m", 
    "%", 
    "%" };

  private String[] attrs_desc = { 
    "透明，无杂质和悬浮物", 
    "≥4.2", 
    "≤0.1", 
    "不比新油原始测定值低10℃", 
    "330~500kV及以上  ≤15；110kV及以下        ≤35。", 
    "＜150", 
    "", 
    "", 
    "", 
    "", 
    "", 
    "", 
    "330kV及以上     ＜1；220kV及以下     ＜5。", 
    "＜150", 
    "≥19", 
    "500kV及以上      ≤0.020；  ≤kV330          ≤0.040。", 
    "500kV及以上      ≥50；66~110kV         ≥35；    35kV及以下       ≥30。", 
    "500kV及以上     ≥1×1010；  ≤330kV         ≥5×109。", 
    "330~500kV及以上     ≤3", 
    "＜0.02" };

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