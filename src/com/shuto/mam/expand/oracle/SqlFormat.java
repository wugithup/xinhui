package com.shuto.mam.expand.oracle;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract interface SqlFormat
{
  public abstract SqlFormat setTableRoot(String paramString);

  public abstract void setDoutouWhere(String paramString1, String paramString2);

  public abstract void addDoutouWhere(String paramString1, String paramString2);

  public abstract String getSql();

  public abstract void format();

  public abstract ResultSet executeQuery()
    throws SQLException;

  public abstract void setWhere(String paramString);
}