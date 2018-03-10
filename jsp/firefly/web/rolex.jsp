/***********************************
* Rolex 1.1			   *
* (c) JJM			   *
***********************************/

var initial = <%= request.getParameter("time") %>;
var speedVariable = 1;

var timerChunk = '<DIV CLASS="quizTimer" ID="divTime"><FORM NAME="formTR">&nbsp;Time left:<INPUT TYPE=TEXT NAME="textTR" READONLY STYLE="width:35;font-size:8pt" VALUE="Wait..."><BR><DIV STYLE="height: 10; width: 100; background-color: white"><DIV  ID="divStatusBar" STYLE="height: 10; width: 100; background-color: #000066"></DIV></DIV></DIV></FORM>';
var isOK = false;

brsInt = parseInt(navigator.appVersion);
if (brsInt > 3) { if (document.all) { isOK = true } }

speedVariable = speedVariable * 1000;
var tempMin = 0;
var currentCount = initial;
function startCount() {
x = setTimeout("countDown()",speedVariable);
}
function countDown() {
oldValue = currentCount;
if (oldValue > 1) {
currentCount = oldValue - 1;

if (currentCount > 60) {
tempMin = parseInt(currentCount / 60);
tempSec = currentCount - (tempMin * 60);
if (tempSec > 9) {toDisplay = "" + tempMin + ":" + tempSec;}
if (tempSec < 10) {toDisplay = "" + tempMin + ":0" + tempSec;}

} 
else {
toDisplay = "0:" + currentCount;
}

if (isOK == true) {
if (oldValue - 1 < (2 * initial) / 3) { document.all.divTime.style.backgroundColor = "#F8DD1F"; document.all.divTime.style.color = "black";  }
if (oldValue - 1 < initial / 3) { document.all.divTime.style.backgroundColor = "#EE8800"; document.all.divTime.style.color = "white";  }
if (oldValue - 1 < 11) { document.all.divTime.style.backgroundColor = "#CC2200" }
document.all.divStatusBar.style.width = ((oldValue - 1) / initial * 100);
document.forms['formTR'].elements['textTR'].value = toDisplay;
}
else {
window.status="Time remaining: " + toDisplay;
}
x = setTimeout("countDown()",speedVariable);
}
else {
document.forms['formQuiz'].submit();
}
}
function redraw() {
if (isOK) {
document.all.divTime.style.left = document.body.clientWidth - document.all.divTime.scrollWidth - 30 + document.body.scrollLeft;
document.all.divTime.style.top = document.body.clientHeight - document.all.divTime.scrollHeight - 30 + document.body.scrollTop;
y = setTimeout("redraw()",200);
} }