package org.haywired.firefly;

import org.haywired.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class DeleteSectionServlet extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    doPost(req, res);
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    DBSettingsBean db = new DBSettingsBean();
    HttpSession sedb = req.getSession();
    if(sedb.getAttribute("db") != null) db.setdb(sedb.getAttribute("db").toString());

    AuthBean au = new AuthBean(new DBSettingsBean());
    String user = au.auth(req);
    if(au.checkGroup(-1, user)) {

    String back = req.getParameter("back");
    if(back == null) back = req.getHeader("Referer");

    SetSectionsBean ssb = new SetSectionsBean(db);
    int s = Integer.parseInt(req.getParameter("sid"));
    ssb.setdoDelete(true);
    ssb.setsid(s);
    ssb.go();
    res.sendRedirect(back);

    } else res.sendError(res.SC_FORBIDDEN);
  }
}