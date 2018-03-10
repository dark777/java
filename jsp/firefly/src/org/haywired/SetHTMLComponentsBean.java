package org.haywired;
import java.net.URL;
import java.sql.*;

public class SetHTMLComponentsBean {
        /* @version 1.5.1 */
        private DBSettingsBean db;
        private boolean doDelete;
        private int cid;
        private boolean cidSET;
        private String data;
        private boolean dataSET;
        private int wiki;
        private boolean wikiSET;
public SetHTMLComponentsBean(DBSettingsBean a) {
  db = a;
}
public void setdoDelete(boolean a) {
doDelete = a;
}
        public void setcid(int a) {
                cid = a;
                cidSET = true;
        }
        public void setdata(String a) {
                data = a;
                dataSET = true;
        }
        public void setwiki(boolean a) {
		if(a) wiki = 1;
                else wiki = 0;
		wikiSET = true;
	}
        public void go() {
                try {
                        Class.forName(db.getdriver());
                        Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
                        Statement stmt = con.createStatement();

                        if(doDelete) {
                          String query = "DELETE FROM HTMLComponents";
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
                          if(dataSET) {
                            if(!wheredone) {
                              query = query + " WHERE ";
                              wheredone = true;
                            }
                            else
                              query = query + " AND ";
                            query = query + "data = " + "'" + data + "'";
                          }
                          stmt.executeUpdate(query);
                        }

                        else {
                          String query = "INSERT INTO HTMLComponents (";
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
                          if(dataSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "data";
                          }
                          if(wikiSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "wiki";
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
                          if(dataSET) {
                            data = SearchandReplace.doSearchReplace(data, "'", "\\'");
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "'" + data + "'";
                          }
                          if(wikiSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + wiki;
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
                SetHTMLComponentsBean a = new SetHTMLComponentsBean(new DBSettingsBean());
                a.setcid(999);
                a.setdata("<h1><blink>test</blink></h1>");
                a.go();
        }
        public int getcid() {
                return cid;
        }
}

