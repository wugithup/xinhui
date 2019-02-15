package com.shuto.mam.webclient.beans.stpi.pi_importdata;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.shuto.mam.crontask.stpi.dbutils.MaximoUtils;
import com.shuto.mam.webclient.beans.stpi.pi_importdata.datatype.MyDataType;

import cc.aicode.e2e.ExcelHelper;
import cc.aicode.e2e.exception.ExcelParseException;

/**
 * 功能说明： 单元测试 参数说明：
 *
 */
public class ImportData {
	public final static String fileUrl = "C:\\PiImportData\\dataFile\\";// 扫描的文件夹路径

	public ImportData() throws ExcelParseException {
		ExcelHelper.registerNewType(MyDataType.class);
	}

	public static void main(String[] args) throws InvalidFormatException, IOException, ExcelParseException {
		ImportData.analysisFiles("巡点检初始化数据模板-巡检.xls", null);
	}

	public static String analysisFiles(String fileName, String type) throws InvalidFormatException, IOException {
		ErrorMessage errorMessage = ErrorMessage.getInstance();
		errorMessage.setEmpty();
		String url = fileUrl + fileName;
		ExcelHelper eh = ExcelHelper.readExcel(url);
		List<PiData> entitys_dj = null;
		List<PiXjData> entitys_xj = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String timestamp = sdf.format(date);
		String result = "";
		try {
			if ("点检".equals(type)) {
				entitys_dj = eh.toEntitys(PiData.class);
			} else {
				entitys_xj = eh.toEntitys(PiXjData.class);
			}
			result = errorMessage.getResult();
			if (result.isEmpty()) {
				ImportDB importData = new ImportDB();
				if ("点检".equals(type)) {
					importData.importData(entitys_dj, timestamp);
				} else {
					importData.importXjData(entitys_xj, timestamp);
				}
				if (setPiData(timestamp)) {
					result = "导入成功！";
				} else {
					result = "导入失败，请联系系统管理员！";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 插入
	 * 
	 * @param timestamp
	 * @return
	 */
	public static boolean setPiData(String timestamp) {
		boolean flag = false;
		// 分组查询任务设置
		String sql1 = "select taskcfgname,professional,type,siteid,orgid from st_pi_importdata where timestamp = '"
				+ timestamp + "' group by taskcfgname,professional,type,siteid,orgid";
		String sql12 = "select SITEID, ORGID, PROFESSIONAL, TYPE, TASKCFGNAME, AREANAME, RFID_CODE, DEVICE_NO, DEVICE_NAME, LOCATION, DEVICE_PART_NAME, CHECKPROJECT, CHECK_METHOD, ON_OFF_POTIN, POINT_UNIT, HIGHER_LIMIT, LOWER_LIMIT, SHAKE_TYPE, POINT_NORM, ISPCHECK, TIMESTAMP, POSTNO, DATAFORMATNO, CYCLE_UNIT, CYCLE, BEGIN_TIME, END_TIME, ST_PI_DUTYINFONUM, DAYCYCLE "
				+ "from st_pi_importdata where timestamp = ? and taskcfgname = ? and professional = ? and type = ? and siteid = ? and orgid = ?";

		String sql2 = "insert into st_pi_taskcfg (ST_PI_TASKCFGID, DESCRIPTION, ORGID, SITEID, HASLD, NO, BEGIN_TIME, END_TIME, PROFESSIONAL, CYCLE, CYCLE_UNIT, TYPE, ST_PI_POSTID, IS_ACTIVITY, ST_PI_TASKCFGNUM, VERSION, CYCLE_TYPE, CYCLE_SHIFT, CREATEDATE, CREATEBY, PERMITDATE, INTERVAL, POSTNO, DATAFORMATNO, CYCLE_UNIT_XJ, IS_ENABLE, ST_PI_DUTYINFONUM) "
				+ "values (?, ?, ?, ?, 0, ?, ?, ?, ?, ?, ?, ?, null, 0, ?, 1, null, null, sysdate, ?, null, null, ?, ?, null, 0, ?)";
		String sql3 = "select areaname,rfid_code from st_pi_importdata where taskcfgname = ? and  professional = ? and timestamp = ? and type = ? and siteid = ? and orgid = ? group by areaname,rfid_code";
		String sql4 = "insert into st_pi_area (ST_PI_AREAID, DESCRIPTION, ORGID, SITEID, HASLD, NO, IS_BATCH, SEQ, PROFESSIONAL, RFID_NO, RFID_CODE, TYPE, ST_PI_AREANUM, CREATEBY, CREATEDATE) "
				+ "values (?, ?, ?, ?, 0, ?, 0, null, ?, null, ?, ?, ?, ?, sysdate)";
		String sql5 = "select st_pi_importdataid,DEVICE_NO,DEVICE_NAME,LOCATION,DEVICE_PART_NAME,Checkproject,CHECK_METHOD,ON_OFF_POTIN,POINT_UNIT,HIGHER_LIMIT,LOWER_LIMIT,SHAKE_TYPE,POINT_NORM,ISPCHECK "
				+ "from st_pi_importdata where timestamp = ? and areaname = ? and siteid = ? and PROFESSIONAL = ? and RFID_CODE = ? and TYPE = ?";
		String sql6 = "insert into st_pi_item (ST_PI_ITEMID, DESCRIPTION, ORGID, SITEID, HASLD, NO, LOCATION, DEVICE_NO, DEVICE_NAME, DEVICE_PART_NAME, POINT_TYPE, POINT_UNIT, HIGHER_LIMIT, LOWER_LIMIT, CHECK_METHOD, SHAKE_TYPE, ON_OFF_POTIN, POINT_NORM, TYPE, ST_PI_ITEMNUM, ISPCHECK, IS_ENABLE, CREATEBY, CREATEDATE) "
				+ "select ?, ?, ORGID, SITEID, 0, ?, LOCATION, DEVICE_NO, DEVICE_NAME, DEVICE_PART_NAME, ?, POINT_UNIT, HIGHER_LIMIT, LOWER_LIMIT, CHECK_METHOD, SHAKE_TYPE, ON_OFF_POTIN, POINT_NORM, TYPE, ?, ISPCHECK, 1, ?, sysdate from st_pi_importdata where st_pi_importdataid = ?";
		String sql8 = "insert into ST_PI_AREA_ITEM (ST_PI_AREA_ITEMID, DESCRIPTION, ORGID, SITEID, HASLD, ST_PI_AREAID, ST_PI_ITEMID, SEQ, TYPE, ST_PI_AREA_ITEMNUM) "
				+ "values (St_Pi_Area_Itemidseq.Nextval, null, ?, ?, 0, ?, ?, ?, ?, ?)";
		String sql9 = "insert into ST_PI_TASKCFG_AREA (ST_PI_TASKCFG_AREAID, DESCRIPTION, ORGID, SITEID, HASLD, ST_PI_AREAID, ST_PI_TASKCFGID, SEQ, TYPE) "
				+ "values (?, null, ?, ?, 0, ?, ?, ?, ?)";

		String sql10 = "insert into ST_PI_TASKCFG_ITEM (ST_PI_TASKCFG_ITEMID, DESCRIPTION, ORGID, SITEID, HASLD, ST_PI_TASKCFGID, SEQ, TYPE, ST_PI_ITEMID, ST_PI_TASKCFG_AREAID) "
				+ "values (St_Pi_Taskcfg_Itemidseq.Nextval, null, ?, ?, 0, ?, ?, ?, ?, ?)";

		String sql13 = "insert into ST_PI_DAYCYCLE (ST_PI_DAYCYCLEID, DESCRIPTION, ORGID, SITEID, HASLD, ST_PI_TASKCFGID, BEGIN_TIME, END_TIME, COUNT) "
				+ "values (St_Pi_Daycycleidseq.Nextval, null, ?, ?, 0, ?, to_date(?, 'yyyy-mm-dd hh24:mi:ss'), to_date(?, 'yyyy-mm-dd hh24:mi:ss'), 1)";
		MaximoUtils maximoUtils = new MaximoUtils();
		Connection connection = maximoUtils.getMaximoConn();
		try {
			List<Map<String, Object>> lineList = maximoUtils.findModeResult(sql1, null);
			for (Map<String, Object> lineMap : lineList) {
				String taskcfgname = (String) lineMap.get("TASKCFGNAME");
				String professional = (String) lineMap.get("PROFESSIONAL");
				String type = (String) lineMap.get("TYPE");
				String siteid = (String) lineMap.get("SITEID");
				String orgid = (String) lineMap.get("ORGID");

				List<Object> sLineParams = new ArrayList<Object>();
				sLineParams.add(timestamp);
				sLineParams.add(taskcfgname);
				sLineParams.add(professional);
				sLineParams.add(type);
				sLineParams.add(siteid);
				sLineParams.add(orgid);
				String personid = "MAXADMIN";
				Map<String, Object> lineFieldMap = maximoUtils.findSimpleResult(sql12, sLineParams);

				// 查询ST_PI_TASKCFGNUM
				String st_pi_taskcfgnum = maximoUtils.getNextNumber("ST_PI_TASKCFGNUM");
				// 查询ST_PI_TASKCFGID
				long st_pi_taskcfgid = maximoUtils.getNextSequence("ST_PI_TASKCFGIDSEQ");
				// 查询编号
				String cfgNo = getNextTaskCfgNo(connection, siteid, type);
				List<Object> taskcfgParams = new ArrayList<Object>();
				taskcfgParams.add(st_pi_taskcfgid);
				taskcfgParams.add(taskcfgname);
				taskcfgParams.add(orgid);
				taskcfgParams.add(siteid);
				taskcfgParams.add(cfgNo);
				taskcfgParams.add(lineFieldMap.get("BEGIN_TIME"));
				taskcfgParams.add(lineFieldMap.get("END_TIME"));
				taskcfgParams.add(professional);
				taskcfgParams.add(lineFieldMap.get("CYCLE"));
				taskcfgParams.add(lineFieldMap.get("CYCLE_UNIT"));
				taskcfgParams.add(type);
				taskcfgParams.add(st_pi_taskcfgnum);
				taskcfgParams.add(personid);
				taskcfgParams.add(lineFieldMap.get("POSTNO"));
				taskcfgParams.add(lineFieldMap.get("DATAFORMATNO"));
				taskcfgParams.add(lineFieldMap.get("ST_PI_DUTYINFONUM"));
				maximoUtils.updateByPreparedStatement(sql2, taskcfgParams);

				if ("巡检".equals(type)) {
					String daycycle = (String) lineFieldMap.get("DAYCYCLE");
					if (daycycle != null && !"".equals(daycycle)) {
						String[] timeslot = daycycle.split(",");
						for (int i = 0; i < timeslot.length; i++) {
							String[] times = timeslot[i].split("\\|");
							String startTime = "1970-1-1 " + times[0];
							String endTime = "1970-1-1 " + times[1];
							// 插入每日周期
							List<Object> iDayCycleParams = new ArrayList<Object>();
							iDayCycleParams.add(orgid);
							iDayCycleParams.add(siteid);
							iDayCycleParams.add(st_pi_taskcfgid);
							iDayCycleParams.add(startTime);
							iDayCycleParams.add(endTime);
							maximoUtils.updateByPreparedStatement(sql13, iDayCycleParams);
						}
					}

				}

				// 分组查询区域
				List<Object> sAreaParams = new ArrayList<Object>();
				sAreaParams.add(taskcfgname);
				sAreaParams.add(professional);
				sAreaParams.add(timestamp);
				sAreaParams.add(type);
				sAreaParams.add(siteid);
				sAreaParams.add(orgid);
				List<Map<String, Object>> areaList = maximoUtils.findModeResult(sql3, sAreaParams);
				int areaSeq = 0;
				for (Map<String, Object> areaMap : areaList) {
					areaSeq = areaSeq + 5;
					String areaname = (String) areaMap.get("AREANAME");
					String rfid_code = (String) areaMap.get("RFID_CODE");
					String sql = "select st_pi_areaid from st_pi_area where description = '" + areaname
							+ "' and siteid = '" + siteid + "' and PROFESSIONAL = '" + professional
							+ "' and RFID_CODE = '" + rfid_code + "' and TYPE = '" + type + "'";
					// 判断区域是否存在
					long st_pi_areaid = getFirstId(connection, sql);
					boolean areaFlag = st_pi_areaid == 0;
					if (areaFlag) {
						// 查询st_pi_areanum
						String st_pi_areanum = maximoUtils.getNextNumber("ST_PI_AREANUM");
						// 查询st_pi_areaid
						st_pi_areaid = maximoUtils.getNextSequence("ST_PI_AREAIDSEQ");
						// 查询编号
						String areaNo = getNextAreaNo(connection, siteid, type);
						List<Object> iAreaParams = new ArrayList<Object>();
						iAreaParams.add(st_pi_areaid);
						iAreaParams.add(areaname);
						iAreaParams.add(orgid);
						iAreaParams.add(siteid);
						iAreaParams.add(areaNo);
						iAreaParams.add(professional);
						iAreaParams.add(rfid_code);
						iAreaParams.add(type);
						iAreaParams.add(st_pi_areanum);
						iAreaParams.add(personid);
						maximoUtils.updateByPreparedStatement(sql4, iAreaParams);
					}
					// 插入ST_PI_TASKCFG_AREA表
					// 查询st_pi_taskcfg_areaid
					long st_pi_taskcfg_areaid = maximoUtils.getNextSequence("ST_PI_TASKCFG_ITEMIDSEQ");
					List<Object> iCfgAreaParams = new ArrayList<Object>();
					iCfgAreaParams.add(st_pi_taskcfg_areaid);
					iCfgAreaParams.add(orgid);
					iCfgAreaParams.add(siteid);
					iCfgAreaParams.add(st_pi_areaid);
					iCfgAreaParams.add(st_pi_taskcfgid);
					iCfgAreaParams.add(areaSeq);
					iCfgAreaParams.add(type);

					maximoUtils.updateByPreparedStatement(sql9, iCfgAreaParams);

					// 查询区域下面的项目
					List<Object> sItemParams = new ArrayList<Object>();
					sItemParams.add(timestamp);
					sItemParams.add(areaname);
					sItemParams.add(siteid);
					sItemParams.add(professional);
					sItemParams.add(rfid_code);
					sItemParams.add(type);
					List<Map<String, Object>> itemList = maximoUtils.findModeResult(sql5, sItemParams);
					int itemSeq = 0;
					for (Map<String, Object> itemMap : itemList) {
						itemSeq = itemSeq + 5;
						String device_no = (String) itemMap.get("DEVICE_NO");
						String device_part_name = (String) itemMap.get("DEVICE_PART_NAME");
						String check_method = (String) itemMap.get("CHECK_METHOD");
						String on_off_potin = (String) itemMap.get("ON_OFF_POTIN");
						String sql7 = "";
						if ("测振".equals(check_method)) {
							String shake_type = (String) itemMap.get("SHAKE_TYPE");
							sql7 = "select st_pi_itemid from st_pi_item where DEVICE_NO = '" + device_no
									+ "' and DEVICE_PART_NAME = '" + device_part_name + "' and CHECK_METHOD = '"
									+ check_method + "' and SHAKE_TYPE = '" + shake_type + "' and ON_OFF_POTIN = '"
									+ on_off_potin + "'";
						} else {
							sql7 = "select st_pi_itemid from st_pi_item where DEVICE_NO = '" + device_no
									+ "' and DEVICE_PART_NAME = '" + device_part_name + "' and CHECK_METHOD = '"
									+ check_method + "' and ON_OFF_POTIN = '" + on_off_potin + "'";
						}
						// 判断项目是否存在
						long st_pi_itemid = getFirstId(connection, sql7);
						boolean itemFlag = st_pi_itemid == 0;
						if (itemFlag) {
							String device_name = (String) itemMap.get("DEVICE_NAME");
							String checkproject = (String) itemMap.get("CHECKPROJECT");
							String point_type = (String) itemMap.get("point_type");

							String itemName = device_name + "_" + device_part_name + "_" + checkproject;
							// 查询st_pi_itemnum
							String st_pi_itemnum = maximoUtils.getNextNumber("ST_PI_ITEMNUM");
							// 查询st_pi_itemid
							st_pi_itemid = maximoUtils.getNextSequence("ST_PI_ITEMIDSEQ");
							// 查询编号
							String itemNo = getNextItemNo(connection, siteid, type);
							List<Object> iItemParams = new ArrayList<Object>();
							iItemParams.add(st_pi_itemid);
							iItemParams.add(itemName);
							iItemParams.add(itemNo);
							iItemParams.add(point_type);
							iItemParams.add(st_pi_itemnum);
							iItemParams.add(personid);
							iItemParams.add(itemMap.get("ST_PI_IMPORTDATAID"));
							maximoUtils.updateByPreparedStatement(sql6, iItemParams);
						}
						boolean aiFlag = false;
						if (itemFlag == true || areaFlag == true) {
							aiFlag = true;
						} else {
							// 判断是否已经建立关联
							String sql11 = "select ST_PI_AREA_ITEMID from ST_PI_AREA_ITEM where st_pi_areaid = '"
									+ st_pi_areaid + "' and st_pi_itemid = '" + st_pi_itemid + "'";
							if (!maximoUtils.isHasRecord(sql11)) {
								// 如果没有建立关联
								aiFlag = true;
							}
						}
						if (aiFlag) {
							// 插入ST_PI_AREA_ITEM表
							// 查询ST_PI_AREA_ITEMNUM
							String st_pi_area_itemnum = maximoUtils.getNextNumber("ST_PI_AREA_ITEMNUM");
							List<Object> iAreaItemParams = new ArrayList<Object>();
							iAreaItemParams.add(orgid);
							iAreaItemParams.add(siteid);
							iAreaItemParams.add(st_pi_areaid);
							iAreaItemParams.add(st_pi_itemid);
							iAreaItemParams.add(itemSeq);
							iAreaItemParams.add(type);
							iAreaItemParams.add(st_pi_area_itemnum);
							maximoUtils.updateByPreparedStatement(sql8, iAreaItemParams);
						}

						// 插入ST_PI_TASKCFG_ITEM表
						List<Object> iCfgItemParams = new ArrayList<Object>();
						iCfgItemParams.add(orgid);
						iCfgItemParams.add(siteid);
						iCfgItemParams.add(st_pi_taskcfgid);
						iCfgItemParams.add(itemSeq);
						iCfgItemParams.add(type);
						iCfgItemParams.add(st_pi_itemid);
						iCfgItemParams.add(st_pi_taskcfg_areaid);
						maximoUtils.updateByPreparedStatement(sql10, iCfgItemParams);
					}

				}

			}
			maximoUtils.commit();
			flag = true;
		} catch (SQLException e) {
			maximoUtils.rollback();
			e.printStackTrace();
		} finally {
			maximoUtils.setAutoCommit(true);
			maximoUtils.closePs();
		}

		return flag;
	}

	/**
	 * 获取任务配置编号
	 * 
	 * @param connection
	 * @param siteid
	 * @param type
	 * @return
	 */
	private static String getNextTaskCfgNo(Connection connection, String siteid, String type) {
		String no = "";
		int number = 1;
		String getSql = "select no from ST_PI_TASKCFG where no = (select max(no) from ST_PI_TASKCFG where type = '"
				+ type + "'  and siteid = '" + siteid + "')";
		Statement st = null;
		ResultSet rs = null;
		try {
			st = connection.createStatement();
			rs = st.executeQuery(getSql);
			if (rs.next()) {
				no = rs.getString(1);
				number = Integer.valueOf(no.substring(3));
				++number;
			}
			DecimalFormat decimalFormat = new DecimalFormat("000");
			String tmp = decimalFormat.format(number);
			String qz = "";
			if ("点检".equals(type)) {
				qz = "DPZ";
			} else {
				qz = "XPZ";
			}
			no = qz + tmp;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return no;
	}

	/**
	 * 获取区域编号
	 * 
	 * @param connection
	 * @param siteid
	 * @param type
	 * @return
	 */
	private static String getNextAreaNo(Connection connection, String siteid, String type) {
		String no = "";
		int number = 1;
		String getSql = "select no from ST_PI_AREA where no = (select max(no) from ST_PI_AREA where type = '" + type
				+ "'  and siteid = '" + siteid + "')";
		Statement st = null;
		ResultSet rs = null;
		try {
			st = connection.createStatement();
			rs = st.executeQuery(getSql);
			if (rs.next()) {
				no = rs.getString(1);
				number = Integer.valueOf(no.substring(3));
				++number;
			}
			DecimalFormat decimalFormat = new DecimalFormat("0000");
			String tmp = decimalFormat.format(number);
			String qz = "";
			if ("点检".equals(type)) {
				qz = "DQY";
			} else {
				qz = "XQY";
			}
			no = qz + tmp;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return no;
	}

	/**
	 * 获取项目编号
	 * 
	 * @param connection
	 * @param siteid
	 * @param type
	 * @return
	 */
	private static String getNextItemNo(Connection connection, String siteid, String type) {
		String no = "";
		int number = 1;
		String getSql = "select no from ST_PI_ITEM where no = (select max(no) from ST_PI_ITEM where type = '" + type
				+ "'  and siteid = '" + siteid + "')";
		Statement st = null;
		ResultSet rs = null;
		try {
			st = connection.createStatement();
			rs = st.executeQuery(getSql);
			if (rs.next()) {
				no = rs.getString(1);
				number = Integer.valueOf(no.substring(3));
				++number;
			}
			DecimalFormat decimalFormat = new DecimalFormat("0000");
			String tmp = decimalFormat.format(number);
			String qz = "";
			if ("点检".equals(type)) {
				qz = "DXM";
			} else {
				qz = "XXM";
			}
			no = qz + tmp;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return no;
	}

	/**
	 * 得到测点类型
	 * 
	 * @param check_method
	 * @return
	 */
	private static String getPoint_type(String check_method) {
		String point_type = "";
		if ("测温".equals(check_method)) {
			point_type = "CW";
		} else if ("测振".equals(check_method)) {
			point_type = "CZ";
		} else if ("观察".equals(check_method)) {
			point_type = "GC";
		} else if ("记录".equals(check_method)) {
			point_type = "JL";
		}
		return point_type;
	}

	/**
	 * 获取记录唯一ID
	 * 
	 * @param connection
	 * @param siteid
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	private static long getFirstId(Connection connection, String sql) throws SQLException {
		Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs;
		long id = 0;
		rs = st.executeQuery(sql);
		if (rs.next()) {
			id = rs.getLong(1);
		}
		rs.close();
		st.close();
		return id;
	}
}
