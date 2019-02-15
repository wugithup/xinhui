/**
 * 
 */
package com.shuto.mam.webclient.beans.duty;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * 
com.shuto.mam.webclient.beans.duty.DutynoteDataBean 江苏
* @author       zhaowei  zhaowei@shuoto.cn
* @date         2017年5月17日 下午5:00:06
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class DutynoteDataBean extends DataBean {

	
	public int addrow() throws MXException {
		try {
			DataBean resultsBean = this.app.getDataBean("1406258629004");
			int j = resultsBean.count();
			String personid = this.parent.getMbo().getUserInfo().getPersonId();
			Date qdtime = psdi.server.MXServer.getMXServer().getDate();
			MboSetRemote persons = this.parent.getMbo().getMboSet("#person", "person","personid='"+personid+"'");
			String bm = "";
			String zbperson = "";
			String tel = "";
			String zy = "";
			
			if (!persons.isEmpty()) {
				bm = persons.getMbo(0).getString("department");
				zbperson = persons.getMbo(0).getString("displayname");
				tel = persons.getMbo(0).getString("MOBILEPHONE");
				zy = persons.getMbo(0).getString("PROFESSION");
			}
			
			String siteid = this.parent.getMbo().getString("siteid"); 
			if("RDBQ000".equals(siteid)) {
				if (j>0) {
				    MboSetRemote dutynotes = resultsBean.getMboSet();
				    String zbpersonid = null;
					for (int i = 0; i < dutynotes.count(); i++) {
					    zbpersonid = dutynotes.getMbo(i).getString("ZBPERSONID");
					    if(personid.equals(zbpersonid)) {
					       throw new MXApplicationException("", ""+zbperson+"的值班记录已经存在,请不要重复登记");
					    }
					}
				}
			}
			super.addrow();
			
		    //把系统中人员信息自动带入展开列表中
			this.getMbo().setValue("zy", zy);
			this.getMbo().setValue("tel", tel);
			this.getMbo().setValue("bm", bm);
			this.getMbo().setValue("zpperson", zbperson);
			this.getMbo().setValue("zbpersonid", personid);
			this.getMbo().setValue("isqd", 1,11L);
			this.getMbo().setValue("qdtime", qdtime,11L);
			this.getMbo().setFieldFlag("RECORD", 7L , false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 1;
	}
}

