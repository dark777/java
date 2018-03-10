package org.haywired;
import java.net.URL;
import java.sql.*;

public class SetPageMakeupBean {
        /* @version 1.5.1 */
        private DBSettingsBean db;
        private boolean doDelete;
        private int page;
        private int position;
        private int type;
        private int cid;
        private boolean pageSET;
        private boolean positionSET;
        private boolean typeSET;
        private boolean cidSET;
public SetPageMakeupBean(DBSettingsBean a) {
  db = a;
}
public void setdoDelete(boolean a) {
doDelete = a;
}
public void setpage(int a) {
page = a;
pageSET = true;
}
public void setpage(String a) {
page = Integer.parseInt(a);
pageSET = true;
}
public void setposition(int a) {
position = a;
positionSET = true;
}
public void setposition(String a) {
position = Integer.parseInt(a);
positionSET = true;
}
public void settype(int a) {
type = a;
typeSET = true;
}
public void settype(String a) {
type = Integer.parseInt(a);
typeSET = true;
}
public void setcid(int a) {
cid = a;
cidSET = true;
}
public void setcid(String a) {
cid = Integer.parseInt(a);
cidSET = true;
}
        public void go() {
                try {
                        Class.forName(db.getdriver());
                        Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
                        Statement stmt = con.createStatement();

                        if(doDelete) {
                          String query = "DELETE FROM PageMakeup";
                          boolean wheredone = false;
                          if(pageSET) {
                            if(!wheredone) {
                              query = query + " WHERE ";
                              wheredone = true;
                            }
                            else
                              query = query + " AND ";
                            query = query + "page = " + page;
                          }
                          if(positionSET) {
                            if(!wheredone) {
                              query = query + " WHERE ";
                              wheredone = true;
                            }
                            else
                              query = query + " AND ";
                            query = query + "position = " + position;
                          }
                          if(typeSET) {
                            if(!wheredone) {
                              query = query + " WHERE ";
                              wheredone = true;
                            }
                            else
                              query = query + " AND ";
                            query = query + "type = " + type;
                          }
                          if(cidSET) {
                            if(!wheredone) {
                              query = query + " WHERE ";
                              wheredone = true;
                            }
                            else
                              query = query + " AND ";
                            query = query + "cid = " + cid;
                          }
                          System.out.println(query);
                          stmt.executeUpdate(query);
                        }

                        else {
                          String query = "INSERT INTO PageMakeup (";
                          boolean firstdone = false;
                          if(pageSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "page";
                          }
                          if(positionSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "position";
                          }
                          if(typeSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "type";
                          }
                          if(cidSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "cid";
                          }
                          query = query + ") VALUES (";
                          firstdone = false;
                          if(pageSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + page;
                          }
                          if(positionSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + position;
                          }
                          if(typeSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + type;
                          }
                          if(cidSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + cid;
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

        public static void main(String[] args) {
                SetPageMakeupBean a = new SetPageMakeupBean(new DBSettingsBean());
//              a.setdoDelete(true);
                a.setpage(0);
                a.setposition(99);
                a.settype(0);
                a.setcid(99);
                a.go();
        }
}

