package org.haywired;
public class SearchandReplace {
  public static String doSearchReplace(String StringToBeSearched, String searchstring, String replacement) {
    String newstring = ""; 
    String temp1string = "";
    if (StringToBeSearched != null) {
      int ptr = StringToBeSearched.indexOf(searchstring); 
      while(StringToBeSearched.indexOf(searchstring) != -1) {
        ptr = StringToBeSearched.indexOf(searchstring);
        temp1string = StringToBeSearched.substring(0,ptr) + replacement; //the first part 
        StringToBeSearched = StringToBeSearched.substring((ptr+1),StringToBeSearched.length()); //the 2nd part
        newstring=newstring+temp1string;
      }
      newstring = newstring+StringToBeSearched;
    }
    return (newstring);
  }
  public static void main(String args[]) {
    System.out.println(doSearchReplace(args[0], args[1], args[2]));
  }
}
