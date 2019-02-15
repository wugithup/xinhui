package com.shuto.mam.webclient.beans.dfjs;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
/**
 * 电费结算
 com.shuto.mam.webclient.beans.dfjs.dfjsAppBean 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午8:56:02
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class dfjsAppBean extends CAppBean
{
  public int INSERT()
    throws MXException, RemoteException
  {
    int i = super.INSERT();
    MboRemote thismbo = this.app.getAppBean().getMbo();
    String personid = thismbo.getThisMboSet().getUserInfo().getPersonId();
    thismbo.setValue("STATUS", "新建");

    return i;
  }

  public int SAVE() throws MXException, RemoteException
  {
    String status = getMbo().getString("STATUS");
    BigDecimal BYZFDL = null;
    BigDecimal BYZFDJ = null;
    BigDecimal BYBTDDL = null;
    BigDecimal BYBTDDJ = null;
    BigDecimal BYKHDL = null;
    BigDecimal BYKHDJ = null;
    BigDecimal BYZBDL = null;
    BigDecimal BYZBDJ = null;
    BigDecimal BYWSDL = null;
    BigDecimal BYWSDJ = null;
    BigDecimal BYSYDL = null;
    BigDecimal BYSYDJ = null;
    BigDecimal BYRLDFDL = null;
    BigDecimal BYRLDFDJ = null;
    BigDecimal BYCSCBDL = null;
    BigDecimal BYCSCBDJ = null;
    BigDecimal BYTDDL = null;
    BigDecimal BYBWYXFYDF = null;
    BigDecimal BYFZFWFYDF = null;
    BigDecimal BYLGXZXJDF = null;

    MboRemote thismbo = this.app.getAppBean().getMbo();
    MboSetRemote SetMbo = getMbo().getMboSet("DFJSB");

    String Y = thismbo.getString("YEAR");
    String M = thismbo.getString("MONTH");
    String JZ = thismbo.getString("JZ");
    status = thismbo.getString("STATUS");
    MboSetRemote sfcfSet = thismbo.getMboSet("$DFJSB", "DFJSB", "YEAR='" + 
      Y + "' and MONTH='" + M + "' and JZ='" + JZ + "'");
    if (sfcfSet.isEmpty())
    {
      if (status.equals("新建"))
      {
        BYZFDL = new BigDecimal(thismbo.getString("BYZFDL").replace(
          ",", ""));
        BYZFDJ = new BigDecimal(thismbo.getString("BYZFDJ").replace(
          ",", ""));
        BYBTDDL = new BigDecimal(thismbo.getString("BYBTDDL").replace(
          ",", ""));
        BYBTDDJ = new BigDecimal(thismbo.getString("BYBTDDJ").replace(
          ",", ""));
        BYKHDL = new BigDecimal(thismbo.getString("BYKHDL").replace(
          ",", ""));
        BYKHDJ = new BigDecimal(thismbo.getString("BYKHDJ").replace(
          ",", ""));
        BYZBDL = new BigDecimal(thismbo.getString("BYZBDL").replace(
          ",", ""));
        BYZBDJ = new BigDecimal(thismbo.getString("BYZBDJ").replace(
          ",", ""));
        BYWSDL = new BigDecimal(thismbo.getString("BYWSDL").replace(
          ",", ""));
        BYWSDJ = new BigDecimal(thismbo.getString("BYWSDJ").replace(
          ",", ""));
        BYSYDL = new BigDecimal(thismbo.getString("BYSYDL").replace(
          ",", ""));
        BYSYDJ = new BigDecimal(thismbo.getString("BYSYDJ").replace(
          ",", ""));
        BYRLDFDL = new BigDecimal(thismbo.getString("BYRLDFDL")
          .replace(",", ""));
        BYRLDFDJ = new BigDecimal(thismbo.getString("BYRLDFDJ")
          .replace(",", ""));
        BYCSCBDL = new BigDecimal(thismbo.getString("BYCSCBDL")
          .replace(",", ""));
        BYCSCBDJ = new BigDecimal(thismbo.getString("BYCSCBDJ")
          .replace(",", ""));
        BYTDDL = new BigDecimal(thismbo.getString("BYTDDL").replace(
          ",", ""));
        BYBWYXFYDF = new BigDecimal(thismbo.getString("BYBWYXFYDF")
          .replace(",", ""));
        BYFZFWFYDF = new BigDecimal(thismbo.getString("BYFZFWFYDF")
          .replace(",", ""));
        BYLGXZXJDF = new BigDecimal(thismbo.getString("BYLGXZXJDF")
          .replace(",", ""));

        BigDecimal BYZFDF = mul(BYZFDL, BYZFDJ);
        thismbo.setValue("BYZFDF", BYZFDF.doubleValue());
        BigDecimal BYBTDDF = mul(BYBTDDL, BYBTDDJ);
        thismbo.setValue("BYBTDDF", BYBTDDF.doubleValue());
        BigDecimal BYKHDF = mul(BYKHDL, BYKHDJ);
        thismbo.setValue("BYKHDF", BYKHDF.doubleValue());
        BigDecimal BYZBDF = mul(BYZBDL, BYZBDJ);
        thismbo.setValue("BYZBDF", BYZBDF.doubleValue());
        BigDecimal BYWSDF = mul(BYWSDL, BYWSDJ);
        thismbo.setValue("BYWSDF", BYWSDF.doubleValue());
        BigDecimal BYSYDF = mul(BYSYDL, BYSYDJ);
        thismbo.setValue("BYSYDF", BYSYDF.doubleValue());
        BigDecimal BYRLDFDF = mul(BYRLDFDL, BYRLDFDJ);
        thismbo.setValue("BYRLDFDF", BYRLDFDF.doubleValue());
        BigDecimal BYCSCBDF = mul(BYCSCBDL, BYCSCBDJ);
        thismbo.setValue("BYCSCBDF", BYCSCBDF.doubleValue());
        BigDecimal BYSYXJDL = add(add(add(BYZFDL, BYBTDDL), BYKHDL), BYZBDL);
        thismbo.setValue("BYSYXJDL", BYSYXJDL.doubleValue());
        BigDecimal BYSYXJDF = add(add(add(BYZFDF, BYBTDDF), BYKHDF), BYZBDF);
        thismbo.setValue("BYSYXJDF", BYSYXJDF.doubleValue());

        BigDecimal BYQTXJDL = add(add(add(BYWSDL, BYSYDL), BYRLDFDL), BYCSCBDL);
        thismbo.setValue("BYQTXJDL", BYQTXJDL.doubleValue());
        BigDecimal BYQTXJDF = add(add(add(BYWSDF, BYSYDF), BYRLDFDF), BYCSCBDF);
        thismbo.setValue("BYQTXJDF", BYQTXJDF.doubleValue());
        BigDecimal BYTDDF = add(BYSYXJDF, BYLGXZXJDF);
        thismbo.setValue("BYTDDF", BYTDDF.doubleValue());

        BigDecimal BYGDHJDL = add(BYSYXJDL, BYQTXJDL);
        thismbo.setValue("BYGDHJDL", BYGDHJDL.doubleValue());
        BigDecimal BYGDHJDF = add(add(add(BYSYXJDF, BYQTXJDF), BYBWYXFYDF), BYFZFWFYDF);
        thismbo.setValue("BYGDHJDF", BYGDHJDF.doubleValue());

        Double dl = Double.valueOf(SetMbo.sum("BYGDHJDL"));
        BigDecimal NLJGDHJDL = add(new BigDecimal(dl.doubleValue()), BYGDHJDL);
        thismbo.setValue("NLJGDHJDL", NLJGDHJDL.doubleValue());
        Double d2 = Double.valueOf(SetMbo.sum("BYZFDL"));
        BigDecimal NLJZFDL = add(new BigDecimal(d2.doubleValue()), BYZFDL);
        thismbo.setValue("NLJZFDL", NLJZFDL.doubleValue());
        Double d3 = Double.valueOf(SetMbo.sum("BYBTDDL"));
        BigDecimal NLJBTDDL = add(new BigDecimal(d3.doubleValue()), BYBTDDL);
        thismbo.setValue("NLJBTDDL", NLJBTDDL.doubleValue());
        Double d4 = Double.valueOf(SetMbo.sum("BYKHDL"));
        BigDecimal NLJKHDL = add(new BigDecimal(d4.doubleValue()), BYKHDL);
        thismbo.setValue("NLJKHDL", NLJKHDL.doubleValue());
        Double d5 = Double.valueOf(SetMbo.sum("BYZBDL"));
        BigDecimal NLJZBDL = add(new BigDecimal(d5.doubleValue()), BYZBDL);
        thismbo.setValue("NLJZBDL", NLJZBDL.doubleValue());
        Double d6 = Double.valueOf(SetMbo.sum("BYSYXJDL"));
        BigDecimal NLJSYXJDL = add(new BigDecimal(d6.doubleValue()), BYSYXJDL);
        thismbo.setValue("NLJSYXJDL", NLJSYXJDL.doubleValue());
        Double d7 = Double.valueOf(SetMbo.sum("BYWSDL"));
        BigDecimal NLJWSDL = add(new BigDecimal(d7.doubleValue()), BYWSDL);
        thismbo.setValue("NLJWSDL", NLJWSDL.doubleValue());
        Double d8 = Double.valueOf(SetMbo.sum("BYSYDL"));
        BigDecimal NLJSYDL = add(new BigDecimal(d8.doubleValue()), BYSYDL);
        thismbo.setValue("NLJSYDL", NLJSYDL.doubleValue());
        Double d9 = Double.valueOf(SetMbo.sum("BYRLDFDL"));
        BigDecimal NLJRLDFDL = add(new BigDecimal(d9.doubleValue()), BYRLDFDL);
        thismbo.setValue("NLJRLDFDL", NLJRLDFDL.doubleValue());
        Double dl0 = Double.valueOf(SetMbo.sum("BYCSCBDL"));
        BigDecimal NLJCSZBDL = add(new BigDecimal(dl0.doubleValue()), BYCSCBDL);
        thismbo.setValue("NLJCSZBDL", NLJCSZBDL.doubleValue());
        Double dl1 = Double.valueOf(SetMbo.sum("BYQTXJDL"));
        BigDecimal NLJQTXJDL = add(new BigDecimal(dl1.doubleValue()), BYQTXJDL);
        thismbo.setValue("NLJQTXJDL", NLJQTXJDL.doubleValue());
        Double dl2 = Double.valueOf(SetMbo.sum("BYTDDL"));
        BigDecimal NLJTDDL = add(new BigDecimal(dl2.doubleValue()), BYTDDL);
        thismbo.setValue("NLJTDDL", NLJTDDL.doubleValue());
        Double dl3 = Double.valueOf(SetMbo.sum("BYGDHJDF"));
        BigDecimal NLJGDHJDF = add(new BigDecimal(dl3.doubleValue()), BYGDHJDF);
        thismbo.setValue("NLJGDHJDF", NLJGDHJDF.doubleValue());
        Double dl4 = Double.valueOf(SetMbo.sum("BYZFDF"));
        BigDecimal NLJZFDF = add(new BigDecimal(dl4.doubleValue()), BYZFDF);
        thismbo.setValue("NLJZFDF", NLJZFDF.doubleValue());
        Double dl5 = Double.valueOf(SetMbo.sum("BYBTDDF"));
        BigDecimal NLJBTDDF = add(new BigDecimal(dl5.doubleValue()), BYBTDDF);
        thismbo.setValue("NLJBTDDF", NLJBTDDF.doubleValue());
        Double dl6 = Double.valueOf(SetMbo.sum("BYKHDF"));
        BigDecimal NLJKHDF = add(new BigDecimal(dl6.doubleValue()), BYKHDF);
        thismbo.setValue("NLJKHDF", NLJKHDF.doubleValue());
        Double dl7 = Double.valueOf(SetMbo.sum("BYZBDF"));
        BigDecimal NLJZBDF = add(new BigDecimal(dl7.doubleValue()), BYZBDF);
        thismbo.setValue("NLJZBDF", NLJZBDF.doubleValue());
        Double dl8 = Double.valueOf(SetMbo.sum("BYSYXJDF"));
        BigDecimal NLJSYXJDF = add(new BigDecimal(dl8.doubleValue()), BYSYXJDF);
        thismbo.setValue("NLJSYXJDF", NLJSYXJDF.doubleValue());
        Double dl9 = Double.valueOf(SetMbo.sum("BYWSDF"));
        BigDecimal NLJWSDF = add(new BigDecimal(dl9.doubleValue()), BYWSDF);
        thismbo.setValue("NLJWSDF", NLJWSDF.doubleValue());
        Double d20 = Double.valueOf(SetMbo.sum("BYSYDF"));
        BigDecimal NLJSYDF = add(new BigDecimal(d20.doubleValue()), BYSYDF);
        thismbo.setValue("NLJSYDF", NLJSYDF.doubleValue());
        Double d21 = Double.valueOf(SetMbo.sum("BYRLDFDF"));
        BigDecimal NLJRLDFDF = add(new BigDecimal(d21.doubleValue()), BYRLDFDF);
        thismbo.setValue("NLJRLDFDF", NLJRLDFDF.doubleValue());
        Double d22 = Double.valueOf(SetMbo.sum("BYCSCBDF"));
        BigDecimal NLJCSZBDF = add(new BigDecimal(d22.doubleValue()), BYCSCBDF);
        thismbo.setValue("NLJCSZBDF", NLJCSZBDF.doubleValue());
        Double d23 = Double.valueOf(SetMbo.sum("BYQTXJDF"));
        BigDecimal NLJQTXJDF = add(new BigDecimal(d23.doubleValue()), BYQTXJDF);
        thismbo.setValue("NLJQTXJDF", NLJQTXJDF.doubleValue());
        Double d24 = Double.valueOf(SetMbo.sum("BYBWYXFYDF"));
        BigDecimal NLJBWYXFYDF = add(new BigDecimal(d24.doubleValue()), BYBWYXFYDF);
        thismbo.setValue("NLJBWYXFYDF", NLJBWYXFYDF.doubleValue());
        Double d25 = Double.valueOf(SetMbo.sum("BYFZFWFYDF"));
        BigDecimal NLJFZFWFYDF = add(new BigDecimal(d25.doubleValue()), BYFZFWFYDF);
        thismbo.setValue("NLJFZFWFYDF", NLJFZFWFYDF.doubleValue());
        Double d26 = Double.valueOf(SetMbo.sum("BYLGXZXJDF"));
        BigDecimal NLJLGXZXJDF = add(new BigDecimal(d26.doubleValue()), BYLGXZXJDF);
        thismbo.setValue("NLJLGXZXJDF", NLJLGXZXJDF.doubleValue());
        BigDecimal NLJKHDJ;
        if (NLJKHDL.doubleValue() == 0.0D) {
          NLJKHDJ = add(NLJKHDF, NLJKHDL);
          thismbo.setValue("NLJKHDJ", NLJKHDJ.doubleValue());
        }
        else if (NLJKHDL.doubleValue() != 0.0D) {
          NLJKHDJ = div(NLJKHDF, NLJKHDL);
          thismbo.setValue("NLJKHDJ", NLJKHDJ.doubleValue());
        }
        BigDecimal NLJZBDJ;
        if (NLJZBDL.doubleValue() == 0.0D) {
          NLJZBDJ = add(NLJZBDF, NLJZBDL);
          thismbo.setValue("NLJZBDJ", NLJZBDJ.doubleValue());
        }
        else if (NLJZBDL.doubleValue() != 0.0D) {
          NLJZBDJ = div(NLJZBDF, NLJZBDL);
          thismbo.setValue("NLJZBDJ", NLJZBDJ.doubleValue());
        }
        BigDecimal NLJSYXJDJ;
        if (NLJSYXJDL.doubleValue() == 0.0D) {
          NLJSYXJDJ = add(NLJSYXJDF, NLJSYXJDL);
          thismbo.setValue("NLJSYXJDJ", NLJSYXJDJ.doubleValue());
        }
        else if (NLJSYXJDL.doubleValue() != 0.0D) {
          NLJSYXJDJ = div(NLJSYXJDF, NLJSYXJDL);
          thismbo.setValue("NLJSYXJDJ", NLJSYXJDJ.doubleValue());
        }
        BigDecimal NLJGDHJDJ;
        if (NLJGDHJDL.doubleValue() == 0.0D) {
          NLJGDHJDJ = add(NLJGDHJDF, NLJGDHJDL);
          thismbo.setValue("NLJGDHJDJ", NLJGDHJDJ.doubleValue());
        }
        else if (NLJGDHJDL.doubleValue() != 0.0D) {
          NLJGDHJDJ = div(NLJGDHJDF, NLJGDHJDL);
          thismbo.setValue("NLJGDHJDJ", NLJGDHJDJ.doubleValue());
        }
        BigDecimal NLJZFDJ;
        if (NLJZFDL.doubleValue() == 0.0D) {
          NLJZFDJ = add(NLJZFDF, NLJZFDL);
          thismbo.setValue("NLJZFDJ", NLJZFDJ.doubleValue());
        }
        else if (NLJZFDL.doubleValue() != 0.0D) {
          NLJZFDJ = div(NLJZFDF, NLJZFDL);
          thismbo.setValue("NLJZFDJ", NLJZFDJ.doubleValue());
        }
        BigDecimal NLJBTDDJ;
        if (NLJBTDDL.doubleValue() == 0.0D) {
          NLJBTDDJ = add(NLJBTDDF, NLJBTDDL);
          thismbo.setValue("NLJBTDDJ", NLJBTDDJ.doubleValue());
        }
        else if (NLJBTDDL.doubleValue() != 0.0D) {
          NLJBTDDJ = div(NLJBTDDF, NLJBTDDL);
          thismbo.setValue("NLJBTDDJ", NLJBTDDJ.doubleValue());
        }
        BigDecimal NLJWSDJ;
        if (NLJWSDL.doubleValue() == 0.0D) {
          NLJWSDJ = add(NLJWSDF, NLJWSDL);
          thismbo.setValue("NLJWSDJ", NLJWSDJ.doubleValue());
        }
        else if (NLJWSDL.doubleValue() != 0.0D) {
          NLJWSDJ = div(NLJWSDF, NLJWSDL);
          thismbo.setValue("NLJWSDJ", NLJWSDJ.doubleValue());
        }
        BigDecimal NLJSYDJ;
        if (NLJSYDL.doubleValue() == 0.0D) {
          NLJSYDJ = add(NLJSYDF, NLJSYDL);
          thismbo.setValue("NLJSYDJ", NLJSYDJ.doubleValue());
        }
        else if (NLJSYDL.doubleValue() != 0.0D) {
          NLJSYDJ = div(NLJSYDF, NLJSYDL);
          thismbo.setValue("NLJSYDJ", NLJSYDJ.doubleValue());
        }
        BigDecimal NLJRLDFDJ;
        if (NLJRLDFDL.doubleValue() == 0.0D) {
          NLJRLDFDJ = add(NLJRLDFDF, NLJRLDFDL);
          thismbo.setValue("NLJRLDFDJ", NLJRLDFDJ.doubleValue());
        }
        else if (NLJRLDFDL.doubleValue() != 0.0D) {
          NLJRLDFDJ = div(NLJRLDFDF, NLJRLDFDL);
          thismbo.setValue("NLJRLDFDJ", NLJRLDFDJ.doubleValue());
        }
        BigDecimal NLJCSZBDJ;
        if (NLJCSZBDL.doubleValue() == 0.0D) {
          NLJCSZBDJ = add(NLJCSZBDF, NLJCSZBDL);
          thismbo.setValue("NLJCSZBDJ", NLJCSZBDJ.doubleValue());
        }
        else if (NLJCSZBDL.doubleValue() != 0.0D) {
          NLJCSZBDJ = div(NLJCSZBDF, NLJCSZBDL);
          thismbo.setValue("NLJCSZBDJ", NLJCSZBDJ.doubleValue());
        }
        BigDecimal NLJQTXJDJ;
        if (NLJQTXJDL.doubleValue() == 0.0D)
        {
          NLJQTXJDJ = add(NLJQTXJDF, NLJQTXJDL);
          thismbo.setValue("NLJQTXJDJ", NLJQTXJDJ.doubleValue());
        }
        else if (NLJQTXJDL.doubleValue() != 0.0D) {
          NLJQTXJDJ = div(NLJQTXJDF, NLJQTXJDL);
          thismbo.setValue("NLJQTXJDJ", NLJQTXJDJ.doubleValue());
        }
        BigDecimal BYSYXJDJ;
        if (BYSYXJDL.doubleValue() == 0.0D)
        {
          BYSYXJDJ = add(BYSYXJDF, BYSYXJDL);
          thismbo.setValue("BYSYXJDJ", BYSYXJDJ.doubleValue());
        }
        else if (BYSYXJDL.doubleValue() != 0.0D) {
          BYSYXJDJ = div(BYSYXJDF, BYSYXJDL);
          thismbo.setValue("BYSYXJDJ", BYSYXJDJ.doubleValue());
        }
        BigDecimal BYQTXJDJ;
        if (BYQTXJDL.doubleValue() == 0.0D) {
          BYQTXJDJ = add(BYQTXJDF, BYQTXJDL);
          thismbo.setValue("BYQTXJDJ", BYQTXJDJ.doubleValue());
        }
        else if (BYQTXJDL.doubleValue() != 0.0D) {
          BYQTXJDJ = div(BYQTXJDF, BYQTXJDL);
          thismbo.setValue("BYQTXJDJ", BYQTXJDJ.doubleValue());
        }
        BigDecimal BYGDHJDJ;
        if (BYGDHJDL.doubleValue() == 0.0D) {
          BYGDHJDJ = add(BYGDHJDF, BYGDHJDL);
          thismbo.setValue("BYGDHJDJ", BYGDHJDJ.doubleValue());
        }
        else if (BYGDHJDL.doubleValue() != 0.0D) {
          BYGDHJDJ = div(BYGDHJDF, BYGDHJDL);
          thismbo.setValue("BYGDHJDJ", BYGDHJDJ.doubleValue());
        }

        super.SAVE();
      }

    }
    else if ((!(sfcfSet.isEmpty())) && (sfcfSet.count() > 1)) {
      throw new MXApplicationException("", "当前月份不允许重复");
    }

    return super.SAVE();
  }

  private BigDecimal mul(BigDecimal b1, BigDecimal b2)
  {
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