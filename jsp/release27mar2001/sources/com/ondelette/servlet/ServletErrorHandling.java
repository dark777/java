

package com.ondelette.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class ServletErrorHandling {

  private final static String mEnglishColor = "<FONT COLOR=\"RED\">";
  private final static String mFrenchColor = "<FONT COLOR=\"BLUE\">";


  public static void printErrorMessage(HttpServletRequest request,
          HttpServletResponse response, Exception e)
          throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println("<HTML><BODY>");
    out.println("<H1>"+mFrenchColor+"Rapport d'erreur</FONT>&nbsp;/&nbsp;"+mEnglishColor+"Error report</FONT></H1>");
    Enumeration enum = request.getParameterNames();
    if(enum.hasMoreElements()) {
      out.println("<HR>");
      out.println("<PRE>");
      out.println("<H2>"+mFrenchColor+"Voici les paramètres définis</FONT> / "+mEnglishColor+"Here are the defined parameters</FONT></H2>");
      while(enum.hasMoreElements()) {
        String param =(String)enum.nextElement();
        out.print(param +" = ");
        out.println(request.getParameter(param));
      }
      out.println("</PRE>");
      out.println("<HR>");
    }
    if(e!=null) {
      out.println("<PRE>");
      out.println(mFrenchColor+"Condition avec exception</FONT>&nbsp;/&nbsp;"+mEnglishColor+"Exception thrown</FONT>");
      e.printStackTrace(out);
      out.println("</PRE>");
    } else {
      out.println("<P>"+mFrenchColor+"Erreur sans exception</FONT>&nbsp;/&nbsp;"+mEnglishColor+"No exception thrown</FONT>");
      out.println("<P>"+mFrenchColor+"Vous n'avez sans doute pas indiqué de forum pour cette servlet. L'URL utilisé n'est pas accessible directement et certains paramètres cachés sont manquants.</FONT>");
      out.println("<P>"+mEnglishColor+"You probably haven't specified a forum for this servlet. This URL cannot be called directly, some hidden parameters are required.</FONT>");
    }
    out.println("<P>"+mFrenchColor+"Veuillez faire rapport du problème au besoin en donnant ces quelques informations.</FONT>");
    out.println("<P>"+mEnglishColor+"Please report this problem if necessary with the above information.</FONT>");
    ServletCopyright.printFooter (out);
    out.flush();
  }

}