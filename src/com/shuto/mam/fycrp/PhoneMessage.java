package com.shuto.mam.fycrp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import oracle.jdbc.driver.OracleDriver;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:阜阳台账
 */
public class PhoneMessage
{
  Connection conn = null;

  public PhoneMessage() throws ClassNotFoundException, SQLException
  {
    Connection conn = null;
    String url = "jdbc:oracle:thin:@10.59.54.110:1521:orcl";
    String driver = "oracle.jdbc.driver.OracleDriver";
    String user = "foaapp";
    String password = "foaapp";

    println("正在试图加载驱动程序 " + driver);
    Class.forName(driver);
    println("驱动程序已加载");

    println("url=" + url);
    println("user=" + user);
    println("password=" + password);
    println("正在试图连接数据库--------");

    DriverManager.registerDriver(new OracleDriver());

    conn = DriverManager.getConnection(url, user, password);

    println("OK,成功连接到数据库");

    conn.setAutoCommit(false);
    this.conn = conn;
  }

  public void addMessage(String toPersonid, String toDepartment, String toDisplayname, String fromPersonid, String fromDepartment, String fromDisplayname, String phoneNum, String txt)
    throws SQLException
  {
    String sql = "insert into message(ID,RECEIVERMOBILE,RECEIVERID,RECEIVERDEPT,RECEIVERPOSITION,RECEIVERNAME,CONTEXT,SENDERID,SENDERDEPT,SENDERPOSITION,SENDERNAME,SENDERTIME,STA) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

    PreparedStatement stmt = this.conn.prepareStatement(sql);
    SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyyMMdd-HH:mm:ss:SSS");
    String id = "FYCRP-EAM-" + bartDateFormat.format(new Date());

    stmt.setString(1, id);
    stmt.setString(2, phoneNum);
    stmt.setString(3, toPersonid);
    stmt.setString(4, toDepartment);
    stmt.setString(5, "");
    stmt.setString(6, toDisplayname);
    stmt.setString(7, txt);
    stmt.setString(8, fromPersonid);
    stmt.setString(9, fromDepartment);
    stmt.setString(10, "");
    stmt.setString(11, fromDisplayname);
    stmt.setTimestamp(12, new Timestamp(new Date().getTime()));
    stmt.setString(13, "未发送");
    stmt.execute();
    stmt.close();
  }

  public void commit()
    throws SQLException
  {
    this.conn.commit();
    this.conn.close();
  }

  public void close() throws SQLException
  {
    this.conn.close();
  }

  public static void main(String[] args)
    throws SQLException, ClassNotFoundException
  {
    PhoneMessage pm = new PhoneMessage();
    pm.addMessage("jiangxy", "蒋鑫燚", "技术部", 
      "jiangxy", "蒋鑫燚", "技术部", "13106013865", "EAM测试手机发送信息");
    pm.commit();
  }

  public static void println(String s)
  {
  }
}