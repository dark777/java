package com.ondelette.servlet.webannonces;

import java.util.*;
import java.io.*;
import com.ondelette.servlet.*;

public class AnnoncesFolder implements Runnable {

  private Vector mAnnoncesVector = new Vector();
  private int mSize;
  private long mDelay;
  protected File mFolderfile;
  private Thread mSaveThread;
  private boolean mHasChanged = false;
  private int mMaxID = -1;
  private AnnoncesLocale mAnnoncesLocale;


  public AnnoncesFolder(int size, long delay, File Folderfile) {
    File localefile = new File("none");
    mAnnoncesLocale = AnnoncesLocaleFactory.getAnnoncesFolder(localefile);
    mSize = size;
    mFolderfile = Folderfile;
    mDelay = delay;
    mSaveThread = new Thread(this);
    mHasChanged = false;
    try {
      read();
    } catch (IOException ioe) {
      System.err.println("Could not read AnnoncesFolder "+ioe.toString());
    }
    mSaveThread.setPriority(Thread.MIN_PRIORITY);
    mSaveThread.start();
  }

  public synchronized void add(Annonce annonce) {
    annonce.setID(++mMaxID);
    mAnnoncesVector.insertElementAt(annonce, 0);
    try {
      mAnnoncesVector.removeElementAt(mSize - 1);
    } catch (ArrayIndexOutOfBoundsException aioobe) {}
    mHasChanged = true;
  }

  public void remove(int id) {
     Enumeration enum = getAnnonces();
     while (enum.hasMoreElements()) {
      Annonce a = (Annonce) enum.nextElement();
      if(a.getID() == id) {
        mAnnoncesVector.removeElement(a);
        mHasChanged = true;
        return;
      }
     }
  }

  public Enumeration getAnnonces() {
    return( mAnnoncesVector.elements() );
  }


  public synchronized void write() throws IOException  {
     BufferedOutputStream bos = new BufferedOutputStream(
        new FileOutputStream(mFolderfile));
     try {
       Enumeration enum = getAnnonces();
       while (enum.hasMoreElements()) {
        Annonce a = (Annonce) enum.nextElement();
        a.write(bos);
       }
       bos.flush();
     } catch (IOException ioe) {
       System.err.println("Could not do maintenance in AnnoncesFolder! "+ioe.toString());
     } finally {
       bos.close();
     }
     mHasChanged = false;
  }

  private synchronized void read() throws IOException {
     mMaxID = 0;
     BufferedInputStream bis = new BufferedInputStream(
        new FileInputStream(mFolderfile));
    try {
      while(bis.available() > 0) {
        Annonce a = Annonce.read(bis);
        if(a != null) {
          mAnnoncesVector.addElement(a);
          mMaxID = mMaxID > a.getID() ? mMaxID : a.getID();
        }
      }
    } finally {
      bis.close();
    }
  }

  public void run() {
    Thread me = Thread.currentThread();
    while (me == mSaveThread) {
      try {
        me.sleep(mDelay);
      } catch (InterruptedException ie) {}
      if(mHasChanged)
        try {
          write();
        } catch (IOException ioe) {
          System.err.println("Could not do maintenance in AnnoncesFolder! "+ioe.toString());
        }
    }
  }

  public void destroy() {
    mSaveThread.interrupt();
    mSaveThread = null;
    if(mHasChanged) {
      try {
        write();
      } catch (IOException ioe) {
        ioe.printStackTrace();
      }
    }
  }

  public void show(PrintWriter out) throws IOException {
    mAnnoncesLocale.printHeader(out);
    mAnnoncesLocale.print(this,out);
    mAnnoncesLocale.printForm(this,out);
    ServletCopyright.printFooter(out);
  }

  public void showAdmin(PrintWriter out) throws IOException {
    mAnnoncesLocale.printHeader(out);
    mAnnoncesLocale.printAdmin(this,out);
    mAnnoncesLocale.printForm(this,out);
    ServletCopyright.printFooter(out);
  }

}
