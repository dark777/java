<%@ page import="com.oreilly.servlet.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="org.haywired.*" %>
<%@ page import="org.haywired.firefly.*" %>

<%-- include file="../includes/auth.inl" --%>

<% MultipartRequest multi = new MultipartRequest(request, "/tmp/firefly", 1000000);
System.out.println("D");
Enumeration files = multi.getFileNames();
while(files.hasMoreElements()) {
String name = (String)files.nextElement();
String filename = multi.getFilesystemName(name);
File f = multi.getFile(name);
if((!filename.toLowerCase().endsWith(".jpg")) && (!filename.toLowerCase().endsWith(".gif")) && (!filename.toLowerCase().endsWith(".png"))) f.delete();
if(f.exists()) {
  System.out.println("E");
  DBSettingsBean db = new DBSettingsBean();
  HttpSession sedb = request.getSession();
  if(sedb.getAttribute("db") != null) db.setdb(sedb.getAttribute("db").toString());
  SeqBean seqb = new SeqBean(db);
  int cid = seqb.getSeq("PageMakeup");
  System.out.println(cid);
  String target = "/home/httpd/html/resources/firefly/i" + cid;
  String ext = "";
  if(filename.toLowerCase().endsWith(".jpg")) ext = ".jpg";
  if(filename.toLowerCase().endsWith(".gif")) ext = ".gif";
  if(filename.toLowerCase().endsWith(".png")) ext = ".png";
  target = target + ext;
  System.out.println(target);
  File targetf = new File(target);
  f.renameTo(targetf);
  SetImageComponentsBean sicb = new SetImageComponentsBean(db);
  sicb.setcid(cid);
  sicb.setname(ext);
  sicb.setsid(Integer.parseInt(multi.getParameter("sid")));
  sicb.go();
  PageMakeupBean pmb = new PageMakeupBean(db);
  int pos = 0;
  for(int i = 0; i < pmb.getsize(); i++) pos = pmb.getposition() + 1;
  SetPageMakeupBean spmb = new SetPageMakeupBean(db);
  spmb.setpage(Integer.parseInt(multi.getParameter("page")));
  spmb.setposition(pos);
  spmb.settype(1);
  spmb.setcid(cid);
  spmb.go();
}
} %>

<html>
<head>
<style>
body { font: 10px, Verdana, Tahoma, Arial }
td { font: 10px, Verdana, Tahoma, Arial }
.stdbtn { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
.stdedit { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 100px }
.stdfile { font: 10px, Verdana, Tahoma, Arial; background-color: #F4F4F4; width: 200px }
strong { font: 10px, Verdana, Tahoma, Arial; font-weight: bold }
</style>


<!-- sjehay-ok 180500 -->
<title>Firefly Add Image</title>
</head>
<body background="../images/picture.jpg">
<form onSubmit="window.close()" name="thisform">

<table width="100%" border=0>
<tr><td height="50" colspan=2></td><tr>
<tr><td colspan=2><hr color="black" width="100%" size=1><strong>Firefly Add Image</strong><hr color="black" width="100%" size=1></td></tr>

<tr><td colspan=2>Your commands were performed.  Please refresh to view your changes.</td></tr>

<tr height=25><td span=2></td></tr>
<tr><td></td><td><input type="submit" value="Dismiss" class=stdbtn></td></tr>
</table>
</form>
</body>
</html>

<%-- include file="../includes/auth2.inl" --%>
