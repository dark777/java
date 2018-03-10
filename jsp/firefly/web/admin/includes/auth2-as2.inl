<% } else {
String path = "/firefly/logon/logon.jsp?back=" + request.getServletPath() + "?" + request.getQueryString();
response.sendRedirect(path); } %>