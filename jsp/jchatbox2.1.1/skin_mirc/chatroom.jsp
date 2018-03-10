<html>
<% if (dMode != Conf.BUFFEREDFRAMED) { %><meta http-equiv=Refresh content="<%= refreshValue %>;URL=<%= response.encodeURL("content.jsp?todo=refresh") %>"><%}%>
<head>
<title><%= chatroomName %></title>
<script language="JavaScript"><!--
function scrollFrame()
{
<% if (dMode == Conf.BUFFEREDFRAMED) { %>parent.dbcontent.scrollBy(0,1000);<% } else { %>parent.content.scrollBy(0,1000);<% } %>
}
function privatemsg(user)
{
 window.open('<%= response.encodeURL("privatetext.jsp?to=") %>'+escape(user)+'','PrivateMessage','resizable=yes,width=600,height=70');
}
//--></script>
</head>
<body bgcolor="#FFFFFF" leftmargin="1" topmargin="1" marginwidth="1" marginheight="1">
<center>
<table width=99% height=100% border=0 cellspacing=1 cellpadding=0>
<tr>
<td align=center valign=bottom>
	<%
       /** Display chat content. Top - Bottom */
       StringBuffer sb = new StringBuffer();
	   if (chatroom != null)
	   {
         Vector v = chatroom.getChat(cUser.getName());
         Vector filters = chatroom.getParams().getFilters();
         jChatBox.Chat.Filter.Filter filter = null;
         if (v != null)
         {
            Msg message = null;
            String messageStr = null;
            String fromStr = null;
            int i = v.size() - (chatroom.getParams().getHistory());
            if (i<0) i = 0;
            int lines = 0;
      		sb.append("<table width=100% border=0 cellspacing=0 cellpadding=0>\n");
            while ( (i<v.size()) && (lines<chatroom.getParams().getHistory()) )
            {
               message = (Msg) v.elementAt(i);
               messageStr = message.getMsg();
               fromStr = message.getFrom();
               /** Filter From and Msg fields for non-SYSTEM users */
               if (!(message.getFrom()).equals(XMLConfig.SYSTEMLOGIN))
               {
               		for (int f=0;f<filters.size();f++)
               		{
						filter = (jChatBox.Chat.Filter.Filter) filters.elementAt(f);
               			fromStr = filter.process(fromStr);
               			messageStr = filter.process(messageStr);
               		}
               }
               i++;
               lines++;
			   sb.append("<tr>\n");
               // System Administrator message.
               if ( message.getType() == Msg.SYSTEM )
               {
                  sb.append("<td valign=top><font face=Verdana size=-1 color=#FF0000>### "+message.getMsg()+"</font></td>\n");
			   }
               // Join message.
               if ( message.getType() == Msg.JOIN )
               {
                  sb.append("<td valign=top><font face=Verdana size=-1 color=#006600>*** "+messageStr+" has joined chatroom.</font></td>\n");
               }
               // Quit message.
                else if ( message.getType() == Msg.LEFT )
               {
                  sb.append("<td valign=top><font face=Verdana size=-1 color=#000066>*** "+messageStr+" has left chatroom.</font></td>\n");
               }
               // Private message.
               else if ( message.getType() == Msg.PRIVATE )
               {
                  sb.append("<td valign=top><font face=Verdana size=-1 color=#AA0000>&lt;"+fromStr+"&gt;&nbsp;"+messageStr+"</font></td>\n");
               }
               // Message for all users in the chatroom.
               else
               {
                  sb.append("<td valign=top><font face=Verdana size=-1>&lt;"+fromStr+"&gt;&nbsp;"+messageStr+"</font></td>\n");
               }
               sb.append("</tr>\n");
            }
      		sb.append("</table>\n");
         }
	 	 out.print(new String(sb));
	   }
	%>
  </td>
	<td valign=bottom align=right>
      <table border=0 cellspacing=0 cellpadding=0>
        <tr bgcolor=#000099>
          <td>
            <table width=100% border=0 cellspacing=1 cellpadding=2>
              <tr bgcolor=#000077 align=center>
                <td nowrap><font size=-2 face=Verdana color=#FFFFFF><b>Users : <%= chatroomTotalUsers %>/<%= chatroomMaxUsers %></b></font></td>
              </tr>
              <tr bgcolor=#FFFFFF>
                <td nowrap><font size=-2 face=Verdana color=#000000>
				<%
				  /** Display users list. Links to privatemsg could be enabled. Head depends on user Type.*/
				  if (chatroom != null)
				  {
					String priv = null;
					sb = new StringBuffer();
				  	Hashtable table = chatroom.getUsers();
				    Enumeration e = table.keys();
			        String name = null;
			        String username = null;
      				ChatroomUser user = null;
         			Vector filters = chatroom.getParams().getFilters();
         			jChatBox.Chat.Filter.Filter filter = null;
      				while (e.hasMoreElements())
      				{
              			name = (String) e.nextElement();
              			user = (ChatroomUser) table.get(name);
              			username = user.getName();
              			/** Filter username for non-SYSTEM users */
						if (user.getType() != User.SYSTEM)
						{
							for (int f=0;f<filters.size();f++)
							{
								filter = (jChatBox.Chat.Filter.Filter) filters.elementAt(f);
		               			username = filter.process(username);
		               		}
		                }
              			if (chatroom.getParams().getPrivateStatus()==true) priv = "<a href=\"javascript:privatemsg('"+user.getName()+"')\">"+username+"</a><br>";
						else priv = username+"<br>";
              			if (user.getType() == User.SYSTEM) priv="@"+priv;
              			else if (user.getType() == User.MODERATOR) priv="#"+priv;
              			sb.append(priv);
				  	}
      				sb.append("<br>&copy;&nbsp;<a href=\"http://www.javazoom.net\" target=\"_blank\">JavaZOOM</a><br>");
      				out.print(new String(sb));
      			  }
				%>
                </font></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <br>
    </td>
  </tr>
</table>
</center>
<script>scrollFrame();</script>
</body>
</html>