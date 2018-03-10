<%@ page info="Firefly Page Displayer" %>

<%-- Next line is for Release-000509 beans only - new beans should be in new package. --%>

<%@ page import="org.haywired.*" %>

<%@ page import="org.haywired.firefly.*" %>

<%-- DBSettingsBean db is created; set session DB param, if passed --%>

<jsp:useBean id="db" scope="page" class="org.haywired.DBSettingsBean" />

<%-- Security: --%>
<% AuthBean au = new AuthBean(new DBSettingsBean());
String user = au.auth(request);
if(!user.equals("Guest")) {
boolean isadmin = false;
if(au.checkGroup(-1, user)) isadmin = true; %>

<%-- Check you've agreed to the ICT Policy recently: --%>
<% boolean policyok = false;
if((!user.equals("Guest")) && (!isadmin)) {
CheckPolicyTimeBean cptb = new CheckPolicyTimeBean(new DBSettingsBean()); 
policyok = cptb.check(user); } else { policyok = true; } %>

<% HttpSession sedb = request.getSession();
if(request.getParameter("db") != null)
  sedb.setAttribute("db", request.getParameter("db").toString());
if(sedb.getAttribute("db") != null)
  db.setdb(sedb.getAttribute("db").toString());

/** PagesBean pba is created; it is used to get information about the current page.
If page param is passed, set that property, else set to 0. **/

PagesBean pba = new PagesBean(db);
if(request.getParameter("page") != null) {
  pba.setpage(request.getParameter("page"));
} else {
  pba.setpage(0);
} 

/** SectionsBean sba is created; it is used to get information about the current section.
Set sid to be current page's. **/

SectionsBean sba = new SectionsBean(db);
int sid = pba.getsid();
sba.setsid(sid);

// save away the current page for later use
int pg = pba.getpage();
String title = pba.gettitle();
String sname = sba.getname();

// POLICY CHECKING

if(pg == 15) {
  if(db.getdb().equals("CC")) {
    policyok = true;
  }
}

if(policyok) {

// SECURITY CHECKING

int readgid = pba.getreadgid();
int writegid = pba.getwritegid();
boolean canread = false;
boolean canwrite = false;
if(au.checkGroup(readgid, user)) canread = true;
if(au.checkGroup(writegid, user)) canwrite = true;

if(canread) { %>

<html>
<head>
<title><%= title %></title>
</head>
<style>
td { font-family: Tahoma,Arial,Helvetica; font-size: 9pt }
h1 { font-family: Tahoma,Arial,Helvetica }
</style>
<script>
function OpenAddImage() {
  window.open('admin/picture/picture1.jsp?page=' + <%= pg %> + '&sid=' + <%= sid %>,'C','toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,copyhistory=no,scrollbars=no,width=374,height=383');
}
</script>
<body bgcolor="#FFFFFF"  leftmargin=0 rightmargin=0 topmargin=0 bottommargin=0 marginwidth=0>
<table border=0 bgcolor="#000000" height=30 width="100%"><tr><td><font color="#FFFFFF">
<b>firefly</b> print view | <i><%= sname %></i> | Page: <b><%= pg %></b>; DB: <b><%= db.getdb() %></b>
</font></td></tr></table>
<table border=0 cellpadding=10><tr><td>
<h1><%= title %></h1>
<%@ include file="content.inl" %>
</td></tr></table>
</body>
</html>

<% // this is the end of the canread if
} else { %>
<%@ include file="error403.inl" %>
<% }
// this is the end of the policy-ok if 
} else { %>
<%@ include file="policynotok.inl" %>
<% } 
} else { %>
<%@ include file="guest.inl" %>
<% } %>