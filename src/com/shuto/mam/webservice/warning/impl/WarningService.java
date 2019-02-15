/**   
* @Title: WarningService.java 
* @Package com.shuto.mam.webservice.warning.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lull lull@shuto.cn
* @date 2017年6月29日 上午9:41:26 
* @version V1.0.0
*/
package com.shuto.mam.webservice.warning.impl;

import com.shuto.mam.webservice.warning.IWarningService;
import com.shuto.mam.webservice.warning.bussiness.Workorder;

/** 
* @ClassName: WarningService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author lull lull@shuto.cn
* @date 2017年6月29日 上午9:41:26 
*  
*/
public class WarningService implements IWarningService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.shuto.mam.webservice.warning.IWarningService#queryStatus(java.lang.
	 * String)
	 */
	@Override
	public String queryStatus(String yjuid) {
		Workorder wo = new Workorder();
		return wo.queryStatus(yjuid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.shuto.mam.webservice.warning.IWarningService#queryWOnum(java.lang.
	 * String)
	 */
	@Override
	public String queryWOnum(String yjuid) {
		Workorder wo = new Workorder();
		return wo.queryWOnum(yjuid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.shuto.mam.webservice.warning.IWarningService#queryWang(java.lang.
	 * String)
	 */
	@Override
	public String queryWang(String yjuid) {
		Workorder wo = new Workorder();
		return wo.queryWang(yjuid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.shuto.mam.webservice.warning.IWarningService#addWarningWO(java.lang.
	 * String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, int, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String addWarningWO(String yjuid, String c_yjkssj, String c_yjjssj, String c_yjnr, String c_yjtsr, String c_yjdj, String c_yjyxms, String siteid, String c_wfid, String c_dfid,
			String c_createdate, String c_yjjx, String c_yjssxt, String c_yjly, String c_yjpc, String c_mxid, String c_yjdate) {
		Workorder wo = new Workorder();
		return wo.addWarningWO(yjuid, c_yjkssj, c_yjjssj, c_yjnr, c_yjtsr, c_yjdj, c_yjyxms, siteid, c_wfid, c_dfid, c_createdate, c_yjjx, c_yjssxt, c_yjly, c_yjpc, c_mxid, c_yjdate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.shuto.mam.webservice.warning.IWarningService#queryStatus2(java.lang.
	 * String)
	 */
	@Override
	public String queryStatus2(String wonum) {
		Workorder wo = new Workorder();
		return wo.queryStatus2(wonum);
	}

}
