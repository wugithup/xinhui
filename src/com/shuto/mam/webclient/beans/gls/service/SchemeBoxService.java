package com.shuto.mam.webclient.beans.gls.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import psdi.server.MXServer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shuto.mam.webclient.beans.gls.model.Line;
import com.shuto.mam.webclient.beans.gls.model.Node;
import com.shuto.mam.webclient.beans.gls.util.db.DbManager;

/**
 * 
 * @author Liuyc
 *
 */
public class SchemeBoxService extends HttpServlet{
	private static final String CRUB="crud";//保存参数
	private static final String QUERYBOX="querybox";//显示已经保存的任务参数
	private static final String CHOOSEBOX="choosebox";//选择钥匙
	private static final String CHOOSEWOTICKET="choosewoticket";//选择工作票
	

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String  result="";
		response.setContentType("text/json;charset=utf-8");
		Connection conn=MXServer.getMXServer().getDBManager().getSequenceConnection();
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		String param = request.getParameter("method"); 
		System.out.println(param+"========================");
		if(CRUB.equals(param)){
			ServletInputStream in=request.getInputStream(); ;
//			InputStream ins =new InputStream(in);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			try {
				result=this.setBox(reader);
			} catch (SQLException e) {
				result="error";
			}

			 response.getWriter().println("{\"status\":\""+result+"\"}");
		}else if(QUERYBOX.equals(param)){//查询策划好的钥匙箱
			result=this.getBox();
			response.getWriter().println(result);  //返回成功的json
		}else if(CHOOSEBOX.equals(param)){//为空箱选择钥匙箱名称
			String boxfb=new String(request.getParameter("boxfb").getBytes("ISO-8859-1"),"utf-8");
			String boxdes=new String(request.getParameter("boxdes").getBytes("ISO-8859-1"),"utf-8");

			
			String pageSizeDes=request.getParameter("pagesize");
			String pageNumDes=request.getParameter("pagenum");
			int pageNum=1;
			int pageSize=10;
			String where="1=1";
			if(boxfb!=null && !"".equals(boxfb)){
				where=where+" and BOXLOCATION like '%"+boxfb+"%'";
			}
			if(boxdes!=null && !"".equals(boxdes)){
				where=where+" and description like '%"+boxdes+"%'";
			}
			if(pageNumDes!=null && !"".equals(pageNumDes)){
				pageNum=Integer.valueOf(pageNumDes);
			}
			pageSize=Integer.valueOf(pageSizeDes);
			
			int rowMax=pageSize*pageNum;
			int rowMin=pageSize*(pageNum-1);

			result=this.chooseBox(where,rowMin,rowMax);
			response.getWriter().println(result);  //返回成功的json
		}else if(CHOOSEWOTICKET.equals(param)){//如果执行的为选择工作票
			result=this.chooseWoticket();
			System.out.println(result);
			response.getWriter().println(result);  //返回成功的json
		}
		

		
	}
	
	/**
	 * 将前端策划好的钥匙箱插入到数据中
	 * @param reader
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	public String setBox(BufferedReader reader) throws IOException, SQLException{
		DbManager db=new DbManager();
		StringBuffer buffer = new StringBuffer();
		String line =null; 
		while((line=reader.readLine())!=null){
			buffer.append(line);
		}
		String datas []= buffer.toString().split("shuto");
		
		String lines = datas[0];  //连接线json字符串
		String nodes = datas[1];//节点json字符串 
		
		//查询记录是否已经存在 如果存在删除（更新比较复杂，因为用户修改节点多个属性的话，得记住操作哪个节点，先查询这个节点是否存在，存在更新 不存在插入）
		String  deleteline  ="delete  from safebox_line"; 
		String  deletenode  ="delete  from safebox_node "; 
		List<String> sqls=new ArrayList<String>();
		sqls.add(deleteline);
		sqls.add(deletenode);
		
		//添加操作线 
		List<JSONObject>  objs=  JSONArray.parseArray("["+lines+"]", JSONObject.class);
		for(int x =0;x<objs.size();x++){
			JSONObject jsonObject = objs.get(x);
			Map<String,JSONObject> map = jsonObject.toJavaObject((JSON)jsonObject, Map.class); //此工作流插件json是以map形式的格式，所以在这里转换成了map
			for(Map.Entry<String,JSONObject> entry:  map.entrySet()){
				String key = entry.getKey();  
				JSONObject  lineJson = entry.getValue(); 
				String type = lineJson.getString("type"); 
				String from_= lineJson.getString("from");
				String to_ =lineJson.getString("to");
				String name = lineJson.getString("name");
				if(name==null){
					name ="";
				}
				boolean c =  lineJson.getBoolean("marked");
				int marked =0 ; 
				if(c){
					marked =1 ; 
				}
				
				String sql = "insert into safebox_line (id,type,from_,to_,name,marked) values('"+key+"','"+type+"','"+from_+"','"+to_+"','"+name+"',"+marked+")";
				sqls.add(sql); 
			}
		}
		
		//添加节点
		List<JSONObject>  objNodes=  JSONArray.parseArray("["+nodes+"]", JSONObject.class);
		for(int x =0;x<objNodes.size();x++){
			JSONObject jsonObject = objNodes.get(x);
			Map<String,JSONObject> map = jsonObject.toJavaObject((JSON)jsonObject, Map.class); //此工作流插件json是以map形式的格式，所以在这里转换成了map
			for(Map.Entry<String,JSONObject> entry:  map.entrySet()){
				String key = entry.getKey();  
				JSONObject  nodejson = entry.getValue();
				
				String name = nodejson.getString("name");
				int left = nodejson.getIntValue("left");
				int top = nodejson.getIntValue("top");
				String type =nodejson.getString("type");
				int width = nodejson.getIntValue("width");
				int height = nodejson.getInteger("height");
				int seq = Integer.valueOf(key.substring(key.lastIndexOf("_")+1,key.length()));
				
				
				String sql = "insert into safebox_node (id,name,left,top,type,width,height,seq) values('"+key+"','"+name+"',"+left+","+top+",'"+type+"',"+width+","+height+","+seq+")";
				sqls.add(sql); 
			}
		}
		String result="error";
		int dbResult=db.update(sqls);
		if(dbResult==1){
			 result="success";
		}

		return result;
	}

	
	
	public String  getBox() throws RemoteException{
		DbManager db=new DbManager();
		//查询钥匙箱的json
		String query_nodesql  = "select * from safebox_node";
		List<Map<String,String>> dbNodes= db.queryBySqlStr(query_nodesql);
		Map<String, Node> mapnode =new HashMap<String, Node>();
		for(int i=0;i<dbNodes.size();i++){
			Map<String,String> nodeMap=dbNodes.get(i);
			Node node = new Node();
			node.setHeight(Integer.valueOf(nodeMap.get("HEIGHT")));
			node.setLeft(Integer.valueOf(nodeMap.get("LEFT")));
			node.setName(nodeMap.get("NAME")); 
			node.setTop(Integer.valueOf(nodeMap.get("TOP"))); 
			node.setType(nodeMap.get("TYPE"));
			node.setWidth(Integer.valueOf(nodeMap.get("WIDTH")));
			mapnode.put(nodeMap.get("ID"), node);
		}
		
		Map<String, Line> mapline =new HashMap<String, Line>();
		String query_linesql = "select * from safebox_line";
		List<Map<String,String>> dbLines=db.queryBySqlStr(query_linesql);
		for(int i=0;i<dbLines.size();i++){
			Map<String,String> lineMap=dbLines.get(i);
			Line line=new Line();
			
			boolean flag = false  ; 
			line.setFrom(lineMap.get("FROM_"));
			String c = lineMap.get("MARKED"); 
			if("1".equals(c) ){
				flag = true; 
			}
			line.setMarked(flag);
			String name = lineMap.get("NAME"); 
			if(name ==null){ //防止出现undifine
				name = "";
			}
			line.setName(name);
			line.setTo(lineMap.get("TO_"));
			line.setType(lineMap.get("TYPE"));
			mapline.put(lineMap.get("ID"), line);
		}
		JSONObject jsonModel = new JSONObject();
		jsonModel.put("title", "钥匙箱级联策划图");
		jsonModel.put("nodes", mapnode);
		jsonModel.put("lines", mapline);

		JSONObject allObject = new JSONObject();
		String queryMaxnodeSeq = "select max(seq) as seq from  safebox_node";
		List<Map<String, String>> dbSeq = db.queryBySqlStr(queryMaxnodeSeq);
		String seqStr=dbSeq.get(0).get("SEQ");
		int seq =0;
		if(seqStr!=null && !"".equals(seqStr)){
			seq=Integer.valueOf(seqStr);
		}
	
		allObject.put("datas", jsonModel);
		allObject.put("seq", seq);
		return  JSONObject.toJSONString(allObject); 
		
	}
	
	
	/**
	 * 查询钥匙箱
	 * @param where 钥匙箱过滤条件
	 * @param rowMin 最小行数 用于分页
	 * @param rowMax 最大行数
	 * @return
	 * @throws RemoteException 
	 */
	public String chooseBox(String where,int rowMin,int rowMax) throws RemoteException{
		
		DbManager db=new DbManager();
		String sql="select * from (select rownum as num,barcodeid,description,boxlocation from HSE_MAINTAINLIST where "+where+" and MAINTAINTYPE='钥匙箱' order by BARCODEID) l where  "+rowMin+"<l.num and l.num<="+rowMax+" ";
		System.out.println(sql);
		List<Map<String,String>> dbBoxs=db.queryBySqlStr(sql);
		String countSql="select barcodeid  from HSE_MAINTAINLIST where "+where+" and MAINTAINTYPE='钥匙箱' ";
		List<Map<String,String>> dbCount=db.queryBySqlStr(countSql);
		Map boxMap=new HashMap();
		boxMap.put("total", dbCount.size());
		boxMap.put("rows", dbBoxs);
//		System.out.println(JSON.toJSON(boxMap).toString());
		return JSON.toJSON(boxMap).toString();
		
	}
	
	
	/**
	 * 查询工作票
	 * @return
	 * @throws RemoteException 
	 */
	public String chooseWoticket() throws RemoteException{
		DbManager db=new DbManager();
		String sql="select wonum,description,location, (select description from locations l where l.location=workorder.location  and l.siteid=workorder.siteid) as  locationdes , s_profession,s_jizu from workorder where worktype='工作票' and status='等待批准'";
		List<Map<String,String>> dbWos=db.queryBySqlStr(sql);
		Map boxMap=new HashMap();
		boxMap.put("total", dbWos.size());
		boxMap.put("rows", dbWos);
		return JSON.toJSON(boxMap).toString();
	}
}
