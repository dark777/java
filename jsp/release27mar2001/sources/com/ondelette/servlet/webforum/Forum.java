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

import java.util.*;
import java.io.*;
import javax.servlet.*;
import com.ondelette.servlet.*;


/***************
* Cette classe encapsule un "forum" et
* lit et enregistre le fichier de configuration
*****************/
final public class Forum {

  protected String mForumTitle;
  protected String mForumSubTitle;
  protected ForumLocale mForumLocale;
  protected long mSaveDelay;
  protected int mPostPrivilege = 0;
  protected MessageFolder mMessageFolder;
  protected UserList mUserList;
  protected File mMessageArchiveDirectory;
  protected File mConfigFile;
  protected File mForumLocaleFile;
  protected File mUserListFile;
  protected int mAuthorizationLevel = 0;
  protected String mForumFooter;
  protected String mWelcomeURL;
  protected String mCSSURL;

  public Forum (File forumconfigfile) throws IOException {
    Properties p  = new Properties();
    BufferedInputStream fis = new BufferedInputStream(new FileInputStream(forumconfigfile));
    p.load(fis);
    fis.close();
    mConfigFile = forumconfigfile;
    mMessageArchiveDirectory = new File(p.getProperty("MessageArchiveDirectory"));// if cannot parse, must fail
    mForumLocaleFile = new File(p.getProperty("ForumLocaleFile")); // if cannot parse, must fail
    mUserListFile = new File(p.getProperty("UserListFile"));// if cannot parse, must fail
    try {
      mSaveDelay = Long.parseLong(p.getProperty("SaveDelay"));
    } catch (Exception e) {e.printStackTrace();}
    mCSSURL = p.getProperty("CSSURL");
    if(mCSSURL == null)
      mCSSURL ="";
    mForumTitle = p.getProperty("ForumTitle");
    if(mForumTitle == null)
      mForumTitle = "";
    mForumSubTitle = p.getProperty("ForumSubTitle");
    if(mForumSubTitle == null)
      mForumSubTitle = "";
    try {
      mAuthorizationLevel = Integer.parseInt(p.getProperty("AuthorizationLevel"));
    } catch (Exception e) {}
    mForumFooter = p.getProperty("ForumFooter");
    if (mForumFooter == null)
      mForumFooter = "";
    mWelcomeURL = p.getProperty("WelcomeURL");
    if(mWelcomeURL == null)
      mWelcomeURL ="";
    try {
        mPostPrivilege = Integer.parseInt(p.getProperty("PostPrivilege"));
    } catch (Exception e) {}//on tolère cette erreur
    // initialisation des objets
    mForumLocale = ForumLocaleFactory.getForumLocale(mForumLocaleFile);
    mUserList = UserListFactory.getUserList(mUserListFile);
    mMessageFolder = new MessageFolder(this);
  }

  public void getPage (int k, PrintWriter out, User user, String ServletPath) throws IOException {
    mForumLocale.printForumHeader(this,out);
    mForumLocale.printForumTitle(this,out);
    mForumLocale.printMenu(this,out,k,user,ServletPath);
    mMessageFolder.writePageSummaryHTML(k,out,ServletPath);
    mForumLocale.printMenu(this,out,k,user,ServletPath);
    if(mPostPrivilege > 0) {
      if(user != null)
        if(user.getAutorizationLevel() >= mPostPrivilege)
          mForumLocale.printPostForm(this,out,user,ServletPath);
    } else
      mForumLocale.printPostForm(this,out,user, ServletPath);
    mForumLocale.printButtons(this,out,user,ServletPath);
    out.print(mForumFooter);
    ServletCopyright.printFooter(out);
  }

  public void getLastMessages (int k, PrintWriter out, User user, String ServletPath) throws IOException {
    mForumLocale.printForumHeader(this,out);
    mForumLocale.printForumTitle(this,out);
    mForumLocale.printMenu(this,out,-1,user,ServletPath);//-1 parce qu'on n'est pas sur une page en particulier
    mForumLocale.printBackToForum(out,this,ServletPath);
    mMessageFolder.writeLastMessagesSummaryHTML(k,out,ServletPath);
    mForumLocale.printMenu(this,out,-1,user,ServletPath);
    if(mPostPrivilege > 0) {
      if(user != null)
        if(user.getAutorizationLevel() >= mPostPrivilege)
          mForumLocale.printPostForm(this,out,user,ServletPath);
    } else
      mForumLocale.printPostForm(this,out,user,ServletPath);
    mForumLocale.printButtons(this,out,user,ServletPath);
    out.print(mForumFooter);
    ServletCopyright.printFooter(out);
  }

  /*
  * convenience method
  */
  public MessageReference addMessage(String SubjectString, String MessageString, String AuthorString, String EmailString, User user) throws IOException {
    Message message = new Message(AuthorString,EmailString, SubjectString,MessageString);
    if(user!= null)
      message.setAutorizationLevel(user.getAutorizationLevel());
    return(addMessage(message));
  }

  public MessageReference addMessage(Message message) throws IOException {
    return(mMessageFolder.addMessage(message));
  }


  /*
  * convenience method
  */
  public MessageReference addReply(int k, String SubjectString, String MessageString, String AuthorString, String EmailString, User user) throws IOException {
    Message message = new Message(AuthorString,EmailString, SubjectString,MessageString);
    message.setInReplyTo(k);
    if(user!= null)
      message.setAutorizationLevel(user.getAutorizationLevel());
    return(addMessage(message));
  }


  public void showMessage(int k, PrintWriter out, User user, String ServletPath) throws IOException {
    mForumLocale.printForumHeader(this,out);
    mForumLocale.printForumTitle(this,out);
    mForumLocale.printMenu(this,out,-1,user,ServletPath);
    mForumLocale.printInFull(k,this,out, user,ServletPath);
    if(mPostPrivilege > 0) {
      if(user != null)
        if(user.getAutorizationLevel() >= mPostPrivilege)
          mForumLocale.printReplyForm(k,this,out,user,ServletPath);
    } else
    mForumLocale.printReplyForm(k,this,out,user,ServletPath);
    mForumLocale.printButtons(this,out,user,ServletPath);
    out.print(mForumFooter);
    out.print(mForumFooter);
    ServletCopyright.printFooter(out);
  }

  public void deleteMessage(int k, PrintWriter out, User user, String ServletPath) throws IOException {
    mMessageFolder.deleteMessage(k);
    getPage(0,out,user, ServletPath);
  }

  public void askForConfirmationOnDeletingMessage(int k, PrintWriter out, User user, String ServletPath) throws IOException {
    mForumLocale.printForumHeader(this,out);
    mForumLocale.printForumTitle(this,out);
    mForumLocale.printDeleteConfirmation(k,this,out, user,ServletPath);
    out.print(mForumFooter);
    ServletCopyright.printFooter(out);
  }

/*
  Cette méthode pose un problème de sécurité.
  public synchronized void saveConfig() throws IOException {
    Properties p  = new Properties();
    p.put("MessageArchiveDirectory",this.mMessageArchiveDirectory.toString());
    p.put("ForumLocaleFile",this.mForumLocaleFile.toString());
    p.put("UserListFile",this.mUserListFile.toString());
    p.put("SaveDelay",Long.toString(this.mSaveDelay));
    p.put("ForumTitle",mForumTitle);
    p.put("AuthorizationLevel",Integer.toString(mAuthorizationLevel));
    p.put("ServletURL",mServletURL);
    p.put("CSSURL",mCSSURL);
    p.put("WelcomeURL",mWelcomeURL);
    BufferedOutputStream fis = new BufferedOutputStream(new FileOutputStream(mConfigFile));
    p.save(fis,"Configuration file for forum "+mForumTitle+" in file "+mConfigFile.toString());
    fis.close();
  }*/

  public void destroy() {
    mMessageFolder.destroy();
  }

  public void showWarningNameInUse(String name, PrintWriter out) throws IOException {
    mForumLocale.printForumHeader(this,out);
    mForumLocale.printForumTitle(this,out);
    mForumLocale.showWarningNameInUse(name,out,this);
    out.print(mForumFooter);
    ServletCopyright.printFooter(out);
  }

  public void showWarningPasswordsDontMatch(String name, PrintWriter out)  throws IOException {
    mForumLocale.printForumHeader(this,out);
    mForumLocale.printForumTitle(this,out);
    mForumLocale.showWarningNameInUse(name,out,this);
    out.print(mForumFooter);
    ServletCopyright.printFooter(out);
  }

  public void showWarningAutorizationRequired(PrintWriter out)  throws IOException {
      mForumLocale.printForumHeader(this,out);
      mForumLocale.printForumTitle(this,out);
      mForumLocale.showWarningAutorizationRequired(out,this);
      out.print(mForumFooter);
      ServletCopyright.printFooter(out);
  }

  public void showWarningCannotChangeAdmin(PrintWriter out)  throws IOException {
      mForumLocale.printForumHeader(this,out);
      mForumLocale.printForumTitle(this,out);
      mForumLocale.showWarningCannotChangeAdmin(out,this);
      out.print(mForumFooter);
      ServletCopyright.printFooter(out);
  }

  public void displayUserList (PrintWriter out, String ServletPath)  throws IOException {
      mForumLocale.printForumHeader(this,out);
      mForumLocale.printForumTitle(this,out);
      mForumLocale.printUserList(this,out,ServletPath);
      out.print(mForumFooter);
      ServletCopyright.printFooter(out);
  }

  public void displayUser(String name, PrintWriter out, String ServletPath)  throws IOException {
      mForumLocale.printForumHeader(this,out);
      mForumLocale.printForumTitle(this,out);
      mForumLocale.printUser(this,name,out,ServletPath);
      out.print(mForumFooter);
      ServletCopyright.printFooter(out);
  }

}
