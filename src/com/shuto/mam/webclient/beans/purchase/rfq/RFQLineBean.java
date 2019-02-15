package com.shuto.mam.webclient.beans.purchase.rfq;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * @author xiamy
 * @email xiamy@shuto.cn
 * @create 2017-11-29 9:45
 * @desc 
 * @class com.shuto.mam.webclient.beans.purchase.rfq.RFQLineBean
 * @Copyright: 2017 Shuto版权所有
 **/

public class RFQLineBean extends DataBean
{
  public int toggledeleterow()
    throws MXException
  {
    try
    {
      String where = "rfqorg='" + this.parent.getMbo().getString("orgid") + "' and " + 
        "rfqnum='" + this.parent.getMbo().getString("rfqnum") + "' and " + 
        "rfqlinenum=" + getMbo().getInt("rfqlinenum");

      super.toggledeleterow();
      this.app.getAppBean().save();

      MboSetRemote refprlines = this.parent.getMbo().getMboSet("$prlinesforrfq", "prline", where);
      for (int i = 0; i < refprlines.count(); i++) {
        MboRemote refprline = refprlines.getMbo(i);
        refprline.setFlag(7L, false);
        refprline.setValueNull("rfqnum", 2L);
        refprline.setValueNull("rfqlinenum", 2L);
        refprline.setValueNull("rfqlineid", 2L);
        refprline.setValueNull("rfqorg", 2L);
      }
      refprlines.save();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    this.app.getAppBean().save();
    return 1;
  }
}
