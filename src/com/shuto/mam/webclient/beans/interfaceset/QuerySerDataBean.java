package com.shuto.mam.webclient.beans.interfaceset;

import java.io.StringReader;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;

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
public class QuerySerDataBean extends DataBean {
	/**
	 * @throws JSQLParserException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws RemoteException 
	 * @throws MXException 
	 * 初始化节点属性
	 * @Title: initNodeAttr  
	 * @Description: TODO   void
	 * @throws
	 */
	public void initNodeAttr() throws MXException, RemoteException, ClassNotFoundException, SQLException, JSQLParserException {
		 DataBean nodeDataBean = this.app.getDataBean("node_table");
		 MboRemote service = this.app.getAppBean().getMbo();
		 Connection conn = null;
		 PreparedStatement spstatement = null;
		 ResultSet rs = null;
		 try {
			 if (nodeDataBean != null) {
				 MboRemote selectnode = nodeDataBean.getMbo();
				 if (selectnode != null) {
					 String dburl = service.getString("dburl");
					 String dbuser = service.getString("dbuser");
					 String dbpwd = service.getString("dbpwd");
					 String nodesql = selectnode.getString("sql");
					 MboSetRemote nodeparameters = selectnode.getMboSet("NODEPARAMETER");
					 MboSetRemote nodeattributes = selectnode.getMboSet("NODEATTRIBUTE");
					 if (nodeattributes != null && !nodeattributes.isEmpty()) {
						 WebClientEvent event = clientSession.getCurrentEvent();
						 int msgRet = event.getMessageReturn();
						 if (msgRet < 0 ) {	
						 	throw new MXApplicationYesNoCancelException("BMXAA9310E","service", "initnodeattribute");	
						 }
						 if (msgRet == 8) {
							 nodeattributes.deleteAll();
						 } else {
							 return;
						 }
					 }
					 if (nodesql != null && !"".equals(nodesql)) {
						 Class.forName("oracle.jdbc.driver.OracleDriver");
						 conn=DriverManager.getConnection(dburl, dbuser, dbpwd);
						 conn.setAutoCommit(false);
						 Map<String,Map<String,String>> columnMeta = new HashMap<String,Map<String,String>>();
						 Map<String,String> columnInfo = null;
						 if (selectnode.getInt("layer")==1) {
							 String querySql = "select * from ("+nodesql+")";
							 StringBuffer wherestr = new StringBuffer(" where ");
							 Map<Integer,String> whereparamets = new LinkedHashMap<Integer,String>();
							 int parametnum = 1;
							 int num = 0;
							 //拼接查询条件
							 MboRemote pmbo = null;
							 for (int i=0;i<nodeparameters.count();i++) {
								 pmbo = nodeparameters.getMbo(i);
								 if (num == 0) {
									if ("日期".equals(pmbo.getString("type"))) {
										wherestr.append(pmbo.getString("name")+""+pmbo.getString("comparemark")+"to_date(?, 'yyyy-mm-dd')");
									} else if ("日期时间".equals(pmbo.getString("type"))) {
										wherestr.append(pmbo.getString("name")+""+pmbo.getString("comparemark")+"to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
									} else {
										wherestr.append(pmbo.getString("name")+""+pmbo.getString("comparemark")+"?");
									}
								} else {
									if ("日期".equals(pmbo.getString("type"))) {
										wherestr.append(" and "+pmbo.getString("name")+""+pmbo.getString("comparemark")+"to_date(?, 'yyyy-mm-dd')");
									} else if ("日期时间".equals(pmbo.getString("type"))) {
										wherestr.append(" and "+pmbo.getString("name")+""+pmbo.getString("comparemark")+"to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
									} else {
										wherestr.append(" and "+pmbo.getString("name")+""+pmbo.getString("comparemark")+"?");
									}
								}
								whereparamets.put(parametnum, pmbo.getString("value"));
								parametnum++;
								num ++;
							 }
							 spstatement = conn.prepareStatement(querySql+wherestr.toString());
							 for (int i:whereparamets.keySet()) {
								spstatement.setString(i, whereparamets.get(i));
							 }
						 } else {
							 spstatement = conn.prepareStatement(nodesql);
							//设置查询参数
							 for (int i=0;i<nodeparameters.count();i++) {
							 	spstatement.setString(nodeparameters.getMbo(i).getInt("snum"), nodeparameters.getMbo(i).getString("value"));
							 }
						 }
						 rs = spstatement.executeQuery();
						 //封装查询数据元数据
						 ResultSetMetaData rsd = rs.getMetaData(); 
						 for(int i = 0; i < rsd.getColumnCount(); i++) {
						 	columnInfo = new HashMap<String,String>();
						 	columnInfo.put("COLUMNNAME", rsd.getColumnName(i+1));
						 	columnInfo.put("COLUMNTYPE", rsd.getColumnTypeName(i+1));
						 	columnInfo.put("COLUMNSIZE", rsd.getColumnDisplaySize(i+1)+"");
						 	columnMeta.put(rsd.getColumnName(i+1), columnInfo);
						 } 
						 //解析SQL获取查询字段信息
						 CCJSqlParserManager parserManager = new CCJSqlParserManager();
					     Select select = (Select) parserManager.parse(new StringReader(nodesql));
					     PlainSelect plain=(PlainSelect)select.getSelectBody(); 
					     List<SelectItem> selectitems=plain.getSelectItems();
					     MboRemote attribute = null;
					     //根据SQL查询字段信息封装节点属性
					     for(int i=0;i<selectitems.size();i++) {
					     	Expression expression=((SelectExpressionItem) selectitems.get(i)).getExpression();
					        Alias alias = ((SelectExpressionItem) selectitems.get(i)).getAlias();
					        String tablename = null;
					        String columname = null;
					        String columalias = null;
					        String valuetype = null;
					        if (expression instanceof Column) {
					        	Column col=(Column)expression;
					        	tablename = col.getTable().getName();
					        	columname = col.getColumnName();
					        	valuetype = "字段";
					        } else if (expression instanceof Function) {
					        	valuetype = "函数";
					        } else if (expression instanceof Parenthesis || expression instanceof BinaryExpression) {
					        	valuetype = "运算";
					        } else if (expression instanceof SubSelect) {
					        	valuetype = "子查询";
					        }
					        if (alias != null) {
					        	columalias = alias.getName();
					        }
					        attribute = nodeattributes.add();
					        attribute.setValue("servicenum", service.getString("servicenum"));
					        attribute.setValue("nodenum", selectnode.getString("nodenum"));
					        attribute.setValue("ordernum", i+1);
					        attribute.setValue("valuetype", valuetype);
					        attribute.setValue("tablename", tablename);
					        attribute.setValue("columnname", columname);
					        if (columalias == null) {
					        	attribute.setValue("columalias", columname);
					        } else {
					        	attribute.setValue("columalias", columalias);
					        }
					        attribute.setValue("facename", attribute.getString("columalias").toLowerCase());
					        columnInfo = columnMeta.get(attribute.getString("columalias").toUpperCase());
					        if (columnInfo != null && !columnInfo.isEmpty()) {
					        	attribute.setValue("columntype", columnInfo.get("COLUMNTYPE"));
					        	attribute.setValue("columnsize", columnInfo.get("COLUMNSIZE"));
					        }
					        attribute.setValue("sourcetype", "属性");
					        attribute.setValue("isRequired", true);
					     }
					     app.getAppBean().save();
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
