<%!
	/**--------------------------------------------------------------*/
	/**        MODIFY THE FILENAME BELOW TO YOUR jchatbox.xml        */
	/**                                                              */
	/** Samples  :                                                   */
	/** For Win32 OS => c:/apache/jchatbox/conf/jchatbox.xml         */
	/** For Un*x OS  => /home/alice/jchatbox/conf/jchatbox.xml       */
	/** For Max OS   => MAC_HD:webserver:jchatbox:conf:jchatbox.xml  */
	/**--------------------------------------------------------------*/

	String XMLjChatBox = "D:/tomcat/webapps/jchatbox/conf/jchatbox.xml";

	// Overides jspInit method. Do not modify.
	public void jspInit()
	{
		jChatBox.Util.XMLConfig.init(XMLjChatBox);
	}
%>
