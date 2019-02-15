package com.shuto.mam.app.oplog.tsdlxdan;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;
/**
 * 停送电联系单
 com.shuto.mam.app.oplog.tsdlxdan.TsdlxDan 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午2:26:15
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class TsdlxDan extends Mbo
  implements TsdlxDanRemote
{
  public TsdlxDan(MboSet ms)
    throws RemoteException
  {
    super(ms);
  }

  public void init()
    throws MXException
  {
    super.init();
    String status = getMboValue("STATUS").getString();

    if (status.equals("新建"))
    {
      setFieldFlag("CZRPERSON", 7L, true);
      setFieldFlag("CZSTARTDATE", 7L, true);
      setFieldFlag("CZENDDATE", 7L, true);
    }
    if (status.equals("DDPZ"))
    {
      setFieldFlag("CZRPERSON", 128L, true);
      setFieldFlag("CZSTARTDATE", 7L, true);
      setFieldFlag("CZENDDATE", 7L, true);
    }
    if (status.equals("已批准"))
    {
      setFieldFlag("CZSTARTDATE", 7L, false);
      setFieldFlag("CZENDDATE", 7L, false);
      setFieldFlag("CZSTARTDATE", 128L, true);
      setFieldFlag("CZENDDATE", 128L, true);
    }
    if ((!(status.equals("已关闭"))) && (!(status.equals("已取消"))))
      return;
    setFlag(7L, true);
  }

  public void add()
    throws MXException, RemoteException
  {
    super.add();
    setFieldFlag("CZRPERSON", 7L, true);
    setFieldFlag("CZSTARTDATE", 7L, true);
    setFieldFlag("CZENDDATE", 7L, true);
  }
}