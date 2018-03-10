<%@ include file="../includes/mod1.inl" %>

<html>
<head>
<!-- sjehay-ok 150500 -->
<TITLE>Firefly Modify</TITLE>
<style>
body { font: 10px, Verdana, Tahoma, Arial }
td { font: 10px, Verdana, Tahoma, Arial }
.stdbtn { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
.stdedit { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
strong { font: 10px, Verdana, Tahoma, Arial; font-weight: bold }
</style>

</head>

<body background="../images/mod.jpg">
<form action="mod2.jsp" method="post" name="thisform">
<table width="100%" border=0">
<tr><td height="50"></td><tr>
<tr><td><hr color="black" width="100%" size=1><strong>Firefly Modify</strong><hr color="black" width="100%" size=1></td></tr>
<tr><td>Modify the content in the text box below.</td></tr>

<%@ include file="../includes/mod1p1.inl" %>

<tr><td><input type="submit" value="Modify" class='stdbtn'></td></tr>
</table>
</form>
</body>
</html>