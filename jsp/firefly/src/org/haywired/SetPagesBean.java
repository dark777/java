package org.haywired;
import java.net.URL;
import java.sql.*;

public class SetPagesBean {
        /* @version 1.5.1 */
        private DBSettingsBean db;
        private boolean doDelete;
        private int page;
        private int sid;
        private String title;
        private String linktitle;
        private String defstext;
        private int parent;
        private int headeronly;
        private boolean pageSET;
        private boolean sidSET;
        private boolean titleSET;
        private boolean linktitleSET;
        private boolean defstextSET;
        private boolean parentSET;
        private boolean headeronlySET;
public SetPagesBean(DBSettingsBean a) {
  db = a;
}
public void setdoDelete(boolean a) {
doDelete = a;
}
public void setpage(int a) {
page = a;
pageSET = true;
}
public void setsid(int a) {
sid = a;
sidSET = true;
}
public void settitle(String a) {
title = a;
titleSET = true;
}
public void setlinktitle(String a) {
linktitle = a;
linktitleSET = true;
}
public void setdefstext(String a) {
defstext = a;
defstextSET = true;
}
public void setparent(int a) {
parent = a;
parentSET = true;
}
public void setheaderonly(boolean a) {
headeronly = 0;
if(a) headeronly = 1;
headeronlySET = true;
}
        public void go() {

		if(headeronly < 0) headeronly = 0;	

                try {
                        Class.forName(db.getdriver());
                        Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
                        Statement stmt = con.createStatement();

                        if(doDelete) {
                          String query = "DELETE FROM Pages";
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
                          if(sidSET) {
                            if(!wheredone) {
                              query = query + " WHERE ";
                              wheredone = true;
                            }
                            else
                              query = query + " AND ";
                            query = query + "sid = " + sid;
                          }
                          if(titleSET) {
                            if(!wheredone) {
                              query = query + " WHERE ";
                              wheredone = true;
                            }
                            else
                              query = query + " AND ";
                            query = query + "title = " + "'" + title + "'";
                          }
                          if(linktitleSET) {
                            if(!wheredone) {
                              query = query + " WHERE ";
                              wheredone = true;
                            }
                            else
                              query = query + " AND ";
                            query = query + "linktitle = " + "'" + linktitle + "'";
                          }
                          if(defstextSET) {
                            if(!wheredone) {
                              query = query + " WHERE ";
                              wheredone = true;
                            }
                            else
                              query = query + " AND ";
                            query = query + "defstext = " + "'" + defstext + "'";
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
                          if(headeronlySET) {
                            if(!wheredone) {
                              query = query + " WHERE ";
                              wheredone = true;
                            }
                            else
                              query = query + " AND ";
                            query = query + "headeronly = " + headeronly;
                          }
                          System.out.println(query);
                          stmt.executeUpdate(query);
                        }
                        else {
                          String query = "INSERT INTO Pages (";
                          boolean firstdone = false;
                          if(!pageSET) {
                            page = new SeqBean(db).getSeq("Pages");
                            pageSET = true;
                          }
                          if(pageSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "page";
                          }
                          if(sidSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "sid";
                          }
                          if(titleSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "title";
                          }
                          if(linktitleSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "linktitle";
                          }
                          if(defstextSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "defstext";
                          }
                          if(parentSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "parent";
                          }
                          if(headeronlySET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "headeronly";
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
                          if(sidSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + sid;
                          }
                          if(titleSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "'" + title + "'";
                          }
                          if(linktitleSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "'" + linktitle + "'";
                          }
                          if(defstextSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + "'" + defstext + "'";
                          }
                          if(parentSET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + parent;
                          }
                          if(headeronlySET) {
                            if(firstdone) {
                              query = query + ", ";
                            } else
                              firstdone = true;
                            query = query + headeronly;
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

	public int getpage() {
		return page;
	}

        public static void main(String[] args) {
                SetPagesBean a = new SetPagesBean(new DBSettingsBean());
//              a.setdoDelete(true);
                a.settitle("SPS Intranet Home Page");
                a.go();
        }
}

