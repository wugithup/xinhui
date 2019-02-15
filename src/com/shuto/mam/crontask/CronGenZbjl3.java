package com.shuto.mam.crontask;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import psdi.app.system.CrontaskParamInfo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.UserInfo;
import psdi.server.MXServer;
import psdi.server.SimpleCronTask;
import psdi.util.MXException;

/**
 * *值班记录定时任务类
 * 
 * @author shuto
 * @since 2012-01-07
 */
/**
 * 
com.shuto.mam.crontask.CronGenZbjl 江苏
* @author       zhaowei  zhaowei@shuoto.cn
* @date         2017年5月17日 下午4:54:16
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class CronGenZbjl3 extends SimpleCronTask {

	MXServer mxserver;
	UserInfo userInfo;
	SimpleDateFormat dtfmt = new SimpleDateFormat("yyyy年MM月dd日");
	// 参数mboSet
	private MboSetRemote parmateSet = null ;

	public void init() throws MXException {
		try {
			super.init();
			this.mxserver = MXServer.getMXServer();
			this.userInfo = getRunasUserInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 触发定时任务
	 */
	public void cronAction() {
		try {
			creatCron();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成值班记录
	 */
	private void creatCron() {
		MboSetRemote zbjls = null;
		MboRemote mbo = null;
		try {
			parmateSet = this.getCrontaskInstance().getMboSet("PARAMETER");
			parmateSet.reset();
			if(parmateSet.count()==0){
				return ;
			}
			String orgid = "";
			String siteid = "";
			for (int i=0;i<parmateSet.count();i++) {
				if ("ORGID".equalsIgnoreCase(parmateSet.getMbo(i).getString("parameter"))) {
					orgid = parmateSet.getMbo(i).getString("value");
				}
				if("siteid".equalsIgnoreCase(parmateSet.getMbo(i).getString("parameter"))){
					siteid=parmateSet.getMbo(i).getString("value");
				}
			}
			
			zbjls = this.mxserver.getMboSet("DUTYZHU", this.userInfo);
			mbo = zbjls.add();
			
			Date date = new Date();
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
			String time = dateformat.format(date);
			mbo.setValue("glzd", time, 11l);
			//化工园
			//if ("CRPHGY".equals(orgid)) {
				mbo.setValue("siteid", siteid);
				mbo.setValue("orgid", orgid);

//				String siteid = mbo.getString("siteid");// 等同于bean类里面的这个String
														// siteid =
														// mbo.getString("siteid");
				String COALTJZBNUM = mbo.getString("GLZD");
				MboSetRemote gykgdztempSet = mbo.getMboSet("#DUTYNOTE", "DUTYNOTE",
						"siteid='" + siteid + "' and istemplate=1");
				gykgdztempSet.setOrderBy("SORTNUM desc");// 子表的 排序字段

				int count = gykgdztempSet.count();
				MboSetRemote gykgdz_detail_bynumset_1 = mbo.getMboSet("DUTYNOTE");// 得到子表结果集
				if (!gykgdztempSet.isEmpty()) {// 如果模版数据不为空

					MboRemote gykgdz_detail;
					for (int i = 0; i < count; i++) {

						gykgdz_detail = gykgdz_detail_bynumset_1.add();
						gykgdz_detail.setValue("GLZD", COALTJZBNUM, 2L);// 主键
						gykgdz_detail.setValue("BM", gykgdztempSet.getMbo(i)
								.getString("BM"),// 参数名称
								2L);
						gykgdz_detail.setValue("ZY", gykgdztempSet.getMbo(i)
								.getString("ZY"),// 参数名称
								2L);
						gykgdz_detail.setValue("sortnum", gykgdztempSet.getMbo(i)// 排序字段
								.getString("sortnum"), 2L);
					}
				}
				gykgdztempSet.close();
				gykgdz_detail_bynumset_1.save();
				gykgdz_detail_bynumset_1.close();
				zbjls.save();
	

		
//			} else {
//				//板桥电厂
//				if ("CRPBQ".equals(orgid)) {
//					mbo.setValue("siteid", siteid);
//					mbo.setValue("orgid", orgid);
//				}
//			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化参数
	 */
	public CrontaskParamInfo[] getParameters() throws MXException, RemoteException
	  {
	    try {
	      String[] names = { "orgid","siteid" };
	      String[] defs = { "orgid","siteid" };
	      String[][] descriptions = { { "cronprepr", "" },{"cronprepr2",""} };
	      CrontaskParamInfo[] ret = new CrontaskParamInfo[names.length];
	      for (int i = 0; i < names.length; i++) {
	        ret[i] = new CrontaskParamInfo();
	        ret[i].setName(names[i]);
	        ret[i].setDefault(defs[i]);
	        ret[i].setDescription(descriptions[i][0], descriptions[i][1]);
	      }
	      return ret;
	    } catch (Exception e) {
	      if (getCronTaskLogger().isErrorEnabled())
	        getCronTaskLogger().error(e);
	    }
	    return null;
	  }
}