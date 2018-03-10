<%@ include file="../includes/picture1.inl" %>

<html>
<head>
<style>
body { font: 10px, Verdana, Tahoma, Arial }
td { font: 10px, Verdana, Tahoma, Arial }
.stdbtn { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
.stdedit { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
.stdfile { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 200px }
strong { font: 10px, Verdana, Tahoma, Arial; font-weight: bold }
</style>


<!-- sjehay-ok 180500 -->
<title>Firefly Add Image</title>
</head>
<body background="../images/picture.jpg">
<form ENCTYPE="multipart/form-data" action="picture2.jsp" method="post">

<table width="100%" border=0>
<tr><td height="50" colspan=2></td><tr>
<tr><td colspan=2><hr color="black" width="100%" size=1><strong>Firefly Add Image</strong><hr color="black" width="100%" size=1></td></tr>
<%-- <tr><td width="110" align=right>Name:</td>
<td><input type=text name="name" class=stdedit></td></tr> --%>

<tr><td width="110" align=right>File:</td>
<td><input type=file name="FileToUpload" class=stdfile></td></tr>

<%@ include file="../includes/picture1p1.inl" %>

<tr height=25><td span=2></td></tr>
<tr><td></td><td><input type="submit" value="Add" class=stdbtn></td></tr>
</table>
</form>
</body>
</html>
