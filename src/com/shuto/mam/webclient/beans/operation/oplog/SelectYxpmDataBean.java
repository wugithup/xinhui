package com.shuto.mam.webclient.beans.operation.oplog;

import java.rmi.RemoteException;
import java.util.Vector;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**   
 * @Title: SelectYxpmDataBean.java 
 * @Package com.shuto.mam.webclient.beans.operation.oplog 
 * @Description: TODO(一键取消定期工作) 
 * @author wuqi   
 * @date 2017-6-30 上午10:53:42 
 * @version V1.0   
 */
public class SelectYxpmDataBean  extends DataBean {

	public MboSetRemote getMboSetRemote() throws MXException, RemoteException {
		MboRemote Mbo = this.app.getAppBean().getMbo();

	    mboSetRemote = Mbo.getMboSet("WORKORDER_TASK");
	
		return mboSetRemote;
	}

	public int execute() throws MXException, RemoteException {
		save();
		super.execute();
		MboRemote oplog = app.getAppBean().getMbo();
		String personid = oplog.getUserInfo().getPersonId();
		
		Vector LineSet = this.app.getDataBean("WORKORDER").getMboSet().getSelection();
	    MboRemote workorder = null;

	    if (!LineSet.isEmpty()) {
	      for (int i = 0; i < LineSet.size(); ++i) {
//	    	  System.out.println("选中的数量=="+LineSet.size());
	    	  workorder = (MboRemote)LineSet.elementAt(i);
	    	  workorder.setValue("OPLOG_CANCELYN", true, 11L);
	    	  workorder.setValue("OPLOG_QXPERSON", personid, 11L);
	    	  workorder.setValue("OPLOG_CANCELCAUSE", "机组停机", 11L);
	      }
	      if(workorder !=null){
	    	  workorder.getThisMboSet().save();
	      }
	      this.app.getAppBean().save();
	    }
		return 1;
	}
}

