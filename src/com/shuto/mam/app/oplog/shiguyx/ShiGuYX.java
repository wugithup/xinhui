package com.shuto.mam.app.oplog.shiguyx;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;
/**
 * 事故预想
 com.shuto.mam.app.oplog.shiguyx.ShiGuYX 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午4:30:14
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class ShiGuYX extends Mbo
  implements ShiGuYXRemote
{
  public ShiGuYX(MboSet ms)
    throws RemoteException
  {
    super(ms);
  }

  public void init() throws MXException {
    super.init();

    String Status = getMboValue("STATUS").getString();

    if (Status.equalsIgnoreCase("答题人接收"))
    {
      setFieldFlag("DESCRIPTION", 7L, true);
      setFieldFlag("CHULIGC", 7L, false);
      setFieldFlag("CHULIGC", 128L, true);
      setFieldFlag("DATIDATE", 128L, true);

      setFieldFlag("ZHIZHANGPJ", 7L, true);
      setFieldFlag("ZHANYEPJ", 7L, true);
      setFieldFlag("BUZHANGPJ", 7L, true);
    }

    if (Status.equalsIgnoreCase("值长评价"))
    {
      setFieldFlag("ZHIZHANGPJ", 7L, false);
      setFieldFlag("ZHIZHANGPJ", 128L, true);

      setFieldFlag("DESCRIPTION", 7L, true);
      setFieldFlag("CHULIGC", 7L, true);
      setFieldFlag("ZHANYEPJ", 7L, true);
      setFieldFlag("BUZHANGPJ", 7L, true);
      setFieldFlag("DATIDATE", 7L, true);
      setFieldFlag("RUNFS", 7L, true);
      setFieldFlag("SHIGUXX", 7L, true);
    }

    if (Status.equalsIgnoreCase("专业专工评价"))
    {
      setFieldFlag("ZHANYEPJ", 7L, false);
      setFieldFlag("ZHANYEPJ", 128L, true);

      setFieldFlag("DESCRIPTION", 7L, true);
      setFieldFlag("CHULIGC", 7L, true);
      setFieldFlag("ZHIZHANGPJ", 7L, true);
      setFieldFlag("BUZHANGPJ", 7L, true);
      setFieldFlag("DATIDATE", 7L, true);
      setFieldFlag("RUNFS", 7L, true);
      setFieldFlag("SHIGUXX", 7L, true);
    }

    if (Status.equalsIgnoreCase("部长评价"))
    {
      setFieldFlag("BUZHANGPJ", 7L, false);
      setFieldFlag("BUZHANGPJ", 128L, true);

      setFieldFlag("DESCRIPTION", 7L, true);
      setFieldFlag("CHULIGC", 7L, true);
      setFieldFlag("ZHIZHANGPJ", 7L, true);
      setFieldFlag("ZHANYEPJ", 7L, true);
      setFieldFlag("DATIDATE", 7L, true);
      setFieldFlag("RUNFS", 7L, true);
      setFieldFlag("SHIGUXX", 7L, true);
    }

    if ((!(Status.equalsIgnoreCase("已归档"))) && (!(Status.equalsIgnoreCase("已作废"))))
      return;
    setFlag(7L, true);
  }

  public void add()
    throws MXException, RemoteException
  {
    super.add();
    setFieldFlag("BUZHANGPJ", 7L, true);
    setFieldFlag("DESCRIPTION", 7L, false);
    setFieldFlag("CHULIGC", 7L, true);
    setFieldFlag("ZHIZHANGPJ", 7L, true);
    setFieldFlag("ZHANYEPJ", 7L, true);
    setFieldFlag("DATIDATE", 7L, true);
  }
}