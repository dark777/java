

package com.ondelette.servlet.webannonces;

import java.util.*;
import java.io.*;

public class AnnoncesFolderFactory {

  public static long mDelay = 60 * 60 * 1000;
  public static int mSize = 20;
  private static Hashtable mAnnoncesFolderTable = new Hashtable();

  public static AnnoncesFolder getAnnoncesFolder(File file) {
    AnnoncesFolder af = (AnnoncesFolder) mAnnoncesFolderTable.get(file);
    if (af == null) {
      af = new AnnoncesFolder(mSize, mDelay, file);
      mAnnoncesFolderTable.put(file,af);
    }
    return(af);
  }

  public static void destroy() {
    Enumeration enum = mAnnoncesFolderTable.elements();
    while (enum.hasMoreElements()) {
      AnnoncesFolder af = (AnnoncesFolder) enum.nextElement();
      af.destroy();
    }
  }

} 