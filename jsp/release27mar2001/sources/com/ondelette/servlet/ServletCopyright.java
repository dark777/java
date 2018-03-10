

package com.ondelette.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public final class ServletCopyright {

  private static final String mCopyrigthURL = "http://www.ondelette.com/";
  private static final String mCopyrightString = "&copy; 2000, Daniel Lemire";
  private static final String mVersion = "version 0.34.001";

  public static String getCopyright() {
    return(mCopyrightString);
  }

  public static void printFooter (PrintWriter out) throws IOException {
    out.print("<HR><P CLASS=\"FOOTER\"><A HREF=\"");
    out.print(mCopyrigthURL);
    out.print("\">");
    out.print(mCopyrightString);
    out.print("</A> ");
    out.print(mVersion);
    out.println("</P>");
    out.println("</BODY></HTML>");
  }

}
