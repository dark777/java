<html>

<head>
<!-- sjehay-ok 150500 -->
<TITLE>Firefly Add Section</TITLE>
<style>
body { font: 10px, Verdana, Tahoma, Arial }
td { font: 10px, Verdana, Tahoma, Arial }
.stdbtn { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
.stdedit { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
strong { font: 10px, Verdana, Tahoma, Arial; font-weight: bold }
</style>
<script language="javascript">
function show_color_window(bg_color)
        {
        tempWindow = window.open("","","width=80,height=80") ;
        tempWindow.document.write('<html><HEAD><title>Sample</title></head><BODY BGCOLOR=',bg_color,'></body></html>') ;
        tempWindow.document.close() ;
        setTimeout("tempWindow.close();",3000) ;
        }

</script>

</head>

<body background="../images/as.jpg">
<form action="as2.jsp" name="thisform">

<%@ include file="../includes/as1p1.inl" %>

<table width="100%" border=0">
<tr><td height="50" colspan=2></td><tr>
<tr><td colspan=2><hr color="black" width="100%" size=1><strong>Firefly Add Section</strong><hr color="black" width="100%" size=1></td></tr>
<tr><td width="110" align=right>
Name:
</td><td><input type=text name="name" length=32 class=stdedit></td></tr>
<tr><td align=right valign=top>Colour:</td><td><input type=text name="colour" length=8 class=stdedit><br><input type="button" onClick="show_color_window(thisform.colour.value)" value="Preview" class='stdbtn'></td>
</tr>

<tr><td align="right">Parent:</td>
<td><input type="text" name="parent" length=32 class=stdedit value=-1></td></tr>

<tr><td align="right">Academic?</td>
<td><input type="checkbox" name="academic" checked></td></tr>

<tr height=25><td span=2></td></tr>
<tr><td></td><td><input type="submit" value="Create" class='stdbtn'></td></tr>
</table>
</form>
</body>
</html>
