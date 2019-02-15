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
public class OPTZ015AppBean extends OPTZAppBean
{
  private String[] attrs = { 
    "10kV配电室", "汽机、照明、检修", "公用PC配电室", "锅炉PC配电室", "保安PC配电室", "空压机", "柴发配电室", 
    "变频器室", "汽机厂房0米", "汽机厂房7.5米", "锅炉厂房0米", "锅炉二层", "锅炉三层", "锅炉四层", "锅炉五层", 
    "锅炉六层", "锅炉七层", "锅炉八层", "锅炉九层", "锅炉十层", "电除尘配电室", "空压机房", "脱硫配电室", "氧化风机房", 
    "灰库房", "化学配电室", "厂区电缆桥架" };

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