package com.shuto.mam.app.cron.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory
{
  private static final QName _AddRcjlmx2Response_QNAME = new QName("http://service.batchacceptance.business.crp.com/", "addRcjlmx2Response");
  private static final QName _AddRcjlmxResponse_QNAME = new QName("http://service.batchacceptance.business.crp.com/", "addRcjlmxResponse");
  private static final QName _AddRcjlmx_QNAME = new QName("http://service.batchacceptance.business.crp.com/", "addRcjlmx");
  private static final QName _AddRcjlmx2_QNAME = new QName("http://service.batchacceptance.business.crp.com/", "addRcjlmx2");

  public AddRcjlmx2Response createAddRcjlmx2Response()
  {
    return new AddRcjlmx2Response();
  }

  public AddRcjlmxResponse createAddRcjlmxResponse()
  {
    return new AddRcjlmxResponse();
  }

  public AddRcjlmx2 createAddRcjlmx2()
  {
    return new AddRcjlmx2();
  }

  public AddRcjlmx createAddRcjlmx()
  {
    return new AddRcjlmx();
  }

  @XmlElementDecl(namespace="http://service.batchacceptance.business.crp.com/", name="addRcjlmx2Response")
  public JAXBElement<AddRcjlmx2Response> createAddRcjlmx2Response(AddRcjlmx2Response value)
  {
    return new JAXBElement(_AddRcjlmx2Response_QNAME, AddRcjlmx2Response.class, null, value);
  }

  @XmlElementDecl(namespace="http://service.batchacceptance.business.crp.com/", name="addRcjlmxResponse")
  public JAXBElement<AddRcjlmxResponse> createAddRcjlmxResponse(AddRcjlmxResponse value)
  {
    return new JAXBElement(_AddRcjlmxResponse_QNAME, AddRcjlmxResponse.class, null, value);
  }

  @XmlElementDecl(namespace="http://service.batchacceptance.business.crp.com/", name="addRcjlmx")
  public JAXBElement<AddRcjlmx> createAddRcjlmx(AddRcjlmx value)
  {
    return new JAXBElement(_AddRcjlmx_QNAME, AddRcjlmx.class, null, value);
  }

  @XmlElementDecl(namespace="http://service.batchacceptance.business.crp.com/", name="addRcjlmx2")
  public JAXBElement<AddRcjlmx2> createAddRcjlmx2(AddRcjlmx2 value)
  {
    return new JAXBElement(_AddRcjlmx2_QNAME, AddRcjlmx2.class, null, value);
  }
}