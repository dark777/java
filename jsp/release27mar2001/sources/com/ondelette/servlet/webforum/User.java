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

public class User implements AutorizationConstants, Serializable{

  private int mAutorizationLevel = ILLEGAL;
  private String mLogin = null;
  private String mPassword = null;
  private String mEmail = null;

/*
  private int mBonusPoints;
  private String mCountry;
  private String mPostalCode;
  private String mStreetAddress;
  private String mCity;
  private String mAge;
  private String mOccupation;
  private String mTitle;
  private Date mLastLogin;
  private boolean mIsMale;
  private String mUrl;
  private String mImageUrl;
  private String mDescription;
  */

  public User(String login,String password, String email,int level) {
    mAutorizationLevel = level;
    if(login != null)
      mLogin =  HTMLUtil.makeStringHTMLSafe(login.trim());
    if(password != null)
      mPassword = HTMLUtil.makeStringHTMLSafe(password.trim());
    if(email != null)
      mEmail = HTMLUtil.makeStringHTMLSafe(email.trim());
  }

  public boolean equals (Object o ) {
    if (o instanceof User) {
      User u = (User) o;

      return((areEquals(u.mEmail, mEmail))
        && (areEquals(u.mLogin, mLogin))
        && (areEquals(u.mPassword, mPassword))
        && (u.mAutorizationLevel == mAutorizationLevel));
    }
    return(false);
  }

  private boolean areEquals(String s1, String s2) {
    if(s1 == null) {
      if(s2 == null)
        return(true);
      return(false);
    }
    if(s2 == null)
      return(false);
    return(s1.equals(s2));
  }

  public void setLogin(String login) {
    if(login!=null)
      mLogin = HTMLUtil.makeStringHTMLSafe(login.trim());
  }

  public void setEmail(String email) {
    if(email!=null)
      mEmail = HTMLUtil.makeStringHTMLSafe(email.trim());
  }

  private User() {}

  public String getLogin() {
    return(mLogin);
  }
  public String getEmail() {
    return(mEmail);
  }

  public int getAutorizationLevel() {
    return(mAutorizationLevel);
  }

  protected String getPassword() {
    return(mPassword);
  }

  public boolean checkPassword(String password) {
    if(mPassword == null)
      return(true);
    if((password == null) && (mPassword.length() == 0))
      return(true);
    if(mPassword.equals(password))
      return(true);
    if(mPassword.equals(password.trim()))
      return(true);
    return(false);
  }



  public static User read(InputStream in) throws IOException {
     while(in.available() > 0) {
      String buf = HTMLUtil.readString(in);
        if(buf.equals("USER")) {
          User user = null;
          user = parseUser(in);
          return (user);
        }
     }
     return(null);
  }


  public void write(OutputStream out) throws IOException {
      PrintWriter writer = new PrintWriter(out);
      writer.print("<USER>");
      if(mLogin!=null) {
        writer.print("<LOGIN>");
        writer.print(mLogin);
        writer.print("</LOGIN>");
      }
      if(mPassword!=null) {
        writer.print("<PASSWORD>");
        writer.print(mPassword);
        writer.print("</PASSWORD>");
      }
      if(mEmail!=null) {
        writer.print("<EMAIL>");
        writer.print(mEmail);
        writer.print("</EMAIL>");
      }
      writer.print("<LEVEL>");
      writer.print(Integer.toString(mAutorizationLevel));
      writer.print("</LEVEL>");
      writer.print("</USER>");
      writer.flush();
  }

 private static User parseUser(InputStream in) throws IOException {
    User user = new User();
    String header;
    while(in.available()> 0) {
      header = HTMLUtil.readString(in);
      if (header.equals("/USER"))
        break;
      if (header.equals("LOGIN")) {
        user.mLogin = HTMLUtil.readString(in).trim();
        HTMLUtil.readString(in);
      } else if (header.equals("PASSWORD")) {
        user.mPassword = HTMLUtil.readString(in).trim();
        HTMLUtil.readString(in);
      } else if(header.equals("EMAIL")) {
        user.mEmail = HTMLUtil.readString(in).trim();
        HTMLUtil.readString(in);
      } else if(header.equals("LEVEL")) {
        user.mAutorizationLevel = Integer.parseInt(HTMLUtil.readString(in).trim());
        HTMLUtil.readString(in);
      }
   }
   if ((user.mPassword == null) || (user.mLogin == null) || (user.mAutorizationLevel == ILLEGAL))
    user = null;
   return(user);
 }

}