package com.shuto.mam.webclient.beans.pm;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import psdi.app.pm.PM;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.workflow.WorkFlowServiceRemote;

public class PMAppBean extends psdi.webclient.beans.pm.PMAppBean {
	public void initializeApp() throws MXException, RemoteException {
		super.initializeApp();
	}

	@Override
	public int SAVE() throws MXException, RemoteException {
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		String personid = mainMbo.getUserInfo().getPersonId();// 当前登录人
		Date sysdate = new Date(); // 获取系统时间
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化
		String date = dateformat.format(sysdate);
		mainMbo.setValue("CHANGEBY", personid);
		mainMbo.setValue("CHANGEDATE", date);
		return super.SAVE();
	}

	protected void setCurrentRecordData(MboRemote mbo) throws MXException, RemoteException {
		

		String status = mbo.getString("status");
		
		if("活动".equals(status)){
			mbo.setFlag(MboConstants.READONLY, true);
			String personid = mbo.getUserInfo().getPersonId();

			MboSetRemote mbosetremote = mbo.getMboSet("$PERSONGROUPTEAM", "PERSONGROUPTEAM", 
				      "persongroup= 'XHDC_PMBG' ");
			if (!mbosetremote.isEmpty()) {
			      for (int i = 0; i < mbosetremote.count(); i++) {
			        String b = mbosetremote.getMbo(i).getString("RESPPARTYGROUP");
			        System.out.println(b);
			        if (personid.equals(b))
			        {
			          mbo.setFlag(MboConstants.READONLY, false);
			         // mbo.setFieldFlag("STATUS", MboConstants.READONLY, false);
			        }

			      }

			    }
		}
		super.setCurrentRecordData(mbo);
		
	}

	public boolean isMaxadmin() throws RemoteException, MXException {
		String personid = getMXSession().getUserInfo().getPersonId();
		System.out.println(personid);

		return personid.equalsIgnoreCase("maxadmin");
	}
	//测试生成工单
	public int WOTEST() throws RemoteException, MXException {
		System.out.println("--------1、工单开始生成---------");
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		((PM) mainMbo).generateWork(false, 0);
		System.out.println("--------2、工单生成完毕---------");
		SimpleDateFormat dtfmt1 = new SimpleDateFormat("yyyy-MM-dd");
		MboSetRemote woSet = MXServer.getMXServer()
				.getMboSet("workorder", mainMbo.getUserInfo());
		woSet.setWhere("pmnum='" + mainMbo.getString("pmnum") + "' and siteid='"
				+ mainMbo.getString("siteid") + "' and targstartdate=to_date('"
				+ dtfmt1.format(mainMbo.getDate("nextdate"))
				+ "','yyyy-MM-dd')");
		System.out.println("--------5、排序前，工单数量：" + woSet.count() + "==" + woSet.getMbo(0).getString("wonum") + "---------");
		woSet.setOrderBy("workorderid desc");
		System.out.println("--------6、排序后，工单数量：" + woSet.count() + "==" + woSet.getMbo(0).getString("wonum") + "---------");
		woSet.reset();
		System.out.println("--------7、重置后，工单数量：" + woSet.count() + "==" + woSet.getMbo(0).getString("wonum") + "---------");
		System.out.println("--------3、得到工单，开始设置---------");
		if (!woSet.isEmpty()) {
			String location = mainMbo.getString("LOCATION");
			String s_profession = mainMbo.getString("s_profession");
			String SUPERVISOR = mainMbo.getString("SUPERVISOR");
			String TEAMNAME = mainMbo.getString("TEAMNAME");
			String LEAD = mainMbo.getString("LEAD");

			woSet.getMbo(0).setValue("location", location, 11L);
			woSet.getMbo(0).setValue("createperson", SUPERVISOR, 11L);
			woSet.getMbo(0).setValue("LEAD", LEAD, 11L);
			woSet.getMbo(0).setValue("TEAMNUM", TEAMNAME, 11L);
			woSet.getMbo(0).setValue("PROFESSION", s_profession, 11L);
			woSet.getMbo(0).setValue("DESCRIPTION", "管理员测试预防性维护功能，请勿处置此工单！", 11L);
			
			Date date = new Date();
			woSet.getMbo(0).setValue("SCHEDSTART", date, 11L);
			woSet.getMbo(0).setValue("SCHEDFINISH", date, 11L);
			woSet.save();

//			System.out.println(">>>>>generating workorder IS OK...");
//			WorkFlowServiceRemote wfs = (WorkFlowServiceRemote) MXServer
//					.getMXServer().lookup("WORKFLOW");
//
//			wfs.initiateWorkflow("WO", woSet.getMbo(0));
//			System.out.println(">>>>>initiateWorkflow workorder IS OK...");
		}
		woSet.close();
		System.out.println("--------4、设置完毕---------");
		return 1;
	}
	//测试生成工单,用add方法
	public int WOTEST2() throws RemoteException, MXException, ParseException {
		System.out.println("--------1、工单开始生成---------");
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		MboSetRemote woset2 = mainMbo.getMboSet("WORKORDER");
		MboRemote wo = woset2.add();
		System.out.println("--------2、工单生成完毕---------");
		System.out.println("--------3、wonum：" + wo.getString("wonum") + "---------");
		
		SimpleDateFormat dtfmt1 = new SimpleDateFormat("yyyy-MM-dd");
		Date nextdate = mainMbo.getDate("nextdate");
		nextdate = dtfmt1.parse(dtfmt1.format(nextdate));
		Date date = new Date();
		
		wo.setValue("status", "新建", 11l);
		wo.setValue("statusdate", date, 11l);
		wo.setValue("worktype", "预维护工单", 11l);
		wo.setValue("DESCRIPTION", "管理员测试预防性维护功能，请勿处置此工单！", 11L);
		wo.setValue("targcompdate", nextdate, 11l);
		wo.setValue("targstartdate", nextdate, 11l);
		wo.setValue("reportedby", "MAXADMIN", 11l);
		wo.setValue("reportdate", date, 11l);
		wo.setValue("SITEID", mainMbo.getString("SITEID"), 11l);
		
		System.out.println("--------3、得到工单，开始设置---------");
		String location = mainMbo.getString("LOCATION");
		String s_profession = mainMbo.getString("s_profession");
		String SUPERVISOR = mainMbo.getString("SUPERVISOR");
		String TEAMNAME = mainMbo.getString("TEAMNAME");
		String LEAD = mainMbo.getString("LEAD");

		wo.setValue("location", location, 11L);
		wo.setValue("createperson", SUPERVISOR, 11L);
		wo.setValue("LEAD", LEAD, 11L);
		wo.setValue("TEAMNUM", TEAMNAME, 11L);
		wo.setValue("PROFESSION", s_profession, 11L);
		
		wo.setValue("SCHEDSTART", date, 11L);
		wo.setValue("SCHEDFINISH", date, 11L);
//		woset2.save();

//		System.out.println(">>>>>generating workorder IS OK...");
//		WorkFlowServiceRemote wfs = (WorkFlowServiceRemote) MXServer
//				.getMXServer().lookup("WORKFLOW");
//		wfs.initiateWorkflow("WO", wo);
//		System.out.println(">>>>>initiateWorkflow workorder IS OK...");
		System.out.println("--------4、设置完毕---------");
		return 1;
	}
	public boolean hasSaveAuth() throws RemoteException, MXException {
		MboRemote mbo = getMbo();
		long id = mbo.getUniqueIDValue();
		String tableName = mbo.getName().toUpperCase();
		String personid = mbo.getUserInfo().getPersonId();

		String status = mbo.getString("STATUS");
		String createperson = mbo.getString("CREATEPERSON");

		if (personid.equalsIgnoreCase("maxadmin")) {
			return true;
		}

		if (((status.equals("新建")) || (status.equals("等待批准")) || (status.equals("等待核准")))
				&& (personid.equals(createperson))) {
			return true;
		}

		MboSetRemote mbosetremote = mbo.getMboSet("$instance", "WFINSTANCE",
				"ownertable='" + tableName + "' and ownerid='" + id + "' and active = 1");
		if (!mbosetremote.isEmpty()) {
			String sql = "ownerid='" + id + "' and ownertable='" + tableName
					+ "' and assignstatus='活动' and assigncode='" + personid + "'";
			MboSetRemote mbosetremote1 = getMbo().getMboSet("$assigncode", "WFASSIGNMENT", sql);
			return !mbosetremote1.isEmpty();
		}
		return false;
	}

	public boolean hasRoutewfAuth() throws MXException, RemoteException {
		MboRemote mbo = getMbo();
		long id = mbo.getUniqueIDValue();
		String tableName = mbo.getName().toUpperCase();
		String personid = mbo.getUserInfo().getPersonId();
		String status = mbo.getString("STATUS");
		String createperson = mbo.getString("CREATEPERSON");

		if (personid.equalsIgnoreCase("maxadmin")) {
			return true;
		}

		if (((status.equals("新建")) || (status.equals("等待批准")) || (status.equals("等待核准")) || (status.equals("退回修改")))
				&& (personid.equals(createperson))) {
			return true;
		}

		MboSetRemote mbosetremote = mbo.getMboSet("$instance", "WFINSTANCE",
				"ownertable='" + tableName + "' and ownerid='" + id + "' and active = 1");
		if (!mbosetremote.isEmpty()) {
			String sql = "ownerid='" + id + "' and ownertable='" + tableName
					+ "' and assignstatus='活动' and assigncode='" + personid + "'";
			MboSetRemote mbosetremote1 = getMbo().getMboSet("$assigncode", "WFASSIGNMENT", sql);
			return !mbosetremote1.isEmpty();
		}
		return false;
	}
}