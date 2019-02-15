package com.shuto.mam.webclient.beans.dljs;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
/**
 * 电量结算
 com.shuto.mam.webclient.beans.dljs.dljsAppBean 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午8:59:02
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class dljsAppBean extends CAppBean
{
  public int INSERT()
    throws MXException, RemoteException
  {
    int i = super.INSERT();
    MboRemote thismbo = this.app.getAppBean().getMbo();
    String personid = thismbo.getThisMboSet().getUserInfo().getPersonId();
    thismbo.setValue("STATUS", "新建");
    thismbo.setValue("HY1XBL", "5500");
    thismbo.setValue("HY2XBL", "5500");
    thismbo.setValue("YHZBBL", "2750");
    thismbo.setValue("EHZBBL", "2750");

    return i;
  }

  public int SAVE()
    throws MXException, RemoteException
  {
    String status = getMbo().getString("STATUS");
    BigDecimal HY1XYM = null;
    BigDecimal HY2XYM = null;
    BigDecimal YHZBYM = null;
    BigDecimal EHZBYM = null;

    MboRemote thismbo = this.app.getAppBean().getMbo();
    MboSetRemote SetMbo = getMbo().getMboSet("MONTH");
    MboSetRemote Set1Mbo = getMbo().getMboSet("SMONTH");
    String Y = thismbo.getString("YEAR");
    String M = thismbo.getString("MONTH");
    String JZ = thismbo.getString("JZ");
    status = thismbo.getString("STATUS");
    MboSetRemote sfcfSet = thismbo.getMboSet("$DLJSB", "DLJSB", "YEAR='" + Y + "' and MONTH='" + M + "' and JZ='" + JZ + "'");
    if (sfcfSet.isEmpty()) {
      if (status.equals("新建"))
      {
        HY1XYM = new BigDecimal(thismbo.getString("HY1XYM").replace(",", ""));
        HY2XYM = new BigDecimal(thismbo.getString("HY2XYM").replace(",", ""));
        YHZBYM = new BigDecimal(thismbo.getString("YHZBYM").replace(",", ""));
        EHZBYM = new BigDecimal(thismbo.getString("EHZBYM").replace(",", ""));
        if (!(Set1Mbo.isEmpty())) {
          BigDecimal HY1XYC = new BigDecimal(Set1Mbo.getMbo(0).getString("HY1XYM").replace(",", ""));
          thismbo.setValue("HY1XYC", HY1XYC.doubleValue());
          BigDecimal HY2XYC = new BigDecimal(Set1Mbo.getMbo(0).getString("HY2XYM").replace(",", ""));
          thismbo.setValue("HY2XYC", HY2XYC.doubleValue());
          BigDecimal YHZBYC = new BigDecimal(Set1Mbo.getMbo(0).getString("YHZBYM").replace(",", ""));
          thismbo.setValue("YHZBYC", YHZBYC.doubleValue());
          BigDecimal EHZBYC = new BigDecimal(Set1Mbo.getMbo(0).getString("EHZBYM").replace(",", ""));
          thismbo.setValue("EHZBYC", EHZBYC.doubleValue());
          BigDecimal HY1XBL = new BigDecimal(thismbo.getString("HY1XBL").replace(",", ""));
          BigDecimal HY2XBL = new BigDecimal(thismbo.getString("HY2XBL").replace(",", ""));
          BigDecimal YHZBBL = new BigDecimal(thismbo.getString("YHZBBL").replace(",", ""));
          BigDecimal localBigDecimal1 = new BigDecimal(thismbo.getString("EHZBBL").replace(",", ""));
        }

        BigDecimal HY1XDL = mul(sub(HY1XYM, new BigDecimal(thismbo.getString("HY1XYC").replace(",", ""))), new BigDecimal(thismbo.getString("HY1XBL").replace(",", "")));
        thismbo.setValue("HY1XDL", HY1XDL.doubleValue());
        BigDecimal HY2XDL = mul(sub(HY2XYM, new BigDecimal(thismbo.getString("HY2XYC").replace(",", ""))), new BigDecimal(thismbo.getString("HY2XBL").replace(",", "")));
        thismbo.setValue("HY2XDL", HY2XDL.doubleValue());
        BigDecimal YHZBDL = mul(sub(YHZBYM, new BigDecimal(thismbo.getString("YHZBYC").replace(",", ""))), new BigDecimal(thismbo.getString("YHZBBL").replace(",", "")));
        thismbo.setValue("YHZBDL", YHZBDL.doubleValue());
        BigDecimal EHZBDL = mul(sub(EHZBYM, new BigDecimal(thismbo.getString("EHZBYC").replace(",", ""))), new BigDecimal(thismbo.getString("EHZBBL").replace(",", "")));
        thismbo.setValue("EHZBDL", EHZBDL.doubleValue());
        BigDecimal CJDLHJ;
        BigDecimal GDLHJ;
        if (JZ.equals("1")) {
          CJDLHJ = mul(div(YHZBDL, add(YHZBDL, EHZBDL)), add(HY1XDL, HY2XDL));
          thismbo.setValue("CJDLHJ", CJDLHJ.doubleValue());

          if (!(SetMbo.isEmpty())) {
            GDLHJ = sub(CJDLHJ, new BigDecimal(SetMbo.getMbo(0).getString("BYTDDL").replace(",", "")));
            thismbo.setValue("GDLHJ", GDLHJ.doubleValue());
          } else {
            GDLHJ = CJDLHJ;
            thismbo.setValue("GDLHJ", GDLHJ.doubleValue());
          }

        }
        else if (JZ.equals("2")) {
          CJDLHJ = mul(div(EHZBDL, add(YHZBDL, EHZBDL)), add(HY1XDL, HY2XDL));
          thismbo.setValue("CJDLHJ", CJDLHJ.doubleValue());

          if (!(SetMbo.isEmpty())) {
            GDLHJ = sub(CJDLHJ, new BigDecimal(SetMbo.getMbo(0).getString("BYTDDL").replace(",", "")));
            thismbo.setValue("GDLHJ", GDLHJ.doubleValue());
          } else {
            GDLHJ = CJDLHJ;
            thismbo.setValue("GDLHJ", GDLHJ.doubleValue());
          }

          super.SAVE();
        }
      }

    }
    else if ((!(sfcfSet.isEmpty())) && (sfcfSet.count() > 1)) {
      throw new MXApplicationException("", "当前月份不允许重复");
    }

    return super.SAVE();
  }

  private BigDecimal mul(BigDecimal b1, BigDecimal b2)
  {
    return b1.multiply(b2); }

  private BigDecimal add(BigDecimal b1, BigDecimal b2) {
    return b1.add(b2); }

  private BigDecimal sub(BigDecimal b1, BigDecimal b2) {
    return b1.subtract(b2); }

  private BigDecimal div(BigDecimal b1, BigDecimal b2) {
    return b1.divide(b2, 10, 4);
  }
}