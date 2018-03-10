/*************
*   WebForum
*   (c) 2000 Daniel Lemire
*   Daniel.Lemire@Videotron.ca, http://www.ondelette.com/
*
*
*   Ce programme est libre, vous pouvez le redistribuer et/ou le modifier selon les termes de la Licence
*   Publique Générale GNU publiée par la Free Software Foundation.
*
*   Ce programme est distribué car potentiellement utile, mais SANS AUCUNE GARANTIE, ni explicite ni implicite,
*   y compris les garanties de commercialisation ou d'adaptation dans un but spécifique. Reportez-vous à la
*   Licence Publique Générale GNU pour plus de détails.
*
*   Vous devez avoir reçu une copie de la Licence Publique Générale GNU en même temps que ce programme ; si ce
*   n'est pas le cas, écrivez à la Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
*   02111-1307, États-Unis.
*
***************************************
*
*   This program is free software; you can redistribute it and/or
*   modify it under the terms of the GNU General Public License
*   as published by the Free Software Foundation (version 2).
*
*   This program is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*
*   You should have received a copy of the GNU General Public License
*   along with this program; if not, write to the Free Software
*   Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*
********************************************/
package com.ondelette.servlet.webforum;

import java.io.*;
import java.util.*;
import javax.servlet.*;


/********************
* Cette classe gère les messages et sert
* en fait de mémoire tampon, enregistrant
* les messages au besoin et allant les
* chercher sur le disque, aussi au besoin.
* La classe MessageArchiveBuffer est utilisée
* pour l'accès au disque.
*********************/

final public class MessageFolder {

  protected Forum mForum;
  protected MessageArchiveBuffer mMAB;

  public MessageFolder(Forum forum) throws IOException {
    mForum = forum;
    mMAB = new MessageArchiveBuffer(mForum.mMessageArchiveDirectory,mForum.mSaveDelay);
  }

  public Message getMessage( int ID) throws IOException {
    return(mMAB.getMessage(new MessageReference(ID)));
  }

  public int getNumberOfPages() {
    int number = (int) Math.ceil( mMAB.mHeaders.size() /(double) mForum.mForumLocale.mSizeOfForumFolders);
    return(number);
  }

  public void deleteMessage(MessageReference mr) throws IOException {
      mMAB.deleteMessage(mr);
  }
  public void deleteMessage(int mr) throws IOException {
      mMAB.deleteMessage(new MessageReference(mr));
  }

  private synchronized void deleteLastPage() {
    //
    // no archives at this point
    // we simply throw everything away!
    //
    final int pagenumber = getNumberOfPages() - 1;
    final int max = mMAB.mHeaders.size();
    final int begin = mForum.mForumLocale.mSizeOfForumFolders * pagenumber;
    for ( int messagepointer =  begin;
      messagepointer < max; messagepointer++) {
        MessageReference mr = (MessageReference) mMAB.mHeaders.elementAt(begin);
        try {
          mMAB.deleteMessage(mr);
        } catch (IOException ioe) {
          ioe.printStackTrace();
        }
    }
  }


  public int[] getPage(int pagenumber) {
    if (mForum.mForumLocale.mSizeOfForumFolders * pagenumber > mMAB.mHeaders.size() - 1)
        return (null);
    int min = mForum.mForumLocale.mSizeOfForumFolders * (pagenumber + 1);
    min = min > mMAB.mHeaders.size() ? mMAB.mHeaders.size() : min;
    int begin = mForum.mForumLocale.mSizeOfForumFolders * pagenumber;
    int[] ans = new int[min - begin];
    for ( int messagepointer =  begin; messagepointer < min; messagepointer++) {
        MessageReference mr = (MessageReference) mMAB.mHeaders.elementAt(messagepointer);
        ans[messagepointer - begin] = mr.getID();
    }
    return(ans);
  }


  public void writePageSummaryHTML(int pagenumber, PrintWriter out, String ServletPath) throws IOException {
    if (mForum.mForumLocale.mSizeOfForumFolders * pagenumber > mMAB.mHeaders.size() - 1)
      return;
    int min = mForum.mForumLocale.mSizeOfForumFolders * (pagenumber + 1);
    min = min > mMAB.mHeaders.size() ? mMAB.mHeaders.size() : min;
    out.print("<UL CLASS=\"POSTS\">");
    for ( int messagepointer =  mForum.mForumLocale.mSizeOfForumFolders * pagenumber;
      messagepointer < min; messagepointer++) {
        out.print("<LI CLASS=\"POSTS\">");
        MessageReference mr = (MessageReference) mMAB.mHeaders.elementAt(messagepointer);
        mForum.mForumLocale.printSummary(mr.getID(), mForum, out, ServletPath);
        out.print("</LI>");
    }
    out.print("</UL>");
  }

  public void writeLastMessagesSummaryHTML(int NumberOfMessages, PrintWriter out, String ServletPath) throws IOException {
    int[] LastMessages = mMAB.lastMessages(NumberOfMessages);
    out.print("<UL CLASS=\"POSTS\">");
    for ( int messagepointer =  0;
      messagepointer < LastMessages.length; messagepointer++) {
        out.print("<LI CLASS=\"POSTS\">");
        mForum.mForumLocale.printSummaryWithoutReplies(LastMessages[messagepointer], mForum, out, ServletPath);
        out.print("</LI>");
    }
    out.print("</UL>");
  }

  public MessageReference addMessage(Message message) throws IOException {
    if(message.getInReplyTo() < 0) {
      if (mMAB.mHeaders.size() / mForum.mForumLocale.mSizeOfForumFolders  >= mForum.mForumLocale.mNumberOfFolders + 1)
        deleteLastPage();
      return(mMAB.addMessage(message, true));
    }
    MessageReference parentmr = new MessageReference(message.getInReplyTo());
    Message parentmessage = parentmr.getMessage(this);
    MessageReference newmr= mMAB.addMessage(message, false);
    parentmessage.addReply(newmr);
    mMAB.replaceMessage(parentmr,parentmessage);
    return(newmr);
  }

  public void destroy(){
    mMAB.destroy();
  }
  public int getCurrentMaxIndex() {
    return(mMAB.mCurrentMaxIndex);
  }
}

/********************************************************
* New private class
*********************************************************/
final class MessageArchiveBuffer implements Runnable, AutorizationConstants, FixedNumericalConstants {

  private File mDirectory;
  protected int mCurrentMaxIndex;
  protected Vector mHeaders = new Vector();
  private Hashtable mBuffer = new Hashtable();
  private Hashtable mOldBuffer = new Hashtable();
  private Vector mDeleted = new Vector();
  private Vector mModified = new Vector();
  private Thread mMaintenanceThread;
  private long mDelay;
  private boolean mHeaderHasBeenChanged = false;

  public int[] lastMessages(int NumberOfMessages) {
    int[] messages = new int[NumberOfMessages];
    int allocated = 0;
    int index = mCurrentMaxIndex -1;
    while ((allocated < messages.length) && (index >= 0)) {
       Message m = getMessage(new MessageReference(index));
       if(m!=null)
          if(m.getAutorizationLevel() >= 0)
            messages[allocated++] = index;
       index--;
    }
    if(allocated < messages.length) {
      int[] messages2 = new int[allocated];
      System.arraycopy(messages,0,messages2,0,allocated);
      messages = messages2;
    }
    return(messages);
  }

  public MessageArchiveBuffer (File dir, long delay) {
    mDirectory = dir;
    mDelay = delay;
    try {
          readHeaders();
    } catch (IOException ioe) {
          mCurrentMaxIndex = 0;
          mHeaderHasBeenChanged = true;
          try {
            doMaintenance();
          } catch (IOException ioe2) {
            ioe.printStackTrace();
          }
    }
    mMaintenanceThread = new Thread (this);
    mMaintenanceThread.setPriority(Thread.MIN_PRIORITY);
    mMaintenanceThread.start();
  }

  private void readHeaders() throws IOException {
    mDirectory.mkdirs();
    File indexfile = new File(mDirectory,"index.txt");
    // create the file if it doesn't exist

//    FileOutputStream fos = new FileOutputStream(indexfile.getCanonicalPath());
//    fos.close();
    // read the file
    BufferedReader br = new BufferedReader(
            new InputStreamReader(
              new FileInputStream(indexfile)
            )
      );
    try {
      String line;
      try {
        mCurrentMaxIndex = Integer.parseInt(br.readLine());
      } catch (NumberFormatException nfe) {
        mCurrentMaxIndex = 0;
      }
      while((line = br.readLine())!= null) {
        try {
          mHeaders.addElement(new MessageReference(Integer.parseInt(line)));
        } catch (NumberFormatException nfe) {}
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } finally {
      br.close();
    }
  }

  public Enumeration getHeaders() {
    return(mHeaders.elements());
  }

  public Message getMessage(MessageReference mr) {
    Message message;
    if((mr.getID() < 0) || (mr.getID() > mCurrentMaxIndex))
      return null;
    if ( (message= (Message) mBuffer.get(mr)) == null ) {
      if( (message = (Message) mOldBuffer.get(mr)) == null) {
        try {
          final File messagefile = new File(mDirectory, mr.toString());
          BufferedInputStream is = new BufferedInputStream(new FileInputStream(messagefile));
          message = Message.read(is);
          is.close();
        } catch (IOException ioe) {
          return(null);
        }
		    mBuffer.put(mr, message);
        return(message);
      } else {
        mOldBuffer.remove(mr);// free memory
      }
      mBuffer.put(mr, message);
    }
    return(message);
  }

  public synchronized MessageReference addMessage(Message message, boolean newHeader) {
    MessageReference mr = new MessageReference(mCurrentMaxIndex);
    //il faut d'abord s'assurer que ce message n'existe pas!!!
    while(getMessage(mr) != null)
      mr = new MessageReference(mCurrentMaxIndex++);
    mBuffer.put(mr,message);
    if (newHeader) {
      mHeaders.insertElementAt(mr,0);

    }
    mCurrentMaxIndex++;
    if(mCurrentMaxIndex > MAX_NUMBER_OF_MESSAGES_PER_FORUM)
      mCurrentMaxIndex = 0;
    mModified.addElement(mr);
    mHeaderHasBeenChanged = true;
    return(mr);
  }

  public void replaceMessage(MessageReference mr, Message message) {
    mBuffer.put(mr,message);
    mModified.addElement(mr);
  }



  public void deleteMessage(MessageReference mr) throws IOException {
    Message message = getMessage(mr);
    Enumeration enum = message.getReplies();
    while(enum.hasMoreElements()) {
      final MessageReference repliesmr = (MessageReference) enum.nextElement();
      deleteMessage(repliesmr);
    }
    message.setAutorizationLevel(DELETED);
    if(mHeaders.removeElement(mr)) {
      mHeaderHasBeenChanged = true;
    } else {
      if(message.getInReplyTo()!= -1) {
        MessageReference parentmr = new MessageReference(message.getInReplyTo());
        Message parentmessage = getMessage(parentmr);
        parentmessage.removeReply(mr);
        this.replaceMessage(parentmr,parentmessage);
      }
    }
    mDeleted.addElement(mr);
  }

  public void run () {
    Thread me = Thread.currentThread();
    while (me == mMaintenanceThread) {
      try {
        me.sleep(mDelay * 1000 * 60);
      } catch (InterruptedException ie ) {}
      try {
        doMaintenance();
      } catch (IOException ioe)  {
        ioe.printStackTrace();
      }
    }
  }

  public void  destroy() {
    mMaintenanceThread.interrupt();
    mMaintenanceThread = null;
    // on s'assure d'essayer au moins un fois d'enregistrer
    // (même si on risque, en partique, d'enregistrer deux
    // fois plutôt qu'un.
    try {
      doMaintenance();
    } catch (IOException ioe) {
      System.err.println("Could do not do maintenance in MessageArchiveBuffer "+ioe.toString());
    }
  }

  private void doMaintenance() throws IOException {
     // save headers
     if (mHeaderHasBeenChanged)
       saveHeaders();
    // go through the messages
    saveModifiedMessages();
    deleteDeletedMessages();
    // clean up memory
    synchronized(this) {
      mOldBuffer = mBuffer;
      mBuffer = new Hashtable();
    }
  }

  private synchronized void saveHeaders() throws IOException {
    File indexfile = new File(mDirectory,"index.txt");
    PrintWriter pw = new PrintWriter(new BufferedOutputStream(new FileOutputStream(indexfile)));
    try {
      pw.println( mCurrentMaxIndex);
      Enumeration enum = getHeaders();
      while (enum.hasMoreElements()) {
        final MessageReference mr = (MessageReference) enum.nextElement();
        pw.println(mr.toString());
      }
    } finally {
      pw.close();
    }
    mHeaderHasBeenChanged = false;
  }

  private void saveModifiedMessages() throws IOException {
    Vector OldModified;
    synchronized (this) {
      OldModified = (Vector) mModified.clone();
      mModified = new Vector();
    }
    Enumeration enum = OldModified.elements();
    while (enum.hasMoreElements()) {
      MessageReference mr = (MessageReference) enum.nextElement();
      Message message = getMessage(mr);
      BufferedOutputStream bos = new BufferedOutputStream(
        new FileOutputStream(new File(mDirectory,mr.toString()))
      );
      try {
        message.write(bos);
        bos.flush();
      } finally {
        bos.close();
      }
    }
  }


  private void deleteDeletedMessages() throws IOException {
    Vector OldDeleted;
    synchronized (this) {
      OldDeleted = (Vector) mDeleted.clone();
      mDeleted = new Vector();
    }
    Enumeration enum = OldDeleted.elements();
    while (enum.hasMoreElements()) {
      MessageReference mr = (MessageReference) enum.nextElement();
      File file = new File(mDirectory,mr.toString());
      if(!file.delete())
        System.out.println("could not delete file "+file.getCanonicalPath());
    }
  }
}
