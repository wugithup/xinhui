package com.shuto.mam.webclient.beans.rl.rlshm;
/**  
com.shuto.mam.webclient.beans.rl.rlshm.FyRlShmDataBean 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年9月14日 上午11:10:25
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import java.sql.SQLException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import com.shuto.mam.fycrp.PhoneMessage;

public class FyRlShmDataBean extends RlShmDataBean
{
  public void RC()
    throws RemoteException, MXException
  {
    DataBean localDataBean = this.app.getDataBean("RCDJ");
    MboSetRemote localMboSetRemote1 = this.app.getDataBean("RCDJ").getMboSet();
    MboRemote localMboRemote1 = null;
    PhoneMessage localPhoneMessage = null;

    MboRemote localMboRemote2 = localDataBean.getMbo(localDataBean.getCurrentRow());
    String str1 = localMboRemote2.getString("LX");
    String str2 = localMboRemote2.getString("RLPZ");
    int i = localMboRemote2.getInt("CLNUM");

    if (("副产品".equals(str1)) && ("粗灰".equals(str2)) && (i > 0) && (i <= 31))
    {
      System.out.println("粗灰当前车辆编码是:        " + i);

      int j = i + 1;
      int k = j + 1;
      int l = k + 1;
      if (i == 29) {
        l = 1;
      }

      if (i == 30) {
        k = 1;
        l = 2;
      }

      if (i == 31) {
        j = 1;
        k = 2;
        l = 3;
      }

      System.out.println("粗灰需要通知的车辆编码是:        " + j + ";   " + k + ";   " + l);

      MboSetRemote localMboSetRemote2 = MXServer.getMXServer().getMboSet("CLDXTZ", localMboSetRemote1.getUserInfo());

      localMboSetRemote1.close();
      localMboSetRemote2.setWhere("rlpz='粗灰' and  clbm in  ( '" + j + "','" + k + "','" + l + "')");

      System.out.println(localMboSetRemote2.count() + "    粗灰通知车辆总数");
      localMboSetRemote2.reset();
      try
      {
        for (int i1 = 0; (localMboRemote1 = localMboSetRemote2.getMbo(i1)) != null; i1++)
        {
          String str3 = localMboRemote1.getString("LXDH");
          String str4 = localMboRemote1.getString("LXR");

          System.out.println("粗灰通知第" + (i1 + 1) + "车辆联系人：" + str4 + ";  联系电话        " + str3);

          localPhoneMessage = new PhoneMessage();

          localPhoneMessage.addMessage(str4, "运煤车辆", str4, "ADMIN", "经营策划部", "EAM系统", str3, "粗灰车辆入厂通知：目前 " + i + "号车辆已进厂，请" + j + "号,   " + k + "   号,    " + l + "    号车辆做准备.");

          localPhoneMessage.commit();
        }

        localMboSetRemote2.close();
      }
      catch (SQLException localSQLException1)
      {
        localSQLException1.printStackTrace();
      }
      catch (ClassNotFoundException localClassNotFoundException1) {
        localClassNotFoundException1.printStackTrace();
      }

    }

    if (("副产品".equals(str1)) && ("细灰".equals(str2)) && (i > 0) && (i <= 11))
    {
      System.out.println("细灰当前车辆编码是:        " + i);

      int j = i + 1;
      int k = j + 1;
      int l = k + 1;
      if (i == 8) {
        l = 1;
      }

      if (i == 9) {
        k = 1;
        l = 2;
      }

      if (i == 11) {
        j = 1;
        k = 2;
        l = 3;
      }

      System.out.println("细灰需要通知的车辆编码是:        " + j + ";   " + k + ";   " + l);

      MboSetRemote localMboSetRemote2 = MXServer.getMXServer().getMboSet("CLDXTZ", localMboSetRemote1.getUserInfo());

      localMboSetRemote1.close();

      localMboSetRemote2.setWhere("rlpz='细灰' and  clbm in  ( '" + j + "','" + k + "','" + l + "')");

      System.out.println(localMboSetRemote2.count() + "    细灰通知车辆总数");
      localMboSetRemote2.reset();
      try
      {
        for (int i2 = 0; (localMboRemote1 = localMboSetRemote2.getMbo(i2)) != null; i2++)
        {
          String str3 = localMboRemote1.getString("LXDH");
          String str4 = localMboRemote1.getString("LXR");

          System.out.println("细灰通知第" + (i2 + 1) + "车辆联系人：" + str4 + ";  联系电话        " + str3);

          localPhoneMessage = new PhoneMessage();

          localPhoneMessage.addMessage(str4, "运煤车辆", str4, "ADMIN", "经营策划部", "EAM系统", str3, "细灰车辆入厂通知：目前 " + i + "号车辆已进厂，请" + j + "号,   " + k + "   号,    " + l + "    号车辆做准备.");

          localPhoneMessage.commit();
        }

        localMboSetRemote2.close();
      }
      catch (SQLException localSQLException2)
      {
        localSQLException2.printStackTrace();
      }
      catch (ClassNotFoundException localClassNotFoundException2) {
        localClassNotFoundException2.printStackTrace();
      }

    }

    super.RC();
  }
}
