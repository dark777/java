package org.haywired.firefly;

import org.haywired.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;
import java.net.URL;
import java.sql.*;

import java.util.Date;

public class CheckPolicyTimeBean {
  DBSettingsBean db;
  public CheckPolicyTimeBean(DBSettingsBean a) {
    db = a;
    db.setdb("sessions");
  } 
  public boolean check(String user) {
    // first, figure out the current time as a huge great long
    long currenttime = new Date().getTime();
    long lastagreed = 0;
    // now, we must retrieve the last time from the database
    try {
      Class.forName(db.getdriver());
      Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
      Statement stmt = con.createStatement();
      String query = "SELECT lastagreed FROM PolicyTime WHERE user = '" + user + "'";
      System.out.println(query);
      ResultSet res = stmt.executeQuery(query);
      res.next();
      lastagreed = new Long(res.getString(1)).longValue();
      res.close();
      stmt.close();
      con.close();
    } catch(Exception e) {
      e.printStackTrace();
    }
    if((currenttime - lastagreed) > 31536000000) return false;
    return true;
    // there now!  done!
  }
  public long getlastagreed(String user) {
    long lastagreed = 0;
    // now, we must retrieve the last time from the database
    try {
      Class.forName(db.getdriver());
      Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
      Statement stmt = con.createStatement();
      String query = "SELECT lastagreed FROM PolicyTime WHERE user = '" + user + "'";
      System.out.println(query);
      ResultSet res = stmt.executeQuery(query);
      res.next();
      lastagreed = new Long(res.getString(1)).longValue();
      res.close();
      stmt.close();
      con.close();
    } catch(Exception e) {
      e.printStackTrace();
    }
    return lastagreed;
  }
  public long getlastviewed(String user) {
    long lastviewed = 0;
    // now, we must retrieve the last time from the database
    try {
      Class.forName(db.getdriver());
      Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
      Statement stmt = con.createStatement();
      String query = "SELECT viewed FROM PolicyTime WHERE user = '" + user + "'";
      System.out.println(query);
      ResultSet res = stmt.executeQuery(query);
      res.next();
      lastviewed = new Long(res.getString(1)).longValue();
      res.close();
      stmt.close();
      con.close();
    } catch(Exception e) {
      e.printStackTrace();
    }
    if(!(lastviewed > 0)) lastviewed = -1;
    return lastviewed;
  }
}
