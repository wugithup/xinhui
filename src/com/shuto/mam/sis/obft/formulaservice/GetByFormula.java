
package com.shuto.mam.sis.obft.formulaservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>getByFormula complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�����ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="getByFormula">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
com.shuto.mam.sis.obft.formulaservice.GetByFormula 江苏
* @author       zhaowei  zhaowei@shuoto.cn
* @date         2017年5月16日 下午5:44:49
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getByFormula", propOrder = {
    "arg0"
})
public class GetByFormula {

    protected String arg0;

    /**
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArg0() {
        return arg0;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArg0(String value) {
        this.arg0 = value;
    }

}
