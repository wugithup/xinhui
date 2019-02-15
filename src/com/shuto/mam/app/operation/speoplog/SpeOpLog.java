/**  
* <p> 此类为运行日志的mbo
* @author       Haox haoxing@shuto.cn
* @date         2012-9-14 下午04:17:49
* 
* @Copyright:   2012 Shuto版权所有
* @version      V1.0  
*/
package com.shuto.mam.app.operation.speoplog;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXException;

/**
 * 航运交接班     SPEOPLOG
 com.shuto.mam.app.operation.speoplog.SpeOpLog 江苏
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月17日 上午10:58:58
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class SpeOpLog extends Mbo implements SpeOpLogRemote {
	
	/**
	 * <p>此方法为构造方法
	 */
	public SpeOpLog(MboSet ms) throws RemoteException {
		super(ms);
	}
	/**
	 * <p>此方为运行日志的初始化方法，功能为验证当前用户是否有权限填写数据
	 * 控制权限
	 * @throws MXException
	 */
	public void init() throws MXException{
		super.init();
		String status = getMboValue("status").getString();
		try {
			if ("CLOSE".equalsIgnoreCase(status)||"INVALID".equalsIgnoreCase(status)) {
				setFlag(7L, true);
			} else {
				MboRemote oplog = this.getMboValue("status").getMbo();
				setReadOnly(oplog);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void setReadOnly(MboRemote oplog) throws RemoteException, MXException {
		if (!(oplog == null)) {
			// 获得当前登录人的personid
			String login2 = oplog.getUserInfo().getPersonId();
			String dbPerson=oplog.getString("dbperson");
			// 如果当前用户与当前运行日志的类型 或是班值不对应则设置只读
			if (!login2.equals(dbPerson)&&!"MAXADMIN".equalsIgnoreCase(login2)) {
				//设置子表为只读
				oplog.getMboSet("speoplognotes").setFlag(7L,true);
			}
		}
	}
}
