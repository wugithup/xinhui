package com.shuto.mam.app.accidentthreat;

import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

public class AccidentThreatSet extends MboSet
  implements AccidentThreatSetRemote
{
  private final String APPLOGGER = "maximo.application.ACCIDENTTHREAT";
  private MXLogger log;

  public AccidentThreatSet(MboServerInterface mboServerInterface0)
    throws RemoteException
  {
    super(mboServerInterface0);
    this.log = MXLoggerFactory.getLogger("maximo.application.ACCIDENTTHREAT");
  }

  protected Mbo getMboInstance(MboSet mboSet0) throws MXException, RemoteException {
    this.log.debug("AccidentThreatSet.getMboInstance");
    return new AccidentThreat(mboSet0);
  }
}