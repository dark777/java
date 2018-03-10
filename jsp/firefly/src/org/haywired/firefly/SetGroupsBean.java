package org.haywired.firefly;

import org.haywired.*;
import java.net.URL;
import java.sql.*;

public class SetGroupsBean {
        /* @version 1.5.1 */
        private DBSettingsBean db;
        private boolean doDelete;
        private int gid;
        private String name;
        private boolean gidSET;
        private boolean nameSET;
public SetGroupsBean(DBSettingsBean a) {
  db = a;
}
public void setdoDelete(boolean a) {
doDelete = a;
}
public void setgid(int a) {
gid = a;
gidSET = true;
}
public void setname(String a) {
name = a;
nameSET = true;
}
        public void go() {
                try {
                        Class.forName(db.getdriver());
                        Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
                        Statement stmt = con.createStatement();

                        if(doDelete) {
                          String query = "DELETE FROM Groups";
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
                          if(nameSET) {
                            if(!wheredone) {
                              query = query + " WHERE ";
                              wheredone = true;
                            }
                            else
                              query = query + " AND ";
                            query = query + "name = " + "'" + name + "'";
                          }
                          System.out.println(query);
                          stmt.executeUpdate(query);
                        }
                        else {
                          String query = "INSERT INTO Groups (";
                          boolean firstdone = false;
                          if(!gidSET) {
                            gid = new SeqBean(db).getSeq("Groups");
                            gidSET = true;
                          }
                          if(gidSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "gid";
                          }
                          if(nameSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "name";
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
                          if(nameSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "'" + name + "'";
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
	public String getname() {
	  return name;
	}
}

