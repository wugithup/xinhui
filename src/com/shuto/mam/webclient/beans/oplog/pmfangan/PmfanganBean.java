package com.shuto.mam.webclient.beans.oplog.pmfangan;

import java.io.IOException;
import java.rmi.RemoteException;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
/**
 * 混配煤方案
 com.shuto.mam.webclient.beans.oplog.pmfangan.PmfanganBean 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月4日 下午8:59:14
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class PmfanganBean extends CAppBean
{
  public void COPYPMFA()
    throws RemoteException, MXException, IOException
  {
    MboRemote rec = getMbo();
    String OldSID = rec.getString("SID");
    String SITEID = rec.getString("SITEID");
    String oldmzsid = "";
    String newmzsid = "";

    MboRemote newmbo = getMboSet().add();
    setCurrentRecordData(newmbo);

    MboRemote mbo = getMbo();
    String oldsql = "ZBSID='" + OldSID + "'" + " and " + "SITEID='" + SITEID + "' order by sid";
    MboSetRemote OldfanganMbo = getMbo().getMboSet("$temp_oldsql", "ST_PMFANGANZB", oldsql);

    MboSetRemote PMFANGANZB = getMbo().getMboSet("ST_PMFANGANZB");

    if (OldfanganMbo.count() > 0) {
      String SID = mbo.getString("SID");
      for (int i = 0; i < OldfanganMbo.count(); ++i) {
        MboRemote mboremote = PMFANGANZB.add();
        mboremote.setValue("PEILU", OldfanganMbo.getMbo(i).getString("PEILU"));
        mboremote.setValue("CHANGKU", OldfanganMbo.getMbo(i).getString("CHANGKU"));
        mboremote.setValue("PMFS", OldfanganMbo.getMbo(i).getString("PMFS"));
        mboremote.setValue("BEIZU", OldfanganMbo.getMbo(i).getString("BEIZU"));
        mboremote.setValue("ZBSID", SID);
        oldmzsid = OldfanganMbo.getMbo(i).getString("SID");
        newmzsid = mboremote.getString("SID");

        if (OldfanganMbo.getMbo(i).getString("SID").equalsIgnoreCase("")) {
          continue;
        }

        String fasql = "ZBSID='" + oldmzsid + "'" + " and " + "SITEID='" + SITEID + "'";

        MboSetRemote fasqlMbo = getMbo().getMboSet("$temp_fapb", "ST_PMFANGANPB", fasql);

        String addsql = "SITEID='" + SITEID + "'";
        MboSetRemote FANGANPB = getMbo().getMboSet("$temp_addfapb", "ST_PMFANGANPB", addsql);
        if (fasqlMbo.count() > 0) {
          for (int w = 0; w < fasqlMbo.count(); ++w) {
            MboRemote meizhong = FANGANPB.add();
            meizhong.setValue("TYPE", fasqlMbo.getMbo(w).getString("TYPE"));
            meizhong.setValue("HPB", fasqlMbo.getMbo(w).getString("HPB"));
            meizhong.setValue("ZBSID", newmzsid);
          }

        }

        String hhmzsql = "SID='" + oldmzsid + "'" + " and " + "ZBSID='" + OldSID + "' order by sid";

        MboSetRemote hhmzsqlMbo = getMbo().getMboSet("$temp_hhmzsql", "ST_PMFANGANZB3", hhmzsql);

        String addhhmsql = "ZBSID='" + SID + "'" + " AND SITEID='" + SITEID + "'";

        MboSetRemote addhhmzmboset = getMbo().getMboSet("$temp_addhhmz", "ST_PMFANGANZB3", addhhmsql);
        if (hhmzsqlMbo.count() > 0) {
          for (int z = 0; z < hhmzsqlMbo.count(); ++z) {
            MboRemote hphmzmbo = addhhmzmboset.add();
            hphmzmbo.setValue("REZHI", hhmzsqlMbo.getMbo(z).getString("REZHI"));
            hphmzmbo.setValue("QUANSHUI", hhmzsqlMbo.getMbo(z).getString("HPB"));
            hphmzmbo.setValue("HFF", hhmzsqlMbo.getMbo(z).getString("REZHI"));
            hphmzmbo.setValue("HUIFEN", hhmzsqlMbo.getMbo(z).getString("HUIFEN"));
            hphmzmbo.setValue("LIUFEN", hhmzsqlMbo.getMbo(z).getString("LIUFEN"));
            hphmzmbo.setValue("SID", newmzsid);
            hphmzmbo.setValue("ZBSID", SID);
          }
        }
      }

    }

    throw new MXApplicationException("system", "COPYCZP2");
  }

  public void SCPROGRAM()
    throws RemoteException, MXException, IOException
  {
    MboRemote rec = getMbo();
    String status = rec.getString("STATUS");
    String sid = rec.getString("sid");
    String siteid = rec.getString("siteid");

    if (status.equalsIgnoreCase("已批准"))
      return;
    MboSetRemote ST_PMFANGANZB3 = getMbo().getMboSet("ST_PMFANGANZB3");

    String fasql = "ZBSID='" + sid + "'" + " and " + "SITEID='" + siteid + "' and pmfs not in('检修') ";
    MboSetRemote fazbMbo = getMbo().getMboSet("$temp_pmfanganzb", "st_pmfanganzb", fasql);
    if (fazbMbo.count() > 0) {
      ST_PMFANGANZB3.deleteAll();
      for (int i = 0; i < fazbMbo.count(); ++i) {
        String pbzbsid = fazbMbo.getMbo(i).getString("ZBSID");
        String pbsid = fazbMbo.getMbo(i).getString("SID");
        String PMFS = fazbMbo.getMbo(i).getString("PMFS");
        String pbsql = "ZBSID='" + pbsid + "'" + " and " + "SITEID='" + siteid + "'";
        MboSetRemote ST_PMFANGANPB = getMbo().getMboSet("$temp_PMFANGANPB", "ST_PMFANGANPB", pbsql);
        if ((PMFS.equalsIgnoreCase("单上")) &&
          (ST_PMFANGANPB.count() > 0)) {
          MboRemote mboremoteDS = ST_PMFANGANZB3.add();
          mboremoteDS.setValue("ZBSID", pbzbsid);
          mboremoteDS.setValue("SID", pbsid);
          mboremoteDS.setValue("TYPE", ST_PMFANGANPB.getMbo(0).getString("TYPE"));
          mboremoteDS.setValue("REZHI", ST_PMFANGANPB.getMbo(0).getString("TYPE.REZHI"));
          mboremoteDS.setValue("QUANSHUI", ST_PMFANGANPB.getMbo(0).getString("TYPE.QUANSHUI"));
          mboremoteDS.setValue("HFF", ST_PMFANGANPB.getMbo(0).getString("TYPE.HFF"));
          mboremoteDS.setValue("HUIFEN", ST_PMFANGANPB.getMbo(0).getString("TYPE.HUIFEN"));
          mboremoteDS.setValue("LIUFEN", ST_PMFANGANPB.getMbo(0).getString("TYPE.LIUFEN"));
        }

        if (PMFS.equalsIgnoreCase("混配")) {
          double HPB = 0.0D;
          double REZHI = 0.0D;
          double QUANSHUI = 0.0D;
          double HFF = 0.0D;
          double HUIFEN = 0.0D;
          double LIUFEN = 0.0D;
          if (ST_PMFANGANPB.count() > 0) {
            for (int z = 0; z < ST_PMFANGANPB.count(); ++z) {
              HPB += ST_PMFANGANPB.getMbo(z).getDouble("HPB");
              REZHI += ST_PMFANGANPB.getMbo(z).getDouble("TYPE.REZHI") * ST_PMFANGANPB.getMbo(z).getDouble("HPB");
              QUANSHUI += ST_PMFANGANPB.getMbo(z).getDouble("TYPE.QUANSHUI") * ST_PMFANGANPB.getMbo(z).getDouble("HPB");
              HFF += ST_PMFANGANPB.getMbo(z).getDouble("TYPE.HFF") * ST_PMFANGANPB.getMbo(z).getDouble("HPB");
              HUIFEN += ST_PMFANGANPB.getMbo(z).getDouble("TYPE.HUIFEN") * ST_PMFANGANPB.getMbo(z).getDouble("HPB");
              LIUFEN += ST_PMFANGANPB.getMbo(z).getDouble("TYPE.LIUFEN") * ST_PMFANGANPB.getMbo(z).getDouble("HPB");
            }
            MboRemote mboremoteHP = ST_PMFANGANZB3.add();
            mboremoteHP.setValue("ZBSID", pbzbsid);
            mboremoteHP.setValue("SID", pbsid);
            mboremoteHP.setValue("REZHI", REZHI / HPB);
            mboremoteHP.setValue("QUANSHUI", QUANSHUI / HPB);
            mboremoteHP.setValue("HFF", HFF / HPB);
            mboremoteHP.setValue("HUIFEN", HUIFEN / HPB);
            mboremoteHP.setValue("LIUFEN", LIUFEN / HPB);
          }
        }
      }
      super.SAVE();
      throw new MXApplicationException("system", "PMFANGAN2");
    }
  }

  public boolean hasAuth()
    throws MXException, RemoteException
  {
    long tablesid = getMbo().getLong("ST_PMFANGANID");
    String s = getMbo().getString("STATUS");
    String s1 = getMbo().getUserInfo().getPersonId();
    if (s1.equalsIgnoreCase("maxadmin"))
      return true;
    if ("新建".equals(s))
      return true;
    MboSetRemote mbosetremote = getMbo().getMboSet("$instance",
      "WFINSTANCE",
      "ownertable='ST_PMFANGAN' and ownerid='" + tablesid + "' and active = 1");
    if (!(mbosetremote.isEmpty())) {
      String s2 = "ownerid='" +
        tablesid +
        "' and ownertable='ST_PMFANGAN' and assignstatus='活动' and assigncode='" +
        s1 + "'";
      MboSetRemote mbosetremote1 = getMbo().getMboSet("$assigncode",
        "WFASSIGNMENT", s2);
      return (!(mbosetremote1.isEmpty()));
    }
    return false;
  }

  public int ROUTEWF() throws MXException, RemoteException
  {
    if (!(hasAuth())) {
      throw new MXApplicationException("system", "SYSROUTEWF2");
    }
    return super.ROUTEWF();
  }

  public int INSERT() throws MXException, RemoteException {
    super.INSERT();

    String SID = getString("SID");
    MboSetRemote ST_FANGANINFO = getMbo().getMboSet("ST_FANGANINFO");
    MboSetRemote ST_PMFANGANZB = getMbo().getMboSet("ST_PMFANGANZB");
    if (ST_FANGANINFO.count() > 0) {
      for (int i = 0; i < ST_FANGANINFO.count(); ++i) {
        MboRemote mboremote = ST_PMFANGANZB.add();
        mboremote.setValue("PEILU", ST_FANGANINFO.getMbo(i).getString("PEILU"));
        mboremote.setValue("CHANGKU", ST_FANGANINFO.getMbo(i).getString("CHANGKU"));
        mboremote.setValue("PMFS", "混配");
        mboremote.setValue("BEIZU", ST_FANGANINFO.getMbo(i).getString("BEIZU"));
        mboremote.setValue("ZBSID", SID);
      }
    }
    return 1;
  }

  public int SAVE() throws MXException, RemoteException
  {
    if (!(hasAuth())) {
      throw new MXApplicationException("system", "SYSSAVE2");
    }
    MboRemote mbo = this.app.getAppBean().getMbo();
    String personid = mbo.getUserInfo().getPersonId();
    String createby = mbo.getString("CREATEPERSON");
    if ((getString("status").equals("新建")) && (!(personid.equals(createby))) &&
      (!(personid.equals("MAXADMIN")))) {
      throw new MXApplicationException("system", "SYSSAVE2");
    }
    return super.SAVE();
  }

  @Override
  public int DELETE()
    throws MXException, RemoteException
  {
    MboRemote mbo = this.app.getAppBean().getMbo();
    String personid = mbo.getUserInfo().getPersonId();
    String createby = mbo.getString("CREATEPERSON");
    if ((getString("status").equals("新建")) && (!(personid.equals(createby))) &&
      (!(personid.equals("MAXADMIN")))) {
      throw new MXApplicationException("system", "SYSDELETE2");
    }

    return super.DELETE();
  }
}