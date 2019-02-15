package com.shuto.mam.app.eletel;
/**  
com.shuto.mam.app.eletel.Eletel 江苏
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年5月24日 下午5:34:47
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class Eletel extends Mbo
  implements EletelRemote
{
  private static final long serialVersionUID = 1L;

  public Eletel(MboSet paramMboSet)
    throws RemoteException
  {
    super(paramMboSet);
  }

  public void init() throws MXException
  {
    super.init();
    try {
      String str1 = getString("STATUS");
      String str2 = getString("ORGID");
      if ("CRPXZ".equals(str2))
      {
        String[] arrayOfString;
        if (("新建".equals(str1)) || ("待联系人修改".equals(str1)) || (isNew()))
        {
          arrayOfString = new String[] { "CZPERSON", "STOPTIME", "JHPERSON", "REMARKS" };

          setFieldFlag(arrayOfString, 128L, false);
          setFieldFlag(arrayOfString, 7L, true);
        }

        if ("待接收人审核".equals(str1)) {
          setFlag(7L, false);
          arrayOfString = new String[] { "CZPERSON", "STOPTIME", "JHPERSON" };

          setFieldFlag(arrayOfString, 7L, false);
          setFieldFlag("REMARKS", 7L, false);

          setFieldFlag(arrayOfString, 128L, true);
        }
      }
      if (("已关闭".equals(str1)) || ("待通知联系人".equals(str1)) || ("已作废".equals(str1)))
        setFlag(7L, true);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}
