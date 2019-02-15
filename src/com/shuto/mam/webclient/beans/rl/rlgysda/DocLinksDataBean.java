package com.shuto.mam.webclient.beans.rl.rlgysda;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * com.shuto.mam.webclient.beans.rl.rlgysda.DocLinksDataBean 华东大区 阜阳项目
 * 副产品供应商档案附件列表类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月11日 下午1:48:29
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class DocLinksDataBean extends
		psdi.webclient.beans.doclinks.DocLinksBean {

	public void addTxt() throws RemoteException, MXException {
		clientSession.loadDialog("addnewattachmentfile");
		MboRemote mbo = this.app.getAppBean().getMbo();
		MboSetRemote msrht = mbo.getMboSet("doclinks");
		msrht.setOrderBy("doclinksid desc");
		msrht.setValue("type", "供应商档案文件", DataBean.ATTR_READONLY);
	}

	/**
	 * 来访登记
	 * 
	 * @throws RemoteException
	 * @throws MXException
	 */
	public void addLF() throws RemoteException, MXException {
		clientSession.loadDialog("addnewattachmentfile");
		MboRemote mbo = this.app.getAppBean().getMbo();
		MboSetRemote msrht = mbo.getMboSet("doclinks1");
		msrht.setValue("type", "来访登记", DataBean.ATTR_READONLY);
	}

	/**
	 * 去访登记
	 * 
	 * @throws RemoteException
	 * @throws MXException
	 */
	public void addQF() throws RemoteException, MXException {
		clientSession.loadDialog("addnewattachmentfile");
		MboRemote mbo = this.app.getAppBean().getMbo();
		MboSetRemote msrht = mbo.getMboSet("doclinks2");
		msrht.setValue("type", "去访登记", DataBean.ATTR_READONLY);
	}
}
