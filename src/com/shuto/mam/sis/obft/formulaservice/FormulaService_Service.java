package com.shuto.mam.sis.obft.formulaservice;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

import com.shuto.mam.sis.service.FormulaService;
import com.shuto.mam.sis.util.Property;

/**
 * This class was generated by Apache CXF 2.7.15
 * 2015-03-26T19:41:40.278+08:00
 * Generated source version: 2.7.15
 * 
 */
/**
 * 
com.shuto.mam.sis.obft.formulaservice.FormulaService_Service 江苏
* @author       zhaowei  zhaowei@shuoto.cn
* @date         2017年5月16日 下午5:44:40
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */

public class FormulaService_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.service.sis.mam.shuto.com/", "FormulaService");
    public final static QName FormulaServiceImplPort = new QName("http://impl.service.sis.mam.shuto.com/", "FormulaServiceImplPort");
    static {
        URL url = null;
        Property pro = new Property();
        try {
            url = new URL(pro.getServerurl()+"/formulaService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(FormulaService_Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", pro.getServerurl()+"/formulaService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public FormulaService_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public FormulaService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public FormulaService_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public FormulaService_Service(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public FormulaService_Service(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public FormulaService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName);
    }

    /**
     *
     * @return
     *     returns FormulaService
     */
    @WebEndpoint(name = "FormulaServiceImplPort")
    public FormulaService getFormulaServiceImplPort() {
        return super.getPort(FormulaServiceImplPort, FormulaService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns FormulaService
     */
    @WebEndpoint(name = "FormulaServiceImplPort")
    public FormulaService getFormulaServiceImplPort(WebServiceFeature... features) {
        return super.getPort(FormulaServiceImplPort, FormulaService.class, features);
    }

}