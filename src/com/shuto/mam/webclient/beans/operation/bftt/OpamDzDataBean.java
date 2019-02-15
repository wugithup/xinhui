package com.shuto.mam.webclient.beans.operation.bftt;


import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年4月11日
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
      MboSetRemote steps = getMboSet();

      steps.setOrderBy("OPMA_BHTTJDZXGDJ_SONNUM");

      for (int i = 0; i < steps.count(); ++i)
      {
        MboRemote mbo = steps.getMbo(i);

        if (mbo.getInt("OPMA_BHTTJDZXGDJ_SONNUM") <= getCurrentRow())
          continue;
        mbo.setValue("OPMA_BHTTJDZXGDJ_SONNUM", mbo.getInt("OPMA_BHTTJDZXGDJ_SONNUM") + 1);
      }

      insert(getCurrentRow());

      steps.getMbo(this.currentRow).setValue("ordernum", 
        this.currentRow + 1);
      setRemoveOnCancel(this.currentRow);
      setNewRowUnedited(true);
      this.tableStateFlags.setFlag(64L, true);

      refreshTable();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 1;
  }
}