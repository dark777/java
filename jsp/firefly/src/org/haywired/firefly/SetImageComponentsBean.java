package org.haywired.firefly;

import org.haywired.*;
import java.net.URL;
import java.sql.*;

public class SetImageComponentsBean {
        /* @version 1.5.1 */
        private DBSettingsBean db;
        private boolean doDelete;
        private int cid;
        private boolean cidSET;
        private String name;
        private boolean nameSET;
        private int sid;
        private boolean sidSET;
public SetImageComponentsBean(DBSettingsBean a) {
  db = a;
}
public void setdoDelete(boolean a) {
doDelete = a;
}
        public void setcid(int a) {
                cid = a;
                cidSET = true;
        }
        public void setname(String a) {
                name = a;
                nameSET = true;
        }
        public void setsid(int a) {
		sid = a;
		sidSET = true;
	}
        public void go() {
                try {
                        Class.forName(db.getdriver());
                        Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
                        Statement stmt = con.createStatement();

                        if(doDelete) {
                          String query = "DELETE FROM ImageComponents";
                          boolean wheredone = false;
                          if(cidSET) {
                            if(!wheredone) {
                              query = query + " WHERE ";
                              wheredone = true;
                            }
                            else
                              query = query + " AND ";
                            query = query + "cid = " + cid;
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
                          stmt.executeUpdate(query);
                        }

                        else {
                          String query = "INSERT INTO ImageComponents (";
                          boolean firstdone = false;
                          if(!cidSET) {
                            cid = new SeqBean(db).getSeq("PageMakeup");
                            cidSET = true;
                          }
                          if(cidSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "cid";
                          }
                          if(nameSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "name";
                          }
                          if(sidSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "sid";
                          }
                          query = query + ") VALUES (";
                          firstdone = false;
                          if(cidSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + cid;
                          }
                          if(nameSET) {
                            name = SearchandReplace.doSearchReplace(name, "'", "\\'");
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "'" + name + "'";
                          }
                          if(sidSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + sid;
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
}

