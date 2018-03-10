package org.haywired.firefly;

import org.haywired.*;
import java.net.URL;
import java.sql.*;

public class SetGroupMapsBean {
        /* @version 1.5.1 */
        private DBSettingsBean db;
        private boolean doDelete;
        private int gid;
        private String user;
        private boolean gidSET;
        private boolean userSET;
public SetGroupMapsBean(DBSettingsBean a) {
  db = a;
}
public void setdoDelete(boolean a) {
doDelete = a;
}
public void setgid(int a) {
gid = a;
gidSET = true;
}
public void setuser(String a) {
user = a;
userSET = true;
}
        public void go() {
                try {
                        Class.forName(db.getdriver());
                        Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
                        Statement stmt = con.createStatement();

                        if(doDelete) {
                          String query = "DELETE FROM GroupMaps";
                          boolean wheredone = false;
                          if(gidSET) {
                            if(!wheredone) {
                              query = query + " WHERE ";
                              wheredone = true;
                            }
                            else
                              query = query + " AND ";
                            query = query + "gid = " + gid;
                          }
                          if(userSET) {
                            if(!wheredone) {
                              query = query + " WHERE ";
                              wheredone = true;
                            }
                            else
                              query = query + " AND ";
                            query = query + "user = " + "'" + user + "'";
                          }
                          System.out.println(query);
                          stmt.executeUpdate(query);
                        }
                        else {
                          String query = "INSERT INTO GroupMaps (";
                          boolean firstdone = false;
                          if(!gidSET) {
                            gid = new SeqBean(db).getSeq("GroupMaps");
                            gidSET = true;
                          }
                          if(gidSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "gid";
                          }
                          if(userSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "user";
                          }
                          query = query + ") VALUES (";
                          firstdone = false;
                          if(gidSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + gid;
                          }
                          if(userSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "'" + user + "'";
                          }
                          query = query + ")";
                          System.out.println(query);
                          stmt.executeUpdate(query);
                        }

                        stmt.close();
                        con.close();
                } catch(Exception e) {
                        e.printStackTrace();
                }
        }

	public int getgid() {
          return gid;
        }
	public String getuser() {
	  return user;
	}
}

