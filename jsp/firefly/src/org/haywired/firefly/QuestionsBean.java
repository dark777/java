package org.haywired.firefly;

import org.haywired.*;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class QuestionsBean {
/* @version 1.5.1 */
private DBSettingsBean db;
private Vector v;
private int qid;
private int qidCOUNT;
private boolean qidSET;
private int tid;
private int tidCOUNT;
private boolean tidSET;
private String ddesc;
private int ddescCOUNT;
private boolean ddescSET;
private int answer;
private boolean answerSET;
private int answerCOUNT;
private int explanation;
private boolean explanationSET;
private int explanationCOUNT;
private boolean initdone;
public QuestionsBean(DBSettingsBean a) {
  db = a;
}
public int getsize() {
if(!initdone) init();
return v.size();
}
public int getqid() {
if(!initdone) init();
return ((QuestionsDataBean)v.elementAt(qidCOUNT++)).getqid();
}
public void setqid(int a) {
qid = a;
qidSET = true;
}
public int gettid() {
if(!initdone) init();
if(v.size() != 0)
  return ((QuestionsDataBean)v.elementAt(tidCOUNT++)).gettid();
else return -888;
}
public void settid(int a) {
tid = a;
tidSET = true;
}
public String getddesc() {
if(!initdone) init();
if(v.size() != 0)
  return ((QuestionsDataBean)v.elementAt(ddescCOUNT++)).getddesc();
else return "Orphan!";
}
public void setddesc(String a) {
ddesc = a;
ddescSET = true;
}
public int getanswer() {
if(!initdone) init();
return ((QuestionsDataBean)v.elementAt(answerCOUNT++)).getanswer();
}
public void setanswer(int a) {
answer = a;
answerSET = true;
}
public int getexplanation() {
if(!initdone) init();
return ((QuestionsDataBean)v.elementAt(explanationCOUNT++)).getexplanation();
}
private void init() {
v = new Vector();
try {
Class.forName(db.getdriver());
Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
Statement stmt = con.createStatement();
String query = "SELECT qid, tid, ddesc, answer, explanation FROM Questions";
boolean wheredone = false;
if(qidSET) {
if(!wheredone) {
query = query + " WHERE ";
wheredone = true;
}
else
query = query + " AND ";
query = query + "qid = " + qid;
}
if(tidSET) {
if(!wheredone) {
query = query + " WHERE ";
wheredone = true;
}
else
query = query + " AND ";
query = query + "tid = " + tid;
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
if(answerSET) {
if(!wheredone) {
query = query + " WHERE ";
wheredone = true;
}
else
query = query + " AND ";
query = query + "answer = " + answer;
}
query = query + " ORDER BY qid";
ResultSet res = stmt.executeQuery(query);
System.out.println("AOK!");
while(res.next()) {
v.addElement(new QuestionsDataBean(res.getInt(1), res.getInt(2), res.getString(3), res.getInt(4), res.getInt(5)));
}
stmt.close();
con.close();
} catch(Exception e) {
e.printStackTrace();
}
qidCOUNT = 0;
tidCOUNT = 0;
ddescCOUNT = 0;
answerCOUNT = 0;
explanationCOUNT = 0;
initdone = true;
}
}
