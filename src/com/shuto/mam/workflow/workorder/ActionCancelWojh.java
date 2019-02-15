/**
 * 工单流程类，取消工单领料计划
 */
package com.shuto.mam.workflow.workorder;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Map;

import com.shuto.mam.webservice.ebs.client.ErpUtil;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

/**
 * 单领料单流程类，取消工单领料计划
 * @author ITROBOT
 *
 */
public class ActionCancelWojh implements ActionCustomClass  {
	public void applyCustomAction(MboRemote mboremote, Object[] aobj) throws MXException, RemoteException {
		MboRemote woMbo = mboremote;
		//记录原状态,用于接口调用失败时,还原单据状态
		String oldstatus = woMbo.getString("status");
		MboSetRemote woset = woMbo.getMboSet("$workorder1", "workorder","wonum=:wonum");
		woset.save();
		woset.getMbo(0).setValue("status","已取消",MboConstants.NOACCESSCHECK);
		woset.getMbo(0).setValue("cancel_person", woMbo.getUserInfo().getPersonId(),MboConstants.NOACCESSCHECK);
		woset.getMbo(0).setValue("cancel_date", psdi.server.MXServer.getMXServer().getDate(),MboConstants.NOACCESSCHECK);
		woset.getMbo(0).setValue("cancel_reason","取消领料单",MboConstants.NOACCESSCHECK);
		woset.save(2L);
		woset.close();
		MboSetRemote maters = woMbo.getMboSet("SHOWWOMURITEM");
		if (!maters.isEmpty()) {
			ErpUtil erputil = new ErpUtil();
			Map<String,Map<String,String>> returnmap = null;
			Map<String, String> jhret = null;
			Map<String, String> llret = null;
			try {
				returnmap = erputil.cancelWo(woMbo);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				boolean success = true;
				String msg = null;
				if (returnmap != null) {
					jhret = returnmap.get("JHRET");
					llret = returnmap.get("LLRET");
					if (jhret != null) {
						if (!"1".equals(jhret.get("resultcode"))) {
							success = false;
							msg = "计划返回信息：" + jhret.get("resultmsg");
						} else {
							if (llret != null) {
								if (!"1".equals(llret.get("resultcode"))) {
									success = false;
									msg = "领料返回信息：" + llret.get("resultmsg");
								}
							} else {
								success = false;
								msg = "领料单上传失败";
							}
						}
					} else {
						success = false;
						msg = "计划上传失败";
					}
				} else {
					success = false;
					msg = "计划上传失败";
				}
//				if (!success) {
//					woset = woMbo.getMboSet("$workorder2", "workorder",
//							"wonum=:wonum");
//					woset.getMbo(0).setValue("status", oldstatus,
//							MboConstants.NOACCESSCHECK);
//					woset.save(2L);
//					woset.close();
//				}
			}
		}
	}
}