<html>
<head>
<!-- sjehay-ok 150500 -->
<TITLE>Firefly Access Policy</TITLE>
<style>
body { font: 12px, Verdana, Tahoma, Arial; background-repeat: no-repeat }
td { font: 12px, Verdana, Tahoma, Arial }
.stdbtn { font: 12px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
.stdedit { font: 12px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
strong { font: 12px, Verdana, Tahoma, Arial; font-weight: bold }
</style>
</head>

<body background="ict.jpg">
<table width="100%" border=0" cellspacing=10>
<tr><td height="50" colspan=2></td><tr>
<tr><td colspan=2><hr color="black" width="100%" size=1><strong>Firefly Access Policy</strong><hr color="black" width="100%" size=1></td></tr>
<tr><td colspan=2><p>Firefly detected that you have not agreed to the ICT access policy in the last year. Click <a href="firefly-pd.jsp?page=15&db=CC">here</a> to go to the ICT access policy page.</td></tr>
<tr><td colspan=2><p><b>WARNING:</b> If you do not agree to the terms of the ICT access policy, you will no longer have access to the school computer system. Failure to agree to the terms within seven days of this notice will count as disagreement.</td></tr>
<% CheckPolicyTimeBean c = new CheckPolicyTimeBean(db);
long viewed = c.getlastviewed(user);
if(viewed < 0) {
UpdatePolicyTimeBean u = new UpdatePolicyTimeBean(db);
u.updatelastviewed(user);
} else {
long expire = viewed + 604800000;
if(expire < (new java.util.Date().getTime())) { 
// code here to send angry e-mail to wrtl, etc. 3%>
<tr><td colspan=2><p><b>YOU HAVE FAILED TO AGREE TO THE POLICY.  YOUR ACCOUNT WILL BE DISABLED.</b></p></td></tr>
<% } else { %>
<tr><td colspan=2><p>You have until <%= new java.util.Date(expire).toString() %> to agree to the policy.</p></td></tr>
<% } } %>
</table>
</body>
</html>
