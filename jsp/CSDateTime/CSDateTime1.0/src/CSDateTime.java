/**
 * CSDateTime.java
 * Matt Tucker
 * CoolServlets.com
 * Mar 10, 1999
 * Version 1.0
 *
 * Any errors or suggested improvements to this servlet can be reported
 * as instructed on Coolservlets.com. We hope you enjoy
 * this program... your comments will encourage further development!
 *
 *    Copyright (C) 1999  Matt Tucker
 *
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * The CSDateTime servlet.
 *
 * @author  Matt Tucker
 * @version 1.0
 */
public class CSDateTime extends HttpServlet {

  /**
   * Initializes the servlet variables.
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);    
  }
  /**
   * Process the HTTP Get request. It will simply call the post function
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doPost( request, response );
  }
  /**
   * Process the HTTP Post request.
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    response.setContentType("text/html");
    //I read somewhere that some implementations have problems with doing
    //SSI while using the getWriter() method. If that's really the case,
    //I'll change this.
    PrintWriter out = response.getWriter();

    SimpleDateFormat simpleDate = new SimpleDateFormat();
    
    //Darn, if you're looking at this source, you're realizing how
    //absurdly easy Java makes this servlet.
    String format = request.getParameter("format");
    if (format == null)
      //The default if no format is specified
      simpleDate.applyPattern("MMMM d,yyyy");
    else
      simpleDate.applyPattern(format);

    //Create a date object. By default the date will represent this current
    //instant in time. Different time zone support will be added in the next
    //version of the servlet.
    Date theDate = new Date();

    out.println(simpleDate.format(theDate));

    out.close();
    theDate = null;
    simpleDate = null;
  }
  /**
   * Returns Servlet information
   */
  public String getServletInfo() {
    return "CSDateTime - by CoolServlets.com";
  }
}
