
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class ServletWriteTest extends HttpServlet {
  String mServerInfo;
  String mJavaVersion;
  String mJavaVendor;

  public void init(ServletConfig config)
    throws ServletException {
    super.init(config);
    try {
      ServletContext sc = config.getServletContext();
      //mMajorVersion = sc.getMajorVersion();
      //mMinorVersion = sc.getMinorVersion();
      mServerInfo = sc.getServerInfo();
      mJavaVendor = System.getProperty("java.vendor");
      mJavaVersion = System.getProperty("java.version");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void doGet(HttpServletRequest request,
          HttpServletResponse response)
          throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<HTML><BODY>");
        out.println("<PRE>");
        out.println(" server = "+mServerInfo);
        out.println(" java.version = "+mJavaVersion);
        out.println(" java.vendor = "+mJavaVendor);
        out.println("writing to disk...");
        try {
          PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream ("/usr/local/httpd/other_homes/emedicalgate_home/deleteme.txt")));
          pw.println("hello");
          pw.println((new Date()).toString());
          pw.flush();
          pw.close();
        } catch (Exception e) {
          e.printStackTrace(out);
        }
        out.println("writing to disk...done");
        out.println("writing to disk...");
        try {
          PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream ("/home/lemire/deleteme.txt")));
          pw.println("hello");
          pw.println((new Date()).toString());
          pw.flush();
          pw.close();
        } catch (Exception e) {
          e.printStackTrace(out);
        }
        out.println("writing to disk...done");
        out.println("</PRE>");
        out.println("</BODY></HTML>");
        out.flush();
  }

}
