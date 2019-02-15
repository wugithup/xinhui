package com.shuto.mam.webclient.controls;

import java.rmi.RemoteException;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

public class DataBeanUtils extends DataBean {
	/**
	 * @param i
	 * @param tableBean
	 * @throws MXException 
	 * @throws RemoteException 
	 */
	public static void movetorow(int i, DataBean dataBean) throws RemoteException, MXException {
		int pageEndRowTemp = -1;
		int rownum = i;
		if (dataBean.getMboSet().count() < rownum) {
			rownum = dataBean.getMboSet().count();
		}
		while (dataBean.getEndRow() < rownum || dataBean.getEndRow() - dataBean.getPageRowCount() > rownum) {
			if (dataBean.getEndRow() < rownum) {
				dataBean.scrollnext();// 下一页
			} else {
				dataBean.scrollprev();// 上一页
			}
			if (pageEndRowTemp == dataBean.getEndRow()) {
				break;
			}
			pageEndRowTemp = dataBean.getEndRow();
		}
//		rownum = dataBean.getCurrentRow() + 1;
	}

}