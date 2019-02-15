package com.shuto.mam.crontask;

import java.sql.Connection;

import psdi.server.SimpleCronTask;


/**
 * @Title: CronTaskDemo.java
 * @Description: TODO
 * @author: lull lull@shuto.cn
 * @date: 2017年3月8日 下午1:53:05
 * @version: V1.0.0
 */
// 定时任务类
public class XXXCronTask extends SimpleCronTask {

	private Connection conn;

	/**
	 * <p>
	 * Title: cronAction
	 * <p>
	 * Description:
	 * 
	 * @see psdi.server.SimpleCronTask#cronAction()
	 */
	@Override
	public void cronAction() {
		/*
		try {
			conn = MXServer.getMXServer().getDBManager().getSequenceConnection();
		} catch (RemoteException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}*/
	}

}
