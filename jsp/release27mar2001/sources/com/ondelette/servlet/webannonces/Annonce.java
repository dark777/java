package com.ondelette.servlet.webannonces;

import java.util.*;
import java.text.*;
import java.io.*;
import com.ondelette.servlet.webforum.*;
import com.ondelette.servlet.*;

public class Annonce {

  private Date mDate;
  private String mTitle = null;
  private String mAuthor = null;
  private String mEmail = null;
  private String mPhoneNumber = null;
  private String mAddress = null;
  private String mMessage = null;
  private String mURL = null;
  private int mID = -1;


  // the next line might seem like a mistake but it is not...
  // we use US English as the default for writing to disk...
  // saved message should not depend on the server locale...
  private static DateFormat mDateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL,new Locale("en","us"));
  private static DateFormat mDateFormatSecondary = DateFormat.getDateInstance(DateFormat.FULL,new Locale("en","us"));

  private Annonce() {}

  public Annonce(String Title, String Author, String Email, String PhoneNumber, String Address, String Message, String URL) {
    if(Title != null) {
      mTitle = HTMLUtil.makeStringHTMLSafe(Title.trim());
      if(mTitle.length() == 0)
        mTitle = null;
    }
    if(Author != null)  {
      mAuthor = HTMLUtil.makeStringHTMLSafe(Author.trim());
    }
    if(Email != null) {
      mEmail = HTMLUtil.makeStringHTMLSafe(Email.trim());
    }
    if(PhoneNumber != null) {
      mPhoneNumber = HTMLUtil.makeStringHTMLSafe(PhoneNumber.trim());
    }
    if(Address != null) {
      mAddress = HTMLUtil.makeStringHTMLSafe(Address.trim());
    }
    if(Message != null) {
      mMessage = HTMLUtil.makeStringHTMLSafe(Message.trim());
    }
    if (URL != null) {
      mURL = HTMLUtil.makeStringHTMLSafe(URL.trim());
    }
    mDate = new Date();
  }

  public String getContent() {
    return(mMessage);
  }

  public String getTitle() {
    return(mTitle);
  }

  public String getAuthor() {
    return(mAuthor);
  }

  public String getEmail() {
    return(mEmail);
  }

  public String getPhoneNumber() {
    return(mPhoneNumber);
  }

  public String getAddress() {
    return(mAddress);
  }

  public Date getDate() {
    return(mDate);
  }

  public int getID() {
    return(mID);
  }

  public String getURL() {
    return(mURL);
  }

  public void setID(int id) {
    mID = id;
  }

 public void write(OutputStream out)
     throws IOException  {
     PrintWriter writer = new PrintWriter(out);
     writer.println("<ANNONCE>");
     writer.println("<POSTED>");
     writer.println(mDateFormat.format(mDate).toString());
     writer.println("</POSTED>");
     if(mTitle != null) {
      writer.println("<TITLE>");
      writer.println(mTitle);
      writer.println("</TITLE>");
     }
     if(mAuthor != null) {
      writer.println("<AUTHOR>");
      writer.println(mAuthor);
      writer.println("</AUTHOR>");
     }
     if(mEmail != null) {
      writer.println("<EMAIL>");
      writer.println(mEmail);
      writer.println("</EMAIL>");
     }
     if(mPhoneNumber != null) {
      writer.println("<PHONENUMBER>");
      writer.println(mPhoneNumber);
      writer.println("</PHONENUMBER>");
     }
     if(mAddress != null) {
      writer.println("<ADDRESS>");
      writer.println(mAddress);
      writer.println("</ADDRESS>");
     }
     if(mURL != null) {
      writer.println("<URL>");
      writer.println(mURL);
      writer.println("</URL>");
     }
     if(mMessage != null) {
      writer.println("<CONTENT>");
      writer.println(mMessage);
      writer.println("</CONTENT>");
     }
     writer.println("<ID>");
     writer.println(mID);
     writer.println("</ID>");
     writer.println("</ANNONCE>");
     writer.flush();
 }


 private static Annonce parseAnnonce(InputStream in) throws IOException {
    Annonce annonce = new Annonce();
    String header;
    while(in.available()> 0) {
      header = HTMLUtil.readString(in);

      if (header.equals("/ANNONCE"))
        break;
      if (header.equals("POSTED")) {
        String DateString = HTMLUtil.readString(in).trim();
        try {
          annonce.mDate = mDateFormat.parse(DateString);
        } catch (ParseException pe) {
          try {
            annonce.mDate = mDateFormatSecondary.parse(DateString);
          } catch (ParseException pe2) {
            annonce.mDate = new Date();
          }

        }
        HTMLUtil.readString(in);
      } else if (header.equals("TITLE")) {
        annonce.mTitle = HTMLUtil.readString(in).trim();
        HTMLUtil.readString(in);
      } else if (header.equals("AUTHOR")) {
        annonce.mAuthor = HTMLUtil.readString(in).trim();
        HTMLUtil.readString(in);
      } else if (header.equals("EMAIL")) {
        annonce.mEmail = HTMLUtil.readString(in).trim();
        HTMLUtil.readString(in);
      } else if (header.equals("PHONENUMBER")) {
        annonce.mPhoneNumber = HTMLUtil.readString(in).trim();
        HTMLUtil.readString(in);
      } else if (header.equals("URL")) {
        annonce.mURL = HTMLUtil.readString(in).trim();
        HTMLUtil.readString(in);
      } else if (header.equals("ADDRESS")) {
        annonce.mAddress = HTMLUtil.readString(in).trim();
        HTMLUtil.readString(in);
      } else if (header.equals("ID")) {
        annonce.mID = Integer.parseInt(HTMLUtil.readString(in).trim());
        HTMLUtil.readString(in);
      } else if (header.equals("CONTENT")) {
        annonce.mMessage = HTMLUtil.readString(in).trim();
        HTMLUtil.readString(in);
      }
   }
   if(annonce.mDate==null)
    annonce.mDate = new Date();
   return(annonce);
 }

 public static Annonce read(InputStream in)
     throws IOException {
     while(in.available() > 0) {
        String buf = HTMLUtil.readString(in);
        if(buf.equals("ANNONCE")) {
          Annonce annonce = null;
          annonce = parseAnnonce(in);
          return (annonce);
        }
     }
     return(null);
 }



}