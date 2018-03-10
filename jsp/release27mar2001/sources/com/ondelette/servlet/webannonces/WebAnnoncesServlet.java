

package com.ondelette.servlet.webannonces;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.ondelette.servlet.*;

public class WebAnnoncesServlet extends HttpServlet implements ParameterNames {

  public void init(ServletConfig config)
    throws ServletException {
    super.init(config);
  }

  public void doGet(HttpServletRequest request,
          HttpServletResponse response)
          throws ServletException, IOException {
          doPost(request,response);
  }

  public void doPost(HttpServletRequest request,
          HttpServletResponse response)
          throws ServletException, IOException {
    try {
      String s;
      if ((s = request.getParameter(ANNONCEFILEPARAM)) != null) {
        AnnoncesFolder af = AnnoncesFolderFactory.getAnnoncesFolder(new File(s));
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if ((s = request.getParameter(DELETE)) != null) {
          af.remove(Integer.parseInt(s.trim()));
          af.showAdmin(out);
          out.flush();
          return;
        } else if(request.getParameter(ADMINPARAM) != null) {
          af.showAdmin(out);
          out.flush();
          return;
        } else if ((s = request.getParameter(ANNONCEPARAM)) != null) {
          if (s.length() == 0) {
            af.show(out);
            out.flush();
            return;
          }
          af.add(new Annonce(
            request.getParameter(SUBJECTPARAM),
            request.getParameter(AUTHORPARAM),
            request.getParameter(EMAILPARAM),
            request.getParameter(PHONENUMBERPARAM),
            request.getParameter(ADDRESSPARAM),
            request.getParameter(ANNONCEPARAM),
            request.getParameter(URLPARAM)));
          af.show(out);
          out.flush();
          return;
        } else {
          af.show(out);
          out.flush();
          return;
        }
      } else {
       ServletErrorHandling.printErrorMessage(request, response, null);
      }

    } catch (Exception ioe) {
        ServletErrorHandling.printErrorMessage(request, response, ioe);
    }
  }

  public void destroy() {
    AnnoncesFolderFactory.destroy();
  }


}
