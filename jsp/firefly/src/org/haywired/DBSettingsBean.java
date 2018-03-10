package org.haywired;
public class DBSettingsBean {
  private String driver;
  private String dbstart;
  private String dburl;
  private String user;
  private String pass;
  private String db;
  public DBSettingsBean() {
    driver = "com.imaginary.sql.msql.MsqlDriver";
    dbstart = "jdbc:msql://localhost:1114/";
    dburl = "jdbc:msql://localhost:1114/SPSIntranet";
    user = "firefly";
    pass = "firefly";
  }
  public void setdriver(String a) {
    driver = a;
  }
  public void setdburl(String a) {
    dburl = a;
  }
  public void setuser(String a) {
    user = a;
  }
  public void setpass(String a) {
    pass = a;
  }
  public void setdb(String a) {
    db = a;
    dburl = dbstart + a;
  }
  public String getdb() {
    return db;
  }
  public String getdriver() {
    return driver;
  }
  public String getdburl() {
    return dburl;
  }
  public String getuser() {
    return user;
  }
  public String getpass() {
    return pass;
  }
}
