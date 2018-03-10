package org.haywired;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class PagesBean {
/* @version 1.5.1 */
private DBSettingsBean db;
private Vector v;
private int page;
private int pageCOUNT;
private boolean pageSET;
private int sid;
private int sidCOUNT;
private boolean sidSET;
private String title;
private int titleCOUNT;
private boolean titleSET;
private String linktitle;
private int linktitleCOUNT;
private boolean linktitleSET;
private String defstext;
private int defstextCOUNT;
private boolean defstextSET;
private int parent;
private boolean parentSET;
private int parentCOUNT;
private boolean headeronly;
private boolean headeronlySET;
private int headeronlyCOUNT;
private int readgid;
private boolean readgidSET;
private int readgidCOUNT;
private int writegid;
private boolean writegidSET;
private int writegidCOUNT;
private boolean initdone;
public PagesBean(DBSettingsBean a) {
  db = a;
}
public int getsize() {
if(!initdone) init();
return v.size();
}
public int getpage() {
if(!initdone) init();
return ((PagesDataBean)v.elementAt(pageCOUNT++)).getpage();
}
public void setpage(int a) {
page = a;
pageSET = true;
}
public void setpage(String a) {
page = Integer.parseInt(a);
pageSET = true;
}
public int getsid() {
if(!initdone) init();
if(v.size() != 0)
  return ((PagesDataBean)v.elementAt(sidCOUNT++)).getsid();
else return -888;
}
public void setsid(int a) {
sid = a;
sidSET = true;
}
public String gettitle() {
if(!initdone) init();
if(v.size() != 0)
  return ((PagesDataBean)v.elementAt(titleCOUNT++)).gettitle();
else return "Orphan!";
}
public void settitle(String a) {
title = a;
titleSET = true;
}
public String getlinktitle() {
if(!initdone) init();
return ((PagesDataBean)v.elementAt(linktitleCOUNT++)).getlinktitle();
}
public void setlinktitle(String a) {
linktitle = a;
linktitleSET = true;
}
public String getdefstext() {
if(!initdone) init();
return ((PagesDataBean)v.elementAt(defstextCOUNT++)).getdefstext();
}
public void setdefstext(String a) {
defstext = a;
defstextSET = true;
}
public int getparent() {
if(!initdone) init();
return ((PagesDataBean)v.elementAt(parentCOUNT++)).getparent();
}
public void setparent(int a) {
parent = a;
parentSET = true;
}
public boolean getheaderonly() {
if(!initdone) init();
return ((PagesDataBean)v.elementAt(headeronlyCOUNT++)).getheaderonly();
}
public void setheaderonly(boolean a) {
headeronly = a;
headeronlySET = true;
}
public int getreadgid() {
if(!initdone) init();
return ((PagesDataBean)v.elementAt(readgidCOUNT++)).getreadgid();
}
public void setreadgid(int a) {
readgid = a;
readgidSET = true;
}
public int getwritegid() {
if(!initdone) init();
return ((PagesDataBean)v.elementAt(writegidCOUNT++)).getwritegid();
}
public void setwritegid(int a) {
writegid = a;
writegidSET = true;
}

private void init() {
v = new Vector();
try {
Class.forName(db.getdriver());
Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
Statement stmt = con.createStatement();
String query = "SELECT page, sid, title, linktitle, defstext, parent, headeronly, readgid, writegid FROM Pages";
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
query = query + "title = " + title;
}
if(linktitleSET) {
if(!wheredone) {
query = query + " WHERE ";
wheredone = true;
}
else
query = query + " AND ";
query = query + "linktitle = " + linktitle;
}
if(defstextSET) {
if(!wheredone) {
query = query + " WHERE ";
wheredone = true;
}
else
query = query + " AND ";
query = query + "defstext = " + defstext;
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
query = query + "headeronly = ";
if(headeronly) query = query + "1";
else query = query + "0";
}
if(readgidSET) {
if(!wheredone) {
query = query + " WHERE ";
wheredone = true;
}
else
query = query + " AND ";
query = query + "readgid = " + readgid;
}
if(writegidSET) {
if(!wheredone) {
query = query + " WHERE ";
wheredone = true;
}
else
query = query + " AND ";
query = query + "writegid = " + writegid;
}
query = query + " ORDER BY page";
ResultSet res = stmt.executeQuery(query);
while(res.next()) {
boolean b = false;
if(res.getInt(7) > 0) b = true;
v.addElement(new PagesDataBean(res.getInt(1), res.getInt(2), res.getString(3), res.getString(4), res.getString(5), res.getInt(6), b, res.getInt(8), res.getInt(9)));
}
stmt.close();
con.close();
} catch(Exception e) {
e.printStackTrace();
}
pageCOUNT = 0;
sidCOUNT = 0;
titleCOUNT = 0;
linktitleCOUNT = 0;
defstextCOUNT = 0;
parentCOUNT = 0;
headeronlyCOUNT = 0;
readgidCOUNT = 0;
writegidCOUNT = 0;
initdone = true;
}
public static void main(String[] args) {
PagesBean a = new PagesBean(new DBSettingsBean());
for(int i = 0; i < a.getsize(); i++) {
System.out.println(a.getpage());
System.out.println(a.getsid());
System.out.println(a.gettitle());
System.out.println(a.getlinktitle());
System.out.println(a.getdefstext());
System.out.println(a.getparent());
System.out.println(a.getheaderonly());
System.out.println("***");
}
}
}
