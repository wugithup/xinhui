package com.shuto.mam.app.threeedu;
/**  
com.shuto.mam.app.threeedu.ThreeEdu 华中
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年7月27日 下午10:18:48
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
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

public class ThreeEdu extends Mbo
  implements ThreeEduRemote
{
  public ThreeEdu(MboSet paramMboSet)
    throws RemoteException
  {
    super(paramMboSet);
  }

  public void init() throws MXException
  {
    String str = "";
    try {
      str = getString("status");
    } catch (RemoteException localRemoteException1) {
      localRemoteException1.printStackTrace();
    }
    if (("新建".equals(str)) || ("退回".equals(str))) {
      try {
        setAttributesReadOnly("ST_THREESAFE", new String[] { "PROFESSION", "NAME", "SEX", "BIRTHDAY", "WORKNAME", "CULTURALDEGREE", "JOINTIME" });
      } catch (RemoteException localRemoteException2) {
        localRemoteException2.printStackTrace();
      }
      setFieldFlag("NAME", 7L, false);
      setFieldFlag("SEX", 7L, false);
      setFieldFlag("BIRTHDAY", 7L, false);
      setFieldFlag("WORKNAME", 7L, false);
      setFieldFlag("CULTURALDEGREE", 7L, false);
      setFieldFlag("JOINTIME", 7L, false);
      setFieldFlag("PROFESSION", 7L, false);

      setFieldFlag("NAME", 128L, true);
      setFieldFlag("SEX", 128L, true);
      setFieldFlag("BIRTHDAY", 128L, true);
      setFieldFlag("WORKNAME", 128L, true);
      setFieldFlag("CULTURALDEGREE", 128L, true);
      setFieldFlag("JOINTIME", 128L, true);
      setFieldFlag("PROFESSION", 128L, true);
    }
    if ("待EHS部审核".equals(str)) {
      try {
        setAttributesReadOnly("ST_THREESAFE", new String[] { "COMEDUBEGIN", "COMEDUEND", "COMEXAMBEGIN", "COMEXAMEND", "COMSCORE", "COMCHECKER", "COMPANYEDU" });
      }
      catch (RemoteException localRemoteException3) {
        localRemoteException3.printStackTrace();
      }
      setFieldFlag("COMEDUBEGIN", 7L, false);
      setFieldFlag("COMEDUEND", 7L, false);
      setFieldFlag("COMEXAMBEGIN", 7L, false);
      setFieldFlag("COMEXAMEND", 7L, false);
      setFieldFlag("COMSCORE", 7L, false);
      setFieldFlag("COMCHECKER", 7L, false);
      setFieldFlag("COMPANYEDU", 7L, false);

      setFieldFlag("COMEDUBEGIN", 128L, true);
      setFieldFlag("COMEDUEND", 128L, true);
      setFieldFlag("COMEXAMBEGIN", 128L, true);
      setFieldFlag("COMEXAMEND", 128L, true);
      setFieldFlag("COMSCORE", 128L, true);
      setFieldFlag("COMCHECKER", 128L, true);
      setFieldFlag("COMPANYEDU", 128L, true);
    }

    if ("待部门安全专工审核".equals(str)) {
      try {
        setAttributesReadOnly("ST_THREESAFE", new String[] { "DEPTEDUBEGIN", "DEPTEDUEND", "DEPTEXAMBEGIN", "DEPTEXAMEND", "DEPTEDUER", "DEPTSCORE", "DEPTCHECKER", "DEPTEDU" });
      }
      catch (RemoteException localRemoteException4) {
        localRemoteException4.printStackTrace();
      }
      setFieldFlag("DEPTEDUBEGIN", 7L, false);
      setFieldFlag("DEPTEDUEND", 7L, false);
      setFieldFlag("DEPTEXAMBEGIN", 7L, false);
      setFieldFlag("DEPTEXAMEND", 7L, false);
      setFieldFlag("DEPTEDUER", 7L, false);
      setFieldFlag("DEPTSCORE", 7L, false);
      setFieldFlag("DEPTCHECKER", 7L, false);
      setFieldFlag("DEPTEDU", 7L, false);

      setFieldFlag("DEPTEDUBEGIN", 128L, true);
      setFieldFlag("DEPTEDUEND", 128L, true);
      setFieldFlag("DEPTEXAMBEGIN", 128L, true);
      setFieldFlag("DEPTEXAMEND", 128L, true);
      setFieldFlag("DEPTEDUER", 128L, true);
      setFieldFlag("DEPTSCORE", 128L, true);
      setFieldFlag("DEPTCHECKER", 128L, true);
      setFieldFlag("DEPTEDU", 128L, true);
    }
    if ("待班组审核".equals(str)) {
      try {
        setAttributesReadOnly("ST_THREESAFE", new String[] { "TEAMEDUBEGIN", "TEAMEDUEND", "TEAMEXAMBEGIN", "TEAMCHECKEND", "TEAMCHECKSCORE", "TEAMCHECKER", "TEAMEDU" });
      }
      catch (RemoteException localRemoteException5) {
        localRemoteException5.printStackTrace();
      }
      setFieldFlag("TEAMEDUBEGIN", 7L, false);
      setFieldFlag("TEAMEDUEND", 7L, false);
      setFieldFlag("TEAMEXAMBEGIN", 7L, false);
      setFieldFlag("TEAMCHECKEND", 7L, false);
      setFieldFlag("TEAMCHECKSCORE", 7L, false);
      setFieldFlag("TEAMCHECKER", 7L, false);
      setFieldFlag("TEAMEDU", 7L, false);

      setFieldFlag("TEAMEDUBEGIN", 128L, true);
      setFieldFlag("TEAMEDUEND", 128L, true);
      setFieldFlag("TEAMEXAMBEGIN", 128L, true);
      setFieldFlag("TEAMCHECKEND", 128L, true);
      setFieldFlag("TEAMCHECKSCORE", 128L, true);
      setFieldFlag("TEAMCHECKER", 128L, true);
      setFieldFlag("TEAMEDU", 128L, true);
    }
    if (("关闭".equals(str)) || ("完成".equals(str)) || ("待信息部确认".equals(str)) || ("待人力资源部确认".equals(str)))
      try {
        setAttributesReadOnly("ST_THREESAFE", new String[] { "" });
      } catch (RemoteException localRemoteException6) {
        localRemoteException6.printStackTrace();
      }
  }

  public MboRemote duplicate()
    throws MXException, RemoteException
  {
    MboRemote localMboRemote = copy();
    return localMboRemote;
  }

  public void setAttributesReadOnly(String paramString, String[] paramArrayOfString)
    throws RemoteException, MXException
  {
    HashMap localHashMap = new HashMap();
    MboSetRemote localMboSetRemote = getMboSet("$maxattribute", "maxattribute", "objectname='" + paramString + "'");
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
      setFieldFlag(str, 7L, true);
    }

    localMboSetRemote.close();
  }
}