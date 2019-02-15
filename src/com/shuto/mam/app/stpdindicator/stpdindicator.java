package com.shuto.mam.app.stpdindicator;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ibm.icu.math.BigDecimal;

import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.mbo.SqlFormat;
import psdi.util.MXException;
/**
 * 生产指标录入
 com.shuto.mam.app.stpdindicator.stpdindicator 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午8:30:00
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class stpdindicator extends Mbo
  implements stpdindicatorRemote
{
  public stpdindicator(MboSet mboset)
    throws RemoteException, MXException
  {
    super(mboset);
  }

  public void save() throws MXException, RemoteException {
    super.save();
    Date tjdate = getDate("tjdate");
    MboSetRemote pdoldmboset = getMboSet("$oldmboset", "ST_PDINDICATOR");
    if (pdoldmboset.count() > 0) {
      String sql = "";
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      Calendar cal = Calendar.getInstance();
      cal.setTime(tjdate);
      cal.add(5, -1);
      String olddate = df.format(cal.getTime());
      sql = "to_char(tjdate,'yyyy-MM-dd')='" + olddate + "'";
      SqlFormat sqlfmart1 = new SqlFormat(sql);
      pdoldmboset.setWhere(sqlfmart1.format());
      pdoldmboset.reset();

      MboRemote oldmbo = pdoldmboset.getMbo(0);
      double O_ONEPD02 = oldmbo.getDouble("ONEPD02");
      double O_ONEPD04 = oldmbo.getDouble("ONEPD04");
      double O_ONEPD06 = oldmbo.getDouble("ONEPD06");
      double O_ONEPD08 = oldmbo.getDouble("ONEPD08");
      double O_ONEPD10 = oldmbo.getDouble("ONEPD10");
      double O_ONEPD12 = oldmbo.getDouble("ONEPD12");
      double O_ONEPD14 = oldmbo.getDouble("ONEPD14");
      double O_ONEPD15 = oldmbo.getDouble("ONEPD15");
      double O_TWOPD07 = oldmbo.getDouble("TWOPD07");
      double O_TWOPD08 = oldmbo.getDouble("TWOPD08");

      double O_FOURPD02 = oldmbo.getDouble("FOURPD02");
      double O_FOURPD06 = oldmbo.getDouble("FOURPD06");
      double O_FOURPD07 = oldmbo.getDouble("FOURPD07");
      double O_FOURPD10 = oldmbo.getDouble("FOURPD10");

      double O_THREEPD03 = oldmbo.getDouble("THREEPD03");
      double O_THREEPD04 = oldmbo.getDouble("THREEPD04");
      double O_THREEPD05 = oldmbo.getDouble("THREEPD05");

      double O_THREEPD09 = oldmbo.getDouble("THREEPD09");
      double O_THREEPD10 = oldmbo.getDouble("THREEPD10");

      double O_THREEPD14 = oldmbo.getDouble("THREEPD14");
      double O_THREEPD15 = oldmbo.getDouble("THREEPD15");
      double O_THREEPD16 = oldmbo.getDouble("THREEPD16");
      double O_THREEPD19 = oldmbo.getDouble("THREEPD19");
      double O_THREEPD20 = oldmbo.getDouble("THREEPD20");
      double O_THREEPD25 = oldmbo.getDouble("THREEPD25");
      double O_THREEPD28 = oldmbo.getDouble("THREEPD28");

      double O_THREEPD33 = oldmbo.getDouble("THREEPD33");
      double O_THREEPDH18 = oldmbo.getDouble("THREEPDH18");
      double O_THREEPDH19 = oldmbo.getDouble("THREEPDH19");
      double O_THREEPDH20 = oldmbo.getDouble("THREEPDH20");
      double O_THREEPDHQ18 = oldmbo.getDouble("THREEPDHQ18");
      double O_THREEPDHQ19 = oldmbo.getDouble("THREEPDHQ19");
      double O_THREEPDHQ20 = oldmbo.getDouble("THREEPDHQ20");

      double ONEPD01 = getDouble("ONEPD01");
      double ONEPD03 = getDouble("ONEPD03");
      double ONEPD05 = getDouble("ONEPD05");
      double ONEPD07 = getDouble("ONEPD07");
      double ONEPD09 = getDouble("ONEPD09");
      double ONEPD11 = getDouble("ONEPD11");
      double ONEPD12 = getDouble("ONEPD12");
      double ONEPD13 = getDouble("ONEPD13");
      double ONEPD14 = getDouble("ONEPD14");

      double JYQCKPD1 = getDouble("JYQCKPD1");
      double JYQCKPD2 = getDouble("JYQCKPD2");
      double NOXPD1 = getDouble("NOXPD1");
      double NOXPD2 = getDouble("NOXPD2");
      double SO2PD1 = getDouble("SO2PD1");
      double SO2PD2 = getDouble("SO2PD2");
      double TLTYLPD1 = getDouble("TLTYLPD1");
      double TLTYLPD2 = getDouble("TLTYLPD2");

      double ZQWD1 = getDouble("ZQWD1");
      double ZQWD2 = getDouble("ZQWD2");
      double ZQYL1 = getDouble("ZQYL1");
      double ZQYL2 = getDouble("ZQYL2");
      double ZRQW1 = getDouble("ZRQW1");
      double ZRQW2 = getDouble("ZRQW2");
      double PYWD1 = getDouble("PYWD1");
      double PYWD2 = getDouble("PYWD2");
      double FHHT1 = getDouble("FHHT1");
      double FHHT2 = getDouble("FHHT2");
      double YL1 = getDouble("YL1");
      double YL2 = getDouble("YL2");
      double ZK1 = getDouble("ZK1");
      double ZK2 = getDouble("ZK2");

      double TWOPD06 = getDouble("TWOPD06");
      double TWOPD0J7 = getDouble("TWOPD07");

      double FOURPD01 = getDouble("FOURPD01");
      double FOURPD03 = getDouble("FOURPD03");
      double FOURPD04 = getDouble("FOURPD04");
      double FOURPD05 = getDouble("FOURPD05");
      double FOURPD08 = getDouble("FOURPD08");
      double FOURPD09 = getDouble("FOURPD09");
      double FOURPDJ10 = getDouble("FOURPD10");
      double Hsdhsl = getDouble("HSDHSL");

      double THREEPD01 = getDouble("THREEPD01");
      double THREEPD02 = getDouble("THREEPD02");
      double THREEPD06 = getDouble("THREEPD06");
      double THREEPD07 = getDouble("THREEPD07");
      double THREEPD11 = getDouble("THREEPD11");
      double THREEPD12 = getDouble("THREEPD12");

      double THREEPD18 = getDouble("THREEPD18");

      double THREEPD24 = getDouble("THREEPD24");
      double THREEPD27 = getDouble("THREEPD27");
      double THREEPDJ28 = getDouble("THREEPD28");
      double THREEPD31 = getDouble("THREEPD31");
      double THREEPD32 = getDouble("THREEPD32");

      double THREEPD36 = getDouble("THREEPD36");
      double THREEPD37 = getDouble("THREEPD37");
      double THREEPDH18 = getDouble("THREEPDH18");
      double THREEPDHHQ18 = getDouble("THREEPDHQ18");
      SimpleDateFormat dd = new SimpleDateFormat("dd");
      String tjday = dd.format(tjdate);
      Date tjd = getDate("tjdate");
      SimpleDateFormat ddf = new SimpleDateFormat("yyyy年MM月dd日");
      setValue("description", ddf.format(tjdate) + "生产指标录入");
      SimpleDateFormat mmdd = new SimpleDateFormat("dd");
      String tjdaymd = dd.format(tjdate);
      Date tjdmd = getDate("tjdate");
      SimpleDateFormat ddfmd = new SimpleDateFormat("MM-dd");
      if (Integer.valueOf(tjday).intValue() == 1)
      {
        setValue("ONEPD02", getDouble("ONEPD01"));
        setValue("ONEPD04", getDouble("ONEPD03"));
        setValue("ONEPD06", getDouble("ONEPD05"));
        setValue("ONEPD08", getDouble("ONEPD07"));
        setValue("ONEPD10", getDouble("ONEPD09"));
        setValue("ONEPD12", getDouble("ONEPD11"));
        setValue("ONEPD14", getDouble("ONEPD13"));
        setValue("THREEPD28", getDouble("THREEPD27"));

        setValue("TWOPD08", O_TWOPD07);
        setValue("THREEPD03", getDouble("THREEPD02"));
        setValue("THREEPD19", getDouble("THREEPD18"));
        setValue("THREEPDH19", getDouble("THREEPDH18"));

        setValue("FOURPD02", getDouble("FOURPD01"));
        setValue("FOURPD06", getDouble("FOURPD05"));
        setValue("FOURPD10", getDouble("FOURPD09"));
        if (ddfmd.format(tjdmd).equals("01-01"))
        {
          setValue("ONEPD15", getDouble("ONEPD11"));

          setValue("THREEPD04", getDouble("THREEPD02"));
          setValue("THREEPD10", getDouble("THREEPD06") + getDouble("THREEPD07"));
          setValue("THREEPD15", getDouble("THREEPD11") + getDouble("THREEPD12"));

          setValue("THREEPDH20", getDouble("THREEPDH18"));

          setValue("FOURPD07", getDouble("FOURPD05"));
          setValue("THREEPD20", getDouble("THREEPD18"));
        }
        else {
          setValue("ONEPD15", getDouble("ONEPD11") + O_ONEPD15);
          setValue("THREEPD04", O_THREEPD04 + getDouble("THREEPD02"));

          setValue("THREEPD10", getDouble("THREEPD06") + getDouble("THREEPD07") + O_THREEPD10);
          setValue("THREEPD15", getDouble("THREEPD11") + getDouble("THREEPD12") + O_THREEPD15);

          setValue("THREEPDH20", getDouble("THREEPDH18") + O_THREEPDH20);

          setValue("FOURPD07", getDouble("FOURPD05") + O_FOURPD07);
          setValue("THREEPD20", getDouble("THREEPD18") + O_THREEPD20);
        }
        setValue("TWOPD07", getDouble("ONEPD11") - getDouble("ONEPD13"));
        setValue("TWOPD09", (getDouble("TWOPD07") - getDouble("TWOPD06")) * 100.0D / getDouble("ONEPD11"));
        setValue("TWOPD10", (getDouble("ONEPD11") - getDouble("ONEPD13")) * 100.0D / getDouble("ONEPD11"));

        setValue("TWOPD11", (ONEPD12 - ONEPD14) * 100.0D / ONEPD12);
        setValue("THREEPD02", getDouble("THREEPD36") + getDouble("THREEPD37"));

        setValue("THREEPD08", getDouble("THREEPD06") + getDouble("THREEPD07"));
        setValue("THREEPD09", getDouble("THREEPD06") + getDouble("THREEPD07"));

        setValue("THREEPD13", getDouble("THREEPD11") + getDouble("THREEPD12"));
        setValue("THREEPD14", getDouble("THREEPD11") + getDouble("THREEPD12"));
        setValue("THREEPD05", O_THREEPD05 + getDouble("THREEPD01") - getDouble("THREEPD02"));
        setValue("THREEPD16", getDouble("THREEPD18") + O_THREEPD16 - getDouble("THREEPD11") - getDouble("THREEPD12") + getDouble("YDPMTZ"));
        setValue("THREEPD25", (getDouble("THREEPD06") + getDouble("THREEPD07")) * 0.9D + O_THREEPD25 - ((getDouble("THREEPD11") + getDouble("THREEPD12")) * 0.9D));

        setValue("THREEPD24", (getDouble("THREEPD11") + getDouble("THREEPD12")) * 0.9D);

        setValue("THREEPD29", getDouble("THREEPD27") * 100.0D / (getDouble("THREEPD31") + getDouble("THREEPD32")));
        setValue("THREEPD30", getDouble("THREEPD28") * 100.0D / (getDouble("THREEPD31") + getDouble("THREEPD32")));
        setValue("THREEPD33", getDouble("THREEPD31") + getDouble("THREEPD32"));
        setValue("FOURPD05", getDouble("FOURPD03") + getDouble("FOURPD04"));

        setValue("FOURPD09", getDouble("FOURPD03") + getDouble("FOURPD08") + getDouble("HSDHSL") + getDouble("FOURPD04"));
        setValue("FOURPD11", getDouble("FOURPD09") / getDouble("ONEPD11"));
        setValue("FOURPD12", getDouble("FOURPD10") / getDouble("ONEPD12"));
        setValue("FOURPD13", getDouble("ONEPD11") / 24.0D);
        setValue("FOURPD14", getDouble("ONEPD11") * 100.0D / 1440.0D);

        setValue("JYQCKPD1", getDouble("JYQCKPD1"));
        setValue("JYQCKPD2", getDouble("JYQCKPD2"));
        setValue("NOXPD1", getDouble("NOXPD1"));
        setValue("NOXPD2", getDouble("NOXPD2"));
        setValue("SO2PD1", getDouble("SO2PD1"));
        setValue("SO2PD2", getDouble("SO2PD2"));
        setValue("TLTYLPD1", getDouble("TLTYLPD1"));
        setValue("TLTYLPD2", getDouble("TLTYLPD2"));

        setValue("ZQWD1", getDouble("ZQWD1"));
        setValue("ZQWD2", getDouble("ZQWD2"));
        setValue("ZQYL1", getDouble("ZQYL1"));
        setValue("ZQYL2", getDouble("ZQYL2"));
        setValue("ZRQW1", getDouble("ZRQW1"));
        setValue("ZRQW2", getDouble("ZRQW2"));
        setValue("PYWD1", getDouble("PYWD1"));
        setValue("PYWD2", getDouble("PYWD2"));
        setValue("FHHT1", getDouble("FHHT1"));
        setValue("FHHT2", getDouble("FHHT2"));
        setValue("YL1", getDouble("YL1"));
        setValue("YL2", getDouble("YL2"));
        setValue("ZK1", getDouble("ZK1"));
        setValue("ZK2", getDouble("ZK2"));
      }
      else
      {
        setValue("ONEPD02", getDouble("ONEPD01") + O_ONEPD02);
        setValue("ONEPD04", getDouble("ONEPD03") + O_ONEPD04);
        setValue("ONEPD06", getDouble("ONEPD05") + O_ONEPD06);
        setValue("ONEPD08", getDouble("ONEPD07") + O_ONEPD08);
        setValue("ONEPD10", getDouble("ONEPD09") + O_ONEPD10);
        setValue("ONEPD12", getDouble("ONEPD11") + O_ONEPD12);
        setValue("ONEPD14", getDouble("ONEPD13") + O_ONEPD14);

        setValue("ONEPD15", getDouble("ONEPD11") + O_ONEPD15);
        setValue("THREEPD05", O_THREEPD05 + getDouble("THREEPD01") - getDouble("THREEPD02"));
        setValue("THREEPD04", O_THREEPD04 + getDouble("THREEPD02"));
        setValue("THREEPD10", getDouble("THREEPD06") + getDouble("THREEPD07") + O_THREEPD10);
        setValue("THREEPD15", getDouble("THREEPD11") + getDouble("THREEPD12") + O_THREEPD15);
        setValue("THREEPD16", getDouble("THREEPD06") + getDouble("THREEPD07") + O_THREEPD16 - getDouble("THREEPD11") - getDouble("THREEPD12") + getDouble("YDPMTZ"));
        setValue("THREEPDH20", getDouble("THREEPDH18") + O_THREEPDH20);
        setValue("THREEPD25", (getDouble("THREEPD06") + getDouble("THREEPD07")) * 0.9D + O_THREEPD25 - getDouble("THREEPD24"));
        setValue("FOURPD07", getDouble("FOURPD05") + O_FOURPD07);
        setValue("THREEPD20", getDouble("THREEPD18") + O_THREEPD20);

        setValue("JYQCKPD1", getDouble("JYQCKPD1"));
        setValue("JYQCKPD2", getDouble("JYQCKPD2"));
        setValue("NOXPD1", getDouble("NOXPD1"));
        setValue("NOXPD2", getDouble("NOXPD2"));
        setValue("SO2PD1", getDouble("SO2PD1"));
        setValue("SO2PD2", getDouble("SO2PD2"));
        setValue("TLTYLPD1", getDouble("TLTYLPD1"));
        setValue("TLTYLPD2", getDouble("TLTYLPD2"));

        setValue("ZQWD1", getDouble("ZQWD1"));
        setValue("ZQWD2", getDouble("ZQWD2"));
        setValue("ZQYL1", getDouble("ZQYL1"));
        setValue("ZQYL2", getDouble("ZQYL2"));
        setValue("ZRQW1", getDouble("ZRQW1"));
        setValue("ZRQW2", getDouble("ZRQW2"));
        setValue("PYWD1", getDouble("PYWD1"));
        setValue("PYWD2", getDouble("PYWD2"));
        setValue("FHHT1", getDouble("FHHT1"));
        setValue("FHHT2", getDouble("FHHT2"));
        setValue("YL1", getDouble("YL1"));
        setValue("YL2", getDouble("YL2"));
        setValue("ZK1", getDouble("ZK1"));
        setValue("ZK2", getDouble("ZK2"));

        setValue("TWOPD07", getDouble("ONEPD11") - getDouble("ONEPD13"));
        setValue("TWOPD08", getDouble("TWOPD07") + O_TWOPD08);
        setValue("TWOPD09", (getDouble("TWOPD07") - getDouble("TWOPD06")) * 100.0D / getDouble("ONEPD11"));
        setValue("TWOPD10", (getDouble("ONEPD11") - getDouble("ONEPD13")) * 100.0D / getDouble("ONEPD11"));

        setValue("THREEPD02", getDouble("THREEPD36") + getDouble("THREEPD37"));
        setValue("THREEPD03", getDouble("THREEPD02") + O_THREEPD03);

        setValue("THREEPD08", getDouble("THREEPD06") + getDouble("THREEPD07"));
        setValue("THREEPD09", getDouble("THREEPD06") + getDouble("THREEPD07") + O_THREEPD09);

        setValue("THREEPD13", getDouble("THREEPD11") + getDouble("THREEPD12"));
        setValue("THREEPD14", getDouble("THREEPD11") + getDouble("THREEPD12") + O_THREEPD14);

        setValue("THREEPD16", getDouble("THREEPD06") + getDouble("THREEPD07") + O_THREEPD16 - getDouble("THREEPD11") - getDouble("THREEPD12") + getDouble("YDPMTZ"));
        setValue("THREEPD19", getDouble("THREEPD18") + O_THREEPD19);

        setValue("THREEPDH19", getDouble("THREEPDH18") + O_THREEPDH19);

        setValue("THREEPD24", (getDouble("THREEPD11") + getDouble("THREEPD12")) * 0.9D);

        setValue("THREEPD28", getDouble("THREEPD27") + O_THREEPD28);
        setValue("THREEPD29", getDouble("THREEPD27") * 100.0D / (getDouble("THREEPD31") + getDouble("THREEPD32")));
        setValue("THREEPD33", getDouble("THREEPD31") + getDouble("THREEPD32") + O_THREEPD33);
        setValue("THREEPD30", getDouble("THREEPD28") * 100.0D / (getDouble("THREEPD31") + getDouble("THREEPD32") + O_THREEPD33));

        setValue("FOURPD02", getDouble("FOURPD01") + O_FOURPD02);
        setValue("FOURPD05", getDouble("FOURPD03") + getDouble("FOURPD04"));
        setValue("FOURPD06", getDouble("FOURPD05") + O_FOURPD06);

        setValue("FOURPD09", getDouble("FOURPD03") + getDouble("FOURPD08") + getDouble("HSDHSL") + getDouble("FOURPD04"));
        setValue("FOURPD10", getDouble("FOURPD09") + O_FOURPD10);
        setValue("FOURPD11", getDouble("FOURPD09") / getDouble("ONEPD11"));
        setValue("FOURPD12", getDouble("FOURPD10") / getDouble("ONEPD12"));
        setValue("FOURPD13", getDouble("ONEPD11") / 24.0D);
        setValue("FOURPD14", getDouble("ONEPD11") * 100.0D / 1440.0D);
      }
    }
  }

  public void init() throws MXException
  {
    super.init();
    try {
      if ((getString("status").equals("已批准")) || (getString("status").equals("已关闭")) || (getString("status").equals("已取消")))
        setFlag(7L, true);
    }
    catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  public void add() throws MXException, RemoteException {
    super.add();
    setValue("status", "新建");
    setValue("statusdate", new Date());
  }

  private BigDecimal mul(BigDecimal b1, BigDecimal b2) {
    return b1.multiply(b2);
  }

  private BigDecimal add(BigDecimal b1, BigDecimal b2) {
    return b1.add(b2);
  }

  private BigDecimal sub(BigDecimal b1, BigDecimal b2) {
    return b1.subtract(b2);
  }

  private BigDecimal div(BigDecimal b1, BigDecimal b2) {
    return b1.divide(b2, 10, 4);
  }
}