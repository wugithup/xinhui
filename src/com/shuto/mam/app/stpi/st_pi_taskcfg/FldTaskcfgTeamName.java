package com.shuto.mam.app.stpi.st_pi_taskcfg;

import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldTaskcfgTeamName extends MAXTableDomain
{
  String tableName = null;
  String attName = null;

  public FldTaskcfgTeamName(MboValue paramMboValue) {
    super(paramMboValue);
    this.attName = getMboValue().getName();
	setRelationship("TEAM","");
    String[] arrayOfString1 = { this.attName };
    String[] arrayOfString2 = { "TEAMNUM" };
    setLookupKeyMapInOrder(arrayOfString1, arrayOfString2);
  }

  public MboSetRemote getList()
    throws MXException, RemoteException
  {
    StringBuffer localStringBuffer = null;
    Mbo localMbo = getMboValue().getMbo();
    localStringBuffer = new StringBuffer(" status  = '活动' and siteid='"+ localMbo.getString("siteid")+"'");
    setListCriteria(localStringBuffer.toString());
    MboSetRemote localMboSetRemote = super.getList();
    return localMboSetRemote;
  }
}