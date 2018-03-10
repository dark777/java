

package com.ondelette.servlet.webannonces;

import java.util.*;
import java.io.*;

public class AnnoncesLocaleFactory {

  private static Hashtable mAnnoncesLocaleTable = new Hashtable();

  public static AnnoncesLocale getAnnoncesFolder(File file) {
    AnnoncesLocale al = (AnnoncesLocale) mAnnoncesLocaleTable.get(file);
    if (al == null) {
      al = new AnnoncesLocale(file);
      mAnnoncesLocaleTable.put(file,al);
    }
    return(al);
  }

}
