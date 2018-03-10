jChatBox 2.1.1 é uma aplicação de Chat em JSP. O Server Side é 100% JSP. 
Client Side pode ser HTML/JavaScript, Applet ou Flash. 
O usuário de sistema pode abrir e pode controlar multiplas salas de chat. 
Pode administrar os usuários (lista, proiba, kickoff), admim.
https://www.scriptbrasil.com.br/script/jsp/jchatbox2.1.1.zip
Copyright JavaZOOM 1999-2001
http://www.javazoom.net

==========================================================
jChatBox support page :
http://www.javazoom.net/jzservlets/jchatbox/jchatbox.html

JSP Forums :
http://www.javazoom.net/services/forums/index.jsp
==========================================================

* Open documentation/index.html for instructions *


07/02/2001 : v2.1.1
-------------------
jChatBox Add-Ons are available at : http://www.javazoom.net/jzservlets/jchatbox/jchatboxtools.html
  Emoticons : This Add-On is an HTML filter that converts smiley signs into images.
  DBAuth : This Add-On provides a database authentication for jChatBox users.
Bug Fixes :
 - Filter ambiguous class error (Servlets 2.3 API only).

+ WebLogic 6.0 SP2+ support added.
+ Tomcat 4.0 support added.


05/02/2001 : v2.1
-----------------
QUIT feature added to avoid to invalidate HttpSession when user leaves a chatroom.
API improved :
 - Filter API finalized.
 - Filter classes loaded at Runtime to allow developers to add their own filters to jChatBox.
   (Filters are now included in jchatbox.xml)
 - xxxLogin and xxxProcessor have moved to jChatBox.Service package.
Registration message added to "Manage jChatBox" admin section.
Xerces migration to 1.3.1.

+ ServletExec 3.1 support added.
+ Weblogic 5.1 support added.
+ WebAppCabaret.com support added.

Bug Fixes :
 - Apache+Tomcat+IE admin panel mess fixed.
 - Session loss in buffered-framed mode fixed.
 - XML file CRLF loss fixed.
 - JavaScript bug fix under Netscape for Buffered-Framed mode chatrooms,
   (sometimes chatroom's content was blank), css style removed.


04/02/2001 : v2.0
-----------------
jChatBox, 100% JSP Chat Application.
Multiple chatrooms supported.
Filters features (HTML, URL Converter).
HTML/JavaScript skins (mIRC, Classic, j-TV).

+ Tomcat 3.x support.
+ JRun 3.01 support.
+ Resin 1.2.3 support.