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
<body bgcolor="#EEEEEE" leftmargin="1" topmargin="1" marginwidth="1" marginheight="1" onLoad="ready()" link="#0000FF" vlink="#0000CC" alink="#0000FF">
<form method="get" action="<%= response.encodeURL("content.jsp") %>" name="chat" target="content" OnSubmit='SendMessage();return false;'>
  <table width="98%" border="0" cellspacing="1" cellpadding="0">
    <tr>
      <td>
        <table width="98%" border="0" cellspacing="1" cellpadding="1" align="center">
          <tr>
            <td colspan="2" nowrap><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><b>
              </b></font><font face="Verdana, Arial" size="-1"><b><font color="#000099">Message
              :</font></b></font>
              <input type="text" name="msg" size="50" maxlength="120">
              <input type="hidden" name="to" value="ALL">
            </td>
          </tr>
          <tr>
            <td nowrap><font face="Verdana, Arial" size="-1"><a href="javascript:chat()"><b>Send</b></a></font>
              <b><font face="Verdana, Arial" size="-1">to All Users</font></b>
              <input type="hidden" name="todo" value="chat">
            </td>
            <td align="right" nowrap><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><a href="<%= response.encodeURL("content.jsp?todo=refresh") %>" target="content">refresh</a></font>
              <font face="Verdana, Arial" size="-1">&nbsp;<a href="<%= response.encodeURL("content.jsp?todo=logout") %>" target="_parent">logout</a></font>&nbsp;
            </td>
          </tr>
        </table>
      </td>
      <td valign="middle">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr bgcolor="#AAAAAA">
            <td bgcolor="#AAAAAA">
              <table width="100%" border="0" cellspacing="1" cellpadding="2">
                <tr bgcolor="#FFFFFF">
                  <td nowrap><font size="-2" face="Verdana, Arial" color="#FF0000"><b><%= chatroomName %></b>
                    <br>
                    <font color="#660000"><%= chatroomDate%></font></font></td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td nowrap><font size="-2" face="Verdana, Arial" color="#000066"><u>Subject</u>
                    :<br>
                    <font color="#0000FF"><b><%= chatroomSubject %></b></font></font></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</form>
</body>
</html>