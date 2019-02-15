package com.shuto.mam.app.sclh;

import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author zhaowei
 * @email zhaowei@shuto.cn
 * @create 2018-11-08 16:46
 * @desc
 * @class com.shuto.mam.app.sclh.CustSclh
 * @Copyright: 2018 Shuto版权所有
 **/

public class CustSclh extends Mbo implements MboRemote {

    private static final MXLogger MX_LOGGER = MXLoggerFactory.getLogger("maximo.application");

    public CustSclh(MboSet ms) throws RemoteException {

        super(ms);
    }

    public void createSclh() {

        MboSetRemote sclhSet;
        try {
            Calendar cal = Calendar.getInstance();
            int day1 = cal.get(Calendar.DATE);
            cal.set(Calendar.DATE, day1 + 1);
            cal.set(Calendar.HOUR_OF_DAY, 9);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            String tomDay = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            //非工作日
            MboSetRemote noWorkSet = this.getMboSet("$NONWORKLINE", "NONWORKLINE",
                    " TO_CHAR(NONWORKDATE,'yyyy-MM-dd') = '" + tomDay + "' ");
            if (noWorkSet.isEmpty()) {
                sclhSet = this.getThisMboSet();
                sclhSet.setOrderBy(" to_number(HYCX) DESC");
                sclhSet.reset();
                //上一条记录
                MboRemote sclhMbo = sclhSet.getMbo(0);
                //新建生产例会
                MboRemote newSclhMbo = sclhSet.add();
                newSclhMbo.setValue("CREATEDATE", cal.getTime());
                newSclhMbo.setValue("JOINPEOPLE", sclhMbo.getString("JOINPEOPLE"));
                newSclhMbo.setValue("JLZL", sclhMbo.getString("JLZL"));
                newSclhMbo.setValue("ZS", sclhMbo.getString("ZS"));
                newSclhMbo.setValue("CS", sclhMbo.getString("CS"));
                String num = newSclhMbo.getString("SCDDHYNUM");
                //上一条记录的遗留跟踪
                MboSetRemote ylgzSet = sclhMbo.getMboSet("XXTSX");
                MboRemote ylgzMbo;
                MboRemote newYlgzMbo;
                if (!ylgzSet.isEmpty()) {
                    //新记录的遗留跟踪
                    MboSetRemote newYlgzSet = newSclhMbo.getMboSet("XXTSX");
                    int i = 0;
                    while (ylgzSet.getMbo(i) != null) {
                        ylgzMbo = ylgzSet.getMbo(i);
                        newYlgzMbo = newYlgzSet.add();
                        newYlgzMbo.setValue("XXTSXNUM", num, 11L);
                        newYlgzMbo.setValue("XH", ylgzMbo.getString("XH"), 11L);
                        newYlgzMbo.setValue("DESCRIPTION", ylgzMbo.getString("DESCRIPTION"), 11L);
                        newYlgzMbo.setValue("ZYFK", ylgzMbo.getString("ZYFK"), 11L);
                        newYlgzMbo.setValue("XH", ylgzMbo.getString("XH"), 11L);
                        newYlgzMbo.setValue("RECORDTYPE", ylgzMbo.getString("RECORDTYPE"), 11L);
                        newYlgzMbo.setValue("RECORDBY", ylgzMbo.getString("RECORDBY"), 11L);
                        newYlgzMbo.setValue("DEPARTMENT", ylgzMbo.getString("DEPARTMENT"), 11L);
                        i++;
                    }
                }
                sclhSet.save();
            }
            noWorkSet.close();
        } catch (MXException | RemoteException e) {
            MX_LOGGER.error(e);
        }
    }
}
