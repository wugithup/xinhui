package com.shuto.mam.app.oplog.jsexplain;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
/**
 * 
com.shuto.mam.app.oplog.jsexplain.JsexplainStatus 河北分公司（曹妃甸）
* @author       songdd  songdd@shuoto.cn
* @date         2017年5月5日 上午11:05:53
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class JsexplainStatus extends MboValueAdapter
{
  public JsexplainStatus()
  {
  }

  public JsexplainStatus(MboValue mbv)
  {
    super(mbv); }

  public void action() throws MXException, RemoteException {
    super.action();
    MboRemote mbo = getMboValue().getMbo();
    String status = getMboValue("STATUS").getString();

    if (status.equals("已关闭"))
    {
      mbo.setFieldFlag("DESCRIPTION", 7L, true);
      mbo.setFieldFlag("JDNEIRONG", 7L, true);
      mbo.setFieldFlag("YXDATE", 7L, true);
      mbo.setFieldFlag("DEPARTMENT", 7L, true);
      mbo.setFieldFlag("TYPE", 7L, true);
    }
    if (!(status.equals("已取消")))
      return;
    mbo.setFieldFlag("DESCRIPTION", 7L, false);
    mbo.setFieldFlag("JDNEIRONG", 7L, false);
    mbo.setFieldFlag("YXDATE", 7L, false);
    mbo.setFieldFlag("DEPARTMENT", 7L, false);
    mbo.setFieldFlag("TYPE", 7L, false);
  }
}