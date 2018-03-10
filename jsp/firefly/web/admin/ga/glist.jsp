<%@ include file="../includes/glist.inl" %>

<html>
<head>
<style>
body { font: 10px, Verdana, Tahoma, Arial }
td { font: 10px, Verdana, Tahoma, Arial }
.stdbtn { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
.stdedit { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
strong { font: 10px, Verdana, Tahoma, Arial; font-weight: bold }
</style>

<script>
function OpenNewWindow(a) {
  window.open(a,'J','toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,copyhistory=no,scrollbars=no,width=374,height=383');
}
</script>

<!-- sjehay-ok 180500 -->
<title>Firefly Group Administration</title>
</head>
<body background="../images/ua.jpg">
<form onSubmit="window.close()" name="thisform">
<table width="100%" border=0>
<tr><td height="50" colspan=2></td><tr>
<tr><td colspan=2><hr color="black" width="100%" size=1><strong>Firefly Group Administration</strong><hr color="black" width="100%" size=1></td></tr>

<tr><td colspan=2>

<%@ include file="../includes/glistp1.inl" %>
<p><a href="javascript:OpenNewWindow('../ua/ua1.jsp?gid=<%= gid %>')">Add User</a>

</td></tr>

<tr><td></td><td></td></tr>
<tr><td colspan=2><input type="submit" value="Dismiss" class='stdbtn'>
</td></tr></table>
</body>
</html>

<%@ include file="../includes/auth2.inl" %>