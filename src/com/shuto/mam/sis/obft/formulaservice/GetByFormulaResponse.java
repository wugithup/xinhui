
package com.shuto.mam.sis.obft.formulaservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>getByFormulaResponse complex type�� Java �ࡣ
 * 
 * 
 * <pre>
 * &lt;complexType name="getByFormulaResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
/**
 * 
com.shuto.mam.sis.obft.formulaservice.GetByFormulaResponse 江苏
* @author       zhaowei  zhaowei@shuoto.cn
* @date         2017年5月16日 下午5:45:03
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getByFormulaResponse", propOrder = {
    "_return"
})
public class GetByFormulaResponse {

    @XmlElement(name = "return")
    protected String _return;

    /**
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturn() {
        return _return;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturn(String value) {
        this._return = value;
    }

}
