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
import java.lang.Runnable;

final public class UserList implements Runnable{
  private Hashtable mLoginTable = new Hashtable();
  private boolean mHasChanged = false;
  private File mUserListFile;
  private Thread mSaveThread;
  private long mDelay;

  public UserList(File file, long delay) {
    mDelay = delay;
    try {
      synchronized(this) {
          BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
          while(bis.available() > 0) {
            User user = User.read(bis);
            if( user != null) {
              mLoginTable.put(user.getLogin(),user);
            }
          }
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
      //nothing to do here
    }
    mUserListFile = file;
    mSaveThread = new Thread(this);
    mSaveThread.setPriority(Thread.MIN_PRIORITY);
    mSaveThread.start();
  }

  public Enumeration getUsers() {
    return(mLoginTable.elements());
  }

  public User getUser(String login, String password) {
    if(login == null)
      return(null);
    Object o;
    if((o = mLoginTable.get(login)) == null) {
      if((o = mLoginTable.get(HTMLUtil.makeStringHTMLSafe(login))) == null) {
        return(null);
      }
    }
    final User user = (User) o;
    if(user.checkPassword(password)) {
      return(user);
    }
    return(null);
  }

  public User getUser(String login) {
    if(login == null)
      return(null);
    Object o;
    if((o = mLoginTable.get(login)) == null) {
      if((o = mLoginTable.get(HTMLUtil.makeStringHTMLSafe(login))) == null) {
        return(null);
      }
    }
    return((User) o);
  }

  public void add(User user) {
    if(user == null)
      return;
    mLoginTable.put(user.getLogin(),user);
    mHasChanged = true;
  }

  public boolean isInList(User user) {
    if(user == null)
      return (false);
    return(mLoginTable.contains(user));
  }

  public void remove(String login) {
    if(login == null)
      return;
    if(mLoginTable.remove(login)==null)
      mLoginTable.remove(HTMLUtil.makeStringHTMLSafe(login));
    mHasChanged=true;
  }

  public boolean isInList(String login) {
    if(login == null)
      return (false);
    if(mLoginTable.containsKey(login))
      return(true);
    return(mLoginTable.containsKey(HTMLUtil.makeStringHTMLSafe(login)));
  }

  public void save() {
    mHasChanged = true;
  }



  private void write() throws IOException {
    if(mHasChanged) {
      synchronized (this) {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(mUserListFile));
        try {
          Enumeration enum = mLoginTable.elements();
          while(enum.hasMoreElements()) {
            User u = (User) enum.nextElement();
            u.write(bos);
            bos.write('\r');//Windows!
            bos.write('\n');
          }
        } catch (IOException ioe) {
          ioe.printStackTrace();
        } finally {
          bos.close();
        }
      }
    }
    mHasChanged = false;
  }

  public void run() {
    Thread me = Thread.currentThread();
    while (me == mSaveThread) {
      try {
        mSaveThread.sleep(mDelay);
      } catch (InterruptedException ie) {}
      try {
        write();
      } catch (IOException ioe) {
        ioe.printStackTrace();
      }
    }
  }



  public void destroy() {
    mSaveThread.interrupt();
    mSaveThread = null;
    // on s'assure d'essayer au moins un fois d'enregistrer
    // (même si on risque, en partique, d'enregistrer deux
    // fois plutôt qu'un.
    try {
      write();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

}