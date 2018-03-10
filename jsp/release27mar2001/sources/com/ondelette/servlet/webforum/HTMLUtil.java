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

/*********************
* cette classe sert uniquement à manipuler
* du texte (de type HTML) efficacement.
**********************/
public final class HTMLUtil {

  public static String readString (InputStream in) throws IOException {
    // StringWriter sw = new StringWriter();
    StringBuffer ans = new StringBuffer();
    while ((ans.length() == 0) && (in.available() > 0)) {
      for( int r = in.read();(r!= '<') && (r!= '>') && (r != -1) ; r = in.read()) {
        ans.append((char)r);
      }
    }
    return(ans.toString());
  }


   /*****************
   * Le constructeur est privé
   ******************/
   private HTMLUtil() {
   }

   /*******************
   * Compte le nombre de lignes
   ********************/
   public static int getNumberOfLines (String s) {
      if(s == null)
        return (0);
      if(s.length() < 2)
        return(0);
      int NumberOfLines = 0;
      for(int st = 0; st < s.length() - 1; ++st) {
        if(s.charAt(st)== '\r') {
          if(s.charAt(st + 1) != '\n')
            ++NumberOfLines;
        } else if ( s.charAt(st)== '\n') {
           ++NumberOfLines;
        }
      }
      if ((s.charAt(s.length() - 1) == '\n') || (s.charAt(s.length() - 1) == '\r'))
         ++NumberOfLines;
      return(NumberOfLines + 1);
   }


   public static String replaceLinesWithParagraphs (String s) {
      if( s == null)
        return(null);
      if(s.length() < 2)
        return(s);
      StringBuffer sb = new StringBuffer();
      char c;
      for (int pos = 0; pos < s.length() - 1; ++pos) {
        switch ( c = s.charAt(pos)) {
          case '\r' :
            if (s.charAt(pos + 1) != '\n') {
              sb.append("<BR>");
              ++pos;
            }
            break;
          case '\n' :
            sb.append("<BR>");
            break;
          default :
            sb.append(c);
            break;
        }//fin du switch
      }//fin du for
      sb.append( s.charAt(s.length() - 1));
      return(sb.toString());
   }

   public static String makeStringHTMLSafe(String str) {
   	  StringBuffer s = new StringBuffer();
      char c;
      for(int pos = 0; pos < str.length(); ++pos) {
        switch ( c = str.charAt(pos)) {
          case '<' :
            s.append("&lt;");
            break;
          case '>' :
            s.append("&gt;");
            break;
          case '"' :
            s.append("&quot;");
            break;

          default :
            s.append(c);
            break;
        }
      }
	  return(s.toString());
   }

   public static String recoverHTML(String str) {
      if(str== null)
        return(null);
      if(str.length() < 3)
        return(str);
      StringBuffer outs = new StringBuffer();
      int pos;
      for(pos = 0; pos < str.length() - 3; ++pos) {
	      if ( str.startsWith("&lt;",pos) ) {
                outs.append('<');
                pos += 3;
         } else if ( str.startsWith("&gt;",pos) ) {
                outs.append('>');
                pos += 3;
         } else if (str.startsWith("&quot;",pos) ) {
                outs.append('"');
                pos += 5;
			    } else {
                outs.append( str.charAt(pos) );
          }
      }
      for(int k = pos; k < str.length(); k++) {
        outs.append( str.charAt(k) );
      }
	    return(outs.toString());
   }
}
