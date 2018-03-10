package org.haywired.firefly;

import org.haywired.*;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class ImageComponentsBean {
        /* @version 1.5.1 */
        private DBSettingsBean db;
        private Vector v;
        private int cid;
        private int cidCOUNT;
        private boolean cidSET;
        private String name;
        private int nameCOUNT;
        private boolean nameSET;
	private int sidCOUNT;
        private boolean initdone;
        public ImageComponentsBean(DBSettingsBean a) {
          db = a;
        }
        public int getsize() {
               if(!initdone) init();
                return v.size();
        }
        public int getcid() {
                if(!initdone) init();
                return ((ImageComponentsDataBean)v.elementAt(cidCOUNT++)).getcid();
        }
        public void setcid(int a) {
                cid = a;
                cidSET = true;
        }
        public String getname() {
                if(!initdone) init();
                return ((ImageComponentsDataBean)v.elementAt(nameCOUNT++)).getname();
        }
        public void setname(String a) {
                name = a;
                nameSET = true;
        }
	public int getsid() {
		if(!initdone) init();
		return ((ImageComponentsDataBean)v.elementAt(sidCOUNT++)).getsid();
	}
        private void init() {
                v = new Vector();
                try {
                        Class.forName(db.getdriver());
                        Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
                        Statement stmt = con.createStatement();
                        String query = "SELECT cid, name, sid FROM ImageComponents";
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
                                query = query + "name = '" + name + "'";
                        }
                        ResultSet res = stmt.executeQuery(query);
                        while(res.next())
                                v.addElement(new ImageComponentsDataBean(res.getInt(1), res.getString(2), res.getInt(3)));
                        stmt.close();
                        con.close();
                } catch(Exception e) {
                        e.printStackTrace();
                }
                cidCOUNT = 0;
                nameCOUNT = 0;
		sidCOUNT = 0;
                initdone = true;
        }
}
