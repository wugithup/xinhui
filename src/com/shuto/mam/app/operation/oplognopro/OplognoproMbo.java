package com.shuto.mam.app.operation.oplognopro;

import java.rmi.RemoteException;
import java.util.Iterator;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.mbo.MboValueInfo;
import psdi.util.MXException;

/**   
 * @Title: OplognoproMbo.java 
 * @Package com.shuto.mam.app.operation.oplognopro 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author wuqi   
 * @date 2017-5-8 下午04:10:43 
 * @version V1.0   
 */
public class OplognoproMbo extends Mbo
  implements OplognoproMboRemote
{
  public OplognoproMbo(MboSet ms)
    throws RemoteException
  {
    super(ms);
  }

  public void init()
    throws MXException
  {
    super.init();
    try {
      String createPerson = getString("CREATEPERSON");
      String tsPerson = getString("TSPERSON");
      String personId = getUserInfo().getPersonId();
      if ((personId.equals(createPerson)) || (personId.equals(tsPerson)) || ("maxadmin".equalsIgnoreCase(personId))) {
        return;
      }
      Iterator it = getMboSetInfo().getAttributes();
      while (it.hasNext()) {
        MboValueInfo next = (MboValueInfo)it.next();
        setFieldFlag(next.getAttributeName(), 7L, true);
      }

    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }
}