package com.shuto.mam.app.sbpj;
/**  
com.shuto.mam.app.sbpj.FldFdb 华南
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年6月21日 下午3:01:47
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.io.PrintStream;
import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class FldFdb extends MboValueAdapter
{
  public FldFdb(MboValue mbovalue)
    throws MXException
  {
    super(mbovalue);
  }

  public void init()
    throws MXException, RemoteException
  {
    super.init();
    MboRemote mboremote = getMboValue().getMbo().getOwner();
    String status = mboremote.getString("status");
    System.out.println("status1123==" + status);
    if ((status.equalsIgnoreCase("新建")) || (status.equalsIgnoreCase("检修评分")) || (status.equalsIgnoreCase("技术部评分")) || (status.equalsIgnoreCase("部长查看")) || (status.equalsIgnoreCase("分管领导查看")) || (status.equalsIgnoreCase("已关闭"))) {
      getMboValue("FDB").setReadOnly(true);
    }
    if ((status.equalsIgnoreCase("新建")) || (status.equalsIgnoreCase("发电部评分")) || (status.equalsIgnoreCase("技术部评分")) || (status.equalsIgnoreCase("部长查看")) || (status.equalsIgnoreCase("分管领导查看")) || (status.equalsIgnoreCase("已关闭"))) {
      getMboValue("JXZG").setReadOnly(true);
    }
    if ((status.equalsIgnoreCase("新建")) || (status.equalsIgnoreCase("发电部评分")) || (status.equalsIgnoreCase("检修评分")) || (status.equalsIgnoreCase("部长查看")) || (status.equalsIgnoreCase("分管领导查看")) || (status.equalsIgnoreCase("已关闭")))
      getMboValue("JSZCB").setReadOnly(true);
  }
}