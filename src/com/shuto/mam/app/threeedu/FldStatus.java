package com.shuto.mam.app.threeedu;
/**  
com.shuto.mam.app.threeedu.FldStatus 华中
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年7月28日 上午9:27:47
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class FldStatus extends MboValueAdapter
{
  public FldStatus(MboValue paramMboValue)
  {
    super(paramMboValue);
  }

  public void action() throws MXException, RemoteException
  {
    super.action();
    Mbo localMbo = getMboValue().getMbo();
    String str = localMbo.getString("status");
    if ("新建".equals(str)) {
      setAttributesReadOnly("ST_THREESAFE", new String[] { "PROFESSION", "NAME", "SEX", "BIRTHDAY", "WORKNAME", "CULTURALDEGREE", "JOINTIME" });
    }
    localMbo.setFieldFlag("NAME", 7L, false);
    localMbo.setFieldFlag("SEX", 7L, false);
    localMbo.setFieldFlag("BIRTHDAY", 7L, false);
    localMbo.setFieldFlag("JOINTIME", 7L, false);
    localMbo.setFieldFlag("PROFESSION", 7L, false);
    localMbo.setFieldFlag("WORKNAME", 7L, false);
    localMbo.setFieldFlag("CULTURALDEGREE", 7L, false);

    localMbo.setFieldFlag("NAME", 128L, true);
    localMbo.setFieldFlag("SEX", 128L, true);
    localMbo.setFieldFlag("BIRTHDAY", 128L, true);
    localMbo.setFieldFlag("JOINTIME", 128L, true);
    localMbo.setFieldFlag("PROFESSION", 128L, true);
  }

  public void setAttributesReadOnly(String paramString, String[] paramArrayOfString) throws RemoteException, MXException
  {
    HashMap localHashMap = new HashMap();
    Mbo localMbo = getMboValue().getMbo();
    MboSetRemote localMboSetRemote = localMbo.getMboSet("$maxattribute", "maxattribute", "objectname='" + paramString + "'");
    if (localMboSetRemote != null)
      for (int i = 0; i < localMboSetRemote.count(); i++)
        localHashMap.put(localMboSetRemote.getMbo(i).getString("attributename").toUpperCase(), localMboSetRemote.getMbo(i).getString("attributename").toUpperCase());
    Object localObject;
    for (int i = 0; i < paramArrayOfString.length; i++) {
      localObject = paramArrayOfString[i];
      if (localHashMap.containsKey(((String)localObject).toUpperCase())) {
        localHashMap.remove(localObject);
      }
    }

    for (Iterator localIterator = localHashMap.entrySet().iterator(); localIterator.hasNext(); ) { localObject = (Map.Entry)localIterator.next();
      String str = (String)((Map.Entry)localObject).getKey();
      localMbo.setFieldFlag(str, 7L, true);
    }

    localMboSetRemote.close();
  }
}
