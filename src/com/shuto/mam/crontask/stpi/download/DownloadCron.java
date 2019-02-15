package com.shuto.mam.crontask.stpi.download;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.ibm.icu.text.SimpleDateFormat;
import com.shuto.mam.crontask.stpi.StpiSimpleCronTask;
import com.shuto.mam.crontask.stpi.dbutils.MaximoUtils;
import com.shuto.mam.crontask.stpi.interfacelog.Interfacelog;
import com.shuto.mam.crontask.stpi.upload.PiUpload;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年5月11日
 * @since:巡点检定时任务推送
 */
public class DownloadCron extends StpiSimpleCronTask {
	public void downloadTask(String orgid,String siteid){
		// 获取巡点检任务结果集
		MboSetRemote taskSet = null;
		// 区域结果集
		MboSetRemote areaSet = null;
		// 项目结果集
		MboSetRemote itemSet = null;
		// 人员结果集
		MboSetRemote userSet = null;
		try {
			MaximoUtils maximoUtils = new MaximoUtils();
			maximoUtils.getMaximoConn();

			String site = maximoUtils.getSite(siteid);

			MXServer server = MXServer.getMXServer();
			taskSet = server.getMboSet("ST_PI_TASK", server.getSystemUserInfo());
			// 得到相应地点点检的记录
			taskSet.setWhere("orgid = '" + orgid + "' and siteid = '" + siteid + "' and status = '未下载任务'");
			if (!taskSet.isEmpty()) {
				// 日志主表信息
				Map<String, String> logMap = new HashMap<String, String>();
				// 日志子表信息列表
				List<Map<String, String>> logList = new ArrayList<Map<String, String>>();
				// 日志子表信息
				Map<String, String> logdetailMap = new HashMap<String, String>();
				int counts = 0;
				int scounts = 0;
				int ecounts = 0;
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String starttime = dateFormat.format(new Date());

				PiDownload piDownload = null;
				counts = taskSet.count();
				for (int i = 0; i < counts; i++) {
					MboRemote taskMbo = taskSet.getMbo(i);
					areaSet = taskMbo.getMboSet("ST_PI_TASK_AREA");
					areaSet.setOrderBy("no");
					/*if("HRHB000".equals(siteid)){
						areaSet.setOrderBy("no");
					}else{
						areaSet.setOrderBy("seq");
					}*/
					itemSet = taskMbo.getMboSet("ST_PI_TASK_ITEM");
					itemSet.setOrderBy("st_pi_task_areaid,deviceseq,seq");
					userSet = taskMbo.getMboSet("ST_PI_TASK_USER");
					//接口相关
					MboRemote dataformatMbo = taskMbo.getMboSet("ST_PI_DATAFORMAT").getMbo(0);
					String interfaceclass = dataformatMbo.getString("INTERFACECLASS");
					piDownload = (PiDownload) Class.forName(interfaceclass).newInstance();
					logdetailMap = piDownload.download(taskMbo, areaSet, itemSet, userSet);
					if (logdetailMap == null) {
						taskMbo.setValue("status", "未上传结果", 11L);
					} else {
						ecounts++;
						logList.add(logdetailMap);
					}
					//释放数据集
					if (areaSet != null) {
						areaSet.close();
					}
					if (itemSet != null) {
						itemSet.close();
					}
					if (userSet != null) {
						userSet.close();
					}
				}
				System.out.println("任务下载结果：共 " + counts + " 条任务，成功 " + scounts + " 条，失败 " + ecounts + " 条");
				taskSet.save();

				String endtime = dateFormat.format(new Date());
				String status = "正常";
				if (ecounts > 0) {
					status = "异常";
				}
				logMap.put("DESCRIPTION", site + " 任务数据下载");
				logMap.put("COUNTS", String.valueOf(counts));
				logMap.put("SCOUNTS", String.valueOf(scounts));
				logMap.put("ECOUNTS", String.valueOf(ecounts));
				logMap.put("STARTTIME", starttime);
				logMap.put("ENDTIME", endtime);
				logMap.put("LOGTYPE", "任务数据下载");
				logMap.put("STATUS", status);
				logMap.put("ORGID", orgid);
				logMap.put("SITEID", siteid);

				Interfacelog interfacelog = new Interfacelog();
				interfacelog.setInterfacelog(logMap, logList);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (taskSet != null) {
					taskSet.close();
				}
				if (areaSet != null) {
					areaSet.close();
				}
				if (itemSet != null) {
					itemSet.close();
				}
				if (userSet != null) {
					userSet.close();
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	public void cronAction() {
		try {
			downloadTask(getParamAsString("orgid"), getParamAsString("siteid"));
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 手动触发
	 * @param mbo
	 * @param orgid
	 * @param siteid
	 * @param dataformatno
	 */
	public void autoAction(MboRemote mbo,String orgid,String siteid) {
		downloadTask(orgid, siteid);
	}
}
