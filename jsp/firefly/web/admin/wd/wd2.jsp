<%@ include file="../includes/wd2.inl" %>

<html>
<head>
<!-- sjehay-ok 160500 -->
<style>
body { font: 10px, Verdana, Tahoma, Arial }
td { font: 10px, Verdana, Tahoma, Arial }
.stdbtn { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
.stdedit { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
strong { font: 10px, Verdana, Tahoma, Arial; font-weight: bold }
</style>


<title>Firefly Wipe Database</title>
</head>
<body background="../images/wd.jpg">
<form onSubmit="window.close()" name="thisform">
<table width="100%" border=0>
<tr><td height="50" colspan=2></td><tr>
<tr><td colspan=2><hr color="black" width="100%" size=1><strong>Firefly Wipe Database</strong><hr color="black" width="100%" size=1></td></tr>
<tr><td colspan=2>Your commands were performed - database <%= db.getdburl() %> wiped.

<%@ include file="../includes/wd2p1.inl" %>

</td></tr>
<tr><td colspan=2><i>Everything is gone;<br>
Your life's work is destroyed.<br>
Squeeze trigger (yes/no)?</td></tr>
<tr><td></td><td></td></tr>
<tr><td colspan=2><input type="submit" value="Dismiss" class='stdbtn'>
</td></tr></table>
</body>
</html>

<%@ include file="../includes/auth2.inl" %>