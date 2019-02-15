package com.shuto.mam.webclient.beans.stpi.pi_importdata;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.shuto.mam.crontask.stpi.dbutils.MaximoUtils;

public class ImportDB {
	public void importData(List<PiData> pidata,String timestamp){
		MaximoUtils maximoUtils = new MaximoUtils();
		String sql = "insert into st_pi_importdata (st_pi_importdataid,hasld,PROFESSIONAL,TYPE,TASKCFGNAME,AREANAME,RFID_CODE,DEVICE_NO,DEVICE_NAME,LOCATION,DEVICE_PART_NAME,Checkproject,CHECK_METHOD,ON_OFF_POTIN,POINT_UNIT,HIGHER_LIMIT,LOWER_LIMIT,SHAKE_TYPE,POINT_NORM,ISPCHECK,siteid,orgid,timestamp,postno,dataformatno,cycle_unit,cycle,begin_time,end_time) "+
			"values (st_pi_importdataidseq.nextval,0,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			maximoUtils.getMaximoConn();
			PreparedStatement piPstmt = maximoUtils.LoggableStatement(sql);
			for (PiData p : pidata) {
	            List<Object> params = new ArrayList<Object>();
				params.add(p.getProfessional());
				params.add(p.getType());
				params.add(p.getTaskcfgName());
				params.add(p.getAreaName());
				params.add(p.getRfidCode());
				params.add(p.getDeviceNo());
				params.add(p.getDeviceName());
				params.add(p.getLocation());
				params.add(p.getDevicePartName());
				params.add(p.getCheckProject());
				params.add(p.getCheckMethod());
				params.add(p.getOnOffPotin());
				params.add(p.getPointUnit());
				params.add(p.getHigherLimit());
				params.add(p.getLowerLimit());
				params.add(p.getShakeType());
				params.add(p.getPointNorm());
				params.add(p.getIsPcheck());
				params.add(p.getSiteid());
				params.add(p.getOrgid());
				params.add(timestamp);
				params.add(p.getPostNo());
				params.add(p.getDataFormatNo());
				params.add(p.getCycleUnit());
				params.add(p.getCycle());
				params.add(p.getStartDate());
				params.add(p.getEndDate());
				maximoUtils.updateBatchByPreparedStatement(piPstmt, params);
	        }
			piPstmt.executeBatch();
			maximoUtils.commit();
			maximoUtils.closePstmt(piPstmt);
			maximoUtils.closePs();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void importXjData(List<PiXjData> pidata,String timestamp){
		MaximoUtils maximoUtils = new MaximoUtils();
		String sql = "insert into st_pi_importdata (st_pi_importdataid,hasld,PROFESSIONAL,TYPE,TASKCFGNAME,AREANAME,RFID_CODE,DEVICE_NO,DEVICE_NAME,LOCATION,DEVICE_PART_NAME,Checkproject,POINT_TYPE,CHECK_METHOD,ON_OFF_POTIN,POINT_UNIT,HIGHER_LIMIT,LOWER_LIMIT,SHAKE_TYPE,POINT_NORM,ISPCHECK,siteid,orgid,timestamp,postno,dataformatno,cycle,begin_time,end_time,st_pi_dutyinfonum,daycycle) "+
			"values (st_pi_importdataidseq.nextval,0,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			maximoUtils.getMaximoConn();
			PreparedStatement piPstmt = maximoUtils.LoggableStatement(sql);
			for (PiXjData p : pidata) {
	            List<Object> params = new ArrayList<Object>();
				params.add(p.getProfessional());
				params.add(p.getType());
				params.add(p.getTaskcfgName());
				params.add(p.getAreaName());
				params.add(p.getRfidCode());
				params.add(p.getDeviceNo());
				params.add(p.getDeviceName());
				params.add(p.getLocation());
				params.add(p.getDevicePartName());
				params.add(p.getCheckProject());
				params.add(p.getPointType());
				params.add(p.getCheckMethod());
				params.add(p.getOnOffPotin());
				params.add(p.getPointUnit());
				params.add(p.getHigherLimit());
				params.add(p.getLowerLimit());
				params.add(p.getShakeType());
				params.add(p.getPointNorm());
				params.add(p.getIsPcheck());
				params.add(p.getSiteid());
				params.add(p.getOrgid());
				params.add(timestamp);
				params.add(p.getPostNo());
				params.add(p.getDataFormatNo());
				params.add(p.getCycle());
				params.add(p.getStartDate());
				params.add(p.getEndDate());
				params.add(p.getDutyinfoNum());
				params.add(p.getDayCycle());
				maximoUtils.updateBatchByPreparedStatement(piPstmt, params);
	        }
			piPstmt.executeBatch();
			maximoUtils.commit();
			maximoUtils.closePstmt(piPstmt);
			maximoUtils.closePs();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
