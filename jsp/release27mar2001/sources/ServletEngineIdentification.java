
/**
 * Title:        Forum Web<p>
 * Description:  Un forum pour le Web~ndestiné à un usage professionnel.<p>
 * Copyright:    Copyright (c) 1998<p>
 * Company:      Ondelette<p>
 * @author Daniel Lemire
 * @version
 */
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class ServletEngineIdentification extends HttpServlet {

  String mJavaVersion;
  String mJavaVendor;
  String mUserDir;
  String mServerInfo;
  int mMajorVersion;
  int mMinorVersion;

  public void init(ServletConfig config)
    throws ServletException {
    super.init(config);
    ServletContext sc = config.getServletContext();
    try {
      mMajorVersion = sc.getMajorVersion();
    } catch (Exception e) {}
    try {
      mMinorVersion = sc.getMinorVersion();
    } catch (Exception e) {}
    try {
      mServerInfo = sc.getServerInfo();
    } catch (Exception e) {}

    mJavaVendor = System.getProperty("java.vendor");
    mJavaVersion = System.getProperty("java.version");
    mUserDir = System.getProperty("user.dir");
  }

  public void doGet(HttpServletRequest request,
          HttpServletResponse response)
          throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<HTML><BODY>");
        out.println("<PRE>");
        try {
          out.println("Servlet engine major version "+ mMajorVersion);
        } catch (Exception e) {
          try {
            out.println("Servlet engine major version "+ this.getServletContext().getMajorVersion());
          } catch (Exception e2) {
            out.println("Servlet engine major version 'Unknown'");
          }
        }
        try {
          out.println("Servlet engine minor version "+ mMinorVersion);
        } catch (Exception e) {
          try {
            out.println("Servlet engine minor version "+ this.getServletContext().getMinorVersion());
          } catch (Exception e2) {
            out.println("Servlet engine minor version 'Unknown'");
          }
        }
        try {
          out.println("Server Info "+mServerInfo);
        } catch (Exception e) {
          try {
            this.getServletContext().getServerInfo();
          } catch (Exception e2) {
            out.println("Server Info 'Unknown'");
          }
        }
        try {
        out.println("Java vendor "+mJavaVendor);
        out.println("Java version "+mJavaVersion);
        out.println("User dir "+mUserDir);
        } catch (Exception e) {
          e.printStackTrace(out);
        }
        out.println("</PRE>");
        out.println("</BODY></HTML>");
        out.flush();
  }

}
