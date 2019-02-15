/**   
* @Title: PersonGroupBean.java 
* @Package com.shuto.mam.webclient.beans.sjsq 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lull lull@shuto.cn
* @date 2017年6月28日 下午5:22:00 
* @version V1.0.0
*/
package com.shuto.mam.webclient.beans.sjsq;

import java.rmi.RemoteException;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/** 
* @ClassName: PersonGroupBean 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author lull lull@shuto.cn
* @date 2017年6月28日 下午5:22:00 
*  
*/
public class PersonGroupBean extends DataBean {
	// com.shuto.mam.webclient.beans.sjsq.PersonGroupBean

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
			setValue("depnum", depnum, 11l);
			setValue("postnum", postnum, 11l);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 1;
	}

}
