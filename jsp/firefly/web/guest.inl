<html>
<head>
<!-- sjehay-ok 150500 -->
<TITLE>Enter Intranet</TITLE>
<style>
body { font: 12px, Verdana, Tahoma, Arial }
td { font: 12px, Verdana, Tahoma, Arial }
.stdbtn { font: 12px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
.stdedit { font: 12px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
strong { font: 12px, Verdana, Tahoma, Arial; font-weight: bold }
</style>
</head>
<body bgcolor="#FFFFFF">
<center>
<h1>Enter Intranet</h1>
<p>To enter the intranet, click on the image below.</p>

<script>
brsInt = parseInt(navigator.appVersion);
var blnk = '<a href="logon/logon.jsp?b=n&back=/firefly/firefly-pd.jsp">';
if (brsInt > 3) { if(document.all) {
blnk = '<a href="logon/logon.jsp?b=e&back=/firefly/firefly-pd.jsp">';
} }
document.write(blnk);
</script>

<img src="SPScrest.gif" height="100%" alt="Enter" border=0>
</a>
</center>
</body>
</html>
