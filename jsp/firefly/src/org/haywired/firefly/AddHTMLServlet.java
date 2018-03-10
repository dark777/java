package org.haywired.firefly;

import org.haywired.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class AddHTMLServlet extends HttpServlet {

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

    SetHTMLComponentsBean shcb = new SetHTMLComponentsBean(db);
    shcb.setdata(req.getParameter("data"));
    if(req.getParameter("wiki") != null) if(req.getParameter("wiki").equals("on")) shcb.setwiki(true);
    shcb.go(); 
    PageMakeupBean pmb = new PageMakeupBean(db);
    int pos = 0;
    for(int i = 0; i < pmb.getsize(); i++) pos = pmb.getposition() + 1;
    SetPageMakeupBean spmb = new SetPageMakeupBean(db);
    if(req.getParameter("page") != null) spmb.setpage(Integer.parseInt(req.getParameter("page")));
    spmb.setposition(pos);
    spmb.settype(0);
    spmb.setcid(shcb.getcid());
    spmb.go();
    res.sendRedirect(back);

    } else res.sendError(res.SC_FORBIDDEN);
  }
}