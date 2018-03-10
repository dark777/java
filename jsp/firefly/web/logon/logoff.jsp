<%@ page import="org.haywired.*" %>
<%@ page import="org.haywired.firefly.*" %>

<% String back;
if(request.getParameter("back") != null) back = request.getParameter("back");
else if(request.getHeader("Referer") != null) back = request.getHeader("Referer");
else back = "/firefly/firefly-pd.jsp";
DBSettingsBean db = new DBSettingsBean();
db.setdb("sessions");

LogonBean l = new LogonBean(db);
l.setuser(request.getRemoteUser());
l.setlogoff(true);
l.go(request);
response.sendRedirect(back); %>

<html><body>
<h1>Done.</h1>
</body></html>