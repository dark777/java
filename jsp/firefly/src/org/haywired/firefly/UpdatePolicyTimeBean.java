package org.haywired.firefly;

import org.haywired.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;
import java.net.URL;
import java.sql.*;

import java.util.Date;

public class UpdatePolicyTimeBean {
  DBSettingsBean db;
  public UpdatePolicyTimeBean(DBSettingsBean a) {
    db = a;
    db.setdb("sessions");
  } 
  public void update(String user) {
    // first, figure out the current time as a huge great string
    String currenttime = new Long(new Date().getTime()).toString();
    // now, we must remove anything already in the database for this user...
    try {
      Class.forName(db.getdriver());
      Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
      Statement stmt = con.createStatement();
      String query = "DELETE FROM PolicyTime WHERE user = '" + user + "'";
      System.out.println(query);
      stmt.executeUpdate(query);
      stmt.close();
      con.close();
    } catch(Exception e) {
      e.printStackTrace();
    }
    // then, we must add a new record...
    try {
      Class.forName(db.getdriver());
      Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
      Statement stmt = con.createStatement();
      String query = "INSERT INTO PolicyTime VALUES ('" + user + "', '" + currenttime + "', '-1')";
      System.out.println(query);
      stmt.executeUpdate(query);
      stmt.close();
      con.close();
    } catch(Exception e) {
      e.printStackTrace();
    }
    // there now!  done!
  }

  public void updatelastviewed(String user) {
    // first, figure out the current time as a huge great string
    String currenttime = new Long(new Date().getTime()).toString();
    // second, figure out the time last agreed as a huge great string
    CheckPolicyTimeBean c = new CheckPolicyTimeBean(db);
    String lasttime = new Long(c.getlastagreed(user)).toString();
    // now, we must remove anything already in the database for this user...
    try {
      Class.forName(db.getdriver());
      Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
      Statement stmt = con.createStatement();
      String query = "DELETE FROM PolicyTime WHERE user = '" + user + "'";
      System.out.println(query);
      stmt.executeUpdate(query);
      stmt.close();
      con.close();
    } catch(Exception e) {
      e.printStackTrace();
    }
    // then, we must add a new record...
    try {
      Class.forName(db.getdriver());
      Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
      Statement stmt = con.createStatement();
      String query = "INSERT INTO PolicyTime VALUES ('" + user + "', '" + lasttime + "', '" + currenttime + "')";
      System.out.println(query);
      stmt.executeUpdate(query);
      stmt.close();
      con.close();
    } catch(Exception e) {
      e.printStackTrace();
    }
    // there now!  done!
  }

}
