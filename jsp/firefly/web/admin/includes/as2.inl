<%@ page info="SPSIntranet Page Displayer" %>
<%@ page import="org.haywired.*" %>
<%@ page import="org.haywired.firefly.*" %>

<%@ include file="../includes/auth.inl" %>

<% DBSettingsBean db = new DBSettingsBean();
HttpSession sedb = request.getSession();
if(request.getParameter("db") != null) if(!request.getParameter("db").equals(""))
  sedb.setAttribute("db", request.getParameter("db").toString());
if(sedb.getAttribute("db") != null)
  db.setdb(sedb.getAttribute("db").toString()); %>

<% SetSectionsBean ssb = new SetSectionsBean(db);
if(request.getParameter("sid") != null) ssb.setsid(Integer.parseInt(request.getParameter("sid")));
if(request.getParameter("name") != null) ssb.setname(request.getParameter("name"));
if(request.getParameter("homepage") != null) ssb.sethomepage(Integer.parseInt(request.getParameter("homepage")));

ssb.setparent(Integer.parseInt(request.getParameter("parent")));
System.out.println(Integer.parseInt(request.getParameter("parent")));

if(request.getParameter("colour") != null) ssb.setcolour(request.getParameter("colour"));
ssb.go();

SetPagesBean pa = new SetPagesBean(db);
pa.setpage(ssb.gethomepage());
pa.setsid(ssb.getsid());
pa.settitle(ssb.getname() + " Home");
pa.setlinktitle("Home");
pa.setparent(-1);
pa.setheaderonly(true);
pa.go();

if(request.getParameter("academic") != null) {
	if(request.getParameter("academic").equalsIgnoreCase("on")) {

SetPagesBean pb = new SetPagesBean(db);
pb.setsid(ssb.getsid());
pb.settitle(ssb.getname() + " Department Members");
pb.setlinktitle("Staff Members");
pb.setparent(-1);
pb.setheaderonly(true);
pb.go();

SetPagesBean pc = new SetPagesBean(db);
pc.setsid(ssb.getsid());
pc.settitle(ssb.getname() + " Syllabus");
pc.setlinktitle("Syllabus");
pc.setparent(-1);
pc.setheaderonly(true);
pc.go();

SetPagesBean pd = new SetPagesBean(db);
pd.setsid(ssb.getsid());
pd.settitle(ssb.getname() + " 4th Form Syllabus");
pd.setlinktitle("4th Form");
pd.setparent(pc.getpage());
pd.setheaderonly(true);
pd.go();

SetPagesBean pe = new SetPagesBean(db);
pe.setsid(ssb.getsid());
pe.settitle(ssb.getname() + " GCSE Syllabus");
pe.setlinktitle("GCSE");
pe.setparent(pc.getpage());
pe.setheaderonly(true);
pe.go();

SetPagesBean pf = new SetPagesBean(db);
pf.setsid(ssb.getsid());
pf.settitle(ssb.getname() + " A/AS Level Syllabus");
pf.setlinktitle("A/AS Level");
pf.setparent(pc.getpage());
pf.setheaderonly(true);
pf.go();

	}
}

%>
