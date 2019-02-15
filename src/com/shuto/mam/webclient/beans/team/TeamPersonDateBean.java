/**   
* @Title: TeamPersonDateBean.java 
* @Package com.shuto.mam.webclient.beans.team 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lull lull@shuto.cn
* @date 2017年6月19日 下午11:04:04 
* @version V1.0.0
*/
package com.shuto.mam.webclient.beans.team;

import java.rmi.RemoteException;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * @ClassName: TeamPersonDateBean
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lull lull@shuto.cn
 * @date 2017年6月19日 下午11:04:04
 * 
 */

public class TeamPersonDateBean extends DataBean {

	public int toggledeleterow() throws MXException {
		try {
			getMbo().setValue("banzu", "");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		this.app.getAppBean().reloadTable();
		this.app.getAppBean().refreshTable();
		return 1;
	}
}