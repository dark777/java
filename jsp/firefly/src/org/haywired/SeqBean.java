package org.haywired;
import java.net.URL;
import java.sql.*;

public class SeqBean {
  private DBSettingsBean db;
  public SeqBean(DBSettingsBean a) {
    db = a;
  }
  public int getSeq(String t) {
  int i = 0;
    try {
      Class.forName(db.getdriver());
      Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
      Statement stmt = con.createStatement();
      ResultSet res = stmt.executeQuery("SELECT _seq FROM " + t);
      res.next();
      i = res.getInt(1);
      stmt.close();
      con.close();
    } catch(Exception e) {
      e.printStackTrace();
    }
    return i;
  }
}
