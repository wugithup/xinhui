package com.shuto.mam.webclient.beans.htjs;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
* @author       lzq liuzq@shuoto.cn
* @Description  华南合同
* @date         2017-6-7 上午11:12:31
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */

public class HTJSLineBean extends DataBean
{
  public int toggledeleterow()
    throws MXException
  {
    try
    {
      getMbo().setValueNull("htjsnum", 2L);
      save();
      this.app.getAppBean().save();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return 1;
  }
}