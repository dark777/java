<%@ include file="../includes/ap1.inl" %>

<html>
<head>
<style>
body { font: 10px, Verdana, Tahoma, Arial }
td { font: 10px, Verdana, Tahoma, Arial }
.stdbtn { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
.stdedit { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
strong { font: 10px, Verdana, Tahoma, Arial; font-weight: bold }
</style>


<!-- sjehay-ok 180500 -->
<title>Firefly Add Page</title>
</head>
<body background="../images/ap.jpg">
<form action="ap2.jsp" name="thisform">

<%@ include file="../includes/ap1p1.inl" %>

<table width="100%" border=0>
<tr><td height="50" colspan=2></td><tr>
<tr><td colspan=2><hr color="black" width="100%" size=1><strong>Firefly Add Page</strong><hr color="black" width="100%" size=1></td></tr>
<tr><td width="110" align=right>
Name:
</td><td><input type=text name="title" length=32 class=stdedit></td></tr>

<%@ include file="../includes/ap1p2.inl" %>

<tr><td align=right>Link title:</td>
<td><input type="text" name="linktitle" length=64 class=stdedit></td></tr>

<tr><td align=right>External link:</b></td>
<td><input type="text" name="defstext" length=128 class=stdedit></td></tr>

<tr><td align=right>Parent:</b></td>
<td><input type="text" name="parent" value="-1" length=128 class=stdedit></td></tr>

<tr height=25><td span=2></td></tr>
<tr><td></td><td><input type="submit" value="Create" class=stdbtn></td></tr>
</table>
</form>
</body>
</html>