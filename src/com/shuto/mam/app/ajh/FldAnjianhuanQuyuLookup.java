package com.shuto.mam.app.ajh;

import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldAnjianhuanQuyuLookup extends MAXTableDomain
{
  public FldAnjianhuanQuyuLookup(MboValue paramMboValue)
    throws RemoteException, MXException
  {
    super(paramMboValue);

    String str1 = getMboValue().getMbo().getString("siteid");
    String str2 = "AJHQY";
    String str3 = null;
    str3 = "SITEID=:SITEID  and AREANUM=:" + getMboValue().getName();
    setMultiKeyWhereForLookup("SITEID=:SITEID ");
    setRelationship(str2, str3);
    if ("XZHP000".equals(str1))
      setLookupKeyMapInOrder(new String[] { getMboValue().getName(), "PGPERSON" }, new String[] { "AREANUM", "AJHQYDB" });
    else if ("YXHR000".equals(str1))
      setLookupKeyMapInOrder(new String[] { getMboValue().getName(), "PGPERSON" }, new String[] { "AREANUM", "AJHQYDB" });
    else if ("NJHX000".equals(str1))
      setLookupKeyMapInOrder(new String[] { getMboValue().getName(), "PGPERSON" }, new String[] { "AREANUM", "AJHQYDB" });
    else if ("HRNR000".equals(str1))
      setLookupKeyMapInOrder(new String[] { getMboValue().getName(), "PGPERSON" }, new String[] { "AREANUM", "AJHQYDB" });
    else
      setLookupKeyMapInOrder(new String[] { getMboValue().getName() }, new String[] { "AREANUM" });
  }
}