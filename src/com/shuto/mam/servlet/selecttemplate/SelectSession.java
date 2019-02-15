package com.shuto.mam.servlet.selecttemplate;

import java.util.HashMap;

import javax.servlet.ServletRequest;

public class SelectSession {
	
	static HashMap<String, HashMap<String, String>> session=new HashMap<String, HashMap<String, String>>();
	HashMap<String, String> stSessionMap=null;
	boolean isnew=false;
	ServletRequest req=null;
	public SelectSession(ServletRequest req) {
		// TODO Auto-generated constructor stub
		this.req=req;
		
		String stSession=req.getParameter("SelectTemplateSession");
		System.out.println("stSession="+stSession);
		String stType=req.getParameter("SelectTemplateType");
		if(session.get(stSession)==null){
			stSessionMap=new HashMap<String, String>();
			session.put(stSession, stSessionMap);
			isnew=true;
		}else{
			stSessionMap=session.get(stSession);
		}
	}
	
	
	public HashMap<String, String> getstSessionMap(){
		return stSessionMap;	
	}

	public boolean isNew() {
		// TODO Auto-generated method stub
		return isnew;
	}
	

}
