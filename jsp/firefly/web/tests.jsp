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
<TITLE>Firefly Tests</TITLE>
<style>
body { background-image: url(standard.jpg); background-repeat: no-repeat; font-family: Verdana,Arial,Helvetica; font-size: 10pt }
td { font-family: Verdana,Arial,Helvetica; font-size: 10pt }
.stdbtn { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
button { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 35px }
.stdedit { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
.std {font: 10px, Verdana, Tahoma, Arial}
strong { font: 10px, Verdana, Tahoma, Arial; font-weight: bold }
</style>

</head>

<body bgcolor="#FFFFFF">

<a name="top"></a>

<table height=50 border=0><tr><td height=50>&nbsp;</td></tr></table>

<hr color="black" width="100%" size=1><strong><font size="2">Firefly Tests</font></strong><hr color="black" width="100%" size=1>

<% DBSettingsBean db = new DBSettingsBean();
HttpSession sedb = request.getSession();
if(sedb.getAttribute("db") != null) db.setdb(sedb.getAttribute("db").toString()); 
TestsBean tb = new TestsBean(db);
tb.setsid(Integer.parseInt(request.getParameter("sid")));
SectionsBean sb = new SectionsBean(db);
sb.setsid(tb.getsid()); %>

<center>
<h1><%= sb.getname() %></h1>
Please choose a test from the list below.
<p>

<% for(int i = 0; i < tb.getsize(); i++) { %>
<a href="test.jsp?tid=<%= tb.gettid() %>"><%= tb.gettitle() %></a><br>
<% } } %>

<p><a href="#top">Back to Top</a></p>
</center>
</body>
</html>
