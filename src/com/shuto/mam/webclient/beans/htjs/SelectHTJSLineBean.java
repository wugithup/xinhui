package com.shuto.mam.webclient.beans.htjs;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
* @author       lzq liuzq@shuoto.cn
* @Description  华南合同
* @date         2017-6-7 上午11:12:31
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */

public class SelectHTJSLineBean extends DataBean
{
  private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

  public MboSetRemote getMboSetRemote() throws MXException, RemoteException {
    MboRemote mbo = this.app.getAppBean().getMbo();

    String where = "htjsnum is null";
    if (!mbo.isNull("htlineid"))
      where = where + " and htlineid=" + mbo.getInt("htlineid");
    if (!mbo.isNull("jsstartdate"))
      where = where + " and jsdate>=to_date('" + this.sdf.format(mbo.getDate("jsstartdate")) + "', 'yyyy-mm-dd')";
    if (!mbo.isNull("jsenddate")) {
      where = where + " and jsdate<=to_date('" + this.sdf.format(mbo.getDate("jsenddate")) + "', 'yyyy-mm-dd')";
    }
    MboSetRemote mbosetremote = mbo.getMboSet("$htjsline", "htjsline", where);
    mbosetremote.reset();
    return mbosetremote;
  }

  public int execute() throws MXException, RemoteException {
    MboSetRemote lines = getMboSet();
    if (lines.isEmpty())
      return 1;
    lines.resetWithSelection();

    MboRemote mbo = this.app.getAppBean().getMbo();
    for (int i = 0; i < lines.count(); ++i) {
      lines.getMbo(i).setValue("htjsnum", mbo.getString("htjsnum"));
    }
    lines.save();
    this.parent.save();
    this.app.getAppBean().save();
    return 1;
  }
}