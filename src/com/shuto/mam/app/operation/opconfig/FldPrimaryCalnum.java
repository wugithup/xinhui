package com.shuto.mam.app.operation.opconfig;

import java.rmi.RemoteException;

import psdi.app.person.Person;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;
/**
 * 
com.shuto.mam.app.operation.opconfig.FldPrimaryCalnum 磴口项目
* @author       songdd  songdd@shuoto.cn
* @date         2017年5月7日 下午1:27:33
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class FldPrimaryCalnum extends MAXTableDomain
{
  public FldPrimaryCalnum(MboValue mbv)
    throws MXException, RemoteException
  {
    super(mbv);
    setRelationship("calendar", "calnum=:primarycalnum");
    setLookupKeyMapInOrder(new String[] { "primarycalnum" }, new String[] { "calnum" });
  }

  public void action() throws MXException, RemoteException
  {
    super.action();

    MboValue thisValue = getMboValue();
    Mbo thisMbo = thisValue.getMbo();
    String PERSONGROUP = thisMbo.getString("LABORPOST");
    MboSetRemote SetRemote = thisMbo.getMboSet("$PERSONGROUPTEAM", "PERSONGROUPTEAM", " PERSONGROUP='" + PERSONGROUP + "'");
    if (!(SetRemote.isEmpty()))
      for (int i = 0; i < SetRemote.count(); ++i) {
        String personid = SetRemote.getMbo(i).getString("resppartygroup");
        MboSetRemote person = SetRemote.getMbo(i).getMboSet("$PERSON", "PERSON", " personid='" + personid + "'");
        Person p = (Person)person.getMbo(0);

        p.setValue("primarycalnum", thisMbo.getString("primarycalnum"));
      }
  }

  public MboSetRemote getList()
    throws MXException, RemoteException
  {
    return super.getList();
  }
}