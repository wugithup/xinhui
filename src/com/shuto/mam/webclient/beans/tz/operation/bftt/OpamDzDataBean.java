package com.shuto.mam.webclient.beans.tz.operation.bftt;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:控股台账
 */
public class OpamDzDataBean extends DataBean
{
  public int addSonRow()
    throws MXException, RemoteException
  {
    super.addrow();

    getMbo().setValue("OPMA_BHTTJDZXGDJ_SONNUM", getMboSet().count());
    return 1;
  }
  public int insertstep() throws RemoteException, MXException {
    validate();
    try
    {
      MboSetRemote localMboSetRemote = getMboSet();

      localMboSetRemote.setOrderBy("OPMA_BHTTJDZXGDJ_SONNUM");

      for (int i = 0; i < localMboSetRemote.count(); ++i)
      {
        MboRemote localMboRemote = localMboSetRemote.getMbo(i);

        if (localMboRemote.getInt("OPMA_BHTTJDZXGDJ_SONNUM") <= getCurrentRow())
          continue;
        localMboRemote.setValue("OPMA_BHTTJDZXGDJ_SONNUM", localMboRemote.getInt("OPMA_BHTTJDZXGDJ_SONNUM") + 1);
      }

      insert(getCurrentRow());

      localMboSetRemote.getMbo(this.currentRow).setValue("ordernum", this.currentRow + 1);

      setRemoveOnCancel(this.currentRow);
      setNewRowUnedited(true);
      this.tableStateFlags.setFlag(64L, true);

      refreshTable();
    } catch (Exception localException) {
      localException.printStackTrace();
    }
    return 1;
  }
}