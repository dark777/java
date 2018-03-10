package org.haywired.firefly;

import org.haywired.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;
import java.net.URL;
import java.sql.*;

public class LogonBean {
  DBSettingsBean db;
  String user;
  boolean authok;
  boolean logoff;
  String status;
  int keya;
  int keyb;

public LogonBean(DBSettingsBean a) {
  db = a;
  authok = false;
}

public void setuser(String a) {
  user = a;
}

public void setlogoff(boolean a) {
  logoff = a;
}

public String getstatus() {
  return status;
}

public int getkeya() {
  return keya;
}

public int getkeyb() {
  return keyb;
}

public void go(HttpServletRequest req) {

  HttpSession se = req.getSession();

  if(logoff) {

    try {
      Class.forName(db.getdriver());
      Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
      Statement stmt = con.createStatement();
      String query = "DELETE FROM Sessions WHERE user = '" + user + "' AND keya = " + se.getValue("keya") + " AND keyb = " + se.getValue("keyb");
      System.out.println(query);
      stmt.executeUpdate(query);
      stmt.close();
      con.close();
    
      se.removeValue("keya");
      se.removeValue("keyb");

      status = "User " + user + " logged off successfully!"; 

    } catch(Exception e) { e.printStackTrace(); }  

  } else {

    Random r = new Random();
    keya = r.nextInt();
    keyb = r.nextInt();

    try {
      Class.forName(db.getdriver());
      Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
      Statement stmt = con.createStatement();
      String query = "INSERT INTO Sessions VALUES ( '" + user + "', " + keya + ", " + keyb + ")";
      System.out.println(query);
      stmt.executeUpdate(query);
      stmt.close();
      con.close();

      se.setAttribute("keya", Integer.toString(keya));
      se.setAttribute("keyb", Integer.toString(keyb));
    
      status = "User " + user + " logged on successfully!"; 

    } catch(Exception e) { e.printStackTrace(); }  

  }

  System.out.println(status);
  
}

}