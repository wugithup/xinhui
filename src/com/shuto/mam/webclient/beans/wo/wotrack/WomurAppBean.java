/**
 *
 */
package com.shuto.mam.webclient.beans.wo.wotrack;

import com.shuto.mam.webclient.beans.base.CAppBean;
import com.shuto.mam.webservice.ebs.client.ErpUtil;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author Administrator
 *
 */
public class WomurAppBean extends CAppBean {

	protected String OWNERTABLE = "";
	protected long OWNERID;

	public int SAVE() throws MXException, RemoteException {
		MboRemote mbo = app.getAppBean().getMbo();
		if (!hasAuth()) {
			throw new MXApplicationException("system", "noauth");
		}
		/** 领料验证 **/
		// 验证是否选择了旧物资编码\失效的编码
		String msg = yNNewItem(mbo);
		if (msg != null && !"".equals(msg)) {
			throw new MXApplicationException("workorder", msg);
		}
		//验证领用数量是否为0
      	msg = ynQuanty(mbo);
      	if (msg != null && !"".equals(msg)) {
      		throw new MXApplicationException("workorder", "\r\n"+msg);
      	}
		// 所属组织\预算科目\验证项目\任务
		msg = checkYskmXmRw(mbo);
		if (msg != null && !"".equals(msg)) {
			throw new MXApplicationException("workorder", "请选择 " + msg + "!");
		}
		// 领料总金额
		mbo.setValue("totalcost", getTotalCost(mbo));
		setWpmaterOrgtype(mbo);
		/** 领料验证 **/
		return super.SAVE();
	}

	/**
	 * 上传计划
	 *
	 * @return
	 */
	public int SCJH() throws MXException, RemoteException {
		MboRemote woMbo = app.getAppBean().getMbo();
		Map<String,Map<String,String>> returnmap = null;
		Map<String, String> jhret = null;
		Map<String, String> llret = null;
		// 记录原状态,用于接口调用失败时,还原单据状态
		String oldstatus = woMbo.getString("status");
		MboSetRemote maters = woMbo.getMboSet("SHOWWOMURITEM");
		if (!maters.isEmpty()) {
			try {
				woMbo.setValue("status", "已批准", MboConstants.NOACCESSCHECK);
				super.SAVE();
				ErpUtil erputil = new ErpUtil();
				returnmap = erputil.uploadWo(woMbo);
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
				if (!success) {
					MboSetRemote woset = woMbo.getMboSet("$workorder", "workorder",
							"wonum=:wonum");
					woset.getMbo(0).setValue("status", oldstatus,
							MboConstants.NOACCESSCHECK);
					woset.save(2L);
					woset.close();
					throw new psdi.util.MXApplicationException("", msg);
				}
			}
		}
		return 1;
	}

	/**
	 * 取消计划
	 *
	 * @return
	 */
	public int QXJH() throws MXException, RemoteException {
		MboRemote woMbo = app.getAppBean().getMbo();
		String personid = woMbo.getUserInfo().getPersonId();
		Map<String,Map<String,String>> returnmap = null;
		Map<String, String> jhret = null;
		Map<String, String> llret = null;
		// 记录原状态,用于接口调用失败时,还原单据状态
		String oldstatus = woMbo.getString("status");
		MboSetRemote maters = woMbo.getMboSet("SHOWWOMURITEM");
		if (!maters.isEmpty()) {
			try {
				woMbo.setValue("status", "已取消", MboConstants.NOACCESSCHECK);
				woMbo.setValue("cancel_person", personid);
				woMbo.setValue("cancel_date", psdi.server.MXServer.getMXServer()
						.getDate());
				woMbo.setValue("cancel_reason", "取消计划");
				super.SAVE();
				ErpUtil erputil = new ErpUtil();
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
				if (!success) {
					MboSetRemote woset = woMbo.getMboSet("$workorder", "workorder",
							"wonum=:wonum");
					woset.getMbo(0).setValue("status", oldstatus,
							MboConstants.NOACCESSCHECK);
					woset.save(2L);
					woset.close();
					throw new psdi.util.MXApplicationException("", msg);
				}
			}
		}
		return 1;
	}

	public boolean hasAuth() throws MXException, RemoteException {
		MboRemote mbo = getMbo();
		if (mbo == null) {
			return true;
		}
		String personid = mbo.getUserInfo().getPersonId();

		if (isSysuser()) {
			return true;
		}
		this.OWNERTABLE = getMbo().getName();
		this.OWNERID = getMbo().getUniqueIDValue();

		MboSetRemote wfinstance = mbo.getMboSet("instance", "WFINSTANCE",
				"ownertable='" + this.OWNERTABLE + "' and ownerid='"
						+ this.OWNERID + "' and active = 1");
		boolean noInstance = wfinstance.isEmpty();
		wfinstance.close();

		if (noInstance) {
			String createperson = mbo.getString("createperson");
			return personid.equals(createperson);
		}
		String sql = "ownerid='" + this.OWNERID + "'" + " and ownertable='"
				+ this.OWNERTABLE + "'" + " and assignstatus='活动'"
				+ " and assigncode='" + personid + "'";
		MboSetRemote mbosetremote = mbo.getMboSet("$assigncode",
				"WFASSIGNMENT", sql);
		boolean hasAssigncode = mbosetremote.isEmpty();
		mbosetremote.close();
		return !hasAssigncode;
	}

	@Override
    public boolean isSysuser() throws MXException, RemoteException {
		MboRemote mbo = getMbo();
		String personid = mbo.getUserInfo().getPersonId();
		MboSetRemote users = mbo.getMboSet("$tmp_maxuser", "maxuser",
				"sysuser =1 and personid='" + personid + "'");
		boolean isEmpty = (users.getMbo(0) != null)
				&& (users.getMbo(1) == null);
		users.close();
		return isEmpty;
	}

	/**
	 * 验证是否为新物资编码\失效的编码
	 *
	 * @param wo
	 * @return
	 * @throws RemoteException
	 * @throws MXException
	 */
	private String yNNewItem(MboRemote wo) throws RemoteException, MXException {
		String msg = "";
		DataBean wpmaterials = this.app.getDataBean("plans_materials_table");
		String erpid = null;
		String status = null;
		String erpstatus = null;
		String erpitemstatus = null;
		if (wpmaterials != null) {
			for (int i = 0; i < wpmaterials.count(); i++) {
				if (!wpmaterials.getMbo(i).toBeDeleted()) {
					erpid = wpmaterials.getMbo(i).getString("item.erpid");
					status = wpmaterials.getMbo(i).getString(
							"erpinventlist.status");
					erpstatus = wpmaterials.getMbo(i).getString(
							"erpinventlist.erpstatus");
					erpitemstatus = wpmaterials.getMbo(i).getString(
							"erpinventlist.erpitemstatus");
					if (erpid == null || "".equals(erpid)) {
						msg = msg + wpmaterials.getMbo(i).getString("itemnum")
								+ "为旧编码,不能领用!\r\n";
					}
					if ((status != null && "不活动".equalsIgnoreCase(status))
							|| (erpstatus != null && "Y"
									.equalsIgnoreCase(erpstatus))
							|| (erpitemstatus != null && "INACTIVE"
									.equalsIgnoreCase(erpitemstatus))) {
						msg = msg + wpmaterials.getMbo(i).getString("itemnum")
								+ "已失效,不能领用!\r\n";
					}
				}
			}
		}
		return msg;
	}

	/**
	 * 验证领用数量是否为0
	 * @param wo
	 * @return
	 * @throws RemoteException
	 * @throws MXException
	 */
	private String ynQuanty(MboRemote wo) throws RemoteException, MXException {
		String msg = "";
		DataBean wpmaterials = this.app.getDataBean("plans_materials_table");
		double itemqty = 0.0;
		if (wpmaterials != null) {
			for (int i = 0; i < wpmaterials.count(); i++) {
				if (!wpmaterials.getMbo(i).toBeDeleted()) {
					itemqty = wpmaterials.getMbo(i).getDouble("itemqty");
					if (itemqty <= 0) {
						msg = msg + wpmaterials.getMbo(i).getString("itemnum") + " 申请数量需大于0 !\r\n";
					}
				}
			}
		}
		return msg;
	}

	/**
	 * 验证项目\预算科目\任务
	 *
	 * @param wo
	 * @return
	 * @throws RemoteException
	 * @throws MXException
	 */
	private String checkYskmXmRw(MboRemote wo) throws RemoteException,
			MXException {
		String msg = "";
		// 所属组织
		String orgtype = wo.getString("interfaceorgtype");
		// 预算项目
		String xm = wo.getString("project_code");
		// 预算科目
		String yxkm = wo.getString("dept_line_id");
		// 预算任务
		String rw = wo.getString("task_id");
		// 产品段
		String prod = wo.getString("prod_code");
		// 机组
		String jz = wo.getString("c_jizu");
		// ERP专业
		String erpzy = wo.getString("s_erpprofession");
		DataBean wpmaterials = this.app.getDataBean("plans_materials_table");
		boolean hasMater = false;
		if (wpmaterials != null) {
			for (int i = 0; i < wpmaterials.count(); i++) {
				if (!wpmaterials.getMbo(i).toBeDeleted()) {
					hasMater = true;
					break;
				}
			}
		} else {
			MboSetRemote maters = wo.getMboSet("SHOWWOMURITEM");
			if (!maters.isEmpty()) {
				hasMater = true;
			}
		}
		// 如果策划了物料
		if (hasMater) {
			if (orgtype == null || "".equals(orgtype)) {
				msg = msg + " 所属组织";
			}
			if (yxkm == null || "".equals(yxkm)) {
				msg = msg + " 预算科目";
			} else {
				MboSetRemote yxkms = wo.getMboSet("FACCOUNTS");
				if ("Y".equals(yxkms.getMbo(0).getString("proj_required_flag"))
						&& (xm == null || "".equals(xm))) {
					msg = msg + " 预算项目";
				}
				if ("Y".equals(yxkms.getMbo(0).getString("prod_required_flag"))
						&& (prod == null || "".equals(prod))) {
					msg = msg + " 产品段";
				}
			}
			if (xm != null && !"".equals(xm) && (rw == null || "".equals(rw))) {
				msg = msg + " 预算任务";
			}
			if (jz == null || "".equals(jz)) {
				msg = msg + " 机组";
			}
			if (erpzy == null || "".equals(erpzy)) {
				msg = msg + " ERP专业";
			}
		}
		return msg;
	}

	/**
	 * 获取工单物料总金额
	 *
	 * @param wo
	 * @return
	 * @throws RemoteException
	 * @throws MXException
	 */
	private double getTotalCost(MboRemote wo) throws RemoteException,
			MXException {
		double totalcost = 0.0D;
		DataBean wpmaterials = this.app.getDataBean("plans_materials_table");
		if (wpmaterials != null) {
			for (int i = 0; i < wpmaterials.count(); i++) {
				if (!wpmaterials.getMbo(i).toBeDeleted()) {
					totalcost += wpmaterials.getMbo(i).getDouble("linecost");
				}
			}
		} else {
			totalcost = wo.getDouble("totalcost");
		}
		return totalcost;
	}

	/**
	 * 设置工单物料字表所属组织
	 * @param wo
	 * @throws RemoteException
	 * @throws MXException
	 */
	private void setWpmaterOrgtype(MboRemote wo) throws RemoteException, MXException {
		super.SAVE();
		MboSetRemote maters = wo.getMboSet("SHOWWOMURITEM");
		if (!maters.isEmpty()) {
			// 所属组织
			String orgtype = wo.getString("interfaceorgtype");
			for (int i=0;i<maters.count();i++) {
				maters.getMbo(i).setValue("orgtype", orgtype);
			}
		}
	}
}
