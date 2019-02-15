package com.shuto.mam.app.sbpj;
/**  
com.shuto.mam.app.sbpj.FldZhuanye 华南
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年6月21日 下午3:04:02
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import com.ibm.ism.content.mriu.StringUtil;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class FldZhuanye extends MboValueAdapter
{
  private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

  public FldZhuanye(MboValue mbovalue) {
    super(mbovalue);
  }

  public void action() throws MXException, RemoteException {
    super.action();

    MboRemote mbo1 = getMboValue().getMbo();
    String zhuanye = getMboValue().getString();
    String siteid = mbo1.getString("siteid");
    String month = this.sdf.format(getMboValue("datemonth").getDate());

    if ((month.equalsIgnoreCase("")) || (month == null)) {
      throw new MXApplicationException("sbpj", "请先填写月份");
    }
    if (!StringUtil.isEmpty(zhuanye)) {
      MboSetRemote cz = mbo1.getMboSet("SBPJ");
      cz.deleteAll(11L);

      MboSetRemote msr = getMboValue().getMbo().getMboSet("$tme", "SBPJDEMO", "zhuanye='" + zhuanye + "' and siteid = '" + siteid + "' ");
      for (int i = 0; i < msr.count(); i++) {
        String locations = msr.getMbo(i).getString("DESCRIPTION");
        String qxzys = msr.getMbo(i).getString("QXZY");
        String[] location = locations.split(",");
        String[] qxzy = qxzys.split(",");
        int countnum1 = 0;
        int countnum2 = 0;
        int countnum3 = 0;
        for (int j = 0; j < location.length; j++) {
          for (int m = 0; m < qxzy.length; m++) {
            MboSetRemote sr1 = getMboValue().getMbo().getMboSet("$sr1", "sr", 
              "    to_char(reportdate,'yyyy')=substr('" + month + "',1,4) " + 
              "and to_char(reportdate,'mm')=substr('" + month + "',6,2) " + 
              "and REPORTEDPRIORITY=1 and source='缺陷' and siteid='" + siteid + "' " + 
              "and location like '" + location[j] + "%' and profession='" + qxzy[m] + "'");

            MboSetRemote sr2 = getMboValue().getMbo().getMboSet("$sr2", "sr", 
              "    to_char(reportdate,'yyyy')=substr('" + month + "',1,4) " + 
              "and to_char(reportdate,'mm')=substr('" + month + "',6,2) " + 
              "and REPORTEDPRIORITY=2 and source='缺陷' and siteid='" + siteid + "' " + 
              "and location like '" + location[j] + "%' and profession='" + qxzy[m] + "'");

            MboSetRemote sr3 = getMboValue().getMbo().getMboSet("$sr3", "sr", 
              "    to_char(reportdate,'yyyy')=substr('" + month + "',1,4) " + 
              "and to_char(reportdate,'mm')=substr('" + month + "',6,2) " + 
              "and REPORTEDPRIORITY=3 and source='缺陷' and siteid='" + siteid + "' " + 
              "and location like '" + location[j] + "%' and profession='" + qxzy[m] + "'");

            int srnum1 = sr1.count();
            int srnum2 = sr2.count();
            int srnum3 = sr3.count();
            countnum1 += srnum1;
            countnum2 += srnum2;
            countnum3 += srnum3;
          }
        }
        MboRemote xxx = cz.add();
        xxx.setValue("FJMC", msr.getMbo(i).getString("FJMC"));
        xxx.setValue("XTPF", 100 - countnum1 * 50 - countnum2 * 25 - 
          countnum3 > 0 ? 100 - countnum1 * 50 - countnum2 * 25 - 
          countnum3 : 0);
        xxx.setValue("SBPJID", getMboValue().getMbo().getInt("SBPJID"));
      }
    }
  }
}