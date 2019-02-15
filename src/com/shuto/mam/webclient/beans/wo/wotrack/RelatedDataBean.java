package com.shuto.mam.webclient.beans.wo.wotrack;

import java.rmi.RemoteException;
import java.sql.SQLException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;

/**
 * 跳转到SAFETY系统工作票
 * @author Liuyc
 * @version 1.0
 *
 */
public class RelatedDataBean extends DataBean {
	
	/**
	 * 工作票跳转按钮
	 * @throws RemoteException
	 * @throws MXException
	 * @throws SQLException
	 */
	public void skiptohi() throws RemoteException, MXException {
		MboRemote mainMbo = this.getMbo();
		DataBean WotickDataBean = this.app.getDataBean("relatedrec_related_wt");
		if (WotickDataBean != null) {
			MboRemote wotickmbo = WotickDataBean.getMbo();
			if (wotickmbo != null) {
				String orgid = mainMbo.getString("orgid");
				String siteid = mainMbo.getString("siteid");
				String personid = mainMbo.getUserInfo().getPersonId();
				String woseqnum = wotickmbo.getString("RELATEDRECWO.WOSEQNUM");
				
				MboSetRemote users = mainMbo.getMboSet("$maxuser","MAXUSER","personid='"+personid+"'");
				if (users != null && !users.isEmpty()) {
					String loginid= users.getMbo(0).getString("loginid");
					String password = users.getMbo(0).getString("ldappassword");
					MboSetRemote msr=mainMbo.getMboSet("&exterser","EXTERSER","serconfnum=(select serconfnum from exterserconfig where partnerid='SAFETY') "
							+ "and upper(serapp)='WOTOWOTRACK' and orgid='"+orgid+"' and siteid='"+siteid+"'");

				    if(!msr.isEmpty()){
				    	MboRemote configMbo = msr.getMbo(0);
				    	String url = configMbo.getString("serurl");
				    	if (url != null && !"".equals(url)) {
				    		/**
						     * 替换参数
						     */
						    url=url.replaceAll("\\*woseqnum\\*", woseqnum);
							url= url.replaceAll("\\*password\\*",password);
							url=url.replaceAll("\\*user\\*", loginid);
						   
						    this.app.openURL(url, true);
				    	} else {
				    		throw new MXApplicationException("system", "接口URL缺失,请联系系统管理员");
				    	}
				    } else {
				    	throw new MXApplicationException("system", "缺少接口配置,请联系系统管理员");
				    }
				    msr.close();
				} else {
					throw new MXApplicationException("system", "用户不存在,请确认当前用户是否有效");
				}
			} else {
				Utility.showMessageBox(this.clientSession.getCurrentEvent(), "提示","关联工作票不存在,请联系管理员"  ,1);
			}
		}
	}
}
