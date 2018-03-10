<% aci++; %>

<p><center>
  <hr width="100%" size=1 color="black"><span class="sizer"><strong>Add Content</strong></span><hr width="100%" size=1 color="black">

<STYLE>
  #editBar<%= aci %>  {position: absolute; display: none; width: 100%; border: 1px black solid; background: #F4F4F4 ; text-align: center}
  #editBar<%= aci %> INPUT {font-size:10pt; width: 2em; font-weight: bold}
  button { background: #F4F4F4 }
</STYLE>
<SCRIPT>
  function getPos<%= aci %>(el,which) {
    var iPos<%= aci %>=0
    while (el.offsetParent!=null) {
      iPos<%= aci %>+=el["offset"+which]
      el = el.offsetParent
      el.onfocus = new Function("displayToolbar<%= aci %>(null,false)")
    }
    return iPos<%= aci %>
  }

  function displayToolbar<%= aci %>(ed, how) {
    var eb<%= aci %> = document.all.editbar<%= aci %>
    if (how)
      eb<%= aci %>.style.display = "block"
    else
      eb<%= aci %>.style.display = "none" 
    if (ed!=null) {
      eb<%= aci %>.style.pixelTop = getPos<%= aci %>(ed,"Top") + ed.offsetHeight + 1
      eb<%= aci %>.style.pixelLeft = getPos<%= aci %>(ed,"Left")
      eb<%= aci %>._editor = window.frames[ed.id]
      eb<%= aci %>._editor.setFocus()
    }
  }

  function doFormat<%= aci %>(what) {
    var eb<%= aci %> = document.all.editbar<%= aci %>
    eb<%= aci %>._editor.execCommand(what, arguments[1])
  }

  function swapMode<%= aci %>(b) {
    var eb<%= aci %> = document.all.editbar<%= aci %>._editor
    eb<%= aci %>.swapModes()
    b.value = eb<%= aci %>.format + " Mode"

  }

function copyValue<%= aci %>(f) {
    f.elements.data.value = "" + myEditor<%= aci %>.textEdit.document.body.innerHTML + "";
  }

</SCRIPT>
<P><IFRAME ID=myEditor<%= aci %> SRC="richText.htm" ONBLUR="displayToolbar<%= aci %>(null,false)" ONFOCUS="displayToolbar<%= aci %>(this,true)" WIDTH=100% HEIGHT=300></IFRAME>
<SCRIPT>
window.focus()
</SCRIPT>
<DIV ID=editbar<%= aci %> ONCLICK="this._editor.setFocus()">
<button ONCLICK="doFormat<%= aci %>('Cut')">
<img src="icons/Cut.gif" alt="Cut">
</button>
<button ONCLICK="doFormat<%= aci %>('Copy')">
<img src="icons/Copy.gif" alt="Copy">
</button>
<button ONCLICK="doFormat<%= aci %>('Paste')">
<img src="icons/Paste.gif" alt="Paste">
</button>
<button ONCLICK="doFormat<%= aci %>('Bold')">
<img src="icons/Bold.gif" alt="Bold">
</button>
<button ONCLICK="doFormat<%= aci %>('Italic')">
<img src="icons/Italic.gif" alt="Italic">
</button>
<button ONCLICK="doFormat<%= aci %>('Underline')">
<img src="icons/Underline.gif" alt="Underline">
</button>
<button ONCLICK="doFormat<%= aci %>('Indent')">
<img src="icons/FingerRight.gif" alt="Indent">
</button>
<button ONCLICK="doFormat<%= aci %>('Outdent')">
<img src="icons/FingerLeft.gif" alt="Outdent">
</button>
<button ONCLICK="doFormat<%= aci %>('JustifyLeft')">
<img src="icons/AlignLeftArrow.gif" alt="Left Align">
</button>
<button ONCLICK="doFormat<%= aci %>('JustifyCenter')">
<img src="icons/AlignCenterArrow.gif" alt="Centre">
</button>
<button ONCLICK="doFormat<%= aci %>('JustifyRight')" alt="Right Align">
<img src="icons/AlignRightArrow.gif">
</button>
<button ONCLICK="doFormat<%= aci %>('InsertUnorderedList')">
<img src="icons/Circle.gif" alt="Unordered List">
</button>
<button ONCLICK="doFormat<%= aci %>('InsertOrderedList')">
<img src="icons/Circle_1.gif" alt="Ordered List">
</button><br>
<INPUT TYPE="button" STYLE="background: #F4F4F4; font-family: Tahoma, Arial, Helvetica" VALUE="P" ONCLICK="doFormat<%= aci %>('formatBlock','<P>')"><INPUT TYPE="button" STYLE="background: #F4F4F4; font-family: Tahoma, Arial, Helvetica; width: 3em" VALUE="PRE" ONCLICK="doFormat<%= aci %>('formatBlock','<PRE>')">
<SCRIPT>
for (var i=1; i<=6; i++)
  document.write("<INPUT TYPE=\"button\" STYLE=\"background: #F4F4F4; font-family: Tahoma, Arial, Helvetica\" VALUE=\"H"+i+"\" ONCLICK=\"doFormat<%= aci %>('formatBlock','<H"+i+">')\">")
</SCRIPT>
<button ONCLICK="doFormat<%= aci %>('InsertHorizontalRule')">
<img src="icons/SplitHorizontal.gif" alt="Horizontal Rule">
</button>
<button ONCLICK="doFormat<%= aci %>('InsertImage', prompt('Enter the URL of the image:', ''))">
<img src="icons/Paint.gif" alt="Insert Image">
</button>
<button ONCLICK="doFormat<%= aci %>('InsertMarquee')">
<img src="icons/RotCCLeft.gif" alt="Insert Marquee">
</button>
<button ONCLICK="
strLink = 'firefly-pd.jsp?page=' + prompt('Enter the page number to link to:', 0);
doFormat<%= aci %>('CreateLink', strLink)">
<img src="icons/World.gif" alt="Internal Link">
</button>
<button ONCLICK="doFormat<%= aci %>('CreateLink', prompt('Enter the URL to link to:', ''))">
<img src="icons/World2.gif" alt="External Link">
</button>
<button ONCLICK="doFormat<%= aci %>('Unlink')">
<img src="icons/Exit.gif" alt="Remove Link">
</button>
</DIV>

<form action="/firefly/servlet/org.haywired.firefly.AddHTMLServlet" method="POST" onsubmit="copyValue<%= aci %>(this)">
  <p><input type="hidden" name="data">
  <input type="hidden" name="back" value=<%= request.getRequestURI() + "?" + request.getQueryString() %>>
  <input type="hidden" name="page" value=<%= pg %>>
  <input type="submit" value="Submit" class="stdbtn"><input type="button" value="Add Image" onclick="OpenAddImage()" class="stdbtn">
</form>
</center>
