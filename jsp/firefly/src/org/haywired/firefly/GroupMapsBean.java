package org.haywired.firefly;

import org.haywired.*;
import java.net.URL;
import java.sql.*;
import java.util.*;
class GroupMapsDataBean {
/* @version 1.5.1 */
private int gid;
private String user;
private int homepage;
private int parent;
private String colour;
GroupMapsDataBean(int gida, String usera) {
gid = gida;
user = usera;
}
public int getgid() {
return gid;
}
public String getuser() {
return user;
}
}
public class GroupMapsBean {
/* @version 1.5.1 */
private DBSettingsBean db;
private Vector v;
private int gid;
private int gidCOUNT;
private boolean gidSET;
private String user;
private int userCOUNT;
private boolean userSET;
private boolean initdone;
public GroupMapsBean(DBSettingsBean a) {
  db = a;
}
public int getsize() {
if(!initdone) init();
return v.size();
}
public int getgid() {
if(!initdone) init();
return ((GroupMapsDataBean)v.elementAt(gidCOUNT++)).getgid();
}
public void setgid(int a) {
gid = a;
gidSET = true;
}
public String getuser() {
if(!initdone) init();
return ((GroupMapsDataBean)v.elementAt(userCOUNT++)).getuser();
}
public void setuser(String a) {
user = a;
userSET = true;
}

private void init() {
v = new Vector();
try {
Class.forName(db.getdriver());
Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
Statement stmt = con.createStatement();
String query = "SELECT gid, user FROM GroupMaps";
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
if(userSET) {
if(!wheredone) {
query = query + " WHERE ";
wheredone = true;
}
else
query = query + " AND ";
query = query + "user = '" + user + "'";
}
query = query + " ORDER BY gid";
System.out.println(query);
ResultSet res = stmt.executeQuery(query);
while(res.next())
v.addElement(new GroupMapsDataBean(res.getInt(1), res.getString(2)));
stmt.close();
con.close();
} catch(Exception e) {
e.printStackTrace();
}
gidCOUNT = 0;
userCOUNT = 0;
initdone = true;
}
}
