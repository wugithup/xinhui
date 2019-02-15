package com.shuto.mam.app.oplog.pmfangan;

import com.ibm.icu.text.SimpleDateFormat;

import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.server.MXServer;
import psdi.util.MXException;
/**
 * 混配煤方案
 com.shuto.mam.app.oplog.pmfangan.PmFangan 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月4日 下午8:56:47
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class PmFangan extends Mbo
  implements PmFanganRemote
{
  public PmFangan(MboSet ms)
    throws RemoteException
  {
    super(ms);
  }

  public void add()
    throws MXException, RemoteException
  {
    super.add();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String DESCRIPTION = df.format(MXServer.getMXServer().getDate()) + "混配煤方案";
    setValue("DESCRIPTION", DESCRIPTION, 11L);
  }

  public void init()
    throws MXException
  {
    super.init();
    String status = getMboValue("STATUS").getString();

    if (!(status.equals("已批准")))
      return;
    setFlag(7L, true);
  }
}