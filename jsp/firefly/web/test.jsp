<%@ page import="org.haywired.*" %>
<%@ page import="org.haywired.firefly.*" %>

<%-- Security: --%>
<% int aci = 0;
AuthBean au = new AuthBean(new DBSettingsBean());
String user = au.auth(request);
if(!user.equals("Guest")) {
boolean isadmin = false;
if(au.checkGroup(-1, user)) isadmin = true; 
boolean canwrite = isadmin;

DBSettingsBean db = new DBSettingsBean(); 
HttpSession sedb = request.getSession();
if(sedb.getAttribute("db") != null) db.setdb(sedb.getAttribute("db").toString());
System.out.println(db.getdburl());

int tid = Integer.parseInt(request.getParameter("tid"));

TestsBean tb = new TestsBean(db);
tb.settid(tid);
SectionsBean sb = new SectionsBean(db);
sb.setsid(tb.getsid());
QuestionsBean qb = new QuestionsBean(db);
qb.settid(tid);

String title = tb.gettitle();
String section = sb.getname(); %>

<html>
<head>
<TITLE>Firefly Test - <%= title %></TITLE>
<style>
body { background-image: url(standard.jpg); background-repeat: no-repeat; font-family: Verdana,Arial,Helvetica; font-size: 10pt }
td { font-family: Verdana,Arial,Helvetica; font-size: 10pt }
.stdbtn { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
.stdedit { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
.std {font: 10px, Verdana, Tahoma, Arial}
strong { font: 10px, Verdana, Tahoma, Arial; font-weight: bold }
.quizTimer { border-width: 1px; border-color: black; border-style: solid; background: green; width: 100; height: 30; font-family: Verdana,Arial,Helvetica; font-size: 8pt; color: white; position: absolute; left: 30; }
</style>
<script>
function OpenModify(a) {
  window.open('admin/mod/mod1.jsp?cid=' + a,'C','toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,copyhistory=no,scrollbars=no,width=600,height=450');
}
</script>
<% if(!canwrite) { %> <script language="javascript" src="rolex.jsp?time=<%= tb.gettime() %>"></script><% } %>
</head>

<body bgcolor="#FFFFFF"<% if(!canwrite) { %> onLoad="startCount(); redraw()"<% } %>>

<% if(!canwrite) { %> <SCRIPT>if (isOK) { document.write(timerChunk); }</SCRIPT><% } %>

<a name="top"></a>

<table height=50 border=0><tr><td height=50>&nbsp;</td></tr></table>

<hr color="black" width="100%" size=1><strong><font size="2">Firefly Test: <%= section %></font></strong><hr color="black" width="100%" size=1>

<center>
<h1><%= title %></h1>
</center>

<% if(!canwrite) { %><form name='formQuiz' action="marktest.jsp" method="post"><% } %>

<% for(int k = 0; k < qb.getsize(); k++) {
  int pg = qb.getqid(); %>
  <b><%= k + 1 %>.</b><br>
  <%@ include file="content-noadd.inl" %>
  <% if(canwrite) { %><p><center><a href="simple.jsp?page=<%= pg %>">Add Content</a></center></p><% } %>
  <% AnswersBean ab = new AnswersBean(db);
  ab.setqid(pg);
  for(int j = 0; j < ab.getsize(); j++) { %>
    <br><input type="radio" name=<%= pg %> value=<%= ab.getaid() %>> <%= ab.getddesc() %>
  <% } %>
    <br><input type="radio" name=<%= pg %> value="skip" checked> Skip this question
    <p><hr noshade><p>
<% } %>

<% if(!canwrite) { %><center><input class='stdbtn' type="reset" value="Start Again" onclick="return confirm('Are you sure you want to start again?')"> <input class='stdbtn' type="submit" value="Finish!" onclick="return confirm('Are you sure you want to end this test?')"></center><% } %>

</form>

<center><a href="#fireflytop">Back to Top</a></center>

</body>
</html>

<% } %>
