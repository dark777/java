package org.haywired.firefly;

import org.haywired.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class DeletePageServlet extends HttpServlet {

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

    SetPagesBean spb = new SetPagesBean(db);
    int p = Integer.parseInt(req.getParameter("kpage"));
    spb.setdoDelete(true);
    spb.setpage(p);
    spb.go();
    SetPageMakeupBean spmb = new SetPageMakeupBean(db);
    spmb.setdoDelete(true);
    spmb.setpage(p);
    spmb.go();
    res.sendRedirect(back);

    } else res.sendError(res.SC_FORBIDDEN);
  }
}