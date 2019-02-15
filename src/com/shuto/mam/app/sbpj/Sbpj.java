package com.shuto.mam.app.sbpj;
/**  
com.shuto.mam.app.sbpj.Sbpj 华南
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年6月21日 下午3:06:23
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import java.util.Date;
import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.security.UserInfo;
import psdi.server.MXServer;
import psdi.util.MXException;

public class Sbpj extends Mbo
  implements SbpjRemote
{
  public Sbpj(MboSet arg0)
    throws RemoteException
  {
    super(arg0);
  }

  public void init() throws MXException {
    super.init();
  }

  public void add() throws MXException, RemoteException {
    super.add();
    Date date = MXServer.getMXServer().getDate();
    String personid = getUserInfo().getPersonId();
    setValue("reportby", personid, 11L);
    setValue("reportdate", date, 11L);
    setValue("status", "新建");
  }
}