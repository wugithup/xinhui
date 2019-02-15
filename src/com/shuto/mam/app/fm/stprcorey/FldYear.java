package com.shuto.mam.app.fm.stprcorey;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;
/**
 * 电费结算和电量结算的年份
 com.shuto.mam.app.fm.stprcorey.FldYear 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午8:53:50
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class FldYear extends MAXTableDomain
{
  public FldYear(MboValue mbovalue)
    throws MXException
  {
    super(mbovalue);
    setRelationship("ALNDOMAIN", "");
    String[] strFrom = { "VALUE" };
    String[] strTo = { "YEAR" };
    setLookupKeyMapInOrder(strTo, strFrom);
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    setListCriteria("domainid = 'ST_YEAR' and value >= to_char(sysdate - interval '1' year,'yyyy') and value <= to_char(sysdate + interval '4' year,'yyyy')");
    return super.getList();
  }
}