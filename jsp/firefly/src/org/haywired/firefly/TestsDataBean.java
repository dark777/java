package org.haywired.firefly;

public class TestsDataBean {
/* @version 1.5.1 */
private int tid;
private int sid;
private String title;
private int ttime;
public TestsDataBean(int tida, int sida, String titlea, int ttimea) {
tid = tida;
sid = sida;
title = titlea;
ttime = ttimea;
}
public int gettid() {
return tid;
}
public int getsid() {
return sid;
}
public String gettitle() {
return title;
}
public int gettime() {
return ttime;
}
}
