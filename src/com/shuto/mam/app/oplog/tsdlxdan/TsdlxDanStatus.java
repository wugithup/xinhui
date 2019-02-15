package com.shuto.mam.app.oplog.tsdlxdan;

import com.ibm.icu.text.DecimalFormat;

import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.shuto.mam.app.oplog.number.NumberSetRemote;
import java.rmi.RemoteException;
import java.util.Calendar;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
/**
 * 停送电联系单
 com.shuto.mam.app.oplog.tsdlxdan.TsdlxDanStatus 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月9日 上午9:25:27
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class TsdlxDanStatus extends MboValueAdapter
{
  public TsdlxDanStatus()
  {
  }

  public TsdlxDanStatus(MboValue mbv)
  {
    super(mbv); }

  public void action() throws MXException, RemoteException {
    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("MM");
    SimpleDateFormat ye = new SimpleDateFormat("yyyy");
    String year = ye.format(c.getTime());
    String month = df.format(c.getTime());
    String SITEID = getMboValue("SITEID").getString();
    NumberFormat nf = new DecimalFormat("000");

    String usernm = getMboValue().getMbo().getUserInfo().getPersonId();
    String status = getMboValue("STATUS").getString();
    String zhiji = getMboValue("ZHIJI").getString();
    String jzorxt = getMboValue("JZORXT").getString();
    if (status.equals("已批准"))
    {
      getMboValue("SHRPERSON").setValue(usernm);
      getMboValue("SHDATE").setValue(c.getTime());
      int num = getNumber(year, month, "ST_TSDLXDAN", SITEID);
      String lxdpnum = nf.format(num);
      String endczpnum = "TSD-" + year + "-" + month + "-" + zhiji + "-" + jzorxt + "-" + lxdpnum;
      getMboValue("LXDNUM").setValue(endczpnum);
    }

    super.action();
  }

  private int getNumber(String year, String month, String biaoname, String siteid)
    throws RemoteException, MXException
  {
    NumberSetRemote number = (NumberSetRemote)getMboValue().getMbo().getMboSet("ST_NUMBER");
    number.setFlag(false);
    return number.getCurNumber(year, month, biaoname, siteid);
  }
}