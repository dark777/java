<%@ page import="org.haywired.*" %>
<%@ page import="org.haywired.firefly.*" %>

<% String back = "/firefly/";
if(request.getParameter("back") != null) back = request.getParameter("back");
else if(request.getHeader("Referer") != null) back = request.getHeader("Referer");

DBSettingsBean db = new DBSettingsBean();
db.setdb("sessions");

HttpSession se = request.getSession();
se.setAttribute("b", request.getParameter("b"));

LogonBean l = new LogonBean(db);
l.setuser(request.getRemoteUser());
l.go(request); %>

<html>
<head>
<script>
location.href = '<%= back %>';
</script>
</head>
<body bgcolor="#FFFFFF">
<center>
<a href="<%= back %>">If you are not automatically redirected, please click here.</a>
</center>
</body>
</html>
