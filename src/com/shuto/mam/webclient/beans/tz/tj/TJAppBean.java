package com.shuto.mam.webclient.beans.tz.tj;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
/** 
 * @author  lzq
 * @date 创建时间：2017-3-24 上午9:31:27 
 * @since  原华南台账相关类
 */
public class TJAppBean extends AppBean
{
  public int selectqx()
    throws MXException, RemoteException
  {
    DataBean databean = this.app.getDataBean("1339658714061");
    MboSetRemote mbosetremote = databean.getMboSet();
    MboSetRemote person1 = this.app.getAppBean().getMbo().getMboSet("$person", "person", "personid='" + this.app.getAppBean().getMbo().getUserName() + "'");
    System.out.println("person1=" + this.app.getAppBean().getMbo().getUserName());
    System.out.println("person1.site=" + person1.getMbo(0).getString("locationsite"));
    MboSetRemote qxtj1 = this.app.getAppBean().getMbo().getMboSet("$qxtj", "qxtj", "siteid='" + person1.getMbo(0).getString("locationsite") + "'");
    mbosetremote.deleteAll();
    mbosetremote.save();
    String startdate = this.app.getAppBean().getString("tj_startdate");
    String enddate = this.app.getAppBean().getString("tj_enddate");
    System.out.println(startdate);
    System.out.println(enddate);

    MboSetRemote dqsy = this.app.getAppBean().getMbo().getMboSet("$sr", "sr", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and source='缺陷' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=reportedby)='一值'");
    MboSetRemote xqsy = this.app.getAppBean().getMbo().getMboSet("$sr2", "sr", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and source='缺陷'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=reportedby)='一值' and status in ('GZGB','GZZJ','已关闭')");
    MboRemote mboremotey = qxtj1.add();
    mboremotey.setValue("TJLB", "一值", 11L);
    mboremotey.setValue("DQS", dqsy.count() - xqsy.count(), 11L);
    mboremotey.setValue("XQS", xqsy.count(), 11L);
    mboremotey.setValue("QXZJ", dqsy.count(), 11L);

    MboSetRemote dqse = this.app.getAppBean().getMbo().getMboSet("$sr", "sr", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and source='缺陷' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=reportedby)='二值'");
    MboSetRemote xqse = this.app.getAppBean().getMbo().getMboSet("$sr2", "sr", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and source='缺陷'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=reportedby)='二值' and status in ('GZGB','GZZJ','已关闭')");
    MboRemote mboremotee = qxtj1.add();
    mboremotee.setValue("TJLB", "二值", 11L);
    mboremotee.setValue("DQS", dqse.count() - xqse.count(), 11L);
    mboremotee.setValue("XQS", xqse.count(), 11L);
    mboremotee.setValue("QXZJ", dqse.count(), 11L);

    MboSetRemote dqss = this.app.getAppBean().getMbo().getMboSet("$sr", "sr", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and source='缺陷' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=reportedby)='三值'");
    MboSetRemote xqss = this.app.getAppBean().getMbo().getMboSet("$sr2", "sr", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and source='缺陷'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=reportedby)='三值' and status in ('GZGB','GZZJ','已关闭')");
    MboRemote mboremotes = qxtj1.add();
    mboremotes.setValue("TJLB", "三值", 11L);
    mboremotes.setValue("DQS", dqss.count() - xqss.count(), 11L);
    mboremotes.setValue("XQS", xqss.count(), 11L);
    mboremotes.setValue("QXZJ", dqss.count(), 11L);

    MboSetRemote dqss4 = this.app.getAppBean().getMbo().getMboSet("$sr", "sr", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and source='缺陷' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=reportedby)='四值'");
    MboSetRemote xqss4 = this.app.getAppBean().getMbo().getMboSet("$sr2", "sr", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and source='缺陷'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=reportedby)='四值' and status in ('GZGB','GZZJ','已关闭')");
    MboRemote mboremotes4 = qxtj1.add();
    mboremotes4.setValue("TJLB", "四值", 11L);
    mboremotes4.setValue("DQS", dqss4.count() - xqss4.count(), 11L);
    mboremotes4.setValue("XQS", xqss4.count(), 11L);
    mboremotes4.setValue("QXZJ", dqss4.count(), 11L);

    MboSetRemote dqsw = this.app.getAppBean().getMbo().getMboSet("$sr", "sr", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and source='缺陷' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=reportedby)='五值'");
    MboSetRemote xqsw = this.app.getAppBean().getMbo().getMboSet("$sr2", "sr", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and source='缺陷'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=reportedby)='五值' and status in ('GZGB','GZZJ','已关闭')");
    MboRemote mboremotew = qxtj1.add();
    mboremotew.setValue("TJLB", "五值", 11L);
    mboremotew.setValue("DQS", dqsw.count() - xqsw.count(), 11L);
    mboremotew.setValue("XQS", xqsw.count(), 11L);
    mboremotew.setValue("QXZJ", dqsw.count(), 11L);

    MboSetRemote msr = this.app.getAppBean().getMbo().getMboSet("$alndomain", "alndomain", "domainid='PROFESSION' and siteid='" + person1.getMbo(0).getString("locationsite") + "'");
    for (int i = 0; i < msr.count(); ++i) {
      String zy = msr.getMbo(i).getString("value");
      MboSetRemote dqs1 = this.app.getAppBean().getMbo().getMboSet("$sr", "sr", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and source='缺陷' and profession= '" + zy + "' and siteid='" + person1.getMbo(0).getString("locationsite") + "'");
      MboSetRemote xqs2 = this.app.getAppBean().getMbo().getMboSet("$sr2", "sr", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and source='缺陷' and profession= '" + zy + "'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and status in ('GZGB','GZZJ','已关闭')");
      int dqs = dqs1.count();
      int xqs = xqs2.count();
      MboRemote mboremote = qxtj1.add();
      mboremote.setValue("TJLB", zy, 11L);
      mboremote.setValue("DQS", dqs - xqs, 11L);
      mboremote.setValue("XQS", xqs, 11L);
      mboremote.setValue("QXZJ", dqs, 11L);

      databean.refreshTable();
      this.sessionContext.queueRefreshEvent();
    }
    qxtj1.save();
    qxtj1.reset();
    this.app.getAppBean().save();
    return 1;
  }

  public int selectgzp()
    throws MXException, RemoteException
  {
    DataBean databean = this.app.getDataBean("1339659910202");
    MboSetRemote mbosetremote = databean.getMboSet();
    MboSetRemote person1 = this.app.getAppBean().getMbo().getMboSet("$person", "person", "personid='" + this.app.getAppBean().getMbo().getUserName() + "'");
    System.out.println("person1=" + this.app.getAppBean().getMbo().getUserName());
    System.out.println("person1.site=" + person1.getMbo(0).getString("locationsite"));
    MboSetRemote gzptj1 = this.app.getAppBean().getMbo().getMboSet("$gzptj", "gzptj", "siteid='" + person1.getMbo(0).getString("locationsite") + "'");
    mbosetremote.deleteAll();
    mbosetremote.save();

    DataBean databean3 = this.app.getDataBean("1339659919709");
    MboSetRemote mbosetremote3 = databean3.getMboSet();
    mbosetremote3.deleteAll();
    mbosetremote3.save();

    DataBean databean2 = this.app.getDataBean("1339659913050");
    MboSetRemote mbosetremote2 = databean2.getMboSet();
    mbosetremote2.deleteAll();
    mbosetremote2.save();

    DataBean databean4 = this.app.getDataBean("1339659927074");
    MboSetRemote mbosetremote4 = databean4.getMboSet();
    mbosetremote4.deleteAll();
    mbosetremote4.save();

    DataBean databean5 = this.app.getDataBean("1339659927103");
    MboSetRemote mbosetremote5 = databean5.getMboSet();
    mbosetremote5.deleteAll();
    mbosetremote5.save();

    DataBean databean6 = this.app.getDataBean("1339659927129");
    MboSetRemote mbosetremote6 = databean6.getMboSet();
    mbosetremote6.deleteAll();
    mbosetremote6.save();

    DataBean databean8 = this.app.getDataBean("1453797888876");
    MboSetRemote mbosetremote8 = databean8.getMboSet();
    mbosetremote8.deleteAll();
    mbosetremote8.save();

    String startdate = this.app.getAppBean().getString("tj_startdate");
    String enddate = this.app.getAppBean().getString("tj_enddate");
    System.out.println(startdate);
    System.out.println(enddate);

    MboSetRemote kga = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第一种工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='一值' and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zja = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第一种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='一值' and status in ('CLOSED')");
    MboSetRemote zfa = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第一种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='一值' and status in ('CANCEL')");
    MboSetRemote yqa = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第一种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='一值' and cwo28dt is not null");

    MboRemote mboremotea = gzptj1.add();
    mboremotea.setValue("PROFESSION", "一值", 11L);
    mboremotea.setValue("KGS", kga.count(), 11L);
    mboremotea.setValue("ZJS", zja.count(), 11L);
    mboremotea.setValue("ZFS", zfa.count(), 11L);
    mboremotea.setValue("YQS", yqa.count(), 11L);
    mboremotea.setValue("GZPLX", "电气第一种工作票", 11L);

    MboSetRemote kgb = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第一种工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='二值' and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjb = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第一种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='二值' and status in ('CLOSED')");
    MboSetRemote zfb = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第一种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='二值' and status in ('CANCEL')");
    MboSetRemote yqb = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第一种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='二值' and cwo28dt is not null");

    MboRemote mboremoteb = gzptj1.add();
    mboremoteb.setValue("PROFESSION", "二值", 11L);
    mboremoteb.setValue("KGS", kgb.count(), 11L);
    mboremoteb.setValue("ZJS", zjb.count(), 11L);
    mboremoteb.setValue("ZFS", zfb.count(), 11L);
    mboremoteb.setValue("YQS", yqb.count(), 11L);
    mboremoteb.setValue("GZPLX", "电气第一种工作票", 11L);

    MboSetRemote kgc = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第一种工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='三值'  and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjc = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第一种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='三值' and status in ('CLOSED')");
    MboSetRemote zfc = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第一种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='三值' and status in ('CANCEL')");
    MboSetRemote yqc = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第一种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='三值' and cwo28dt is not null");

    MboRemote mboremotec = gzptj1.add();
    mboremotec.setValue("PROFESSION", "三值", 11L);
    mboremotec.setValue("KGS", kgc.count(), 11L);
    mboremotec.setValue("ZJS", zjc.count(), 11L);
    mboremotec.setValue("ZFS", zfc.count(), 11L);
    mboremotec.setValue("YQS", yqc.count(), 11L);
    mboremotec.setValue("GZPLX", "电气第一种工作票", 11L);

    MboSetRemote kgd = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第一种工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='四值' and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjd = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第一种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='四值' and status in ('CLOSED')");
    MboSetRemote zfd = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第一种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='四值' and status in ('CANCEL')");
    MboSetRemote yqd = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第一种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='四值' and cwo28dt is not null");

    MboRemote mboremoted = gzptj1.add();
    mboremoted.setValue("PROFESSION", "四值", 11L);
    mboremoted.setValue("KGS", kgd.count(), 11L);
    mboremoted.setValue("ZJS", zjd.count(), 11L);
    mboremoted.setValue("ZFS", zfd.count(), 11L);
    mboremoted.setValue("YQS", yqd.count(), 11L);
    mboremoted.setValue("GZPLX", "电气第一种工作票", 11L);

    MboSetRemote kgw = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第一种工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='五值' and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjw = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第一种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='五值' and status in ('CLOSED')");
    MboSetRemote zfw = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第一种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='五值' and status in ('CANCEL')");
    MboSetRemote yqw = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第一种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='五值' and cwo28dt is not null");

    MboRemote mboremotew = gzptj1.add();
    mboremotew.setValue("PROFESSION", "五值", 11L);
    mboremotew.setValue("KGS", kgw.count(), 11L);
    mboremotew.setValue("ZJS", zjw.count(), 11L);
    mboremotew.setValue("ZFS", zfw.count(), 11L);
    mboremotew.setValue("YQS", yqw.count(), 11L);
    mboremotew.setValue("GZPLX", "电气第一种工作票", 11L);

    MboSetRemote msr = this.app.getAppBean().getMbo().getMboSet("$alndomain", "alndomain", "domainid='PROFESSION' and siteid='" + person1.getMbo(0).getString("locationsite") + "'");
    for (int i = 0; i < msr.count(); ++i) {
      String zy = msr.getMbo(i).getString("value");
      MboSetRemote kg1 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第一种工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  reportdep='" + zy + "' and status not in ('CANCEL','已取消','等待批准')");
      MboSetRemote zj1 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第一种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and reportdep='" + zy + "' and status in ('CLOSED')");
      MboSetRemote zf1 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第一种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and reportdep='" + zy + "'  and status in ('CANCEL')");
      MboSetRemote yq1 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第一种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and reportdep='" + zy + "'  and cwo28dt is not null");
      MboRemote mboremote1 = gzptj1.add();
      mboremote1.setValue("PROFESSION", zy, 11L);
      mboremote1.setValue("KGS", kg1.count(), 11L);
      mboremote1.setValue("ZJS", zj1.count(), 11L);
      mboremote1.setValue("ZFS", zf1.count(), 11L);
      mboremote1.setValue("YQS", yq1.count(), 11L);
      mboremote1.setValue("GZPLX", "电气第一种工作票", 11L);

      databean.refreshTable();
      this.sessionContext.queueRefreshEvent();
    }

    MboSetRemote kga2 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第二种工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='一值'  and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zja2 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第二种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='一值' and status in ('CLOSED')");
    MboSetRemote zfa2 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第二种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='一值' and status in ('CANCEL')");
    MboSetRemote yqa2 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第二种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='一值' and cwo28dt is not null");

    MboRemote mboremotea2 = gzptj1.add();
    mboremotea2.setValue("PROFESSION", "一值", 11L);
    mboremotea2.setValue("KGS", kga2.count(), 11L);
    mboremotea2.setValue("ZJS", zja2.count(), 11L);
    mboremotea2.setValue("ZFS", zfa2.count(), 11L);
    mboremotea2.setValue("YQS", yqa2.count(), 11L);
    mboremotea2.setValue("GZPLX", "电气第二种工作票", 11L);

    MboSetRemote kgb2 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第二种工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='二值' and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjb2 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第二种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='二值' and status in ('CLOSED')");
    MboSetRemote zfb2 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第二种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='二值' and status in ('CANCEL')");
    MboSetRemote yqb2 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第二种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='二值' and cwo28dt is not null");

    MboRemote mboremoteb2 = gzptj1.add();
    mboremoteb2.setValue("PROFESSION", "二值", 11L);
    mboremoteb2.setValue("KGS", kgb2.count(), 11L);
    mboremoteb2.setValue("ZJS", zjb2.count(), 11L);
    mboremoteb2.setValue("ZFS", zfb2.count(), 11L);
    mboremoteb2.setValue("YQS", yqb2.count(), 11L);
    mboremoteb2.setValue("GZPLX", "电气第二种工作票", 11L);

    MboSetRemote kgc2 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第二种工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='三值' and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjc2 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第二种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='三值' and status in ('CLOSED')");
    MboSetRemote zfc2 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第二种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='三值' and status in ('CANCEL')");
    MboSetRemote yqc2 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第二种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='三值' and cwo28dt is not null");

    MboRemote mboremotec2 = gzptj1.add();
    mboremotec2.setValue("PROFESSION", "三值", 11L);
    mboremotec2.setValue("KGS", kgc2.count(), 11L);
    mboremotec2.setValue("ZJS", zjc2.count(), 11L);
    mboremotec2.setValue("ZFS", zfc2.count(), 11L);
    mboremotec2.setValue("YQS", yqc2.count(), 11L);
    mboremotec2.setValue("GZPLX", "电气第二种工作票", 11L);

    MboSetRemote kgd2 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第二种工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='四值'  and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjd2 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第二种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='四值' and status in ('CLOSED')");
    MboSetRemote zfd2 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第二种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='四值'  and status in ('CANCEL')");
    MboSetRemote yqd2 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第二种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='四值' and cwo28dt is not null");

    MboRemote mboremoted2 = gzptj1.add();
    mboremoted2.setValue("PROFESSION", "四值", 11L);
    mboremoted2.setValue("KGS", kgd2.count(), 11L);
    mboremoted2.setValue("ZJS", zjd2.count(), 11L);
    mboremoted2.setValue("ZFS", zfd2.count(), 11L);
    mboremoted2.setValue("YQS", yqd2.count(), 11L);
    mboremoted2.setValue("GZPLX", "电气第二种工作票", 11L);

    MboSetRemote kgw2 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第二种工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='五值'  and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjw2 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第二种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='五值' and status in ('CLOSED')");
    MboSetRemote zfw2 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第二种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='五值'  and status in ('CANCEL')");
    MboSetRemote yqw2 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第二种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='五值' and cwo28dt is not null");

    MboRemote mboremotew2 = gzptj1.add();
    mboremotew2.setValue("PROFESSION", "五值", 11L);
    mboremotew2.setValue("KGS", kgw2.count(), 11L);
    mboremotew2.setValue("ZJS", zjw2.count(), 11L);
    mboremotew2.setValue("ZFS", zfw2.count(), 11L);
    mboremotew2.setValue("YQS", yqw2.count(), 11L);
    mboremotew2.setValue("GZPLX", "电气第二种工作票", 11L);

    MboSetRemote msr2 = this.app.getAppBean().getMbo().getMboSet("$alndomain", "alndomain", "domainid='PROFESSION' and siteid='" + person1.getMbo(0).getString("locationsite") + "'");
    for (int i = 0; i < msr2.count(); ++i) {
      String zy = msr2.getMbo(i).getString("value");
      MboSetRemote kg12 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第二种工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  reportdep='" + zy + "'  and status not in ('CANCEL','已取消','等待批准')");
      MboSetRemote zj12 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第二种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and reportdep='" + zy + "' and status in ('CLOSED')");
      MboSetRemote zf12 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第二种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and reportdep='" + zy + "'   and status in ('CANCEL')");
      MboSetRemote yq12 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='电气第二种工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and reportdep='" + zy + "'  and cwo28dt is not null");
      MboRemote mboremote12 = gzptj1.add();
      mboremote12.setValue("PROFESSION", zy, 11L);
      mboremote12.setValue("KGS", kg12.count(), 11L);
      mboremote12.setValue("ZJS", zj12.count(), 11L);
      mboremote12.setValue("ZFS", zf12.count(), 11L);
      mboremote12.setValue("YQS", yq12.count(), 11L);
      mboremote12.setValue("GZPLX", "电气第二种工作票", 11L);

      databean2.refreshTable();
      this.sessionContext.queueRefreshEvent();
    }

    MboSetRemote kga3 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热力机械工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='一值'  and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zja3 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热力机械工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='一值' and status in ('CLOSED')");
    MboSetRemote zfa3 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热力机械工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='一值'  and status in ('CANCEL')");
    MboSetRemote yqa3 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热力机械工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='一值' and cwo28dt is not null");

    MboRemote mboremotea3 = gzptj1.add();
    mboremotea3.setValue("PROFESSION", "一值", 11L);
    mboremotea3.setValue("KGS", kga3.count(), 11L);
    mboremotea3.setValue("ZJS", zja3.count(), 11L);
    mboremotea3.setValue("ZFS", zfa3.count(), 11L);
    mboremotea3.setValue("YQS", yqa3.count(), 11L);
    mboremotea3.setValue("GZPLX", "热力机械工作票", 11L);

    MboSetRemote kgb3 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热力机械工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='二值' and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjb3 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热力机械工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='二值' and status in ('CLOSED')");
    MboSetRemote zfb3 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热力机械工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='二值'  and status in ('CANCEL')");
    MboSetRemote yqb3 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热力机械工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='二值' and cwo28dt is not null");

    MboRemote mboremoteb3 = gzptj1.add();
    mboremoteb3.setValue("PROFESSION", "二值", 11L);
    mboremoteb3.setValue("KGS", kgb3.count(), 11L);
    mboremoteb3.setValue("ZJS", zjb3.count(), 11L);
    mboremoteb3.setValue("ZFS", zfb3.count(), 11L);
    mboremoteb3.setValue("YQS", yqb3.count(), 11L);
    mboremoteb3.setValue("GZPLX", "热力机械工作票", 11L);

    MboSetRemote kgc3 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热力机械工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='三值'  and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjc3 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热力机械工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='三值' and status in ('CLOSED')");
    MboSetRemote zfc3 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热力机械工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='三值' and status in ('CANCEL')");
    MboSetRemote yqc3 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热力机械工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='三值' and cwo28dt is not null");

    MboRemote mboremotec3 = gzptj1.add();
    mboremotec3.setValue("PROFESSION", "三值", 11L);
    mboremotec3.setValue("KGS", kgc3.count(), 11L);
    mboremotec3.setValue("ZJS", zjc3.count(), 11L);
    mboremotec3.setValue("ZFS", zfc3.count(), 11L);
    mboremotec3.setValue("YQS", yqc3.count(), 11L);
    mboremotec3.setValue("GZPLX", "热力机械工作票", 11L);

    MboSetRemote kgd3 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热力机械工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='四值' and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjd3 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热力机械工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='四值' and status in ('CLOSED')");
    MboSetRemote zfd3 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热力机械工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='四值' and status in ('CANCEL')");
    MboSetRemote yqd3 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热力机械工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='四值' and cwo28dt is not null");

    MboRemote mboremoted3 = gzptj1.add();
    mboremoted3.setValue("PROFESSION", "四值", 11L);
    mboremoted3.setValue("KGS", kgd3.count(), 11L);
    mboremoted3.setValue("ZJS", zjd3.count(), 11L);
    mboremoted3.setValue("ZFS", zfd3.count(), 11L);
    mboremoted3.setValue("YQS", yqd3.count(), 11L);
    mboremoted3.setValue("GZPLX", "热力机械工作票", 11L);

    MboSetRemote kgw3 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热力机械工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='五值' and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjw3 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热力机械工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='五值' and status in ('CLOSED')");
    MboSetRemote zfw3 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热力机械工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='五值' and status in ('CANCEL')");
    MboSetRemote yqw3 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热力机械工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='五值' and cwo28dt is not null");

    MboRemote mboremotew3 = gzptj1.add();
    mboremotew3.setValue("PROFESSION", "五值", 11L);
    mboremotew3.setValue("KGS", kgw3.count(), 11L);
    mboremotew3.setValue("ZJS", zjw3.count(), 11L);
    mboremotew3.setValue("ZFS", zfw3.count(), 11L);
    mboremotew3.setValue("YQS", yqw3.count(), 11L);
    mboremotew3.setValue("GZPLX", "热力机械工作票", 11L);

    MboSetRemote msr3 = this.app.getAppBean().getMbo().getMboSet("$alndomain", "alndomain", "domainid='PROFESSION' and siteid='" + person1.getMbo(0).getString("locationsite") + "'");
    for (int i = 0; i < msr3.count(); ++i) {
      String zy = msr3.getMbo(i).getString("value");
      MboSetRemote kg13 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热力机械工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  reportdep='" + zy + "' and status not in ('CANCEL','已取消','等待批准')");
      MboSetRemote zj13 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热力机械工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and reportdep='" + zy + "' and status in ('CLOSED')");
      MboSetRemote zf13 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热力机械工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and reportdep='" + zy + "'  and status in ('CANCEL')");
      MboSetRemote yq13 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热力机械工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and reportdep='" + zy + "'  and cwo28dt is not null");
      MboRemote mboremote13 = gzptj1.add();
      mboremote13.setValue("PROFESSION", zy, 11L);
      mboremote13.setValue("KGS", kg13.count(), 11L);
      mboremote13.setValue("ZJS", zj13.count(), 11L);
      mboremote13.setValue("ZFS", zf13.count(), 11L);
      mboremote13.setValue("YQS", yq13.count(), 11L);
      mboremote13.setValue("GZPLX", "热力机械工作票", 11L);

      databean3.refreshTable();
      this.sessionContext.queueRefreshEvent();
    }

    MboSetRemote kga4 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热控工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='一值' and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zja4 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热控工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='一值' and status in ('CLOSED')");
    MboSetRemote zfa4 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热控工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='一值' and status in ('CANCEL')");
    MboSetRemote yqa4 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热控工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='一值' and cwo28dt is not null");

    MboRemote mboremotea4 = gzptj1.add();
    mboremotea4.setValue("PROFESSION", "一值", 11L);
    mboremotea4.setValue("KGS", kga4.count(), 11L);
    mboremotea4.setValue("ZJS", zja4.count(), 11L);
    mboremotea4.setValue("ZFS", zfa4.count(), 11L);
    mboremotea4.setValue("YQS", yqa4.count(), 11L);
    mboremotea4.setValue("GZPLX", "热控工作票", 11L);

    MboSetRemote kgb4 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热控工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='二值'  and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjb4 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热控工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='二值' and status in ('CLOSED')");
    MboSetRemote zfb4 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热控工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='二值'  and status in ('CANCEL')");
    MboSetRemote yqb4 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热控工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='二值' and cwo28dt is not null");

    MboRemote mboremoteb4 = gzptj1.add();
    mboremoteb4.setValue("PROFESSION", "二值", 11L);
    mboremoteb4.setValue("KGS", kgb4.count(), 11L);
    mboremoteb4.setValue("ZJS", zjb4.count(), 11L);
    mboremoteb4.setValue("ZFS", zfb4.count(), 11L);
    mboremoteb4.setValue("YQS", yqb4.count(), 11L);
    mboremoteb4.setValue("GZPLX", "热控工作票", 11L);

    MboSetRemote kgc4 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热控工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='三值'  and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjc4 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热控工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='三值' and status in ('CLOSED')");
    MboSetRemote zfc4 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热控工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='三值'  and status in ('CANCEL')");
    MboSetRemote yqc4 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热控工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='三值' and cwo28dt is not null");

    MboRemote mboremotec4 = gzptj1.add();
    mboremotec4.setValue("PROFESSION", "三值", 11L);
    mboremotec4.setValue("KGS", kgc4.count(), 11L);
    mboremotec4.setValue("ZJS", zjc4.count(), 11L);
    mboremotec4.setValue("ZFS", zfc4.count(), 11L);
    mboremotec4.setValue("YQS", yqc4.count(), 11L);
    mboremotec4.setValue("GZPLX", "热控工作票", 11L);

    MboSetRemote kgd4 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热控工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='四值'  and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjd4 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热控工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='四值' and status in ('CLOSED')");
    MboSetRemote zfd4 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热控工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='四值'  and status in ('CANCEL')");
    MboSetRemote yqd4 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热控工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='四值' and cwo28dt is not null");

    MboRemote mboremoted4 = gzptj1.add();
    mboremoted4.setValue("PROFESSION", "四值", 11L);
    mboremoted4.setValue("KGS", kgd4.count(), 11L);
    mboremoted4.setValue("ZJS", zjd4.count(), 11L);
    mboremoted4.setValue("ZFS", zfd4.count(), 11L);
    mboremoted4.setValue("YQS", yqd4.count(), 11L);
    mboremoted4.setValue("GZPLX", "热控工作票", 11L);

    MboSetRemote kgw4 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热控工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='五值'  and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjw4 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热控工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='五值' and status in ('CLOSED')");
    MboSetRemote zfw4 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热控工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='五值'  and status in ('CANCEL')");
    MboSetRemote yqw4 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热控工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='五值' and cwo28dt is not null");

    MboRemote mboremotew4 = gzptj1.add();
    mboremotew4.setValue("PROFESSION", "五值", 11L);
    mboremotew4.setValue("KGS", kgw4.count(), 11L);
    mboremotew4.setValue("ZJS", zjw4.count(), 11L);
    mboremotew4.setValue("ZFS", zfw4.count(), 11L);
    mboremotew4.setValue("YQS", yqw4.count(), 11L);
    mboremotew4.setValue("GZPLX", "热控工作票", 11L);

    MboSetRemote msr4 = this.app.getAppBean().getMbo().getMboSet("$alndomain", "alndomain", "domainid='PROFESSION' and siteid='" + person1.getMbo(0).getString("locationsite") + "'");
    for (int i = 0; i < msr4.count(); ++i) {
      String zy = msr4.getMbo(i).getString("value");
      MboSetRemote kg14 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热控工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  reportdep='" + zy + "'  and status not in ('CANCEL','已取消','等待批准')");
      MboSetRemote zj14 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热控工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and reportdep='" + zy + "' and status in ('CLOSED')");
      MboSetRemote zf14 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热控工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and reportdep='" + zy + "'   and status in ('CANCEL')");
      MboSetRemote yq14 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='热控工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and reportdep='" + zy + "'  and cwo28dt is not null");
      MboRemote mboremote14 = gzptj1.add();
      mboremote14.setValue("PROFESSION", zy, 11L);
      mboremote14.setValue("KGS", kg14.count(), 11L);
      mboremote14.setValue("ZJS", zj14.count(), 11L);
      mboremote14.setValue("ZFS", zf14.count(), 11L);
      mboremote14.setValue("YQS", yq14.count(), 11L);
      mboremote14.setValue("GZPLX", "热控工作票", 11L);

      databean4.refreshTable();
      this.sessionContext.queueRefreshEvent();
    }

    MboSetRemote kga5 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='二级动火工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='一值'  and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zja5 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='二级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='一值' and status in ('CLOSED')");
    MboSetRemote zfa5 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='二级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='一值'  and status in ('CANCEL')");
    MboSetRemote yqa5 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='二级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='一值' and cwo28dt is not null");

    MboRemote mboremotea5 = gzptj1.add();
    mboremotea5.setValue("PROFESSION", "一值", 11L);
    mboremotea5.setValue("KGS", kga5.count(), 11L);
    mboremotea5.setValue("ZJS", zja5.count(), 11L);
    mboremotea5.setValue("ZFS", zfa5.count(), 11L);
    mboremotea5.setValue("YQS", yqa5.count(), 11L);
    mboremotea5.setValue("GZPLX", "二级动火工作票", 11L);

    MboSetRemote kgb5 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='二级动火工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='二值'  and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjb5 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='二级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='二值' and status in ('CLOSED')");
    MboSetRemote zfb5 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='二级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='二值'  and status in ('CANCEL')");
    MboSetRemote yqb5 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='二级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='二值' and cwo28dt is not null");

    MboRemote mboremoteb5 = gzptj1.add();
    mboremoteb5.setValue("PROFESSION", "二值", 11L);
    mboremoteb5.setValue("KGS", kgb5.count(), 11L);
    mboremoteb5.setValue("ZJS", zjb5.count(), 11L);
    mboremoteb5.setValue("ZFS", zfb5.count(), 11L);
    mboremoteb5.setValue("YQS", yqb5.count(), 11L);
    mboremoteb5.setValue("GZPLX", "二级动火工作票", 11L);

    MboSetRemote kgc5 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='二级动火工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='三值' and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjc5 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='二级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='三值' and status in ('CLOSED')");
    MboSetRemote zfc5 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='二级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='三值'  and status in ('CANCEL')");
    MboSetRemote yqc5 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='二级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='三值' and cwo28dt is not null");

    MboRemote mboremotec5 = gzptj1.add();
    mboremotec5.setValue("PROFESSION", "三值", 11L);
    mboremotec5.setValue("KGS", kgc5.count(), 11L);
    mboremotec5.setValue("ZJS", zjc5.count(), 11L);
    mboremotec5.setValue("ZFS", zfc5.count(), 11L);
    mboremotec5.setValue("YQS", yqc5.count(), 11L);
    mboremotec5.setValue("GZPLX", "二级动火工作票", 11L);

    MboSetRemote kgd5 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='二级动火工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='四值'  and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjd5 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='二级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='四值' and status in ('CLOSED')");
    MboSetRemote zfd5 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='二级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='四值'  and status in ('CANCEL')");
    MboSetRemote yqd5 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='二级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='四值' and cwo28dt is not null");

    MboRemote mboremoted5 = gzptj1.add();
    mboremoted5.setValue("PROFESSION", "四值", 11L);
    mboremoted5.setValue("KGS", kgd5.count(), 11L);
    mboremoted5.setValue("ZJS", zjd5.count(), 11L);
    mboremoted5.setValue("ZFS", zfd5.count(), 11L);
    mboremoted5.setValue("YQS", yqd5.count(), 11L);
    mboremoted5.setValue("GZPLX", "二级动火工作票", 11L);

    MboSetRemote kgw5 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='二级动火工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='五值'  and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjw5 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='二级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='五值' and status in ('CLOSED')");
    MboSetRemote zfw5 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='二级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='五值'  and status in ('CANCEL')");
    MboSetRemote yqw5 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='二级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='五值' and cwo28dt is not null");

    MboRemote mboremotew5 = gzptj1.add();
    mboremotew5.setValue("PROFESSION", "五值", 11L);
    mboremotew5.setValue("KGS", kgw5.count(), 11L);
    mboremotew5.setValue("ZJS", zjw5.count(), 11L);
    mboremotew5.setValue("ZFS", zfw5.count(), 11L);
    mboremotew5.setValue("YQS", yqw5.count(), 11L);
    mboremotew5.setValue("GZPLX", "二级动火工作票", 11L);

    MboSetRemote msr5 = this.app.getAppBean().getMbo().getMboSet("$alndomain", "alndomain", "domainid='PROFESSION' and siteid='" + person1.getMbo(0).getString("locationsite") + "'");
    for (int i = 0; i < msr5.count(); ++i) {
      String zy = msr5.getMbo(i).getString("value");
      MboSetRemote kg15 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='二级动火工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  reportdep='" + zy + "'  and status not in ('CANCEL','已取消','等待批准')");
      MboSetRemote zj15 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='二级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and reportdep='" + zy + "' and status in ('CLOSED')");
      MboSetRemote zf15 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='二级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and reportdep='" + zy + "'   and status in ('CANCEL')");
      MboSetRemote yq15 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='二级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and reportdep='" + zy + "'  and cwo28dt is not null");
      MboRemote mboremote15 = gzptj1.add();
      mboremote15.setValue("PROFESSION", zy, 11L);
      mboremote15.setValue("KGS", kg15.count(), 11L);
      mboremote15.setValue("ZJS", zj15.count(), 11L);
      mboremote15.setValue("ZFS", zf15.count(), 11L);
      mboremote15.setValue("YQS", yq15.count(), 11L);
      mboremote15.setValue("GZPLX", "二级动火工作票", 11L);

      databean6.refreshTable();
      this.sessionContext.queueRefreshEvent();
    }

    MboSetRemote kga8 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='现场工作申请单' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='一值'  and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zja8 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='现场工作申请单'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='一值' and status in ('CLOSED')");
    MboSetRemote zfa8 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='现场工作申请单'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='一值'  and status in ('CANCEL')");
    MboSetRemote yqa8 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='现场工作申请单'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='一值' and cwo28dt is not null");

    MboRemote mboremotea8 = gzptj1.add();
    mboremotea8.setValue("PROFESSION", "一值", 11L);
    mboremotea8.setValue("KGS", kga8.count(), 11L);
    mboremotea8.setValue("ZJS", zja8.count(), 11L);
    mboremotea8.setValue("ZFS", zfa8.count(), 11L);
    mboremotea8.setValue("YQS", yqa8.count(), 11L);
    mboremotea8.setValue("GZPLX", "现场工作申请单", 11L);

    MboSetRemote kgb8 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='现场工作申请单' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='二值'  and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjb8 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='现场工作申请单'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='二值' and status in ('CLOSED')");
    MboSetRemote zfb8 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='现场工作申请单'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='二值'  and status in ('CANCEL')");
    MboSetRemote yqb8 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='现场工作申请单'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='二值' and cwo28dt is not null");

    MboRemote mboremoteb8 = gzptj1.add();
    mboremoteb8.setValue("PROFESSION", "二值", 11L);
    mboremoteb8.setValue("KGS", kgb8.count(), 11L);
    mboremoteb8.setValue("ZJS", zjb8.count(), 11L);
    mboremoteb8.setValue("ZFS", zfb8.count(), 11L);
    mboremoteb8.setValue("YQS", yqb8.count(), 11L);
    mboremoteb8.setValue("GZPLX", "现场工作申请单", 11L);

    MboSetRemote kgc8 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='现场工作申请单' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='三值' and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjc8 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='现场工作申请单'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='三值' and status in ('CLOSED')");
    MboSetRemote zfc8 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='现场工作申请单'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='三值'  and status in ('CANCEL')");
    MboSetRemote yqc8 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='现场工作申请单'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='三值' and cwo28dt is not null");

    MboRemote mboremotec8 = gzptj1.add();
    mboremotec8.setValue("PROFESSION", "三值", 11L);
    mboremotec8.setValue("KGS", kgc8.count(), 11L);
    mboremotec8.setValue("ZJS", zjc8.count(), 11L);
    mboremotec8.setValue("ZFS", zfc8.count(), 11L);
    mboremotec8.setValue("YQS", yqc8.count(), 11L);
    mboremotec8.setValue("GZPLX", "现场工作申请单", 11L);

    MboSetRemote kgd8 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='现场工作申请单' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='四值'  and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjd8 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='现场工作申请单'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='四值' and status in ('CLOSED')");
    MboSetRemote zfd8 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='现场工作申请单'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='四值'  and status in ('CANCEL')");
    MboSetRemote yqd8 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='现场工作申请单'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='四值' and cwo28dt is not null");

    MboRemote mboremoted8 = gzptj1.add();
    mboremoted8.setValue("PROFESSION", "四值", 11L);
    mboremoted8.setValue("KGS", kgd8.count(), 11L);
    mboremoted8.setValue("ZJS", zjd8.count(), 11L);
    mboremoted8.setValue("ZFS", zfd8.count(), 11L);
    mboremoted8.setValue("YQS", yqd8.count(), 11L);
    mboremoted8.setValue("GZPLX", "现场工作申请单", 11L);

    MboSetRemote kgw8 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='现场工作申请单' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='五值'  and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjw8 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='现场工作申请单'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='五值' and status in ('CLOSED')");
    MboSetRemote zfw8 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='现场工作申请单'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='五值'  and status in ('CANCEL')");
    MboSetRemote yqw8 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='现场工作申请单'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='五值' and cwo28dt is not null");

    MboRemote mboremotew8 = gzptj1.add();
    mboremotew8.setValue("PROFESSION", "五值", 11L);
    mboremotew8.setValue("KGS", kgw8.count(), 11L);
    mboremotew8.setValue("ZJS", zjw8.count(), 11L);
    mboremotew8.setValue("ZFS", zfw8.count(), 11L);
    mboremotew8.setValue("YQS", yqw8.count(), 11L);
    mboremotew8.setValue("GZPLX", "现场工作申请单", 11L);

    MboSetRemote msr8 = this.app.getAppBean().getMbo().getMboSet("$alndomain", "alndomain", "domainid='PROFESSION' and siteid='" + person1.getMbo(0).getString("locationsite") + "'");
    for (int i = 0; i < msr8.count(); ++i) {
      String zy = msr8.getMbo(i).getString("value");
      MboSetRemote kg18 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='现场工作申请单' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  reportdep='" + zy + "'  and status not in ('CANCEL','已取消','等待批准')");
      MboSetRemote zj18 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='现场工作申请单'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and reportdep='" + zy + "' and status in ('CLOSED')");
      MboSetRemote zf18 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='现场工作申请单'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and reportdep='" + zy + "'   and status in ('CANCEL')");
      MboSetRemote yq18 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='现场工作申请单'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and reportdep='" + zy + "'  and cwo28dt is not null");
      MboRemote mboremote18 = gzptj1.add();
      mboremote18.setValue("PROFESSION", zy, 11L);
      mboremote18.setValue("KGS", kg18.count(), 11L);
      mboremote18.setValue("ZJS", zj18.count(), 11L);
      mboremote18.setValue("ZFS", zf18.count(), 11L);
      mboremote18.setValue("YQS", yq18.count(), 11L);
      mboremote18.setValue("GZPLX", "现场工作申请单", 11L);

      databean8.refreshTable();
      this.sessionContext.queueRefreshEvent();
    }

    MboSetRemote kga6 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='一级动火工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='一值'  and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zja6 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='一级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='一值' and status in ('CLOSED')");
    MboSetRemote zfa6 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='一级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='一值'  and status in ('CANCEL')");
    MboSetRemote yqa6 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='一级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='一值' and cwo28dt is not null");

    MboRemote mboremotea6 = gzptj1.add();
    mboremotea6.setValue("PROFESSION", "一值", 11L);
    mboremotea6.setValue("KGS", kga6.count(), 11L);
    mboremotea6.setValue("ZJS", zja6.count(), 11L);
    mboremotea6.setValue("ZFS", zfa6.count(), 11L);
    mboremotea6.setValue("YQS", yqa6.count(), 11L);
    mboremotea6.setValue("GZPLX", "一级动火工作票", 11L);

    MboSetRemote kgb6 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='一级动火工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='二值' and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjb6 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='一级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='二值' and status in ('CLOSED')");
    MboSetRemote zfb6 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='一级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='二值'  and status in ('CANCEL')");
    MboSetRemote yqb6 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='一级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='二值' and cwo28dt is not null");

    MboRemote mboremoteb6 = gzptj1.add();
    mboremoteb6.setValue("PROFESSION", "二值", 11L);
    mboremoteb6.setValue("KGS", kgb6.count(), 11L);
    mboremoteb6.setValue("ZJS", zjb6.count(), 11L);
    mboremoteb6.setValue("ZFS", zfb6.count(), 11L);
    mboremoteb6.setValue("YQS", yqb6.count(), 11L);
    mboremoteb6.setValue("GZPLX", "一级动火工作票", 11L);

    MboSetRemote kgc6 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='一级动火工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='三值'  and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjc6 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='一级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='三值' and status in ('CLOSED')");
    MboSetRemote zfc6 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='一级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='三值'  and status in ('CANCEL')");
    MboSetRemote yqc6 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='一级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='三值' and cwo28dt is not null");

    MboRemote mboremotec6 = gzptj1.add();
    mboremotec6.setValue("PROFESSION", "三值", 11L);
    mboremotec6.setValue("KGS", kgc6.count(), 11L);
    mboremotec6.setValue("ZJS", zjc6.count(), 11L);
    mboremotec6.setValue("ZFS", zfc6.count(), 11L);
    mboremotec6.setValue("YQS", yqc6.count(), 11L);
    mboremotec6.setValue("GZPLX", "一级动火工作票", 11L);

    MboSetRemote kgd6 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='一级动火工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='四值'  and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjd6 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='一级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='四值' and status in ('CLOSED')");
    MboSetRemote zfd6 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='一级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='四值' and status in ('CANCEL')");
    MboSetRemote yqd6 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='一级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='四值' and cwo28dt is not null");

    MboRemote mboremoted6 = gzptj1.add();
    mboremoted6.setValue("PROFESSION", "四值", 11L);
    mboremoted6.setValue("KGS", kgd6.count(), 11L);
    mboremoted6.setValue("ZJS", zjd6.count(), 11L);
    mboremoted6.setValue("ZFS", zfd6.count(), 11L);
    mboremoted6.setValue("YQS", yqd6.count(), 11L);
    mboremoted6.setValue("GZPLX", "一级动火工作票", 11L);

    MboSetRemote kgw6 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='一级动火工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo3)='五值'  and status not in ('CANCEL','已取消','等待批准')");
    MboSetRemote zjw6 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='一级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo13)='五值' and status in ('CLOSED')");
    MboSetRemote zfw6 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='一级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo57)='五值' and status in ('CANCEL')");
    MboSetRemote yqw6 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='一级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=cwo28)='五值' and cwo28dt is not null");

    MboRemote mboremotew6 = gzptj1.add();
    mboremotew6.setValue("PROFESSION", "五值", 11L);
    mboremotew6.setValue("KGS", kgw6.count(), 11L);
    mboremotew6.setValue("ZJS", zjw6.count(), 11L);
    mboremotew6.setValue("ZFS", zfw6.count(), 11L);
    mboremotew6.setValue("YQS", yqw6.count(), 11L);
    mboremotew6.setValue("GZPLX", "一级动火工作票", 11L);

    MboSetRemote msr6 = this.app.getAppBean().getMbo().getMboSet("$alndomain", "alndomain", "domainid='PROFESSION' and siteid='" + person1.getMbo(0).getString("locationsite") + "'");
    for (int i = 0; i < msr6.count(); ++i) {
      String zy = msr6.getMbo(i).getString("value");
      MboSetRemote kg16 = this.app.getAppBean().getMbo().getMboSet("$workorder", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='一级动火工作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  reportdep='" + zy + "'  and status not in ('CANCEL','已取消','等待批准')");
      MboSetRemote zj16 = this.app.getAppBean().getMbo().getMboSet("$workorder2", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='一级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and reportdep='" + zy + "' and status in ('CLOSED')");
      MboSetRemote zf16 = this.app.getAppBean().getMbo().getMboSet("$workorder3", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='一级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and reportdep='" + zy + "'   and status in ('CANCEL')");
      MboSetRemote yq16 = this.app.getAppBean().getMbo().getMboSet("$workorder4", "workorder", "(reportdate>to_date('" + startdate + "','YYYY mm dd') and reportdate<=to_date('" + enddate + "','YYYY mm dd')+1)  and S_ORDERTYPE='一级动火工作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and reportdep='" + zy + "'  and cwo28dt is not null");
      MboRemote mboremote16 = gzptj1.add();
      mboremote16.setValue("PROFESSION", zy, 11L);
      mboremote16.setValue("KGS", kg16.count(), 11L);
      mboremote16.setValue("ZJS", zj16.count(), 11L);
      mboremote16.setValue("ZFS", zf16.count(), 11L);
      mboremote16.setValue("YQS", yq16.count(), 11L);
      mboremote16.setValue("GZPLX", "一级动火工作票", 11L);

      databean5.refreshTable();
      this.sessionContext.queueRefreshEvent();
    }
    gzptj1.save();
    gzptj1.reset();
    this.app.getAppBean().save();
    return 1;
  }

  public int selectczp() throws MXException, RemoteException
  {
    DataBean databean = this.app.getDataBean("1339729002716");
    MboSetRemote mbosetremote = databean.getMboSet();
    MboSetRemote person1 = this.app.getAppBean().getMbo().getMboSet("$person", "person", "personid='" + this.app.getAppBean().getMbo().getUserName() + "'");
    System.out.println("person1=" + this.app.getAppBean().getMbo().getUserName());
    System.out.println("person1.site=" + person1.getMbo(0).getString("locationsite"));
    MboSetRemote czptj1 = this.app.getAppBean().getMbo().getMboSet("$czptj", "czptj", "siteid='" + person1.getMbo(0).getString("locationsite") + "'");
    mbosetremote.deleteAll();
    mbosetremote.save();
    String startdate = this.app.getAppBean().getString("tj_startdate");
    String enddate = this.app.getAppBean().getString("tj_enddate");
    System.out.println(startdate);
    System.out.println(enddate);

    MboSetRemote dqa = this.app.getAppBean().getMbo().getMboSet("$opticket", "opticket", "(createtime>to_date('" + startdate + "','YYYY mm dd') and createtime<=to_date('" + enddate + "','YYYY mm dd')+1)  and optickettype='电气操作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=operator)='一值' and opstatus in ('已批准')");
    MboSetRemote rja = this.app.getAppBean().getMbo().getMboSet("$opticket2", "opticket", "(createtime>to_date('" + startdate + "','YYYY mm dd') and createtime<=to_date('" + enddate + "','YYYY mm dd')+1)  and optickettype in ('热机操作票')  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=operator)='一值' and opstatus in ('已批准')");
    MboRemote mboremotea = czptj1.add();
    mboremotea.setValue("PROFESSION", "一值", 11L);
    mboremotea.setValue("DQCZP", dqa.count(), 11L);
    mboremotea.setValue("RJCZP", rja.count(), 11L);

    MboSetRemote dqb = this.app.getAppBean().getMbo().getMboSet("$opticket", "opticket", "(createtime>to_date('" + startdate + "','YYYY mm dd') and createtime<=to_date('" + enddate + "','YYYY mm dd')+1)  and optickettype='电气操作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=operator)='二值' and opstatus in ('已批准')");
    MboSetRemote rjb = this.app.getAppBean().getMbo().getMboSet("$opticket2", "opticket", "(createtime>to_date('" + startdate + "','YYYY mm dd') and createtime<=to_date('" + enddate + "','YYYY mm dd')+1)  and optickettype in ('热机操作票')  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=operator)='二值' and opstatus in ('已批准')");
    MboRemote mboremoteb = czptj1.add();
    mboremoteb.setValue("PROFESSION", "二值", 11L);
    mboremoteb.setValue("DQCZP", dqb.count(), 11L);
    mboremoteb.setValue("RJCZP", rjb.count(), 11L);

    MboSetRemote dqc = this.app.getAppBean().getMbo().getMboSet("$opticket", "opticket", "(createtime>to_date('" + startdate + "','YYYY mm dd') and createtime<=to_date('" + enddate + "','YYYY mm dd')+1)  and optickettype='电气操作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=operator)='三值' and opstatus in ('已批准')");
    MboSetRemote rjc = this.app.getAppBean().getMbo().getMboSet("$opticket2", "opticket", "(createtime>to_date('" + startdate + "','YYYY mm dd') and createtime<=to_date('" + enddate + "','YYYY mm dd')+1)  and optickettype in ('热机操作票')  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=operator)='三值' and opstatus in ('已批准')");
    MboRemote mboremotec = czptj1.add();
    mboremotec.setValue("PROFESSION", "三值", 11L);
    mboremotec.setValue("DQCZP", dqc.count(), 11L);
    mboremotec.setValue("RJCZP", rjc.count(), 11L);

    MboSetRemote dqd = this.app.getAppBean().getMbo().getMboSet("$opticket", "opticket", "(createtime>to_date('" + startdate + "','YYYY mm dd') and createtime<=to_date('" + enddate + "','YYYY mm dd')+1)  and optickettype='电气操作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=operator)='四值' and opstatus in ('已批准')");
    MboSetRemote rjd = this.app.getAppBean().getMbo().getMboSet("$opticket2", "opticket", "(createtime>to_date('" + startdate + "','YYYY mm dd') and createtime<=to_date('" + enddate + "','YYYY mm dd')+1)  and optickettype in ('热机操作票')  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=operator)='四值' and opstatus in ('已批准')");
    MboRemote mboremoted = czptj1.add();
    mboremoted.setValue("PROFESSION", "四值", 11L);
    mboremoted.setValue("DQCZP", dqd.count(), 11L);
    mboremoted.setValue("RJCZP", rjd.count(), 11L);

    MboSetRemote dqw = this.app.getAppBean().getMbo().getMboSet("$opticket", "opticket", "(createtime>to_date('" + startdate + "','YYYY mm dd') and createtime<=to_date('" + enddate + "','YYYY mm dd')+1)  and optickettype='电气操作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=operator)='五值' and opstatus in ('已批准')");
    MboSetRemote rjw = this.app.getAppBean().getMbo().getMboSet("$opticket2", "opticket", "(createtime>to_date('" + startdate + "','YYYY mm dd') and createtime<=to_date('" + enddate + "','YYYY mm dd')+1)  and optickettype in ('热机操作票')  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  (select department from person where personid=operator)='五值' and opstatus in ('已批准')");
    MboRemote mboremotew = czptj1.add();
    mboremotew.setValue("PROFESSION", "五值", 11L);
    mboremotew.setValue("DQCZP", dqw.count(), 11L);
    mboremotew.setValue("RJCZP", rjw.count(), 11L);

    MboSetRemote msr = this.app.getAppBean().getMbo().getMboSet("$alndomain", "alndomain", "domainid='PROFESSION' and siteid='" + person1.getMbo(0).getString("locationsite") + "'");
    for (int i = 0; i < msr.count(); ++i) {
      String zy = msr.getMbo(i).getString("value");
      MboSetRemote dq1 = this.app.getAppBean().getMbo().getMboSet("$opticket", "opticket", "(createtime>to_date('" + startdate + "','YYYY mm dd') and createtime<=to_date('" + enddate + "','YYYY mm dd')+1)  and optickettype='电气操作票' and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  special='" + zy + "' and opstatus in ('已批准')");
      MboSetRemote rj1 = this.app.getAppBean().getMbo().getMboSet("$opticket2", "opticket", "(createtime>to_date('" + startdate + "','YYYY mm dd') and createtime<=to_date('" + enddate + "','YYYY mm dd')+1)  and optickettype='热机操作票'  and siteid='" + person1.getMbo(0).getString("locationsite") + "' and  special='" + zy + "' and opstatus in ('已批准')");

      MboRemote mboremote1 = czptj1.add();
      mboremote1.setValue("PROFESSION", zy, 11L);
      mboremote1.setValue("DQCZP", dq1.count(), 11L);
      mboremote1.setValue("RJCZP", rj1.count(), 11L);

      databean.refreshTable();
      this.sessionContext.queueRefreshEvent();
    }
    czptj1.save();
    czptj1.reset();
    this.app.getAppBean().save();
    return 1;
  }
}