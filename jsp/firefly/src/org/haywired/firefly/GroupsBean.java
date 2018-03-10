package org.haywired.firefly;

import org.haywired.*;
import java.net.URL;
import java.sql.*;
import java.util.*;
class GroupsDataBean {
/* @version 1.5.1 */
private int gid;
private String name;
private int homepage;
private int parent;
private String colour;
GroupsDataBean(int gida, String namea) {
gid = gida;
name = namea;
}
public int getgid() {
return gid;
}
public String getname() {
return name;
}
}
public class GroupsBean {
/* @version 1.5.1 */
private DBSettingsBean db;
private Vector v;
private int gid;
private int gidCOUNT;
private boolean gidSET;
private String name;
private int nameCOUNT;
private boolean nameSET;
private boolean initdone;
public GroupsBean(DBSettingsBean a) {
  db = a;
}
public int getsize() {
if(!initdone) init();
return v.size();
}
public int getgid() {
if(!initdone) init();
return ((GroupsDataBean)v.elementAt(gidCOUNT++)).getgid();
}
public void setgid(int a) {
gid = a;
gidSET = true;
}
public String getname() {
if(!initdone) init();
return ((GroupsDataBean)v.elementAt(nameCOUNT++)).getname();
}
public void setname(String a) {
name = a;
nameSET = true;
}

private void init() {
v = new Vector();
try {
Class.forName(db.getdriver());
Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
Statement stmt = con.createStatement();
String query = "SELECT gid, name FROM Groups";
boolean wheredone = false;
if(gidSET) {
if(!wheredone) {
query = query + " WHERE ";
wheredone = true;
}
else
query = query + " AND ";
query = query + "gid = " + gid;
}
if(nameSET) {
if(!wheredone) {
query = query + " WHERE ";
wheredone = true;
}
else
query = query + " AND ";
query = query + "name = " + name;
}
query = query + " ORDER BY gid";
ResultSet res = stmt.executeQuery(query);
while(res.next())
v.addElement(new GroupsDataBean(res.getInt(1), res.getString(2)));
stmt.close();
con.close();
} catch(Exception e) {
e.printStackTrace();
}
gidCOUNT = 0;
nameCOUNT = 0;
initdone = true;
}
}
