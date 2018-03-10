<%@ page info="SPSIntranet Page Displayer" %>
<%@ page import="org.haywired.*" %>
<%@ page import="org.haywired.firefly.*" %>

<%@ include file="../includes/auth.inl" %>

<% DBSettingsBean db = new DBSettingsBean();
db.setdb("SPS");
SetGroupsBean ssb = new SetGroupsBean(db);
if(request.getParameter("name") != null) ssb.setname(request.getParameter("name"));
System.out.println(request.getParameter("name"));
ssb.go(); %>
