package com.shuto.mam.webclient.beans.htjs;

import com.shuto.mam.webclient.beans.base.CAppBean;
/**
* @author       lzq liuzq@shuoto.cn
* @Description  华南合同
* @date         2017-6-7 上午11:12:31
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */

public class HTJSAppBean extends CAppBean
{
  private String ATTR_ID;

public HTJSAppBean()
  {
    this.OWNERTABLE = "HTJS";
    this.ATTR_ID = (this.OWNERTABLE + "ID");
  }
}