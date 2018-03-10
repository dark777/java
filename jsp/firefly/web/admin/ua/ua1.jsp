<%@ include file="../includes/ua1.inl" %>

<html>
<head>
<style>
body { font: 10px, Verdana, Tahoma, Arial }
td { font: 10px, Verdana, Tahoma, Arial }
.stdbtn { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
.stdedit { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
strong { font: 10px, Verdana, Tahoma, Arial; font-weight: bold }
</style>

<title>Firefly Add User to Group</title>
</head>
<body background="../images/ua.jpg">
<form action="ua2.jsp" name="thisform">

<table width="100%" border=0>
<tr><td height="50" colspan=2></td><tr>
<tr><td colspan=2><hr color="black" width="100%" size=1><strong>Firefly Add User to Group</strong><hr color="black" width="100%" size=1></td></tr>
<tr><td width="110" align=right>User:</td>
<td><input type=text name="user" length=32 class=stdedit></td></tr>

<%@ include file="../includes/ua1p1.inl" %>

<tr height=25><td span=2></td></tr>
<tr><td></td><td><input type="submit" value="Add" class=stdbtn></td></tr>
</table>
</form>
</body>
</html>