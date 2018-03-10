package org.haywired.firefly;

import org.haywired.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class DeleteImageServlet extends HttpServlet {

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

    int c = Integer.parseInt(req.getParameter("cid"));
    ImageComponentsBean icb = new ImageComponentsBean(db);
    icb.setcid(c);
    String ext = icb.getname();
    String file = "/home/httpd/html/resources/firefly/i" + c + ext;
    File f = new File(file);
    System.out.println(file);
    f.delete();

    SetImageComponentsBean shcb = new SetImageComponentsBean(db);
    shcb.setdoDelete(true);
    shcb.setcid(c);
    shcb.go();
    SetPageMakeupBean spmb = new SetPageMakeupBean(db);
    spmb.setdoDelete(true);
    spmb.setcid(c);
    spmb.go();
    res.sendRedirect(back);

    } else res.sendError(res.SC_FORBIDDEN);
  }
}