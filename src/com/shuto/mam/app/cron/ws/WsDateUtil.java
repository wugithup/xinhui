package com.shuto.mam.app.cron.ws;

import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class WsDateUtil
{
  public static XMLGregorianCalendar xmlToDate(java.util.Date date)
  {
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(date);
    XMLGregorianCalendar gc = null;
    try {
      gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return gc;
  }

  public static XMLGregorianCalendar xmlToDate(java.sql.Date date) {
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(date);
    XMLGregorianCalendar gc = null;
    try {
      gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return gc;
  }

  public static java.util.Date DateToXML(XMLGregorianCalendar gc)
  {
    GregorianCalendar ca = gc.toGregorianCalendar();
    return ca.getTime();
  }
}