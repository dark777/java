<%@ page info="SPSIntranet Page Displayer" %>
<%@ page import="org.haywired.*" %>
<%@ page import="org.haywired.firefly.*" %>

<%@ include file="../includes/auth.inl" %>

<% DBSettingsBean db = new DBSettingsBean();
db.setdb("SPS"); %>

<% SetGroupMapsBean ssb = new SetGroupMapsBean(db);
ssb.setgid(Integer.parseInt(request.getParameter("gid")));
ssb.setuser(request.getParameter("user"));
ssb.go(); %>
