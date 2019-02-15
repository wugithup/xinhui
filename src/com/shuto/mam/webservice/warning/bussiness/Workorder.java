package com.shuto.mam.webservice.warning.bussiness;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.shuto.mam.webservice.bean.Element;
import com.shuto.mam.webservice.util.MboTools;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

/** 
* @ClassName: Workorder  
* @Description: 预警平台借口类
* @author lull lull@shuto.cn
* @date 2017年6月29日 上午9:42:30 
*  
*/
public class Workorder {

	public static String S000A000 = result("SUCCESS", "工单编号");
	public static String ERROR001 = result("ERROR001", "推送用户不存在");
	public static String ERROR002 = result("ERROR002", "推送地点不存在");
	public static String ERROR003 = result("ERROR003", "推送风机不存在");
	public static String ERROR004 = result("ERROR004", "推送时间格式不正确");
	public static String ERROR005 = result("ERROR005", "yjuid 不能为空");
	public static String ERROR = result("ERROR", "推送失败");

	/**
	 * 
	* @Title: addWarningWO 
	* @Description: TODO(新建预警工单) 
	 * @param yjuid            预警流水ID
	 * @param c_yjkssj	            预警开始时间
	 * @param c_yjjssj		预警结束时间
	 * @param c_yjnr		预警内容
	 * @param c_yjtsr		预警推送人
	 * @param c_yjdj		预警等级  
	 * @param c_yjyxms		预警运行模式
	 * @param siteid		工单属于哪个地点的
	 * @param c_wfid		风机对应风电场标识
	 * @param c_dfid		风机编号
	 * @param c_createdate  预警推送时间
	 * @param c_yjjx        预警机型
	 * @param c_yjssxt      所属系统
	 * @param c_yjly        预警来源
	 * @param c_yjpc        预警频次
	 * @return				如果成功则创建预警工单编号,否则返回 "FAILD"
	 */
	public String addWarningWO(String yjuid, String c_yjkssj, String c_yjjssj, String c_yjnr, String c_yjtsr,
			String c_yjdj, String c_yjyxms, String siteid, String c_wfid, String c_dfid, String c_createdate,
			String c_yjjx, String c_yjssxt, String c_yjly, String c_yjpc, String c_mxid, String c_yjdate) {
		String xml = "<workorder>	<yjuid>" + yjuid + "</yjuid>	<c_yjkssj>" + c_yjkssj + "</c_yjkssj>	<c_yjjssj>"
				+ c_yjjssj + "</c_yjjssj>	<c_yjnr>" + c_yjnr + "</c_yjnr>	<c_yjtsr>" + c_yjtsr
				+ "</c_yjtsr>	<c_yjdj>" + c_yjdj + "</c_yjdj>	<c_yjyxms>" + c_yjyxms + "</c_yjyxms>	<siteid>"
				+ siteid + "</siteid>	<c_wfid>" + c_wfid + "</c_wfid>	<c_dfid>" + c_dfid + "</c_dfid><c_createdate>"
				+ c_createdate + "</c_createdate>	<c_yjjx>" + c_yjjx + "</c_yjjx>	<c_yjssxt>" + c_yjssxt
				+ "</c_yjssxt>	<c_yjly>" + c_yjly + "</c_yjly>	<c_yjpc>" + c_yjpc + "</c_yjpc>	<c_mxid>" + c_mxid
				+ "</c_mxid>	<c_yjdate>" + c_yjdate + "</c_yjdate></workorder>";

		try {

			if ("".equals(yjuid) || yjuid == null) {
				return ERROR005;
			}

			String sql = " c_workordertype = '预警工单' and siteid = '" + siteid + "' and warninguid = '" + yjuid
					+ "'  and status not in ('关闭','已作废')  ";
			MboSetRemote woSet = MboTools.getMboSet("WORKORDER");
			woSet.setWhere(sql);
			woSet.setOrderBy(" c_createdate desc ");
			woSet.reset();
			if (!woSet.isEmpty()) {
				SimpleDateFormat sdfFullFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String result = "FAILD";
				String wonum = woSet.getMbo(0).getString("wonum");
				String status = woSet.getMbo(0).getString("status");
				String createperson = woSet.getMbo(0).getString("c_createby");
				String createdate = sdfFullFormat.format(woSet.getMbo(0).getDate("c_createdate"));
				String remarkdesc = woSet.getMbo(0).getString("remarkdesc");
				sql = "<?xml version=\"1.0\"?><result><result_code>" + result + "</result_code><wonum>" + wonum
						+ "</wonum><status>" + status + "</status><createperson>" + createperson
						+ "</createperson><createdate>" + createdate + "</createdate><remark>" + remarkdesc
						+ "</remark></result>";
				woSet.close();
				loginfo(xml, "addWarningWO", sql);
				return sql;
			}
			woSet.close();

			/** 判断推送人是否存在BFDQ **/
			MboSetRemote personSet = MboTools.getMboSet("PERSON");
			personSet.setWhere("personid = upper('" + c_yjtsr + "') ");
			personSet.reset();
			boolean isNoPerson = personSet.isEmpty();
			personSet.close();
			if (isNoPerson) {
				loginfo(xml, "addWarningWO", ERROR001);
				return ERROR001;
			}

			/** 判断地点是否存在 **/
			MboSetRemote siteSet = MboTools.getMboSet("site");
			siteSet.setWhere("siteid = '" + siteid + "' ");
			siteSet.reset();
			boolean isNoSite = siteSet.isEmpty();
			siteSet.close();
			if (isNoSite) {
				loginfo(xml, "addWarningWO", ERROR002);
				return ERROR002;
			}

			/**   **/
			MboSetRemote dfinfoSet = MboTools.getMboSet("c_dfinfo");
			dfinfoSet.setWhere("c_wfname='" + c_wfid + "' and c_dfid='" + c_dfid + "'");
			dfinfoSet.reset();
			if (dfinfoSet.isEmpty()) {
				dfinfoSet.close();
				loginfo(xml, "addWarningWO", ERROR003);
				return ERROR003;
			}
			MboRemote dfinfoMbo = dfinfoSet.getMbo(0);
			String location = dfinfoMbo.getString("c_dfkks");
			String level1 = dfinfoMbo.getString("level1");
			String level2 = dfinfoMbo.getString("level2");
			dfinfoSet.close();

			/** 判断运维模式 **/
			MboSetRemote orgstructureSet = MboTools.getMboSet("orgstructure");
			orgstructureSet.setWhere("type='站点' and value = '" + siteid + "'");
			orgstructureSet.reset();
			if (orgstructureSet.isEmpty()) {
				orgstructureSet.close();
				loginfo(xml, "addWarningWO", ERROR002);
				return ERROR002;
			}
			/**如果是空则为单电厂模式；不为空则为集中运维模式 **/
			boolean issjyw = orgstructureSet.getMbo(0).isNull("inoc");
			orgstructureSet.close();

			/** 格式化时间 **/
			SimpleDateFormat sdfFullFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date yjkssj = null;
			Date yjjssj = null;
			try {
				yjkssj = sdfFullFormat.parse(c_yjkssj);
				yjjssj = sdfFullFormat.parse(c_yjjssj);
			} catch (ParseException e) {
				loginfo(xml, "addWarningWO", ERROR004);
				return ERROR004;
			}

			MboSetRemote wos = MboTools.getMboSet("workorder");
			wos.setWhere(
					"C_ISWOTRACK = 1 and substr(C_WOTKNUM,2,6) = to_char(sysdate,'yyyyMM')  and substr(C_WOTKNUM,0,1) = 'F'  and siteid = '"
							+ siteid + "'");
			wos.reset();
			int num = wos.count() + 1;
			wos.close();
			/* 生成预警工单 */
			MboSetRemote workorderSet = MboTools.getMboSet("WORKORDER");
			MboRemote woMbo = workorderSet.add();
			woMbo.setValue("warninguid", yjuid, 2L); // 预警流水号
			woMbo.setValue("c_yjkssj", yjkssj, 2L);// 预警开始时间
			woMbo.setValue("c_yjjssj", yjjssj, 2L);// 预警结束时间
			woMbo.setValue("DESCRIPTION", c_yjnr);// 预警内容
			woMbo.setValue("C_CREATEBY", c_yjtsr);// 预警推送人
			woMbo.setValue("c_yjdj", c_yjdj, 2L); // 预警等级
			woMbo.setValue("c_yjyxms", c_yjyxms, 2L);// 预警运行模式
			woMbo.setValue("siteid", siteid, 2L);// 地点
			SimpleDateFormat dateF = new SimpleDateFormat("yyyyMM");
			Calendar calendar = Calendar.getInstance();
			DecimalFormat df = new DecimalFormat("000");
			woMbo.setValue("C_WOTKNUM", "F" + dateF.format(calendar.getTime()) + df.format(num), 11l);// 编号
			woMbo.setValue("C_ISWOTRACK", true, 2L);
			woMbo.setValue("C_WOPROFESS", "风机");
			woMbo.setValue("C_WORKORDERTYPE", "预警工单");
			woMbo.setValue("LOCATION", location);
			woMbo.setValue("LEVEL1", level1);
			woMbo.setValue("LEVEL2", level2);
			woMbo.setValue("status", "新建工单", 11L);
			woMbo.setValue("lead", "", 11L);
			woMbo.setValue("issjyw", !issjyw, 11L);
			woMbo.setValue("c_createdate", c_createdate, 11L);// 预警推送时间
			woMbo.setValue("C_yjjx", c_yjjx, 11L);// 预警机型
			woMbo.setValue("C_yjssxt", c_yjssxt, 11L);// 所属系统
			woMbo.setValue("C_yjly", c_yjly, 11L);// 预警来源
			woMbo.setValue("C_yjpc", c_yjpc, 11L);// 预警频次
			woMbo.setValue("c_mxid", c_mxid, 11L);// 预警频次
			woMbo.setValue("c_yjdate", c_yjdate, 11L);// 预警频次
			workorderSet.save(2L);
			String wonum = woMbo.getString("wonum");
			workorderSet.close();

			loginfo(xml, "addWarningWO", result("SUCCESS", wonum));
			return result("SUCCESS", wonum);
		} catch (RemoteException e1) {
			loginfo(xml, "addWarningWO", ERROR);
			e1.printStackTrace();
			return ERROR;
		} catch (MXException e1) {
			loginfo(xml, "addWarningWO", ERROR);
			e1.printStackTrace();
			return ERROR;
		}
	}

	/**
	 * 
	 * @param code
	 * @param msg
	 * @return 
	 */
	private static String result(String code, String msg) {
		return "<?xml version=\"1.0\"?>" + new Element("result",
				new Element[] { new Element("result_code", code), new Element("result_msg", msg) }).toString();
	}

	/**
	 * 
	* @Title: queryStatus 
	* @Description: 根据预警流水查询工单状态
	* @param @param yjuid
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */

	public String queryStatus(String yjuid) {
		String status = "FAILD";
		try {
			MboSetRemote workorderSet = MboTools.getMboSet("WORKORDER");
			workorderSet.setWhere("warninguid='" + yjuid + "'");
			workorderSet.reset();
			if (!workorderSet.isEmpty()) {
				status = workorderSet.getMbo(0).getString("status");
			}
			workorderSet.close();
		} catch (RemoteException e) {
			// e.printStackTrace();
		} catch (MXException e) {
			// e.printStackTrace();
		}
		loginfo(yjuid, "queryStatus", status);
		return status;
	}

	/**
	 * 
	* @Title: queryWOnum 
	* @Description: 返回预警工单编号
	* @param @param yjuid
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String queryWOnum(String yjuid) {
		String wonum = "FAILD";
		try {
			MboSetRemote workorderSet = MboTools.getMboSet("WORKORDER");
			workorderSet.setWhere("warninguid='" + yjuid + "'");
			workorderSet.reset();
			if (!workorderSet.isEmpty()) {
				wonum = workorderSet.getMbo(0).getString("wonum");
			}
			workorderSet.close();
		} catch (RemoteException e) {
			e.printStackTrace();
			return wonum;
		} catch (MXException e) {
			e.printStackTrace();
			return wonum;
		}
		loginfo(yjuid, "queryWOnum", wonum);
		return wonum;
	}

	/**
	 * 
	* @Title: queryWang 
	* @Description: 返回预警工单编号、预警工单状态、预警推送人、预警推送时间
	* @param @param yjuid
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String queryWang(String yjuid) {
		String result = "FAILD";
		String wonum = "";
		String status = "";
		String createperson = "";
		String createdate = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			MboSetRemote workorderSet = MboTools.getMboSet("WORKORDER");
			workorderSet.setWhere("warninguid='" + yjuid + "' and status not in ('关闭','已作废')");
			workorderSet.reset();
			if (!workorderSet.isEmpty()) {
				result = "exist";
				wonum = workorderSet.getMbo(0).getString("wonum");
				status = workorderSet.getMbo(0).getString("status");
				createperson = workorderSet.getMbo(0).getString("c_createby");
				createdate = df.format(workorderSet.getMbo(0).getDate("c_createdate"));
			}
			wonum = "<workorder><result>" + result + "</result><wonum>" + wonum + "</wonum><status>" + status
					+ "</status><createperson>" + createperson + "</createperson><createdate>" + createdate
					+ "</createdate>" + "</workorder>";

			workorderSet.close();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}
		loginfo(yjuid, "queryWang", wonum);
		return wonum;
	}

	/**
	 * 
	* @Title: queryStatus2 
	* @Description: 工单编号查询工单状态
	* @param @param wonum
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */

	public String queryStatus2(String wonum) {
		String status = "";
		String result1 = "";
		String result2 = "";
		String remarkdesc = "";
		String cwyj = "";
		String c_tszt = "";
		try {
			MboSetRemote workorderSet = MboTools.getMboSet("WORKORDER");
			workorderSet.setWhere("wonum='" + wonum + "'");
			workorderSet.reset();
			if (!workorderSet.isEmpty()) {
				status = workorderSet.getMbo(0).getString("status");// 工单状态;
				result1 = workorderSet.getMbo(0).getString("c_tszt");// 推送状态
				c_tszt = workorderSet.getMbo(0).getString("c_tszt");// 推送状态
				cwyj = workorderSet.getMbo(0).getString("c_tscwyy");// 错误原因
				if ("错误".equals(result1)) {
					result1 = workorderSet.getMbo(0).getString("c_tscwyy");// 原因
					if ("其他原因".equals(result1)) {
						cwyj = workorderSet.getMbo(0).getString("c_qtyy");// 错误原因
					}
				}
				result2 = workorderSet.getMbo(0).getString("c_wsjy");// 完善建议
				remarkdesc = workorderSet.getMbo(0).getString("remarkdesc");
			}
			status = "<workorder><status>" + status + "</status><c_tszt>" + c_tszt + "" + "</c_tszt><c_cwyj>" + cwyj
					+ "</c_cwyj><c_wsjy>" + result2 + "</c_wsjy><remark>" + remarkdesc + "</remark></workorder>";
			workorderSet.close();
		} catch (RemoteException e) {
			e.printStackTrace();
			return status;
		} catch (MXException e) {
			e.printStackTrace();
			return status;
		}
		loginfo(wonum, "queryStatus2", status);
		return status;
	}

	/**
	 * 
	* @Title: loginfo 
	* @Description: 日志文件
	* @param @param requestData
	* @param @param type
	* @param @param result    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void loginfo(String requestData, String type, String result) {
		try {
			MboSetRemote logSet = MboTools.getMboSet("interfacelog");
			MboRemote logMbo = logSet.add();
			logMbo.setValue("SYNCHTYPE", type, 11l);
			logMbo.setValue("synchdesc", result, 11l);
			logMbo.setValue("SYNCHDATE", new Date(), 11l);
			logMbo.setValue("SYNCHSTATUS", "", 11l);
			logMbo.setValue("DESCRIPTION", "预警工单", 11l);
			logMbo.setValue("DATALOG", requestData);
			logSet.save();
			logSet.close();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}

	}

}
