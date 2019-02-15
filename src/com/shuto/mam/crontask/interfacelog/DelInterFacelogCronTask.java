package com.shuto.mam.crontask.interfacelog;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import psdi.server.MXServer;
import psdi.server.SimpleCronTask;

/**
    *  文件名： com.shuto.mam.crontask.interfacelog.DelInterFacelogCronTask.java
    *  说明：interfacelog 定期删除 每月15号4点执行  删除一周前数据
    *  创建日期： 2017年9月6日
    *  修改历史 :   
    *     1. [2017年9月6日下午1:58:43] 创建文件 by lull lull@shuto.cn
   */
public class DelInterFacelogCronTask extends SimpleCronTask {
	//com.shuto.mam.crontask.interfacelog.DelInterFacelogCronTask

	/*
	 * (non-Javadoc)
	 * 
	 * @see psdi.server.SimpleCronTask#cronAction()
	 */
	@Override
	public void cronAction() {
		Connection conn = null;
		Statement stm = null;
		String sql = " delete interfacelog where synchdate < sysdate-7  ";
		try {
			conn = MXServer.getMXServer().getDBManager().getSequenceConnection();
			stm = conn.createStatement();
			int count = stm.executeUpdate(sql);
			System.out.println("count = " + count);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stm != null) {
				try {
					stm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
