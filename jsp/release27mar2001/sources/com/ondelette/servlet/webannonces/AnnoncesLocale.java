

package com.ondelette.servlet.webannonces;

import java.io.*;
import java.util.*;
import java.text.*;
import com.ondelette.servlet.*;

public class AnnoncesLocale implements ParameterNames {

    String mSubjectString = "Sujet";

    String mAuthorString = "Nom";
    String mEmailString = "Courriel";
    String mPasswordString = "Mot de passe";
    String mURLString = "URL";

    String mPhoneNumberString = "Numéro de téléphone";
    String mAddressString = "Adresse";
    String mAnnonceString = "Annonce";

    String mDeleteButtonString = "Effacer";
    String mPostButtonString = "Soumettre mon annonce";

    String mDateString = "Date";

    String mLocaleLanguage = "fr";
    String mLocaleCountry ="CA";
    String mLocaleVariant ="";
	  Locale mCurrentLocale;
    DateFormat mDateFormat;
    int mNumberOfRowsForTextArea = 10;
    int mNumberOfColsForTextArea = 37;
    int mNumberOfColsForTextFields = 40;

    int mBorderSize = 5;
    int mTableWidth = 400;

  public AnnoncesLocale(File AnnoncesLocaleFile) {
    mCurrentLocale = new Locale(mLocaleLanguage,mLocaleCountry,mLocaleVariant);
    mDateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.SHORT,mCurrentLocale);
  }


  public void print(Annonce a, PrintWriter out) {
    out.println("<DIV ALIGN=\"CENTER\">");
    out.print("<TABLE BORDER=\"");
    out.print(mBorderSize);
    out.print("\" WIDTH=\"");
    out.print(mTableWidth);
    out.print("\">");
    out.println("<TR><TD WIDTH=20%>");
    out.println(mSubjectString);
    out.println("</TD><TD WIDTH=80%>");
    if(a.getTitle()!=null) {
      if(a.getURL() != null) {
        if(a.getURL().length() > 0) {
          out.print("<A HREF=\"");
          out.print(a.getURL());
          out.print("\">");
          out.println(a.getTitle());
          out.println("</A>");
        }  else
          out.println(a.getTitle());
      } else
        out.println(a.getTitle());
    }
    out.println("</TD></TR>");
    out.println("<TR><TD>");
    out.println(mAuthorString);
    out.println("</TD><TD>");
    if(a.getAuthor()!=null) {
      if(a.getEmail() != null) {
        if(a.getEmail().length() > 0 ) {
          out.print("<A HREF=\"mailto:");
          out.print(a.getEmail());
          out.print("\">");
          out.println(a.getAuthor());
          out.println("</A>");
        } else
          out.println(a.getAuthor());
      } else
        out.println(a.getAuthor());
    }
    out.println("</TD></TR>");
    out.println("<TR><TD>");
    out.println(mDateString);
    out.println("</TD><TD>");

    out.println(mDateFormat.format(a.getDate()));

    out.println("</TD></TR>");

    out.println("<TR><TD>");
    out.println(mPhoneNumberString);
    out.println("</TD><TD>");
    if(a.getPhoneNumber()!=null)
      out.println(a.getPhoneNumber());
    out.println("</TD></TR>");
    out.println("<TR><TD>");
    out.println(mAddressString);
    out.println("</TD><TD>");
    if(a.getAddress() != null)
      out.println(a.getAddress());
    out.println("</TD></TR>");
    out.println("<TR><TD>");
    out.println(mAnnonceString);
    out.println("</TD><TD>");
    out.println("<P>");
    if(a.getContent()!=null)
      out.println(a.getContent());
    out.println("</TD></TR>");
    out.println("</TABLE>");
    out.println("</DIV>");

  }

  public void printHeader (PrintWriter out) throws IOException {
    out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\"");
    out.println("\"http://www.w3.org/TR/REC-html40/loose.dtd\">");
    out.println("<HTML>");
    out.println("<HEAD>");
    out.print("<TITLE>");
    out.print("web.ondelette.servlet.webannonces");
    out.println("</TITLE>");
    out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">");
    out.println("<meta http-equiv=\"Content-Style-Type\" content=\"text/css\">");
    out.print("<META HTTP-EQUIV=\"Content-Language\" CONTENT=\"");
    out.print(mLocaleLanguage);
    out.println("\">");
    out.print("<META NAME=\"Copyright\" CONTENT=\"");
    out.print(ServletCopyright.getCopyright());
    out.println("\">");
    out.println("</HEAD>");
    out.println("<BODY>");

  }


  public void print(AnnoncesFolder af, PrintWriter out) {
    Enumeration enum = af.getAnnonces();
    while(enum.hasMoreElements()) {
      Annonce a = (Annonce) enum.nextElement();
      print(a,out);
    }
  }

  public void printAdmin(AnnoncesFolder af, PrintWriter out) {
    Enumeration enum = af.getAnnonces();
    while(enum.hasMoreElements()) {
      Annonce a = (Annonce) enum.nextElement();
      print(a,out);
      printDeleteForm(af, a,out);
    }
  }

  public void printDeleteForm(AnnoncesFolder af, Annonce a, PrintWriter out) {
    out.print("<form method=\"post\">");
    out.print("<input type=\"hidden\" name=\"");
    out.print(DELETE);
    out.print("\" value=\"");
    out.print(a.getID());
    out.print("\">");
    out.print("<input type=\"hidden\" name=\"");
    out.print(ANNONCEFILEPARAM);
    out.print("\" value=\"");
    out.print(af.mFolderfile.getPath());
    out.println("\">");
    out.print("<input type=\"SUBMIT\" value=\"");
    out.print(mDeleteButtonString);
    out.print("\" name=\"");
    out.print("SUBMIT");
    out.print("\">");
    out.println("</form>");
  }


  public void printForm(AnnoncesFolder af,PrintWriter out) throws IOException {
    out.print("<P>");
    out.print(mPostButtonString);
    out.println("&nbsp;:</P>");
    out.print("<form  method=\"post\">");
    out.print("<input type=\"hidden\" name=\"");
    out.print(ANNONCEFILEPARAM);
    out.print("\" value=\"");
    out.print(af.mFolderfile.getPath());
    out.println("\">");
    out.println("<TABLE>");
    out.println("<TR><TD>");
    out.println(mSubjectString);
    out.println("</TD><TD>");

    out.print("<input type=\"TEXT\" value=\"");
    out.print("\" name=\"");
    out.print(SUBJECTPARAM);
    out.print("\" size=\"");
    out.print(Integer.toString(mNumberOfColsForTextFields));
    out.println("\" maxlength=\"80\">");


    out.println("</TD></TR>");


    out.println("<TR><TD>");
    out.println(mAuthorString);
    out.println("</TD><TD>");

    out.print("<input type=\"TEXT\" value=\"");
    out.print("\" name=\"");
    out.print(AUTHORPARAM);
    out.print("\" size=\"");
    out.print(Integer.toString(mNumberOfColsForTextFields));
    out.println("\" maxlength=\"80\">");

    out.println("</TD></TR>");

    out.println("<TR><TD>");
    out.println(mEmailString);
    out.println("</TD><TD>");

    out.print("<input type=\"TEXT\" value=\"");
    out.print("\" name=\"");
    out.print(EMAILPARAM);
    out.print("\" size=\"");
    out.print(Integer.toString(mNumberOfColsForTextFields));
    out.println("\" maxlength=\"80\">");

    out.println("</TD></TR>");



    out.println("<TR><TD>");
    out.println(mURLString);
    out.println("</TD><TD>");

    out.print("<input type=\"TEXT\" value=\"");
    out.print("\" name=\"");
    out.print(URLPARAM);
    out.print("\" size=\"");
    out.print(Integer.toString(mNumberOfColsForTextFields));
    out.println("\" maxlength=\"80\">");

    out.println("</TD></TR>");
    out.println("<TR><TD>");
    out.println(mPhoneNumberString);
    out.println("</TD><TD>");


    out.print("<input type=\"TEXT\" value=\"");
    out.print("\" name=\"");
    out.print(PHONENUMBERPARAM);
    out.print("\" size=\"");
    out.print(Integer.toString(mNumberOfColsForTextFields));
    out.println("\" maxlength=\"80\">");


    out.println("</TD></TR>");
    out.println("<TR><TD>");
    out.println(mAddressString);
    out.println("</TD><TD>");

    out.print("<input type=\"TEXT\" value=\"");
    out.print("\" name=\"");
    out.print(ADDRESSPARAM);
    out.print("\" size=\"");
    out.print(Integer.toString(mNumberOfColsForTextFields));
    out.println("\" maxlength=\"256\">");

    out.println("</TD></TR>");
    out.println("<TR><TD>");
    out.println(mAnnonceString);
    out.println("</TD><TD>");

    out.print("<textarea rows=\"");
    out.print(Integer.toString(mNumberOfRowsForTextArea));
    out.print("\" name=\"");
    out.print(ANNONCEPARAM);
    out.print("\" cols=\"");
    out.print(Integer.toString(mNumberOfColsForTextArea));
    out.println("\" wrap=\"soft\"></textarea>");

    out.println("</TD></TR>");
    out.println("</TABLE>");

    out.print("<input type=\"SUBMIT\" value=\"");
    out.print(mPostButtonString);
    out.print("\" name=\"");
    out.print("SUBMIT");
    out.print("\">");
    out.println("</form>");

  }

}