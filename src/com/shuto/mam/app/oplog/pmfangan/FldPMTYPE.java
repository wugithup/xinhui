package com.shuto.mam.app.oplog.pmfangan;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboValue;
import psdi.util.MXException;
/**
 * 混配煤方案
 com.shuto.mam.app.oplog.pmfangan.FldPMTYPE 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月4日 下午10:33:15
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class FldPMTYPE extends MAXTableDomain
{
  public FldPMTYPE(MboValue mbv)
    throws MXException, RemoteException
  {
    super(mbv);

    String where = "siteid=:siteid and yesorno=1";
    setRelationship("ST_PMFANGANZB2", where);
    setListCriteria(where);
    setLookupKeyMapInOrder(new String[] { "TYPE" }, new String[] { "SID", "TYPE", "REZHI", "HANSHUI", "HFF", "HUISHUI", "LIUFEN", "PCNUM" });
  }
}