<html>
<%@ page import="jChatBox.Util.*,jChatBox.Chat.*,java.util.*,java.text.SimpleDateFormat" %>
<jsp:useBean id="UserProcessor" class="jChatBox.Service.UserProcessor" scope="application" />
<%
	jChatBox.Chat.ChatroomManager ChatroomManager = null;
	String chatroomName = "", chatroomSubject = "", chatroomDate = "";
	String jspDisplay = UserProcessor.execute(request,response,session,application);
	if (jspDisplay != null)
	{
		response.sendRedirect(response.encodeURL(jspDisplay));
	}
	else
	{
		ChatroomManager = jChatBox.Chat.ChatroomManager.getInstance();
		ChatroomUser cUser = (ChatroomUser) session.getValue("user");
		Chatroom chatroom = null;
		if (cUser != null)
		{
			int cID = cUser.getParams().getChatroom();
			try
			{
				chatroom = ChatroomManager.getChatroom(cID);
				chatroomName = chatroom.getParams().getName();
				chatroomSubject = chatroom.getParams().getSubject();
				chatroomDate = (new SimpleDateFormat("yyyyy/MM/dd HH:mm")).format(chatroom.getDate());
			} catch (ChatException ce)
			  {
			  	/** Chatroom not found */
				response.sendRedirect(response.encodeURL(Conf.JSPUSERLOGIN));
			  }
		}
	}
%>
<head>
<title><%= chatroomName %></title>
<script language="JavaScript"><!--
function ready()
{
	document.chat.msg.focus();
}
function SendMessage()
{
	document.chat.submit();
	document.chat.msg.value = "";
	document.chat.reset();
	document.chat.msg.focus();
}
function chat()
{
	document.chat.todo.value="chat";
	SendMessage();
}
//--></script>
</head>
<body bgcolor="#000000" leftmargin="1" topmargin="1" marginwidth="1" marginheight="1" onLoad="ready()" link="#FFFF66" vlink="#FFFF66" alink="#FFFF99" text="#FFFFFF" background="images/j-TVback.gif">
<form method="get" action="<%= response.encodeURL("content.jsp") %>" name="chat" target="content" OnSubmit='SendMessage();return false;'>
  <table width="98%" border="0" cellspacing="1" cellpadding="0" align="center">
    <tr>
      <td>
        <table width="100%" border="0" cellspacing="1" cellpadding="1" align="center">
          <tr>
            <td nowrap width="121"><img src="images/message.gif" width="140" height="24"></td>
            <td valign="top" nowrap width="320">
              <input type="text" name="msg" size="50" maxlength="120">
              <input type="hidden" name="to" value="ALL">
            </td>
            <td valign="top" nowrap><font face="Verdana, Arial" size="-1"><a href="javascript:chat()"><b><img src="images/send.gif" width="53" height="20" border="0" alt="Send message"></b></a></font>
              <b></b>
              <input type="hidden" name="todo" value="chat">
            </td>
          </tr>
          <tr>
            <td nowrap width="121"><font face="Verdana, Arial" size="-1"><a href="<%= response.encodeURL("content.jsp?todo=logout") %>" target="_parent"><img src="images/logout.gif" width="53" height="23" border="0" alt="Logout this chatroom"></a></font><font size="-2" face="Verdana, Arial" color="#FF0000"></font></td>
            <td align="left" nowrap width="320"><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><font size="-2" face="Arial, Helvetica, sans-serif" color="#CC9900"><b><u>Chatroom</u>
              :</b></font><b> </b></font><b><font size="-2" face="Arial, Helvetica, sans-serif" color="#FF0000"><%= chatroomName %></font></b>
              <font face="Arial, Helvetica, sans-serif" size="-1"><b><font size="-2" color="#CC9900"><u><font color="#3399FF">Topic</font></u>
              :</font><font size="-2"> </font></b></font><font size="-2" face="Arial, Helvetica, sans-serif" color="#FF0000"><font color="#99CCFF"><b><%= chatroomSubject %></b></font></font></td>
            <td align="left" nowrap><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><a href="<%= response.encodeURL("content.jsp?todo=refresh") %>" target="content"><img src="images/refresh.gif" width="55" height="20" border="0" alt="Refresh chatroom content"></a></font><font face="Verdana, Arial" size="-1"></font><font face="Verdana, Arial" size="-1"></font></td>
          </tr>
        </table>
      </td>
      <td valign="middle" align="center"><img src="images/j-TV.gif" width="115" height="55"></td>
    </tr>
  </table>
</form>
</body>
</html>