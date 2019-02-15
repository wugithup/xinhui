package com.shuto.mam.app.yfx;
/**  
com.shuto.mam.app.Fldasset.FldParent 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月30日 下午8:37:27
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.io.PrintStream;
import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldParent extends MAXTableDomain
{
  public FldParent(MboValue mbovalue)
  {
    super(mbovalue);

    setRelationship("YBZ", "");
    String[] strFrom = { "YBZNUM" };
    String[] strTo = { "PARENT" };
    setLookupKeyMapInOrder(strTo, strFrom);
  }

  public MboSetRemote getList() throws MXException, RemoteException
  {
    setListCriteria("type = '标准'");
    System.out.println("+++++++++++++");
    return super.getList();
  }

  public void action() throws MXException, RemoteException {
    super.action();
    MboRemote Mbo = getMboValue().getMbo();

    System.out.println("+++++++++++++");
    MboSetRemote DetailSet = Mbo.getMboSet("YBZDETAIL");
    MboSetRemote XmSet = Mbo.getMboSet("YBZXM");
    MboRemote DetailMbo = null;
    MboRemote XmMbo = null;
    String MboNum = Mbo.getString("YBZNUM");
    String Mbobbmc = Mbo.getString("YBZ.BBMC");
    Mbo.setValue("BBMC", Mbobbmc);
    if (!DetailSet.isEmpty()) {
      DetailSet.deleteAll();
    }

    if (!XmSet.isEmpty()) {
      XmSet.deleteAll();
    }

    System.out.println("+++++++++++++");
    MboSetRemote OwnerSet = Mbo.getMboSet("YBZ");
    System.out.println("-----------");
    MboRemote Owner = null;
    if (!OwnerSet.isEmpty()) {
      MboRemote OwnerDetailMbo = null;
      MboRemote OwnerXmMbo = null;
      Owner = OwnerSet.getMbo(0);
      MboSetRemote OwnerDetailSet = Owner.getMboSet("YBZDETAIL");
      MboSetRemote OwnerXmSet = Owner.getMboSet("YBZXM");
      if (!OwnerDetailSet.isEmpty())
      {
        String xmbh = "";
        String bzbh = "";
        String xm = "";
        String bz = "";
        int xmxh = 0;
        int bzxh = 0;
        String value = "";
        for (int a = 0; a < OwnerDetailSet.count(); a++) {
          OwnerDetailMbo = OwnerDetailSet.getMbo(a);
          xmbh = OwnerDetailMbo.getString("xmbh");
          bzbh = OwnerDetailMbo.getString("bzbh");
          value = OwnerDetailMbo.getString("value");
          xm = OwnerDetailMbo.getString("YBZXM.NAME");
          bz = OwnerDetailMbo.getString("YBZBZ.NAME");
          xmxh = OwnerDetailMbo.getInt("YBZXM.SN");
          bzxh = OwnerDetailMbo.getInt("YBZBZ.SN");
          System.out.println("-------111111111111111----");
          DetailMbo = DetailSet.add();
          DetailMbo.setValue("XMBH", xmbh);
          DetailMbo.setValue("BZBH", bzbh);
          DetailMbo.setValue("VALUE", value);
          DetailMbo.setValue("YBZNUM", MboNum);
          DetailMbo.setValue("XM", xm);
          DetailMbo.setValue("BZ", bz);
          DetailMbo.setValue("XMXH", xmxh);
          DetailMbo.setValue("BZXH", bzxh);
        }
      }

      if (!OwnerXmSet.isEmpty())
      {
        String xm = "";
        String xmbh = "";
        int xmxh = 0;

        for (int b = 0; b < OwnerXmSet.count(); b++) {
          OwnerXmMbo = OwnerXmSet.getMbo(b);

          xm = OwnerXmMbo.getString("NAME");
          xmbh = OwnerXmMbo.getString("YBZXMNUM");
          xmxh = OwnerXmMbo.getInt("SN");

          System.out.println("-------111111111111111----");
          System.out.println("-------111111111111111----");
          System.out.println("-------111111111111111----");
          System.out.println("-------111111111111111----");
          System.out.println("-------111111111111111----");
          XmMbo = XmSet.add();

          XmMbo.setValue("YBZNUM", MboNum);
          XmMbo.setValue("NAME", xm);
          XmMbo.setValue("YBZXMNUM", xmbh);
          XmMbo.setValue("SN", xmxh);
        }
      }
    }
  }
}
