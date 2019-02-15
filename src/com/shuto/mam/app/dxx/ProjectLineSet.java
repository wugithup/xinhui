package com.shuto.mam.app.dxx;


import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
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
public class ProjectLineSet extends MboSet
  implements ProjectLineSetRemote
{
  private MXLogger log;

  public ProjectLineSet(MboServerInterface mboServerInterface0)
    throws RemoteException
  {
    super(mboServerInterface0);
    this.log = MXLoggerFactory.getLogger("maximo.application.DXX");
  }

  protected Mbo getMboInstance(MboSet mboSet0) throws MXException, RemoteException {
    this.log.debug("ProjectLineSet.getMboInstance");
    return new ProjectLine(mboSet0);
  }
}