package org.haywired.firefly;

public class AnswersDataBean {
/* @version 1.5.1 */
private int aid;
private int qid;
private String ddesc;
public AnswersDataBean(int aida, int qida, String ddesca) {
aid = aida;
qid = qida;
ddesc = ddesca;
}
public int getaid() {
return aid;
}
public int getqid() {
return qid;
}
public String getddesc() {
return ddesc;
}
}
