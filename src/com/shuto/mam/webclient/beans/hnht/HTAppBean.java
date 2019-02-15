package com.shuto.mam.webclient.beans.hnht;


import com.shuto.mam.webclient.beans.base.CAppBean;
import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
/**
* @author       lzq liuzq@shuoto.cn
* @Description  华南合同
* @date         2017-6-7 上午11:12:31
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class HTAppBean extends CAppBean
{
  private String ATTR_ID;

public HTAppBean()
  {
    this.OWNERTABLE = "HT";
    this.ATTR_ID = (this.OWNERTABLE + "ID");
  }

  public int INSERT() throws MXException, RemoteException {
    super.INSERT();

    String orgid = getMbo().getString("orgid");
    //横沥项目    西江项目   无此地点
    if ((orgid.equals("CRPHL")) || (orgid.equals("CRPXJ"))) {
      MboSetRemote htapprs = getMbo().getMboSet("htappr");

      MboSetRemote depts = getMbo().getMboSet("$department", "department", 
        //"siteid='" + getMbo().getString("siteid") + "' and parent is not null " +
    	"siteid='" + getMbo().getString("siteid") +"and department<>'" + getMbo().getString("createdby.topdepartment") + "'");
      for (int i = 0; i < depts.count(); ++i) {
        MboRemote htappr = htapprs.add();
        htappr.setValue("department", depts.getMbo(i).getString("department"));
        htappr.setValue("siteid", depts.getMbo(i).getString("siteid"));
        htappr.setValue("htnum", getMbo().getString("htnum"));
      }
      refreshTable();
    }
    return 1;
  }
}