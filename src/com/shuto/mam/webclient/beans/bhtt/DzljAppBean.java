package com.shuto.mam.webclient.beans.bhtt;
/**  
com.shuto.mam.webclient.beans.bhtt.DzljAppBean 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月23日 下午6:13:01
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

import com.shuto.mam.webclient.beans.base.CAppBean;

public class DzljAppBean extends CAppBean
{
  public int SAVE()
    throws MXException, RemoteException
  {
    MboRemote localMboRemote = getMbo();

    if ((localMboRemote.getString("OPMA_BHTTJDZXGDJNUM") == null) || ("".equals(localMboRemote.getString("OPMA_BHTTJDZXGDJNUM"))))
    {
      createOPMA_BHTTJDZXGDJNUM();
    }
    return super.SAVE();
  }

  public void createOPMA_BHTTJDZXGDJNUM()
    throws RemoteException, MXException
  {
    MboRemote localMboRemote = getMbo();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    Calendar localCalendar = Calendar.getInstance();

    MboSetRemote localMboSetRemote = localMboRemote.getMboSet("$OPMA_BHTTJDZXGDJ", "OPMA_BHTTJDZXGDJ", "  OPMA_BHTTJDZXGDJNUM  in (select max(OPMA_BHTTJDZXGDJNUM) from OPMA_BHTTJDZXGDJ where   to_char(CREATEDATE,'yyyyMMdd')='" + localSimpleDateFormat.format(localCalendar.getTime()) + "'  )   ");

    System.out.println("select   * from  OPMA_BHTTJDZXGDJ where  OPMA_BHTTJDZXGDJNUM  in (select max(OPMA_BHTTJDZXGDJNUM) from OPMA_BHTTJDZXGDJ where   to_char(CREATEDATE,'yyyyMMdd')='" + localSimpleDateFormat.format(localCalendar.getTime()) + "'  )  ");

    DecimalFormat localDecimalFormat = new DecimalFormat("000");

    if (localMboSetRemote.isEmpty()) {
      localMboRemote.setValue("OPMA_BHTTJDZXGDJNUM", "BHXG-" + localSimpleDateFormat.format(localCalendar.getTime()) + "-001", 11L);
    }
    else {
      String str1 = localMboSetRemote.getMbo(0).getString("OPMA_BHTTJDZXGDJNUM");
      String str2 = str1.substring(str1.length() - 3, str1.length());

      int i = Integer.parseInt(str2);
      i += 1;
      localMboRemote.setValue("OPMA_BHTTJDZXGDJNUM", "BHXG-" + localSimpleDateFormat.format(localCalendar.getTime()) + "-" + localDecimalFormat.format(i), 11L);
    }

    localMboSetRemote.close();
  }
}
