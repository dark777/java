package org.haywired.firefly.wiki;
import java.applet.Applet;
import java.io.*;

public class Preview extends Applet {
	static Translator parser;

	public Preview() {
	}

	public String translate(String aString) {

		// setup parser
		StringBufferInputStream in = new StringBufferInputStream(aString);
		if (parser == null) parser = new Translator(in);
		else parser.ReInit(in);
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		parser.out = new PrintStream(bytes);

		// sample definitions
		// parser.pages.put("SamplePage", "A test page.");
		// parser.pages.put("WardCunningham", "See http://c2.com/~ward.");

		// sample header
		parser.out.print("<body bgcolor=#ffffff>\r\n");
		// parser.out.print("<h1><image src=logo.gif> Sample Page</h1>\r\n");

		try {
			parser.Page();
		}
		catch (ParseException e) {
			parser.out.print("\n<p><pre>\n");
			parser.out.print(e.getMessage());
		}
		return bytes.toString();
	}
}
