<%@ page info="SPSIntranet Page Displayer" %>
<%@ page import="org.haywired.*" %>
<%@ page import="org.haywired.firefly.*" %>

<%@ include file="../includes/auth.inl" %>

<% DBSettingsBean db = new DBSettingsBean();
db.setdb("SPS");

GroupMapsBean gb = new GroupMapsBean(db);
int gid = Integer.parseInt(request.getParameter("gid"));
gb.setgid(gid); %>
