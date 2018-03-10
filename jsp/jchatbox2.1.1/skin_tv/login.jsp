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
function enter(id)
{
	document.chat.chatrooms.value=id;
	document.chat.submit();
}
//--></script>
<title>Chat Login + j-TV Skin</title>
</head>
<body bgcolor="#000000" leftmargin="1" topmargin="1" marginwidth="1" marginheight="1" onLoad="ready()" text="#FFFFFF" link="#0099FF" vlink="#00CCFF" alink="#0099FF" background="images/j-TVback.gif">
<br>
<form method="post" action="<%= response.encodeURL("login.jsp") %>" name="chat">
  <table width="96%" border="0" cellspacing="1" cellpadding="0" align="center">
    <tr>
      <td>
        <table width="100%" border="0" cellspacing="1" cellpadding="1" align="center">
          <tr>
            <td colspan="2" nowrap>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td nowrap><font face="Verdana, Arial, Helvetica, sans-serif"><font color="#FFFFFF" size="-1"><b>Enter
                    your Nickname :&nbsp;</b></font></font>
                    <input type="text" name="name" size="10" maxlength="16">
                    <font face="Verdana, Arial, Helvetica, sans-serif" size="-2" color="#FF0000">&nbsp;&nbsp;</font><font face="Verdana, Arial, Helvetica, sans-serif"><font color="#FFFFFF" size="-1"><b>AND
                    select a chatroom.</b></font></font><font face="Verdana, Arial, Helvetica, sans-serif" size="-2" color="#FF0000"><br>
                    <%
                    	if (UserLogin.getSysMessage()==Conf.CHATROOMNOTFOUND) out.print("CHATROOM NOT FOUND.");
                    	else if (UserLogin.getSysMessage()==Conf.CLOSED) out.print("CHATROOM IS CLOSED.");
                    	else if (UserLogin.getSysMessage()==Conf.NOVACANCIES) out.print("CHATROOM IS FULL.");
                    	else if (UserLogin.getSysMessage()==Conf.NAMENOTAVAILABLE) out.print("NAME NOT AVAILABLE.");
                    	else if (UserLogin.getSysMessage()==Conf.BANNED) out.print("YOU'RE BANNED.");
                    	else if (UserLogin.getSysMessage()==Conf.CHATROOMIDMISSING) out.print("CLICK ON A CHATROOM TO ENTER.");
                    	else out.print(UserLogin.getSysMessage());
                    %>
                    </font> </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td colspan="2" nowrap><br>
              <table width="100%%" border="0" cellspacing="1" cellpadding="0">
                  <%
                   	Vector chatrooms = ChatroomManager.getChatrooms();
                   	Chatroom chatroom = null;
                   	String selection = "";
                   	for (int i=0;i<chatrooms.size();i++)
                   	{
                   		chatroom = (Chatroom) chatrooms.elementAt(i);
						%>
		                <tr>

                  <td align="center"><font face="Arial, Helvetica, sans-serif"><a href="javascript:enter('<%= chatroom.getParams().getID() %>')"><img src="images/tiny3.gif" width="12" height="11" border="0" alt="Enter Chatroom !"></a></font></td>
                  <td valign="top"><font face="Arial, Helvetica, sans-serif"><a href="javascript:enter('<%= chatroom.getParams().getID() %>')"><b><%= chatroom.getParams().getName() %></b></a><i><font size="-1">
                    </font></i><font size="-1"><i><font color="#339900">(<%= chatroom.getParams().getSubject() %>)</font>
                    </i>&#149; <font color="#CC6600"><%= chatroom.getTotalUsers() %>
                    users connected. </font></font></font></td>
		                </tr>
		                <%
                   	}
                 %>
              </table>
            </td>
          </tr>
        </table>
      </td>
      <td align="right" valign="top"><img src="images/j-TV.gif" width="115" height="55">
      </td>
    </tr>
  </table>
 <input type="hidden" name="chatrooms">
</form>
<br>
<hr size="1" width="98%" noshade>
<br>
<table width="98%" border="0" cellspacing="2" cellpadding="0" align="center">
  <tr align="center">
    <td valign="top" width="40%" align="left"> <font size="-1" face="Verdana, Arial, Helvetica, sans-serif"><b><font color="#FFFFFF">jChatBox
      2.1.1</font><font color="#666666"><br>
      </font></b><font color="#339900"><b>Look and feel : j-TV</b></font><br>
      <font size="-2" color="#CC6600">This chatroom is performed by jChatBox application.
      Server side is 100% JSP (Java Server Pages). Client side could be HTML/JavaScript,
      Applet or Flash. System user can open and control many chatrooms. He can
      manage users (list, ban, kickoff), manage blacklist, generate transcripts
      and apply chatrooms parameters as max users, filters ... Chatrooms are easily
      customizable. They can be extended by designers or programmers through the
      jChatBox API. Have fun ... <br>
      </font></font><br>
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
<br>
<hr size="1" width="98%" noshade>
</body>
</html>
