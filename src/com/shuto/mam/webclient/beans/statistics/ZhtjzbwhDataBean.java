/**
 * 
 */
package com.shuto.mam.webclient.beans.statistics;

import java.rmi.RemoteException;
import java.util.Map;

import com.shuto.mam.sis.service.impl.FormulaServiceImpl;


import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * @author Administrator
 *
 */
public class ZhtjzbwhDataBean extends DataBean {
	
	public int addrow() throws MXException {
		return super.addrow();
	}
	
	/**
	 * 验证公式
	 * @return
	 * @throws Exception 
	 */
	public int checkformula() {
		DataBean resultsBean = this.app.getDataBean("1426312218488");
		MboSetRemote mboset;
		try {
			mboset = resultsBean.getMboSet();
			MboRemote selectmbo = mboset.getMbo(getCurrentRow());
			String formula = selectmbo.getString("formula");
			String orgid = selectmbo.getString("orgid");
			if ("取SIS值".equals(selectmbo.getString("type")) && !"".equals(formula.trim())) {
				FormulaServiceImpl formulaService = new FormulaServiceImpl(orgid);
				Map<String, Object> retmap = formulaService.getByFormulaAsMap(formula);
				Double ret = Double.valueOf(retmap.get("RET").toString());
				String msg = (String) retmap.get("MSG");
				if (msg != null && !"null".equals(msg)) {
					selectmbo.setValue("checkvalue", "");
					selectmbo.setValue("active", 0);
					mboset.save();
					refreshTable();
					throw new MXApplicationException("消息提示",msg);
				} else {
					selectmbo.setValue("checkvalue", ret);
					selectmbo.setValue("active", 1);
					mboset.save();
					refreshTable();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return 1;
	}
}
