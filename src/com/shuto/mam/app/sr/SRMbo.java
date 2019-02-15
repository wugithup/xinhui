package com.shuto.mam.app.sr;

import java.rmi.RemoteException;

import com.ibm.ism.content.mriu.StringUtil;
import psdi.app.ticket.SR;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

public class SRMbo extends SR implements SRMboRemote {
	public SRMbo(MboSet ms) throws RemoteException, MXException {
		super(ms);
	}

	@Override
	public void init() throws MXException {
		super.init();
		String s_qxlb = getMboValue("S_QXLB").getString();
		String fl = getMboValue("S_CLASSIFICATION").getString();
		if ("四类缺陷".equalsIgnoreCase(s_qxlb)) {
			String[] s_classification = { "S_CLASSIFICATION" };
			this.setFieldFlag(s_classification, 128L, true);// 必填
		}
		if ("生产".equalsIgnoreCase(fl) && "四类缺陷".equals(s_qxlb)) {
			this.setFieldFlag("YXJK", 128L, true);// 必填
		}
		if ("三类缺陷".equals(s_qxlb)) {
			this.setFieldFlag("YXJK", 128L, true);// 必填
		}
		if ("二类缺陷".equals(s_qxlb)) {
			this.setFieldFlag("YXJK", 128L, true);// 必填
		}
		if ("一类缺陷".equals(s_qxlb)) {
			this.setFieldFlag("YXJK", 128L, true);// 必填
		}
	}

    @Override
    protected   void save() throws MXException, RemoteException {
	    //获得缺陷流水号
	   /* String ticketid = getString("TICKETID");
	    //转专业
	    String s_zzyto = getString("S_ZZYTO");
	    //原专业
	    String s_yzy = getString("S_YZY");

	    //最终专业
	    String profession = getString("PROFESSION");
        String s_zzymemo = getString("S_ZZYMEMO");

        if (StringUtil.isEmpty(s_yzy))
        {
            setValue("S_YZY",profession);
        }else{
            if (!StringUtil.isEmpty(s_zzyto))
            {

                //获得转专业对象
                MboSetRemote turnMajorMboSet = getMboSet("TICKETID");
                MboRemote turnMajorMbo =null;
                if (!turnMajorMboSet.isEmpty())
                {

                    turnMajorMboSet.setOrderBy("CREATEDATE DESC");
                    String turnMajorS_zzyto = turnMajorMboSet.getMbo(0).getString("S_ZZYTO");
                    if (!s_zzyto.equals(turnMajorS_zzyto))
                    {
                        //记录转专业次数
                        int cont  = turnMajorMboSet.count()+1;

                        turnMajorMboSet.reset();
                        turnMajorMbo = turnMajorMboSet.add();
                        turnMajorMbo.setValue("S_YZY",turnMajorS_zzyto);
                        setValue("S_YZY",turnMajorS_zzyto);
                        turnMajorMbo.setValue("S_ZZYTO",s_zzyto);
                        turnMajorMbo.setValue("S_ZZZY3",profession);
                        turnMajorMbo.setValue("TICKETID",ticketid);
                        turnMajorMbo.setValue("S_ZZYMEMO",s_zzymemo);
                        turnMajorMbo.setValue("DESCRIPTION","第"+cont+"次转专业");
                        turnMajorMbo.getThisMboSet().save();
                    }
                }else{
                    turnMajorMboSet.reset();
                    turnMajorMbo = turnMajorMboSet.add();
                    turnMajorMbo.setValue("S_ZZYTO",s_zzyto);
                    turnMajorMbo.setValue("S_YZY",s_yzy);
                    turnMajorMbo.setValue("S_ZZZY3",profession);
                    turnMajorMbo.setValue("TICKETID",ticketid);
                    turnMajorMbo.setValue("S_ZZYMEMO",s_zzymemo);
                    turnMajorMbo.setValue("DESCRIPTION","第1次转专业");
                    turnMajorMbo.getThisMboSet().save();
                }




            }


        }*/



        super.save();
    }



}