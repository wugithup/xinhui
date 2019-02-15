package com.shuto.mam.app.dxx;


import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXException;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月31日
 * @since:控股台账
 */
public class ProjectLine extends Mbo
  implements ProjectLineRemote
{
  private MXLogger log;

  public ProjectLine(MboSet mboSet0)
    throws RemoteException
  {
    super(mboSet0);
    this.log = MXLoggerFactory.getLogger("maximo.application.DXX");
  }

  public void add() throws MXException, RemoteException {
    super.add();
    MboRemote owner = getOwner();
    if (owner == null)
      return;
    setValue("dxxprojectid", owner.getString("tz_dxxprojectid"), 11L);
  }

  public void init() throws MXException
  {
    super.init();
    this.log.debug("ProjectLine.init");
  }
}