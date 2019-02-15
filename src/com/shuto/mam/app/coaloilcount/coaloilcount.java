package com.shuto.mam.app.coaloilcount;
/**  
com.shuto.mam.app.coaloilcount.coaloilcount 河北
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年5月22日 下午8:26:27
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.mbo.SqlFormat;
import psdi.util.MXException;

public class coaloilcount extends Mbo
  implements coaloilcountRemote
{
  public coaloilcount(MboSet mboset)
    throws RemoteException, MXException
  {
    super(mboset);
  }

  public void save() throws MXException, RemoteException {
    super.save();
    Date tjdate = getDate("tjdate");
    MboSetRemote pdoldmboset = getMboSet("$oldmboset", "ST_COALOILCOUNT");
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

      double BONEPD01 = oldmbo.getDouble("ONEPD01");
      double BONEPD03 = oldmbo.getDouble("ONEPD03");

      double BTWOPD01 = oldmbo.getDouble("TWOPD01");
      double BTWOPD03 = oldmbo.getDouble("TWOPD03");
      double BTWOPD06 = oldmbo.getDouble("TWOPD06");

      double BTHREEPD02 = oldmbo.getDouble("THREEPD02");
      double BTHREEPD04 = oldmbo.getDouble("THREEPD04");

      double BFOURPD01 = oldmbo.getDouble("FOURPD01");
      double BFOURPD04 = oldmbo.getDouble("FOURPD04");
      double BFOURPD07 = oldmbo.getDouble("FOURPD07");
      double BFOURPD10 = oldmbo.getDouble("FOURPD10");
      double BFOURPD13 = oldmbo.getDouble("FOURPD13");
      double BFOURPD16 = oldmbo.getDouble("FOURPD16");
      double BFOURPD18 = oldmbo.getDouble("FOURPD18");

      double BFIVEPD01 = oldmbo.getDouble("FIVEPD01");
      double BFIVEPD04 = oldmbo.getDouble("FIVEPD04");
      double BFIVEPD07 = oldmbo.getDouble("FIVEPD07");
      double BFIVEPD10 = oldmbo.getDouble("FIVEPD10");
      double BFIVEPD13 = oldmbo.getDouble("FIVEPD13");
      double BFIVEPD16 = oldmbo.getDouble("FIVEPD16");
      double BFIVEPD18 = oldmbo.getDouble("FIVEPD18");

      double BSIXPD01 = oldmbo.getDouble("SIXPD01");
      double BSIXPD03 = oldmbo.getDouble("SIXPD03");
      double BSIXPD06 = oldmbo.getDouble("SIXPD06");

      double BSEVENPD01 = oldmbo.getDouble("SEVENPD01");
      double BSEVENPD03 = oldmbo.getDouble("SEVENPD03");
      double BSEVENPD05 = oldmbo.getDouble("SEVENPD05");
      double BSEVENPD07 = oldmbo.getDouble("SEVENPD07");

      double TONEPD01 = getDouble("ONEPD01");
      double TONEPD03 = getDouble("ONEPD03");

      double TTWOPD01 = getDouble("TWOPD01");
      double TTWOPD03 = getDouble("TWOPD03");

      double TFOURPD01 = getDouble("FOURPD01");
      double TFOURPD04 = getDouble("FOURPD04");
      double TFOURPD07 = getDouble("FOURPD07");
      double TFOURPD10 = getDouble("FOURPD10");
      double TFOURPD13 = getDouble("FOURPD13");

      double TFIVEPD01 = getDouble("FIVEPD01");
      double TFIVEPD04 = getDouble("FIVEPD04");
      double TFIVEPD07 = getDouble("FIVEPD07");
      double TFIVEPD10 = getDouble("FIVEPD10");
      double TFIVEPD13 = getDouble("FIVEPD13");

      double TSIXPD01 = getDouble("SIXPD01");
      double TSIXPD03 = getDouble("SIXPD03");

      double TSEVENPD01 = getDouble("SEVENPD01");
      double TSEVENPD03 = getDouble("SEVENPD03");

      SimpleDateFormat dd = new SimpleDateFormat("dd");
      String tjday = dd.format(tjdate);
      Date tjd = getDate("tjdate");
      SimpleDateFormat ddf = new SimpleDateFormat("yyyy年MM月dd日");
      setValue("description", ddf.format(tjdate) + "锅炉燃煤燃油统计");

      if (Integer.valueOf(tjday).intValue() == 1)
      {
        setValue("ONEPD02", TONEPD01 - BONEPD01);
        setValue("ONEPD04", TONEPD03 - BONEPD03);
        setValue("ONEPD05", TONEPD01 - BONEPD01 + TONEPD03 - BONEPD03);

        setValue("TWOPD02", TTWOPD01 - BTWOPD01);
        setValue("TWOPD04", TTWOPD03 - BTWOPD03);
        setValue("TWOPD05", TTWOPD01 - BTWOPD01 + TTWOPD03 - BTWOPD03);
        setValue("TWOPD06", TTWOPD01 - BTWOPD01 + TTWOPD03 - BTWOPD03);

        setValue("THREEPD01", TFOURPD01 + TFOURPD04 + TFOURPD07 + TFOURPD10 + TFOURPD13 - BFOURPD16 + TFIVEPD01 + TFIVEPD04 + TFIVEPD07 + TFIVEPD10 + TFIVEPD13 - BFIVEPD16);
        setValue("THREEPD02", TFOURPD01 + TFOURPD04 + TFOURPD07 + TFOURPD10 + TFOURPD13 - BFOURPD16 + TFIVEPD01 + TFIVEPD04 + TFIVEPD07 + TFIVEPD10 + TFIVEPD13 - BFIVEPD16);
        setValue("THREEPD03", TTWOPD01 - BTWOPD01 + TTWOPD03 - BTWOPD03 - (TFOURPD01 + TFOURPD04 + TFOURPD07 + TFOURPD10 + TFOURPD13 - BFOURPD16 + TFIVEPD01 + TFIVEPD04 + TFIVEPD07 + TFIVEPD10 + TFIVEPD13 - BFIVEPD16));
        setValue("THREEPD04", TTWOPD01 - BTWOPD01 + TTWOPD03 - BTWOPD03 - (TFOURPD01 + TFOURPD04 + TFOURPD07 + TFOURPD10 + TFOURPD13 - BFOURPD16 + TFIVEPD01 + TFIVEPD04 + TFIVEPD07 + TFIVEPD10 + TFIVEPD13 - BFIVEPD16));

        setValue("FOURPD02", TFOURPD01 - BFOURPD01);
        setValue("FOURPD05", TFOURPD04 - BFOURPD04);
        setValue("FOURPD08", TFOURPD07 - BFOURPD07);
        setValue("FOURPD11", TFOURPD10 - BFOURPD10);
        setValue("FOURPD14", TFOURPD13 - BFOURPD13);
        setValue("FOURPD16", TFOURPD01 + TFOURPD04 + TFOURPD07 + TFOURPD10 + TFOURPD13);
        setValue("FOURPD17", TFOURPD01 + TFOURPD04 + TFOURPD07 + TFOURPD10 + TFOURPD13 - BFOURPD16);
        setValue("FOURPD18", TFOURPD01 + TFOURPD04 + TFOURPD07 + TFOURPD10 + TFOURPD13 - BFOURPD16);

        setValue("FIVEPD02", TFIVEPD01 - BFIVEPD01);
        setValue("FIVEPD05", TFIVEPD04 - BFIVEPD04);
        setValue("FIVEPD08", TFIVEPD07 - BFIVEPD07);
        setValue("FIVEPD11", TFIVEPD10 - BFIVEPD10);
        setValue("FIVEPD14", TFIVEPD13 - BFIVEPD13);
        setValue("FIVEPD16", TFIVEPD01 + TFIVEPD04 + TFIVEPD07 + TFIVEPD10 + TFIVEPD13);
        setValue("FIVEPD17", TFIVEPD01 + TFIVEPD04 + TFIVEPD07 + TFIVEPD10 + TFIVEPD13 - BFIVEPD16);
        setValue("FIVEPD18", TFIVEPD01 + TFIVEPD04 + TFIVEPD07 + TFIVEPD10 + TFIVEPD13 - BFIVEPD16);

        setValue("SIXPD02", TSIXPD01 - BSIXPD01);
        setValue("SIXPD04", TSIXPD03 - BSIXPD03);
        setValue("SIXPD05", TSIXPD01 - BSIXPD01 + (TSIXPD03 - BSIXPD03));
        setValue("SIXPD06", TSIXPD01 - BSIXPD01 + (TSIXPD03 - BSIXPD03) + BSIXPD06);

        setValue("SEVENPD02", TSEVENPD01 - BSEVENPD01);
        setValue("SEVENPD04", TSEVENPD03 - BSEVENPD03);
        setValue("SEVENPD05", TSEVENPD01 + TSEVENPD03);
        setValue("SEVENPD06", TSEVENPD01 + TSEVENPD03 - BSEVENPD05);
        setValue("SEVENPD07", TSEVENPD01 + TSEVENPD03 - BSEVENPD05);
      }
      else
      {
        setValue("ONEPD02", TONEPD01 - BONEPD01);
        setValue("ONEPD04", TONEPD03 - BONEPD03);
        setValue("ONEPD05", TONEPD01 - BONEPD01 + TONEPD03 - BONEPD03);

        setValue("TWOPD02", TTWOPD01 - BTWOPD01);
        setValue("TWOPD04", TTWOPD03 - BTWOPD03);
        setValue("TWOPD05", TTWOPD01 - BTWOPD01 + TTWOPD03 - BTWOPD03);
        setValue("TWOPD06", TTWOPD01 - BTWOPD01 + TTWOPD03 - BTWOPD03 + BTWOPD06);

        setValue("THREEPD01", TFOURPD01 + TFOURPD04 + TFOURPD07 + TFOURPD10 + TFOURPD13 - BFOURPD16 + TFIVEPD01 + TFIVEPD04 + TFIVEPD07 + TFIVEPD10 + TFIVEPD13 - BFIVEPD16);
        setValue("THREEPD02", BTHREEPD02 + TFOURPD01 + TFOURPD04 + TFOURPD07 + TFOURPD10 + TFOURPD13 - BFOURPD16 + TFIVEPD01 + TFIVEPD04 + TFIVEPD07 + TFIVEPD10 + TFIVEPD13 - BFIVEPD16);
        setValue("THREEPD03", TTWOPD01 - BTWOPD01 + TTWOPD03 - BTWOPD03 - (TFOURPD01 + TFOURPD04 + TFOURPD07 + TFOURPD10 + TFOURPD13 - BFOURPD16 + TFIVEPD01 + TFIVEPD04 + TFIVEPD07 + TFIVEPD10 + TFIVEPD13 - BFIVEPD16));
        setValue("THREEPD04", TTWOPD01 - BTWOPD01 + TTWOPD03 - BTWOPD03 - (TFOURPD01 + TFOURPD04 + TFOURPD07 + TFOURPD10 + TFOURPD13 - BFOURPD16 + TFIVEPD01 + TFIVEPD04 + TFIVEPD07 + TFIVEPD10 + TFIVEPD13 - BFIVEPD16) + BTHREEPD04);

        setValue("FOURPD02", TFOURPD01 - BFOURPD01);
        setValue("FOURPD05", TFOURPD04 - BFOURPD04);
        setValue("FOURPD08", TFOURPD07 - BFOURPD07);
        setValue("FOURPD11", TFOURPD10 - BFOURPD10);
        setValue("FOURPD14", TFOURPD13 - BFOURPD13);
        setValue("FOURPD16", TFOURPD01 + TFOURPD04 + TFOURPD07 + TFOURPD10 + TFOURPD13);
        setValue("FOURPD17", TFOURPD01 + TFOURPD04 + TFOURPD07 + TFOURPD10 + TFOURPD13 - BFOURPD16);
        setValue("FOURPD18", TFOURPD01 + TFOURPD04 + TFOURPD07 + TFOURPD10 + TFOURPD13 - BFOURPD16 + BFOURPD18);

        setValue("FIVEPD02", TFIVEPD01 - BFIVEPD01);
        setValue("FIVEPD05", TFIVEPD04 - BFIVEPD04);
        setValue("FIVEPD08", TFIVEPD07 - BFIVEPD07);
        setValue("FIVEPD11", TFIVEPD10 - BFIVEPD10);
        setValue("FIVEPD14", TFIVEPD13 - BFIVEPD13);
        setValue("FIVEPD16", TFIVEPD01 + TFIVEPD04 + TFIVEPD07 + TFIVEPD10 + TFIVEPD13);
        setValue("FIVEPD17", TFIVEPD01 + TFIVEPD04 + TFIVEPD07 + TFIVEPD10 + TFIVEPD13 - BFIVEPD16);
        setValue("FIVEPD18", TFIVEPD01 + TFIVEPD04 + TFIVEPD07 + TFIVEPD10 + TFIVEPD13 - BFIVEPD16 + BFIVEPD18);

        setValue("SIXPD02", TSIXPD01 - BSIXPD01);
        setValue("SIXPD04", TSIXPD03 - BSIXPD03);
        setValue("SIXPD05", TSIXPD01 - BSIXPD01 + (TSIXPD03 - BSIXPD03));
        setValue("SIXPD06", TSIXPD01 - BSIXPD01 + (TSIXPD03 - BSIXPD03) + BSIXPD06);

        setValue("SEVENPD02", TSEVENPD01 - BSEVENPD01);
        setValue("SEVENPD04", TSEVENPD03 - BSEVENPD03);
        setValue("SEVENPD05", TSEVENPD01 + TSEVENPD03);
        setValue("SEVENPD06", TSEVENPD01 + TSEVENPD03 - BSEVENPD05);
        setValue("SEVENPD07", TSEVENPD01 + TSEVENPD03 - BSEVENPD05 + BSEVENPD07);
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
}
