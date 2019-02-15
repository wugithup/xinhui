package com.shuto.mam.webclient.beans.purchase.rfq;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import psdi.app.rfq.RFQRemote;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

public class PRLineBean extends DataBean
{
  private Map<Integer, Integer> delList;

  public PRLineBean()
  {
    this.delList = new HashMap();
  }
  public MboSetRemote getMboSetRemote() throws MXException, RemoteException {

    MboSetRemote localMboSetRemote2 = null;
    String str2 = this.parent.getMbo().getString("siteid");
    String str3 = "ponum is null and rfqnum is null and contractnum is null and mktplcitem=0 and prnum in (select prnum from pr where appname = 'PR' and status in ('已核准','已批准')) and siteid = '" + str2 + "'" + "and status is null";

    System.out.println("query---------:" + str3);
    localMboSetRemote2 = this.parent.getMbo().getMboSet("$prline", "prline", str3);
    localMboSetRemote2.setOrderBy("siteid, prnum, prlinenum");
    return localMboSetRemote2;
  }

  public int execute() throws MXException, RemoteException {
    RFQRemote localRFQRemote = (RFQRemote)this.parent.getMbo();

    MboSetRemote localMboSetRemote1 = getMboSet();

    localMboSetRemote1.setOrderBy("siteid");

    localRFQRemote.copyPRToCurrentRFQ(localMboSetRemote1);
    this.parent.save();

    MboSetRemote localMboSetRemote2 = localRFQRemote.getMboSet("RFQLINE");

    for (int i = 0; i < localMboSetRemote2.count(); i++) {
      Object localObject1 = localMboSetRemote2.getMbo(i);
      MboSetRemote localMboSetRemote3 = ((MboRemote)localObject1).getMboSet("refprline");
      if (localMboSetRemote3.count() == 0)
        throw new MXApplicationException("rfq", "notfoundrefprline");
      localMboSetRemote3.getMbo(0).setValue("rfqorg", localRFQRemote.getString("orgid"));
      localMboSetRemote3.save();
    }

    for (int i = 0; i < localMboSetRemote2.count(); i++) {
      Object localObject1 = localMboSetRemote2.getMbo(i);
      if (((MboRemote)localObject1).toBeDeleted()) {
        continue;
      }
      ((MboRemote)localObject1).setValue("rfqlinenum", i + 1);

      if (((MboRemote)localObject1).getString("linetype").equals("项目")) {
        for (int j = i + 1; j < localMboSetRemote2.count(); j++) {
          if (((MboRemote)localObject1).toBeDeleted())
            continue;
          MboRemote localMboRemote1 = localMboSetRemote2.getMbo(j);
          Object localObject2 = localMboRemote1.getString("itemnum");

          if (!((String)localObject2).equals(((MboRemote)localObject1).getString("itemnum"))) {
            continue;
          }
          ((MboRemote)localObject1).setValue("orderqty", ((MboRemote)localObject1).getDouble("orderqty") + localMboRemote1.getDouble("orderqty"));

          if ((localMboRemote1.getString("remark") != null) && (!localMboRemote1.getString("remark").equals("")))
          {
            if ((((MboRemote)localObject1).getString("remark") == null) || (((MboRemote)localObject1).getString("remark").equals("")))
            {
              ((MboRemote)localObject1).setValue("remark", localMboRemote1.getString("remark"));
            }
            else {
              Object localObject3 = ((MboRemote)localObject1).getString("remark") + "; " + localMboRemote1.getString("remark");

              ((MboRemote)localObject1).setValue("remark", (String)localObject3);
            }

          }

          Object localObject3 = localMboRemote1.getMboSet("refprline");

          if (((MboSetRemote)localObject3).count() == 0) {
            throw new MXApplicationException("rfq", "notfoundrefprline");
          }
          this.delList.put(Integer.valueOf(((MboSetRemote)localObject3).getMbo(0).getInt("prlineid")), Integer.valueOf(((MboRemote)localObject1).getInt("rfqlineid")));

          localMboRemote1.delete();
        }
      }
    }
    localMboSetRemote2.save();
    this.app.getAppBean().save();

    Iterator localIterator = this.delList.entrySet().iterator();
    while (localIterator.hasNext()) {
      Object localObject1 = (Map.Entry)localIterator.next();
      int j = ((Integer)((Map.Entry)localObject1).getKey()).intValue();
      int k = ((Integer)((Map.Entry)localObject1).getValue()).intValue();

      Object localObject2 = this.parent.getMbo().getMboSet("$prline", "prline", "prlineid=" + j);

      Object localObject3 = this.parent.getMbo().getMboSet("$rfqline", "rfqline", "rfqlineid=" + k);

      MboRemote localMboRemote2 = ((MboSetRemote)localObject2).getMbo(0);
      MboRemote localMboRemote3 = ((MboSetRemote)localObject3).getMbo(0);

      localMboRemote2.setFlag(7L, false);
      localMboRemote2.setValue("rfqnum", localMboRemote3.getString("rfqnum"), 2L);
      localMboRemote2.setValue("rfqlinenum", localMboRemote3.getInt("rfqlinenum"), 2L);
      localMboRemote2.setValue("rfqlineid", localMboRemote3.getInt("rfqlineid"), 2L);
      localMboRemote2.setValue("rfqorg", localRFQRemote.getString("orgid"), 2L);
      ((MboSetRemote)localObject2).save();
    }
    return 2;
  }
}