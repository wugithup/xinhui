package com.shuto.mam.webservice.warning;

/**
 * 
* @ClassName: IWarningService 
* @Description: 预警平台借口
* @author lull lull@shuto.cn
* @date 2017年7月20日 下午4:52:14 
*
 */
public abstract interface IWarningService {

	/**
	 * 
	 * @param yjuid
	 * @param c_yjkssj
	 * @param c_yjjssj
	 * @param c_yjnr
	 * @param c_yjtsr
	 * @param c_yjdj
	 * @param c_yjyxms
	 * @param siteid
	 * @param c_wfid
	 * @param c_dfid
	 * @param c_createdate
	 * @param C_yjjx
	 * @param C_yjssxt
	 * @param C_yjly
	 * @param C_yjpc
	 * @param c_mxid
	 * @param c_yjdate
	 * @return
	 */
	public String addWarningWO(String yjuid, String c_yjkssj, String c_yjjssj, String c_yjnr, String c_yjtsr,
			String c_yjdj, String c_yjyxms, String siteid, String c_wfid, String c_dfid, String c_createdate,
			String c_yjjx, String c_yjssxt, String c_yjly, String c_yjpc, String c_mxid, String c_yjdate);

	/**
	 * 
	 * @param yjuid			预警信息唯一标识
	 * @return				如果成功则返回对应预警工单状态,否则返回 "FAILD";
	 */
	public String queryStatus(String yjuid);

	/**
	 * 
	 * @param yjuid			预警信息唯一标识
	 * @return				如果成功则返回对应预警工单编号,否则返回 "FAILD";
	 */
	public String queryWOnum(String yjuid);

	/**
	 * 
	 * @param yjuid			预警信息唯一标识
	 * @return				预警工单状态未完结返回exist，否则返回FAILD；
	 * @return              wonum：返回标识为exist时，返回预警工单编号，否则为空；
	 * @return              status：返回标识为exist时，返回预警工单状态，否则为空；
	 * @return              createperson：返回标识为exist时，返回创建人，否则为空；
	 * @return              createdate: 返回标识为exist时，返回创建时间，否则为空；
	 */
	public String queryWang(String yjuid);

	/**
	 * 
	 * @param wonum			工单编号
	 * @return				如果查询成功则预警工单状态,否则返回 "FAILD"；
	 */

	public String queryStatus2(String wonum);
}