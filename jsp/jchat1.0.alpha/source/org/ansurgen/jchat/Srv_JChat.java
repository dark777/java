import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.ansurgen.jchat.Channel;
import org.ansurgen.jchat.User;
import org.ansurgen.jchat.Message;
import org.ansurgen.jchat.PrivateMessage;

/**
<pre>
Srv_JChat.java

See the README.txt file that come with this distribution for
installation instructions

@author ttimoteo@ansurgen.org
@version 1.0 09-NOV-1998
@see org.ansurgen.jchat.Channel
@see org.ansurgen.jchat.User
@see org.ansurgen.jchat.Message
@see org.ansurgen.jchat.PrivateMessage
</pre>
*/


public class Srv_JChat extends HttpServlet {

   private final boolean ALLOW_HTML = false;
   private final int MAX_USERS_PER_CHANNEL        = 15;
   private final int MAX_MESSAGES_PER_CHANNEL     = 20;
   private final int MAX_PVT_MESSAGES_PER_CHANNEL = 10;
   private final int MAX_LATENT_TIME              = 5;//...in minutes

   private final String DEFAULT_URL = "http://www.ansurgen.org/servlets/Srv_JChat";
   private final String ABOUT_URL   = "http://www.ansurgen.org/board/ttimoteo/jchat/about_jchat.html";

   private String DEFAULT_LOGIN_MESSAGE  = "<b>HAS JOINED"; //username will be prepended...
   private String DEFAULT_LOGOUT_MESSAGE = "<b>HAS LEFT"; //username will be prepended...

   private int user_count;
   private int message_count;

   private Date date;

   //to add a channel, add it here
   private String[] channels_array = {"ANSURGEN","Java","Perl","ColdFusion","Merlot","Pacifico","Judo","Paratrooper","BigLlama","FunkBucket","BBShelly_1","BBShelly_2"};

   private Hashtable channels_hash;


   public void init(ServletConfig config) throws ServletException{
      user_count    = 0;
      message_count = 0;
      channels_hash = new Hashtable();
      date = new Date();

      for (int i=0; i < channels_array.length; i++) {
         Channel channel = new Channel(channels_array[i],
                                       MAX_MESSAGES_PER_CHANNEL,
                                       MAX_USERS_PER_CHANNEL,
                                       MAX_PVT_MESSAGES_PER_CHANNEL,
                                       MAX_LATENT_TIME);
         channels_hash.put(channels_array[i], channel);
      }
   }//init()


   public void doGet(HttpServletRequest req,
                     HttpServletResponse res)
                     throws ServletException, IOException{

      res.setContentType("text/html");

      ServletOutputStream out = res.getOutputStream();

      out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">");
      out.println("<HTML>");
      out.println("<HEAD>");
      out.println("<TITLE>JChat Login</TITLE>");

      out.println("<script language=Javascript>");
      out.println("  function validate(){");
      out.println("     if(validateUserName() && validateChannel() && validateEmail()){");
      out.println("        return true;");
      out.println("     }");
      out.println("     return false;");
      out.println("  }");

      out.println("  function validateUserName(){");
      out.println("     user = document.jchat.str_user_name.value;");
      out.println("     if(user.length == 0){");
      out.println("        alert('You must specify a UserName!');");
      out.println("        document.jchat.str_user_name.focus();");
      out.println("        return false;");
      out.println("     }else if(user.indexOf(\" \") > 0){");
      out.println("        alert('UserName cannot contain white space!');");
      out.println("        document.jchat.str_user_name.focus();");
      out.println("        return false;");
      out.println("     }");
      out.println("     return true;");
      out.println("  }");

      out.println("  function validateEmail(){");
      out.println("     email = document.jchat.str_email.value;");
      out.println("     if(email.length > 0){");
      out.println("        at  = email.indexOf(\"@\");");
      out.println("        dot = email.lastIndexOf(\".\");");
      out.println("        n   = email.length;");
      out.println("        if(at < 1 || at >= dot || (dot+2) >= n){");
      out.println("           alert('Invalid email!  Use a valid address or leave it blank.');");
      out.println("           document.jchat.str_email.focus();");
      out.println("           return false;");
      out.println("        }");
      out.println("     }");
      out.println("     return true;");
      out.println("  }");

      out.println("  function validateChannel(){");
      out.println("     var n = document.jchat.str_channel.length;");
      out.println("     for (i=0; i<n; i++) {");
      out.println("        if (document.jchat.str_channel.options[i].selected) {");
      out.println("           if (document.jchat.str_channel.options[i].value == \"FULL\") {");
      out.println("              alert('Selected channel is FULL! Try again later');");
      out.println("              return false;");
      out.println("           }");
      out.println("        }");
      out.println("     }");
      out.println("     return true;");
      out.println("  }");

      out.println("</script>");

      out.println("</HEAD>");
      out.println("<BODY bgcolor=white>");
      out.println("<form name=\"jchat\" onSubmit=\"return validate()\" method=post action=\"Srv_JChat\">");
      out.println("<table border=0 cellspacing=0 cellpadding=0 width=100% height=100%>");
      out.println("<tr>");
      out.println("<td align=center valign=middle>");

      out.println("<img src=\"http://www.ansurgen.org/board/ttimoteo/images/header.gif\" border=0>");

      out.println("<table border=0 cellspacing=0 cellpadding=3 width=300 bgcolor=brown>");
      out.println("<tr>");
      out.println("<th><font face=\"tahoma,verdana,arial\" size=-1 color=white>JChat Login</th>");
      out.println("</tr>");
      out.println("<tr><td align=center valign=middle><table border=0 cellspacing=0 cellpadding=3 bgcolor=silver width=100%>");
      out.println("<tr>");
      out.println("<td align=right><font face=\"tahoma,verdana,arial\" size=-1><b>UserName</td>");
      out.println("<td><input type=text name=str_user_name size=20 maxlength=20 onChange=\"return validateUserName()\"></td>");
      out.println("</tr>");
      out.println("<tr>");
      out.println("<td align=right><font face=\"tahoma,verdana,arial\" size=-1><b>Email</td>");
      out.println("<td><input type=text name=str_email size=20 maxlength=30 onChange=\"return validateEmail()\"></td>");
      out.println("</tr>");
      out.println("<tr>");
      out.println("<td align=right><font face=\"tahoma,verdana,arial\" size=-1><b>Channel</td>");
      out.println("<td><select name=str_channel onChange=\"return validateChannel()\">");

      Enumeration enum = channels_hash.elements();

      int open_channels = 0;

      while (enum.hasMoreElements()) {
         Channel channel = (Channel)enum.nextElement();
         if (!channel.hasMaxUsers()) {
            out.println("<option value=\""+channel.getName()+"\">" + channel.getName() + " [" + channel.size() + " users]");
            open_channels++;
         }else{
            out.println("<option value=\"FULL\">" + channel.getName() + " [FULL]");
         }
      }
      
      if (open_channels == 0) {
         out.println("<option value=\"FULL\"> ALL CHANNELS FULL");
      }

      out.println("</select>");
      out.println("</td>");
      out.println("</tr>");
      out.println("<tr>");
      out.println("<td align=center colspan=2><input type=submit name=\"btn_login\" value=\"   Login   \"></td>");
      out.println("</tr>");
      out.println("<tr>");
      out.println("<td colspan=2><font face=\"tahoma,verdana,arial\" size=-2>");
      out.println(getSettings());
      out.println("</td>");
      out.println("</tr></table></td>");
      out.println("</tr>");
      out.println("</table>");
      out.println("</td>");
      out.println("</tr>");
      out.println("</table>");
      out.println("</BODY>");
      out.println("</HTML>");

   }

   public String getSettings(){
      String info = "<a href=\""+ABOUT_URL+"\" target=\"_blank\">@about JChat</a><br>"
                     +"@author: <a href=\"mailto:ttimoteo@ansurgen.org\">ttimoteo@ansurgen.org</a><br>"
                     +"@version: 1.0[alpha] 07NOV1998<br>"
                     +"@servlet_started: "+date+"<br>"
                     +"@max_users_per_channel_setting: " + MAX_USERS_PER_CHANNEL + "<br>"
                     +"@max_messages_per_channel_setting: " + MAX_MESSAGES_PER_CHANNEL + "<br>"
                     +"@max_private_messages_per_channel_setting: " + MAX_PVT_MESSAGES_PER_CHANNEL + "<br>"
                     +"@max_latent_time: " + MAX_LATENT_TIME + "<br>"
                     +"@allow_html: " + ALLOW_HTML;
      return info;

   }



   public void doPost(HttpServletRequest req,
                     HttpServletResponse res)
                     throws ServletException, IOException{

      res.setContentType("text/html");

      ServletOutputStream out = res.getOutputStream();
      
      HttpSession session = req.getSession(true);

      //parameters...
      String btn_login        = req.getParameter("btn_login");
      String btn_logout       = req.getParameter("btn_logout");
      String btn_post         = req.getParameter("btn_post");
      String str_user_name    = req.getParameter("str_user_name");
      String str_email        = req.getParameter("str_email");
      String str_channel      = req.getParameter("str_channel");
      String str_message      = req.getParameter("str_message");
      String str_recipient_id = req.getParameter("str_recipient_id");
      

      //System.out.println("+@params:");
      //System.out.println("  +btn_login: " + btn_login);
      //System.out.println("  +btn_logout: " + btn_logout);
      //System.out.println("  +btn_post: " + btn_post);
      //System.out.println("  +str_user_name: " + str_user_name);
      //System.out.println("  +str_email: " + str_email);
      //System.out.println("  +str_channel: " + str_channel);
      //System.out.println("  +str_message: " + str_message);
      //System.out.println("  +str_recipient_id: " + str_recipient_id);
      

      User user = null;
      
      if (btn_login != null && str_user_name != null && !str_user_name.equals("")) {
         
         user = (User)session.getValue(str_user_name+":"+str_channel);
         if (user != null) {
            errorPage(out, "The user_name <i><b>" + str_user_name + "</b></i> is already in use.  Pick another name and try again.");
            return;
         }

         user_count++;
         user = new User(user_count,
                         str_user_name,
                         str_email,
                         str_channel,
                         MAX_LATENT_TIME);
         
         session.putValue(str_user_name+":"+str_channel, user);
         
         try{
            login(user);
         }catch(Exception e){
            e.printStackTrace();
            errorPage(out, "Srv_JChat.doPost() -> error during login operation");
            return;
         }
         
      }else{
         user = (User)session.getValue(str_user_name+":"+str_channel);
         if (!user.refresh()) {
            session.removeValue(str_user_name+":"+str_channel);
            errorPage(out,"Session expired... you must login again");
            return;
         }
      }

      if (user == null) {
         errorPage(out,"Could not find User object in session [Srv_JChat.doPost()] - Your session may have expired (Max Allowable Latent Time: "+MAX_LATENT_TIME+")");
         return;
      }

      if (btn_logout != null) {
         try{
            logout(user);
            session.removeValue(str_user_name+":"+str_channel);
            doGet(req, res);
            return;
         }catch(Exception e){
            e.printStackTrace();
            errorPage(out, "Srv_JChat.doPost() -> error during logout operation");
            return;
         }
      }

      try{
         Channel channel = getChannel(user.getChannelName());
         if (btn_post != null && (str_message != null && !str_message.equals("")) ||
              (str_message != null && !str_message.equals(""))) {
            if (!ALLOW_HTML) {
               str_message = replaceHtml(str_message);
            }
            
            if (!str_recipient_id.equals("ALL USERS")) {
               User p_user = channel.getUser(Integer.parseInt(str_recipient_id));
               postPrivateMessage(channel, new PrivateMessage(user, str_message, p_user));
            }else{
               postMessage(channel, new Message(user, str_message));
            }
         }
      }catch(Exception e){
         e.printStackTrace();
         errorPage(out, "Srv_JChat.doPost() -> error getting channel::posting message");
         return;
      }

      try{
         showHeader(out, user);

         showMessages(out, user);

         showPrivateMessages(out, user);

         showFooter(out, user);
      }catch(Exception e){
         e.printStackTrace();
         errorPage(out, "Srv_JChat.doPost() -> " + e.getMessage());
         return;
      }
      
   }//doPost()


   public String replaceHtml(String str_message){
      if (str_message.indexOf("<") > 0) {
         str_message = str_message.replace('<', '#');
      }

      if (str_message.indexOf(">") > 0) {
         str_message = str_message.replace('>', '#');
      }
      
      return str_message;
   }


   public void showHeader(ServletOutputStream out,
                           User user)throws Exception{
      try{
         out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">");
         out.println("<HTML>");
         out.println("<HEAD>");
         out.println("<TITLE>JChat: " + user.getChannelName() + "</TITLE>");

         out.println("<script language=Javascript>");
         out.println("  function setFocus(){");
         out.println("     document.jchat.str_message.focus();");
         out.println("     return true;");
         out.println("  }");
         out.println("</script>");

         out.println("</HEAD>");

         out.println("<BODY bgcolor=white onload=\"return setFocus()\"><center>");
         out.println("<form name=\"jchat\" method=post action=\"Srv_JChat\">");
         out.println("<input type=hidden name=\"str_user_name\" value=\"" + user.getName() + "\">");
         out.println("<input type=hidden name=\"str_channel\" value=\"" + user.getChannelName() + "\">");
         out.println("<nobr><img src=\"http://www.ansurgen.org/board/ttimoteo/images/jchat_top.gif\" border=0><img src=\"http://www.ansurgen.org/board/ttimoteo/images/alpha.gif\" border=0></nobr>");
         out.println("<font face=\"tahoma,verdana,arial\" size=-1><br>");
         out.println("Be Sure To Logout When You Are Finished!");
      }catch(Exception e){
         throw new Exception(e.getMessage());
      }
   }

   public void showFooter(ServletOutputStream out, User aUser)
                           throws Exception{
      try{
         Channel channel = getChannel(aUser.getChannelName());
         Vector users    = channel.getUsers();
         
         out.println("<P><table border=1 cellpadding=3 cellspacing=0 width=550>");
         out.println("<tr bgcolor=brown>");
         out.println("<th colspan=2><font color=white face=\"tahoma,verdana,arial\" size=-1>");
         out.println("Control Panel: "+aUser.getName()+"@JChat."+channel.getName()+"</th>");
         out.println("</tr>");

         out.println("<tr bgcolor=silver>");
         out.println("<th width=450><font face=\"tahoma,verdana,arial\" size=-1>");
         out.println("Message</th>");
         out.println("<th width=100><font face=\"tahoma,verdana,arial\" size=-1>");
         out.println("Send To</th>");
         out.println("</tr>");

         out.println("<tr>");
         out.println("<td nowrap>");
         out.println("<nobr><input type=text name=str_message size=45 maxlength=255> <input type=submit name=btn_post value=\"Send\">");
         out.println("</td>");
         out.println("<td align=center>");

         showUsers(out, users);

         out.println("</td>");
         out.println("</tr>");
         out.println("</table><P>");
         out.println("<input type=submit name=\"btn_logout\" value=\"  Logout  \">");

         out.println("<br><font face=\"tahoma,verdana,arial\" size=-2>");
         
         out.println("</center>");
         out.println(getSettings());
         
      }catch(Exception e){
         throw new Exception(e.getMessage());
      }
   }

   public void showUsers(ServletOutputStream out, Vector users){
      
      try{
         out.println("<select name=str_recipient_id>");

         Enumeration enum = users.elements();

         out.println("<option selected>ALL USERS");

         while (enum.hasMoreElements()) {
            User user = (User)enum.nextElement();
            out.println("<option value=\"" + user.getId() + "\">" + user.getName());
         }

         out.println("</select>");

      }catch(Exception e){
         e.printStackTrace();
      }
   }//showUsers()


   public Channel getChannel(String channel_name)throws Exception{
      Channel channel = (Channel)channels_hash.get(channel_name);
      if (channel == null) {
         throw new Exception("The following channel does not exist: " + channel_name);
      }
      return channel;
   }//getChannel()


   public synchronized void showMessages(ServletOutputStream out,
                                         User user)throws Exception{
      try{
         out.println("<br><table border=1 cellpadding=3 cellspacing=0 width=550>");
         out.println("<tr bgcolor=brown>");
         out.println("<th colspan=2><font color=white face=\"tahoma,verdana,arial\" size=-1>"
                     + "Channel: " + user.getChannelName() + "</th>");
         out.println("</tr>");
         out.println("<tr bgcolor=silver>");
         out.println("<th width=100><font face=\"tahoma,verdana,arial\" size=-1>"
                     + "User</th>");
         out.println("<th width=450><font face=\"tahoma,verdana,arial\" size=-1>"
                     + "Message</th>");
         out.println("</tr>");

         Channel channel = getChannel(user.getChannelName());
         Vector messages = channel.getMessages();

         Enumeration enum = messages.elements();

         int i = 0;
         while (enum.hasMoreElements()) {
            Message msg = (Message)enum.nextElement();
            if ((i % 2) == 0) {//even number
               out.println("<tr bgcolor=ffffca>");
            }else{
               out.println("<tr>");
            }
            out.println("<td align=right><font face=\"tahoma,verdana,arial\" size=-1>");

               if (msg.getSenderEmail() != null) {
                  out.println("<a href=\"mailto:" + msg.getSenderEmail() + "\">");
               }

            out.println(msg.getSenderName() + "</a></td>"
                        + "<td align=left>"
                        + "<font face=\"tahoma,verdana,arial\" size=-1>"
                        + msg.getContent()
                        + "</td>");
            out.println("</tr>");
            i++;
         }
         out.println("</table>");
      }catch(Exception e){
         throw new Exception(e.getMessage());
      }
   }//showMessages()

   public synchronized void showPrivateMessages(ServletOutputStream out,
                                         User aUser)throws Exception{
      Channel channel   = null;
      Vector p_messages = null;
      try{
         channel   = getChannel(aUser.getChannelName());
         p_messages = channel.getPrivateMessages(aUser);
      }catch(Exception e){
         throw new Exception("showPrivateMessages() -> get channel|p_messages");
      }

      if (p_messages == null || p_messages.size() == 0) {
         return;
      }
      

      try{
         out.println("<P><table border=1 cellpadding=3 cellspacing=0 width=550>");
         out.println("<tr bgcolor=brown>");
         out.println("<th colspan=2><font color=white face=\"tahoma,verdana,arial\" size=-1>"
                     + "Private Messages</th>");
         out.println("</tr>");
         out.println("<tr bgcolor=silver>");
         out.println("<th width=100><font face=\"tahoma,verdana,arial\" size=-1>"
                     + "User</th>");
         out.println("<th width=450><font face=\"tahoma,verdana,arial\" size=-1>"
                     + "Message</th>");
         out.println("</tr>");

         int i = 0;

         Enumeration enum = p_messages.elements();
         
         while (enum.hasMoreElements()) {
            PrivateMessage msg = (PrivateMessage)enum.nextElement();
            out.println("<tr>");
            out.println("<td align=right><font face=\"tahoma,verdana,arial\" size=-2>");

            if (msg.getSenderEmail() != null) {
               out.println("<a href=\"mailto:" + msg.getSenderEmail() + "\">");
            }

            out.println(msg.getSenderName() + "</a> -&gt; ");

            if (msg.getRecipientEmail() != null) {
               out.println("<a href=\"mailto:" + msg.getRecipientEmail() + "\">");
            }

            out.println(msg.getRecipientName() + "</a></td>"
                        + "<td align=left>"
                        + "<font face=\"tahoma,verdana,arial\" size=-2>"
                        + msg.getContent()
                        + "</td>");
            out.println("</tr>");
         }
         out.println("</table>");
      }catch(Exception e){
         throw new Exception("Srv_JChat.showPrivateMessages() -> " + e.getMessage());
      }
   }//showPrivateMessages()

   
   public synchronized void login(User user)throws Exception{
      Channel channel = null;
      try{
         channel = getChannel(user.getChannelName());
         channel.addUser(user);
      }catch(Exception e){
         throw new Exception(e.getMessage());
      }
      postMessage(channel, new Message(user, DEFAULT_LOGIN_MESSAGE));
   }

   public synchronized void logout(User user)throws Exception{
      Channel channel = null;
      try{
         channel = getChannel(user.getChannelName());
         channel.removeUser(user);
      }catch(Exception e){
         throw new Exception(e.getMessage());
      }
      postMessage(channel, new Message(user, DEFAULT_LOGOUT_MESSAGE));
   }


   public synchronized void postMessage(Channel aChannel, Message aMessage){
      aChannel.addMessage(aMessage);
   }

   public synchronized void postPrivateMessage(Channel aChannel, PrivateMessage aMessage){
      aChannel.addPrivateMessage(aMessage);
   }


   public void errorPage(ServletOutputStream out, String message){
      try{
         out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">");
         out.println("<HTML>");
         out.println("<HEAD>");
         out.println("<TITLE>Error</TITLE>");
         out.println("</HEAD><BODY bgcolor=white><font face=\"tahoma,verdana,arial\" size=-1><blockquote>");
         out.println("<h2>JChat Error</h2>");
         out.println("The following error occurred while processing your request:<br>");
         out.println("<b>Exception:</b> " + message);
         out.println("<P>Try <a href=\""+DEFAULT_URL+"\">Logging In Again</a>.");
      }catch(Exception e){
         e.printStackTrace();
      }
   }



}



