
package com.shuto.mam.sis.obft.formulaservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.shuto.mam.sis.obft.formulaservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
/**
 * 
com.shuto.mam.sis.obft.formulaservice.ObjectFactory 江苏
* @author       zhaowei  zhaowei@shuoto.cn
* @date         2017年5月16日 下午5:45:15
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetByFormula_QNAME = new QName("http://service.sis.mam.shuto.com/", "getByFormula");
    private final static QName _GetByFormulaResponse_QNAME = new QName("http://service.sis.mam.shuto.com/", "getByFormulaResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.shuto.mam.sis.obft.formulaservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetByFormula }
     * 
     */
    public GetByFormula createGetByFormula() {
        return new GetByFormula();
    }

    /**
     * Create an instance of {@link GetByFormulaResponse }
     * 
     */
    public GetByFormulaResponse createGetByFormulaResponse() {
        return new GetByFormulaResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetByFormula }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.sis.mam.shuto.com/", name = "getByFormula")
    public JAXBElement<GetByFormula> createGetByFormula(GetByFormula value) {
        return new JAXBElement<GetByFormula>(_GetByFormula_QNAME, GetByFormula.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetByFormulaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.sis.mam.shuto.com/", name = "getByFormulaResponse")
    public JAXBElement<GetByFormulaResponse> createGetByFormulaResponse(GetByFormulaResponse value) {
        return new JAXBElement<GetByFormulaResponse>(_GetByFormulaResponse_QNAME, GetByFormulaResponse.class, null, value);
    }

}
