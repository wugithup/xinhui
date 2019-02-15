package com.shuto.mam.webclient.beans.stpi.pi_tcfg;

import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.shuto.mam.app.stpi.Toolkit;
import com.shuto.mam.crontask.stpi.dbutils.MaximoUtils;
import com.shuto.mam.crontask.stpi.download.DownloadCron;
import com.shuto.mam.crontask.stpi.pitask.CreateDjTaskCron;
import com.shuto.mam.crontask.stpi.upload.UploadCron;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.WebClientEvent;

public class Pi_tcfgAppBean extends AppBean {

	public Pi_tcfgAppBean() {
		super();
	}

	@Override
	public int INSERT() throws MXException, RemoteException {
		int insert = super.INSERT();
		MboRemote mainMbo = app.getAppBean().getMbo();
		String siteid = mainMbo.getString("siteid");
		int number = 1;
		MboSetRemote resultSet = getMbo().getMboSet("$resultSet", "ST_PI_TASKCFG",
				"no = (select max(no) from ST_PI_TASKCFG where type = '点检'  and siteid = '" + siteid + "')");
		if (!resultSet.isEmpty()) {
			number = Integer.valueOf(resultSet.getMbo(0).getString("no").substring(3));
			++number;
		}
		DecimalFormat decimalFormat = new DecimalFormat("000");
		String tmp = decimalFormat.format(number);
		String strNo = "DPZ" + tmp;
		getMbo().setValue("no", strNo, 11L);
		resultSet.close();

		MboSetRemote dataformatSet = mainMbo.getMboSet("DATAFORMAT_DJ");
		if (dataformatSet.count() == 1) {
			String DATAFORMATNO = dataformatSet.getMbo(0).getString("NO");
			mainMbo.setValue("DATAFORMATNO", DATAFORMATNO, 11L);
		}
		return insert;
	}

	@Override
	public int SAVE() throws MXException, RemoteException {
		/*if (!(hasAuth())) {
			throw new MXApplicationException("hasnoAuth", "hasnoAuth");
		}*/
		MboRemote mbo = app.getAppBean().getMbo();
		MboSetRemote areaSet = mbo.getMboSet("ST_PI_TASKCFG_AREA");
		if (!areaSet.isEmpty()) {
			for (int i = 0; i < areaSet.count(); i++) {
				MboRemote areaMbo = areaSet.getMbo(i);
				String areaNo = areaMbo.getString("ST_PI_AREA.NO");
				Object[] params = { areaNo };
				if (areaMbo.getMboSet("ST_PI_TASKCFG_ITEM").isEmpty()) {
					throw new MXApplicationException("PI_TCFG", "NOITEM", params);
				}
			}
		} else {
			throw new MXApplicationException("PI_TCFG", "NOAREA");
		}
		DownloadCron DownloadCron = new DownloadCron();
		// DownloadCron.cronAction();
		UploadCron UploadCron = new UploadCron();
		// UploadCron.cronAction();
		CreateDjTaskCron CreateDjTaskCron = new CreateDjTaskCron();
		// CreateDjTaskCron.cronAction();
		return super.SAVE();
	}

	public boolean hasAuth() throws MXException, RemoteException {
		String createby = getMbo().getString("CREATEBY");
		String s1 = getMbo().getUserInfo().getPersonId();
		if (s1.equalsIgnoreCase("maxadmin"))
			return true;
		if (s1.equalsIgnoreCase(createby))
			return true;
		return false;
	}

	/**
	 * 启用配置
	 * 
	 * @throws MXException
	 * @throws RemoteException
	 */
	public void ENABLE() throws RemoteException, MXException {

		SAVE();
		MboRemote mbo = app.getAppBean().getMbo();
		boolean is_enable = mbo.getBoolean("IS_ENABLE");
		boolean flag = false;
		if (!is_enable) {
			String beginTime = mbo.getString("BEGIN_TIME");
			String endTime = mbo.getString("END_TIME");

			MboSetRemote PI_TASKDATETIME = mbo.getMboSet("ST_PI_TASKDATETIME");
			if (!PI_TASKDATETIME.isEmpty()) {
				PI_TASKDATETIME.deleteAll();
			}
			String cycle_unit = mbo.getString("CYCLE_UNIT");
			Date[] arg = null;
			int cycle = mbo.getInt("CYCLE");
			if ("天".equals(cycle_unit)) {
				arg = WeekDayUtil.getDates(beginTime, endTime, cycle);
			} else {
				cycle = 1;
				String weekDays = "";
				if ("周一".equals(cycle_unit)) {
					weekDays = "星期一";
				} else if ("周二".equals(cycle_unit)) {
					weekDays = "星期二";
				} else if ("周三".equals(cycle_unit)) {
					weekDays = "星期三";
				} else if ("周四".equals(cycle_unit)) {
					weekDays = "星期四";
				} else if ("周五".equals(cycle_unit)) {
					weekDays = "星期五";
				} else if ("周六".equals(cycle_unit)) {
					weekDays = "星期六";
				} else if ("周日".equals(cycle_unit)) {
					weekDays = "星期日";
				} else {
					//weekDays = "星期一|星期二|星期三|星期四|星期五|星期六|星期日";
					weekDays = "星期一|星期二|星期三|星期四|星期五";
				}
				arg = WeekDayUtil.getDates(beginTime, endTime, weekDays);
			}
			// 插入PI_TASKDATETIME
			flag = setTaskdatetime(arg, cycle);
			if (flag) {
				mbo.setValue("IS_ENABLE", true, 11L);
				this.app.getAppBean().save();
			} else {
				throw new MXApplicationException("PI_TCFG", "TASKTIMEERROR");
			}
		}

	}

	/**
	 * 禁用配置
	 * 
	 * @throws MXException
	 * @throws RemoteException
	 */
	public void DISABLE() throws RemoteException, MXException {

		
		/*
		System.out.println("=============");
		DownloadCron downloadCron = new DownloadCron();
		
		downloadCron.downloadTask("CRPH500CC", "HRHB000");*/
		
		
		MboRemote mbo = app.getAppBean().getMbo();
		// 判断配置是否活动，需先取消活动
		boolean is_activity = mbo.getBoolean("IS_ACTIVITY");
		if (is_activity) {
			throw new MXApplicationException("PI_TCFG", "ACTIVATE");
		}
		// 禁用配置后删除任务时间明细
		MboSetRemote taskdatetiemSet = mbo.getMboSet("st_pi_taskdatetime");
		if (!taskdatetiemSet.isEmpty()) {
			taskdatetiemSet.deleteAll();
		}
		// 设置为未启用
		mbo.setValue("IS_ENABLE", false, 11L);
		this.app.getAppBean().save();

	}

	/**
	 * 激活过程
	 * 
	 * @throws MXException
	 * @throws RemoteException
	 */
	public void ACTIVATE() throws RemoteException, MXException {
		MboRemote mbo = app.getAppBean().getMbo();
		// 判断配置是否已启用 ST_PI_TASKCFG.IS_ACTIVITY

		boolean is_enable = mbo.getBoolean("IS_ENABLE");
		if (!is_enable) {
			ENABLE();
		} else {
			SAVE();
		}
		String no = mbo.getString("NO");
		// 将其它版本取消活动
		MboSetRemote otherSet = mbo.getMboSet("$ACTIVECFG", "ST_PI_TASKCFG", "IS_ACTIVITY = 1 and NO='" + no + "'");
		if (!otherSet.isEmpty()) {
			for (int i = 0; i < otherSet.count(); i++) {
				MboRemote otherMbo = otherSet.getMbo(i);
				otherMbo.setValue("IS_ACTIVITY", false, 11L);
			}
		}
		otherSet.close();
		// 设置为活动
		mbo=app.getAppBean().getMbo();
		mbo.setValue("IS_ACTIVITY", true, 11L);
		mbo.setValue("PERMITDATE", new Date(), 11L);
		this.app.getAppBean().save();
		

	}

	/**
	 * 取消激活
	 * 
	 * @throws MXException
	 * @throws RemoteException
	 */
	public void DEACTIVATE() throws RemoteException, MXException {
		getMbo().setValue("IS_ACTIVITY", false, 11L);
		this.app.getAppBean().save();
	}

	/**
	 * 插入点检任务时间表
	 * 
	 * @param arg
	 * @param cycle
	 *            周期
	 * @return
	 */
	private boolean setTaskdatetime(Date[] arg, int cycle) {
		boolean flag = false;
		MaximoUtils maximoutils = new MaximoUtils();
		try {
			if (arg != null && arg.length > 0) {
				maximoutils.getMaximoConn();
				MboRemote mbo = app.getAppBean().getMbo();
				long taskcfgid = mbo.getUniqueIDValue();
				String siteid = mbo.getString("siteid");
				String orgid = mbo.getString("orgid");
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String sql = "insert into st_pi_taskdatetime(st_pi_taskdatetimeid,BEGIN_TIME,END_TIME,TYPE,st_pi_taskcfgid,STATUS,Hasld,SITEID ,ORGID) "
						+ " values(st_pi_taskdatetimeidseq.nextval,to_date(?,'yyyy-mm-dd hh24:mi:ss'),to_date(?,'yyyy-mm-dd hh24:mi:ss'),'点检',?,'未生成任务',0,?,?)";
				PreparedStatement pstmt = maximoutils.LoggableStatement(sql);
				for (int i = 0; i < arg.length; i++) {
					List<Object> params = new ArrayList<Object>();
					Date date = arg[i];
					String beginTime = dateFormat.format(date);
					String endtime = dateFormat.format(Toolkit.getNextDayTime(date, cycle));
					params.add(beginTime);
					params.add(endtime);
					params.add(Integer.parseInt(String.valueOf(taskcfgid)));
					params.add(siteid);
					params.add(orgid);
					maximoutils.updateBatchByPreparedStatement(pstmt, params);
				}
				pstmt.executeBatch();
				maximoutils.commit();
				pstmt.close();
				flag = true;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			maximoutils.rollback();
			e.printStackTrace();
		} finally {
			maximoutils.setAutoCommit(true);
		}
		return flag;
	}

	/**
	 * 创建修订版本
	 * 
	 * @return
	 * @throws MXException
	 * @throws RemoteException
	 */
	public int UPGRADE() throws MXException, RemoteException {
		boolean flag = false;
		// 提示是否进行升版
		WebClientEvent event = clientSession.getCurrentEvent();
		int msgRet = event.getMessageReturn();
		if (msgRet < 0) {
			// 弹出提示窗口问是否继续
			throw new MXApplicationYesNoCancelException("BMXAA8382E", "PI_TCFG", "UPGRADE");
		}
		if (msgRet != 8) {
			return 0;
		}

		MboRemote mainMbo = app.getAppBean().getMbo();
		long oldtaskcfgid = mainMbo.getUniqueIDValue();
		String no = mainMbo.getString("no");
		String iTaskSql = "insert into st_pi_taskcfg (ST_PI_TASKCFGID, DESCRIPTION, ORGID, SITEID, HASLD, NO, BEGIN_TIME, END_TIME, PROFESSIONAL, CYCLE, CYCLE_UNIT, TYPE, ST_PI_POSTID, IS_ACTIVITY, ST_PI_TASKCFGNUM, VERSION, CYCLE_TYPE, CYCLE_SHIFT, CREATEDATE, CREATEBY, PERMITDATE, INTERVAL, POSTNO, DATAFORMATNO, CYCLE_UNIT_XJ, IS_ENABLE, ST_PI_DUTYINFONUM) "
				+ "select ?, DESCRIPTION, ORGID, SITEID, HASLD, NO, BEGIN_TIME, END_TIME, PROFESSIONAL, CYCLE, CYCLE_UNIT, TYPE, ST_PI_POSTID, 0, ?, ?, CYCLE_TYPE, CYCLE_SHIFT, SYSDATE, CREATEBY, PERMITDATE, INTERVAL, POSTNO, DATAFORMATNO, CYCLE_UNIT_XJ, 0, ST_PI_DUTYINFONUM from st_pi_taskcfg where st_pi_taskcfgid = ?";
		String iAreaSql = "insert into st_pi_taskcfg_area (ST_PI_TASKCFG_AREAID, DESCRIPTION, ORGID, SITEID, HASLD, ST_PI_AREAID, ST_PI_TASKCFGID, SEQ, TYPE) "
				+ "select ?, DESCRIPTION, ORGID, SITEID, HASLD, ST_PI_AREAID, ?, SEQ, TYPE from ST_PI_TASKCFG_AREA where ST_PI_TASKCFG_AREAID = ?";
		String iItemSql = "insert into ST_PI_TASKCFG_ITEM (ST_PI_TASKCFG_ITEMID, DESCRIPTION, ORGID, SITEID, HASLD, ST_PI_TASKCFGID, SEQ, TYPE, ST_PI_ITEMID, ST_PI_TASKCFG_AREAID) "
				+ "select St_Pi_Taskcfg_Itemidseq.Nextval, DESCRIPTION, ORGID, SITEID, HASLD, ?, SEQ, TYPE, ST_PI_ITEMID, ? from ST_PI_TASKCFG_ITEM where ST_PI_TASKCFG_ITEMID = ?";
		String sVersion = "select max(version) as version from st_pi_taskcfg where no = ?";
		MaximoUtils maximoUtils = new MaximoUtils();
		maximoUtils.getMaximoConn();
		List<Object> sVersionParams = new ArrayList<Object>();
		sVersionParams.add(no);
		PreparedStatement areaPstmt = null;
		PreparedStatement itemPstmt = null;
		try {
			int version = Integer
					.valueOf(String.valueOf(maximoUtils.findSimpleResult(sVersion, sVersionParams).get("VERSION"))) + 1;
			areaPstmt = maximoUtils.LoggableStatement(iAreaSql);
			itemPstmt = maximoUtils.LoggableStatement(iItemSql);
			// 得到任务配置表区域子表
			MboSetRemote cfgareaSet = mainMbo.getMboSet("ST_PI_TASKCFG_AREA");

			// 得到任务ID
			long st_pi_taskcfgid = maximoUtils.getNextSequence("st_pi_taskcfgidseq");
			// 得到AUTOKEY
			String st_pi_taskcfgnum = maximoUtils.getNextNumber("ST_PI_TASKCFGNUM");
			// 插入任务表
			List<Object> iTaskParams = new ArrayList<Object>();
			iTaskParams.add(st_pi_taskcfgid);
			iTaskParams.add(st_pi_taskcfgnum);
			iTaskParams.add(version);
			iTaskParams.add(oldtaskcfgid);
			maximoUtils.updateByPreparedStatement(iTaskSql, iTaskParams);

			if (!cfgareaSet.isEmpty()) {
				// 插入区域表
				for (int k = 0; k < cfgareaSet.count(); k++) {
					MboRemote cfgareaMbo = cfgareaSet.getMbo(k);
					long oldtaskcfg_areaid = cfgareaMbo.getUniqueIDValue();
					long st_pi_taskcfg_areaid = maximoUtils.getNextSequence("st_pi_taskcfg_areaidseq");
					List<Object> iAreaParams = new ArrayList<Object>();
					iAreaParams.add(st_pi_taskcfg_areaid);
					iAreaParams.add(st_pi_taskcfgid);
					iAreaParams.add(oldtaskcfg_areaid);
					maximoUtils.updateBatchByPreparedStatement(areaPstmt, iAreaParams);

					// 得到配置中项目信息
					MboSetRemote areaitemSet = cfgareaMbo.getMboSet("ST_PI_TASKCFG_ITEM");
					if (!areaitemSet.isEmpty()) {
						for (int m = 0; m < areaitemSet.count(); m++) {
							MboRemote cfgitemMbo = areaitemSet.getMbo(m);
							long st_pi_taskcfg_itemid = cfgitemMbo.getUniqueIDValue();
							List<Object> iItemParams = new ArrayList<Object>();
							iItemParams.add(st_pi_taskcfgid);
							iItemParams.add(st_pi_taskcfg_areaid);
							iItemParams.add(st_pi_taskcfg_itemid);
							maximoUtils.updateBatchByPreparedStatement(itemPstmt, iItemParams);
						}
					}
				}
			}

			areaPstmt.executeBatch();
			itemPstmt.executeBatch();
			maximoUtils.commit();
			flag = true;
		} catch (SQLException e) {
			maximoUtils.rollback();
			e.printStackTrace();
		} finally {
			maximoUtils.closePstmt(areaPstmt);
			maximoUtils.closePstmt(itemPstmt);
			maximoUtils.setAutoCommit(true);
			maximoUtils.closePs();
		}
		;

		if (flag) {
			throw new MXApplicationException("PI_TCFG", "UPGRADEOK");
		}
		return 1;
	}
}
