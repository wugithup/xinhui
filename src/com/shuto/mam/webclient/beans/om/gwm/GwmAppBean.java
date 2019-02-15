package com.shuto.mam.webclient.beans.om.gwm;
/**  
com.shuto.mam.webclient.beans.om.gwm.GwmAppBean 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月16日 上午9:34:24
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.util.MXRequiredFieldException;
import psdi.webclient.system.beans.AppBean;

public class GwmAppBean extends AppBean
{
  public int SAVE()
    throws MXException, RemoteException
  {
    super.SAVE();
    MboSetRemote localMboSetRemote1 = getMbo().getMboSet("GWMWORK");
    MboRemote localMboRemote1 = null;
    MboSetRemote localMboSetRemote2 = null;
    MboRemote localMboRemote2 = null;
    String str1 = "";
    String str2 = "";
    String str3 = getMbo().getString("STATUS");
    if (("新建".equals(str3)) && 
      (!localMboSetRemote1.isEmpty())) {
      for (int i = 0; i < localMboSetRemote1.count(); i++) {
        localMboRemote1 = localMboSetRemote1.getMbo(i);
        localMboSetRemote2 = localMboRemote1.getMboSet("WORKORDER");
        localMboRemote2 = localMboSetRemote2.getMbo(0);
        str1 = localMboRemote2.getString("STATUS");
//        String str4 = localMboRemote2.getString("CWO5");
//        if (("关闭".equals(str1)) || ("已终结".equals(str1)) || (!"N".equals(str4)))
          if (("已关闭".equals(str1)) || ("已终结".equals(str1)))
          continue;
        str2 = str2 + " " + localMboRemote1.getString("WONUM");
      }

      if (!"".equals(str2)) {
        throw new MXRequiredFieldException("提示 ", " 单号为" + str2 + " 的相关工作票没有结束，请您及时处理。");
      }

    }

    return 1;
  }

  public void INSTALL() throws RemoteException, MXException {
    String str1 = getMbo().getString("STATUS");

    if ("新建".equals(str1)) {
      super.SAVE();
      MboSetRemote localMboSetRemote = getMbo().getMboSet("GWMWORK");
      if (localMboSetRemote.isEmpty()) {
        String str2 = getMbo().getString("ISQX");
        if ("N".equals(str2)) {
          throw new MXRequiredFieldException("提示 ", " 请至少填写一个工作票！");
        }
      }
      getMbo().setValue("STATUS", "已安装", 11L);

      SAVE();
    }
  }

  public void UNINSTALL() throws RemoteException, MXException {
    String str1 = getMbo().getString("STATUS");

    if ("已安装".equals(str1)) {
      super.SAVE();
      MboSetRemote localMboSetRemote = getMbo().getMboSet("GWMWORK");
      String str2 = getMbo().getString("ISQX");
      if (localMboSetRemote.isEmpty()) {
        if ("N".equals(str2)) {
          throw new MXRequiredFieldException("提示 ", " 请至少填写一个工作票！");
        }
        getMbo().setValue("STATUS", "关闭", 11L);

        SAVE();
      }
      else {
        MboRemote localMboRemote = null;
        int i = 0;
        for (int j = 0; j < localMboSetRemote.count(); j++) {
          localMboRemote = localMboSetRemote.getMbo(j);
          String str3 = localMboRemote.getMboSet("WORKORDER").getMbo(0).getString("STATUS");

//          String str4 = localMboRemote.getMboSet("WORKORDER").getMbo(0).getString("CWO5");

//          if (("关闭".equals(str3)) || (!"N".equals(str4)) || ("已终结".equals(str3)) || ("已作废".equals(str3)) || ("已取消".equals(str3)))
          if (("已关闭".equals(str3)) || ("已终结".equals(str3)) || ("已作废".equals(str3)) || ("已取消".equals(str3)))
          {
            continue;
          }
          i += 1;
        }

        if (i != 0) {
          if ("N".equals(str2)) {
            throw new MXRequiredFieldException("提示 ", " 请将相关工作票全部结束后再执行此操作！");
          }

          getMbo().setValue("STATUS", "关闭", 11L);

          SAVE();
        }
        else {
          getMbo().setValue("STATUS", "关闭", 11L);

          SAVE();
        }
      }
    }
  }
}
