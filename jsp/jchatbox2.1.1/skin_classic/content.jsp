<!-- Chunk -->
<%@ page import="jChatBox.Util.*,jChatBox.Chat.*,jChatBox.Chat.Filter.*,java.util.*,java.net.*" %>
<jsp:useBean id="UserProcessor" class="jChatBox.Service.UserProcessor" scope="application" />
<%
	jChatBox.Chat.ChatroomManager ChatroomManager = null;
	String chatroomName = "", buffering = null, todo = null;
	int chatroomMaxUsers = -1, chatroomTotalUsers = -1, refreshValue = 20, dMode = -1;
	Chatroom chatroom = null;
	ChatroomUser cUser = null;
	String jspDisplay = UserProcessor.execute(request,response,session,application);
	if (jspDisplay != null)
	{
		response.sendRedirect(response.encodeURL(jspDisplay));
	}
	else
	{
		ChatroomManager = jChatBox.Chat.ChatroomManager.getInstance();
        cUser = (ChatroomUser) session.getValue("user");
		if (cUser != null)
		{
			int cID = cUser.getParams().getChatroom();
			refreshValue = cUser.getParams().getRefresh();
			try
			{
				chatroom = ChatroomManager.getChatroom(cID);
				dMode = chatroom.getParams().getDisplayMode();
				chatroomName = chatroom.getParams().getName();
				chatroomMaxUsers = chatroom.getParams().getMaxUsers();
				chatroomTotalUsers = chatroom.getTotalUsers();
				buffering = request.getParameter("buffering");
				todo = request.getParameter("todo");
			} catch (ChatException ce)
			  {
			  	/** Chatroom not found */
				response.sendRedirect(response.encodeURL(Conf.JSPUSERLOGIN));
			  }
		}
	}
%>
<%
/**----------------------------*/
/** No action means blank page */
/**----------------------------*/
if (todo == null)
{%>
<html>
<body bgcolor="#EEEEEE"></body>
</html>
<%}
/**--------------------------------------------------------------------------------------*/
/** BUFFEREDFRAMED mode. Content of chatroom has been buffered in a JavaScript variable. */
/**--------------------------------------------------------------------------------------*/
else if ( (dMode == Conf.BUFFEREDFRAMED) && (buffering == null) )
{%>
<html>
<meta http-equiv=Refresh content="<%= refreshValue %>;URL=<%= response.encodeURL("content.jsp?todo=refresh") %>">
<head>
<title><%= chatroomName %></title>
<script language="JavaScript"><!--
	var thechat = '<%= URLEncoder.encode((String)session.getValue("bufferedChat")).replace('+',' ') %>';
	function execute()
	{
		if (typeof(self.parent.dbcontent) != "undefined")
		{
			self.parent.dbcontent.document.open("text/html");
			self.parent.dbcontent.document.writeln(unescape(thechat));
			self.parent.dbcontent.document.write();
			self.parent.dbcontent.document.close();
		}
	}
//--></script>
</head>
<body bgcolor="#EEEEEE" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="execute()">
</body>
</html>
<%}
/**----------------------*/
/** Content of chatroom. */
/**----------------------*/
else
{%><%@ include file="chatroom.jsp" %><%}%>