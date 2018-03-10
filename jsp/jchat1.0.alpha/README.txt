Bate Papo em JSP
JChat Servlet version 1.0 alpha é um chat de batepapo escrito
completamente em Java que usa o Java Servlet API. 
JChat é um servlet experimental projetado para prover conversa forma simples, 
rápida e o código de fonte está livremente disponível. 
A implementação atual armaze.
https://www.scriptbrasil.com.br/script/jsp/jchat1.0alpha.zip

To install the servlet "as is" simply follow the installation instructions 
below.  Feel free to modify the source code to suite your needs.

Warranty:
This software is provided as is without warranty.  Use at your own risk.

Copyright Information:
JChat Servlet falls under the GNU GENERAL PUBLIC LICENSE (see license.txt 
included with this distribution).


Installation:
1. Extract the class files (c:\jchat1.0.alpha\classes is the default directory)
2. Copy the classes to your servlets directory.  Make sure you keep the 
same directory structure as below:
   
   ../servlets/
         |--/Srv_JChat.class
         |--/org
              |--/ansurgen
                     |--/jchat
                          |--/Channel.class
                          |--/User.class
                          |--/Message.class
                          |--/PrivateMessage.class

3. Point your browser to http://server:port/servlets/Srv_JChat.  The URL 
will vary depending on your web server and configuration, mine are as follows:
   
   //my development machine
   http://localhost:8080/servlet/Srv_JChat
   
   //through my ISP
   http://www.ansurgen.org/servlets/Srv_JChat



Compiling the source:
1. Extract the source files (c:\jchat1.0.alpha\source is the default directory)
2. cd c:\jchat1.0.alpha\source\org\ansurgen\jchat (or wherever you unzip the source)
3. javac Srv_JChat.java -d c:\javawebserver1.1\servlets (or wherever your servlets directory is)



Support & Feedback:
Feel free to contact me with any questions or comments you may have.  If 
you need help please include the following information:
1. Your OS (Win 95/NT, Linux, Solaris, etc)
2. Your webserver mfg and version (JavaWebServer1.1, Apache 1.3.1, 
Netscape 3.5.1, etc)
3. Your servlet engine and version (JRun2.2, JavaWebServer1.1, etc)


@author Tauasa Timoteo
@contact ttimoteo@ansurgen.org


