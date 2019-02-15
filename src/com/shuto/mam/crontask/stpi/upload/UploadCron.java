package com.shuto.mam.crontask.stpi.upload;

import java.rmi.RemoteException;

import com.shuto.mam.crontask.stpi.StpiSimpleCronTask;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;

public class UploadCron extends StpiSimpleCronTask{

	public void cronAction() {
		try {
			String orgid = this.getParamAsString("orgid");
			String siteid = this.getParamAsString("siteid");
			MXServer server = MXServer.getMXServer();
			// 获取点检数据源表
			MboSetRemote dfSet = server.getMboSet("ST_PI_DATAFORMAT",server.getSystemUserInfo());
			//得到有任务的数据源
			dfSet.setWhere("no in (select c.dataformatno from ST_PI_TASK t, ST_PI_TASKCFG c where t.st_pi_taskcfgid = c.st_pi_taskcfgid and t.orgid = '"+orgid+"' and t.siteid = '"+siteid+"' and t.status = '未上传结果')");
			if(!dfSet.isEmpty()){
				PiUpload piUpload = null;
				for(int i=0;i<dfSet.count();i++){
					MboRemote dfMbo = dfSet.getMbo(i);
					String interfaceclass = dfMbo.getString("UPLOADINTERFACECLASS");
					piUpload = (PiUpload) Class.forName(interfaceclass).newInstance();
					int[] result = piUpload.upload(dfMbo.getMboSet("ST_PI_DBSOURCE").getMbo(0));
					System.out.println("第"+ i+1 +"数据源上传结果：共 "+result[0] +" 条任务，失败 "+result[1]+" 条");
				}
			}
			dfSet.close();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
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
	public void autoAction(MboRemote mbo,String orgid,String siteid,String dataformatno) {
		try {
			// 获取点检数据源表
			MboSetRemote dfSet = null;
			//得到有任务的数据源
			if (dataformatno != null && !"".equals(dataformatno)) {
				dfSet = mbo.getMboSet("$st_pi_dataformat","ST_PI_DATAFORMAT","no in (select c.dataformatno from ST_PI_TASK t, ST_PI_TASKCFG c where t.st_pi_taskcfgid = c.st_pi_taskcfgid and t.orgid = '"+orgid+"' and t.siteid = '"+siteid+"' and t.status = '未上传结果') and no='"+dataformatno+"'");
			} else {
				dfSet = mbo.getMboSet("$st_pi_dataformat","ST_PI_DATAFORMAT","no in (select c.dataformatno from ST_PI_TASK t, ST_PI_TASKCFG c where t.st_pi_taskcfgid = c.st_pi_taskcfgid and t.orgid = '"+orgid+"' and t.siteid = '"+siteid+"' and t.status = '未上传结果')");
			}
			
			if(!dfSet.isEmpty()){
				PiUpload piUpload = null;
				for(int i=0;i<dfSet.count();i++){
					MboRemote dfMbo = dfSet.getMbo(i);
					String interfaceclass = dfMbo.getString("UPLOADINTERFACECLASS");
					piUpload = (PiUpload) Class.forName(interfaceclass).newInstance();
					int[] result = piUpload.upload(dfMbo.getMboSet("ST_PI_DBSOURCE").getMbo(0));
					System.out.println("第"+ i+1 +"数据源上传结果：共 "+result[0] +" 条任务，失败 "+result[1]+" 条");
				}
			}
			dfSet.close();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
