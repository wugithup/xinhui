package com.shuto.mam.webclient.beans.gls.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import psdi.server.MXServer;
import psdi.util.MXException;

import com.alibaba.fastjson.JSONObject;
import com.shuto.mam.webclient.beans.gls.model.BoxAttr;
import com.shuto.mam.webclient.beans.gls.util.db.DbManager;

/**
 * com.shuto.mam.webclient.beans.gls.service.SafeLockServiceServlet 华东大区
 * 钥匙箱分布图查询功能
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年7月28日 下午4:49:10
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class SafeLockServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String boxlocationType = "BOXLOCATIONTYPE";// 查询钥匙箱分类
	private static final String queryBox = "QUERYBOX";// 分类下面钥匙箱状况 查询按钮点击
	private static final String queryTagout = "QUERYTAGOUT";// 加载每个钥匙箱的隔离点
	private String result;
	String siteid = null;
	private DbManager db = new DbManager(); // 数据库连接

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String type = req.getParameter("type");
		try {
			siteid = MXServer.getMXServer().getSystemUserInfo().getInsertSite();
		} catch (MXException e1) {
			e1.printStackTrace();
		}// 获取当前登录人站点
		if (boxlocationType.equals(type)) {// 查询钥匙箱分部大类 页面点击集控室的方法
			try {
				result = this.getBoxType(req, resp);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (MXException e) {
				e.printStackTrace();
			}
		} else if (queryBox.equals(type)) {// 查询大类下钥匙箱分部情况 查询按钮点击
			String boxType = req.getParameter("boxtype");// 集控室
			String activity = req.getParameter("activity");// 钥匙箱占用情况

			if (activity != null) {
				// activity=new String(activity.getBytes("ISO-8859-1"),"UTF-8");
				System.out.println(activity);
				activity = URLDecoder.decode(activity, "UTF-8");
			}

			System.out.println(activity);
			String where = "";// 拼接where条件
			if ("".equals(boxType) || boxType == null) {
				where = " boxlocation in (select value from alndomain where  domainid='BOXLOCATION' and  siteid='"
						+ siteid + "')";
			} else {
				System.out.println("集控室为：" + boxType);
				where = " boxlocation in (select value from alndomain where alndomainid='"
						+ boxType
						+ "' and domainid='BOXLOCATION' and  siteid='"
						+ siteid
						+ "')";
			}

			if ("1".equals(activity)) {// 已占用 在 有 安错 没恢复的 钥匙箱 就是占用
				System.out.println("已占用的");
				where = where
						+ "and barcodeid in (select ysxid from v_activity_wo where siteid='"
						+ siteid
						+ "' and  ysxid is not null and ishf=0  "
						+ "  and wonum  not In ( select wonum  from WORKORDER "
						+ " where siteid='"
						+ siteid
						+ "'  and status in ('待确认','已终结','关闭', '取消', '已关闭', '已取消','已作废'))  group by  ysxid)";
			}
			if ("2".equals(activity)) {// 不占用的 不再安错没恢复了 的钥匙箱里面 就是未占用
				System.out.println("未占用的");
				where = where
						+ "and barcodeid not in (select ysxid from v_activity_wo where  siteid='"
						+ siteid
						+ "'  and ysxid is not null and ishf=0 group by  ysxid)";
			}

			result = this.getBox(where);
		} else if (queryTagout.equals(type)) {// 查询钥匙箱中的隔离点
			String boxid = req.getParameter("boxid");
			result = this.getTagout(boxid);
		} else {

			resp.setContentType("text/xml;charset=utf-8");
			resp.setCharacterEncoding("utf-8");
			PrintWriter writer = resp.getWriter();
			writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?><servlet-mapping>sdfsfddf</servlet-mapping>");
			writer.flush();
		}
		System.out.println(result);
		resp.setContentType("text/xml;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		PrintWriter writer = resp.getWriter();
		writer.println(result);
		writer.flush();
		writer.close();

	}

	/**
	 * 查询钥匙箱所在集控室
	 * 
	 * @param req
	 * @param resp
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ServletException
	 * @throws IOException
	 * @throws MXException
	 */
	public String getBoxType(HttpServletRequest req, HttpServletResponse resp)
			throws ClassNotFoundException, SQLException, ServletException,
			IOException, MXException {
		String query_boxtype = "select alndomainid,value from alndomain where domainid='BOXLOCATION' and  siteid='"
				+ siteid + "' ";
		List<Map<String, String>> dataList = db.queryBySqlStr(query_boxtype);

		String htmlSelect = "<option value=>请选择</option>";
		for (int i = 0; i < dataList.size(); i++) {
			Map<String, String> data = dataList.get(i);
			htmlSelect = htmlSelect + "<option value="
					+ data.get("ALNDOMAINID") + ">" + data.get("VALUE")
					+ "</option>";
		}

		return htmlSelect;
	}

	/**
	 * 查询钥匙箱分布以及钥匙箱占用情况
	 * 
	 * @param type
	 * @return
	 * @throws RemoteException
	 */
	public String getBox(String where) throws RemoteException {
		/**
		 * 以下参数为钥匙箱分布图初始参数
		 */
		int width = 120;
		int height = 100;
		int x = 10;
		int y = -10;
		String color = "CCFFCC";
		String connectors = "";

		List<Map<String, String>> mapData = this.getActivityBox();
		Map<String, String> noCtMap = mapData.get(0);// 无冲突钥匙箱
		Map<String, String> noMapW = mapData.get(2);// 无冲突钥匙箱
		Map<String, String> ctMap = mapData.get(1);// 冲突钥匙箱

		connectors = "<connectors color='FF0000' stdThickness='5'>";

		Map<String, String> onlyCtMap = new HashMap<String, String>();
		// 冲突钥匙箱画线
		for (Entry entry : ctMap.entrySet()) {
			String key = (String) entry.getKey();// 钥匙箱和工作票号
			String keys[] = key.split("&");
			String ysxid = keys[0];// 冲突的钥匙箱
			String wonum = keys[1];// 冲突的工作票（既该工作票的隔离点锁在其他钥匙箱里面）
			String sourceYsx = noMapW.get(wonum);
			String aqsid = (String) entry.getValue();
			if (sourceYsx != null) {
				connectors = connectors + "<connector strength='0.4'  label='"
						+ aqsid + "' from='" + ysxid + "' to='" + sourceYsx
						+ "' color='BBBB00'  arrowAtStart='0' arrowAtEnd='1'/>";
			} else {

				String str = onlyCtMap.get(ysxid);
				if (str == null) {
					onlyCtMap.put(ysxid, wonum + ";");
				} else {
					onlyCtMap.put(ysxid, str + wonum + ";");
				}

			}

		}
		connectors = connectors + "</connectors>";

		// 查询全部钥匙箱
		String query_box = "select  description,maintaintype,boxlocation ,barcodeid from hse_maintainlist where   siteid='"
				+ siteid
				+ "'  and "
				+ where
				+ " and maintaintype='钥匙箱'   order by barcodeid ";
		System.out.println("查询全部钥匙箱:" + "\r\n" + query_box + "\r\n");

		List<Map<String, String>> boxList = db.queryBySqlStr(query_box);
		int calculagraph = 1;
		String xml = "<chart xAxisMinValue='0' xAxisMaxValue='100' yAxisMinValue='0' yAxisMaxValue='-100' is3D='1' showFormBtn='0' viewMode='1' bgcolor='FFFFFF' chartTopMargin='50' canvasbordercolor='FFFFFF'  baseFontSize='12'  baseFont='微软雅黑'> <dataset seriesName='DS1' >";
		int boxCount = boxList.size();
		for (int i = 0; i < boxList.size(); i++) {
			Map<String, String> dataMap = boxList.get(i);
			BoxAttr attr = new BoxAttr();
			String boxId = dataMap.get("BARCODEID");

			String name = noCtMap.get(boxId);// 工作票编号
			String wodecription = "";
			String woAqsid = "";
			if (name != null) {
				System.out.println("工作票编号：  " + name);
				String query_WoDes = "select description, aqsid from workorder  where  siteid='"
						+ siteid
						+ "'  and   AQSID is not null and  wonum ='"
						+ name + "'";
				System.out.println("查询工作票描述语句：\n" + query_WoDes);
				List<Map<String, String>> wonums = db
						.queryBySqlStr(query_WoDes);
				Map<String, String> dataMap1 = wonums.get(0);
				wodecription = dataMap1.get("DESCRIPTION");
				woAqsid = dataMap1.get("AQSID");
				System.out.println("工作票描述为： " + wodecription + "安全箱为：  "
						+ woAqsid);

			}

			String oblyWonum = onlyCtMap.get(boxId);
			if (name == null && oblyWonum == null) {
				attr.setName(dataMap.get("DESCRIPTION"));
				attr.setColor(color);
			} else {
				if (name == null) {
					name = "";
				}

				if (oblyWonum == null) {
					oblyWonum = "";
				}
				// attr.setName(dataMap.get("DESCRIPTION") + "&#x000A;工作票号:"
				// + name + ";" + oblyWonum);
				attr.setName(dataMap.get("DESCRIPTION") + "&#x000A;安全锁:"
						+ woAqsid + "&#x000A;工作票号:" + name + ";" + oblyWonum
						+ "&#x000A;描述:" + wodecription);
				attr.setColor("FE8181");
			}

			attr.setWidth(width + "");
			attr.setHeight(height + "");

			attr.setId(boxId);
			attr.setX(x + "");
			attr.setY(y + "");

			x = x + 20;
			if (calculagraph % 5 == 0) {
				y = y - 40;
				x = 10;
			}
			calculagraph++;
			xml = xml + attr.getSet();
		}

		xml = xml + "</dataset> ";

		xml = boxCount + "~" + xml + connectors + "</chart>";
		return xml;

	}

	/**
	 * 以工作票为主线查询工作票存放在哪个钥匙箱中，
	 * 钥匙箱分为两种，第一种为主钥匙箱（工作票中没有隔离冲突的隔离点的钥匙存放的钥匙箱）第二种为冲突钥匙箱（
	 * 该工作票的隔离点已经被其他的工作票执行切已经锁入钥匙箱中） 如果有隔离冲突说明该钥匙箱不是住钥匙箱
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public List<Map<String, String>> getActivityBox() throws RemoteException {
		// 查询工单
		String query_Wo = "select wonum from v_activity_wo where   siteid='"
				+ siteid + "'  and   glysid is not null group by wonum";
		List<Map<String, String>> wonums = db.queryBySqlStr(query_Wo);
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();

		Map<String, String> notCtMap = new HashMap<String, String>();// 记录无冲突的钥匙箱，key为钥匙箱，value为工作票号
		Map<String, String> notCtWMap = new HashMap<String, String>();// 记录无冲突的钥匙箱，value为钥匙箱，key为工作票号
		Map<String, String> ctTagMsp = new HashMap<String, String>();// 记录冲突钥匙箱，key为钥匙箱，value为隔离点编号、隔离钥匙号、安全锁号

		for (int i = 0; i < wonums.size(); i++) {
			String wonum = wonums.get(i).get("WONUM");
			String query_Tag = "select * from hse_tagout  where     siteid='"
					+ siteid
					+ "'  and   wotagoutid in (select wosafetylinkid from WOSAFETYLINK where    siteid='"
					+ siteid + "'  and    wonum='" + wonum
					+ "' and tagoutid is not null ) and ishf=0";
			List<Map<String, String>> tags = db.queryBySqlStr(query_Tag);

			/* 循环工作票隔离点，查询隔离点是否冲突 */
			for (int j = 0; j < tags.size(); j++) {
				Map<String, String> tagMap = tags.get(j);
				String isglct = tagMap.get("ISGLCT");
				// String wotagoutid=tagMap.get("WOTAGOUTID");
				// String glysid=tagMap.get("GLYSID");
				String aqsid = tagMap.get("AQSID");
				String ysxid = tagMap.get("YSXID");
				if ("0".equals(isglct)) {// 不冲突的钥匙箱
					notCtMap.put(ysxid, wonum);
					notCtWMap.put(wonum, ysxid);
				} else {// 交叉作业的钥匙箱{wonum,wotagoutid,glysid,aqsid};
					String att = aqsid;
					ctTagMsp.put(ysxid + "&" + wonum, att);
				}

			}

		}
		dataList.add(notCtMap);
		dataList.add(ctTagMsp);
		dataList.add(notCtWMap);
		return dataList;
	}

	/**
	 * 查询钥匙箱中的隔离点
	 * 
	 * @param boxid
	 *            钥匙箱编号
	 * @throws RemoteException
	 */
	public String getTagout(String boxid) throws RemoteException {

		String queryTag = "select distinct h.glysid, (select w.tagoutid from WOSAFETYLINK w where  w.siteid='"
				+ siteid
				+ "'  and    w.wosafetylinkid=h.WOTAGOUTID) as tagoutid,  "
				+ "(select description from locations where    locations.siteid='"
				+ siteid
				+ "'  and locations.location=h.location  and  locations.siteid = h.siteid ) as description, "
				+ "  aqsid  "
				+ "from HSE_TAGOUT1 h where     siteid='"
				+ siteid
				+ "'  and  h.wonum  =  (select min(wonum) wonum  from HSE_TAGOUT h  where   siteid='"
				+ siteid
				+ "'  and    h.ysxid = '"
				+ boxid
				+ "'  and ishf = 0) and ishf=0";
		System.out.println("查询钥匙箱中的隔离点\n" + queryTag);
		List<Map<String, String>> tagoutList = db.queryBySqlStr(queryTag);

		Map tagoutMap = new HashMap();
		tagoutMap.put("total", tagoutList.size());
		tagoutMap.put("rows", tagoutList);
		return JSONObject.toJSONString(tagoutMap);

	}
}
