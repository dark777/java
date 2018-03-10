package org.haywired.firefly;

import org.haywired.*;
import java.net.URL;
import java.sql.*;

public class UpdateHTMLComponentsBean {

        private DBSettingsBean db;

        private int oldcid;
        private boolean oldcidSET;
        private String olddata;
        private boolean olddataSET;
        private int oldwiki;
        private boolean oldwikiSET;

        private int newcid;
        private boolean newcidSET;
        private String newdata;
        private boolean newdataSET;
      	private int newwiki;
        private boolean newwikiSET;

	public UpdateHTMLComponentsBean(DBSettingsBean a) {
		db = a;
	}

        public void setoldcid(int a) {
		oldcid = a;
                oldcidSET = true;
        }
        public void setolddata(String a) {
		olddata = a;
                olddataSET = true;
        }
        public void setoldwiki(boolean a) {
		if(a) oldwiki = 1;
                else oldwiki = 0;
		oldwikiSET = true;
	}

	public void setnewcid(int a) {
		newcid = a;
		newcidSET = true;
	}
	public void setnewdata(String a) {
		newdata = SearchandReplace.doSearchReplace(a, "'", "\\'");
		newdataSET = true;
	}
	public void setnewwiki(boolean a) {
		if(a) newwiki = 1;
		else newwiki = 0;
		newwikiSET = true;
	}

        public void go() {
                try {

                	Class.forName(db.getdriver());
                    Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
                    Statement stmt = con.createStatement();

			String query = "UPDATE HTMLComponents SET";
			boolean firstdone = false;

			if(newcidSET) {
				if(firstdone) query = query + ", ";
				else {
					query = query + " ";
					firstdone = true;
				}
				query = query + "cid = " + newcid;
			}

			if(newdataSET) {
				if(firstdone) query = query + ", ";
				else {
					query = query + " ";
					firstdone = true;
				}
				query = query + "data = '" + newdata + "'";
			}

			if(newwikiSET) {
				if(firstdone) query = query + ", ";
				else {
					query = query + " ";
					firstdone = true;
				}
				query = query + "wiki = " + newwiki;
			}

			boolean wheredone = false;
			
			if(oldcidSET) {
				if(!wheredone) {
					query = query + " WHERE ";
					wheredone = true;
				} else
					query = query + " AND ";
				query = query + "cid = " + oldcid;
			}

			if(olddataSET) {
				if(!wheredone) {
					query = query + " WHERE ";
					wheredone = true;
				} else
					query = query + " AND ";
				query = query + "data = '" + olddata + "'";
			}

			if(oldwikiSET) {
				if(!wheredone) {
					query = query + " WHERE ";
					wheredone = true;
				} else
					query = query + " AND ";
				query = query + "wiki = " + oldwiki;
			}

			System.out.println(query);
			stmt.executeUpdate(query);

			stmt.close();
			con.close();

                } catch(Exception e) {
                        e.printStackTrace();
                }
        }

}

