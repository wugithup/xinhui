package com.shuto.mam.app.pm.pmline;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author wuchang
 * @version V1.0
 * @Title: FldFrequnit
 * @Package com.shuto.mam.app.pm.pmline
 * @Description: pnline表中FREQUNIT字段的Fld类
 * @date 2018/12/20 10:10
 */
public class FldFrequnit extends MboValueAdapter {
    public FldFrequnit(MboValue mbv){
        super(mbv);
    }

    /**
     * 当用户选择频率单位时对牵涉的字段进行控制
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public void action() throws MXException, RemoteException {
        MboRemote mbo = this.getMboValue().getMbo();
        String thisAtt = this.getMboValue().getName();
        String unitname = mbo.getString(thisAtt);
        final String week = "固定星期";
        final String date = "固定日期";
        if (!unitname.isEmpty()) {

            if (date.equals(unitname) || week.equals(unitname)) {
                //当用户选择固定星期或固定日期的时候进行如下操作
                String frequency = mbo.getString("FREQUENCY");
                if(frequency != null && !"".equals(frequency))
                {
                    mbo.setFieldFlag("FREQUENCY", MboConstants.READONLY, false);
                    mbo.setValueNull("FREQUENCY");
                }

                mbo.setFieldFlag("FREQUENCY", MboConstants.READONLY, true);
                mbo.setFieldFlag("FIXEDDAY", MboConstants.READONLY, false);

            }else{

                String fixed = mbo.getString("FIXEDDAY");
                if(fixed != null && !"".equals(fixed))
                {
                    mbo.setFieldFlag("FIXEDDAY", MboConstants.READONLY, false);
                    mbo.setValueNull("FIXEDDAY");
                }
                mbo.setFieldFlag("FREQUENCY", MboConstants.READONLY, false);
                mbo.setFieldFlag("FIXEDDAY", MboConstants.READONLY, true);
            }

        }


    }

}
