package com.shuto.mam.app.hyjy;
/**  
com.shuto.mam.app.hyjy.FldBzspr 河南分公司  登封电厂
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年5月5日 下午3:02:04
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;

import psdi.app.person.FldPersonID;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldBzspr extends FldPersonID
{
  public FldBzspr(MboValue mbovalue)
    throws MXException
  {
    super(mbovalue);
    String[] strFrom = { "personid" };
    String[] strTo = { "bzspr" };
    setLookupKeyMapInOrder(strTo, strFrom);
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    String gzptype = getMboValue().getMbo().getThisMboSet().getApp();

    String thisAttr = getMboValue().getAttributeName();
    String CREATEPERSON = getMboValue("CREATEPERSON").getString();
    String siteid = getMboValue("siteid").getString();

    setRelationship("PERSON", "personid=:" + thisAttr);
    if ("FDBMLTZ".equals(gzptype))
      setListCriteria("personid in(select personid from SR_PERSONZB where zbsid in(select sid from sr_person where department in( select department from person where personid='" + CREATEPERSON + "') and persongroup in('副部长','部长')) and siteid='"+siteid+"' group by personid)");
    else {
      setListCriteria("personid in(select personid from SR_PERSONZB where zbsid in(select sid from sr_person where department in( select department from person where personid='" + CREATEPERSON + "') and persongroup in('副部长','部长')) and siteid='"+siteid+"' group by personid)");
    }

    return super.getList();
  }
}
