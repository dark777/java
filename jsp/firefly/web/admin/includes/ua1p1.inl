<% if(request.getParameter("gid") != null) if(!request.getParameter("gid").equals("")) { %>
<input type="hidden" name="gid" value=<%= Integer.parseInt(request.getParameter("gid")) %>>
<% } else { %>
<tr><td align=right>Group:</td>
<td>
<select name="gid">
<% for(int j = 0; j < sb.getsize(); j++) { %>
<option value="<%= sb.getgid() %>"><%= sb.getname() %></option>
<% } %>
<select>
</td></tr>
<% } %>