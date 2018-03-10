<p><center>
  <hr width="100%" size=1 color="black"><span class="sizer"><strong>Add Content</strong></span><hr width="100%" size=1 color="black">

<STYLE>
  #editBar  {position: absolute; display: none; width: 100%; border: 1px black solid; background: #F4F4F4 ; text-align: center}
  #editBar INPUT {font-size:10pt; width: 2em; font-weight: bold}
  button { background: #F4F4F4 }
</STYLE>
<SCRIPT>
  function getPos(el,which) {
    var iPos=0
    while (el.offsetParent!=null) {
      iPos+=el["offset"+which]
      el = el.offsetParent
      el.onfocus = new Function("displayToolbar(null,false)")
    }
    return iPos
  }

  function displayToolbar(ed, how) {
    var eb = document.all.editbar
    if (how)
      eb.style.display = "block"
    else
      eb.style.display = "none" 
    if (ed!=null) {
      eb.style.pixelTop = getPos(ed,"Top") + ed.offsetHeight + 1
      eb.style.pixelLeft = getPos(ed,"Left")
      eb._editor = window.frames[ed.id]
      eb._editor.setFocus()
    }
  }

  function doFormat(what) {
    var eb = document.all.editbar
    eb._editor.execCommand(what, arguments[1])
  }

  function swapMode(b) {
    var eb = document.all.editbar._editor
    eb.swapModes()
    b.value = eb.format + " Mode"

  }

function copyValue(f) {
    f.elements.data.value = "" + myEditor.textEdit.document.body.innerHTML + "";
  }

</SCRIPT>
<P><IFRAME ID=myEditor SRC="richText.htm" ONBLUR="displayToolbar(null,false)" ONFOCUS="displayToolbar(this,true)" WIDTH=100% HEIGHT=300></IFRAME>
<SCRIPT>
window.focus()
</SCRIPT>
<DIV ID=editbar ONCLICK="this._editor.setFocus()">
<button ONCLICK="doFormat('Cut')">
<img src="icons/Cut.gif" alt="Cut">
</button>
<button ONCLICK="doFormat('Copy')">
<img src="icons/Copy.gif" alt="Copy">
</button>
<button ONCLICK="doFormat('Paste')">
<img src="icons/Paste.gif" alt="Paste">
</button>
<button ONCLICK="doFormat('Bold')">
<img src="icons/Bold.gif" alt="Bold">
</button>
<button ONCLICK="doFormat('Italic')">
<img src="icons/Italic.gif" alt="Italic">
</button>
<button ONCLICK="doFormat('Underline')">
<img src="icons/Underline.gif" alt="Underline">
</button>
<button ONCLICK="doFormat('Indent')">
<img src="icons/FingerRight.gif" alt="Indent">
</button>
<button ONCLICK="doFormat('Outdent')">
<img src="icons/FingerLeft.gif" alt="Outdent">
</button>
<button ONCLICK="doFormat('JustifyLeft')">
<img src="icons/AlignLeftArrow.gif" alt="Left Align">
</button>
<button ONCLICK="doFormat('JustifyCenter')">
<img src="icons/AlignCenterArrow.gif" alt="Centre">
</button>
<button ONCLICK="doFormat('JustifyRight')" alt="Right Align">
<img src="icons/AlignRightArrow.gif">
</button>
<button ONCLICK="doFormat('InsertUnorderedList')">
<img src="icons/Circle.gif" alt="Unordered List">
</button>
<button ONCLICK="doFormat('InsertOrderedList')">
<img src="icons/Circle_1.gif" alt="Ordered List">
</button><br>
<INPUT TYPE="button" STYLE="background: #F4F4F4; font-family: Tahoma, Arial, Helvetica" VALUE="P" ONCLICK="doFormat('formatBlock','<P>')"><INPUT TYPE="button" STYLE="background: #F4F4F4; font-family: Tahoma, Arial, Helvetica; width: 3em" VALUE="PRE" ONCLICK="doFormat('formatBlock','<PRE>')">
<SCRIPT>
for (var i=1; i<=6; i++)
  document.write("<INPUT TYPE=\"button\" STYLE=\"background: #F4F4F4; font-family: Tahoma, Arial, Helvetica\" VALUE=\"H"+i+"\" ONCLICK=\"doFormat('formatBlock','<H"+i+">')\">")
</SCRIPT>
<button ONCLICK="doFormat('InsertHorizontalRule')">
<img src="icons/SplitHorizontal.gif" alt="Horizontal Rule">
</button>
<button ONCLICK="doFormat('InsertImage', prompt('Enter the URL of the image:', ''))">
<img src="icons/Paint.gif" alt="Insert Image">
</button>
<button ONCLICK="doFormat('InsertMarquee')">
<img src="icons/RotCCLeft.gif" alt="Insert Marquee">
</button>
<button ONCLICK="
strLink = 'firefly-pd.jsp?page=' + prompt('Enter the page number to link to:', 0);
doFormat('CreateLink', strLink)">
<img src="icons/World.gif" alt="Internal Link">
</button>
<button ONCLICK="doFormat('CreateLink', prompt('Enter the URL to link to:', ''))">
<img src="icons/World2.gif" alt="External Link">
</button>
<button ONCLICK="doFormat('Unlink')">
<img src="icons/Exit.gif" alt="Remove Link">
</button>
</DIV>

<form action="/firefly/servlet/org.haywired.firefly.AddHTMLServlet" method="POST" onsubmit="copyValue(this)">
  <p><input type="hidden" name="data">
  <input type="hidden" name="back" value=<%= request.getRequestURI() + "?" + request.getQueryString() %>>
  <input type="hidden" name="page" value=<%= pg %>>
  <input type="submit" value="Submit" class="stdbtn"><input type="button" value="Add Image" onclick="OpenAddImage()" class="stdbtn">
</form>
</center>