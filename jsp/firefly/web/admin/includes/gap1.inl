<b>Groups:</b><p>
<% for(int i = 0; i < gb.getsize(); i++) { %>
<li><a href="javascript:OpenNewWindow('glist.jsp?gid=<%= gb.getgid() %>')"><%= gb.getname() %></a>
<% } %>
