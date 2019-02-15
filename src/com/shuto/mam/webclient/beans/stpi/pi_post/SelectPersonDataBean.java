package com.shuto.mam.webclient.beans.stpi.pi_post;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.Vector;

import com.shuto.mam.app.stpi.Toolkit;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.beans.MultiselectDataBean;
import psdi.webclient.system.controller.WebClientEvent;
/**
 * 岗位选择人员，
 * @author mabin
 * @date 2016年11月14日
 */

public class SelectPersonDataBean extends MultiselectDataBean{

	public SelectPersonDataBean(){
		
	}

	@Override
	protected void initialize() throws MXException, RemoteException {
		//得到当前dialog对话框中的数据集
		MboSetRemote mboSet=this.getMboSet();
//		//得到当前主对象
		MboRemote mainMbo = app.getAppBean().getMbo();
		String sql="";
		MboSetRemote personSet = mainMbo.getMboSet("ST_PI_USER");
		String str = Toolkit.getConcat(personSet, "PERSONID");
		if ((mainMbo != null)) {
			if (!personSet.isEmpty()) {
				sql = " STATUS ='活动' and personid not in "+str+"";
			} else {
				sql = " STATUS ='活动'";
			}
		}
		mboSet.setWhere(sql);
		mboSet.reset();
		super.initialize();
	}
	/**
	 *  
	 */
	
	public int selectPerson() throws MXException, RemoteException, ParseException {
		// 得到当前应用程序databean
		DataBean appBean = app.getAppBean();
		MboRemote mainMbo = appBean.getMbo();
		//得到当前主单主子表关联编号值
		long id=mainMbo.getUniqueIDValue();
		// 获得到dialog中数据的dialogSet
		MboSetRemote dialogSet = this.getMboSet();
		Vector vector = dialogSet.getSelection();
		if (vector.size() <= 0) {
			return 1;
		}
		// 获得对话框选中的数据
		dialogSet.resetWithSelection();
		//通过关联得到子表当中的结果集
		MboSetRemote lineSet = mainMbo.getMboSet("ST_PI_USER");
		MboRemote dialogMbo = null;
		MboRemote lineMbo = null;
		for(int i=0;i<dialogSet.count();i++){
			    // 取得对话框里的记录，把数据插入到子表里面
				dialogMbo=dialogSet.getMbo(i);
				lineMbo = lineSet.add(11l);
				lineMbo.setValue("ST_PI_POSTID", String.valueOf(id),11l);
				lineMbo.setValue("PERSONID", dialogMbo.getString("PERSONID"),11l);
		}
		app.getDataBean("1477638790308").reloadTable();
		app.getDataBean("1477638790308").refreshTable();
		sessionContext.queueEvent(new WebClientEvent("dialogok", sessionContext
				.getCurrentPageId(), "", sessionContext));
		return 1;
	}
}
