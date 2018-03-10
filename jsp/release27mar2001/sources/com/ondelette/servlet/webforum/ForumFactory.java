

package com.ondelette.servlet.webforum;

import java.util.*;
import java.io.*;

public class ForumFactory {
  private static Hashtable mForumList = new Hashtable();

  // un forum "Factory"
  public static Forum getForum(File ForumFile) throws IOException {
    Forum forum;
    if( (forum = (Forum) mForumList.get(ForumFile) ) == null) {
      forum = new Forum(ForumFile);
      mForumList.put(ForumFile, forum);
    }
    return(forum);
  }



  public static void destroy() {
    Enumeration enum = mForumList.elements();
    while (enum.hasMoreElements()) {
      Forum thisforum = (Forum) enum.nextElement();
      thisforum.destroy();
      thisforum = null;
    }
    mForumList = new Hashtable();
  }

}