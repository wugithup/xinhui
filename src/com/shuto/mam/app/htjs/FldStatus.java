package com.shuto.mam.app.htjs;

import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
* @author       lzq liuzq@shuoto.cn
* @Description  华南合同
* @date         2017-6-7 上午11:38:31
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */

public class FldStatus extends MboValueAdapter
{
  public FldStatus(MboValue arg0)
  {
    super(arg0);
  }

  public void init() throws MXException, RemoteException {
    super.init();
    if (getMboValue().getString().equals("已批准"))
      getMboValue().getMbo().setFlag(7L, true);
    else
      getMboValue().getMbo().setFlag(7L, false);
  }
}