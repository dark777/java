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
import java.net.*;
import java.io.*;
import java.text.*;
import javax.servlet.*;

final public class Message implements AutorizationConstants, Serializable {

  private Date mPostedDate;
  private String mSubjectString;
  private String mMessageString;
  private String mAuthorString;
  private String mAuthorEmailString;
  private int mInReplyToID = -1;
  private int mAutorizationLevel = NORMAL;
  private Vector mReplies = new Vector();
  private int mNumberOfLines = 0;
  private int mNumberOfBytes = 0;


  // the next line might seem like a mistake but it is not...
  // we use US English as the default for writing to disk...
  // saved message should not depend on the server locale...
  private static DateFormat mDateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL,new Locale("en","us"));
  private static DateFormat mDateFormatSecondary = DateFormat.getDateInstance(DateFormat.FULL,new Locale("en","us"));


  private Message() {
  }

  public Enumeration getReplies() {
    return(mReplies.elements());
  }

  public boolean contains(MessageReference mr) {
    return(mReplies.contains(mr));
  }

  public Message(String AuthorString, String AuthorEmail, String SubjectString, String MessageString) {
      if(AuthorString != null) {
        mAuthorString = HTMLUtil.makeStringHTMLSafe(AuthorString.trim());
        if(mAuthorString.length() == 0)
          mAuthorString = null;
      }
      if(SubjectString != null) {
        mSubjectString = HTMLUtil.makeStringHTMLSafe(SubjectString.trim());
        if(mSubjectString.length() == 0)
          mSubjectString = null;
      }
      if(AuthorEmail != null) {
        mAuthorEmailString = HTMLUtil.makeStringHTMLSafe(AuthorEmail.trim());
        if(mAuthorEmailString.length() == 0)
          mAuthorEmailString = null;
      }
      if(MessageString != null) {
        mMessageString = MessageString.trim();
        if(mMessageString.length() == 0)
          mMessageString = null;
      }
    long time = System.currentTimeMillis();
    mPostedDate = new Date(time);
    mAutorizationLevel = NORMAL;
    mNumberOfLines = HTMLUtil.getNumberOfLines(mMessageString);
    if(mMessageString != null)
      mNumberOfBytes = mMessageString.length();
    else
      mNumberOfBytes = 0;
  }

  public void setInReplyTo(int ID) {
    mInReplyToID = ID;
  }

  public int getInReplyTo() {
    return(mInReplyToID);
  }

  public Date getDate() {
    return(mPostedDate);
  }

  public void setDate(Date date) {
    mPostedDate = date;
  }

  public void setAutorizationLevel (int level) {
    mAutorizationLevel = level;
  }

  public boolean hasBeenDeleted() {
    return (mAutorizationLevel == DELETED);
  }


  public int getAutorizationLevel () {
    return( mAutorizationLevel );
  }

  public void addReply(MessageReference mr) {
    mReplies.insertElementAt(mr,0);
  }

  public boolean removeReply(MessageReference mr) {
    return(mReplies.removeElement(mr));
  }

  public void setSubject (String Subject) {
    mSubjectString = HTMLUtil.makeStringHTMLSafe(Subject.trim());
    if(mSubjectString.length() == 0)
      mSubjectString = null;
  }

  public void setMessage (String message) {
    mMessageString = message.trim();
    if(mMessageString.length() == 0)
      mMessageString = null;
    mNumberOfLines = HTMLUtil.getNumberOfLines(message);
    if (mMessageString != null)
      mNumberOfBytes = message.length();
    else
      mNumberOfBytes = 0;
  }

  public void setAuthor (String Author) {
    mAuthorString = HTMLUtil.makeStringHTMLSafe(Author.trim());
    if(mAuthorString.length() == 0)
      mAuthorString = null;
  }

  public void setAuthorEmail (String AuthorEmail) {
    mAuthorEmailString = HTMLUtil.makeStringHTMLSafe(AuthorEmail.trim());
    if(mAuthorEmailString.length() == 0)
      mAuthorEmailString = null;
  }


  public int getNumberOfLines() {
    return(mNumberOfLines);
  }

  public int getNumberOfBytes() {
    return(mNumberOfBytes);
  }


  public String getSubject() {
    return(mSubjectString);
  }

  public String getMessage() {
    return(mMessageString);
  }

  public String getFormattedMessage() {
  	return(HTMLUtil.replaceLinesWithParagraphs(mMessageString));
  }

  public String getAuthor() {
    return(mAuthorString);
  }

  public String getAuthorEmail() {
    return(mAuthorEmailString);
  }


  //
  // résumé des réponses
  //
  public void writeRepliesSummaryHTML(ForumLocale locale, Forum forum ,PrintWriter out, int selfmr, String ServletPath) throws IOException {
    Enumeration enum = mReplies.elements();
    if(!enum.hasMoreElements())
      return;
    out.print("<UL CLASS=\"REPLIES\">");
    while(enum.hasMoreElements()) {
      MessageReference mr = (MessageReference) enum.nextElement();
      if (mr.getID() != selfmr) {// on évite ainsi le problème de la boucle infinie
      // c'est en fait le seul instant où le message peut connaître son numéro
      // et c'est ainsi qu'on le veut (de manière à découpler les numéros des
      // messages
        out.print("<LI CLASS=\"REPLIES\">");
        locale.printSummary(mr.getID(), forum, out, ServletPath);// attention, ici on pourrait tomber
        //dans une boucle infinie
        out.print("</LI>");
      }
    }
    out.print("</UL>");
  }

 public void write(OutputStream out)
     throws IOException  {
     PrintWriter writer = new PrintWriter(out);
     writer.println("<MESSAGE>");
     if (this.mAutorizationLevel != NORMAL) {
      writer.println("<AUTHORIZATION>");
      writer.println(Integer.toString(this.mAutorizationLevel));
      writer.println("</AUTHORIZATION>");
     }
     writer.println("<POSTED>");
     writer.println(mDateFormat.format(mPostedDate).toString());
     writer.println("</POSTED>");
     if(mSubjectString != null) {
      writer.println("<SUBJECT>");
      writer.println(mSubjectString);
      writer.println("</SUBJECT>");
     }
     if (mMessageString != null) {
      writer.println("<CONTENT>");
      writer.println(HTMLUtil.makeStringHTMLSafe(mMessageString));
      writer.println("</CONTENT>");
     }
     if(mInReplyToID >= 0) {
      writer.println("<REPLYTOID>");
      writer.println(mInReplyToID);
      writer.println("</REPLYTOID>");
     }
     if( mAuthorString != null) {
      writer.println("<AUTHOR>");
      writer.println(mAuthorString);
      writer.println("</AUTHOR>");
     }
     if(mAuthorEmailString!=null) {
      writer.println("<EMAIL>");
      writer.println(mAuthorEmailString);
      writer.println("</EMAIL>");
     }
	 writer.flush();
     if(mReplies.size() > 0) {
      writer.flush();
      Enumeration enum = mReplies.elements();
      while (enum.hasMoreElements()) {
        final MessageReference mr = (MessageReference) enum.nextElement();
        mr.write(out);
      }
     }
     writer.println("</MESSAGE>");
     writer.flush();
 }


 private static Message parseMessage(InputStream in) throws IOException {
    Message message = new Message();
    String header;
    while(in.available()> 0) {
      header = HTMLUtil.readString(in);

      if (header.equals("/MESSAGE"))
        break;
      if (header.equals("POSTED")) {
        String DateString = HTMLUtil.readString(in).trim();
        try {
          message.mPostedDate = mDateFormat.parse(DateString);
        } catch (ParseException pe) {
          try {
            message.mPostedDate = mDateFormatSecondary.parse(DateString);
          } catch (ParseException pe2) {
            message.mPostedDate = new Date();
          }

        }
        HTMLUtil.readString(in);
      } else if (header.equals("REPLYTOID")) {
        message.mInReplyToID = Integer.parseInt(HTMLUtil.readString(in).trim());
        HTMLUtil.readString(in);
      } else if (header.equals("SUBJECT")) {
        message.mSubjectString = HTMLUtil.readString(in);
        HTMLUtil.readString(in);
      }
      else if (header.equals("CONTENT")) {
		    message.mMessageString =  HTMLUtil.recoverHTML(HTMLUtil.readString(in).trim());
        HTMLUtil.readString(in);
      }
      else if (header.equals("AUTHOR")) {
        message.mAuthorString = HTMLUtil.readString(in).trim();
        HTMLUtil.readString(in);
      }
      else if (header.equals("EMAIL")) {
        message.mAuthorEmailString = HTMLUtil.readString(in).trim();
        HTMLUtil.readString(in);
      }
      else if (header.equals("MREF")) {
        message.mReplies.addElement(new MessageReference(HTMLUtil.readString(in)));
        HTMLUtil.readString(in);
      } else if (header.equals("AUTHORIZATION")) {
        message.mAutorizationLevel = Integer.parseInt(HTMLUtil.readString(in).trim());
        HTMLUtil.readString(in);
      }
   }
   if(message.mPostedDate == null)
     message.mPostedDate = new Date();//en cas de corruption
   return(message);
 }

 public static Message read(InputStream in)
     throws IOException {
     while(in.available() > 0) {
        String buf = HTMLUtil.readString(in);
        if(buf.equals("MESSAGE")) {
          Message message = null;
          message = parseMessage(in);
          return (message);
        }
     }
     return(null);
 }

 public static void main(String[] t) throws IOException {
  Message m = new Message("dan","dan@t","tata","message");
  FileOutputStream fos = new FileOutputStream(t[0]);
  m.write(fos);
  fos.close();
  FileInputStream fis = new FileInputStream(t[0]);
  m = Message.parseMessage(fis);
  fis.close();
  System.out.println(m.getAuthor());
 }

  public String toString () {
    if ((mSubjectString != null) && (mAuthorString != null))
      return(mSubjectString.trim() + " - "+mAuthorString);
    else if (mSubjectString != null)
      return(mSubjectString.trim());
    else if (mAuthorString != null)
      return("("+mAuthorString+")");
    return("?");
  }

}
