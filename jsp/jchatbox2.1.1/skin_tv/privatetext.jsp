<html>
<%@ page import="jChatBox.Util.*,jChatBox.Chat.*,jChatBox.Chat.Filter.*,java.util.*,java.text.SimpleDateFormat" %>
<jsp:useBean id="UserProcessor" class="jChatBox.Service.UserProcessor" scope="application" />
<%
	jChatBox.Chat.ChatroomManager ChatroomManager = null;
	String chatroomName = "", chatroomSubject = "", chatroomDate = "", to = "all";
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
				to = request.getParameter("to");
				if (to==null) to = "ALL";
       			Vector filters = chatroom.getParams().getFilters();
       			jChatBox.Chat.Filter.Filter filter = null;
       			/** Filter username for non-SYSTEM users */
               	if (!(to).equals(XMLConfig.SYSTEMLOGIN))
				{
					for (int f=0;f<filters.size();f++)
					{
						filter = (jChatBox.Chat.Filter.Filter) filters.elementAt(f);
               			to = filter.process(to);
               		}
                }

			} catch (ChatException ce)
			  {
			  	/** Chatroom not found */
				response.sendRedirect(response.encodeURL(Conf.JSPUSERLOGIN));
			  }
		}
	}
%>
<head>
<title>Private Message</title>
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
<body leftmargin="1" topmargin="1" marginwidth="0" marginheight="0" onLoad="ready()" link="#FFFF66" vlink="#FFFF33" alink="#FFFF66" background="images/j-TVback.gif" bgcolor="#000000" text="#FFFFFF">
<form method="get" action="<%= response.encodeURL("content.jsp") %>" name="chat" target="content" OnSubmit='SendMessage();return false;'>
  <table width="98%" border="0" cellspacing="1" cellpadding="0" vspace="0" hspace="0">
    <tr>
      <td>
        <table width="98%" border="0" cellspacing="1" cellpadding="1" align="center">
          <tr>
            <td width="121" nowrap><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><b>
              </b></font><font face="Verdana, Arial" size="-1"><b><img src="images/message.gif" width="140" height="24"></b></font>
            </td>
            <td nowrap>
              <input type="text" name="msg" size="41" maxlength="120">
              <input type="hidden" name="to" value="<%= to %>">
            </td>
          </tr>
          <tr>
            <td nowrap width="121"><font face="Verdana, Arial, Helvetica, sans-serif" size="-1"><font size="-2" face="Arial, Helvetica, sans-serif" color="#CC9900"><b><u>Chatroom</u>
              :</b></font><b> </b></font><b><font size="-2" face="Arial, Helvetica, sans-serif" color="#FF0000"><%= chatroomName %></font></b><font face="Arial, Helvetica, sans-serif" size="-1"><b><font size="-2" color="#CC9900"><u></u></font></b></font></td>
            <td align="left" nowrap><font face="Verdana, Arial" size="-1"><a href="javascript:chat()"><b>Send</b></a></font>
              <font face="Verdana, Arial" size="-1"><b>to </b><font color="#FFFF00"><b><%= to %></b></font><b>
              only</b></font>
              <input type="hidden" name="todo" value="chat">
            </td>
          </tr>
        </table>
      </td>
      <td valign="middle">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><img src="images/j-TV.gif" width="115" height="55">
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</form>
<div align="center"><i>Messages sent from this window are private.</i> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font face="Verdana, Arial" size="-1">&nbsp;<a href="javascript:window.close();">close
  this window</a></font></div>
</body>
</html>