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

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;
import javax.servlet.*;
import com.ondelette.servlet.*;


/***************
* Cette classe encapsule un "locale",
* plusieurs forum pouvant partager le même "locale"
* et cette classe se lit et s'écrit sur le disque au
* besoin.
*****************/
final public class ForumLocale implements ParameterNames, AutorizationConstants, FixedNumericalConstants {



    int mSizeOfForumFolders = 20;//30 = SizeOfForumFolders;
    int mNumberOfFolders = 20;//15 = NumberOfFolders;
    String mResetString = "Relancer la servlet";
    String mDeleteButtonString = "Effacer ce message";
    String mConfirmDeleteButtonString = "Oui, je veux effacer ce message!";
    String mCancelDeleteButtonString = "Non, je ne veux pas efface ce message!";
    String mInReplyTo = "En réponse à";
    String mSansTitreString = "Sans titre";
    String mAnonymousString = "Anonyme";
    String mByteString = " octet";
    String mBytesString = " octets";
    String mNewMessageString = "Soumettre un nouveau message";
    String mNewReplyString = "Répondre à ce message";
    String mMainString = "Retour au forum";
    String mReplyHeaderString = "En réponse à: ";
    String mFromString = "De";
    String mShortFromString = "par";
    String mSubmitString = "Soumettre mon message";
    String mReplyButtonString = "Soumettre ma réponse";
    String mSubjectString = "Sujet";
    String mMessageString = "Message";
    String mAuthorString = "Nom";
    String mEmailString = "Courriel";
    String mPasswordString = "Mot de passe";
    String mLinesString = "lignes";
    String mLineString = "ligne";
    String mMustEnterNameString = "Vous devez saisir votre nom.";
    String mMustEnterSubjectString = "Vous devez saisir un titre.";
    String mDisplayUserListString = "Liste des usagers";
    String mWelcomePageString = "Page d'accueil du forum (inscription)";
    String mWarningNameInUseString = "Ce nom est déjà utilisé.";
    String mWarningPasswordsDontMatchString =  "Vous devez entrer deux fois le même mot de passe.";
    String mWarningAutorizationRequiredString = "Vous n'avez pas l'autorisation nécessaire.";
    String mWarningCannotChangeAdminString = "Vous ne pouvez pas modifier, créer ou effacer un administrateur.";
    String mDeleteUserString = "Effacer cet utilisateur (irréversible).";
    String mAddUserString = "Ajouter cet utilisateur.";
    String mChangeUserString = "Modifier cet utilisateur.";
    String mLastTenString = "Dix messages les plus récents";
    String mOptionalLinkURLString = "Lien URL (optionnel)";
    String mLinkTitleString = "Titre de l'URL";
    String mImageURLString = "URL d'une image (optionnel)";
    String mLocaleLanguage = "fr";
    String mLocaleCountry ="CA";
    String mLocaleVariant ="";
    int mNumberOfRowsForTextArea = 10;
    int mNumberOfColsForTextArea = 37;
    int mNumberOfColsForTextFields = 40;
	  Locale mCurrentLocale;// = new Locale(mLocaleLanguage,mLocaleCountry,mLocaleVariant);
//	DateFormat CurrentDateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL,CurrentLocale);
    DateFormat mDateFormat;



    final static String mOptionalLinkURLPARAM = "OptionalLinkURL";
    final static String mLinkTitlePARAM = "LinkTitle";
    final static String mImageURLPARAM = "ImageURL";
    final static String mSubjectJavaScriptValue = "document."+SUBMITFORMNAME +"."+ SUBJECTPARAM+ ".value";
    final static String mAuthorJavaScriptValue = "document."+SUBMITFORMNAME +"."+ AUTHORPARAM+ ".value";
    final static String mMessageJavaScriptValue = "document."+SUBMITFORMNAME +"."+ MESSAGEPARAM+ ".value";
    final static String mOptionalLinkURLJavaScriptValue = "document."+SUBMITFORMNAME +"."+ mOptionalLinkURLPARAM+ ".value";
    final static String mLinkTitleJavaScriptValue = "document."+SUBMITFORMNAME +"."+ mLinkTitlePARAM+ ".value";
    final static String mImageURLJavaScriptValue = "document."+SUBMITFORMNAME +"."+ mImageURLPARAM+ ".value";



  private void readConfig(File LocaleFile) throws IOException {
      Properties p = new Properties();
      BufferedInputStream fis =new BufferedInputStream( new FileInputStream(LocaleFile));
      p.load(fis);
      fis.close();
      String s = (String)p.get("SizeOfForumFolders");
      if(s!=null)
        mSizeOfForumFolders = Integer.parseInt(s);
      s = (String)p.get("NumberOfFolders");
      if(s!=null)
        mNumberOfFolders = Integer.parseInt(s);
      s = (String)p.get("ResetString");
      if(s!=null)
        mResetString = new String(s);
      s = (String)p.get("DeleteButton");
      if(s!=null)
        mDeleteButtonString = new String(s);
      s = (String)p.get("ConfirmDeleteButton");
      if(s!=null)
        mConfirmDeleteButtonString = new String(s);
      s = (String)p.get("CancelDeleteButton");
      if(s!=null)
        mCancelDeleteButtonString = new String(s);
      s = (String)p.get("InReplyTo");
      if(s!=null)
        mInReplyTo = new String(s);
      s = (String)p.get("SansTitre");
      if(s!=null)
        mSansTitreString = new String(s);
      s = (String)p.get("Anonymous");
      if(s!=null)
        mAnonymousString = new String(s);
      s = (String)p.get("Byte");
      if(s!=null)
        mByteString = new String(s);
      s = (String)p.get("Bytes");
      if(s!=null)
        mBytesString = new String(s);
      s = (String)p.get("NewMessage");
      if(s!=null)
        mNewMessageString = new String(s);
      s = (String)p.get("NewReply");
      if(s!=null)
        mNewReplyString = new String(s);
      s = (String)p.get("Main");
      if(s!=null)
        mMainString = new String(s);
      s = (String)p.get("ReplyHeader");
      if(s!=null)
        mReplyHeaderString = new String(s);
      s = (String)p.get("From");
      if(s!=null)
        mFromString = new String(s);
      s = (String)p.get("ShortFrom");
      if(s!=null)
        mShortFromString = new String(s);
      s = (String)p.get("Submit");
      if(s!=null)
        mSubmitString = new String(s);
      s = (String)p.get("ReplyButton");
      if(s!=null)
        mReplyButtonString = new String(s);
      s = (String)p.get("Subject");
      if(s!=null)
        mSubjectString = new String(s);
      s = (String)p.get("Message");
      if(s!=null)
        mMessageString = new String(s);
      s = (String)p.get("Author");
      if(s!=null)
        mAuthorString = new String(s);
      s = (String)p.get("Email");
      if(s!=null)
        mEmailString = new String(s);
      s = (String)p.get("Password");
      if(s!=null)
        mPasswordString = new String(s);
      s = (String)p.get("Lines");
      if(s!=null)
        mLinesString = new String(s);
      s = (String)p.get("Line");
      if(s!=null)
        mLineString = new String(s);
      s = (String)p.get("MustEnterName");
      if(s!=null)
        mMustEnterNameString = new String(s);
      s = (String)p.get("MustEnterSubject");
      if(s!=null)
        mMustEnterSubjectString = new String(s);
      s = (String)p.get("DisplayUserList");
      if(s!=null)
        mDisplayUserListString = new String(s);
      s = (String) p.get("WelcomePage");
      if(s!=null)
        mWelcomePageString = new String(s);
      s = (String) p.get("WarningNameInUse");
      if(s!=null)
        mWarningNameInUseString = new String(s);
      s = (String) p.get("WarningPasswordsDontMatch");
      if(s!=null)
        mWarningPasswordsDontMatchString = new String(s);
      s = (String) p.get("WarningAutorizationRequired");
      if(s!=null)
        mWarningAutorizationRequiredString = new String(s);
      s = (String) p.get("WarningCannotChangeAdmin");
      if(s!=null)
        mWarningCannotChangeAdminString = new String(s);
      s = (String) p.get("DeleteUser");
      if(s!=null)
        mDeleteUserString = new String(s);
      s = (String) p.get("AddUser");
      if(s!=null)
        mAddUserString =  new String(s);
      s = (String) p.get("ChangeUser");
      if(s!=null)
        mChangeUserString = new String(s);
      s = (String) p.get("LastTen");
      if(s!=null)
        mLastTenString = new String(s);
      s = (String) p.get("OptionalLinkURL");
      if(s!=null)
        mOptionalLinkURLString = new String(s);
      s = (String) p.get("LinkTitle");
      if(s!=null)
        mLinkTitleString = new String(s);
      s = (String) p.get("ImageURL");
      if(s!=null)
        mImageURLString = new String(s);
      s = (String)p.get("LocaleLanguage");
      if(s!=null)
        mLocaleLanguage = new String(s);
      s = (String)p.get("LocaleCountry");
      if(s!=null)
        mLocaleCountry = new String(s);
      s = (String)p.get("LocaleVariant");
      if(s!=null)
        mLocaleVariant = new String(s);
      s = (String)p.get("NumberOfRowsForTextArea");
      if(s!=null)
        mNumberOfRowsForTextArea = Integer.parseInt(s);
      s = (String)p.get("NumberOfColsForTextArea");
      if(s!=null)
        mNumberOfColsForTextArea = Integer.parseInt(s);
      s = (String)p.get("NumberOfColsForTextFields");
      if(s!=null)
        mNumberOfColsForTextFields = Integer.parseInt(s);
  }


  public ForumLocale(File LocaleFile) {
    try {
      readConfig(LocaleFile);
    } catch (Exception e) {
      e.printStackTrace();
    }
    mCurrentLocale = new Locale(mLocaleLanguage,mLocaleCountry,mLocaleVariant);
    mDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT,mCurrentLocale);
  }





  public void printDeleteConfirmation(int mr, Forum forum, PrintWriter out, User user, String ServletPath) throws IOException {
        Message message = forum.mMessageFolder.getMessage(mr);
        confirmDeleteForm(out,forum,mr,user, ServletPath);
        printMessageTable(message, forum, out, ServletPath);
        message.writeRepliesSummaryHTML(this,forum,out,mr, ServletPath);
  }


  public void printInFull(int mr, Forum forum, PrintWriter out, User user, String ServletPath) throws IOException {
    printBackToForum(out,forum, ServletPath);
    Message message = forum.mMessageFolder.getMessage(mr);
    if(user != null) {
      if(user.getAutorizationLevel() >= EXECUTIVEMEMBER) {
          deleteForm(out, forum, mr, user, ServletPath);
      }
    }
    printMessageTable(message, forum, out, ServletPath);
    message.writeRepliesSummaryHTML(this,forum,out,mr, ServletPath);
  }

  private void printMessageTable(Message message, Forum forum, PrintWriter out,String ServletPath) throws IOException {
    out.print("<DIV  CLASS=\"FULLMESSAGE\">");
    out.print("<TABLE CLASS=\"FULLMESSAGE\" BORDER=0>");
    out.println();
    if(message.getInReplyTo() >= 0) {
      out.print("<TR><TD CLASS=\"INREPLYTOID\">");
      out.print(mInReplyTo);
      out.print("&nbsp;: ");
      int parentmr = message.getInReplyTo();
      printSummaryWithoutReplies(parentmr,forum,out,ServletPath);
      out.println("</TD></TR>");
    }
    out.print("<TR><TD CLASS=\"DISPLAYSUBJECT\">");
    out.print(mSubjectString);
    out.print("&nbsp;: ");
    if(message.getSubject()!=null)
      out.print(message.getSubject().toString());
    else
      out.print(mSansTitreString);
    out.print("<BR>");
    putStamp(message,out);
    out.print("</TD></TR>");
    out.println();
    String AuthorClass = getAuthorClass(message.getAutorizationLevel());
    out.print("<TR><TD CLASS=\"");
    out.print(AuthorClass);
    out.print("\">");
    out.print(mFromString);
    out.print("&nbsp;: ");
    if(message.getAuthor() != null) {
      if(message.getAuthorEmail() != null) {
        out.print("<A HREF=\"mailto:");
        out.print(message.getAuthorEmail());
        out.print("\" CLASS=\"");
        out.print(AuthorClass);
        out.print("\">");
        out.print(message.getAuthor().toString());
        out.print("</A>");
      } else {
        out.print(message.getAuthor().toString());
      }
    } else {
      out.print(this.mAnonymousString);
    }
    out.print("</TD></TR>");
    out.println("</TABLE>");
    out.println("</DIV>");
    if(message.getMessage()!=null) {
      out.println("<DIV CLASS=\"DISPLAYMESSAGE\">");
      out.print(mMessageString);
      out.println("&nbsp;: <BR>");
      out.print(message.getFormattedMessage());
      out.println("</DIV>");
    }
  }

  private String getAuthorClass(int AutorizationLevel) {
    String AuthorClass = "AUTHORNAME";
    if(AutorizationLevel >= AutorizationConstants.ADMIN)
      AuthorClass ="ADMINAUTHOR";
    else if (AutorizationLevel >= AutorizationConstants.VIPMEMBER)
      AuthorClass ="VIPMEMBER";
    else if (AutorizationLevel >= AutorizationConstants.EXECUTIVEMEMBER)
      AuthorClass ="EXECUTIVEMEMBERAUTHOR";
    else if (AutorizationLevel >= AutorizationConstants.MEMBER)
      AuthorClass ="MEMBERAUTHOR";
    else if (AutorizationLevel >= AutorizationConstants.IDENTIFIEDGUEST)
      AuthorClass ="GUESTAUTHOR";
    return(AuthorClass);
  }

  public void printSummary(int mr, Forum forum, PrintWriter out,String ServletPath) throws IOException {
    Message message = printSummaryWithoutReplies(mr,forum,out,ServletPath);
    out.println();
    message.writeRepliesSummaryHTML(this,forum,out,mr, ServletPath);
  }

  public Message printSummaryWithoutReplies(int mr, Forum forum, PrintWriter out, String ServletPath) throws IOException {
    Message message = forum.mMessageFolder.getMessage(mr);
    out.println();
    out.print("<A HREF=\"");
    out.print(ServletPath);
    out.print("?");
    out.print(FORUMFILEPARAM);
    out.print("=");
    out.print(forum.mConfigFile.getPath());
    out.print("&");
    out.print(SHOWPARAM);
    out.print("=");
    out.print(Integer.toString(mr));
    out.print("\" CLASS=\"SHOWLINK\">");
    if (message.getSubject() != null)
      if(message.getSubject().length() > 0)
        out.print(message.getSubject());
      else
        out.print(mSansTitreString);
    else
      out.print(mSansTitreString);
    out.print("</A>");
    out.print(" ");
    out.print(mShortFromString);
    out.print(" ");
    String AuthorClass = getAuthorClass(message.getAutorizationLevel());
    if( message.getAuthor() != null) {
      if(message.getAuthorEmail() != null) {
        if(message.getAuthorEmail().length() > 0) {
          out.print("<A HREF=\"mailto:");
          out.print(message.getAuthorEmail());
          out.print("\" CLASS=\"");
          out.print(AuthorClass);
          out.print("\">");
          out.print(message.getAuthor());
          out.print("</A>");
        } else {
          out.print("<B CLASS=\"");
          out.print(AuthorClass);
          out.print("\">");
          out.print(message.getAuthor());
          out.print("</B>");
        }
      } else {
          out.print("<B CLASS=\"");
          out.print(AuthorClass);
          out.print("\">");
          out.print(message.getAuthor());
          out.print("</B>");
      }
    } else {
      out.print("<B CLASS=\"");
      out.print(AuthorClass);
      out.print("\">");
      out.print(mAnonymousString);
      out.print("</B>");
    }
    putStamp(message,out);
    return(message);
  }

  private void putStamp (Message message, PrintWriter out) throws IOException {
    out.print(" <SMALL CLASS=\"STAMP\"><I>");
    out.print(mDateFormat.format(message.getDate()));
    out.print("</I>");
    if (false) {
      out.println(" [");
      out.print(Integer.toString(message.getNumberOfBytes()));
      out.print(" ");
      if(message.getNumberOfBytes() < 2)
        out.print(mByteString);
      else
        out.print(mBytesString);
      out.print("][");
      out.print(Integer.toString(message.getNumberOfLines()));
      out.print(" ");
      if (message.getNumberOfLines() < 2)
        out.print(this.mLineString);
      else
       out.print(this.mLinesString);
      out.print("]");
    }
    out.print("</SMALL>");
  }

  public void printButtons(Forum forum, PrintWriter out, User user, String ServletPath) throws IOException {
    if(user != null) {
      if(user.getAutorizationLevel() >= VIPMEMBER) {
        printResetForm(out,forum, ServletPath);
        printUserListForm(out,forum, ServletPath);
      }
    }
    printBackToWelcomeURL(out,forum);

  }

  public void printMenu(Forum forum, PrintWriter out, int currentpage, User user, String ServletPath) throws IOException {
    int pages = forum.mMessageFolder.getNumberOfPages() > mNumberOfFolders ? mNumberOfFolders : forum.mMessageFolder.getNumberOfPages();
    out.print("<P CLASS=\"MENU\">[ ");
    out.println();
    if(currentpage == 0)
      out.print("<B>1</B>");
    else {
      out.print("<A HREF=\"");
      out.print(ServletPath);
      out.print("?");
      out.print(FORUMFILEPARAM);
      out.print("=");
      out.print(forum.mConfigFile.getPath());
      out.print("&");
      out.print(PAGEPARAM);
      out.print("=0\">1</A>");
    }
    String stringk, stringkMinus;
    for(int k = 1; k < pages; k++) {
      out.println();
      out.print(" | ");
      stringk = Integer.toString(k + 1);
      stringkMinus = Integer.toString(k);
      if(k == currentpage) {
        out.print("<B>"
        +stringk
        +"</B>");
      } else {
        out.print("<A HREF=\"");
        out.print(ServletPath);
        out.print("?");
        out.print(FORUMFILEPARAM);
        out.print("=");
        out.print(forum.mConfigFile.getPath());
        out.print("&");
        out.print(PAGEPARAM);
        out.print("=");
        out.print(stringkMinus);
        out.print("\">");
        out.print(stringk);
        out.print("</A>");
      }
    }
    out.println(" ] ");
    out.println("</P>");
    out.println("<P CLASS=\"MENU\">");
    out.print("<A HREF=\"");
    out.print(ServletPath);
    out.print("?");
    out.print(FORUMFILEPARAM);
    out.print("=");
    out.print(forum.mConfigFile.getPath());
    out.print("&");
    out.print(LASTMESSAGES);
    out.print("=");
    out.print(10);
    out.print("\">");
    out.print(mLastTenString);
    out.print("</A>");
    out.println("</P>");
  }

  public void printForumTitle (Forum forum, PrintWriter out) throws IOException {
    if (forum.mForumTitle != null) {
      out.print("<H1 CLASS=\"TITLE\">");
      out.print(forum.mForumTitle);
      out.println("</H1>");
    }
    if(forum.mForumSubTitle != null) {
      out.print("<H2 CLASS=\"TITLE\">");
      out.print(forum.mForumSubTitle);
      out.println("</H1>");
    }
  }


  private void printJavaScriptHeader(Forum forum, PrintWriter out ) throws IOException {
    out.println("<meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\">");
    out.println("<script type=\"text/javascript\">");
    out.println("<!--");
    out.println("function checkMessage(ext) {");
    out.print("if ((");
    out.print(mAuthorJavaScriptValue);
    out.print(" == null) || (");
    out.print(mAuthorJavaScriptValue);
    out.println(" == ''))");
    out.print("alert('");
    out.print(mMustEnterNameString);
    out.println("');");
    out.print("else if ((");
    out.print(mSubjectJavaScriptValue);
    out.print(" == null) || (");
    out.print(mSubjectJavaScriptValue);
    out.println(" == ''))");
    out.print("alert('");
    out.print(mMustEnterSubjectString);
    out.println("');");
    out.println("else {");
    out.print("if ((");
    out.print(mImageURLJavaScriptValue);
    out.print(" != null) && (");
    out.print(mImageURLJavaScriptValue);
    out.print(" != 'HTTP://') && (");
    out.print(mImageURLJavaScriptValue);
    out.println(" != '')) { ");
    out.println(mMessageJavaScriptValue+" = " +mMessageJavaScriptValue
      +" + '<DIV ALIGN=CENTER><IMG SRC=\"' + "+mImageURLJavaScriptValue+" + '\" ALIGN=CENTER></DIV>';");
    out.println("}");

    out.print("if ((");
    out.print(mOptionalLinkURLJavaScriptValue);
    out.print(" != 'HTTP://') && (");
    out.print(mOptionalLinkURLJavaScriptValue);
    out.print(" != '') && (");
    out.print(mOptionalLinkURLJavaScriptValue);
    out.print(" != null)) {");
    out.print(" if ((");
    out.print(mLinkTitleJavaScriptValue);
    out.print(" != null) && (");
    out.print(mLinkTitleJavaScriptValue);
    out.println(" != '')) { ");
    out.println(" "+mMessageJavaScriptValue+" = " +mMessageJavaScriptValue
      +" + '<DIV ALIGN=CENTER><A HREF=\"' + "+mOptionalLinkURLJavaScriptValue+"+ '\">' + "+mLinkTitleJavaScriptValue+" + '</A></DIV>';");
    out.println(" } else {");
    out.println(" "+mMessageJavaScriptValue+" = " +mMessageJavaScriptValue
      +" + '<DIV ALIGN=CENTER><A HREF=\"' + "+mOptionalLinkURLJavaScriptValue+"+ '\">' + "+mOptionalLinkURLJavaScriptValue+" + '</A></DIV>';");
    out.println("}");
    out.println("}");


    out.print("document.");
    out.print(SUBMITFORMNAME);
    out.println(".submit();}");

    out.println("}");
    out.println("// fin du script -->");
    out.println("</SCRIPT>");
}

  public void printForumHeader (Forum forum, PrintWriter out) throws IOException {
    out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\"");
    out.println("\"http://www.w3.org/TR/REC-html40/loose.dtd\">");
    out.println("<HTML>");
    out.println("<HEAD>");
    out.print("<TITLE>");
    out.print(forum.mForumTitle);
    out.println("</TITLE>");
    out.print("<link rel=stylesheet type=\"text/css\" href=\"");
    out.print(forum.mCSSURL);
    out.println("\">");
    out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">");
    out.println("<meta http-equiv=\"Content-Style-Type\" content=\"text/css\">");
    out.print("<META HTTP-EQUIV=\"Content-Language\" CONTENT=\"");
    out.print(mLocaleLanguage);
    out.println("\">");
    out.print("<META NAME=\"Copyright\" CONTENT=\"");
    out.print(ServletCopyright.getCopyright());
    out.println("\">");
    printJavaScriptHeader(forum, out);
    out.print("</HEAD>");
    out.println();
    out.print("<BODY>");
    out.println();
  }

  public void printReplyForm (int mr, Forum forum, PrintWriter out, User user, String ServletPath) throws IOException {
    out.print("<P CLASS=\"REPLY\">");
    out.print(mNewReplyString);
    out.println("&nbsp;:</P>");
    printForm(out,forum,Integer.toString(mr),user, ServletPath);
  }

  public void printPostForm (Forum forum, PrintWriter out, User user, String ServletPath) throws IOException {
    out.print("<P CLASS=\"REPLY\">");
    out.print(mNewMessageString);
    out.println("&nbsp;:</P>");
    printForm(out,forum,null,user, ServletPath);
  }

  private void printForm(PrintWriter out, Forum forum, String MessageID, User user, String ServletPath) throws IOException {
    out.print("<form action=\"");
    out.print(ServletPath);
    out.print("\" method=\"post\" NAME=\"");
    out.print(SUBMITFORMNAME);
    out.println("\">");
    if (MessageID != null) {
     out.print("<input type=\"hidden\" name=\"");
     out.print(MESSAGEIDPARAM);
     out.print("\" value=\"");
     out.print(MessageID);
     out.println("\">");
    }
    out.print("<input type=\"hidden\" name=\"");
    out.print(FORUMFILEPARAM);
    out.print("\" value=\"");
    out.print(forum.mConfigFile.getPath());
    out.println("\">");
    out.println("<DIV CLASS=\"POSTMESSAGEFORM\">");
    out.println("<TABLE CLASS=\"POSTMESSAGEFORM\">");
    out.println("<TR><TD CLASS=\"SUBJECT\">");
    out.print(mSubjectString);
    out.println("&nbsp;:");
    out.println("</TD><TD CLASS=\"SUBJECTFIELD\">");
    out.print("<input type=\"TEXT\" value=\"");
    out.print("\" name=\"");
    out.print(SUBJECTPARAM);
    out.print("\" size=\"");
    out.print(Integer.toString(mNumberOfColsForTextFields));
    out.print("\" maxlength=\"");
    out.print(MAX_TEXTFIELD_SIZE);
    out.println("\">");
    out.println("</TD></TR>");
    out.println("<TR><TD CLASS=\"MESSAGE\">");
    out.print(mMessageString);
    out.println("&nbsp;:");
    out.println("</TD><TD CLASS=\"FIELD\">");
    out.print("<textarea rows=\"");
    out.print(Integer.toString(mNumberOfRowsForTextArea));
    out.print("\" name=\"");
    out.print(MESSAGEPARAM);
    out.print("\" cols=\"");
    out.print(Integer.toString(mNumberOfColsForTextArea));
    out.println("\" wrap=\"soft\"></textarea>");
    out.println("</TD></TR>");
    out.println("<TR><TD CLASS=\"AUTHOR\">");
    out.print(mAuthorString);
    out.println("&nbsp;:");
    out.println("</TD><TD CLASS=\"AUTHORFIELD\">");
    if(user == null) {
       out.print("<input type=\"TEXT\" value=\"\" name=\"");
       out.print(AUTHORPARAM);
       out.print("\" size=\"");
       out.print(Integer.toString(mNumberOfColsForTextFields));
       out.print("\" maxlength=\"");
       out.print(MAX_TEXTFIELD_SIZE);
       out.println("\">");
    } else {
       if(user.getAutorizationLevel() > 0) {
        if(user.getLogin() != null)
          out.println(user.getLogin());
         out.print("<input type=\"hidden\" value=\"");
         if(user.getLogin() != null)
          out.print(user.getLogin());
         out.print("\" name=\"");
         out.print(AUTHORPARAM);
         out.print("\" size=\"");
         out.print(Integer.toString(mNumberOfColsForTextFields));
         out.print("\" maxlength=\"");
         out.print(MAX_TEXTFIELD_SIZE);
         out.println("\">");
       } else {
         out.print("<input type=\"TEXT\" value=\"");
         if(user.getLogin() != null)
          out.print(user.getLogin());
         out.print("\" name=\"");
         out.print(AUTHORPARAM);
         out.print("\" size=\"");
         out.print(Integer.toString(mNumberOfColsForTextFields));
         out.print("\" maxlength=\"");
         out.print(MAX_TEXTFIELD_SIZE);
         out.println("\">");
       }
    }
    out.println("</TD></TR>");
    out.println("<TR><TD CLASS=\"EMAIL\">");
    out.print(mEmailString);
    out.println("&nbsp;:");
    out.println("</TD><TD CLASS=\"EMAILFIELD\">");
    out.print("<input type=\"TEXT\" value=\"");
    if(user != null)
      if(user.getEmail()!=null)
        out.print(user.getEmail());
    out.print("\" name=\"");
    out.print(EMAILPARAM);
    out.print("\" size=\"");
    out.print(Integer.toString(mNumberOfColsForTextFields));
    out.print("\" maxlength=\"");
    out.print(MAX_TEXTFIELD_SIZE);
    out.println("\">");
    out.println("</TD></TR>");

    out.println("<TR><TD CLASS=\"OPTLINK\">");
    out.print(mOptionalLinkURLString);
    out.println("&nbsp;:");
    out.println("</TD><TD CLASS=\"OPTLINKFIELD\">");
    out.print("<input type=\"TEXT\" value=\"HTTP://\" name=\"");
    out.print(mOptionalLinkURLPARAM);
    out.print("\" size=\"");
    out.print(Integer.toString(mNumberOfColsForTextFields));
    out.print("\" maxlength=\"");
    out.print(MAX_TEXTFIELD_SIZE);
    out.println("\">");
    out.println("</TD></TR>");

    out.println("<TR><TD CLASS=\"LT\">");
    out.print(mLinkTitleString);
    out.println("&nbsp;:");
    out.println("</TD><TD CLASS=\"LTFIELD\">");
    out.print("<input type=\"TEXT\" value=\"\" name=\"");
    out.print(mLinkTitlePARAM);
    out.print("\" size=\"");
    out.print(Integer.toString(mNumberOfColsForTextFields));
    out.print("\" maxlength=\"");
    out.print(MAX_TEXTFIELD_SIZE);
    out.println("\">");
    out.println("</TD></TR>");

    out.println("<TR><TD CLASS=\"IMAGEURL\">");
    out.print(mImageURLString);
    out.println("&nbsp;:");
    out.println("</TD><TD CLASS=\"IMAGEURLFIELD\">");
    out.print("<input type=\"TEXT\" value=\"HTTP://\" name=\"");
    out.print(mImageURLPARAM);
    out.print("\" size=\"");
    out.print(Integer.toString(mNumberOfColsForTextFields));
    out.print("\" maxlength=\"");
    out.print(MAX_TEXTFIELD_SIZE);
    out.println("\">");
    out.println("</TD></TR>");

    out.println("</TABLE>");
    out.println("</DIV>");
    out.println("<DIV CLASS=\"SUBMITBUTTONS\">");
    out.print("<input type=\"hidden\" name=\"");
    out.print(SUBMITMESSAGEPARAM);
    out.println("\" value=\"NADA\">");
    out.print("<input type=\"button\" value=\"");
    if (MessageID == null) {
      out.print(mSubmitString);
      out.print("\" name=\"");
      out.print("toto");
    } else {
      out.print(mReplyButtonString);
      out.print("\" name=\"");
      out.print("toto");
    }
    out.println("\" onClick=\"checkMessage('')\">");
    out.println("</P>");

    out.print("</form>");
    out.println();
  }

  private void deleteForm(PrintWriter out, Forum forum, int mr, User user, String ServletPath) throws IOException {
    out.print("<DIV CLASS=\"DELETEMESSAGE\">");
    out.print("<form action=\"");
    out.print(ServletPath);
    out.println("\" method=\"post\">");
    out.print("<input type=\"hidden\" name=\"");
    out.print(DELETEMESSAGEPARAM);
    out.print("\" value=\"");
    out.print(Integer.toString(mr));
    out.print("\">");
    out.print("<input type=\"hidden\" name=\"");
    out.print(FORUMFILEPARAM);
    out.print("\" value=\"");
    out.print(forum.mConfigFile.getPath());
    out.print("\">");
    out.print("<input type=\"SUBMIT\" value=\"");
    out.print(mDeleteButtonString);
    out.print("\" name=\"");
    out.print("SUBMIT");
    out.print("\">");
    out.println();
    out.print("</P>");
    out.println();
    out.print("</form>");
    out.println();
  }

  private void confirmDeleteForm(PrintWriter out, Forum forum, int mr, User user, String ServletPath) throws IOException {
    out.print("<DIV CLASS=\"CONFIRMDELETEMESSAGE\">");
    out.print("<form action=\"");
    out.print(ServletPath);
    out.println("\" method=\"post\">");
    out.print("<input type=\"hidden\" name=\"");
    out.print(CONFIRMEDDELETEMESSAGEPARAM);
    out.print("\" value=\"");
    out.print(Integer.toString(mr));
    out.print("\">");
    out.print("<input type=\"hidden\" name=\"");
    out.print(FORUMFILEPARAM);
    out.print("\" value=\"");
    out.print(forum.mConfigFile.getPath());
    out.print("\">");
    out.print("<input type=\"SUBMIT\" value=\"");
    out.print(mConfirmDeleteButtonString);
    out.print("\" name=\"");
    out.print("SUBMIT");
    out.println("\">");
    out.println("</form>");
  //  out.println("</TD></TR><TR><TD>");
    out.print("<form action=\"");
    out.print(ServletPath);
    out.println("\" method=\"post\">");
    out.print("<input type=\"hidden\" name=\"");
    out.print(SHOWPARAM);
    out.print("\" value=\"");
    out.print(Integer.toString(mr));
    out.print("\">");
    out.println();
    out.print("<input type=\"hidden\" name=\"");
    out.print(FORUMFILEPARAM);
    out.print("\" value=\"");
    out.print(forum.mConfigFile.getPath());
    out.print("\">");
    out.print("<input type=\"SUBMIT\" value=\"");
    out.print(mCancelDeleteButtonString);
    out.print("\" name=\"");
    out.print("SUBMIT");
    out.println("\">");
    out.println("</form>");
    out.print("</P>");
  }

  public void printResetForm(PrintWriter out, Forum forum, String ServletPath) throws IOException {
    out.print("<DIV CLASS=\"RESET\">");
    out.print("<form action=\"");
    out.print(ServletPath);
    out.println("\" method=\"post\">");
    out.print("<input type=\"hidden\" name=\"");
    out.print(FORUMFILEPARAM);
    out.print("\" value=\"");
    out.print(forum.mConfigFile.getPath());
    out.print("\">");
    out.print("<input type=\"SUBMIT\" value=\"");
    out.print(mResetString);
    out.print("\" name=\"");
    out.print(RESETPARAM);
    out.print("\">");
    out.println();
    out.print("</P>");
    out.println();
    out.println("</form>");
  }

  public void printUserListForm(PrintWriter out, Forum forum, String ServletPath) throws IOException {
    out.print("<DIV CLASS=\"USERLIST\">");
    out.print("<form action=\"");
    out.print(ServletPath);
    out.println("\" method=\"post\">");
    out.print("<input type=\"hidden\" name=\"");
    out.print(FORUMFILEPARAM);
    out.print("\" value=\"");
    out.print(forum.mConfigFile.getPath());
    out.print("\">");
    out.print("<input type=\"SUBMIT\" value=\"");
    out.print(mDisplayUserListString);
    out.print("\" name=\"");
    out.print(DISPLAYUSERLIST);
    out.println("\">");
    out.println("</form>");
    out.println("</DIV>");
  }

  public void printBackToWelcomeURL(PrintWriter out,Forum forum) {
    out.print("<P CLASS=\"BACKTOFORUM\">");
    out.print("<A HREF=\"");
    out.print(forum.mWelcomeURL);
    out.print("\"  CLASS=\"BACKTOFORUM\">");
    out.print(mWelcomePageString);
    out.print("</A>");
    out.println("</P>");
  }

  public void printBackToForum( PrintWriter out, Forum forum, String ServletPath) {
    out.print("<P CLASS=\"BACKTOFORUM\">");
    out.print("<A HREF=\"");
    out.print(ServletPath);
    out.print("?");
    out.print(FORUMFILEPARAM);
    out.print("=");
    out.print(forum.mConfigFile.getPath());
    out.print("\"  CLASS=\"BACKTOFORUM\">");
    out.print(mMainString);
    out.print("</A>");
    out.println("</P>");
  }


  public void showWarningNameInUse(String name, PrintWriter out, Forum forum) {
    printBackToWelcomeURL(out,forum);
    out.println("<P CLASS=\"WARNING\">");
    out.println(mWarningNameInUseString);
    out.println("</P>");
  }

  public void showWarningPasswordsDontMatch(String name, PrintWriter out, Forum forum) {
    printBackToWelcomeURL(out,forum);
    out.println("<P CLASS=\"WARNING\">");
    out.println(mWarningPasswordsDontMatchString);
    out.println("</P>");
  }

  public void showWarningAutorizationRequired(PrintWriter out, Forum forum) {
    printBackToWelcomeURL(out,forum);
    out.println("<P CLASS=\"WARNING\">");
    out.println(mWarningAutorizationRequiredString);
    out.println("</P>");
  }

  public void showWarningCannotChangeAdmin(PrintWriter out, Forum forum) {
    printBackToWelcomeURL(out,forum);
    out.println("<P CLASS=\"WARNING\">");
    out.println(mWarningCannotChangeAdminString);
    out.println("</P>");
  }

  public void printUserList (Forum forum, PrintWriter out, String ServletPath) {
    printBackToForum(out,forum, ServletPath);
    Enumeration enum = forum.mUserList.getUsers();
    out.println("<TABLE BORDER=5>");
    while(enum.hasMoreElements()) {
      User u = (User) enum.nextElement();
      out.println("<TR><TD>");
      if(u.getAutorizationLevel() < ADMIN) {
        out.print("<form action=\"");
        out.print(ServletPath);
        out.println("\" method=\"post\">");
        out.print("<input type=\"hidden\" name=\"");
        out.print(FORUMFILEPARAM);
        out.print("\" value=\"");
        out.print(forum.mConfigFile.getPath());
        out.println("\">");
        out.print("<input type=\"hidden\" name=\"");
        out.print(DISPLAYUSER);
        out.print("\" value=\"");
      }
      if(u.getLogin()!=null)
        out.print(u.getLogin());
      if(u.getAutorizationLevel() < ADMIN) {
        out.print("\">");
        out.print("<input type=\"SUBMIT\" value=\"");
        if(u.getLogin()!=null)
          out.println(u.getLogin());
        out.print("\">");
        out.println("</form>");
      }
      out.println("</TD><TD>");
      out.println("<A HREF=\"mailto:");
      if(u.getEmail() != null)
        out.println(u.getEmail());
      out.println("\">");
      if(u.getEmail() != null)
        out.println(u.getEmail());
      out.println("</A>");
      out.println("</TD><TD>");
      out.println(u.getAutorizationLevel());
      out.println("</TD><TD>");
      if(u.getAutorizationLevel() < ADMIN) {
        out.print("<form action=\"");
        out.print(ServletPath);
        out.println("\" method=\"post\">");
        out.print("<input type=\"hidden\" name=\"");
        out.print(FORUMFILEPARAM);
        out.print("\" value=\"");
        out.print(forum.mConfigFile.getPath());
        out.print("\">");
        out.print("<input type=\"hidden\" name=\"");
        out.print(DELETEUSER);
        out.print("\" value=\"");
        if(u.getLogin()!=null)
          out.print(u.getLogin());
        out.print("\">");
        out.print("<input type=\"SUBMIT\" value=\"");
        out.print(mDeleteUserString);
        out.print("\" onClick=\"return confirm('");
        out.print(mDeleteUserString);
        out.print("')\">");
        out.println("</form>");
      }
      out.println("</TD></TR>");
    }
    out.println("</TABLE>");
    printUserForm (forum, null, out, ServletPath);
  }

  public void printUser (Forum forum, String name, PrintWriter out, String ServletPath) {
    printBackToForum(out,forum, ServletPath);
    printUserForm (forum, forum.mUserList.getUser(name), out, ServletPath);
  }

  public void printUserForm (Forum forum, User u, PrintWriter out, String ServletPath) {
        out.print("<form action=\"");
        out.print(ServletPath);
        out.print("\" method=\"post\">");
        out.println("<TABLE>");
        out.print("<input type=\"hidden\" name=\"");
        out.print(FORUMFILEPARAM);
        out.print("\" value=\"");
        out.print(forum.mConfigFile.getPath());
        out.println("\">");
        if(u!=null) {
          out.print("<input type=\"hidden\" name=\"");
          out.print(OLDUSERNAME);
          out.print("\" value=\"");
          if(u.getLogin()!=null)
            out.print(u.getLogin());
          out.println("\">");
        }
        out.println("<TR><TD>");
        out.println(this.mAuthorString);
        out.println("</TD><TD>");
        out.print("<input type=\"text\" name=\"");
        out.print(NEWUSERNAME);
        out.print("\" value=\"");
        if(u!=null)
          if(u.getLogin()!=null)
            out.print(u.getLogin());
        out.print("\" size=\"");
        out.print(Integer.toString(mNumberOfColsForTextFields));
        out.print("\" maxlength=\"");
        out.print(MAX_TEXTFIELD_SIZE);
        out.println("\">");
        out.println("</TD></TR>");
        out.println("<TR><TD>");
        out.println(mPasswordString);
        out.println("</TD><TD>");
        out.print("<input type=\"text\" name=\"");
        out.print(NEWUSERPASSWORD);
        out.print("\" value=\"");
        if(u!=null)
          if(u.getPassword()!=null)
            if(u.getAutorizationLevel() < ADMIN)
             out.print(u.getPassword());
        out.print("\" size=\"");
        out.print(Integer.toString(mNumberOfColsForTextFields));
        out.print("\" maxlength=\"");
        out.print(MAX_TEXTFIELD_SIZE);
        out.println("\">");
        out.println("</TD></TR>");
        out.println("<TR><TD>");
        out.println(mEmailString);
        out.println("</TD><TD>");
        out.print("<input type=\"text\" name=\"");
        out.print(NEWUSEREMAIL);
        out.print("\" value=\"");
        if(u!=null)
          if(u.getEmail()!=null)
             out.print(u.getEmail());
        out.print("\" size=\"");
        out.print(Integer.toString(mNumberOfColsForTextFields));
        out.print("\" maxlength=\"");
        out.print(MAX_TEXTFIELD_SIZE);
        out.println("\">");
        out.println("</TD></TR>");
        out.println("<TR><TD>");
        out.println("</TD><TD>");
        out.print("<SELECT NAME=\"");
        out.print(NEWUSERLEVEL);
        out.println("\">");
        if(u!=null) {
	      out.print("  <OPTION VALUE=\"");
        out.print(u.getAutorizationLevel());
        out.println("\"> "+u.getAutorizationLevel());
        }
	      out.print("  <OPTION VALUE=\"");
        out.print(IDENTIFIEDGUEST);
        out.println("\"> IDENTIFIEDGUEST ("+IDENTIFIEDGUEST+")");
	      out.print("  <OPTION VALUE=\"");
        out.print(MEMBER);
        out.println("\"> MEMBER ("+MEMBER+")");
	      out.print("  <OPTION VALUE=\"");
        out.print(EXECUTIVEMEMBER);
        out.println("\"> EXECUTIVEMEMBER ("+EXECUTIVEMEMBER+")");
	      out.print("  <OPTION VALUE=\"");
        out.print(VIPMEMBER);
        out.println("\"> VIPMEMBER");
	      out.print("  <OPTION VALUE=\"");
        out.print(ACCOUNTINACTIVE);
        out.println("\"> ACCOUNTINACTIVE ("+ACCOUNTINACTIVE+")");
        out.println("</SELECT>");
        out.println("</TD></TR>");
        out.println("</TABLE>");
        out.print("<input type=\"SUBMIT\" value=\"");
        if(u == null)
          out.print(mAddUserString);
        else
          out.print(mChangeUserString);
        out.print("\" name=\"");
        if(u == null)
          out.print(ADDUSER);
        else
          out.print(CHANGEUSER);
        out.println("\">");

        out.println("</form>");
  }


}
