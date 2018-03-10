<%@ page import = "com.analysisandsolutions.forms.CreditCardValidationSolution, java.io.*, java.util.*, javax.servlet.http.HttpServletRequest" %>

<%
/*
 * Credit Card Validation Solution, Java Edition,
 * Usage Example.
 *
 * Author     Daniel Convissor <danielc@AnalysisAndSolutions.com>
 * Copyright  The Analysis and Solutions Company, 2002-2003
 * Version    $Revision: 1.5 $, $Date: 2003/08/05 17:10:15 $
 * Release    $Note:  $
 * Link       http://www.ccvs.info/
 */
%>

<html>
 <head>
  <title>Credit Card Validation Solution:&trade; Java Edition Test</title>
 </head>
 <body>
<%

CreditCardValidationSolution Form = new CreditCardValidationSolution();

String Month      = "";
String Year       = "";
String RequireExp = "Y";

if (request.getParameter("Number") == null) {
    Form.CCVSNumber = "4002417016240182";

} else {
	/*
     * Put the names of the card types you accept in here.
     * If you want to accept all card types, just put "All".
     */
    ArrayList Accept = new ArrayList(
        Arrays.asList(
            new String[] {"Visa", "JCB"}
        )
    );

    Month = request.getParameter("Month");
    Year  = request.getParameter("Year");

    if ( !Form.validateCreditCard(request.getParameter("Number"), "en",
    				  Accept, RequireExp, Month, Year) ) {
        out.println("  <p>PROBLEM: " + Form.CCVSError + "</p>");
    } else {
        out.println("  <p>That " + Form.CCVSType + " number seems good");
        out.println("  and expires in  " + Form.CCVSExpiration + ".");
        out.println("  <br />The left digits are " + Form.CCVSNumberLeft);
        out.println("  and the right digits are " + Form.CCVSNumberRight + ".</p>");

    }

}

%>
  <form method="post">
   Number: <input type="text" name="Number" size="21" maxlen="21"
       value="<%= Form.CCVSNumber %>" />
   Month: <input type="text" name="Month" size="2" maxlength="2"
       value="<%= Month %>" />
   Year: <input type="text" name="Year" size="4" maxlength="4"
       value="<%= Year %>" />
   <input type="submit" name="Submit" value="Test" />
  </form>
 </body>
</html>
