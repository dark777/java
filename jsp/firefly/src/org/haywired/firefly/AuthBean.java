package org.haywired.firefly;

import org.haywired.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;
import java.net.URL;
import java.sql.*;

public class AuthBean {
  
  DBSettingsBean db;
  
  public AuthBean(DBSettingsBean a) {
    db = a;
    db.setdb("sessions");
  }

  public boolean checkGroup(int gid, String user) {
  	if(gid == -2) return true;
	DBSettingsBean dba = new DBSettingsBean();
	dba.setdb("SPS");
	GroupMapsBean gmb = new GroupMapsBean(dba);
	gmb.setgid(gid);
	gmb.setuser(user);
	System.out.println(gmb.getsize());
	if(gmb.getsize() > 0) if(gmb.getuser().equals(user)) return true;
	return false;
  }

  public String auth(HttpServletRequest req) {
    HttpSession se = req.getSession(false);
    String user = "Guest";
    try {
      Class.forName(db.getdriver());
      Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
      Statement stmt = con.createStatement();
      String query = "SELECT user FROM Sessions WHERE keya = " + se.getAttribute("keya") + " AND keyb = " + se.getAttribute("keyb");
      System.out.println(query);
      ResultSet res = stmt.executeQuery(query);
      res.next();
      user = res.getString(1);
      res.close();
      stmt.close();
      con.close();
    } catch(Exception e) {
      e.printStackTrace();
    }
    if(user == null) user = "Guest";
    return user;
  }

}
