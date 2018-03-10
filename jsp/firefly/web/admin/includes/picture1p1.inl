<% if(request.getParameter("sid") != null) { %>
<input type="hidden" name="sid" value=<%= Integer.parseInt(request.getParameter("sid")) %>>
<% } else { %>
<tr><td align=right>Section:</td>
<td><select name="sid">
<% for(int j = 0; j < sb.getsize(); j++) { %>
<option value="<%= sb.getsid() %>"><%= sb.getname() %></option>
<% } %>
<select></td></tr>
<% } %>
<input type="hidden" name="page" value="<%= request.getParameter("page") %>">