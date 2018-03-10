package org.haywired;
import java.net.URL;
import java.sql.*;

public class ResetDB {
  private DBSettingsBean db;
  public ResetDB(DBSettingsBean a) {
    db = a;
    System.out.println(db.getdb() + db.getdburl());
  }
  public static void main(String[] args) {
    ResetDB r = new ResetDB(new DBSettingsBean());
    r.sessionsm();
  }
  public void destroy() {
    try {
      Class.forName(db.getdriver());
      Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
      String query;

      Statement stmt = con.createStatement();
      query = "DROP TABLE Sections";
      stmt.executeUpdate(query);
      System.out.println(query);
      System.out.println();
      stmt.close();

      Statement stmt1 = con.createStatement();
      query = "DROP TABLE Pages";
      stmt1.executeUpdate(query);
      System.out.println(query);
      System.out.println();
      stmt1.close();

      Statement stmt2 = con.createStatement();
      query = "DROP TABLE PageMakeup";
      stmt2.executeUpdate(query);
      System.out.println(query);
      System.out.println();
      stmt2.close();

      Statement stmt3 = con.createStatement();
      query = "DROP TABLE HTMLComponents";
      stmt3.executeUpdate(query);
      System.out.println(query);
      System.out.println();
      stmt3.close();

      Statement stmt4 = con.createStatement();
      query = "DROP TABLE Groups";
      stmt4.executeUpdate(query);
      System.out.println(query);
      System.out.println();
      stmt4.close();

      Statement stmt5 = con.createStatement();
      query = "DROP TABLE GroupMaps";
      stmt5.executeUpdate(query);
      System.out.println(query);
      System.out.println();
      stmt5.close();

      con.close();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void create() {
    try {
      Class.forName(db.getdriver());
      Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
      String query;

      Statement stmt = con.createStatement();
      query = "CREATE TABLE Sections (sid int not null, name text(32) not null, homepage int not null, colour char(8), parent int not null, readgid int, writegid int)";
      stmt.executeUpdate(query);
      System.out.println(query);
      System.out.println();
      stmt.close();

      Statement stmt1 = con.createStatement();
      query = "CREATE SEQUENCE ON Sections STEP 1 VALUE 0";
      stmt1.executeUpdate(query);
      System.out.println(query);
      System.out.println();
      stmt1.close();

      Statement stmt2 = con.createStatement();
      query = "CREATE TABLE Pages (page int not null, sid int, title text(128) not null, linktitle text(64), defstext text(128), parent int, headeronly int, readgid int, writegid int)";
      stmt2.executeUpdate(query);
      System.out.println(query);
      System.out.println();
      stmt2.close();

      Statement stmt3 = con.createStatement();
      query = "CREATE SEQUENCE ON Pages STEP 1 VALUE 0";
      stmt3.executeUpdate(query);
      System.out.println(query);
      System.out.println();
      stmt3.close();

      Statement stmt4 = con.createStatement();
      query = "CREATE TABLE PageMakeup (page int not null, position int not null, type int not null, cid int not null)";
      stmt4.executeUpdate(query);
      System.out.println(query);
      System.out.println();
      stmt4.close();

      Statement stmt5 = con.createStatement();
      query = "CREATE SEQUENCE ON PageMakeup STEP 1 VALUE 0";
      stmt5.executeUpdate(query);
      System.out.println(query);
      System.out.println();
      stmt5.close();

      Statement stmt6 = con.createStatement();
      query = "CREATE TABLE HTMLComponents (cid int not null, data text(4096) not null, wiki int)";
      stmt6.executeUpdate(query);
      System.out.println(query);
      System.out.println();
      stmt6.close();

      Statement stmt7 = con.createStatement();
      query = "CREATE TABLE Groups (gid int not null, name text(32), hidden int)";
      stmt7.executeUpdate(query);
      System.out.println(query);
      System.out.println();
      stmt7.close();

      Statement stmt10 = con.createStatement();
      query = "CREATE SEQUENCE ON Groups STEP 1 VALUE 0";
      stmt10.executeUpdate(query);
      System.out.println(query);
      System.out.println();
      stmt10.close();

      Statement stmt8 = con.createStatement();
      query = "CREATE TABLE GroupMaps (gid int not null, user text(32) not null)";
      stmt8.executeUpdate(query);
      System.out.println(query);
      System.out.println();
      stmt8.close();

      con.close();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void sessions() {
    try {
      DBSettingsBean dbb = new DBSettingsBean();
      dbb.setdb("sessions");
      Class.forName(dbb.getdriver());
      Connection con = DriverManager.getConnection(dbb.getdburl(), dbb.getuser(), dbb.getpass());
      String query;

      Statement stmt4 = con.createStatement();
      query = "DROP TABLE Sessions";
      stmt4.executeUpdate(query);
      System.out.println(query);
      System.out.println();
      stmt4.close();

      Statement stmt5 = con.createStatement();
      query = "DROP TABLE PolicyTime";
      stmt5.executeUpdate(query);
      System.out.println(query);
      System.out.println();
      stmt5.close();

      con.close();
    } catch(Exception e) {
      e.printStackTrace();
    }

    sessionsm();

  }

  public void sessionsm() {
    try {
      DBSettingsBean dbb = new DBSettingsBean();
      dbb.setdb("sessions");
      Class.forName(dbb.getdriver());
      Connection con = DriverManager.getConnection(dbb.getdburl(), dbb.getuser(), dbb.getpass());
      String query;

      Statement stmt7 = con.createStatement();
      query = "CREATE TABLE Sessions (user char(32) not null, keya int not null, keyb int not null)";
      stmt7.executeUpdate(query);
      System.out.println(query);
      System.out.println();
      stmt7.close();

      Statement stmt8 = con.createStatement();
      query = "CREATE TABLE PolicyTime (user char(32) not null, lastagreed char(50) not null, viewed char(50))";
      stmt8.executeUpdate(query);
      System.out.println(query);
      System.out.println();
      stmt8.close();

      con.close();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }


}