<p><center>
<form action="/firefly/servlet/org.haywired.firefly.AddHTMLServlet" method="POST">
  <hr width="100%" size=1 color="black"><span class="sizer"><strong>Add Content</strong></span><hr width="100%" size=1 color="black">
  <p><input type="hidden" name="back" value=<%= request.getRequestURI() + "?" + request.getQueryString() %>>
  <input type="hidden" name="page" value=<%= pg %>>
  <font face="Courier, Courier New">
    <textarea name="data" cols=70 rows=8></textarea>
  </font>
  <br><span class="sizer">Wiki?</span> <input type="checkbox" name="wiki" checked>
  <input type="submit" value="Submit" class="stdbtn"><br>
</form>
<p><input type="button" value="Add Image" onclick="OpenAddImage()" class="stdbtn">
</center>