package com.shuto.mam.app.sbpj;
/**  
com.shuto.mam.app.sbpj.FldStatus 华南
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年6月21日 下午3:03:19
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class FldStatus extends MboValueAdapter
{
  public FldStatus(MboValue mbovalue)
    throws MXException
  {
    super(mbovalue);
  }

  public void init()
    throws MXException, RemoteException
  {
    super.init();
    String status = getMboValue().getString();
    if ((!status.equalsIgnoreCase("")) && (!status.equalsIgnoreCase("新建"))) {
      getMboValue("DESCRIPTION").setReadOnly(true);
      getMboValue("DATEMONTH").setReadOnly(true);
      getMboValue("ZHUANYE").setReadOnly(true);
    }
  }
}