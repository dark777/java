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

// save away the current page for later use
int pg = Integer.parseInt(request.getParameter("page"));

// POLICY CHECKING

if(pg == 15) {
  if(db.getdb().equals("CC")) {
    policyok = true;
  }
}

if(policyok) {

// SECURITY CHECKING

boolean canread = true;
boolean canwrite = false;
if(au.checkGroup(-1, user)) canwrite = true;

if(canread) { %>

<html>
<head>
</head>
<style>
body { background-image: url(standard.jpg); background-repeat: no-repeat; font-family: Verdana,Arial,Helvetica; font-size: 10pt }
</style>
<script>
function OpenAddImage() {
  window.open('admin/picture/picture1.jsp?page=' + <%= pg %>,'C','toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,copyhistory=no,scrollbars=no,width=374,height=383');
}
</script>
<body bgcolor="#FFFFFF">

<table height=50 border=0><tr><td height=50>&nbsp;</td></tr></table>

<hr color="black" width="100%" size=1><strong><font size="2">Firefly - Simple View</font></strong><hr color="black" width="100%" size=1>

<%@ include file="content.inl" %>
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