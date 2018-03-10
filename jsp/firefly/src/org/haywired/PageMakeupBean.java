package org.haywired;
import java.net.URL;
import java.sql.*;
import java.util.*;
class PageMakeupDataBean {
/* @version 1.5.1 */
private int page;
private int position;
private int type;
private int cid;
PageMakeupDataBean(int pagea, int positiona, int typea, int cida) {
page = pagea;
position = positiona;
type = typea;
cid = cida;
}
public int getpage() {
return page;
}
public int getposition() {
return position;
}
public int gettype() {
return type;
}
public int getcid() {
return cid;
}
}
public class PageMakeupBean {
/* @version 1.5.1 */
private DBSettingsBean db;
private Vector v;
private int page;
private int pageCOUNT;
private boolean pageSET;
private int position;
private int positionCOUNT;
private boolean positionSET;
private int type;
private int typeCOUNT;
private boolean typeSET;
private int cid;
private int cidCOUNT;
private boolean cidSET;
private boolean initdone;
public PageMakeupBean(DBSettingsBean a) {
  db = a;
}
public int getsize() {
if(!initdone) init();
return v.size();
}
public int getpage() {
if(!initdone) init();
if(v.size() > 0)
  return ((PageMakeupDataBean)v.elementAt(pageCOUNT++)).getpage();
else
  return -888;
}
public void setpage(int a) {
page = a;
pageSET = true;
}
public int getposition() {
if(!initdone) init();
return ((PageMakeupDataBean)v.elementAt(positionCOUNT++)).getposition();
}
public void setposition(int a) {
position = a;
positionSET = true;
}
public int gettype() {
if(!initdone) init();
return ((PageMakeupDataBean)v.elementAt(typeCOUNT++)).gettype();
}
public void settype(int a) {
type = a;
typeSET = true;
}
public int getcid() {
if(!initdone) init();
return ((PageMakeupDataBean)v.elementAt(cidCOUNT++)).getcid();
}
public void setcid(int a) {
cid = a;
cidSET = true;
}
private void init() {
v = new Vector();
try {
Class.forName(db.getdriver());
Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
Statement stmt = con.createStatement();
String query = "SELECT page, position, type, cid FROM PageMakeup";
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
if(positionSET) {
if(!wheredone) {
query = query + " WHERE ";
wheredone = true;
}
else
query = query + " AND ";
query = query + "position = " + position;
}
if(typeSET) {
if(!wheredone) {
query = query + " WHERE ";
wheredone = true;
}
else
query = query + " AND ";
query = query + "type = " + type;
}
if(cidSET) {
if(!wheredone) {
query = query + " WHERE ";
wheredone = true;
}
else
query = query + " AND ";
query = query + "cid = " + cid;
}
query = query + " ORDER BY position";
ResultSet res = stmt.executeQuery(query);
while(res.next())
v.addElement(new PageMakeupDataBean(res.getInt(1), res.getInt(2), res.getInt(3), res.getInt(4)));
stmt.close();
con.close();
} catch(Exception e) {
e.printStackTrace();
}
pageCOUNT = 0;
positionCOUNT = 0;
typeCOUNT = 0;
cidCOUNT = 0;
initdone = true;
}
public static void main(String[] args) {
PageMakeupBean a = new PageMakeupBean(new DBSettingsBean());
for(int i = 0; i < a.getsize(); i++) {
System.out.println(a.getpage());
System.out.println(a.getposition());
System.out.println(a.gettype());
System.out.println(a.getcid());
System.out.println("***");
}
}
}
