package com.shuto.mam.webclient.beans.interfaceset;

import java.io.StringReader;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * @Title: QuerySerDataBean.java 
 * @Description: TODO   
 * @author itrobot 
 * @date 2017-6-10 下午04:17:24 
 * @version V1.0 
 */
public class UploadSerDataBean extends DataBean {
	/**
	 * @throws JSQLParserException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws RemoteException 
	 * @throws MXException 
	 * 初始化节点参数
	 * @Title: initNodeParameter  
	 * @Description: TODO   void
	 * @throws
	 */
	public void initNodeParameter() throws MXException, RemoteException, ClassNotFoundException, SQLException, JSQLParserException {
		 DataBean nodeDataBean = this.app.getDataBean("node_table");
		 Connection conn = null;
		 PreparedStatement spstatement = null;
		 ResultSet rs = null;
		 try {
			 if (nodeDataBean != null) {
				 MboRemote selectnode = nodeDataBean.getMbo();
				 if (selectnode != null) {
					 String nodesql = selectnode.getString("sql");
					 MboSetRemote nodeparameters = selectnode.getMboSet("NODEPARAMETER");
					 if (nodeparameters != null && !nodeparameters.isEmpty()) {
						 WebClientEvent event = clientSession.getCurrentEvent();
						 int msgRet = event.getMessageReturn();
						 if (msgRet < 0 ) {	
						 	throw new MXApplicationYesNoCancelException("BMXAA9311E","service", "initnodeparameter");	
						 }
						 if (msgRet == 8) {
							 nodeparameters.deleteAll();
						 } else {
							 return;
						 }
					 }
					 if (nodesql != null && !"".equals(nodesql)) {
						CCJSqlParserManager parserManager = new CCJSqlParserManager();
						Insert insert = (Insert) parserManager.parse(new StringReader(nodesql));
						//表
						String table = insert.getTable().getName();
						//属性
						List<Column> columns = insert.getColumns();
						//参数
						List<Expression> expressions = ((ExpressionList) insert.getItemsList()).getExpressions();
						//封装属性
						if (columns.size() == expressions.size()) {
							selectnode.setValue("tablename", table);
							MboRemote p = null;
							int pnum = 1;
							for (int i=0;i<columns.size();i++) {
								if (expressions.get(i) instanceof JdbcParameter || expressions.get(i) instanceof Function) {
									p = nodeparameters.add();
									p.setValue("snum", pnum);
									p.setValue("name", columns.get(i).getColumnName());
									p.setValue("type", "字符");
									p.setValue("facename", columns.get(i).getColumnName().toLowerCase());
									p.setValue("value", expressions.get(i).toString());
									p.setValue("sourcetype", "参数");
									p.setValue("servicenum", selectnode.getString("servicenum"));
									p.setValue("nodenum", selectnode.getString("nodenum"));
									p.setValue("isRequired", true);
									pnum++;
								}
							}
							 app.getAppBean().save();
						}
					 } else {
						 
					 }
				 } else {
					 
				 }
			 }
		 } finally {
			 if (rs != null) {
				 rs.close();
			 }
			 if (spstatement != null) {
				 spstatement.close();
			 }
			 if (conn != null) {
				 conn.close();
			 }
		 }
	}
}
