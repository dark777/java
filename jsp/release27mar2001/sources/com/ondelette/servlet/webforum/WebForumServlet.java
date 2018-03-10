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
import com.ondelette.servlet.*;

final public class WebForumServlet extends HttpServlet implements ParameterNames, AutorizationConstants {



  public void init(ServletConfig config)
    throws ServletException {
    super.init(config);
  }

  public void doGet(HttpServletRequest request,
          HttpServletResponse response)
          throws ServletException, IOException {
          doPost(request,response);
  }

  public void doPost(HttpServletRequest request,
          HttpServletResponse response)
          throws ServletException, IOException {
    try {
      if (request.getParameter(RESETPARAM) != null) {
        destroy();
      }
      // il doit absolument y avoir un forum de spécifié...
      // sinon, on ne peut rien faire!
      String s;
      if ( (s = request.getParameter(FORUMFILEPARAM)) == null ) {
        ServletErrorHandling.printErrorMessage(request, response, null);
        return;
      }
      // ok, on continue...
      Forum forum = ForumFactory.getForum (new File(s));
      User user =  ServletUtil.getUser(request,response,forum);
      if ((s = request.getParameter(SUBSCRIBEUSER)) != null) {
          if( (request.getParameter(NEWUSERNAME) == null)
                || (request.getParameter(NEWUSERPASSWORD) == null)
                || (request.getParameter(NEWUSERPASSWORD2) == null)) {
               response.sendRedirect (forum.mWelcomeURL);
               return;
          }
          String name = HTMLUtil.makeStringHTMLSafe(request.getParameter(NEWUSERNAME).trim());
          if(name.length() == 0) {
               response.sendRedirect (forum.mWelcomeURL);
               return;
          }
          if(forum.mUserList.isInList(name)) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            forum.showWarningNameInUse(name,out);
            out.flush();
            return;
          }
          String password1 = request.getParameter(NEWUSERPASSWORD).trim();
          String password2 = request.getParameter(NEWUSERPASSWORD2).trim();
          if(!password1.equals(password2)) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            forum.showWarningPasswordsDontMatch(name,out);
            out.flush();
            return;
          }
          String email = request.getParameter(NEWUSEREMAIL);
          if (email != null)
            email = email.trim();
          int level = IDENTIFIEDGUEST;
          user = new User(name,password1,email,level);
          HttpSession session = request.getSession(true);
          session.putValue(USERPARAM,user);
          forum.mUserList.add(user);
      }
      // ok, l'utilisateur est maintenant défini
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      if(forum.mAuthorizationLevel > NORMAL) {
        if(user == null) {
          forum.showWarningAutorizationRequired(out);
          out.flush();
          return;
        }  else if (user.getAutorizationLevel() < forum.mAuthorizationLevel) {
          forum.showWarningAutorizationRequired(out);
          out.flush();
          return;
        }
      }
      // on regarde s'il s'agit d'effacer un message
      if((s = request.getParameter(DELETEMESSAGEPARAM)) != null) {
          if(user == null) {
            forum.showWarningAutorizationRequired(out);
            out.flush();
            return;
          } else if ( user.getAutorizationLevel() >= EXECUTIVEMEMBER ) {
            final int messagenumber = Integer.parseInt(s);
            forum.askForConfirmationOnDeletingMessage(messagenumber,out,user,request.getServletPath());
            out.flush();
            return;
          }

      }
      if((s = request.getParameter(CONFIRMEDDELETEMESSAGEPARAM)) != null) {
          if(user == null) {
            forum.showWarningAutorizationRequired(out);
            out.flush();
            return;
          } else if ( user.getAutorizationLevel() >= EXECUTIVEMEMBER ) {
            final int messagenumber = Integer.parseInt(s);
            forum.deleteMessage(messagenumber,out,user,request.getServletPath());
            out.flush();
            return;
          }

      }
      if((s = request.getParameter(DELETEUSER)) != null) {
        if(user.getAutorizationLevel()  >= VIPMEMBER) {
          User currentuser = forum.mUserList.getUser(s);
          if(currentuser != null) {
            if(currentuser.getAutorizationLevel()==ADMIN) {
              forum.showWarningCannotChangeAdmin(out);
              out.flush();
              return;
            }
          } else {
           // System.out.println("cannot find user "+s);
          }
          forum.mUserList.remove(s);
          forum.displayUserList(out,request.getServletPath());
          out.flush();
          return;
        } else {
          forum.showWarningAutorizationRequired(out);
          out.flush();
          return;
        }
      }
      if(request.getParameter(DISPLAYUSERLIST)!=null) {
        if(user == null) {
          forum.showWarningAutorizationRequired(out);
          out.flush();
          return;
        } else if (user.getAutorizationLevel()  >= VIPMEMBER) {
          forum.displayUserList(out,request.getServletPath());
          out.flush();
          return;
        } else {
          forum.showWarningAutorizationRequired(out);
          out.flush();
          return;
        }
      }
      if((s = request.getParameter(DISPLAYUSER))!=null) {
        if(user == null) {
          forum.showWarningAutorizationRequired(out);
          out.flush();
          return;
        } else  if(user.getAutorizationLevel()  >= VIPMEMBER) {
          forum.displayUser(s,out,request.getServletPath());
          out.flush();
          return;
        } else {
          forum.showWarningAutorizationRequired(out);
          out.flush();
          return;
        }
      }
      if(((request.getParameter(ADDUSER)!= null)||(request.getParameter(CHANGEUSER)!= null))) {
         if(user == null) {
          forum.showWarningAutorizationRequired(out);
          out.flush();
          return;
        } else if(user.getAutorizationLevel()  >= VIPMEMBER) {
          if (( request.getParameter(NEWUSERNAME) == null )
            || (request.getParameter(NEWUSERPASSWORD) == null )) {
              forum.displayUserList(out,request.getServletPath());
              out.flush();
              return;
          }
          String name = HTMLUtil.makeStringHTMLSafe(request.getParameter(NEWUSERNAME).trim());
          if(name.length() == 0) {
              forum.displayUserList(out,request.getServletPath());
              out.flush();
              return;
          }
          User currentuser = forum.mUserList.getUser(name);
          if(currentuser!=null) {
            if(currentuser.getAutorizationLevel()==ADMIN) {
              forum.showWarningCannotChangeAdmin(out);
              out.flush();
              return;
            }
          }
          String password = request.getParameter(NEWUSERPASSWORD).trim();
          String email = request.getParameter(NEWUSEREMAIL);
          if(email!= null)
            email = email.trim();
          int level = Integer.parseInt(request.getParameter(NEWUSERLEVEL).trim());
          if(level >=ADMIN)
            level = VIPMEMBER;
          User u = new User(name,password,email,level);
          forum.mUserList.add(u);
          String oldname;
          if(( oldname = request.getParameter(OLDUSERNAME))!=null) {
            if(!oldname.equals(name))
              forum.mUserList.remove(oldname);
          }
          forum.displayUserList(out,request.getServletPath());
          out.flush();
          return;
        } else {
          forum.showWarningAutorizationRequired(out);
          out.flush();
          return;
        }
      }
      // on regarde si c'est un nouveau message
      if(request.getParameter(SUBMITMESSAGEPARAM) != null) {
        ServletUtil.processNewMessage(request, forum, out, user);
        out.flush();
        return;
      } else if ((s = request.getParameter(SHOWPARAM)) != null) {
        // on regarde s'il s'agit d'afficher un message particulier
        int k = Integer.parseInt(s);
        forum.showMessage(k,out,user,request.getServletPath());
        out.flush();
        return;
      } else if( (s = request.getParameter(PAGEPARAM)) != null) {
        // on regarde s'il s'agit d'afficher une page en particulier
        int k = Integer.parseInt(s);
        forum.getPage(k,out,user,request.getServletPath());
        out.flush();
        return;
      } else if( (s = request.getParameter(LASTMESSAGES)) != null) {
        //affichage des dix derniers messages
        int k = Integer.parseInt(s);
        forum.getLastMessages(k,out,user,request.getServletPath());
        out.flush();
        return;
      } else {
        // dans le doute, on affiche la première page du forum
        // (comportement par défaut)
        forum.getPage(0,out,user,request.getServletPath());
        out.flush();
        return;
      }/**/
    } catch (Exception ioe) {
        ServletErrorHandling.printErrorMessage(request, response, ioe);
    }
  }



  public void destroy() {
    ForumLocaleFactory.destroy();
    ForumFactory.destroy();
  }

}
