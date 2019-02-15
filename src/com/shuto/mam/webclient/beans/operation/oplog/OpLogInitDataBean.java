package com.shuto.mam.webclient.beans.operation.oplog;

import java.rmi.RemoteException;

import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import com.shuto.mam.app.operation.oplog.OpLog;

/**
 * @author wuqi
 * @version V1.0
 * @Title: OpLogInitDataBean.java
 * @Package com.shuto.mam.webclient.beans.operation.oplog
 * @Description: TODO(交待事项)
 * @date 2017-5-10 下午05:22:42
 */
public class OpLogInitDataBean extends DataBean {
	@Override
	public int toggledetailstate(boolean open) throws MXException {
		return super.toggledetailstate(open);
	}

	@Override
	public int toggleselectrow() throws MXException, RemoteException {

		return super.toggleselectrow();
	}

	// 删除oplognote 行
	@Override
	public int toggledeleterow() throws MXException {
		try {
			OpLog oplog = (OpLog) this.parent.getMbo();
			String msg = oplog.isReadOnly();
			System.out.println("---------------------------------------" + msg);
			// 当前登录人
			String login2 = oplog.getUserInfo().getPersonId();
			MboSetRemote oplognum_oplognotes = oplog
					.getMboSet("oplognum_oplognote");
			// 运行记事创建人
			String writeperson = oplognum_oplognotes.getString("writeperson");
			// 判断日志类型
			// if (oplogType.equals("值长运行日志")) {
			// 非创建人和管理员不能删除
			if ((!login2.equals(writeperson))
					&& (!"MAXADMIN".equalsIgnoreCase(login2))) {
				throw new MXApplicationException("system", "messager",
						new String[] { "您没有权限操作！" });
			} else if (msg != null) {
				throw new MXApplicationException("oplog", msg);
			}
			// } else if (oplogType.equals("#1机组运行日志")
			// || oplogType.equals("#2机组运行日志")) {
			// 主控和副控删除
			// if (!post.equals("主控") || !post.equals("副控")) {
			// throw new MXApplicationException("system", "messager",
			// new String[] { "您没有删除权限！" });
			// }
			// } else if (oplogType.equals("巡检运行日志")) {
			// 巡检人员删除
			// if (!post.equals("巡检")) {
			// throw new MXApplicationException("system", "messager",
			// new String[] { "您没有删除权限！" });
			// }
			// }

			super.toggledeleterow();

		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return 1;
	}

	@Override
	public int addrow() throws MXException {
		try {
			OpLog oplog = (OpLog) this.parent.getMbo();
			String login = oplog.getUserInfo().getPersonId();
			System.out.println("-----------------------" + login);
			String msg = oplog.isReadOnly();
			System.out.println("-----------------------" + msg);
			MboSetRemote oplogpersonmsr = this.isJurisdiction(oplog);
			if (oplogpersonmsr.isEmpty() && !"MAXADMIN".equalsIgnoreCase(login)) {
				throw new MXApplicationException("system", "messager",
						new String[] { "您没有权限操作！" });
			} else if (msg != null) {
				throw new MXApplicationException("oplog", msg);
			}
			super.addrow();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return 1;
	}

	private MboSetRemote isJurisdiction(OpLog oplog) throws RemoteException,
			MXException {
		// 登录人
		String login2 = oplog.getUserInfo().getPersonId();
		System.out.println("--------------------------" + login2);
		// 日志类型
		String oplogtype = oplog.getString("OPLOGTYPE");
		// 当班值别
		String zhibie = oplog.getString("ZHIBIE");
		String wheresql = "oplogtype ='" + oplogtype + "' and personid ='"
				+ login2 + "'";
		if ("#1机组运行日志".equals(oplogtype) || "#2机组运行日志".equals(oplogtype)) {
			// 日志类型为#1#2运行日志时增加值别过滤
			wheresql += "  and zhibie = '" + zhibie + "'";
		} else if ("巡检运行日志".equals(oplogtype) || "辅控运行日志".equals(oplogtype)) {
			wheresql += "";
		} else if ("分系统投运日志".equals(oplogtype)) {
			wheresql = "";
		} else if ("值长运行日志".equals(oplogtype)) {
			wheresql = " personid ='" + login2
					+ "' and oplogtype='值长运行日志' and zhibie = '" + zhibie + "'";
		}
		return oplog.getMboSet("$oplogperson", "oplogperson", wheresql);
	}

	@Override
	public void initialize() throws MXException, RemoteException {
		super.initialize();
		setReadOnly();
	}

	@Override
	public void refreshTable() {
		super.refreshTable();
		setReadOnly();
	}

	private void setReadOnly() {
		try {
			OpLog oplog = (OpLog) this.parent.getMbo();
			String login2 = oplog.getUserInfo().getPersonId();
			MboSetRemote oplognum_oplognotes = oplog
					.getMboSet("oplognum_oplognote");

			if (!oplognum_oplognotes.isEmpty()) {
				for (int i = 0; i < oplognum_oplognotes.count(); ++i) {
					String writeperson = oplognum_oplognotes.getMbo(i)
							.getString("writeperson");
					if ((!login2.equals(writeperson))
							&& (!"MAXADMIN".equalsIgnoreCase(login2))) {
						// oplognum_oplognotes.getMbo(i).setFlag(7L, true);
						oplognum_oplognotes.getMbo(i).setFieldFlag("JZ", 7L,
								true);
						oplognum_oplognotes.getMbo(i).setFieldFlag(
								"HAPPENDATE", 7L, true);
						oplognum_oplognotes.getMbo(i).setFieldFlag("TYPE", 7L,
								true);
						oplognum_oplognotes.getMbo(i).setFieldFlag("CONTENT",
								7L, true);
						oplognum_oplognotes.getMbo(i).setFieldFlag("YNZY", 7L,
								true);
						oplognum_oplognotes.getMbo(i).setFieldFlag("YNSBYC",
								7L, true);
						oplognum_oplognotes.getMbo(i).setFieldFlag(
								"COMMITNEXT", 7L, false);

					}

					// if ("运行记事".equalsIgnoreCase(style)) {
					//
					// //带入下一班清空
					// oplognum_oplognotes.getMbo(i).setValue("COMMITNEXT",
					// false, 11L);
					// //专业清空
					// oplognum_oplognotes.getMbo(i).setValue("PROFESSION", "",
					// 11L);
					// oplognum_oplognotes.getMbo(i).setFlag(7L, false);
					// //记事类型不知读
					// oplognum_oplognotes.getMbo(i).setFieldFlag("TYPE", 7L,
					// false);
					// //记事类型必填
					// oplognum_oplognotes.getMbo(i).setFieldFlag("TYPE", 128L,
					// true);
					// //专业和带入下一个班次只读
					// oplognum_oplognotes.getMbo(i).setFieldFlag("PROFESSION",
					// 7L, true);
					// oplognum_oplognotes.getMbo(i).setFieldFlag("COMMITNEXT",
					// 7L, true);
					// //设置记事类型默认值
					// oplognum_oplognotes.getMbo(i).setValue("TYPE", "运行记事",
					// 11L);
					// } else if ("专业提示".equalsIgnoreCase(style)) {
					// oplognum_oplognotes.getMbo(i).setFieldFlag("PROFESSION",
					// 7L, false);
					// oplognum_oplognotes.getMbo(i).setFlag(7L, false);
					// //记事类型设为不必填
					// oplognum_oplognotes.getMbo(i).setFieldFlag("TYPE", 128L,
					// false);
					// //记事类型清空
					// oplognum_oplognotes.getMbo(i).setValue("TYPE", "", 11L);
					// //带入下一班清空
					// oplognum_oplognotes.getMbo(i).setValue("COMMITNEXT",
					// false, 11L);
					// //记事类型和带入下一个班次只读
					// oplognum_oplognotes.getMbo(i).setFieldFlag("TYPE", 7L,
					// true);
					// oplognum_oplognotes.getMbo(i).setFieldFlag("COMMITNEXT",
					// 7L, true);
					// } else if ("交代事项".equalsIgnoreCase(style)) {
					// oplognum_oplognotes.getMbo(i).setFieldFlag("COMMITNEXT",
					// 7L, false);
					// oplognum_oplognotes.getMbo(i).setFlag(7L, false);
					// //记事类型设为不必填
					// oplognum_oplognotes.getMbo(i).setFieldFlag("TYPE", 128L,
					// false);
					// //记事类型清空
					// oplognum_oplognotes.getMbo(i).setValue("TYPE", "", 11L);
					// //专业清空
					// oplognum_oplognotes.getMbo(i).setValue("PROFESSION", "",
					// 11L);
					// //记事类型和专业只读
					// oplognum_oplognotes.getMbo(i).setFieldFlag("PROFESSION",
					// 7L, true);
					// oplognum_oplognotes.getMbo(i).setFieldFlag("TYPE", 7L,
					// true);
					// }
				}
			}

			MboSetRemote oplognopros = oplog.getMboSet("OPLOGNOPRO_ZY");

			if (!oplognopros.isEmpty()) {
				for (int i = 0; i < oplognopros.count(); ++i) {
					String writeperson = oplognopros.getMbo(i).getString(
							"TSPERSON");

					if ((!login2.equals(writeperson))
							&& (!"MAXADMIN".equalsIgnoreCase(login2))) {
						oplognopros.getMbo(i).setFlag(7L, true);
					}
				}
			}

			MboSetRemote oplognopros2 = oplog.getMboSet("OPLOGNOPRO_BM");
			if (!oplognopros2.isEmpty()) {
				for (int i = 0; i < oplognopros2.count(); ++i) {
					String writeperson = oplognopros2.getMbo(i).getString(
							"TSPERSON");

					if ((!login2.equals(writeperson))
							&& (!"MAXADMIN".equalsIgnoreCase(login2))) {
						oplognopros2.getMbo(i).setFlag(7L, true);
					}
				}
			}
		} catch (RemoteException | MXException e) {
			e.printStackTrace();
		}
	}

}