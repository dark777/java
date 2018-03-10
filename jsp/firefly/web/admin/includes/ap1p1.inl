<% if(request.getParameter("page") != null) if(!request.getParameter("page").equals("")) { %>
<input type="hidden" name="page" value=<%= Integer.parseInt(request.getParameter("page")) %>>
<% } %>
<input type="hidden" name="headeronly" value=<%= request.getParameter("headeronly") %>>
