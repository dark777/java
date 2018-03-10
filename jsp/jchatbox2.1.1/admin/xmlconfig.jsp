<html>
<%@ page import="jChatBox.Util.*,jChatBox.Chat.*,java.util.*" %>
<jsp:useBean id="SystemProcessor" class="jChatBox.Service.SystemProcessor" scope="session" />
<%
	jChatBox.Chat.ChatroomManager ChatroomManager = null;
	String jspDisplay = SystemProcessor.execute(request,session, application);
	if (jspDisplay != null)
	{
		response.sendRedirect(jspDisplay);
	}
	else
	{
		ChatroomManager = jChatBox.Chat.ChatroomManager.getInstance();
	}
%>
<head>
<LINK REL=STYLESHEET TYPE="text/css" HREF="styles/admin.css">
<script language="JavaScript">
<!--
function manage(opt)
{
	if (opt != "")
	{
		location.href="chatroom.jsp?todo=manage&rand=<%= System.currentTimeMillis() %>&id="+opt;
	}
}
function backup()
{
	location.href="menu.jsp?todo=backup&rand=<%= System.currentTimeMillis() %>";
}
function logout()
{
	location.href="index.jsp?todo=logout&rand=<%= System.currentTimeMillis() %>";
}
function save()
{
	if ( (document.xmlconfig.backupfile.value.length > 0) && (document.xmlconfig.logfolder.value.length > 0) &&
		 (document.xmlconfig.systemlogin.value.length > 0) && (document.xmlconfig.systememail.value.length > 0) &&
		 (document.xmlconfig.timeout.value.length > 0) )
	{
		if (document.xmlconfig.update.checked == true)
		{
			if (document.xmlconfig.systempassword.value == document.xmlconfig.checkpassword.value)
			{
				document.xmlconfig.submit();
			}
			else
			{
				alert("Password not confirmed !");
			}
		}
		else
		{
			document.xmlconfig.submit();
		}
	}
	else
	{
		alert("You have to fill in \nbackupfile,logfolder,systemlogin,systememail,timeout\n parameters !");
	}
}
function cancel()
{
	location.href="menu.jsp";
}
//-->
</script>
<title>jChatBox System Manager</title>
</head>
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" link="#9999CC" alink="#9999CC" vlink="#9999CC">
<%@ include file="header.jsp" %>
<form method="post" action="xmlconfig.jsp" name="xmlconfig">
  <table width="100%" border="0" cellspacing="1" cellpadding="0">
    <tr>
      <td width="25%" valign="top">
        <table width="100%" border="0" cellspacing="1" cellpadding="4">
          <tr>
            <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><img src="images/reddot.gif" width="12" height="12"><br>
              <br>
              </font></td>
            <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><b><font color="#990000">Manage
              chatroom<br>
              </font></b></font>
              <table width="100%%" border="0" cellspacing="1" cellpadding="0">
                <tr>
                  <td> <font face="Verdana, Arial, Helvetica, sans-serif" size="-1">
                    <select name="chatrooms" onChange="manage(this.options[selectedIndex].value);">
                      <option selected>Select a chatroom</option>
                      <%
                      	if (ChatroomManager != null)
                      	{
                      		Vector chatrooms = ChatroomManager.getChatrooms();
                      		Chatroom chatroom = null;
                      		for (int i=0;i<chatrooms.size();i++)
                      		{
                      			chatroom = (Chatroom) chatrooms.elementAt(i);
                      			out.print("<option value=\""+chatroom.getParams().getID()+"\">"+chatroom.getParams().getName()+"</option>");
                      		}
                      	}
                      %>
                    </select>
                    </font></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><img src="images/reddot.gif" width="12" height="12"></font></td>
            <td class="menulink"><font face="Verdana, Arial, Helvetica, sans-serif" size="-1" color="#FF0000"><b><a href="open.jsp" class="menulink">Open
              a chatroom</a></b></font></td>
          </tr>
          <tr>
            <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><img src="images/reddot.gif" width="12" height="12"></font></td>
            <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><a class="menulink" href="javascript:backup()"><b>Backup
              chatrooms </b></a></font></td>
          </tr>
          <tr>
            <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><img src="images/reddot.gif" width="12" height="12"></font></td>
            <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><b><font color="#FF0000">Manage
              jChatBox</font></b></font></td>
          </tr>
          <tr>
            <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><img src="images/reddot.gif" width="12" height="12"></font></td>
            <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><a class="menulink" href="javascript:logout()"><b>Logout</b></a></font></td>
          </tr>
        </table>
      </td>
      <td width="75%" valign="top">
        <table width="98%" border="0" cellspacing="1" cellpadding="0" align="center">
          <tr>
            <td>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr bgcolor="#999999">
                  <td bgcolor="#003399">
                    <table width="100%" border="0" cellspacing="1" cellpadding="2">
                      <tr bgcolor="#FFFFFF">
                        <td nowrap>
                          <table width="100%" border="0" cellspacing="5" cellpadding="5">
                            <tr>
                              <td>
                                <div align="center"><font face="Verdana, Arial, Helvetica, sans-serif"><b><font color="#0000FF">
                                  <font face="Arial, Helvetica, sans-serif">-
                                  jChatBox SYSTEM parameters -</font></font></b></font><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"></font>
                                  <br>
                                  <font face="Verdana, Arial, Helvetica, sans-serif" size="-1" color="#FF0000"><b>
                                  <%
          							if ((SystemProcessor.getSysMessage()).equals(Conf.XMLCONFIGUPDATED)) out.print("System configuration updated. It will be loaded on next login.");
          							else if ((SystemProcessor.getSysMessage()).equals(Conf.SYSTEMERROR)) out.print("Cannot execute : System error.");
          						  %>
                                  </b></font><br>
                                  <font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><b><br>
                                  </b></font>
                                  <table width="98%" border="0" cellspacing="1" cellpadding="0">
                                    <tr bgcolor="#EEEEEE">
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><b>Backup
                                        File : </b></font></td>
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1">
                                        <input type="text" name="backupfile" value="<%= XMLConfig.BACKUPFILE %>" size="34" class="SystemBox">
                                        </font></td>
                                    </tr>
                                    <tr>
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-2" color="#333333">Backup
                                        filename of jChaBox.<br>
                                        (e.g. : /home/alice/jchatbox/backup.ser)</font></td>
                                      <td class="SystemBox"><font face="Verdana, Arial, Helvetica, sans-serif" size="-1">&nbsp;</font></td>
                                    </tr>
                                    <tr bgcolor="#EEEEEE">
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><b>Log
                                        folder : </b></font></td>
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1">
                                        <input type="text" name="logfolder" value="<%= XMLConfig.LOGFOLDER %>" size="34" class="SystemBox">
                                        </font></td>
                                    </tr>
                                    <tr>
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-2" color="#333333">Log
                                        folder of jChaBox.<br>
                                        (e.g. : /home/alice/jchatbox/logs)</font></td>
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1">&nbsp;</font></td>
                                    </tr>
                                    <tr bgcolor="#EEEEEE">
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><b>System
                                        login : </b></font></td>
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1">
                                        <input type="text" name="systemlogin" value="<%= XMLConfig.SYSTEMLOGIN %>" size="20" class="SystemBox">
                                        </font></td>
                                    </tr>
                                    <tr>
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-2" color="#333333">System
                                        login. Default is : system</font></td>
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1">&nbsp;</font></td>
                                    </tr>
                                    <tr bgcolor="#EEEEEE">
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><b>System
                                        email :</b> </font></td>
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1">
                                        <input type="text" name="systememail" value="<%= XMLConfig.SYSTEMEMAIL %>" size="30" class="SystemBox">
                                        </font></td>
                                    </tr>
                                    <tr>
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-2" color="#333333">System
                                        email.</font></td>
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1">&nbsp;</font></td>
                                    </tr>
                                    <tr bgcolor="#EEEEEE">
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><b>TimeOut
                                        :</b> </font></td>
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1">
                                        <input type="text" name="timeout" value="<%= XMLConfig.TIMEOUT %>" size="30" class="SystemBox">
                                        </font></td>
                                    </tr>
                                    <tr>
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-2" color="#333333">User's
                                        session timeout in seconds.</font></td>
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1">&nbsp;</font></td>
                                    </tr>
                                    <tr bgcolor="#EEEEEE">
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><b>CookieID
                                        :</b> </font></td>
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1">
                                        <input type="text" name="cookieid" value="<%= XMLConfig.COOKIEID %>" size="30" class="SystemBox">
                                        </font></td>
                                    </tr>
                                    <tr>
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-2" color="#333333">Session
                                        tracking ID. DO NOT CHANGE !</font></td>
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1">&nbsp;</font></td>
                                    </tr>
                                    <tr bgcolor="#EEEEEE">
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><b>License
                                        file :</b> </font></td>
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1">
                                        <input type="text" name="license" value="<%= XMLConfig.LICENSE %>" size="30" class="SystemBox">
                                        </font></td>
                                    </tr>
                                    <tr>
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-2" color="#AA0000"><b>
                                      <%
                                      	String reg = XMLConfig.readRegistration();
                                      	if ( (reg != null) && (reg.equals("freefornoncommercial")) )
                                      	{ %>
                                      		jChatBox is free for non-commercial sites.
                                      <%}
                                        else if ((reg != null) && (!reg.equals("")) )
                                        {%>
											jchatBox is registered to : <%= XMLConfig.REGISTRATION %>
                                      <%}
                                        else
                                        {%>
                                            INVALID jChatBox license file !
                                      <%}
                                      %>
                                      </b></font></td>
                                      <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1">&nbsp;</font></td>
                                    </tr>
                                  </table>
                                  <br>
                                  <table width="98%%" border="0" cellspacing="1" cellpadding="0">
                                    <tr>
                                      <td width="50%"> <font face="Verdana, Arial, Helvetica, sans-serif" size="-1">
                                        </font>
                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                          <tr>
                                            <td><font face="Verdana, Arial, Helvetica, sans-serif" size="-1">
                                              <input type="checkbox" name="update" value="yes">
                                              </font></td>
                                            <td nowrap><font face="Verdana, Arial, Helvetica, sans-serif" size="-1">
                                              <font color="#FF0000"><b>&nbsp;Update
                                              SYSTEM password ?</b></font></font></td>
                                          </tr>
                                        </table>
                                        <font face="Verdana, Arial, Helvetica, sans-serif" size="-1">
                                        </font></td>
                                      <td width="50%"><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"></font></td>
                                    </tr>
                                    <tr>
                                      <td width="50%" bgcolor="#EEEEEE"><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><b>New
                                        password :</b> </font></td>
                                      <td width="50%" bgcolor="#EEEEEE"><font face="Verdana, Arial, Helvetica, sans-serif" size="-1">
                                        <input type="password" name="systempassword" size="20" class="SystemBox">
                                        </font></td>
                                    </tr>
                                    <tr>
                                      <td width="50%" bgcolor="#EEEEEE"><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><b>Confirm
                                        password : </b></font></td>
                                      <td width="50%" bgcolor="#EEEEEE"><font face="Verdana, Arial, Helvetica, sans-serif" size="-1">
                                        <input type="password" name="checkpassword" size="20" class="SystemBox">
                                        </font></td>
                                    </tr>
                                    <tr>
                                      <td width="50%"><font face="Verdana, Arial, Helvetica, sans-serif" size="-2" color="#AA0000">Default is : password. CHANGE IT !</font></td>
                                      <td width="50%"><font face="Verdana, Arial, Helvetica, sans-serif" size="-1">&nbsp;</font></td>
                                    </tr>
                                    <tr>
                                      <td colspan="2"><font face="Verdana, Arial, Helvetica, sans-serif" size="-2" color="#333333">You
                                        have to select the checkbox above if you
                                        want to update the SYSTEM password. This
                                        password is stored as its MD5 hash value
                                        (i.e. encrypted in config file).</font></td>
                                    </tr>
                                  </table>
                                  <br>
                                  <font face="Verdana, Arial, Helvetica, sans-serif" size="-1">
                                  <input type="button" name="Save" value="Save" onClick="save()">
                                  &nbsp;
                                  <input type="button" name="Cancel" value="Cancel" onClick="cancel()">
                                  <input type="hidden" name="todo" value="updatesystem">
                                  <br>
                                  </font> </div>
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
      </td>
    </tr>
  </table>
  </form>
<%@ include file="footer.jsp" %>
</body>
</html>
