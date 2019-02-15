package com.shuto.mam.webclient.beans.rl.rlsmscrb;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.shuto.mam.webclient.beans.base.CAppBean;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationWarningException;
import psdi.util.MXException;

public class RlsmscrbAppBean extends CAppBean
{
  public int SAVE()
    throws MXException, RemoteException
  {
    if (!getMboSet().isEmpty()) {
      for (int i = 0; i < getMboSet().count(); ++i) {
        MboRemote smbo = getMboSet().getMbo(i);
        if (smbo.isModified()) {
          Date sj = smbo.getDate("sj");
          if (sj == null) {
            String[] dd = { "时间不能为空" };
            throw new MXApplicationWarningException("configure", "BlankMsg", dd);
          }

          double ACDS = 0.0D;
          double BCDS = 0.0D;
          double YLLJSML = 0.0D;
          double ELLJSML = 0.0D;

          ACDS = smbo.getDouble("ACDS");
          BCDS = smbo.getDouble("BCDS");
          YLLJSML = smbo.getDouble("YLLJSML");
          ELLJSML = smbo.getDouble("ELLJSML");

          SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
          Calendar date = Calendar.getInstance();
          date.setTime(sj);
          date.set(5, date.get(5) - 1);
          String sjs = df.format(date.getTime());

          int mid = smbo.getInt("RLSMSCRBid");
          MboSetRemote tt = smbo.getMboSet("$smscrb", "RLSMSCRB", "to_char(sj,'yyyy-MM-dd')='" + sjs + "' and RLSMSCRBid<>" + mid);
          double tACDS = 0.0D;
          double tBCDS = 0.0D;
          if (tt.isEmpty()) {
            throw new MXApplicationWarningException("configure", "BlankMsg", new String[] { "请先填写" + sjs + "数据！" });
          }
          tACDS = tt.getMbo(0).getDouble("ACDS");
          tBCDS = tt.getMbo(0).getDouble("BCDS");

          double JYCDS = smbo.getDouble("JYCDS");
          double rhml = ACDS - tACDS + BCDS - tBCDS - JYCDS;
          double heji = YLLJSML + ELLJSML;
          double YLHBSML = rhml * YLLJSML / heji;
          double ELHBSML = rhml * ELLJSML / heji;
          smbo.setValue("RSML", rhml);
          smbo.setValue("HEJI", heji);
          smbo.setValue("YLHBSML", YLHBSML);
          smbo.setValue("ELHBSML", ELHBSML);
        }
      }
    }
    return super.SAVE();
  }
}