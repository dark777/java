package org.haywired.firefly;

import org.haywired.*;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class AnswersBean {
/* @version 1.5.1 */
private DBSettingsBean db;
private Vector v;
private int aid;
private int aidCOUNT;
private boolean aidSET;
private int qid;
private int qidCOUNT;
private boolean qidSET;
private String ddesc;
private int ddescCOUNT;
private boolean ddescSET;
private boolean initdone;
public AnswersBean(DBSettingsBean a) {
  db = a;
}
public int getsize() {
if(!initdone) init();
return v.size();
}
public int getaid() {
if(!initdone) init();
return ((AnswersDataBean)v.elementAt(aidCOUNT++)).getaid();
}
public void setaid(int a) {
aid = a;
aidSET = true;
}
public int getqid() {
if(!initdone) init();
if(v.size() != 0)
  return ((AnswersDataBean)v.elementAt(qidCOUNT++)).getqid();
else return -888;
}
public void setqid(int a) {
qid = a;
qidSET = true;
}
public String getddesc() {
if(!initdone) init();
if(v.size() != 0)
  return ((AnswersDataBean)v.elementAt(ddescCOUNT++)).getddesc();
else return "Orphan!";
}
public void setddesc(String a) {
ddesc = a;
ddescSET = true;
}
private void init() {
v = new Vector();
try {
Class.forName(db.getdriver());
Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
Statement stmt = con.createStatement();
String query = "SELECT aid, qid, ddesc FROM Answers";
boolean wheredone = false;
if(aidSET) {
if(!wheredone) {
query = query + " WHERE ";
wheredone = true;
}
else
query = query + " AND ";
query = query + "aid = " + aid;
}
if(qidSET) {
if(!wheredone) {
query = query + " WHERE ";
wheredone = true;
}
else
query = query + " AND ";
query = query + "qid = " + qid;
}
if(ddescSET) {
if(!wheredone) {
query = query + " WHERE ";
wheredone = true;
}
else
query = query + " AND ";
query = query + "ddesc = " + ddesc;
}
query = query + " ORDER BY aid";
ResultSet res = stmt.executeQuery(query);
while(res.next()) {
v.addElement(new AnswersDataBean(res.getInt(1), res.getInt(2), res.getString(3)));
}
stmt.close();
con.close();
} catch(Exception e) {
e.printStackTrace();
}
aidCOUNT = 0;
qidCOUNT = 0;
ddescCOUNT = 0;
initdone = true;
}
}
