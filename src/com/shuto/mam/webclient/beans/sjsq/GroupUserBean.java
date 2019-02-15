/**   
* @Title: GroupUserAppBean.java 
* @Package com.shuto.mam.webclient.beans.sjsq 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lull lull@shuto.cn
* @date 2017年6月28日 下午5:04:52 
* @version V1.0.0
*/
package com.shuto.mam.webclient.beans.sjsq;

import java.rmi.RemoteException;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/** 
* @ClassName: GroupUserAppBean 
* @Description: 部门管理中   关联安全组 
* @author lull lull@shuto.cn
* @date 2017年6月28日 下午5:04:52 
*  
*/
public class GroupUserBean extends DataBean {
	// com.shuto.mam.webclient.beans.sjsq.GroupUserBean
	// USERCPERGROUP
	/*
	 * (non-Javadoc)
	 * 
	 * @see psdi.webclient.system.beans.DataBean#addrow()
	 */
	@Override
	public int addrow() throws MXException {
		try {
			super.addrow();
			String depnum = getMbo().getOwner().getString("depnum");// 部门
			String postnum = getMbo().getOwner().getString("postnum");// 岗位
			setValue("department", depnum, 11l);
			setValue("postnum", postnum, 11l);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 1;
	}

}
