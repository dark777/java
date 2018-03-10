<html>
<%@ page import="jChatBox.Util.*,jChatBox.Chat.*,java.util.*,java.text.SimpleDateFormat" %>
<%@ include file="../configureme.jsp" %>
<jsp:useBean id="UserLogin" class="jChatBox.Service.UserLogin" scope="page" />
<%!
	// Overides jspDestroy method to backup jChatBox.
	public void jspDestroy()
	{
		jChatBox.Util.Debug.log(0,"jChatBox skinDestroy() ...","");
		jChatBox.Chat.ChatroomManager chatroomManager = jChatBox.Chat.ChatroomManager.getInstance();
		chatroomManager.destroy();
		jChatBox.Util.Debug.destroy();
	}
%>
<%
	jChatBox.Chat.ChatroomManager ChatroomManager = jChatBox.Chat.ChatroomManager.getInstance();
	String chatroomTotalUsers = "", chatroomMaxUsers = "", chatroomName = "", chatroomSubject = "", chatroomDate = "";
	String chatroomID = request.getParameter("id");
	int ID = -1;
	if ( (chatroomID != null) && (!chatroomID.equals("")) )
	{
		try
		{
			ID = Integer.parseInt(chatroomID);
			Chatroom chatroom = ChatroomManager.getChatroom(ID);
			chatroomName = chatroom.getParams().getName();
			chatroomSubject = chatroom.getParams().getSubject();
			chatroomMaxUsers = ""+chatroom.getParams().getMaxUsers();
			chatroomTotalUsers = ""+chatroom.getTotalUsers();
			chatroomDate = (new SimpleDateFormat("yyyyy/MM/dd HH:mm")).format(chatroom.getDate());
		} catch (Exception e)
		  {}
	}
	else
	{
		Vector vChat = ChatroomManager.getChatrooms();
		if (vChat.size() > 0)
		{
			Chatroom chatroom = (Chatroom) vChat.elementAt(0);
			ID = chatroom.getParams().getID();
			chatroomName = chatroom.getParams().getName();
			chatroomSubject = chatroom.getParams().getSubject();
			chatroomMaxUsers = ""+chatroom.getParams().getMaxUsers();
			chatroomTotalUsers = ""+chatroom.getTotalUsers();
			chatroomDate = (new SimpleDateFormat("yyyyy/MM/dd HH:mm")).format(chatroom.getDate());
		}
	}
	String jspDisplay = UserLogin.doLogin(request, session);
	if (jspDisplay != null)
	{
		//response.sendRedirect(response.encodeURL(jspDisplay));
		%><jsp:forward page="<%= response.encodeURL(jspDisplay) %>" /><%
	}
%>
<head>
<script language="Javascript"><!--
function ready()
{
	if (typeof(self.parent.content) != "undefined")
	{
		self.parent.location.href="login.jsp";
	}
	document.chat.name.focus();
}
function enter()
{
	document.chat.submit();
}
function info(id)
{
	if (id.length > 0)
	{

		location.href="<%= response.encodeURL("login.jsp?rand="+System.currentTimeMillis()) %>&id="+id;
	}
}
//--></script>
<title>Chat Login</title>
</head>
<body bgcolor="#EEEEEE" leftmargin="1" topmargin="1" marginwidth="1" marginheight="1" onLoad="ready()">
<form method="post" action="<%= response.encodeURL("login.jsp") %>" name="chat">
  <table width="98%" border="0" cellspacing="1" cellpadding="0" align="center">
    <tr>
      <td>
        <table width="100%" border="0" cellspacing="1" cellpadding="1" align="center">
          <tr>
            <td colspan="2" nowrap>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td nowrap><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><b><font color="#000099">NickName
                    :&nbsp;</font></b></font>
                    <input type="text" name="name" size="10" maxlength="16">
                    <font face="Verdana, Arial, Helvetica, sans-serif" size="-2" color="#FF0000">&nbsp;&nbsp;<br>
                    <%
                    	if (UserLogin.getSysMessage()==Conf.CHATROOMNOTFOUND) out.print("CHATROOM NOT FOUND.");
                    	else if (UserLogin.getSysMessage()==Conf.CLOSED) out.print("CHATROOM IS CLOSED.");
                    	else if (UserLogin.getSysMessage()==Conf.NOVACANCIES) out.print("CHATROOM IS FULL.");
                    	else if (UserLogin.getSysMessage()==Conf.NAMENOTAVAILABLE) out.print("NAME NOT AVAILABLE.");
                    	else if (UserLogin.getSysMessage()==Conf.BANNED) out.print("YOU'RE BANNED.");
                    %>
                    </font> </td>
                </tr>
              </table>

            </td>
          </tr>
          <tr>
            <td colspan="2" nowrap><b><a href="javascript:enter()"><font face="Verdana, Arial, Helvetica, sans-serif" size="-1">Enter</font></a></b>
              <b><font face="Verdana, Arial, Helvetica, sans-serif" size="-1">chatroom</font></b>
              <select name="chatrooms" onChange="info(this.options[selectedIndex].value);">
                <option value="none">Select a chatroom</option>
                 <%
                   	Vector chatrooms = ChatroomManager.getChatrooms();
                   	Chatroom chatroom = null;
                   	String selection = "";
                   	for (int i=0;i<chatrooms.size();i++)
                   	{
                   		chatroom = (Chatroom) chatrooms.elementAt(i);
                   		if (ID == chatroom.getParams().getID()) selection = " selected";
                   		else selection="";
                   		out.print("<option value=\""+chatroom.getParams().getID()+"\""+selection+">"+chatroom.getParams().getName()+"</option>");
                   	}
                 %>
              </select>
              </td>
          </tr>
        </table>
      </td>
      <td align="right">
        <table border="0" cellspacing="0" cellpadding="0">
          <tr bgcolor="#999999">
            <td bgcolor="#AAAAAA">
              <table width="100%" border="0" cellspacing="1" cellpadding="2">
                <tr bgcolor="#FFFFFF">
                  <td nowrap><font size="-2" face="Verdana, Arial, Helvetica, sans-serif">ChatRoom
                    : <font color="#FF0000"><b><%= chatroomName %></b></font> (<%= chatroomTotalUsers %>/<%= chatroomMaxUsers %>)<br>
                    Opened :<b> <%= chatroomDate %><br>
                    </b><font size="-2" face="Verdana, Arial, Helvetica, sans-serif">Subject
                    :</font><b><font size="-2" face="Verdana, Arial, Helvetica, sans-serif">
                    <font color="#000066"><b><%= chatroomSubject %></b></font></font></b></font></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</form>
<p>&nbsp;</p>
<table width="100%" border="0" cellspacing="2" cellpadding="0">
  <tr align="center">
    <td valign="top" width="40%">
      <table width="70%" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#999999">
          <td bgcolor="#AAAAAA">
            <table width="100%" border="0" cellspacing="1" cellpadding="2">
              <tr bgcolor="#FFFFFF">
                <td bgcolor="#FFFFFF">
                  <p><font size="-1" face="Verdana, Arial, Helvetica, sans-serif"><b><font color="#FF0000">jChatBox
                    2.1.1</font><font color="#666666"><br>
                    </font>Look and feel : Classic</b><br>
                    <font size="-2" color="#777777">This chatroom is performed
                    by jChatBox application. Server side is 100% JSP (Java Server
                    Pages). Client side could be HTML/JavaScript, Applet or Flash.<br>
                    System user can open and control many chatrooms. He can manage
                    users (list, ban, kickoff), manage blacklist, generate transcripts
                    and apply chatrooms parameters as max users, filters ...<br>
                    Chatrooms are easily customizable. They can be extended by
                    designers or programmers through the jChatBox API. Have fun
                    ... </font><font size="-2" color="#999999"><br>
                    </font></font></p>
                  <table width="100%%" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td><font size="-1" face="Verdana, Arial, Helvetica, sans-serif"><font size="-2" color="#999999"><a href="http://www.javazoom.net/jzservlets/jchatbox/jchatbox.html" target="_blank" style="text-decoration:none">jChatBox
                        Homepage </a></font></font></td>
                      <td align="right"><font size="-1" face="Verdana, Arial, Helvetica, sans-serif"><font size="-1" face="Verdana, Arial, Helvetica, sans-serif"><font size="-2" color="#999999"><a href="http://www.javazoom.net" target="_blank" style="text-decoration:none">&copy;
                        JavaZOOM - 2001</a></font></font></font></td>
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
</body>
</html>
