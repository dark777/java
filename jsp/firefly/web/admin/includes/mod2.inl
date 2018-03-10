<%@ page info="SPSIntranet Page Displayer" %>
<%@ page import="org.haywired.*" %>
<%@ page import="org.haywired.firefly.*" %>

<% DBSettingsBean db = new DBSettingsBean(); %>

<%@ include file="../includes/auth.inl" %>

<% HttpSession sedb = request.getSession();
if(sedb.getAttribute("db") != null) db.setdb(sedb.getAttribute("db").toString()); %>

<% UpdateHTMLComponentsBean uhb = new UpdateHTMLComponentsBean(db);
if(request.getParameter("cid") != null) uhb.setoldcid(Integer.parseInt(request.getParameter("cid")));
uhb.setnewdata(request.getParameter("data"));
uhb.go(); %>