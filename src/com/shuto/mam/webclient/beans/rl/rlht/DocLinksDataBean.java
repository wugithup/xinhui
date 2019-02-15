package com.shuto.mam.webclient.beans.rl.rlht;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * com.shuto.mam.webclient.beans.rl.rlht.DocLinksDataBean 华东大区 阜阳项目 副产品供应商准入附件用
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月21日 下午4:55:06
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class DocLinksDataBean extends
		psdi.webclient.beans.doclinks.DocLinksBean {

	public MboSetRemote getMboSetRemote() throws MXException, RemoteException {
		MboSetRemote msr = super.getMboSetRemote();
		return msr;
	}

	public void addfujian() throws MXException, RemoteException {

		MboRemote mbo = this.app.getAppBean().getMbo();
		String status = mbo.getString("STATUS");
		MboSetRemote msrht = mbo.getMboSet("DOCLINKS");
		msrht.setOrderBy("doclinksid desc");

		if ((!"新建".equals(status)) && (!"已退回".equals(status))
				&& (!"退回修改".equals(status))) {
			msrht.setFlag(7L, true);

			throw new MXApplicationException("提示 ", " 此状态禁止删除添加附件！");
		} else {
			clientSession.loadDialog("addnewattachmentfile");

		}

	}

	public int instantdelete() throws MXException {
		try {
			String status = getMbo().getOwner().getString("STATUS");
			if ((!"新建".equals(status)) && (!"已退回".equals(status))
					&& (!"退回修改".equals(status)) && (!"索赔中".equals(status))) {
				getMbo().setFlag(7L, true);
				throw new MXApplicationException("提示 ", " 此状态禁止删除添加附件！");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return super.instantdelete();
	}

	public int toggledeleterow() throws MXException {
		try {
			String status = getMbo().getOwner().getString("STATUS");
			if ((!"新建".equals(status)) && (!"已退回".equals(status))
					&& (!"退回修改".equals(status)) && (!"索赔中".equals(status))) {
				getMbo().setFlag(7L, true);
				throw new MXApplicationException("提示 ", " 此状态禁止删除添加附件！");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return super.instantdelete();
	}

	/**
	 * 燃煤合同
	 * 
	 * @throws RemoteException
	 * @throws MXException
	 */
	public void addfj() throws RemoteException, MXException {

		MboRemote mbo = this.app.getAppBean().getMbo();
		String status = mbo.getString("STATUS");
		MboSetRemote msrht = mbo.getMboSet("DOCLINKS_RMHT");
		msrht.setOrderBy("doclinksid desc");

		if ((!"新建".equals(status)) && (!"已退回".equals(status))
				&& (!"退回修改".equals(status))) {
			msrht.setFlag(7L, true);

			throw new MXApplicationException("提示 ", " 此状态禁止删除添加附件！");
		} else {
			clientSession.loadDialog("addnewattachmentfile");
			// msrht.setValue("type", "燃煤合同",DataBean.ATTR_READONLY);
		}
	}

	/**
	 * 燃煤合同附件
	 * 
	 * @throws RemoteException
	 * @throws MXException
	 */
	public void addhtfj() throws RemoteException, MXException {
		try {
			MboRemote mbo = this.app.getAppBean().getMbo();
			String status = mbo.getString("STATUS");
			MboSetRemote msrht = mbo.getMboSet("DOCLINKS_RMHTFJ");
			if ((!"新建".equals(status)) && (!"已退回".equals(status))
					&& (!"退回修改".equals(status))) {
				msrht.setFlag(7L, true);
				throw new MXApplicationException("提示 ", " 此状态禁止删除添加附件！");
			} else {
				clientSession.loadDialog("addnewattachmentfile");
				// msrht.setValue("type", "燃煤合同附件",DataBean.ATTR_READONLY);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 燃煤合同其他附件
	 * 
	 * @throws RemoteException
	 * @throws MXException
	 */
	public void addqt() throws RemoteException, MXException {
		try {
			MboRemote mbo = this.app.getAppBean().getMbo();
			String status = mbo.getString("STATUS");
			MboSetRemote msrht = mbo.getMboSet("DOCLINKS_RMTHQT");
			if ((!"新建".equals(status)) && (!"已退回".equals(status))
					&& (!"退回修改".equals(status))) {
				msrht.setFlag(7L, true);
				throw new MXApplicationException("提示 ", " 此状态禁止删除添加附件！");
			} else {
				clientSession.loadDialog("addnewattachmentfile");
				// msrht.setValue("type", "燃煤合同其他",DataBean.ATTR_READONLY);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 亏吨亏卡索赔函附件
	 * 
	 * @throws RemoteException
	 * @throws MXException
	 */
	public void addSph() throws RemoteException, MXException {

		try {
			MboRemote mbo = this.app.getAppBean().getMbo();
			String status = mbo.getString("STATUS");
			MboSetRemote msrht = mbo.getMboSet("doclinks");
			if ((!"新建".equals(status)) && (!"已退回".equals(status))
					&& (!"退回修改".equals(status)) && (!"索赔中".equals(status))) {
				msrht.setFlag(7L, true);
				throw new MXApplicationException("提示 ", " 此状态禁止删除添加附件！");
			} else {
				clientSession.loadDialog("addnewattachmentfile");
				// msrht.setValue("type", "索赔函",DataBean.ATTR_READONLY);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 亏吨亏卡索赔结果附件
	 * 
	 * @throws RemoteException
	 * @throws MXException
	 */
	public void addSpjg() throws RemoteException, MXException {
		try {
			MboRemote mbo = this.app.getAppBean().getMbo();
			String status = mbo.getString("STATUS");
			MboSetRemote msrht = mbo.getMboSet("doclinks1");
			if ((!"新建".equals(status)) && (!"已退回".equals(status))
					&& (!"退回修改".equals(status)) && (!"索赔中".equals(status))) {
				msrht.setFlag(7L, true);
				throw new MXApplicationException("提示 ", " 此状态禁止删除添加附件！");
			} else {
				clientSession.loadDialog("addnewattachmentfile");
				// msrht.setValue("type", "索赔结果",DataBean.ATTR_READONLY);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 服务合同支付
	 * 
	 * @throws RemoteException
	 * @throws MXException
	 */
	public void addfwhtzffj() throws RemoteException, MXException {

		MboRemote mbo = this.app.getAppBean().getMbo();
		String status = mbo.getString("STATUS");
		MboSetRemote msrht = mbo.getMboSet("DOCLINKS3");
		msrht.setOrderBy("doclinksid desc");

		if ((!"新建".equals(status)) && (!"已退回".equals(status))
				&& (!"退回修改".equals(status))) {
			msrht.setFlag(7L, true);

			throw new MXApplicationException("提示 ", " 此状态禁止删除添加附件！");
		} else {
			clientSession.loadDialog("addnewattachmentfile");
			// msrht.setValue("type", "服务合同支付",DataBean.ATTR_READONLY);
		}
	}

	/**
	 * 服务合同支付矿方
	 * 
	 * @throws RemoteException
	 * @throws MXException
	 */
	public void addfwhtzfkf() throws RemoteException, MXException {

		MboRemote mbo = this.app.getAppBean().getMbo();
		String status = mbo.getString("STATUS");
		MboSetRemote msrht = mbo.getMboSet("DOCLINKS4");
		msrht.setOrderBy("doclinksid desc");

		if ((!"新建".equals(status)) && (!"已退回".equals(status))
				&& (!"退回修改".equals(status))) {
			msrht.setFlag(7L, true);

			throw new MXApplicationException("提示 ", " 此状态禁止删除添加附件！");
		} else {
			clientSession.loadDialog("addnewattachmentfile");
			// msrht.setValue("type", "服务合同支付矿方",DataBean.ATTR_READONLY);
		}
	}

	/**
	 * 燃煤合同支付
	 * 
	 * @throws RemoteException
	 * @throws MXException
	 */
	public void addrmhtzffj() throws RemoteException, MXException {

		try {
			MboRemote mbo = this.app.getAppBean().getMbo();
			String status = mbo.getString("STATUS");
			MboSetRemote msrht = mbo.getMboSet("DOCLINKS1");
			msrht.setOrderBy("doclinksid desc");

			if ((!"新建".equals(status)) && (!"已退回".equals(status))
					&& (!"退回修改".equals(status))) {
				msrht.setFlag(7L, true);

				throw new MXApplicationException("提示 ", " 此状态禁止删除添加附件！");
			} else {
				clientSession.loadDialog("addnewattachmentfile");
				System.out.println("11111111111" + msrht);
				// msrht.setValue("type","燃煤合同支付",DataBean.ATTR_READONLY);

			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 燃煤合同支付矿方
	 * 
	 * @throws RemoteException
	 * @throws MXException
	 */
	public void addrmhtzfkf() throws RemoteException, MXException {

		MboRemote mbo = this.app.getAppBean().getMbo();
		String status = mbo.getString("STATUS");
		MboSetRemote msrht = mbo.getMboSet("DOCLINKS2");

		msrht.setOrderBy("doclinksid desc");

		if ((!"新建".equals(status)) && (!"已退回".equals(status))
				&& (!"退回修改".equals(status))) {
			msrht.setFlag(7L, true);

			throw new MXApplicationException("提示 ", " 此状态禁止删除添加附件！");
		} else {
			clientSession.loadDialog("addnewattachmentfile");
			// msrht.setValue("type", "燃煤合同支付矿方",DataBean.ATTR_READONLY);
		}
	}
}
