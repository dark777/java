<%@ page import="com.oreilly.servlet.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="org.haywired.*" %>
<%@ page import="org.haywired.firefly.*" %>

<% MultipartRequest multi = new MultipartRequest(request, "/tmp/firefly", 500000);
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
  if(filename.toLowerCase().endsWith(".jpg")) target = target + ".jpg";
  if(filename.toLowerCase().endsWith(".gif")) target = target + ".gif";
  if(filename.toLowerCase().endsWith(".png")) target = target + ".png";
  System.out.println(target);
  File targetf = new File(target);
  f.renameTo(targetf);
  SetImageComponentsBean sicb = new SetImageComponentsBean(db);
  sicb.setcid(cid);
  sicb.setname(request.getParameter("name");
  sicb.setsid(Integer.parseInt(request.getParameter("sid")));
  sicb.go();
  PageMakeupBean pmb = new PageMakeupBean(db);
  int pos = 0;
  for(int i = 0; i < pmb.getsize(); i++) pos = pmb.getposition() + 1;
  SetPageMakeupBean spmb = new SetPageMakeupBean(db);
  spmb.setpage(Integer.parseInt(request.getParameter("page")));
  spmb.setposition(pos);
  spmb.settype(1);
  spmb.setcid(cid);
  spmb.go();
}
} %>
