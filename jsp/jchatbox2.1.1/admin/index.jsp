<html>
<%@ page import="jChatBox.Util.*" %>
<%@ include file="../configureme.jsp" %>
<jsp:useBean id="SystemLogin" class="jChatBox.Service.SystemLogin" scope="request" />
<%!
	// Overides jspDestroy method to backup jChatBox.
	public void jspDestroy()
	{
		jChatBox.Util.Debug.log(0,"jChatBox sysDestroy() ...","");
		jChatBox.Chat.ChatroomManager chatroomManager = jChatBox.Chat.ChatroomManager.getInstance();
		chatroomManager.destroy();
		jChatBox.Util.Debug.destroy();
	}
%>
<%
	String jspDisplay = SystemLogin.doLogin(request,session);
	if (jspDisplay != null)
	{
		%><jsp:forward page='<%= jspDisplay %>' /><%
		//response.sendRedirect(jspDisplay);
	}
%>
<head>
<LINK REL=STYLESHEET TYPE="text/css" HREF="styles/admin.css">
<title>jChatBox Login</title>
</head>
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" link="#9999CC" alink="#9999CC" vlink="#9999CC">
<center>
<%@ include file="header.jsp" %>
</center>
<form method="post" action="index.jsp" name="chat">
  <div align="center"><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><b><font color="#FF0000">
  <%
  	String error = SystemLogin.getSysMessage();
  	if (error != null)
  	{
		if (error.equals(Conf.ACCESSDENIED)) out.print("Access Denied : "+request.getRemoteAddr());
  	}
  %>
  </font></b></font><br>
    <br>
  </div>
  <table width="40%" border="0" cellspacing="1" cellpadding="0" align="center">
    <tr>
      <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr bgcolor="#999999">
            <td bgcolor="#003399">
              <table width="100%" border="0" cellspacing="1" cellpadding="2">
                <tr bgcolor="#FFFFFF">
                  <td nowrap>
                    <table width="100%" border="0" cellspacing="1" cellpadding="1" align="center">
                      <tr>
                        <td nowrap> <font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><b>
                          </b></font><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><b><font color="#000099">Login
                          :</font></b></font> </td>
                        <td nowrap>
                          <input type="text" name="name" size="16">
                        </td>
                      </tr>
                      <tr>
                        <td nowrap> <font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><b><font color="#000099">Password
                          :</font></b></font> <font face="Verdana, Arial, Helvetica, sans-serif" size="-2">
                          </font></td>
                        <td nowrap>
                          <input type="password" name="password" size="16">
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <br>
  <br>
<center>
 <input type="submit" name="SystemLogin" value="Submit">
 <input type="reset" value="Reset">
</center>
</form>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<%@ include file="footer.jsp" %>
</body>
</html>
