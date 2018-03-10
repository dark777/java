package org.haywired;
import java.net.URL;
import java.sql.*;

public class SetSectionsBean {
        /* @version 1.5.1 */
        private DBSettingsBean db;
        private boolean doDelete;
        private int sid;
        private String name;
        private int homepage;
	private int parent;
        private String colour;
        private boolean sidSET;
        private boolean nameSET;
        private boolean homepageSET;
	private boolean parentSET;
        private boolean colourSET;
public SetSectionsBean(DBSettingsBean a) {
  db = a;
}
public void setdoDelete(boolean a) {
doDelete = a;
}
public void setsid(int a) {
sid = a;
sidSET = true;
}
public void setname(String a) {
name = a;
nameSET = true;
}
public void sethomepage(int a) {
homepage = a;
homepageSET = true;
}
public void setparent(int a) {
parent = a;
parentSET = true;
}
public void setcolour(String a) {
colour = a;
colourSET = true;
}
        public void go() {
                try {
                        Class.forName(db.getdriver());
                        Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
                        Statement stmt = con.createStatement();

                        if(doDelete) {
                          String query = "DELETE FROM Sections";
                          boolean wheredone = false;
                          if(sidSET) {
                            if(!wheredone) {
                              query = query + " WHERE ";
                              wheredone = true;
                            }
                            else
                              query = query + " AND ";
                            query = query + "sid = " + sid;
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
                          if(homepageSET) {
                            if(!wheredone) {
                              query = query + " WHERE ";
                              wheredone = true;
                            }
                            else
                              query = query + " AND ";
                            query = query + "homepage = " + homepage;
                          }
                          if(parentSET) {
                            if(!wheredone) {
                              query = query + " WHERE ";
                              wheredone = true;
                            }
                            else
                              query = query + " AND ";
                            query = query + "parent = " + parent;
                          }
                          if(colourSET) {
                            if(!wheredone) {
                              query = query + " WHERE ";
                              wheredone = true;
                            }
                            else
                              query = query + " AND ";
                            query = query + "colour = " + "'" + colour + "'";
                          }
                          System.out.println(query);
                          stmt.executeUpdate(query);
                        }
                        else {
                          String query = "INSERT INTO Sections (";
                          boolean firstdone = false;
                          if(!sidSET) {
                            sid = new SeqBean(db).getSeq("Sections");
                            sidSET = true;
                          }
                          if(!homepageSET) {
                            homepage = new SeqBean(db).getSeq("Pages");
                            homepageSET = true;
                          }
                          if(sidSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "sid";
                          }
                          if(nameSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "name";
                          }
                          if(homepageSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "homepage";
                          }
                          if(parentSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "parent";
                          }
                          if(colourSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "colour";
                          }
                          query = query + ") VALUES (";
                          firstdone = false;
                          if(sidSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + sid;
                          }
                          if(nameSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "'" + name + "'";
                          }
                          if(homepageSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + homepage;
                          }
                          if(parentSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + parent;
                          }
                          if(colourSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "'" + colour + "'";
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
                SetSectionsBean a = new SetSectionsBean(new DBSettingsBean());
//              a.setdoDelete(true);
                a.setname("Home");
                a.sethomepage(0);
                a.setcolour("000066");
                a.go();
        }
        public int getsid() {
          return sid;
        }
	public String getname() {
	  return name;
	}
        public int gethomepage() {
          return homepage;
        }
}

