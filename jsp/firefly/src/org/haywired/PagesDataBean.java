package org.haywired;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class PagesDataBean {
/* @version 1.5.1 */
private int page;
private int sid;
private String title;
private String linktitle;
private String defstext;
private int parent;
private boolean headeronly;
private int readgid;
private int writegid;
public PagesDataBean(int pagea, int sida, String titlea, String linktitlea, String defstexta, int parenta, boolean headeronlya, int readgida, int writegida) {
page = pagea;
sid = sida;
title = titlea;
linktitle = linktitlea;
defstext = defstexta;
parent = parenta;
headeronly = headeronlya;
readgid = readgida;
writegid = writegida;
}
public PagesDataBean(int pagea, int sida, String titlea, String linktitlea, String defstexta, int parenta, int headeronlya, int readgida, int writegida) {
page = pagea;
sid = sida;
title = titlea;
linktitle = linktitlea;
defstext = defstexta;
parent = parenta;
headeronly = false;
if(headeronlya > 0) headeronly = true;
readgid = readgida;
writegid = writegida;
}
public int getpage() {
return page;
}
public int getsid() {
return sid;
}
public String gettitle() {
return title;
}
public String getlinktitle() {
return linktitle;
}
public String getdefstext() {
return defstext;
}
public int getparent() {
return parent;
}
public boolean getheaderonly() {
return headeronly;
}
public int getreadgid() {
if(readgid == -9) readgid = (-100 - readgid);
return readgid;
}
public int getwritegid() {
if(writegid == -9) writegid = (-100000 - writegid);
return writegid;
}
}
