package com.shuto.mam.webclient.beans.operation.oplog;

import com.shuto.mam.app.operation.oplog.OpLog;
import java.io.IOException;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.util.Date;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.UserInfo;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.AppInstance;
import psdi.webclient.system.controller.SessionContext;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.session.WebClientSession;

public class OplogHandDataBean extends DataBean
{
  MboRemote person1 = null;

  MboRemote person2 = null;

  public OplogHandDataBean() throws RemoteException, MXException {
  }

  public int execute() throws MXException, RemoteException {
    OpLog oplog = (OpLog)this.parent.getMbo();

    Date sysdate = new Date();

    String login1 = oplog.getOnDutyPerson().getMboSet("USER").getMbo(0).getString("loginid");

    String login2 = getMbo().getUserInfo().getLoginID();

    MboSetRemote login1s = getMbo().getMboSet("$login1", "maxuser", "loginid = '" + login1 + "'");
    this.person1 = login1s.getMbo(0).getMboSet("person").getMbo(0);

    String pwd1 = getMbo().getString("password1");
    String jibanperson = this.person1.getString("personid");

    if (pwd1.equals(""))
    {
      throw new MXApplicationException("oplog", "bothCH");
    }

    try
    {
      if (!pwd1.equals(""))
      {
        String ldappassword = login1s.getMbo(0).getString("ldappassword");

        String md5pw = MD5(pwd1);
        if (!md5pw.equalsIgnoreCase(ldappassword)) {
          oplog.setValue("STATUS", "HASSHIFT", 11L);
          oplog.setValue("JBDATE", MXServer.getMXServer().getDate());
          oplog.setValue("JBPERSON", oplog.getString("dbrperson"));
          Utility.showMessageBox(this.clientSession.getCurrentEvent(), "", "      交班完成!。", 0);
          this.sessionContext.queueRefreshEvent();
          this.app.getAppBean().save();
        }
        else {
          this.app.getAppBean().refreshTable();
          throw new MXApplicationException("oplog", "login1Password");
        }

      }

    }
    catch (IOException e)
    {
      e.printStackTrace();
      return 0;
    }
    return 1;
  }

  private String MD5(String inStr)
  {
    MessageDigest md5 = null;
    try {
      md5 = MessageDigest.getInstance("MD5");
    } catch (Exception e) {
      System.out.println(e.toString());
      e.printStackTrace();
      return "";
    }
    char[] charArray = inStr.toCharArray();
    byte[] byteArray = new byte[charArray.length];

    for (int i = 0; i < charArray.length; ++i) {
      byteArray[i] = (byte)charArray[i];
    }
    byte[] md5Bytes = md5.digest(byteArray);

    StringBuffer hexValue = new StringBuffer();

    for (int i = 0; i < md5Bytes.length; ++i) {
      int val = md5Bytes[i] & 0xFF;
      if (val < 16)
        hexValue.append("0");
      hexValue.append(Integer.toHexString(val));
    }
    return hexValue.toString();
  }
}