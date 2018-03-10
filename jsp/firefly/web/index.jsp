<%@ page info="Firefly Page Displayer" %>

<%-- Next line is for Release-000509 beans only - new beans should be in new package. --%>

<%@ page import="org.haywired.*" %>

<%@ page import="org.haywired.firefly.*" %>

<%-- DBSettingsBean db is created; set session DB param, if passed --%>

<jsp:useBean id="db" scope="page" class="org.haywired.DBSettingsBean" />

<%-- Security: --%>
<% AuthBean au = new AuthBean(new DBSettingsBean());
String user = au.auth(request);
if(!user.equals("Guest")) {
boolean isadmin = false;
if(au.checkGroup(-1, user)) isadmin = true; %>

<%-- Check you've agreed to the ICT Policy recently: --%>
<% boolean policyok = false;
if((!user.equals("Guest")) && (!isadmin)) {
CheckPolicyTimeBean cptb = new CheckPolicyTimeBean(new DBSettingsBean()); 
policyok = cptb.check(user); } else { policyok = true; } %>

<% HttpSession sedb = request.getSession();
if(request.getParameter("db") != null)
  sedb.setAttribute("db", request.getParameter("db").toString());
if(sedb.getAttribute("db") != null)
  db.setdb(sedb.getAttribute("db").toString()); %>

<%-- PagesBean pba is created; it is used to get information about the current page.
If page param is passed, set that property, else set to 0. --%>

<% PagesBean pba = new PagesBean(db);
if(request.getParameter("page") != null) {
  pba.setpage(request.getParameter("page"));
} else {
  pba.setpage(0);
} 

// save away the current page for later use
int pg = pba.getpage();

// POLICY CHECKING

if(pg == 15) {
  if(db.getdb().equals("CC")) {
    policyok = true;
  }
}

if(policyok) {

/** SectionsBean sba is created; it is used to get information about the current section.
Set sid to be current page's. **/

// save away the current sid for later use
int sid = pba.getsid();

SectionsBean sba = new SectionsBean(db);
sba.setsid(sid); 
boolean cansread = false;
boolean canswrite = false;
if(au.checkGroup(sba.getreadgid(), user)) cansread = true;
if(au.checkGroup(sba.getwritegid(), user)) canswrite = true;

// SECURITY CHECKING
int readgid = pba.getreadgid();
int writegid = pba.getwritegid();
boolean canread = false;
boolean canwrite = false;
if(readgid == -3) canread = cansread;
else if(au.checkGroup(readgid, user)) canread = true;
if(writegid == -3) canwrite = canswrite;
else if(au.checkGroup(writegid, user)) canwrite = true;

if(canread) {

// save away the current parent for later use
int pbaparent = pba.getparent();
PagesBean pbb = new PagesBean(db);
int pbaparentparent;
if(pbaparent != -1) {
pbb.setpage(pbaparent);
pbaparentparent = pbb.getparent();
} else pbaparentparent = -1;

// save away the current title for later use
String title = pba.gettitle();

%>

<%-- HTML starts here --%>

<html>
  <head>
    <title>firefly - <%= title %></title>
  </head>

<style>
.stdbtn { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
.sizer { font : 10px, Verdana, Tahoma, Arial }
.dmenu { background-color: #CECF9C; border-style: solid; border-width: 1px; border-color: #CECF9C; padding: 3px; font-family: Verdana; font-size: 10pt; background-image: url(brownline.gif) }
.dmenu a:link	{ text-decoration: none; color: #000066; font-weight: bold  }
.dmenu a:visited	{ text-decoration: none; color: gray; font-weight: bold }
.dmenu a:hover	{ text-decoration: underline; }
.topbar { font-family: Verdana; font-size: 10pt }
.topbar a:link { text-decoration: none; color: white }
.topbar a:visited { text-decoration: none; color: white }
.topbar a:hover { text-decoration: underline; color: white }
</style>

<SCRIPT> function formProcess() { 
var intSearch = false; 
if (document.forms["formSearchFF"].elements[1].checked == true) 
{ intSearch = true } if (intSearch) 
{ document.forms["formSearchFF"].submit(); } 
else { document.forms["formSearchGG"].elements["q"].value = document.forms["formSearchFF"].elements["q"].value; 
document.forms["formSearchGG"].submit(); } } 
</SCRIPT>


<%-- Script to pop up admin interface --%>

<SCRIPT LANGUAGE="JavaScript" SRC="menubar.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript">

function OpenAdmin() {
  window.open('admin/admin.html','B','toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,copyhistory=no,scrollbars=no,width=374,height=500');
}

function OpenLogin() {
   window.open('http://piccadilly/logon/','Q');
}

function OpenAddSection() {
  window.open('admin/as/as1.jsp','C','toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,copyhistory=no,scrollbars=no,width=374,height=383');
}

function OpenAddPage() {
  window.open('admin/ap/ap1.jsp?sid=<%= sid %>','C','toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,copyhistory=no,scrollbars=no,width=374,height=383');
}

function OpenModify(a) {
  window.open('admin/mod/mod1.jsp?cid=' + a,'C','toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,copyhistory=no,scrollbars=no,width=600,height=450');
}

function OpenAddImage() {
  window.open('admin/picture/picture1.jsp?page=' + <%= pg %> + '&sid=' + <%= sid %>,'C','toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,copyhistory=no,scrollbars=no,width=374,height=383');
}

<%-- jjm's clever menu script --%>

<% SectionsBean sbz = new SectionsBean(db);
sbz.setparent(-1);
for(int i = 0; i < sbz.getsize(); i++) {
  int sbzCOUNT = 0;
  int sbzsid = sbz.getsid();
  String sbzText = "s" + sbzsid + "Text";
  String sbzLink = "s" + sbzsid + "Link"; %>

  var <%= sbzText %> = new Array();
  var <%= sbzLink %> = new Array();

<% SectionsBean sby = new SectionsBean(db);
sby.setparent(sbzsid);
for(int j = 0; j < sby.getsize(); j++) {
  String sbyText = sbzText + "[" + sbzCOUNT + "]";
  String sbyLink = sbzLink + "[" + sbzCOUNT++ + "]";
  String temphref = request.getRequestURI() + "?page=" + sby.gethomepage();
%>

<%= sbyText %> = "<%= sby.getname() %>";  
<%= sbyLink %> = "<%= temphref %>";

<% } 
} %>

</script>

<%-- Set the page bgcolor to be the section colour code.
We must make a string and set it to the cc - if it's null, set it to a default. --%>

<% String bgcolort = sba.getcolour();
String bgcolor;
if(bgcolort != null) bgcolor = "#" + bgcolort;
else bgcolor = "#FFFFFF"; %>

  <body bgcolor="<%= bgcolor %>" ONMOUSEMOVE="shaker()">

<a name="firefly-top"></a>

    <table border="0" width="100%" bgcolor="#000080" cellspacing="0" cellpadding="3">
      <tr>
        <td width="100%">
          <table border="0" width="100%" bgcolor="#FFFFFF" cellspacing="0">
            <tr>
              <td background="brownline.gif">&nbsp;</td>
              <td>
                <p align="center"><img border="0" src="intranettop.gif">
              </td>
              <td background="brownline.gif">&nbsp;</td>
            </tr>
            <tr>
              <td width="100%" colspan="3" background="thinblack.gif"><div class="topbar">
                <p><font face="Verdana" color="#FFFFFF" size="2">

<%-- Here, we must have a list of all the other sections.  So, first let's make another sb - sbb.
Then, find out it's size and do a for loop, stepping through each section.
If it's sid is equal to the current page's sid, then don't link it, make it bold.
Otherwise, make a link to it's home page, print its name, close the link, add a | and go.
We have to create some temp. variables to store the data returned by the bean so it can be used more than once without going on to the next section. --%>

<% SectionsBean sbb = new SectionsBean(db);
sbb.setparent(-1);
for(int i = 0; i < sbb.getsize(); i++) {
  int tempsid = sbb.getsid();
  String temphref = "subsections.jsp" + "?sid=" + tempsid;
  if(tempsid == 0) temphref = "firefly-pd.jsp" + "?page=" + sbb.gethomepage();
  String flasherlink = "flasher('s" + tempsid + "')"; %>

<a href="<%= temphref %>" ONMOUSEOVER="<%= flasherlink %>"><%= sbb.getname() %></a>

<% if(isadmin) { %>
<a href="servlet/org.haywired.firefly.DeleteSectionServlet?sid=<%= tempsid %>&page=<%= pg %>"  onclick="return confirm('Are you sure you want to delete this section?')"><img src="icons/Delete.gif" border=0></a><% } %> |

<% } %> 
<a href="search.jsp" ONMOUSEOVER="onSearch()">Search</a> | 
<% if(isadmin) { %><a href="javascript:OpenAddSection()">Add Section</a><% } %>

                </font></div>
              </td>
            </tr>
            <tr>
              <td width="100%" colspan="3">
                <table border="0" width="100%" cellspacing="0" cellpadding=10>
                  <tr>
                    <td width="20%" background="brownline.gif" valign="top">
                      <b><font face="Verdana" size="2">

<%-- above the line, all the compulsory pages --%>

<%-- here, a list of all the pages with parent -1 in this section.
First, let's have a new PagesBean, pbz. --%>

<% PagesBean pbc = new PagesBean(db);
pbc.setsid(sid);
pbc.setheaderonly(true);
pbc.setparent(-1);

// now step through each page and save some useful data...

for(int i = 0; i < pbc.getsize(); i++) { 
  boolean canreadc = false;
  boolean canwritec = false;
  int pbcreadgid = pbc.getreadgid();
  int pbcwritegid = pbc.getwritegid();
  if(pbcreadgid == -3) canreadc = cansread;
  else if(au.checkGroup(pbcreadgid, user)) canreadc = true;
  if(pbcwritegid == -3) canwritec = canswrite;
  else if(au.checkGroup(pbcwritegid, user)) canwritec = true;
  String linktitlet = pbc.getlinktitle();
  String titlet = pbc.gettitle();
  int paget = pbc.getpage();
  String tempt;
  if(linktitlet != null) tempt = linktitlet;
  else tempt = titlet;
  String temphref = request.getRequestURI() + "?page=" + paget;
  String tempdefstext = pbc.getdefstext(); if(tempdefstext != null) {
    if(tempdefstext.startsWith("http://")) temphref = "external.jsp?";
    else temphref = "";
    temphref = temphref + tempdefstext;
    }
  boolean iscurrent = false;
  if(paget == pg) iscurrent = true; 
  if(canreadc) { %>

<li><% if(!iscurrent) { %><a href="<%= temphref %>"><% } %><%= tempt %></a>

  <% } %>

<% if((iscurrent) || (pbaparent == paget) || (pbaparentparent == paget)) { %>
<ul>
<% PagesBean pby = new PagesBean(db);
pby.setsid(sid);
// pby.setheaderonly(false);
pby.setparent(paget);

// now step through each page and save some useful data...

for(int j = 0; j < pby.getsize(); j++) { 
  boolean canready = false;
  boolean canwritey = false;
  int pbyreadgid = pby.getreadgid();
  int pbywritegid = pby.getwritegid();
  if(pbyreadgid == -3) canready = cansread;
  else if(au.checkGroup(pbyreadgid, user)) canready = true;
  if(pbywritegid == -3) canwritey = canswrite;
  else if(au.checkGroup(pbywritegid, user)) canwritey = true;
  String linktitlety = pby.getlinktitle();
  String titlety = pby.gettitle();
  int pagety = pby.getpage();
  String tempty;
  if(linktitlety != null) tempty = linktitlety;
  else tempty = titlety;
  String temphrefy = request.getRequestURI() + "?page=" + pagety;
  String tempdefstexty = pby.getdefstext(); if(tempdefstexty != null) {
    if(tempdefstexty.startsWith("http://")) temphrefy = "external.jsp?";
    else temphrefy = "";
    temphrefy = temphrefy + tempdefstexty;
    }
  boolean iscurrenty = false;
  if(pagety == pg) iscurrenty = true; 
  if(canready) { %>

<li><% if(!iscurrenty) { %><a href="<%= temphrefy %>"><% } %><%= tempty %></a>

  <% } %>

<%-- ******************* --%>

<% if((iscurrenty) || (pbaparent == pagety)) { %>

<ul>
<% PagesBean pbx = new PagesBean(db);
pbx.setsid(sid);
pbx.setheaderonly(false);
pbx.setparent(pagety);

// now step through each page and save some useful data...

for(int k = 0; k < pbx.getsize(); k++) { 
  boolean canreadx = false;
  boolean canwritex = false;
  int pbxreadgid = pbx.getreadgid();
  int pbxwritegid = pbx.getwritegid();
  if(pbxreadgid == -3) canreadx = cansread;
  else if(au.checkGroup(pbxreadgid, user)) canreadx = true;
  if(pbxwritegid == -3) canwritex = canswrite;
  else if(au.checkGroup(pbxwritegid, user)) canwritex = true;
  String linktitletx = pbx.getlinktitle();
  String titletx = pbx.gettitle();
  int pagetx = pbx.getpage();
  String temptx;
  if(linktitletx != null) temptx = linktitletx;
  else temptx = titletx;
  String temphrefx = request.getRequestURI() + "?page=" + pagetx;
  String tempdefstextx = pbx.getdefstext(); if(tempdefstextx != null) {
    if(tempdefstextx.startsWith("http://")) temphrefx = "external.jsp?";
    else temphrefx = "";
    temphrefx = temphrefx + tempdefstext;
    }
  boolean iscurrentx = false;
  if(pagetx == pg) iscurrentx = true;
  if(canreadx) { %>
<li><% if(!iscurrentx) { %><a href="<%= temphrefx %>"><% } %><%= temptx %></a> 
  
<% if(!iscurrentx) if(canwritex) { %>
<a href="servlet/org.haywired.firefly.DeletePageServlet?kpage=<%= pagetx %>" onclick="return confirm('Are you sure you want to delete this page?')"><img src="icons/Delete.gif" border=0></a><img src="icons/User.gif" border=0 height=11><img src="icons/Users2.gif" border=0 height=11><% } %>

  <% } %>

<% } %>
</ul>
<% } %>

<%-- ******************* --%>

<% } %>
</ul>
<% } 
} %>

<li><a href="tests.jsp?sid=<%= sid %>">Tests</a>
<hr noshade color="#000000" size=1>

<%-- below it, the custom ones --%>

<%-- here, a list of all the pages with parent -1 in this section.
First, let's have a new PagesBean, pbz. --%>

<% PagesBean pbz = new PagesBean(db);
pbz.setsid(sid);
pbz.setheaderonly(false);
pbz.setparent(-1);

// now step through each page and save some useful data...

for(int i = 0; i < pbz.getsize(); i++) { 
  boolean canreadt = false;
  boolean canwritet = false;
  int pbzreadgid = pbz.getreadgid();
  int pbzwritegid = pbz.getwritegid();
  if(pbzreadgid == -3) canreadt = cansread;
  else if(au.checkGroup(pbzreadgid, user)) canreadt = true;
  if(pbzwritegid == -3) canwritet = canswrite;
  else if(au.checkGroup(pbzwritegid, user)) canwritet = true;
  String linktitlet = pbz.getlinktitle();
  String titlet = pbz.gettitle();
  int paget = pbz.getpage();
  int pagetparent = pbz.getparent();
  String tempt;
  if(linktitlet != null) tempt = linktitlet;
  else tempt = titlet;
  String temphref = request.getRequestURI() + "?page=" + paget;
  String tempdefstext = pbz.getdefstext(); if(tempdefstext != null) {
    if(tempdefstext.startsWith("http://")) temphref = "external.jsp?";
    else temphref = "";
    temphref = temphref + tempdefstext;
    }
  boolean iscurrent = false;
  if(paget == pg) iscurrent = true;
  if(canreadt) { %>

<li><% if(!iscurrent) { %><a href="<%= temphref %>"><% } %><%= tempt %></a> 

<% if(!iscurrent) if(canwritet) { %>
<a href="servlet/org.haywired.firefly.DeletePageServlet?kpage=<%= paget %>"  onclick="return confirm('Are you sure you want to delete this page?')"><img src="icons/Delete.gif" border=0></a><a href="admin/ga/glist.jsp?gid=<%= pbzreadgid %>"><img src="icons/User.gif" border=0 height=11></a><a href="admin/ga/glist.jsp?gid=<%= pbzwritegid %>"><img src="icons/Users2.gif" border=0 height=11></a><% } %>

  <% } %>

<% if((iscurrent) || (pbaparent == paget) || (pbaparentparent == paget)) { %>
<ul>
<% PagesBean pby = new PagesBean(db);
pby.setsid(sid);
pby.setheaderonly(false);
pby.setparent(paget);

// now step through each page and save some useful data...

for(int j = 0; j < pby.getsize(); j++) { 
  boolean canreadty = false;
  boolean canwritety = false;
  int pbyreadgid = pby.getreadgid();
  int pbywritegid = pby.getwritegid();
  if(pbyreadgid == -3) canreadty = cansread;
  else if(au.checkGroup(pbyreadgid, user)) canreadty = true;
  if(pbywritegid == -3) canwritety = canswrite;
  else if(au.checkGroup(pbywritegid, user)) canwritety = true;
  String linktitlety = pby.getlinktitle();
  String titlety = pby.gettitle();
  int pagety = pby.getpage();
  String tempty;
  if(linktitlety != null) tempty = linktitlety;
  else tempty = titlety;
  String temphrefy = request.getRequestURI() + "?page=" + pagety;
  String tempdefstexty = pby.getdefstext(); if(tempdefstexty != null) {
    if(tempdefstexty.startsWith("http://")) temphrefy = "external.jsp?";
    else temphrefy = "";
    temphrefy = temphrefy + tempdefstexty;
    }
  boolean iscurrenty = false;
  if(pagety == pg) iscurrenty = true;
  if(canreadty) { %>

<li><% if(!iscurrenty) { %><a href="<%= temphrefy %>"><% } %><%= tempty %></a> 

<% if(!iscurrenty) if(canwritety) { %>
<a href="servlet/org.haywired.firefly.DeletePageServlet?kpage=<%= pagety %>" onclick="return confirm('Are you sure you want to delete this page?')"><img src="icons/Delete.gif" border=0></a><a href="admin/ga/glist.jsp?gid=<%= pbyreadgid %>"><img src="icons/User.gif" border=0 height=11></a><a href="admin/ga/glist.jsp?gid=<%= pbywritegid %>"><img src="icons/Users2.gif" border=0 height=11></a><% } %>

  <% } %>

<%-- ******************* --%>

<% if((iscurrenty) || (pbaparent == pagety)) { %>

<ul>
<% PagesBean pbx = new PagesBean(db);
pbx.setsid(sid);
pbx.setheaderonly(false);
pbx.setparent(pagety);

// now step through each page and save some useful data...

for(int k = 0; k < pbx.getsize(); k++) { 
  boolean canreadtx = false;
  boolean canwritetx = false;
  int pbxreadgid = pbx.getreadgid();
  int pbxwritegid = pbx.getwritegid();
  if(pbxreadgid == -3) canreadtx = cansread;
  else if(au.checkGroup(pbxreadgid, user)) canreadtx = true;
  if(pbxwritegid == -3) canwritetx = canswrite;
  else if(au.checkGroup(pbxwritegid, user)) canwritetx = true;
  String linktitletx = pbx.getlinktitle();
  String titletx = pbx.gettitle();
  int pagetx = pbx.getpage();
  String temptx;
  if(linktitletx != null) temptx = linktitletx;
  else temptx = titletx;
  String temphrefx = request.getRequestURI() + "?page=" + pagetx;
  String tempdefstextx = pbx.getdefstext(); if(tempdefstextx != null) {
    if(tempdefstextx.startsWith("http://")) temphrefx = "external.jsp?";
    else temphrefx = "";
    temphrefx = temphrefx + tempdefstextx;
    }
  boolean iscurrentx = false;
  if(pagetx == pg) iscurrentx = true;
  if(canreadtx) { %>

<li><% if(!iscurrentx) { %><a href="<%= temphrefx %>"><% } %><%= temptx %></a> 

<% if(!iscurrentx) if(canwritetx) { %>
<a href="servlet/org.haywired.firefly.DeletePageServlet?kpage=<%= pagetx %>" onclick="return confirm('Are you sure you want to delete this page?')"><img src="icons/Delete.gif" border=0></a><a href="admin/ga/glist.jsp?gid=<%= pbxreadgid %>"><img src="icons/User.gif" border=0 height=11></a><a href="admin/ga/glist.jsp?gid=<%= pbxwritegid %>"><img src="icons/Users2.gif" border=0 height=11></a><% } %>

  <% } %>

<% } %>
</ul>
<% } %>

<%-- ******************* --%>

<% } %>
</ul>
<% }%>

<% }
%>

<% if(canswrite) { %><br><a href="javascript:OpenAddPage()">Add Page</a><% } %>

                      </font></b>
                    </td>
                    <td width="80%" valign="top" bgcolor="#FFFFFF">
                      <font face="Verdana" size="2">
                      <h1 align="center"><font color="#000080">
                        <%= title %>
                      </font></h1>

<%@ include file="content.inl" %>

<center><a href="#firefly-top">Back to Top</a></center>

                    </td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr>
              <td width="100%" colspan="3" background="thinblack.gif">
                <font face="Verdana" size="2"
color="#FFFFFF"><b>firefly</b> <i>preview</i> - <%= sedb.getAttribute("b").toString() %>: cansread <%= cansread %>, canswrite <%= canswrite %>, canread <%= canread %>, canwrite <%= canwrite %>
                </font>
              </td>
            </tr>
            <tr>
              <td width="100%" colspan="3" background="brownline.gif">
                <font face="Verdana" size="2">

<%-- information about the user --%>
<% if(isadmin) { %><form><img src="icons/OpenLock.gif"> Administrator
<input type="button" value="Control Panel" onclick="OpenAdmin()" class="stdbtn"><input type="button" value="Server Logon" onclick="OpenLogin()" class="stdbtn"><% }
else if(user.equals("Guest")) { %><img src="icons/UnLock.gif"> Guest<% }
else if(canwrite) { %><img src="icons/teacher.gif"> <%= user %>, write access<% }
else { %><img src="icons/Lock.gif"> <%= user %><% } %>
| <% if(user.equals("Guest")) { %><a href="logon/logon.jsp">Log On</a><% }
  else { %><a href="logon/logoff.jsp">Log Off</a><% } %>
</form>

                </font>
              </td>
            </tr>            
          </table>
        </td>
      </tr>
    </table>

<SCRIPT>
if (isOK == true) { document.write(divLine) }
</SCRIPT>

  </body>
</html>


<% // this is the end of the canread if
} else { %>
<% response.sendError(403); %>
<% }
// this is the end of the policy-ok if 
} else { %>
<%@ include file="policynotok.inl" %>
<% } 
} else { %>
<%@ include file="guest.inl" %>
<% } %>