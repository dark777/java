<%@ page import="com.oroinc.text.perl.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.net.URL" %>
<%@ page import="org.haywired.*" %>
<%@ page import="org.haywired.firefly.*" %>
<%@ page import="org.haywired.firefly.sort.*" %>

<html>
<head>
<TITLE>Firefly Search Result</TITLE>
<style>
body { background-image: url("searchres.jpg"); background-repeat: no-repeat; font-family: Verdana, Tahoma, Arial; font-size: 10pt } 
td { font-family: Verdana, Tahoma, Arial; font-size: 10pt }
.stdbtn { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
.stdedit { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
.std {font: 10px, Verdana, Tahoma, Arial}
strong { font: 10px, Verdana, Tahoma, Arial; font-weight: bold }
</style>

</head>

<body bgcolor="#FFFFFF">

<a name="top"></a>

<% String keywords = request.getParameter("q"); 
if(keywords == null) keywords = ""; 
String regex = "s/<[^>]*>//gm"; %>

<table width="100%" border=0>
<tr><td height="50"></td><td align=right><FORM NAME='searchbox' action='search.jsp'>Search the school intranet:<br><INPUT TYPE=TEXT NAME='q' value="<%= keywords %>"class='stdedit'> <INPUT TYPE=SUBMIT VALUE='Go' class='stdbtn'><br><font size=1><input type='radio' name='o' class='std' value='a'><b>AND</b> | <input type='radio' name='o' value='o' class='std' checked><b>OR</b></font><br>To search the internet, <A HREF='http://www.google.com/'>click here</A>.</FORM></td><tr>
<tr>
<td colspan=2>
<hr color="black" width="100%" size=1>
<table border=0 cellpadding=0 cellspacing=0 width="100%"><tr>
<td width="50%">
<strong><font size="2">Firefly Search Result</font></strong>
</td><td width="50%" align="right">
<a href="firefly-pd.jsp"><strong><font size="2">Back to Intranet</font></strong></a>
</td>
</tr></table>
<hr color="black" width="100%" size=1>
</td>
</tr>

<tr><td colspan=2>Enter some search terms in the box above and press the
button to search again.  You may also choose whether you wish to perform
an AND search, which requires that each matching page contains all the
keywords specified, or an OR search, for which a page must only contain
one of the keywords to match.  Pages are sorted by relevance; the number
given on the left-hand side of each result is the score, which shows how
good a match the page is.<p></td></tr>

<%  if(!keywords.equals("")) { %>

<%-- *** --%>

<% Perl5Util util = new Perl5Util();
Vector ve = util.split(keywords);
Enumeration e = ve.elements();
String query = "SELECT cid, data, wiki FROM HTMLComponents";
boolean wheredone = false;
String opp = " OR ";
if(request.getParameter("o") != null) if(request.getParameter("o").equals("a")) opp = " AND ";
while(e.hasMoreElements()) {
  if(!wheredone) {
    query = query + " WHERE ";
    wheredone = true;
  } else
    query = query + opp;
  query = query + "data CLIKE '%" + (String)e.nextElement() + "%'";
} 
Vector v = new Vector();
DBSettingsBean db = new DBSettingsBean();
HttpSession sedb = request.getSession();
if(sedb.getAttribute("db") != null) db.setdb(sedb.getAttribute("db").toString());
try {
  Class.forName(db.getdriver());
  Connection con = DriverManager.getConnection(db.getdburl(), db.getuser(), db.getpass());
  Statement stmt = con.createStatement();
  ResultSet res = stmt.executeQuery(query);
  while(res.next())
    v.addElement(new HTMLComponentsDataBean(res.getInt(1), res.getString(2), res.getInt(3)));
  stmt.close();
  con.close();
  Enumeration ee = v.elements();
  Hashtable ht = new Hashtable();
  while(ee.hasMoreElements()) {
    HTMLComponentsDataBean t = (HTMLComponentsDataBean)ee.nextElement();
    int tcid = t.getcid();
    String tdata = t.getdata().toLowerCase();
//    System.out.println(util.substitute(regex, "<a href>test</a>"));
    tdata = util.substitute(regex, tdata); 
    int count = 0;
    Enumeration eee = ve.elements();
    while(eee.hasMoreElements()) {
      int beginindex = 0;
      String key = ((String)eee.nextElement()).toLowerCase();
      int keylength = key.length();
      int oc = tdata.indexOf(key);
      if(oc > -1) count++;
      while((oc = tdata.indexOf(key, oc+1)) > -1) count++;
    }     
    PageMakeupBean pmb = new PageMakeupBean(db);
    pmb.setcid(tcid);
    int tpage = pmb.getpage();
    if(count > 0) {
    if(ht.containsKey(new Integer(tpage))) {
      count = count + ((Integer)ht.get(new Integer(tpage))).intValue();
      ht.remove(new Integer(tpage));
    }
    ht.put(new Integer(tpage), new Integer(count));
    } else
      ht.remove(new Integer(tpage));
  }
  Enumeration ek = ht.keys();
  Enumeration ev = ht.elements(); %>

  <tr><td bgcolor=#000066 colspan=2><font size="2" color=#ffffff><b>Your search for "<%= keywords %>" returned <%= ht.size() %> results.  You performed an<%= opp %>search.</b></font></td></tr>
  <tr><td bgcolor=#DEDEAD colspan=2><font size="1"></td></tr>
  <% StrSortVector sv = new StrSortVector(); 
  while(ek.hasMoreElements()) {
    sv.addElement(new PageCountData(((Integer)ek.nextElement()).intValue(), ((Integer)ev.nextElement()).intValue()));
  }
  Enumeration eeee = sv.elements();
  while(eeee.hasMoreElements()) {
    PageCountData pcd = (PageCountData)eeee.nextElement(); 
    PagesBean pb = new PagesBean(db);
    pb.setpage(pcd.page); 
    SectionsBean sb = new SectionsBean(db);
    sb.setsid(pb.getsid()); 
    String shref = "firefly-pd.jsp?page=" + sb.gethomepage();
    PageMakeupBean pmba = new PageMakeupBean(db);
    pmba.setpage(pcd.page);
    pmba.settype(0);
    HTMLComponentsBean hcba = new HTMLComponentsBean(db);
    hcba.setcid(pmba.getcid());
    String desc = util.substitute(regex, hcba.getdata());
    if(desc.length() > 205) desc = desc.substring(0, 200).trim() + "...";
%>

<tr><td bgcolor=#000066 align=center width=20><font color=white><%= pcd.count %></font></td><td bgcolor=#EEEEFF><font size="2"><b><a href="firefly-pd.jsp?page=<%= pcd.page %>"><%= pb.gettitle() %></a><br>Section:</b> <a href="<%= shref %>"><%= sb.getname() %></a></font></td></tr>
<tr><td></td><td bgcolor=#ffffff><%= desc %></td></tr>

  <% }
} catch(Exception ex) { ex.printStackTrace(); } %>

<%-- *** --%>

<% } %>

</table>

<p><a href="#top">Back to Top</a></p>
</body>
</html>
