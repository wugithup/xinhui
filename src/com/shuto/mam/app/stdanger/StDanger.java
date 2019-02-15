package com.shuto.mam.app.stdanger;
/**  
com.shuto.mam.app.stdanger.StDanger 华中
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年7月27日 下午3:46:51
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

public class StDanger extends Mbo
  implements StDangerRemote
{
  public StDanger(MboSet paramMboSet)
    throws RemoteException
  {
    super(paramMboSet);
  }

  public void init()
    throws MXException
  {
    super.init();
    String str1 = "";
    String str2 = "";
    try {
      str1 = getString("status");
      str2 = getString("DANGERTYPE");
      if ("ST_DANGER".equals(getThisMboSet().getApp())) {
        if (("新建".equals(str1)) || ("退回".equals(str1)) || (isNew())) {
          try {
            setValue("isapp", "隐患排查", 11L);
            setAttributesReadOnly("ST_DANGER", new String[] { "DESCRIPTION", "DANGERDESC", "DANGERDEPT", "PROFESSIONAL", "ASSESSMENTYDJ", "CONSEQUENCE" });
          }
          catch (RemoteException localRemoteException1)
          {
            localRemoteException1.printStackTrace();
          }
          setFieldFlag("DESCRIPTION", 7L, false);
          setFieldFlag("DANGERDESC", 7L, false);
          setFieldFlag("DANGERDEPT", 7L, false);
          setFieldFlag("PROFESSIONAL", 7L, false);
          setFieldFlag("ASSESSMENTYDJ", 7L, false);
          setFieldFlag("CONSEQUENCE", 7L, false);

          setFieldFlag("DESCRIPTION", 128L, true);
          setFieldFlag("DANGERDESC", 128L, true);
          setFieldFlag("DANGERDEPT", 128L, true);
          setFieldFlag("PROFESSIONAL", 128L, true);
          setFieldFlag("ASSESSMENTYDJ", 128L, true);
          setFieldFlag("CONSEQUENCE", 128L, true);
        }

        if ("待预评估部门专工审核".equals(str1)) {
          try {
            setAttributesReadOnly("ST_DANGER", new String[] { "ASSESSMENTZGDESC" });
          }
          catch (RemoteException localRemoteException2) {
            localRemoteException2.printStackTrace();
          }
          setFieldFlag("ASSESSMENTZGDESC", 7L, false);
          setFieldFlag("ASSESSMENTZGDESC", 128L, true);
        }
        if ("待预评估部门分管领导审核".equals(str1)) {
          try {
            setAttributesReadOnly("ST_DANGER", new String[] { "ASSESSMENTLDDESC" });
          }
          catch (RemoteException localRemoteException3) {
            localRemoteException3.printStackTrace();
          }
          setFieldFlag("ASSESSMENTLDDESC", 7L, false);
          setFieldFlag("ASSESSMENTLDDESC", 128L, true);
        }
        if (("待经营策划部评级".equals(str1)) || ("待EHS部评级".equals(str1))) {
          try {
            setAttributesReadOnly("ST_DANGER", new String[] { "ASSESSMENTDJ", "DANGERTYPE" });
          }
          catch (RemoteException localRemoteException4) {
            localRemoteException4.printStackTrace();
          }
          setFieldFlag("ASSESSMENTDJ", 7L, false);
          setFieldFlag("DANGERTYPE", 7L, false);
          setFieldFlag("DANGERTYPE", 128L, true);
        }
        if (("待经营策划部评级".equals(str1)) && ("重大事故隐患".equals(str2))) {
          try {
            setAttributesReadOnly("ST_DANGER", new String[] { "PLANLDDESC", "DANGERTYPE" });
          }
          catch (RemoteException localRemoteException5) {
            localRemoteException5.printStackTrace();
          }
          setFieldFlag("PLANLDDESC", 7L, false);
          setFieldFlag("DANGERTYPE", 7L, false);
          setFieldFlag("PLANLDDESC", 128L, true);
          setFieldFlag("DANGERTYPE", 128L, true);
        }

        if (("待经营策划部评级".equals(str1)) && ("一般事故隐患".equals(str2))) {
          try {
            setAttributesReadOnly("ST_DANGER", new String[] { "DEPTZRR", "PLANSAFEHBZYSDESC", "DANGERTYPE" });
          }
          catch (RemoteException localRemoteException6) {
            localRemoteException6.printStackTrace();
          }

          setFieldFlag("PLANSAFEHBZYSDESC", 7L, false);
          setFieldFlag("DANGERTYPE", 7L, false);
          setFieldFlag("PLANSAFEHBZYSDESC", 128L, true);
          setFieldFlag("DANGERTYPE", 128L, true);
        }
        if ("待公司领导核定".equals(str1)) {
          try {
            setAttributesReadOnly("ST_DANGER", new String[] { "MAXDANGEROPINION" });
          }
          catch (RemoteException localRemoteException7) {
            localRemoteException7.printStackTrace();
          }
          setFieldFlag("MAXDANGEROPINION", 7L, false);
          setFieldFlag("MAXDANGEROPINION", 128L, true);
        }

        if ("待责任部门领导分配任务".equals(str1)) {
          try {
            setAttributesReadOnly("ST_DANGER", new String[] { "MANGEPERSON", "MANAGEBEGINTIME", "MANAGEENDTIME" });
          }
          catch (RemoteException localRemoteException8)
          {
            localRemoteException8.printStackTrace();
          }
          setFieldFlag("MANGEPERSON", 7L, false);
          setFieldFlag("MANAGEBEGINTIME", 7L, false);
          setFieldFlag("MANAGEENDTIME", 7L, false);

          setFieldFlag("MANGEPERSON", 128L, true);
          setFieldFlag("MANAGEBEGINTIME", 128L, true);
          setFieldFlag("MANAGEENDTIME", 128L, true);
        }
        if ("待治理人治理".equals(str1)) {
          try {
            setAttributesReadOnly("ST_DANGER", new String[] { "MANAGEWZ", "MANAGETIME", "MANAGETARGET", "MANAGEMONEY", "MANAGESITUATION", "MANGEDEPT" });
          }
          catch (RemoteException localRemoteException9)
          {
            localRemoteException9.printStackTrace();
          }
          setFieldFlag("MANAGEWZ", 7L, false);
          setFieldFlag("MANAGETIME", 7L, false);
          setFieldFlag("MANAGETARGET", 7L, false);
          setFieldFlag("MANAGEMONEY", 7L, false);
          setFieldFlag("MANAGESITUATION", 7L, false);

          setFieldFlag("MANGEDEPT", 7L, false);

          setFieldFlag("MANGEDEPT", 128L, true);
          setFieldFlag("MANAGESITUATION", 128L, true);
        }

        if ("待部门负责人分配任务".equals(str1))
        {
          try {
            setAttributesReadOnly("ST_DANGER", new String[] { "MANGEPERSON", "MANAGEBEGINTIME", "MANAGEENDTIME" });
          }
          catch (RemoteException localRemoteException10)
          {
            localRemoteException10.printStackTrace();
          }
          setFieldFlag("MANGEPERSON", 7L, false);
          setFieldFlag("MANAGEBEGINTIME", 7L, false);
          setFieldFlag("MANAGEENDTIME", 7L, false);

          setFieldFlag("MANGEPERSON", 128L, true);
          setFieldFlag("MANAGEBEGINTIME", 128L, true);
          setFieldFlag("MANAGEENDTIME", 128L, true);
        }

        if ("待申请验收".equals(str1)) {
          try {
            setAttributesReadOnly("ST_DANGER", new String[] { "CHECKDEPT", "CHECKOPINION" });
          }
          catch (RemoteException localRemoteException11) {
            localRemoteException11.printStackTrace();
          }
          setFieldFlag("CHECKDEPT", 7L, false);
          setFieldFlag("CHECKOPINION", 7L, false);

          setFieldFlag("CHECKDEPT", 128L, true);
          setFieldFlag("CHECKOPINION", 128L, true);
        }

        if ("待经营策划部提供奖励意见".equals(str1)) {
          try {
            setAttributesReadOnly("ST_DANGER", new String[] { "AWARDJYCH" });
          }
          catch (RemoteException localRemoteException12) {
            localRemoteException12.printStackTrace();
          }
          setFieldFlag("AWARDJYCH", 7L, false);
          setFieldFlag("AWARDJYCH", 128L, true);
        }

        if ("待公司领导提供奖励意见".equals(str1)) {
          try {
            setAttributesReadOnly("ST_DANGER", new String[] { "AWARDLD" });
          }
          catch (RemoteException localRemoteException13) {
            localRemoteException13.printStackTrace();
          }
          setFieldFlag("AWARDLD", 7L, false);
          setFieldFlag("AWARDLD", 128L, true);
        }

        if ("关闭".equals(str1)) {
          try {
            setAttributesReadOnly("ST_DANGER", new String[0]);
          } catch (RemoteException localRemoteException14) {
            localRemoteException14.printStackTrace();
          }
        }
        if (("完成".equals(str1)) || ("待安监专工归档".equals(str1)) || ("待EHS部归档".equals(str1))) {
          try {
            setAttributesReadOnly("ST_DANGER", new String[0]);
          } catch (RemoteException localRemoteException15) {
            localRemoteException15.printStackTrace();
          }

        }

      }

    }
    catch (RemoteException localRemoteException16)
    {
      localRemoteException16.printStackTrace();
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