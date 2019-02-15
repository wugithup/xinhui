package com.shuto.mam.app.safeedu;
/**  
com.shuto.mam.app.safeedu.FldStatuss  华中
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年7月27日 上午10:09:56
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

public class FldStatuss extends MboValueAdapter
{
  public FldStatuss(MboValue paramMboValue)
  {
    super(paramMboValue);
  }

  public void init() throws MXException, RemoteException {
    super.init();
    Mbo localMbo = getMboValue().getMbo();
    String str1 = localMbo.getThisMboSet().getApp();
    String str2 = localMbo.getString("status");
    if ("ST_SAFETY".equals(str1)) {
      if (("新建".equals(str2)) || ("退回修改".equals(str2))) {
        setAttributesReadOnly("ST_SAFEEUDINFO", new String[] { "TRAINDECS", "TRAINTIME" });
        localMbo.setFieldFlag("TRAINDECS", 7L, false);
        localMbo.setFieldFlag("TRAINTIME", 7L, false);
        localMbo.setFieldFlag("TRAINDECS", 128L, true);
        localMbo.setFieldFlag("TRAINTIME", 128L, true);
      }
      if ("待EHS部培训".equals(str2)) {
        setAttributesReadOnly("ST_SAFEEUDINFO", new String[] { "PXTIME" });
        localMbo.setFieldFlag("PXTIME", 128L, true);
        localMbo.setFieldFlag("PXTIME", 7L, false);
      }
      if ((!"待EHS部培训".equals(str2)) && (!"新建".equals(str2)) && (!"退回修改".equals(str2)))
        localMbo.setFlag(7L, true);
    }
  }

  public void setAttributesReadOnly(String paramString, String[] paramArrayOfString)
    throws RemoteException, MXException
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

