<% AuthBean au = new AuthBean(new DBSettingsBean());
String user = au.auth(request);
if(au.checkGroup(-1, user)) { %>