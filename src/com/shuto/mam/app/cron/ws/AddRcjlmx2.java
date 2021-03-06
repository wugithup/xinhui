package com.shuto.mam.app.cron.ws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addRcjlmx2", propOrder = { "psw", "oRgId", "oRgSub", "rOleId",
		"lEgalOrgId", "jlType", "jlDt2", "jlSd", "jlXh", "jlCch", "jlFzm",
		"jlGys", "jlHwm", "jlShf", "jlCx", "qTyMzFh", "qTyZzFh", "qTyJzFh",
		"qTyPz", "qTyMz", "qTyZz", "qTyJz", "jlJlr", "jlNote", "jlId", "rEfId",
		"ySjlQltKq", "ySjlQltKs", "jlTimes", "sPecType", "unLoadArea",
		"vArAttr1", "vArAttr2", "vArAttr3", "vArAttr4", "nUmAttr1", "nUmAttr2",
		"dAteAttr1", "dAteAttr2" })
public class AddRcjlmx2 {
	protected String psw;
	protected Long oRgId;
	protected String oRgSub;
	protected Long rOleId;
	protected Long lEgalOrgId;

	@XmlElement(name = "jLType")
	protected Long jlType;

	@XmlElement(name = "jLDt2")
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar jlDt2;

	@XmlElement(name = "jLSd")
	protected BigDecimal jlSd;

	@XmlElement(name = "jLXh")
	protected Long jlXh;

	@XmlElement(name = "jLCch")
	protected String jlCch;

	@XmlElement(name = "jLFzm")
	protected String jlFzm;

	@XmlElement(name = "jLGys")
	protected String jlGys;

	@XmlElement(name = "jLHwm")
	protected String jlHwm;

	@XmlElement(name = "jLShf")
	protected String jlShf;

	@XmlElement(name = "jLCx")
	protected String jlCx;
	protected BigDecimal qTyMzFh;
	protected BigDecimal qTyZzFh;
	protected BigDecimal qTyJzFh;
	protected BigDecimal qTyPz;
	protected BigDecimal qTyMz;
	protected BigDecimal qTyZz;
	protected BigDecimal qTyJz;

	@XmlElement(name = "jLJlr")
	protected String jlJlr;

	@XmlElement(name = "jLNote")
	protected String jlNote;

	@XmlElement(name = "jLId")
	protected String jlId;
	protected String rEfId;
	protected BigDecimal ySjlQltKq;
	protected BigDecimal ySjlQltKs;

	@XmlElement(name = "jLTimes")
	protected String jlTimes;
	protected String sPecType;

	@XmlElement(name = "uNLoadArea")
	protected String unLoadArea;
	protected String vArAttr1;
	protected String vArAttr2;
	protected String vArAttr3;
	protected String vArAttr4;
	protected BigDecimal nUmAttr1;
	protected BigDecimal nUmAttr2;

	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar dAteAttr1;

	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar dAteAttr2;

	public String getPsw() {
		return this.psw;
	}

	public void setPsw(String value) {
		this.psw = value;
	}

	public Long getORgId() {
		return this.oRgId;
	}

	public void setORgId(Long value) {
		this.oRgId = value;
	}

	public String getORgSub() {
		return this.oRgSub;
	}

	public void setORgSub(String value) {
		this.oRgSub = value;
	}

	public Long getROleId() {
		return this.rOleId;
	}

	public void setROleId(Long value) {
		this.rOleId = value;
	}

	public Long getLEgalOrgId() {
		return this.lEgalOrgId;
	}

	public void setLEgalOrgId(Long value) {
		this.lEgalOrgId = value;
	}

	public Long getJLType() {
		return this.jlType;
	}

	public void setJLType(Long value) {
		this.jlType = value;
	}

	public XMLGregorianCalendar getJLDt2() {
		return this.jlDt2;
	}

	public void setJLDt2(XMLGregorianCalendar value) {
		this.jlDt2 = value;
	}

	public BigDecimal getJLSd() {
		return this.jlSd;
	}

	public void setJLSd(BigDecimal value) {
		this.jlSd = value;
	}

	public Long getJLXh() {
		return this.jlXh;
	}

	public void setJLXh(Long value) {
		this.jlXh = value;
	}

	public String getJLCch() {
		return this.jlCch;
	}

	public void setJLCch(String value) {
		this.jlCch = value;
	}

	public String getJLFzm() {
		return this.jlFzm;
	}

	public void setJLFzm(String value) {
		this.jlFzm = value;
	}

	public String getJLGys() {
		return this.jlGys;
	}

	public void setJLGys(String value) {
		this.jlGys = value;
	}

	public String getJLHwm() {
		return this.jlHwm;
	}

	public void setJLHwm(String value) {
		this.jlHwm = value;
	}

	public String getJLShf() {
		return this.jlShf;
	}

	public void setJLShf(String value) {
		this.jlShf = value;
	}

	public String getJLCx() {
		return this.jlCx;
	}

	public void setJLCx(String value) {
		this.jlCx = value;
	}

	public BigDecimal getQTyMzFh() {
		return this.qTyMzFh;
	}

	public void setQTyMzFh(BigDecimal value) {
		this.qTyMzFh = value;
	}

	public BigDecimal getQTyZzFh() {
		return this.qTyZzFh;
	}

	public void setQTyZzFh(BigDecimal value) {
		this.qTyZzFh = value;
	}

	public BigDecimal getQTyJzFh() {
		return this.qTyJzFh;
	}

	public void setQTyJzFh(BigDecimal value) {
		this.qTyJzFh = value;
	}

	public BigDecimal getQTyPz() {
		return this.qTyPz;
	}

	public void setQTyPz(BigDecimal value) {
		this.qTyPz = value;
	}

	public BigDecimal getQTyMz() {
		return this.qTyMz;
	}

	public void setQTyMz(BigDecimal value) {
		this.qTyMz = value;
	}

	public BigDecimal getQTyZz() {
		return this.qTyZz;
	}

	public void setQTyZz(BigDecimal value) {
		this.qTyZz = value;
	}

	public BigDecimal getQTyJz() {
		return this.qTyJz;
	}

	public void setQTyJz(BigDecimal value) {
		this.qTyJz = value;
	}

	public String getJLJlr() {
		return this.jlJlr;
	}

	public void setJLJlr(String value) {
		this.jlJlr = value;
	}

	public String getJLNote() {
		return this.jlNote;
	}

	public void setJLNote(String value) {
		this.jlNote = value;
	}

	public String getJLId() {
		return this.jlId;
	}

	public void setJLId(String value) {
		this.jlId = value;
	}

	public String getREfId() {
		return this.rEfId;
	}

	public void setREfId(String value) {
		this.rEfId = value;
	}

	public BigDecimal getYSjlQltKq() {
		return this.ySjlQltKq;
	}

	public void setYSjlQltKq(BigDecimal value) {
		this.ySjlQltKq = value;
	}

	public BigDecimal getYSjlQltKs() {
		return this.ySjlQltKs;
	}

	public void setYSjlQltKs(BigDecimal value) {
		this.ySjlQltKs = value;
	}

	public String getJLTimes() {
		return this.jlTimes;
	}

	public void setJLTimes(String value) {
		this.jlTimes = value;
	}

	public String getSPecType() {
		return this.sPecType;
	}

	public void setSPecType(String value) {
		this.sPecType = value;
	}

	public String getUNLoadArea() {
		return this.unLoadArea;
	}

	public void setUNLoadArea(String value) {
		this.unLoadArea = value;
	}

	public String getVArAttr1() {
		return this.vArAttr1;
	}

	public void setVArAttr1(String value) {
		this.vArAttr1 = value;
	}

	public String getVArAttr2() {
		return this.vArAttr2;
	}

	public void setVArAttr2(String value) {
		this.vArAttr2 = value;
	}

	public String getVArAttr3() {
		return this.vArAttr3;
	}

	public void setVArAttr3(String value) {
		this.vArAttr3 = value;
	}

	public String getVArAttr4() {
		return this.vArAttr4;
	}

	public void setVArAttr4(String value) {
		this.vArAttr4 = value;
	}

	public BigDecimal getNUmAttr1() {
		return this.nUmAttr1;
	}

	public void setNUmAttr1(BigDecimal value) {
		this.nUmAttr1 = value;
	}

	public BigDecimal getNUmAttr2() {
		return this.nUmAttr2;
	}

	public void setNUmAttr2(BigDecimal value) {
		this.nUmAttr2 = value;
	}

	public XMLGregorianCalendar getDAteAttr1() {
		return this.dAteAttr1;
	}

	public void setDAteAttr1(XMLGregorianCalendar value) {
		this.dAteAttr1 = value;
	}

	public XMLGregorianCalendar getDAteAttr2() {
		return this.dAteAttr2;
	}

	public void setDAteAttr2(XMLGregorianCalendar value) {
		this.dAteAttr2 = value;
	}
}