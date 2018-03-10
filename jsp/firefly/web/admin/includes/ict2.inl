<%@ page info="SPSIntranet Page Displayer" %>
<%@ page import="org.haywired.*" %>
<%@ page import="org.haywired.firefly.*" %>

<%@ include file="../includes/auth.inl" %>

<% DBSettingsBean db = new DBSettingsBean();
HttpSession sedb = request.getSession();
if(request.getParameter("db") != null) if(!request.getParameter("db").equals(""))
  sedb.setAttribute("db", request.getParameter("db").toString());
if(sedb.getAttribute("db") != null)
  db.setdb(sedb.getAttribute("db").toString());

CheckPolicyTimeBean cb = new CheckPolicyTimeBean(db);
String username = "Unknown";
if(request.getParameter("username") != null) username = request.getParameter("username"); 
String status = "NOT OK";
String tcolour = "FF0000";
if(cb.check(username)) {
status = "OK";
tcolour = "00FF00";
}
String agreed = new java.util.Date(cb.getlastagreed(username)).toString();
String viewed = new java.util.Date(cb.getlastviewed(username)).toString();
%>