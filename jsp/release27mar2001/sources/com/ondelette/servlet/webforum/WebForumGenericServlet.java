

package com.ondelette.servlet.webforum;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

/**********
 * This servlet is a low-level version
 * of the WebForumServlet. It is meant
 * to be used with the SwingForumApplet
 */
public class WebForumGenericServlet extends GenericServlet implements GenericServletInterface {

  private final static int ERROR = -9999;
  private final static boolean DEBUG = true;

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
    if(DEBUG) System.err.println("Servicing...");
    try {
      char c1, c2;
      int i;
      InputStream is = request.getInputStream();
      DataInputStream dis = new DataInputStream(is);
      String forumfilestring = "";
      try {
        if(DEBUG) System.err.println("applet max index = "+dis.readInt());
        int length = dis.readInt();
        if(DEBUG) System.out.println("reading "+length+" chars...");
        StringBuffer sb = new StringBuffer();
        for (int k = 0; k < length ; k++)
          sb.append(dis.readChar());
        forumfilestring = sb.toString();
        if(DEBUG) System.err.println("read..."+forumfilestring);
        c1 = dis.readChar();
        if(DEBUG) System.err.println("read..."+c1);
        c2 = dis.readChar();
        if(DEBUG) System.err.println("read..."+c2);
        i = dis.readInt();
        if(DEBUG) System.err.println("read..."+i);
      } catch (IOException ioe) {
        ioe.printStackTrace();
        dis.close();
        throw ioe;
      }
      //
      // we check the forumfile info
      //
      Forum forum = null;

      try {
          forum = ForumFactory.getForum(new File(forumfilestring));
      } catch (Exception e) {
          response.setContentType("application/octet-stream");
          OutputStream os = response.getOutputStream();
          DataOutputStream dos = new DataOutputStream(os);
          if(DEBUG) System.err.println("No such forum!");
          dos.writeInt(-1);
          dos.flush();
          return;
      }
      //
      // we check if we need to read more
      //
      int newid = -1;
       switch (c1) {
           case PUT:
            newid = recover(c2,i,is,forum);
            break;
           default:
            break;
        }

      //
      // Ok, we don't need to read anymore
      //
      if(DEBUG) System.err.println("writing...");
      // response
      response.setContentType("application/octet-stream");
      OutputStream os = response.getOutputStream();
      DataOutputStream dos = new DataOutputStream(os);

      try {
        if(DEBUG) System.err.println("Writing "+forum.mMessageFolder.getCurrentMaxIndex());
        dos.writeInt(forum.mMessageFolder.getCurrentMaxIndex());
        if(DEBUG) System.err.println("Writing "+forumfilestring);
        dos.writeInt(forumfilestring.length());
        for (int k = 0; k < forumfilestring.length() ; k++) {
          dos.writeChar(forumfilestring.charAt(k));
        }
        dos.flush();
      } catch (IOException ioe) {
        ioe.printStackTrace();
        dos.close();
        dis.close();
        throw ioe;
      }

      try {
        // ok
        switch (c1) {
         case GET:
            goGet(c2,i,os,forum);
            break;
         case PUT:// something was submitted
            goPut(c2,i,newid,os,forum);
            break;
         default:
            break;
        }
      } finally {
        os.flush();
//        os.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //
  // we check if we need to read more
  //
  private int recover(char c, int parentID,  InputStream is, Forum forum)
      throws IOException {
      switch (c) {
        case MESSAGE :
          ;
          ObjectInputStream ois = new ObjectInputStream(is);
          try {
            if(DEBUG) System.out.println("Reading message...");
            Message m = (Message) ois.readObject();
            final MessageReference newmr = forum.addMessage( m );
            return(newmr.getID());
          } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
          }

        default:
          break;
      }
      return(ERROR);
  }


  /*
  * Mostly a confirmation just to acknowledge the transaction
  */
  private void goPut(char c, int i, int newid , OutputStream os, Forum forum)
      throws IOException {

      switch (c) {
        case MESSAGE :
          DataOutputStream dos = new DataOutputStream (os);
          if(DEBUG) System.err.println("writing GET");
          dos.writeChar(GET);
          if(DEBUG) System.err.println("writing MESSAGE");
          dos.writeChar(MESSAGE);
          if(DEBUG) System.err.println("writing "+i);
          dos.writeInt(i);// should be parent id (just for check)
          dos.writeInt(newid);// should be the id of the new message
          if(DEBUG) System.out.println("DONE!");
          dos.writeChar(END);
          dos.flush();
          break;
        default:
          break;
      }
  }
  /*
  * Processes requests coming in from the client.
  */
  private void goGet(char c, int i,  OutputStream os, Forum forum)
      throws IOException {

      switch (c) {
        case PAGE :

          int[] iarray = forum.mMessageFolder.getPage(i);
          DataOutputStream dos = new DataOutputStream(os);
          if(DEBUG) System.err.println("writing PUT");
          dos.writeChar(PUT);
          if(DEBUG) System.err.println("writing PAGE");
          dos.writeChar(PAGE);
          if(DEBUG) System.err.println("writing "+i);
          dos.writeInt(i);
          //dos.flush();
          if(DEBUG) System.err.println("writing array");
          ObjectOutputStream oos = new ObjectOutputStream(os);
          oos.writeObject(iarray);
          //oos.flush();
          sendTree(iarray,os, forum);
          if(DEBUG) System.out.println("DONE!");
          dos.writeChar(END);
          break;

        case MESSAGE :
          sendMessage(i,os,forum);
          os.flush();
          if(DEBUG) System.out.println("DONE!");
          dos = new DataOutputStream(os);
          dos.writeChar(END);
          dos.flush();
          break;
        default:
          break;

      }
  }

  private void sendMessage(int messagenumber, OutputStream os, Forum forum) throws IOException {
          Message m = forum.mMessageFolder.getMessage(messagenumber);
          DataOutputStream dos = new DataOutputStream (os);
          if(DEBUG) System.err.println("writing PUT");
          dos.writeChar(PUT);
          if(DEBUG) System.err.println("writing MESSAGE");
          dos.writeChar(MESSAGE);
          if(DEBUG) System.err.println("writing "+messagenumber);
          dos.writeInt(messagenumber);
          dos.flush();
          if(DEBUG) System.err.println("writing "+m.toString());
          ObjectOutputStream oos = new ObjectOutputStream (os);
          oos.writeObject(m);
          oos.flush();
  }

  private void sendTree(int[] pagearray, OutputStream os, Forum forum) throws IOException {
      for(int k = 0; k < pagearray.length ; k++) {
          sendMessageFully(pagearray[k],os,forum);
      }
  }

    private void sendMessageFully(int index, OutputStream os, Forum forum) throws IOException{
      Message m = forum.mMessageFolder.getMessage(index);
      sendMessage(index,os,forum);
      Enumeration enum = m.getReplies();
      while (enum.hasMoreElements()) {
        MessageReference mr = (MessageReference) enum.nextElement();
        sendMessageFully(mr.getID(),os,forum);
      }
    }
}
