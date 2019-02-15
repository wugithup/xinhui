package com.shuto.mam.webclient.beans.stpi.pi_tcfg_xj;

import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.shuto.mam.app.stpi.Toolkit;
import com.shuto.mam.crontask.stpi.dbutils.MaximoUtils;
import com.shuto.mam.crontask.stpi.download.DownloadCron;
import com.shuto.mam.crontask.stpi.upload.UploadCron;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.WebClientEvent;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年6月1日
 * @since:可不提交，只是输出被注释掉了
 */
public class Pi_tcfg_xjAppBean extends AppBean {
	public static final int D_QUARTER = 1, D_HOUR = 4 * D_QUARTER, D_DAY = 24 * D_HOUR, D_WEEK = 7 * D_DAY;// 单位常量,刻钟,小时,天,周,月
	public static final int D_WEEK_ = -1, D_MONTH_ = -2;

	public Pi_tcfg_xjAppBean() {
		super();
	}

	@Override
	public int INSERT() throws MXException, RemoteException {
		int insert = super.INSERT();
		MboRemote mainMbo = app.getAppBean().getMbo();
		String siteid = mainMbo.getString("siteid");
		int number = 1;
		MboSetRemote resultSet = getMbo().getMboSet("$resultSet", "ST_PI_TASKCFG",
				"no = (select max(no) from ST_PI_TASKCFG where type = '巡检'  and siteid = '" + siteid + "')");
		if (!resultSet.isEmpty()) {
			number = Integer.valueOf(resultSet.getMbo(0).getString("no").substring(3));
			++number;
		}
		DecimalFormat decimalFormat = new DecimalFormat("000");
		String tmp = decimalFormat.format(number);
		String strNo = "XPZ" + tmp;
		getMbo().setValue("no", strNo, 11L);
		resultSet.close();

		MboSetRemote dataformatSet = mainMbo.getMboSet("DATAFORMAT_XJ");
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
		super.SAVE();
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
		// 判断每日周期是否为空
		MboSetRemote daycycleSet = mbo.getMboSet("ST_PI_DAYCYCLE");
		if (daycycleSet.isEmpty()) {
			throw new MXApplicationException("PI_TCFG", "DAYCYCLENULL");
		}

		return 1;
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
			MboSetRemote PI_TASKDATETIME = mbo.getMboSet("ST_PI_TASKDATETIME");
			if (!PI_TASKDATETIME.isEmpty()) {
				PI_TASKDATETIME.deleteAll();
			}
			int cycle = mbo.getInt("CYCLE");
			Date begin_time = mbo.getDate("BEGIN_TIME");
			Date end_time = mbo.getDate("END_TIME");
			SimpleDateFormat endd = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat endt = new SimpleDateFormat("HH:mm:ss");
			Calendar ca = Calendar.getInstance();
			ca.setTime(begin_time);
			String sql = "insert into st_pi_taskdatetime(st_pi_taskdatetimeid,BEGIN_TIME,END_TIME,TYPE,st_pi_taskcfgid,STATUS,Hasld,SITEID ,ORGID) "
					+ " values(st_pi_taskdatetimeidseq.nextval,to_date(?,'yyyy-mm-dd hh24:mi:ss'),to_date(?,'yyyy-mm-dd hh24:mi:ss'),'巡检',?,'未生成任务',0,?,?)";
			MaximoUtils maximoutils = new MaximoUtils();
			PreparedStatement pstmt = null;
			long taskcfgid = mbo.getUniqueIDValue();
			String siteid = mbo.getString("siteid");
			String orgid = mbo.getString("orgid");

			MboSetRemote daycycleSet = mbo.getMboSet("ST_PI_DAYCYCLE");
			daycycleSet.setOrderBy("BEGIN_TIME");
			daycycleSet.reset();
			int count = daycycleSet.count();
			try {
				maximoutils.getMaximoConn();
				pstmt = maximoutils.LoggableStatement(sql);
				Date sdate = ca.getTime();
				Date edate = null;
				while (sdate.compareTo(end_time) <= 0) {
					edate = sdate;
					for (int j = 0; j < count; j++) {
						MboRemote daycycleMbo = daycycleSet.getMbo(j);
						Date startTime = daycycleMbo.getDate("BEGIN_TIME");
						Date endTime = daycycleMbo.getDate("END_TIME");
						if (startTime.compareTo(endTime) >= 0) {
							edate = Toolkit.getNextDayTime(edate, 1);
						}
						String stime = endd.format(sdate) + " " + endt.format(startTime);
						String etime = endd.format(edate) + " " + endt.format(endTime);
						int taskCount = daycycleMbo.getInt("count");
						for (int k = 0; k < taskCount; k++) {
							List<Object> params = new ArrayList<Object>();
							params.add(stime);
							params.add(etime);
							params.add(Integer.parseInt(String.valueOf(taskcfgid)));
							params.add(siteid);
							params.add(orgid);
							maximoutils.updateBatchByPreparedStatement(pstmt, params);
						}
					}
					ca.add(Calendar.DATE, cycle);
					sdate = ca.getTime();
				}
				pstmt.executeBatch();
				maximoutils.commit();
				pstmt.close();
				flag = true;
			} catch (SQLException e) {
				maximoutils.rollback();
				e.printStackTrace();
			} finally {
				maximoutils.setAutoCommit(true);
			}
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
		/*System.out.println("=====同步到maximo测试========");
		UploadCron uploadCron = new UploadCron();
		uploadCron.cronAction();
		System.out.println("-----------上传结束-----------------");*/
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
		// 判断配置是否已启用
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
		mbo.setValue("IS_ACTIVITY", 1, 11L);
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
		String iDaySql = "insert into ST_PI_DAYCYCLE (ST_PI_DAYCYCLEID, DESCRIPTION, ORGID, SITEID, HASLD, ST_PI_TASKCFGID, BEGIN_TIME, END_TIME, COUNT) "
				+ "select St_Pi_Daycycleidseq.Nextval, DESCRIPTION, ORGID, SITEID, HASLD, ?, BEGIN_TIME, END_TIME, COUNT from ST_PI_DAYCYCLE where ST_PI_DAYCYCLEID = ?";
		MaximoUtils maximoUtils = new MaximoUtils();
		maximoUtils.getMaximoConn();
		List<Object> sVersionParams = new ArrayList<Object>();
		sVersionParams.add(no);
		PreparedStatement areaPstmt = null;
		PreparedStatement itemPstmt = null;
		PreparedStatement dayPstmt = null;
		try {
			int version = Integer
					.valueOf(String.valueOf(maximoUtils.findSimpleResult(sVersion, sVersionParams).get("VERSION"))) + 1;
			areaPstmt = maximoUtils.LoggableStatement(iAreaSql);
			itemPstmt = maximoUtils.LoggableStatement(iItemSql);
			dayPstmt = maximoUtils.LoggableStatement(iDaySql);
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

			// 得到巡检周期子表
			MboSetRemote daycycleSet = mainMbo.getMboSet("ST_PI_DAYCYCLE");
			if (!daycycleSet.isEmpty()) {
				// 插入人员表
				for (int n = 0; n < daycycleSet.count(); n++) {
					MboRemote daycycleMbo = daycycleSet.getMbo(n);
					long st_pi_daycycleid = daycycleMbo.getUniqueIDValue();
					List<Object> iDayParams = new ArrayList<Object>();
					iDayParams.add(st_pi_taskcfgid);
					iDayParams.add(st_pi_daycycleid);
					maximoUtils.updateBatchByPreparedStatement(dayPstmt, iDayParams);
				}
			}

			areaPstmt.executeBatch();
			itemPstmt.executeBatch();
			dayPstmt.executeBatch();
			maximoUtils.commit();
			flag = true;
		} catch (SQLException e) {
			maximoUtils.rollback();
			e.printStackTrace();
		} finally {
			maximoUtils.closePstmt(areaPstmt);
			maximoUtils.closePstmt(itemPstmt);
			maximoUtils.closePstmt(dayPstmt);
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
