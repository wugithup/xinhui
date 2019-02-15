package com.shuto.mam.webclient.beans.rl.rlshm;
/**
com.shuto.mam.webclient.beans.rl.rlshm.RlShmDataBean 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年9月14日 上午11:11:00
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0
 */
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.Date;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.AppInstance;
import psdi.webclient.system.controller.WebClientEvent;
import psdi.webclient.system.session.WebClientSession;

public class RlShmDataBean extends DataBean
{
  public int addrow()
    throws MXException
  {
    try
    {
      MboSetRemote ThisSet = this.app.getDataBean("RCDJ").getMboSet();
      if (ThisSet.count() == 0)
        super.addrow();
    }
    catch (RemoteException e) {
      e.printStackTrace();
    }
    return 1;
  }

  public void RC() throws RemoteException, MXException {
    String num = getMbo().getString("RLSHMNUMNUM");
    String lx = getMbo().getString("LX");
    if (lx.equals("副产品")) {
      StringBuffer url = new StringBuffer(MXServer.getMXServer().getProperty("mxe.runqian.url"));
      url.append("/crph500ec/rlshmfcpdjpz.rpx&num=" + num);

      System.out.println(url);

      this.app.openURL(url.toString(), true);
    }
    getMbo().setValue("RC", true, 2L);
    super.save();
    this.app.getDataBean("RLMX").reset();
  }

  public int CZ()
    throws RemoteException, MXException
  {
    WebClientEvent event = this.clientSession.getCurrentEvent();
    int er = event.getMessageReturn();
    String num = getMbo().getString("CH");
    String bh = getMbo().getString("RLSHMNUMNUM");
    if (er < 0) {
      throw new MXApplicationYesNoCancelException("BMXAA8342E",
        "rlshmcz", "rlshmcz", new String[] { bh + "，车号为" + num });
    }
    if (er == 2) {
      getMbo().setValue("CZ", true, 2L);
      super.save();
      this.app.getDataBean("RLMX").reset();
    }
    return 1;
  }

  public void CC()
    throws RemoteException, MXException
  {
    getMbo().setValue("CC", true, 2L);
    getMbo().setValue("DATECC", new Date(), 2L);
    super.save();
    this.app.getDataBean("RLMX").reset();
  }
  public int ZF() throws RemoteException, MXException {
    WebClientEvent event = this.clientSession.getCurrentEvent();
    int er = event.getMessageReturn();
    String num = getMbo().getString("CH");
    String bh = getMbo().getString("RLSHMNUMNUM");
    if (er < 0)
    {
      throw new MXApplicationYesNoCancelException("BMXAA8341E",
        "rlshmzf", "rlshmzf", new String[] { bh + "，车号为" + num });
    }
    if (er == 2) {
      getMbo().setValue("ZF", true, 2L);
      super.save();
      this.app.getDataBean("RLMX").reset();
    }
    return 1;
  }
  public void shmlx() throws MXException, RemoteException {
    MboSetRemote mainSet = getMboSet();
    String where = "";
    where = "rc = '1' and (cz='1' or sfcz='否') and cc = '0'  and  zf='0'";
    mainSet.setWhere(where);

    super.save();
    mainSet.reset();
  }
  public void shmrmlx() throws MXException, RemoteException {
    MboSetRemote mainSet = getMboSet();
    String where = "";
    where = "rc = '1' and (cz='1' or sfcz='否') and cc = '0'  and  zf='0' and lx ='燃煤'";
    mainSet.setWhere(where);

    super.save();
    mainSet.reset();
  }
  public void shmfcplx() throws MXException, RemoteException {
    MboSetRemote mainSet = getMboSet();
    String where = "";
    where = "rc = '1' and (cz='1' or sfcz='否') and cc = '0'  and  zf='0' and lx ='副产品'";
    mainSet.setWhere(where);

    super.save();
    mainSet.reset();
  }
  public void qchlx() throws MXException, RemoteException {
    MboSetRemote mainSet = getMboSet();
    String where = "";
    where = "rc = '1' and cz = '0' and sfcz = '是'  and  zf='0' and cc='0' ";
    mainSet.setWhere(where);

    super.save();
    mainSet.reset();
  }
  public void qchrmlx() throws MXException, RemoteException {
    MboSetRemote mainSet = getMboSet();
    String where = "";
    where = "rc = '1' and cz = '0' and sfcz = '是'  and  zf='0' and cc='0' and lx ='燃煤'";
    mainSet.setWhere(where);

    super.save();
    mainSet.reset();
  }
  public void qchfcplx() throws MXException, RemoteException {
    MboSetRemote mainSet = getMboSet();
    String where = "";
    where = "rc = '1' and cz = '0' and sfcz = '是'  and  zf='0' and cc='0' and lx ='副产品'";
    mainSet.setWhere(where);

    super.save();
    mainSet.reset();
  }
}
