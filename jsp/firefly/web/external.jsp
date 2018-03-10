<html>
  <head>
    <title>Firefly External Link</title>
    <frameset rows=40,* border=0 framepadding=0 framespacing=0>
      <frame src="external-topbar.jsp?<%= request.getHeader("Referer") %>" scrolling=no>
      <frame src="<%= request.getQueryString() %>">
    </frameset>
  </head>
  <body>
    A frames-capable browser is required.
  </body>
</html>
