package com.shuto.mam.app.htjs;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
* @author       lzq liuzq@shuoto.cn
* @Description  华南合同
* @date         2017-6-7 上午11:38:31
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */

public class FldHtlinenum extends MAXTableDomain
{
  public FldHtlinenum(MboValue mbovalue)
    throws MXException
  {
    super(mbovalue);
    setRelationship("htline", "1=1");
    String[] strTo = { "htlinenum", "htnum" };
    String[] strFrom = { "htlinenum", "htnum" };
    setLookupKeyMapInOrder(strTo, strFrom);
  }
}