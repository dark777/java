<% if(request.getParameter("db") != null) if(!request.getParameter("db").equals("")) { %>
<input type="hidden" name="db" value="<%= request.getParameter("db") %>">
<% } %>