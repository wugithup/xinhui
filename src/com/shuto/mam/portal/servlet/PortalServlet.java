package com.shuto.mam.portal.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shuto.mam.portal.action.SrAction;
import com.shuto.mam.portal.action.WorkorderAction;
import com.shuto.mam.portal.action.WotrackAction;

public class PortalServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static String APP_SR = "sr";
	private final static String APP_WR = "wr";
	private final static String APP_TK = "tk";
	private final static String M_INIT_CONTORL = "initcontorl";
	private final static String M_LOAD = "loaddata";

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String result = "";
		String app = request.getParameter("app");// 功能区分参数如：缺陷、工单、工作票
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=utf-8");
		String siteid = request.getParameter("siteid");
		/**
		 * 采用分发的方式进行处理，根据不同的app请求不同的action 格式为if(){ }else if(){ }
		 */

		// 如果请求的为缺陷时
		if (APP_SR.equals(app)) {
			SrAction sr;
			try {
				sr = new SrAction(siteid);
				String method = request.getParameter("method");
				if (M_INIT_CONTORL.equals(method)) {// 初始化title查询参数
					result = sr.getInitControl();
				} else if (M_LOAD.equals(method)) {// 加载数据
					String startdate = request.getParameter("startdate");// 开始时间
					String enddate = request.getParameter("enddate");// 结束时间
					String sr_status = request.getParameter("sr_status");// 状态
					String sr_zydw = request.getParameter("sr_zydw");// 检修单位
					String sr_zy = request.getParameter("sr_zy");// 专业
					String sr_jb = request.getParameter("sr_jb");// 级别
					// String sr_jz = request.getParameter("sr_jz").replace("J",
					// "#");// 机组 由于机组含有#号 所以偷懒就做了各转码
					String sr_description = request
							.getParameter("sr_description");// 描述
					int pageSize = Integer.parseInt(request
							.getParameter("pagesize"));// 翻页，每页条数
					int pageNum = Integer.parseInt(request
							.getParameter("pagenum"));// 翻页，当前页数

					result = sr.loadData(startdate, enddate, sr_status,
							sr_zydw, sr_zy, sr_jb, sr_description,// sr_jz,
							pageSize, pageNum);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			response.getWriter().println(result);
		}

		// 如果请求的为工单时
		else if (APP_WR.equals(app)) {
			WorkorderAction wr;
			try {
				wr = new WorkorderAction(siteid);
				String method = request.getParameter("method");
				if (M_INIT_CONTORL.equals(method)) {// 初始化title查询参数
					result = wr.getInitControl();
				} else if (M_LOAD.equals(method)) {// 加载数据
					String startdate = request.getParameter("startdate");// 开始时间
					String enddate = request.getParameter("enddate");// 结束时间
					String wr_status = request.getParameter("wr_status");// 状态
					String wr_zydw = request.getParameter("wr_zydw");// 检修单位
					String wr_zy = request.getParameter("wr_zy");// 专业
					String wr_lx = request.getParameter("wr_lx");// 类型
//					String wr_jz = request.getParameter("wr_jz");// 机组 由于机组含有#号
																	// 所以偷懒就做了各转码
					String wr_description = request
							.getParameter("wr_description");// 描述
					int pageSize = Integer.parseInt(request
							.getParameter("pagesize"));// 翻页，每页条数
					int pageNum = Integer.parseInt(request
							.getParameter("pagenum"));// 翻页，当前页数

					result = wr.loadData(startdate, enddate, wr_status,
							wr_zydw, wr_zy, wr_lx,  wr_description,
							pageSize, pageNum);//wr_jz,
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			response.getWriter().println(result);
		}

		// 如果请求的为工作票时
		else if (APP_TK.equals(app)) {
			WotrackAction tk;
			try {
				tk = new WotrackAction(siteid);
				String method = request.getParameter("method");
				if (M_INIT_CONTORL.equals(method)) {// 初始化title查询参数
					result = tk.getInitControl();
				} else if (M_LOAD.equals(method)) {// 加载数据
					String startdate = request.getParameter("startdate");// 开始时间
					String enddate = request.getParameter("enddate");// 结束时间
					String tk_status = request.getParameter("tk_status");// 状态
					String tk_zydw = request.getParameter("tk_zydw");// 检修单位
					String tk_zy = request.getParameter("tk_zy");// 专业
					String tk_lx = request.getParameter("tk_lx");// 类型
					// String tk_jz = request.getParameter("tk_jz");// 机组
					// 由于机组含有#号
																	// 所以偷懒就做了各转码
					String tk_description = request
							.getParameter("tk_description");// 描述
					int pageSize = Integer.parseInt(request
							.getParameter("pagesize"));// 翻页，每页条数
					int pageNum = Integer.parseInt(request
							.getParameter("pagenum"));// 翻页，当前页数

					result = tk.loadData(startdate, enddate, tk_status,
							tk_zydw, tk_zy, tk_lx,  tk_description,//tk_jz,
							pageSize, pageNum);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			response.getWriter().println(result);
		}

	}

}
