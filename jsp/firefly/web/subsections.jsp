<%@ page import="org.haywired.*" %>
<%@ page import="org.haywired.firefly.*" %>

<%-- Security: --%>
<% AuthBean au = new AuthBean(new DBSettingsBean());
String user = au.auth(request);
if(!user.equals("Guest")) {
boolean isadmin = false;
if(au.checkGroup(-1, user)) isadmin = true; %>

<html>
<head>
<TITLE>Firefly Subsections</TITLE>
<style>
body { background-image: url(standard.jpg); background-repeat: no-repeat; font-family: Verdana,Arial,Helvetica; font-size: 10pt }
td { font-family: Verdana,Arial,Helvetica; font-size: 10pt }
.stdbtn { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
.stdedit { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
.std {font: 10px, Verdana, Tahoma, Arial}
strong { font: 10px, Verdana, Tahoma, Arial; font-weight: bold }
</style>

</head>

<body bgcolor="#FFFFFF">

<a name="top"></a>

<table height=50 border=0><tr><td height=50>&nbsp;</td></tr></table>

<hr color="black" width="100%" size=1><strong><font size="2">Firefly Subsections</font></strong><hr color="black" width="100%" size=1>

<% DBSettingsBean db = new DBSettingsBean();
HttpSession sedb = request.getSession();
if(sedb.getAttribute("db") != null) db.setdb(sedb.getAttribute("db").toString()); 
SectionsBean sa = new SectionsBean(db);
sa.setsid(Integer.parseInt(request.getParameter("sid"))); %>

<center>
<h1><%= sa.getname() %></h1>
Please choose a subsection from the list below.
<p>

<% SectionsBean s = new SectionsBean(db);
s.setparent(Integer.parseInt(request.getParameter("sid")));
for(int i = 0; i < s.getsize(); i++) { %>
<a href="firefly-pd.jsp?page=<%= s.gethomepage() %>"><%= s.getname() %></a> 
<% if(isadmin) { %><a href="servlet/org.haywired.firefly.DeleteSectionServlet?sid=<%= s.getsid() %>"><img src="icons/Delete.gif" border=0></a><% } %><br>
<% } } %>

<p><a href="#top">Back to Top</a></p>
</center>
</body>
</html>
