<%@ page info="SPSIntranet Page Displayer" %>
<%@ page import="org.haywired.*" %>
<% DBSettingsBean db = new DBSettingsBean();
HttpSession sedb = request.getSession();
if(sedb.getAttribute("db") != null) db.setdb(sedb.getAttribute("db").toString());
SectionsBean sb = new SectionsBean(db); %>