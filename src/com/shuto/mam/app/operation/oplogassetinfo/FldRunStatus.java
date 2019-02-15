/**  
* <p> 此类为运行交接班设备状态的Fld类
* @author       wuqi@shuto.cn
* @date         2017-05-08 下午04:17:49
* 
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
package com.shuto.mam.app.operation.oplogassetinfo;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**  
* <p> 此类为运行交接班设备状态的Fld类，用来控制其他两个字段的属性
* @author       wuqi@shuto.cn
* @date         2017-05-08 下午04:17:49
* 
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class FldRunStatus  extends MboValueAdapter {
	/**
	 * <p>此构造方法必须添加
	 * @param mbv  maximo封装好的获得属性值的类
	 */
	public FldRunStatus(MboValue mbv) {
		super(mbv);
	}
	/**
     * <p>    此方法用于字段发生变更时触发，控制其他两个字段属性值
     * @throws    MXException
     * @throws    RemoteException
     */
	public void action() throws MXException, RemoteException {
		super.action();
		MboRemote mbo = getMboValue().getMbo();
		   if(getMboValue("RUN").getBoolean()){
			   mbo.setValue(("OVERHAUL"),"0");
			   mbo.setValue(("BACKUP"),"0"); 
//			   mbo.setValue(("FAULT"),"0");
		   }
		   
	}
}
