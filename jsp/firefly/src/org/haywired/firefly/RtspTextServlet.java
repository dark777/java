package org.haywired.firefly;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class RtspTextServlet extends HttpServlet {
  public void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    res.setContentType("video/quicktime");
    PrintWriter out = res.getWriter();
    out.println("rtsptext");
    out.println("rtsp://" + req.getParameter("server") + "/" +
req.getParameter("mov") +
".mov");
    out.close();
  }
} 
