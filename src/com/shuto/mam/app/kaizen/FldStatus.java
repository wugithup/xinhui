package com.shuto.mam.app.kaizen;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
/**
 * KAIZEN项目提案       YX_REGISTR
 com.shuto.mam.app.kaizen.FldStatus 江苏
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月17日 上午10:17:26
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class FldStatus extends MboValueAdapter {


	private String defaultstatus = "草稿";
	
	@Override
	public void init() throws MXException, RemoteException {
		super.init();
		if(!getMboValue().getMbo().isNew()){
			this.initState();
		}
	}

	@Override
	public void action() throws MXException, RemoteException {
		super.action();
		if(!getMboValue().getMbo().isNew()){
			this.initState();
			this.checkScore();
		}
		if("作废".equals(this.getMboValue().getMbo().getString("STATUS"))){
			this.getMboValue().getMbo().setValue("EFFECTIVE", false,2L);
		}
	}
	public FldStatus() {
		super();
	}

	public FldStatus(MboValue mbv) {
		super(mbv);
	}

	/**
	 * 检查权限的方法
	 * @param mbo 当前对象
	 * @return
	 * @throws MXException
	 * @throws RemoteException
	 */
	public boolean checkAuth(MboRemote mbo) throws MXException, RemoteException {
		long ownerid = mbo.getLong("REGISTRATIONID");
		String personid = mbo.getUserInfo().getPersonId();
		String status = getMboValue().getString();
		String createPeron = getMboValue().getMbo().getString("REQUESTBY");
		if (personid.equalsIgnoreCase("maxadmin")){
			return true;
		}
		if(defaultstatus.equals(status) &&!personid.equals(createPeron)) { //如果状态是草稿状态但是创建人不是当前登陆人的话不允许修改
			return false ; 
		}
		if (defaultstatus.equals(status) &&personid.equals(createPeron)){
			return true;
		}
			
		MboSetRemote mbosetremote = mbo.getMboSet(
				"$instance",
				"WFINSTANCE",
				"ownertable='REGISTRATION' and ownerid='" + ownerid
						+ "' and active = 1");
		if (!(mbosetremote.isEmpty())) {
			String s2 = "ownerid='"
					+ ownerid
					+ "' and ownertable='REGISTRATION' and assignstatus='活动' and assigncode='"
					+ personid + "'";
			MboSetRemote mbosetremote1 = mbo.getMboSet("$assigncode",
					"WFASSIGNMENT", s2);
			return (!(mbosetremote1.isEmpty()));
		}
		return false;
	}
	
	public void initState() throws RemoteException, MXException {
		if(!this.checkAuth(getMboValue().getMbo())){ //如果没有权限，则本条设置为只读
			getMboValue().getMbo().setFlag(7L, Boolean.TRUE); 
		}else{
			getMboValue().getMbo().setFlag(7L, Boolean.FALSE); 
		}
	}

	public void checkScore() throws RemoteException, MXException{
		MboRemote mbo  = getMboValue().getMbo(); //获得当前MBO
		String siteid = mbo.getString("siteid");  //获得siteid
			if("徐州项目".equals(siteid)||"华鑫电厂".equals(siteid)){
				String mode = mbo.getString("COMPLETEMODE"); //获取当前的完成形式
				int score = 0 ; //默认得分为0 
				Boolean  flag  =mbo.getBoolean("EFFECTIVE"); //查看是否有效 无效得0分
				String status  = mbo.getString("status"); //查看当前状态
				if("完成".equals(status)){
					if(!flag){
						score = 0 ; 
					}
					if("本人".equals(mode)){
						score = 2 ; 
					}else if("他人".equals(mode)){
						score =1 ; 
					}
					mbo.setValue("SCORE", score,2L);
				}else{
					mbo.setValue("SCORE", 0,2L);
				}
			}
	}
}
