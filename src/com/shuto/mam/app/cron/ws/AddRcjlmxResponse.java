 package com.shuto.mam.app.cron.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="addRcjlmxResponse", propOrder={"_return"})
public class AddRcjlmxResponse
{

  @XmlElement(name="return")
  protected List<String> _return;

  public List<String> getReturn()
  {
    if (this._return == null) {
      this._return = new ArrayList();
    }
    return this._return;
  }
}