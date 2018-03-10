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

<% ResetDB r = new ResetDB(db);
 
  if(!request.getParameter("sessions").equals("on")) {

if(request.getParameter("destroy") != null) {
if(request.getParameter("destroy").equals("on")) r.destroy();
}
if(request.getParameter("create") != null) {
if(request.getParameter("create").equals("on")) r.create();
} 

  } else r.sessions();

%>