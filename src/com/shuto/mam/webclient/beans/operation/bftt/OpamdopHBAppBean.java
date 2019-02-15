package com.shuto.mam.webclient.beans.operation.bftt;
/**  
com.shuto.mam.webclient.beans.operation.bftt.OpamdopHBAppBean 华中
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年7月29日 下午3:57:45
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import com.shuto.mam.app.expand.AutoDateSiteNumHB;
import com.shuto.mam.app.expand.SiteLinkShort;
import com.shuto.mam.webclient.beans.base.CAppBean;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.AppInstance;

public class OpamdopHBAppBean extends CAppBean
{
  public synchronized boolean fetchRecordData()
    throws MXException, RemoteException
  {
    MboRemote localMboRemote = this.app.getAppBean().getMbo();
    String[] arrayOfString1 = { "NAME", "ZXPROFESS", "WZ_OPAMDOPZY", "ZYCD", "ZXPERSON", "TIME01", "TIME02", "STRING01", "STRING03", "STRING02" };
    String[] arrayOfString2 = { "ZXPERSON", "PERSONID04" };
    String[] arrayOfString3 = { "NAME", "ZXPROFESS", "WZ_OPAMDOPZY", "ZYCD", "TIME01", "TIME02", "TIME03", "TIME04", "YONGTU", "STRING30_01", "STRING30_02", "WZ_OPAMDOPTYPE", "STRING01", "STRING03", "STRING02" };

    String[] arrayOfString4 = { "TRJHPERSON", "TRZXPERSON" };
    if (localMboRemote != null) {
      String str = localMboRemote.getString("status");
      if ("已退出".equals(str)) {
        localMboRemote.setFieldFlag(arrayOfString1, 7L, true);
      }
      if ((!"已退出".equals(str)) && (!"新建".equals(str)) && (!"退回修改".equals(str)) && (!"待申请人指定执行人".equals(str)) && (!"申请人指定执行人".equals(str))) {
        localMboRemote.setFlag(7L, true);
      }
      if ("待申请人指定执行人".equals(str)) {
        localMboRemote.setFieldFlag(arrayOfString2, 128L, true);
        localMboRemote.setFieldFlag(arrayOfString3, 7L, true);
        localMboRemote.setFieldFlag(arrayOfString4, 7L, true);
      }
      if ("申请人指定执行人".equals(str)) {
        localMboRemote.setFieldFlag(arrayOfString2, 7L, true);
        localMboRemote.setFieldFlag(arrayOfString4, 128L, true);
        localMboRemote.setFieldFlag(arrayOfString3, 7L, true);
      }
    }
    return super.fetchRecordData();
  }

  public int SAVE() throws MXException, RemoteException
  {
    MboRemote localMboRemote = getMbo();

    if ((localMboRemote.getString("WZ_OPAMDOPCODE") == null) || ("".equals(localMboRemote.getString("WZ_OPAMDOPCODE"))))
    {
      createOpamdopCode();
    }
    return super.SAVE();
  }

  public void createOpamdopCode()
    throws RemoteException, MXException
  {
    MboRemote localMboRemote = getMbo();

    String str1 = localMboRemote.getString("siteid");

    String str2 = localMboRemote.getString("orgid");
    String str3 = localMboRemote.getString("ZXPROFESS");
    SiteLinkShort localSiteLinkShort = new SiteLinkShort(localMboRemote.getThisMboSet());

    String str4 = localSiteLinkShort.getSiteEnShort(localMboRemote.getString("SITEID"));

    AutoDateSiteNumHB localAutoDateSiteNum = new AutoDateSiteNumHB(localMboRemote.getThisMboSet());

    String str5 = localMboRemote.getThisMboSet().getName();

    String str6 = "WZ_OPAMDOPCODE";
    Date localDate = MXServer.getMXServer().getDate();
    int i = localAutoDateSiteNum.getNextAutoDateSiteNum(str1, str2, str5, str6, localDate);

    String str7 = "";
    str7 = str4 + "-" + "BHTT" + "-" + str3 + "-" + new SimpleDateFormat("yyyyMM").format(localDate) + new DecimalFormat("000").format(i);

    localMboRemote.setValue("WZ_OPAMDOPCODE", str7, 11L);
  }
}