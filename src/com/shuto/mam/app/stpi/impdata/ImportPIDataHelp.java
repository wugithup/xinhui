package com.shuto.mam.app.stpi.impdata;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.shuto.mam.app.stpi.impdata.entity.ImportDataEntity;
import com.shuto.mam.app.stpi.impdata.entity.PIAreaEntity;
import com.shuto.mam.app.stpi.impdata.entity.PIItemEntity;
import com.shuto.mam.app.stpi.impdata.entity.PITaskCfgEntity;

import cc.aicode.e2e.ExcelHelper;
import cc.aicode.e2e.exception.ExcelContentInvalidException;
import cc.aicode.e2e.exception.ExcelParseException;
import cc.aicode.e2e.exception.ExcelRegexpValidFailedException;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

/**
 * 
 * @author shixw
 * @date 2017年5月15日
 * @use 数据导入工具类
 */
public class ImportPIDataHelp {

	private static MXLogger log = MXLoggerFactory.getLogger("maximo.stpi.importdata");

	private  PreparedStatement st_pi_area_ps = null;
	private  PreparedStatement st_pi_taskcfg_area_ps = null;
	private  PreparedStatement st_pi_area_select_ps = null;
	private  PreparedStatement st_pi_daycycle_ps = null;
	
	private  ResultSet st_pi_area_select_rs = null;
	
	private  PreparedStatement st_pi_item_select_ps = null;
	private  ResultSet st_pi_item_select_rs = null;
	
	private  PreparedStatement st_pi_item_ps = null;
	private  PreparedStatement st_pi_area_item_ps = null;
	private  PreparedStatement st_pi_taskcfg_item_ps = null;
	

	private static final String RUNASPERSON = "MAXADMIN";

	/**
	 * 解析传入的Excel
	 * 
	 * @return 返回解析完成的任务配置的实体的List 使用IO流操作
	 * @param ins
	 * @throws IOException
	 * @throws InvalidFormatException
	 * @throws ExcelRegexpValidFailedException
	 * @throws ExcelContentInvalidException
	 * @throws ExcelParseException
	 */
	private List<PITaskCfgEntity> analysisExcelFile(InputStream ins) throws InvalidFormatException, IOException,
			ExcelParseException, ExcelContentInvalidException, ExcelRegexpValidFailedException {
		// 解析Excel
		log.info("----开始解析Execl-----");
		ExcelHelper eh = ExcelHelper.readExcel(ins);

		return importDataToTaskCfg(eh.toEntitys(ImportDataEntity.class));
	}
	// /**
	// * 解析传入的Excel
	// *
	// * @return 返回解析完成的任务配置的实体的List
	// * @param url
	// * @throws IOException
	// * @throws InvalidFormatException
	// * @throws ExcelRegexpValidFailedException
	// * @throws ExcelContentInvalidException
	// * @throws ExcelParseException
	// */
	// private static List<PITaskCfgEntity> analysisExcelFile(String fileUrl)
	// throws InvalidFormatException, IOException,
	// ExcelParseException, ExcelContentInvalidException,
	// ExcelRegexpValidFailedException {
	// // 解析Excel
	// log.info("----开始解析Execl-----");
	// ExcelHelper eh = ExcelHelper.readExcel(fileUrl);
	//
	// return importDataToTaskCfg(eh.toEntitys(ImportDataEntity.class));
	// }

	/**
	 * 解析后的实体类转换
	 * 
	 * @param importDatas
	 * @return
	 */
	private List<PITaskCfgEntity> importDataToTaskCfg(List<ImportDataEntity> importDatas) {
		if (importDatas != null && !importDatas.isEmpty()) {
			List<PITaskCfgEntity> piTaskCfgEntities = new ArrayList<PITaskCfgEntity>();
			
			// 遍历Excel解析后映射的实体类集合 中的每一行数据
			for (ImportDataEntity importDataEntity : importDatas) {
				System.out.println("===============importDataEntity.getDeviceseq() = " +importDataEntity.getDeviceseq());
				//// 将任务配置信息添加到集合中 并返回对象
				PITaskCfgEntity piTaskCfg = addTaskCfgToList(new PITaskCfgEntity(importDataEntity.getProfessional(),
						importDataEntity.getType(), importDataEntity.getTaskcfgName(), importDataEntity.getSiteid(),
						importDataEntity.getOrgid(), importDataEntity.getStartDate(), importDataEntity.getEndDate(),
						importDataEntity.getCycleUnit(), importDataEntity.getCycle(), importDataEntity.getPostNo(),
						importDataEntity.getDataFormatNo(),importDataEntity.getDutyinfoNum(),importDataEntity.getDayCycle(),importDataEntity.getDeviceseq()), piTaskCfgEntities);

				// 添加任务对应的区域信息
				PIAreaEntity areaEntity = piTaskCfg
						.addPiArea(new PIAreaEntity(importDataEntity.getAreaName(), importDataEntity.getRfidCode(), importDataEntity.getAreaseq()));

				// 添加区域对应的巡点检项目的信息
				areaEntity.getPiItemEntities()
						.add(new PIItemEntity(importDataEntity.getCheckProject(), importDataEntity.getDeviceName(),
								importDataEntity.getDevicePartName(), importDataEntity.getPointType(),
								importDataEntity.getPointUnit(), importDataEntity.getHigherLimit(),
								importDataEntity.getLowerLimit(), importDataEntity.getCheckMethod(),
								importDataEntity.getShakeType(), importDataEntity.getOnOffPotin(),
								importDataEntity.getPointNorm(), importDataEntity.getIsPcheck(),
								importDataEntity.getType(),importDataEntity.getLocation(),importDataEntity.getDeviceNo(),importDataEntity.getAreaseq(),importDataEntity.getItemseq(),importDataEntity.getDeviceseq()));

			}

			return piTaskCfgEntities;
		}
		return null;
	}

	/**
	 * 将任务配置对象添加到 集合中,如果对象已存在则直接返回已存在对象
	 * 
	 * @param piTaskCfgEntity
	 * @param list
	 * @return
	 */
	private PITaskCfgEntity addTaskCfgToList(PITaskCfgEntity piTaskCfgEntity, List<PITaskCfgEntity> list) {
		// 判断是否存在当前对象
		int index = list.lastIndexOf(piTaskCfgEntity);
		if (index != -1) {// 如果存在则获取已存在的任务配置
			piTaskCfgEntity = list.get(index);
		} else {
			list.add(piTaskCfgEntity);
		}
		return piTaskCfgEntity;
	}

	/**
	 * 将Excel解析并将数据导入到数据库中
	 * 
	 * @param ins
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 * @throws ExcelParseException
	 * @throws ExcelContentInvalidException
	 * @throws ExcelRegexpValidFailedException
	 * @throws SQLException
	 */
	public boolean analysisExcelFileAndImportDataToDB(InputStream ins)
			throws InvalidFormatException, IOException, ExcelParseException, ExcelContentInvalidException,
			ExcelRegexpValidFailedException, SQLException {
		System.out.println("====ImportPIDataHelp.start=====");
		return importDataToDB(analysisExcelFile(ins));
	}

	/**
	 * 将解析完成的数据导入到数据库
	 * 
	 * @param piTaskCfgEntities
	 * @return
	 * @throws SQLException
	 */
	private boolean importDataToDB(List<PITaskCfgEntity> piTaskCfgEntities) {
		boolean flg = true;
		Connection conn = null;
		PreparedStatement st_pi_taskcfg_ps = null;
		try {
			log.info("数据解析完成，开始将数据导入数据库!");
			if (piTaskCfgEntities != null && !piTaskCfgEntities.isEmpty()) {
				log.info("总共需要导入的任务配置条数为：" + piTaskCfgEntities.size());
				conn = ImpDataUtil.getMaximoDBConnection();
				// 任务配置插入SQL
				String st_pi_taskcfg_sql = "insert into st_pi_taskcfg (ST_PI_TASKCFGID, DESCRIPTION, ORGID, SITEID, HASLD, NO, BEGIN_TIME, END_TIME, PROFESSIONAL, CYCLE, CYCLE_UNIT, TYPE, ST_PI_POSTID, IS_ACTIVITY, ST_PI_TASKCFGNUM, VERSION, CYCLE_TYPE, CYCLE_SHIFT, CREATEDATE, CREATEBY, PERMITDATE, INTERVAL, POSTNO, DATAFORMATNO, CYCLE_UNIT_XJ, IS_ENABLE, ST_PI_DUTYINFONUM) "
						+ "values (?, ?, ?, ?, 0, ?, ?, ?, ?, ?, ?, ?, null, 0, ?, 1, null, null, sysdate, ?, null, null, ?, ?, null, 0, ?)";
				st_pi_taskcfg_ps = conn.prepareStatement(st_pi_taskcfg_sql);
				for (int i = 0; i < piTaskCfgEntities.size(); i++) {
					PITaskCfgEntity piTaskCfgEntity = piTaskCfgEntities.get(i);
					System.out.println("开始导入第 " + (i + 1) + " 条任务，任务名称为：" + piTaskCfgEntity.getDescription());
					flg = importPITaskCfg(conn, st_pi_taskcfg_ps, piTaskCfgEntity);
					st_pi_taskcfg_ps.executeBatch();
					conn.commit();
					System.out.println("第 " + (i + 1) + " 条任务导入成功，任务名称为：" + piTaskCfgEntity.getDescription());
				}
			}
		} catch (SQLException e) {
			flg = false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (st_pi_taskcfg_ps != null)
					st_pi_taskcfg_ps.close();
				if (st_pi_area_ps != null)
					st_pi_area_ps.close();
				if (st_pi_taskcfg_area_ps != null)
					st_pi_taskcfg_area_ps.close();
				if (st_pi_area_select_ps != null)
					st_pi_area_select_ps.close();
				if (st_pi_area_select_rs != null)
					st_pi_area_select_rs.close();
				if (st_pi_item_ps != null)
					st_pi_item_ps.close();
				if(st_pi_area_item_ps!=null)
					st_pi_area_item_ps.close();
				if(st_pi_taskcfg_item_ps!=null)
					st_pi_taskcfg_item_ps.close();
				if (st_pi_item_select_ps != null)
					st_pi_item_select_ps.close();
				if (st_pi_item_select_rs != null)
					st_pi_item_select_rs.close();
				if(st_pi_daycycle_ps!=null){
					st_pi_daycycle_ps.close();
				}
				log.info("关闭数据库连接及相关资源！");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flg;
	}

	private boolean importPITaskCfg(Connection conn, PreparedStatement st_pi_taskcfg_ps,
			PITaskCfgEntity piTaskCfgEntity) throws SQLException {
		boolean flg = true;
		// 获取任务配置唯一编号
		long st_pi_taskcfgid = ImpDataUtil.getNextSequence(conn, "ST_PI_TASKCFGIDSEQ");
		// 查询任务配置的num編号
		String st_pi_taskcfgnum = ImpDataUtil.getNextNumber(conn, "ST_PI_TASKCFGNUM");

		st_pi_taskcfg_ps.setLong(1, st_pi_taskcfgid);
		st_pi_taskcfg_ps.setString(2, piTaskCfgEntity.getDescription());
		st_pi_taskcfg_ps.setString(3, piTaskCfgEntity.getOrgid());
		st_pi_taskcfg_ps.setString(4, piTaskCfgEntity.getSiteid());
		st_pi_taskcfg_ps.setString(5, st_pi_taskcfgnum);
		st_pi_taskcfg_ps.setDate(6, new java.sql.Date(piTaskCfgEntity.getBeginTime().getTime()));
		st_pi_taskcfg_ps.setDate(7, new java.sql.Date(piTaskCfgEntity.getEndTime().getTime()));
		st_pi_taskcfg_ps.setString(8, piTaskCfgEntity.getProfessional());
		st_pi_taskcfg_ps.setInt(9, piTaskCfgEntity.getCycle());
		st_pi_taskcfg_ps.setString(10, piTaskCfgEntity.getCycleUnit());
		st_pi_taskcfg_ps.setString(11, piTaskCfgEntity.getType());
		st_pi_taskcfg_ps.setString(12, st_pi_taskcfgnum);
		st_pi_taskcfg_ps.setString(13, RUNASPERSON);
		st_pi_taskcfg_ps.setString(14, piTaskCfgEntity.getPostNo());
		st_pi_taskcfg_ps.setString(15, piTaskCfgEntity.getDataformatNo());
		if ("巡检".equals(piTaskCfgEntity.getType())) {
			st_pi_taskcfg_ps.setString(16, piTaskCfgEntity.getDutyInfoNo());
		} else {
			st_pi_taskcfg_ps.setString(16, "");
		}
		st_pi_taskcfg_ps.addBatch();
		// 处理当前任务配置下的区域
		List<PIAreaEntity> piAreaEntities = piTaskCfgEntity.getPiAreaEntities();
		log.info("--导入当前任务配置对应的区域，总共包括  " + piAreaEntities.size() + " 个区域");
		// 导入区域
		flg = importPIArea(conn, piAreaEntities, piTaskCfgEntity, st_pi_taskcfgid);
		//如果是巡检，需要导入任务时间周期配置信息
		if ("巡检".equals(piTaskCfgEntity.getType())) {
			log.info("--导入当前巡检任务配置对应的时间周期表：  " + piTaskCfgEntity.getDayCycle());
			importPiDayCycle(conn,piTaskCfgEntity,st_pi_taskcfgid);
		}
		return flg;
	}

	/**
	 * 巡检导入任务配置时间表
	 * @param conn
	 * @param piTaskCfgEntity
	 * @param st_pi_taskcfgid
	 * @throws SQLException 
	 */
	private void importPiDayCycle(Connection conn, PITaskCfgEntity piTaskCfgEntity, long st_pi_taskcfgid) throws SQLException {
		String daycycle = piTaskCfgEntity.getDayCycle();
		if (daycycle != null && !"".equals(daycycle)) {
			if(st_pi_daycycle_ps==null){
				String sql = "insert into ST_PI_DAYCYCLE (ST_PI_DAYCYCLEID, DESCRIPTION, ORGID, SITEID, HASLD, ST_PI_TASKCFGID, BEGIN_TIME, END_TIME, COUNT) "
						+ "values (St_Pi_Daycycleidseq.Nextval, null, ?, ?, 0, ?, to_date(?, 'yyyy-mm-dd hh24:mi:ss'), to_date(?, 'yyyy-mm-dd hh24:mi:ss'), 1)";
			
				st_pi_daycycle_ps = conn.prepareStatement(sql);
			}
			String[] timeslot = daycycle.split(",");
			for (int i = 0; i < timeslot.length; i++) {
				String[] times = timeslot[i].split("\\|");
				String startTime = "1970-1-1 " + times[0];
				String endTime = "1970-1-1 " + times[1];
				st_pi_daycycle_ps.setString(1, piTaskCfgEntity.getOrgid());
				st_pi_daycycle_ps.setString(2, piTaskCfgEntity.getSiteid());
				st_pi_daycycle_ps.setLong(3, st_pi_taskcfgid);
				st_pi_daycycle_ps.setString(4, startTime);
				st_pi_daycycle_ps.setString(5, endTime);
				st_pi_daycycle_ps.addBatch();
			}
			st_pi_daycycle_ps.executeBatch();
		}
	}
	/**
	 * 导入区域信息
	 * 
	 * @param piAreaEntity
	 * @return
	 */
	private boolean importPIArea(Connection conn, List<PIAreaEntity> piAreaEntities,
			PITaskCfgEntity piTaskCfgEntity, long st_pi_taskcfgid) throws SQLException {
		boolean flg = true;
		if (piAreaEntities != null && !piAreaEntities.isEmpty()) {
			String st_pi_area_sql = "insert into st_pi_area (ST_PI_AREAID, DESCRIPTION, ORGID, SITEID, HASLD, NO, IS_BATCH, SEQ, PROFESSIONAL, RFID_NO, RFID_CODE, TYPE, ST_PI_AREANUM, CREATEBY, CREATEDATE) "
					+ "values (?, ?, ?, ?, 0, ?, 0, null, ?, null, ?, ?, ?, ?, sysdate)";
			if (st_pi_area_ps == null) {
				st_pi_area_ps = conn.prepareStatement(st_pi_area_sql);
			}
			// 遍历区域
			for (PIAreaEntity piAreaEntity : piAreaEntities) {
				log.info("导入区域，区域名称：" + piAreaEntity.getDescription());
				// 判断当前区域是否已经存在
				long pi_area_id = isHaveArea(conn, piAreaEntity, piTaskCfgEntity);
				if (pi_area_id == -1) {// 如果区域不存在,获取新的ID，并插入新记录
					pi_area_id = ImpDataUtil.getNextSequence(conn, "ST_PI_AREAIDSEQ");
					// 查询st_pi_areanum
					String st_pi_areanum = ImpDataUtil.getNextNumber(conn, "ST_PI_AREANUM");
					st_pi_area_ps.setLong(1, pi_area_id);
					st_pi_area_ps.setString(2, piAreaEntity.getDescription());
					st_pi_area_ps.setString(3, piTaskCfgEntity.getOrgid());
					st_pi_area_ps.setString(4, piTaskCfgEntity.getSiteid());
					st_pi_area_ps.setString(5, st_pi_areanum);
					st_pi_area_ps.setString(6, piTaskCfgEntity.getProfessional());
					st_pi_area_ps.setString(7, piAreaEntity.getRfidCode());
					st_pi_area_ps.setString(8, piTaskCfgEntity.getType());
					st_pi_area_ps.setString(9, st_pi_areanum);
					st_pi_area_ps.setString(10, RUNASPERSON);

					st_pi_area_ps.addBatch();
				}
				// 插入任务配置关联的区域表
				long st_pi_taskcfg_areaid = importPITaskcfgArea(conn, pi_area_id, piAreaEntity, piTaskCfgEntity, st_pi_taskcfgid);
				// 插入区域对应的巡点检项目
				flg = importPIItem(conn, pi_area_id,st_pi_taskcfg_areaid, piAreaEntity, piTaskCfgEntity, st_pi_taskcfgid);
			}
			// 执行批处理
			st_pi_area_ps.executeBatch();
			st_pi_taskcfg_area_ps.executeBatch();
		}
		return flg;
	}

	/**
	 * 插入区域对应的巡点检项目
	 * 
	 * @param conn
	 * @param pi_area_id
	 * @param piAreaEntity
	 * @param piTaskCfgEntity
	 * @param st_pi_taskcfgid
	 * @return
	 * @throws SQLException
	 */
	private boolean importPIItem(Connection conn, long pi_area_id,long st_pi_taskcfg_areaid, PIAreaEntity piAreaEntity,
			PITaskCfgEntity piTaskCfgEntity, long st_pi_taskcfgid) throws SQLException {
		List<PIItemEntity> piItemEntities = piAreaEntity.getPiItemEntities();
		if (piItemEntities != null && !piItemEntities.isEmpty()) {
			log.info("插入区域 " + piAreaEntity.getDescription() + " 对应的巡点检项目，包括 " + piItemEntities.size() + " 条数据!");
			Map<String,String> deveiceMap = new HashMap<String,String>();//创建设备集合，key=设备名字 value=设备编号
			String deviceName = null;
			String st_pi_devicenum = null;
			// 遍历项目
			for (PIItemEntity piItemEntity : piItemEntities) {
				deviceName = piItemEntity.getDeviceName();
				//根据设备名称判重
				if (!deveiceMap.containsKey(deviceName)) {
					//查询设备编号
					st_pi_devicenum = ImpDataUtil.getNextNumber(conn, "ST_PI_DEVICENUM");
					deveiceMap.put(deviceName, st_pi_devicenum);
				} else {
					st_pi_devicenum = deveiceMap.get(deviceName);
				}
				
				// 判断是都已存在 巡点检项目
				long st_pi_itemid = isHaveItem(conn, piItemEntity, piTaskCfgEntity);
				if (st_pi_itemid == -1) {// 如果不存在 添加新纪录
					String st_pi_item_sql = "insert into st_pi_item (ST_PI_ITEMID, DESCRIPTION, ORGID, SITEID, HASLD, NO, LOCATION, DEVICE_NO, DEVICE_NAME, DEVICE_PART_NAME,"
							+ " POINT_TYPE, POINT_UNIT, HIGHER_LIMIT, LOWER_LIMIT, CHECK_METHOD, SHAKE_TYPE, ON_OFF_POTIN, POINT_NORM, TYPE, ST_PI_ITEMNUM, ISPCHECK, IS_ENABLE, CREATEBY, DEVICESEQ,CREATEDATE) "
							+ "values( ?, ?, ?, ?, 0, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1, ?, ?, sysdate )";
					if(st_pi_item_ps==null){
						st_pi_item_ps = conn.prepareStatement(st_pi_item_sql);
					}
					System.out.println(" importPIItem.start------- devseq = " +piItemEntity.getDeviceseq());
					// 查询st_pi_itemnum
					String st_pi_itemnum = ImpDataUtil.getNextNumber(conn, "ST_PI_ITEMNUM");
					// 查询st_pi_itemid
					st_pi_itemid = ImpDataUtil.getNextSequence(conn, "ST_PI_ITEMIDSEQ");
					
					piItemEntity.setDeviceNo(st_pi_devicenum);
					st_pi_item_ps.setLong(1, st_pi_itemid);
					st_pi_item_ps.setString(2, piItemEntity.getDescription());
					st_pi_item_ps.setString(3, piTaskCfgEntity.getOrgid());
					st_pi_item_ps.setString(4, piTaskCfgEntity.getSiteid());
					st_pi_item_ps.setString(5, st_pi_itemnum);
					st_pi_item_ps.setString(6, piItemEntity.getLocation());
					st_pi_item_ps.setString(7, piItemEntity.getDeviceNo());
					st_pi_item_ps.setString(8, piItemEntity.getDeviceName());
					st_pi_item_ps.setString(9, piItemEntity.getDevicePartName());
					st_pi_item_ps.setString(10, piItemEntity.getPointType());
					st_pi_item_ps.setString(11, piItemEntity.getPointUnit());
					st_pi_item_ps.setDouble(12, Double.valueOf((piItemEntity.getHigherLimit()!=null && !"".equals(piItemEntity.getHigherLimit()))?piItemEntity.getHigherLimit():"0.0"));
					st_pi_item_ps.setDouble(13, Double.valueOf((piItemEntity.getLowerLimit()!=null && !"".equals(piItemEntity.getLowerLimit()))?piItemEntity.getLowerLimit():"0.0"));
					st_pi_item_ps.setString(14, piItemEntity.getCheckMethod());
					st_pi_item_ps.setString(15, piItemEntity.getSharkType());
					st_pi_item_ps.setString(16, piItemEntity.getOnOffPoint());
					st_pi_item_ps.setString(17, piItemEntity.getPointNorm());
					st_pi_item_ps.setString(18, piTaskCfgEntity.getType());
					st_pi_item_ps.setString(19, st_pi_itemnum);
					st_pi_item_ps.setInt(20, piItemEntity.getIsPCheck());
					st_pi_item_ps.setString(21, RUNASPERSON);
					st_pi_item_ps.setLong(22, piItemEntity.getDeviceseq());//新加
					System.out.println("上限"+piItemEntity.getHigherLimit()+"下限"+piItemEntity.getLowerLimit());
					//添加到待执行SQL
					st_pi_item_ps.addBatch();
					
					importPIAreaItem(conn,st_pi_itemid,piItemEntity, pi_area_id, st_pi_taskcfg_areaid,  piAreaEntity ,piTaskCfgEntity);
					
					importPITaskCfgItem(conn,st_pi_itemid,piItemEntity, pi_area_id, st_pi_taskcfg_areaid,  piAreaEntity,
							 piTaskCfgEntity,  st_pi_taskcfgid);
				}

			}
			st_pi_item_ps.executeBatch();
			st_pi_area_item_ps.executeBatch();
			st_pi_taskcfg_item_ps.executeBatch();
		}
		return false;
	}

	/**
	 * 导入任务配置区域和巡点检项目的对应
	 * @param conn
	 * @param st_pi_itemid
	 * @param piItemEntity
	 * @param pi_area_id
	 * @param st_pi_taskcfg_areaid
	 * @param piAreaEntity
	 * @param piTaskCfgEntity
	 * @param st_pi_taskcfgid
	 * @throws SQLException
	 */
	private void importPITaskCfgItem(Connection conn, long st_pi_itemid, PIItemEntity piItemEntity,
			long pi_area_id, long st_pi_taskcfg_areaid, PIAreaEntity piAreaEntity, PITaskCfgEntity piTaskCfgEntity,
			long st_pi_taskcfgid) throws SQLException {
		String st_pi_taskcfg_item_sql = "insert into ST_PI_TASKCFG_ITEM (ST_PI_TASKCFG_ITEMID, DESCRIPTION, ORGID, SITEID, HASLD, ST_PI_TASKCFGID, SEQ, TYPE, ST_PI_ITEMID, ST_PI_TASKCFG_AREAID,deviceseq) "
				+ "values (St_Pi_Taskcfg_Itemidseq.Nextval, null, ?, ?, 0, ?, ?, ?, ?, ?, ?)";
		if(st_pi_taskcfg_item_ps==null){
			st_pi_taskcfg_item_ps = conn.prepareStatement(st_pi_taskcfg_item_sql);
		}
		
		st_pi_taskcfg_item_ps.setString(1, piTaskCfgEntity.getOrgid());
		st_pi_taskcfg_item_ps.setString(2, piTaskCfgEntity.getSiteid());
		st_pi_taskcfg_item_ps.setLong(3, st_pi_taskcfgid);
		st_pi_taskcfg_item_ps.setInt(4, piItemEntity.getItemseq());
		st_pi_taskcfg_item_ps.setString(5, piTaskCfgEntity.getType());
		st_pi_taskcfg_item_ps.setLong(6, st_pi_itemid);
		st_pi_taskcfg_item_ps.setLong(7, st_pi_taskcfg_areaid);
		st_pi_taskcfg_item_ps.setLong(8, piItemEntity.getDeviceseq());
		st_pi_taskcfg_item_ps.addBatch();
	}

	/**
	 * 导入区域和巡点检项目的对应
	 * @param conn
	 * @param st_pi_itemid
	 * @param piItemEntity
	 * @param pi_area_id
	 * @param st_pi_taskcfg_areaid
	 * @param piAreaEntity
	 * @throws SQLException
	 */
	private void importPIAreaItem(Connection conn, long st_pi_itemid, PIItemEntity piItemEntity, long pi_area_id,
			long st_pi_taskcfg_areaid, PIAreaEntity piAreaEntity,PITaskCfgEntity piTaskCfgEntity) throws SQLException {
		String st_pi_area_item_sql = "insert into ST_PI_AREA_ITEM (ST_PI_AREA_ITEMID, DESCRIPTION, ORGID, SITEID, HASLD, ST_PI_AREAID, ST_PI_ITEMID, SEQ, TYPE, ST_PI_AREA_ITEMNUM,DEVICESEQ) "
				+ "values (St_Pi_Area_Itemidseq.Nextval, null, ?, ?, 0, ?, ?, ?, ?, '', ?)";
		if(st_pi_area_item_ps==null){
			st_pi_area_item_ps = conn.prepareStatement(st_pi_area_item_sql);
		}
		
		System.out.println("----------------importPIAreaItem.start---- piTaskCfgEntity.getDeviceseq() = " +piItemEntity.getDeviceseq());
		
		st_pi_area_item_ps.setString(1, piTaskCfgEntity.getOrgid());
		st_pi_area_item_ps.setString(2, piTaskCfgEntity.getSiteid());
		st_pi_area_item_ps.setLong(3, pi_area_id);
		st_pi_area_item_ps.setLong(4, st_pi_itemid);
		st_pi_area_item_ps.setInt(5, piItemEntity.getItemseq());
		st_pi_area_item_ps.setString(6, piTaskCfgEntity.getType());
		st_pi_area_item_ps.setLong(7, piItemEntity.getDeviceseq());
		st_pi_area_item_ps.addBatch();
		
	}
	/**
	 * 判断巡点检项目是否已存在
	 * 
	 * @param conn
	 * @param piItemEntity
	 * @param piTaskCfgEntity
	 * @return
	 * @throws SQLException 
	 */
	private long isHaveItem(Connection conn, PIItemEntity piItemEntity, PITaskCfgEntity piTaskCfgEntity) throws SQLException {
		long st_pi_itemid = -1;
		String sql = "";
		String check_method = piItemEntity.getCheckMethod();
		if ("测振".equals(check_method)) {
			String shake_type = piItemEntity.getSharkType();
			sql = "select st_pi_itemid from st_pi_item where DEVICE_NO = '" + piItemEntity.getDeviceNo()
					+ "' and DEVICE_PART_NAME = '" + piItemEntity.getDevicePartName() + "' and CHECK_METHOD = '"
					+ check_method + "' and SHAKE_TYPE = '" + shake_type + "' and ON_OFF_POTIN = '"
					+ piItemEntity.getOnOffPoint() + "'";
		} else {
			sql = "select st_pi_itemid from st_pi_item where DEVICE_NO = '" + piItemEntity.getDeviceNo()
					+ "' and DEVICE_PART_NAME = '" + piItemEntity.getDevicePartName() + "' and CHECK_METHOD = '"
					+ check_method + "' and ON_OFF_POTIN = '" + piItemEntity.getOnOffPoint()  + "'";
		}
		if (st_pi_item_select_ps == null) {
			st_pi_item_select_ps = conn.prepareStatement(sql);
		}
		st_pi_item_select_rs = st_pi_item_select_ps.executeQuery();
		if (st_pi_item_select_rs.next()) {
			log.info("巡点检项目 " + piItemEntity.getDescription() + " 已存在，不重复导入.");
			st_pi_itemid = st_pi_item_select_rs.getLong(1);
		}
		return st_pi_itemid;
	}

	/**
	 * 导入任务配置关联的区域表
	 * 
	 * @param conn
	 * @param pi_area_id
	 * @param piAreaEntity
	 * @param piTaskCfgEntity
	 * @param st_pi_taskcfgid
	 * @param areano
	 * @return
	 * @throws SQLException
	 */
	private long importPITaskcfgArea(Connection conn, long pi_area_id, PIAreaEntity piAreaEntity,
			PITaskCfgEntity piTaskCfgEntity, long st_pi_taskcfgid) throws SQLException {
		String st_pi_taskcfg_area_sql = "insert into ST_PI_TASKCFG_AREA (ST_PI_TASKCFG_AREAID, DESCRIPTION, ORGID, SITEID, HASLD, ST_PI_AREAID, ST_PI_TASKCFGID, SEQ, TYPE) "
				+ "values (?, null, ?, ?, 0, ?, ?, ?, ?)";
		if (st_pi_taskcfg_area_ps == null) {
			st_pi_taskcfg_area_ps = conn.prepareStatement(st_pi_taskcfg_area_sql);
		}
		long st_pi_taskcfg_areaid = ImpDataUtil.getNextSequence(conn, "ST_PI_TASKCFG_ITEMIDSEQ");
		st_pi_taskcfg_area_ps.setLong(1, st_pi_taskcfg_areaid);
		st_pi_taskcfg_area_ps.setString(2, piTaskCfgEntity.getOrgid());
		st_pi_taskcfg_area_ps.setString(3, piTaskCfgEntity.getSiteid());
		st_pi_taskcfg_area_ps.setLong(4, pi_area_id);
		st_pi_taskcfg_area_ps.setLong(5, st_pi_taskcfgid);
		st_pi_taskcfg_area_ps.setInt(6, piAreaEntity.getAreaseq());
		st_pi_taskcfg_area_ps.setString(7, piTaskCfgEntity.getType());

		st_pi_taskcfg_area_ps.addBatch();

		return st_pi_taskcfg_areaid;

	}

	/**
	 * 判断数据库中是否已存在需要导入的区域
	 * 
	 * @param conn
	 * @param piAreaEntity
	 * @return
	 * @throws SQLException
	 */
	private long isHaveArea(Connection conn, PIAreaEntity piAreaEntity, PITaskCfgEntity piTaskCfgEntity)
			throws SQLException {
		long pi_area_id = -1;
		String sql = "select st_pi_areaid from st_pi_area where description = ? and siteid = ? and PROFESSIONAL = ? and RFID_CODE = ? and TYPE = ?";
		if (st_pi_area_select_ps == null) {
			st_pi_area_select_ps = conn.prepareStatement(sql);
		}
		st_pi_area_select_ps.setString(1, piAreaEntity.getDescription());
		st_pi_area_select_ps.setString(2, piTaskCfgEntity.getSiteid());
		st_pi_area_select_ps.setString(3, piTaskCfgEntity.getProfessional());
		st_pi_area_select_ps.setString(4, piAreaEntity.getRfidCode());
		st_pi_area_select_ps.setString(5, piTaskCfgEntity.getType());
		st_pi_area_select_rs = st_pi_area_select_ps.executeQuery();
		if (st_pi_area_select_rs.next()) {
			log.info("区域 " + piAreaEntity.getDescription() + " 已存在，不重复导入.");
			pi_area_id = st_pi_area_select_rs.getLong(1);
		}
		return pi_area_id;
	}

	/**
	 * 测试使用的主方法
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InvalidFormatException
	 * @throws SQLException
	 */
	public static void main(String[] args) throws InvalidFormatException, IOException, SQLException {
		try {
			Date ds = new Date();

			ImportPIDataHelp dataHelp = new ImportPIDataHelp();
			dataHelp.analysisExcelFileAndImportDataToDB(new FileInputStream("C:\\MYDOCUMENT\\WORK\\WORKSPACE\\maximo\\crpeam\\Axj02.xls"));

			Date de = new Date();

			System.out.println((de.getTime() - ds.getTime()));
		} catch (ExcelParseException e) {
			e.printStackTrace();
		} catch (ExcelContentInvalidException e) {
			e.printStackTrace();
		} catch (ExcelRegexpValidFailedException e) {
			e.printStackTrace();
		}

	}
}
