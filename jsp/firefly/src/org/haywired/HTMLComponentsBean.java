package org.haywired;
import java.net.URL;
import java.sql.*;
import java.util.*;

import org.haywired.firefly.wiki.*;

public class HTMLComponentsBean {
        /* @version 1.5.1 */
        private DBSettingsBean db;
        private Vector v;
        private int cid;
        private int cidCOUNT;
        private boolean cidSET;
        private String data;
        private int dataCOUNT;
        private boolean dataSET;
	private int wikiCOUNT;
        private boolean initdone;
        public HTMLComponentsBean(DBSettingsBean a) {
          db = a;
        }
        public int getsize() {
               if(!initdone) init();
                return v.size();
        }
        public int getcid() {
                if(!initdone) init();
                return ((HTMLComponentsDataBean)v.elementAt(cidCOUNT++)).getcid();
        }
        public void setcid(int a) {
                cid = a;
                cidSET = true;
        }
        public String getdata() {
                if(!initdone) init();
                HTMLComponentsDataBean temph = (HTMLComponentsDataBean)v.elementAt(dataCOUNT++);
                if(temph.getwiki()) {
                  Preview preview = new Preview();
                  return preview.translate(temph.getdata());
                }
                return temph.getdata();
        }
	public String getrawdata() {
		if(!initdone) init();
		return ((HTMLComponentsDataBean)v.elementAt(dataCOUNT++)).getdata();
	}
        public void setdata(String a) {
                data = a;
                dataSET = true;
        }
	public boolean getwiki() {
		if(!initdone) init();
		return ((HTMLComponentsDataBean)v.elementAt(wikiCOUNT++)).getwiki();
	}
        private void init() {
                v = new Vector();
                try {
                        Class.forName(db.getdriver());
                        Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
                        Statement stmt = con.createStatement();
                        String query = "SELECT cid, data, wiki FROM HTMLComponents";
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
                                query = query + "data = '" + data + "'";
                        }
                        ResultSet res = stmt.executeQuery(query);
                        while(res.next())
                                v.addElement(new HTMLComponentsDataBean(res.getInt(1), res.getString(2), res.getInt(3)));
                        stmt.close();
                        con.close();
                } catch(Exception e) {
                        e.printStackTrace();
                }
                cidCOUNT = 0;
                dataCOUNT = 0;
		wikiCOUNT = 0;
                initdone = true;
        }
        public static void main(String[] args) {
                HTMLComponentsBean a = new HTMLComponentsBean(new DBSettingsBean());
                for(int i = 0; i < a.getsize(); i++) {
                        System.out.println(a.getcid());
                        System.out.println(a.getdata());
                        System.out.println("***");
                }
        }
}
