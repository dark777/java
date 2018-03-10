<% PageMakeupBean pmb = new PageMakeupBean(db);
pmb.setpage(pg);
for(int i = 0; i < pmb.getsize(); i++) {
  int hcbcid = -1;
  int type = pmb.gettype();
  if(type == 0) {
  HTMLComponentsBean hcb = new HTMLComponentsBean(db);
  hcb.setcid(pmb.getcid()); 
  hcbcid = hcb.getcid(); %>

  <%= hcb.getdata() %>

<%-- If you can write, a 'delete component' button: --%>
<%-- And a modify button ;-) OK --%>

<% if(canwrite) { %>

<a href="servlet/org.haywired.firefly.DeleteHTMLServlet?cid=<%= hcbcid %>&page=<%= pg %>" onclick="return confirm('Are you sure you want to delete this component?')"><img
src="icons/Delete.gif" border=0></a>

<a href="javascript:OpenModify(<%= hcbcid %>)"><img src="icons/write.gif" border=0></a>

  <%
} %>

  <% } else if(type == 1) {
  ImageComponentsBean icb = new ImageComponentsBean(db);
  icb.setcid(pmb.getcid());
  hcbcid = icb.getcid(); %>

  <center><img src="/resources/firefly/i<%= hcbcid %><%= icb.getname() %>" border=1></center>

<%-- If you can write, a 'delete component' button: --%>
<%-- And a modify button ;-) OK --%>

<% if(canwrite) { %>

<a href="servlet/org.haywired.firefly.DeleteImageServlet?cid=<%= hcbcid %>&page=<%= pg %>" onclick="return confirm('Are you sure you want to delete this component?')"><img
src="icons/Delete.gif" border=0></a>

  <%
} %>

  <% } %>

<% } %>