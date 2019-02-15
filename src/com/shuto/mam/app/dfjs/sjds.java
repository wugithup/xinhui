package com.shuto.mam.app.dfjs;

import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;
/**
 * 电费结算
 com.shuto.mam.app.dfjs.sjds 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午8:48:49
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class sjds extends Mbo
  implements sjdsRemote
{
  public sjds(MboSet mboset)
    throws RemoteException, MXException
  {
    super(mboset);
  }

  public void save() throws MXException, RemoteException {
    super.save();
  }

  public void init()
    throws MXException
  {
    super.init();
    String[] Str = { "YEAR", "MONTH", "BYZFDL", "BYZFDJ", "BYBTDDL", "BYBTDDJ", "BYKHDL", "BYKHDJ", "BYZBDL", "BYZBDJ", 
      "BYWSDL", "BYWSDJ", "BYSYDL", "BYSYDJ", "BYRLDFDL", "BYRLDFDJ", "BYCSCBDL", "BYCSCBDJ", "BYBWYXFYDL", "BYBWYXFYDJ", "BYBWYXFYDF", "NLJBWYXFYDL", 
      "NLJBWYXFYDJ", "NLJBWYXFYDF", "BYFZFWFYDL", "BYFZFWFYDJ", "BYFZFWFYDF", "NLJFZFWFYDL", "NLJFZFWFYDJ", "NLJFZFWFYDF", "BYLGXZXJDL", 
      "BYLGXZXJDJ", "BYLGXZXJDF", "NLJLGXZXJDL", "NLJLGXZXJDJ", "NLJLGXZXJDF", "BYTDDL", "BYTDDJ", "BYTDDF", "BYBCDF", "NLJTDDL", "NLJTDDJ", 
      "NLJTDDF", "NLJBCDF", "JZ" };
    try {
      if (!(isNew()))
        if (getString("status").equals("新建")) {
          setFlag(7L, false);
        }
        else
        {
          setFieldFlag(Str, 7L, true);
        }
    }
    catch (RemoteException e) {
      e.printStackTrace();
    }
  }
}