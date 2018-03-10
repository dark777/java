package org.haywired.firefly.sort;

//: StrSortVector.java
// Automatically sorted Vector that 
// accepts and produces only Strings
import java.util.*;

public class StrSortVector {
  private SortVector v = new SortVector(
    // Anonymous inner class:
    new Compare() {
      public boolean 
      lessThan(Object l, Object r) {
       return ((PageCountData)l).count > ((PageCountData)r).count;
      }
      public boolean 
      lessThanOrEqual(Object l, Object r) {
       return ((PageCountData)l).count >= ((PageCountData)r).count;
      }
    }
  );
  private boolean sorted = false;
  public void addElement(PageCountData s) {
    v.addElement(s);
    sorted = false;
  }
  public PageCountData elementAt(int index) {
    if(!sorted) {
      v.sort();
      sorted = true;
    }
    return (PageCountData)v.elementAt(index);
  }
  public Enumeration elements() {
    if(!sorted) {
      v.sort();
      sorted = true;
    }
    return v.elements();
  }
  // Test it:
  public static void main(String[] args) {
    StrSortVector sv = new StrSortVector();
    sv.addElement(new PageCountData(0, 1));
    sv.addElement(new PageCountData(5, 7));
    sv.addElement(new PageCountData(7, 17));
    sv.addElement(new PageCountData(9, 38));
    sv.addElement(new PageCountData(6, 15));
    sv.addElement(new PageCountData(8, 27));
    sv.addElement(new PageCountData(2, 2));
    sv.addElement(new PageCountData(3, 4));
    Enumeration e = sv.elements();
    while(e.hasMoreElements())
      System.out.println(((PageCountData)e.nextElement()).page);
  }
} ///:~
