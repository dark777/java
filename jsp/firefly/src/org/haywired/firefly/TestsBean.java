package org.haywired.firefly;

import org.haywired.*;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class TestsBean {
/* @version 1.5.1 */
private DBSettingsBean db;
private Vector v;
private int tid;
private int tidCOUNT;
private boolean tidSET;
private int sid;
private int sidCOUNT;
private boolean sidSET;
private String title;
private int titleCOUNT;
private boolean titleSET;
private int ttime;
private boolean ttimeSET;
private int ttimeCOUNT;
private boolean initdone;
public TestsBean(DBSettingsBean a) {
  db = a;
}
public int getsize() {
if(!initdone) init();
return v.size();
}
public int gettid() {
if(!initdone) init();
return ((TestsDataBean)v.elementAt(tidCOUNT++)).gettid();
}
public void settid(int a) {
tid = a;
tidSET = true;
}
public void settid(String a) {
tid = Integer.parseInt(a);
tidSET = true;
}
public int getsid() {
if(!initdone) init();
if(v.size() != 0)
  return ((TestsDataBean)v.elementAt(sidCOUNT++)).getsid();
else return -888;
}
public void setsid(int a) {
sid = a;
sidSET = true;
}
public String gettitle() {
if(!initdone) init();
if(v.size() != 0)
  return ((TestsDataBean)v.elementAt(titleCOUNT++)).gettitle();
else return "Orphan!";
}
public void settitle(String a) {
title = a;
titleSET = true;
}
public int gettime() {
if(!initdone) init();
return ((TestsDataBean)v.elementAt(ttimeCOUNT++)).gettime();
}
public void settime(int a) {
ttime = a;
ttimeSET = true;
}
private void init() {
v = new Vector();
try {
Class.forName(db.getdriver());
Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
Statement stmt = con.createStatement();
String query = "SELECT tid, sid, title, ttime FROM Tests";
boolean wheredone = false;
if(tidSET) {
if(!wheredone) {
query = query + " WHERE ";
wheredone = true;
}
else
query = query + " AND ";
query = query + "tid = " + tid;
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
query = query + "title = " + title;
}
if(ttimeSET) {
if(!wheredone) {
query = query + " WHERE ";
wheredone = true;
}
else
query = query + " AND ";
query = query + "ttime = " + ttime;
}
query = query + " ORDER BY tid";
System.out.println(query);
ResultSet res = stmt.executeQuery(query);
while(res.next()) {
v.addElement(new TestsDataBean(res.getInt(1), res.getInt(2), res.getString(3), res.getInt(4)));
}
stmt.close();
con.close();
} catch(Exception e) {
e.printStackTrace();
}
tidCOUNT = 0;
sidCOUNT = 0;
titleCOUNT = 0;
ttimeCOUNT = 0;
initdone = true;
}
}
