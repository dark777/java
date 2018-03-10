<%-- Content starts here --%>

<%@ include file="content-noadd.inl" %>

<%-- Now, if you can write, an 'Add Content' box: --%>

<% if(canwrite) { %>

<% HttpSession acse = request.getSession();
if(acse.getAttribute("b").toString().equals("e")) { %>
<%@ include file="addcontent-new.inl" %>
<% } else { %>
<%@ include file="addcontent-traditional.inl" %>
<% } %>

<% } %>

<%-- Content ends here --%>
