package org.haywired;
import java.net.URL;
import java.sql.*;
import java.util.*;
class SectionsDataBean {
/* @version 1.5.1 */
private int sid;
private String name;
private int homepage;
private int parent;
private String colour;
private int readgid;
private int writegid;
SectionsDataBean(int sida, String namea, int homepagea, int parenta, String coloura, int readgida, int writegida) {
sid = sida;
name = namea;
homepage = homepagea;
parent = parenta;
colour = coloura;
readgid = readgida;
writegid = writegida;
}
public int getsid() {
return sid;
}
public String getname() {
return name;
}
public int gethomepage() {
return homepage;
}
public int getparent() {
return parent;
}
public String getcolour() {
return colour;
}
public int getreadgid() {
return readgid;
}
public int getwritegid() {
return writegid;
}
}
public class SectionsBean {
/* @version 1.5.1 */
private DBSettingsBean db;
private Vector v;
private int sid;
private int sidCOUNT;
private boolean sidSET;
private String name;
private int nameCOUNT;
private boolean nameSET;
private int homepage;
private int homepageCOUNT;
private boolean homepageSET;
private int parent;
private int parentCOUNT;
private boolean parentSET;
private String colour;
private int colourCOUNT;
private boolean colourSET;
private boolean initdone;
private int readgid;
private int readgidCOUNT;
private boolean readgidSET;
private int writegid;
private int writegidCOUNT;
private boolean writegidSET;

public SectionsBean(DBSettingsBean a) {
  db = a;
}
public int getsize() {
if(!initdone) init();
return v.size();
}
public int getsid() {
if(!initdone) init();
if(v.size() != 0)
  return ((SectionsDataBean)v.elementAt(sidCOUNT++)).getsid();
else return -888;
}
public void setsid(int a) {
sid = a;
sidSET = true;
}
public String getname() {
if(!initdone) init();
if(v.size() != 0)
  return ((SectionsDataBean)v.elementAt(nameCOUNT++)).getname();
else return "Orphan!";
}
public void setname(String a) {
name = a;
nameSET = true;
}
public int gethomepage() {
if(!initdone) init();
return ((SectionsDataBean)v.elementAt(homepageCOUNT++)).gethomepage();
}
public int getparent() {
if(!initdone) init();
return ((SectionsDataBean)v.elementAt(parentCOUNT++)).getparent();
}
public void sethomepage(int a) {
homepage = a;
homepageSET = true;
}
public void setparent(int a) {
parent = a;
parentSET = true;
}
public String getcolour() {
if(!initdone) init();
return ((SectionsDataBean)v.elementAt(colourCOUNT++)).getcolour();
}
public void setcolour(String a) {
colour = a;
colourSET = true;
}

public int getreadgid() {
if(!initdone) init();
return ((SectionsDataBean)v.elementAt(readgidCOUNT++)).getreadgid();
}
public void setreadgid(int a) {
readgid = a;
readgidSET = true;
}
public int getwritegid() {
if(!initdone) init();
return ((SectionsDataBean)v.elementAt(writegidCOUNT++)).getwritegid();
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
String query = "SELECT sid, name, homepage, parent, colour, readgid, writegid FROM Sections";
boolean wheredone = false;
if(sidSET) {
if(!wheredone) {
query = query + " WHERE ";
wheredone = true;
}
else
query = query + " AND ";
query = query + "sid = " + sid;
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
if(homepageSET) {
if(!wheredone) {
query = query + " WHERE ";
wheredone = true;
}
else
query = query + " AND ";
query = query + "homepage = " + homepage;
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
if(colourSET) {
if(!wheredone) {
query = query + " WHERE ";
wheredone = true;
}
else
query = query + " AND ";
query = query + "colour = " + colour;
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

query = query + " ORDER BY sid";
ResultSet res = stmt.executeQuery(query);
while(res.next())
v.addElement(new SectionsDataBean(res.getInt(1), res.getString(2), res.getInt(3), res.getInt(4), res.getString(5), res.getInt(6), res.getInt(7)));
stmt.close();
con.close();
} catch(Exception e) {
e.printStackTrace();
}
sidCOUNT = 0;
nameCOUNT = 0;
homepageCOUNT = 0;
parentCOUNT = 0;
colourCOUNT = 0;
readgidCOUNT = 0;
writegidCOUNT = 0;
initdone = true;
}
public static void main(String[] args) {
SectionsBean a = new SectionsBean(new DBSettingsBean());
for(int i = 0; i < a.getsize(); i++) {
System.out.println(a.getsid());
System.out.println(a.getname());
System.out.println(a.gethomepage());
System.out.println(a.getcolour());
System.out.println("***");
}
}
}
