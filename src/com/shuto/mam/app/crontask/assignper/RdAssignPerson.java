package com.shuto.mam.app.crontask.assignper;
/**  
com.shuto.mam.app.crontask.assignper.RdAssignPerson 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年9月22日 上午11:01:32
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import psdi.server.MXServer;
import psdi.server.SimpleCronTask;

public class RdAssignPerson extends SimpleCronTask
{
  public void cronAction()
  {
    try
    {
      Connection connection = MXServer.getMXServer().getDBManager().getSequenceConnection();
      Statement statement = connection.createStatement();
      Statement statement1 = connection.createStatement();

//      Map map = new HashMap();
      String[] person=new String[5];
      String sql = "select banzhang, banchi, cy1, cy2, cy3, zy1, zy2  from assignpersoncfg  where banchi =     (select decode(banchi, '一班', '二班', '二班', '三班', '三班', '一班')        from assignperson         where createdate = (select max(createdate) from assignperson))";

      ResultSet rs = statement.executeQuery(sql);
      String bz = "";
      String bc = "";
      while (rs.next()) {
        bz = rs.getString("banzhang");
        bc = rs.getString("banchi");
//        map.put(Integer.valueOf(1), rs.getString("cy1"));
//        map.put(Integer.valueOf(2), rs.getString("cy2"));
//        map.put(Integer.valueOf(3), rs.getString("cy3"));
//        map.put(Integer.valueOf(4), rs.getString("zy1"));
//        map.put(Integer.valueOf(5), rs.getString("zy2"));
        person[0]=rs.getString("cy1");
        person[1]=rs.getString("cy2");
        person[2]=rs.getString("cy3");
        person[3]=rs.getString("zy1");
        person[4]=rs.getString("zy2");
      }
//      String[] assignper = getPerson(map);
      insertPerson(person, bz, bc, statement1);
      rs.close();
      statement1.close();
      statement.close();
      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void insertPerson(String[] p, String bz, String bc, Statement statement12) throws SQLException
  {
    String insertSql = "insert into assignperson(assignpersonid,hasld,createdate,description,cy1,cy2,cy3,zy1,zy2,banchi,isview,siteid,orgid)  values (assignpersonidseq.nextval,0,trunc(sysdate),'" + 
      bz + "'," + 
      " '" + p[0] + "','" + p[1] + "','" + p[2] + "','" + p[3] + "','" + p[4] + "','" + bc + "',1,'NDKJ000','CRPH500NM')";
    statement12.executeUpdate(insertSql);
  }

  /*private String[] getPerson(Map<Integer, String> map)
  {
    int[] a = randomCommon(1, 6, 5);
    String[] temp = new String[6];
    for (int i = 0; i < a.length; i++) {
      temp[i] = ((String)map.get(Integer.valueOf(a[i])));
    }
    return temp;
  }

  public static int[] randomCommon(int min, int max, int n) {
    if ((n > max - min + 1) || (max < min)) {
      return null;
    }
    int[] result = new int[n];
    int count = 0;
    while (count < n) {
      int num = (int)(Math.random() * (max - min)) + min;
      boolean flag = true;
      for (int j = 0; j < n; j++) {
        if (num == result[j]) {
          flag = false;
          break;
        }
      }
      if (flag) {
        result[count] = num;
        count++;
      }
    }
    return result;
  }*/
}