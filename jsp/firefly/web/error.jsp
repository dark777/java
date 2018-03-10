<% int error = Integer.parseInt(request.getParameter("error"));

String title = "Page Not Found";
String message = "The requested page was not found.";
boolean report = false; 

if(error == 403) {
title = "Access Denied"; 
message = "You don't have permission to perform that operation.";
} %>
<html>
<head>
<!-- sjehay-ok 150500 -->
<TITLE>Firefly - <%= title %></TITLE>
<style>
body { font: 12px, Verdana, Tahoma, Arial; background-repeat: no-repeat }
td { font: 12px, Verdana, Tahoma, Arial }
.stdbtn { font: 12px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
.stdedit { font: 12px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
strong { font: 12px, Verdana, Tahoma, Arial; font-weight: bold }
</style>
</head>

<body background="error.jpg" bgcolor="#FFFFFF">
<table width="100%" border=0" cellspacing=10>
<tr><td height="50" colspan=2></td><tr>
<tr><td colspan=2><hr color="black" width="100%" size=1><strong>Firefly
- <%= title %></strong><hr color="black" width="100%" size=1></td></tr>
<tr><td colspan=2>
<p><b>Error:</b> <%= error %>
<p><%= message %>
</td></tr>
</table>
</body>
</html>