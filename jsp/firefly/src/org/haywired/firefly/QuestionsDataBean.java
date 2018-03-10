package org.haywired.firefly;

public class QuestionsDataBean {
/* @version 1.5.1 */
private int qid;
private int tid;
private String ddesc;
private int answer;
private int explanation;
public QuestionsDataBean(int qida, int tida, String ddesca, int answera, int explanationa) {
qid = qida;
tid = tida;
ddesc = ddesca;
answer = answera;
explanation = explanationa;
}
public int getqid() {
return qid;
}
public int gettid() {
return tid;
}
public String getddesc() {
return ddesc;
}
public int getanswer() {
return answer;
}
public int getexplanation() {
return explanation;
}
}
