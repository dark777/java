/******************************************
* menubar.js - SPS Intranet Menu          *
* Version 1.0 JJM			     	      *
******************************************/
var isOK = false;
var divHeight = 130;

if (document.all) {
brsInt = parseInt(navigator.appVersion);
if (brsInt > 3) { isOK = true; }
uAgent =  navigator.userAgent;
if (uAgent.indexOf("MSIE 4.")>=1) { isOK = false }
}

if (isOK == true) {

var divLine = '<DIV CLASS="dMenu" STYLE="visibility: hidden; position: absolute; top: ' + divHeight + '; Filter:revealTrans (duration=0.2, transition=5);" ID="hMenu"></DIV>';


var leftX = 0;
var rightX = 0;
var topY = 0;
var botY = 0;

}

function hideMenu() {
if (isOK == true) {
document.all.hMenu.innerHTML = "";
document.all.hMenu.style.visibility = "hidden";
leftX = 0;
leftY = 0;
topY = 0;
botY = 0;
}
}

function shaker() {
if (isOK == true) {
if (leftX > 0) {
if (window.event.clientX < leftX) { hideMenu(); }
if (window.event.clientX > rightX) { hideMenu(); }
if (window.event.clientY < topY) { hideMenu(); }
if (window.event.clientY > botY) { hideMenu(); }
}
}
}

function flasher(showWhich) {

if (isOK == true) {

leftX = 0;
rightX = 0;
topY = 0;
botY = 0;


xPos = window.event.srcElement.offsetLeft + window.event.srcElement.offsetParent.offsetLeft + 16;
divETop = document.all.hMenu.style.posTop - document.body.scrollTop;


textArray = eval(showWhich + "Text");
linkArray = eval(showWhich + "Link");

var upUntil = textArray.length;
var forDoc = "";

for (i = 0; i < upUntil; i++) {
forDoc = forDoc + "<IMG SRC='cross2.gif' BORDER=0> <A HREF='" + linkArray[i] + "'>" + textArray[i] + "</A><BR>";

}

if (forDoc != "") {

document.all.hMenu.innerHTML = forDoc;
document.all.hMenu.style.left = xPos;
document.all.hMenu.filters[0].Apply();
document.all.hMenu.style.visibility = "visible";
document.all.hMenu.filters[0].transition=12;
document.all.hMenu.filters[0].duration=0.2;
document.all.hMenu.filters[0].Play();
leftX = document.all.hMenu.style.posLeft;
rightX = leftX + document.all.hMenu.offsetWidth;
topY = divETop - 10;
botY = topY + document.all.hMenu.offsetHeight + 10;
} } }

function onSearch() {
if (isOK == true) {
document.all.hMenu.style.visibility = "hidden";
document.all.hMenu.filters[0].Stop();
document.all.hMenu.filters[0].Apply();

xPos = window.event.srcElement.offsetLeft + window.event.srcElement.offsetParent.offsetLeft + 16;

forDoc = '<TABLE BORDER=0 STYLE="font-family:Verdana,Arial,Helvetica"><TR><TD COLSPAN=2><IMG SRC="firefly.gif"></TD></TR><TR><TD><FORM ACTION="http://piccadilly/firefly/search.jsp" NAME="formSearchFF"><INPUT TYPE=TEXT NAME="q" VALUE=""><BR><FONT SIZE="-2"><INPUT TYPE=RADIO CHECKED NAME="type" VALUE="i">Intranet<INPUT TYPE=RADIO NAME="type" VALUE="w">Web</FONT></TD><TD><INPUT TYPE=BUTTON ONCLICK="formProcess()" VALUE="Search"></FORM></TD></TR></TABLE><IMG SRC="http://service.bfast.com/bfast/serve?bfmid=27253343&siteid=31702521&bfpage=horizontal" BORDER="0" WIDTH="1" HEIGHT="1" NOSAVE ><FORM ACTION="http://service.bfast.com/bfast/click" NAME="formSearchGG"><INPUT TYPE="hidden" NAME="siteid" VALUE="31702521"><INPUT TYPE="hidden" NAME="bfpage" VALUE="horizontal"><INPUT TYPE="hidden" NAME="bfmid" VALUE="27253343"><INPUT TYPE=HIDDEN name=q size=15 maxlength=255 value=""><INPUT type=HIDDEN name=sa VALUE="Go"></FORM>';

document.all.hMenu.innerHTML = forDoc;
document.all.hMenu.style.left = xPos;
document.all.hMenu.style.visibility = "visible";
document.all.hMenu.filters[0].transition=12;
document.all.hMenu.filters[0].duration=0.1;
document.all.hMenu.filters[0].Play();
leftX = document.all.hMenu.style.posLeft;
rightX = leftX + document.all.hMenu.offsetWidth;
topY = document.all.hMenu.style.posTop - 10 ;
botY = topY + document.all.hMenu.offsetHeight + 10;
}
}