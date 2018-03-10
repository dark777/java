<%@ page info="SPSIntranet Page Displayer" %>
<%@ page import="org.haywired.*" %>
<%@ page import="org.haywired.firefly.*" %>
<% DBSettingsBean db = new DBSettingsBean();
db.setdb("SPS");
GroupsBean sb = new GroupsBean(db); %>
