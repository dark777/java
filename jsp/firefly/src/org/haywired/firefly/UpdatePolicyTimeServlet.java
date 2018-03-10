package org.haywired.firefly;

import org.haywired.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class UpdatePolicyTimeServlet extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    doPost(req, res);
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    String back = "/firefly/firefly-pd.jsp?db=SPSIntranet&page=0";

    AuthBean au = new AuthBean(new DBSettingsBean());
    String user = au.auth(req);

    if(req.getParameter("iagreea") != null) {
    if(req.getParameter("iagreeb") != null) {
    if(req.getParameter("iagreec") != null) {   

    if((req.getParameter("iagreea").equals("on")) && (req.getParameter("iagreeb").equals("on")) && (req.getParameter("iagreec").equals("on"))) {
    UpdatePolicyTimeBean uptb = new UpdatePolicyTimeBean(new DBSettingsBean());
    uptb.update(user);
    }

    }
    }
    }

    res.sendRedirect(back);

  }
}