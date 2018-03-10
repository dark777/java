<% if(!request.getParameter("sessions").equals("on")) {
if(request.getParameter("create").equals("on")) { %>
<p>To add a Home Section to the database, <a href="../as/as1.jsp?db=<%= request.getParameter("db") %>">click here</a>.
<% } } %>