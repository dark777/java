/*************
*   WebForum
*   (c) 2000 Daniel Lemire
*   Daniel.Lemire@Videotron.ca, http://www.ondelette.com/
*
*
*   Ce programme est libre, vous pouvez le redistribuer et/ou le modifier selon les termes de la Licence
*   Publique Générale GNU publiée par la Free Software Foundation.
*
*   Ce programme est distribué car potentiellement utile, mais SANS AUCUNE GARANTIE, ni explicite ni implicite,
*   y compris les garanties de commercialisation ou d'adaptation dans un but spécifique. Reportez-vous à la
*   Licence Publique Générale GNU pour plus de détails.
*
*   Vous devez avoir reçu une copie de la Licence Publique Générale GNU en même temps que ce programme ; si ce
*   n'est pas le cas, écrivez à la Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
*   02111-1307, États-Unis.
*
***************************************
*
*   This program is free software; you can redistribute it and/or
*   modify it under the terms of the GNU General Public License
*   as published by the Free Software Foundation (version 2).
*
*   This program is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*
*   You should have received a copy of the GNU General Public License
*   along with this program; if not, write to the Free Software
*   Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*
********************************************/
package com.ondelette.servlet.webforum;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;


public final class ServletUtil implements ParameterNames, AutorizationConstants {


  public static void processNewMessage(HttpServletRequest request, Forum forum, PrintWriter out, User user) throws IOException {
          String SubjectString = request.getParameter(SUBJECTPARAM);
          if(SubjectString!=null)
            SubjectString = SubjectString.trim();
          String MessageString = request.getParameter(MESSAGEPARAM);
          if(MessageString != null)
            MessageString = MessageString.trim();
          String AuthorString = request.getParameter(AUTHORPARAM);
          if(AuthorString != null)
              AuthorString = HTMLUtil.makeStringHTMLSafe(AuthorString.trim());
          String EmailString = request.getParameter(EMAILPARAM);
          if(EmailString != null)
              EmailString = EmailString.trim();
          // mise à jour de la session
          user = updateUserStatus(forum, request, AuthorString, EmailString, user);
          if(user != null)
            AuthorString = user.getLogin();
          // on traite maintenant les deux types de posts
          if(request.getParameter(MESSAGEIDPARAM) != null) {
            final int MessageNumber = Integer.parseInt(request.getParameter(MESSAGEIDPARAM));
            forum.addReply(MessageNumber,SubjectString, MessageString, AuthorString, EmailString, user);
            forum.showMessage(MessageNumber,out, user,request.getServletPath());
            out.flush();
            return;
          }
          forum.addMessage(SubjectString, MessageString, AuthorString, EmailString, user);
          forum.getPage(0,out, user,request.getServletPath());
          out.flush();
  }

  private static User updateUserStatus(Forum forum, HttpServletRequest request, String AuthorString, String EmailString, User user) {
    HttpSession session = request.getSession(true);
    if(user == null) {
      user = new User(AuthorString,null,EmailString,NORMAL);
    } else if(forum.mUserList.isInList(AuthorString)) {
      user.setEmail(EmailString);
      forum.mUserList.save();
    } else {
      user.setEmail(EmailString);
      user.setLogin(AuthorString);
    }
    session.putValue(USERPARAM,user);
    return (user);
  }



  public static User processLoggingIn(HttpServletRequest request, Forum forum) {
    String login = request.getParameter(LOGINPARAM);
    if(login != null)
      login = login.trim();
    String password = request.getParameter(PASSWORDPARAM);
    if(password != null)
      password = password.trim();
    User user = forum.mUserList.getUser(login, password);
    if( user != null) {
      HttpSession session = request.getSession(true);
      session.putValue(USERPARAM,user);
    }
    return(user);
  }


  public static User getUser(HttpServletRequest request,
          HttpServletResponse response, Forum forum)
          throws ServletException, IOException {
        User user = null;
        HttpSession session = request.getSession(true);
          // on vérifie s'il y a déjà un user de défini
        if(session.getValue(USERPARAM) != null) {
            try {
              user = (User) session.getValue(USERPARAM);
              if(user == null) {
                session.removeValue(USERPARAM);
              }
              if((user.getAutorizationLevel() > 0) && (!forum.mUserList.isInList(user)) ) {
                session.removeValue(USERPARAM);
                user = null;
              }
            } catch (ClassCastException cce) {
              session.removeValue(USERPARAM);
            }
        }
        // on traite ensuite le login s'il y a lieu
        if ( request.getParameter(LOGINPARAM) != null ) {
          user = processLoggingIn(request, forum);
          if (   (user == null) || (user.getAutorizationLevel() < 0) ) {
            response.sendRedirect (forum.mWelcomeURL);
            return (null);
          }
        }
        return (user);
  }

}
