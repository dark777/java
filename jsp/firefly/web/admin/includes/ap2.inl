<%@ page info="SPSIntranet Page Displayer" %>
<%@ page import="org.haywired.*" %>
<%@ page import="org.haywired.firefly.*" %>

<% DBSettingsBean db = new DBSettingsBean(); %>

<%@ include file="../includes/auth.inl" %>

<% HttpSession sedb = request.getSession();
if(sedb.getAttribute("db") != null) db.setdb(sedb.getAttribute("db").toString()); %>

<% SetPagesBean spb = new SetPagesBean(db);
if(request.getParameter("page") != null) spb.setpage(Integer.parseInt(request.getParameter("page")));
if(request.getParameter("sid") != null) spb.setsid(Integer.parseInt(request.getParameter("sid")));
if(request.getParameter("title") != null) spb.settitle(request.getParameter("title"));
if(request.getParameter("linktitle") != null) spb.setlinktitle(request.getParameter("linktitle"));
if(request.getParameter("defstext") != null) spb.setdefstext(request.getParameter("defstext"));
if(request.getParameter("parent") != null) { spb.setparent(Integer.parseInt(request.getParameter("parent"))); }
else { spb.setparent(-1); }
if(request.getParameter("headeronly") != null) {
	if(request.getParameter("headeronly").equalsIgnoreCase("yes")) spb.setheaderonly(true);
	else spb.setheaderonly(false);
} else spb.setheaderonly(false);
spb.go(); %>