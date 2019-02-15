/**
 * 
 */
package com.shuto.mam.sis.service.impl;

import java.util.Map;

import com.shuto.mam.sis.obft.formulaservice.FormulaService_Service;
import com.shuto.mam.sis.service.FormulaService;
import com.shuto.mam.sis.util.Property;
import com.shuto.mam.sis.util.SoapClint;

/**
 * 
com.shuto.mam.sis.service.impl.FormulaServiceImpl 江苏
* @author       zhaowei  zhaowei@shuoto.cn
* @date         2017年5月16日 下午5:31:40
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class FormulaServiceImpl implements FormulaService {

	private FormulaService_Service bean;
	private FormulaService formulaService;
	private SoapClint soapClint;
	
	public FormulaServiceImpl() {
		bean = new FormulaService_Service();
		formulaService = bean.getFormulaServiceImplPort();
	}
	
	public FormulaServiceImpl(String siteid) {
		Property pro = new Property();
		String url = pro.getServerurl(siteid)+"/soap_formulaService";
		soapClint = new SoapClint(url);
	}
	
	/* (non-Javadoc)
	 * @see com.shuto.mam.sis.service.FormulaService#getByFormula(java.lang.String)
	 */
	@Override
	public String getByFormula(String funcStr) {
		// TODO Auto-generated method stub
		return formulaService.getByFormula(funcStr);
	}

	/**
	 * 通过HttpURLConnection调用SOAP服务
	 * @param funcStr
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getByFormulaAsMap(String funcStr) throws Exception {
		Map<String,Object> retmap = soapClint.callWebService(funcStr);
		return retmap;
	}
}
