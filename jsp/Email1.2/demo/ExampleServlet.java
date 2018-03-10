
/**
 *  ExampleServlet.java
 *
 *  CS Email Package, Version 1.2
 *
 *  September 27, 1999
 *
 *  Copyright (C) 1999  Bill Lynch, CoolServlets.com
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Library General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  http://www.coolservlets.com/LGPL.html
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Library General Public License for more details.
 *
 *  You should have received a copy of the GNU Library General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.coolservlets.email.*;

/**
 *  This is a fully functional demo servlet which uses the Email Package.
 *
 *  Disclamer 1:
 *  This demo assumes you have some knowledge of Java serlvets and you have
 *  access to a web server that supports servlets. If you don't a good place
 *  to start learning about servlets is:
 *      http://www.coolservlets.com
 *
 *  Disclamer 2:
 *  This servlet works but does not do any parameter error checking and should
 *  not be a model for servlet design in that regard. Make sure when you
 *  submit an email (through the HTML form) that all the fields have
 *  data in them or else you'll get an "Error 505, Internal Server Error" or
 *  "document contains no data" errors. This is a bare bones servlet which is
 *  designed to illustrate the use of the Email Package. :)
 *
 *  To run this demo, you need to do a few things:
 *
 *  (1) Have access to a web server that can run Java servlets.
 *
 *  (2) Copy this servlet in your site's servlet directory.
 *
 *  (3) Copy the email classes into the servlet directory in the following way:
 *      If your servlet directory on the server is "servlets" then:
 *      /servlets/  <-- ExampleServlet.class goes here
 *               / com /
 *                     / coolservlets /
 *                                    / email / <email classes>
 *      where <email classes> are the files included in the distribution.
 *
 *  (4) Find out what the HTTP path is to the servlet directory. This is
 *      the path you use to invoke serlvets with. For example, on
 *      CoolServlets.com, the path is:
 *          http://www.coolservlets.com/servlet/
 *      You'll need to change the SERVLET_HTTP_PATH private variable in the
 *      ExampleServlet class below to this value.
 *
 */
public class ExampleServlet extends HttpServlet {

    private String SERVLET_HTTP_PATH = "";  /* ie, http://www.yourSite.com/servlet/     */;

    // Init method -- called the first time the servlet is run.
    public void init( ServletConfig config ) throws ServletException {
        super.init(config);
    }

    // Since this servlet makes no distinction between a GET or a POST, pass
    // the GET requests off to the doPost method.
    public void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        doPost( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        response.setContentType( "text/html" );     // Tell the client we're going to output HTML.
        PrintWriter out = response.getWriter();     // Get the writer.

        // Check for the "mode" parameter. If it's blank, we're running the servlet
        // for the first time, so print out the form where the user can input
        // the email information. Otherwise, we have the email info incoming,
        // so we need to process it (the else branch).
        String mode = request.getParameter( "mode" );
        if( mode == null || mode.length() == 0 ) {
            out.println( printHeader( "ExampleServlet, CS Email Package 1.2, CoolServlets.com" ) );
            out.println( printFormHTML( SERVLET_HTTP_PATH ) );
            out.println( printFooter() );
        }
        else {
            out.println( printHeader( "CS Email Package 1.2" ) );
            getParametersAndSendEmail( request, out );
            out.println( printFooter() );
        }
        out.close();
    }

    // This is where the email classes finally get used. It's exactly similiar
    // to the ExampleApp class (the other demo in this package).
    private void getParametersAndSendEmail( HttpServletRequest request, PrintWriter out ) {
        // Get the parameters
        String YOUR_NAME       = request.getParameter( "fromName" );
        String YOUR_EMAIL      = request.getParameter( "fromEmail" );
        String RECIPIENT_NAME  = request.getParameter( "toName" );
        String RECIPIENT_EMAIL = request.getParameter( "toEmail" );
        String EMAIL_SUBJECT   = request.getParameter( "subject" );
        String EMAIL_TEXT      = request.getParameter( "text" );
        String SMTP_HOST       = request.getParameter( "smtpHost" );
        int    SMTP_PORT       = Integer.parseInt( request.getParameter( "smtpPort" ) );

        // Create the message and transport objects:
        Message msg  = new Message();
        Transport tr = new Transport( SMTP_HOST, SMTP_PORT );

        // Set the FROM, TO, SUBJECT and text of the email message
        msg.setFrom( new Address( YOUR_NAME, YOUR_EMAIL ) );
        msg.setRecipient( RecipientType.TO, new Address( RECIPIENT_NAME, RECIPIENT_EMAIL ) );
        msg.setSubject( EMAIL_SUBJECT );
        msg.setText( EMAIL_TEXT );

        // Send it! Catch the TransportException.
        try {
            tr.send( msg );
            out.println( "Message was sent!" );
        }
        catch( TransportException te ) {
            out.println( "Transport Exception: " + te );
        }
    }

    // The next 3 methods I wrote so the doPost method would be more readable.
    private String printHeader( String title ) {
        return( "<html><head><title>"+ title +"</title></head><body>" );
    }

    private String printFooter( ) {
        return( "</body></html>" );
    }

    // By the way, it's inefficient to do String concatenation in the following
    // way. Check out the CoolServlets.com tips section for a better example:
    // http://www.coolservlets.com/developer/tips/
    private String printFormHTML( String servletPath ) {
        String html = "";
		html += "<form method=post action='"+ servletPath +"ExampleServlet'>";
		html += "	<input type='hidden' name='mode' value='sendEmail'>";
		html += "	<pre>";
		html += "	SMTP Host:  <input type='text' name='smtpHost' size=30>\n";
		html += "	SMTP Port:  <input type='text' name='smtpPort' size=30>\n";
		html += "	From Name:  <input type='text' name='fromName' size=30>\n";
		html += "	From Email: <input type='text' name='fromEmail' size=30>\n";
		html += "	To Name:    <input type='text' name='toName' size=30>\n";
		html += "	To Email:   <input type='text' name='toEmail' size=30>\n";
		html += "	-------------------------------------------\n";
		html += "	Subject:    <input type='text' name='subject' size=30>\n";
		html += "	Text:\n";
		html += "	            <textarea cols=28 rows=10 wrap='virtual' name='text'></textarea>\n";
		html += "	-------------------------------------------\n";
		html += "	<input type='submit' value='Send'>\n";
		html += "	</pre>";
		html += "</form>";
        return html;
    }
}