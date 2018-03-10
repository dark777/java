
/**
 *  Transport.java
 *
 *  Version 1.2
 *
 *  September 27, 1999
 *
 *  Copyright (C) 1999  Bill Lynch, CoolServlets.com
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Library General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  http://www.coolservlets.com/LGPL.html
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Library General Public License for more details.
 *
 *  You should have received a copy of the GNU Library General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.coolservlets.email;

/**
 *  Transport class
 *
 *  Version 1.2 now reads from the SMTP port and reports errors in a more robust fashion.
 *  It also implements the Transportable interface.
 *
 *  Version 1.1.1 includes a minor bugfix.
 *  This line in the send method:
 *    PrintWriter out = new PrintWriter( s.getOutputStream(), true );
 *  was fixed. (Thanks to Jon Barber.)
 *
 *  This class is the class that actually sends an email by talking to an SMTP
 *  server.
 *
 *  What's changed in 1.1 (most changes to 1.1 occured in THIS class)
 *    1.  Improved the FROM field a little so that the correct error is thrown if
 *        the FROM is not included. (It's required inorder to send an email.)
 *    2.  Fixed the behavior of the "To" and "Cc" fields. In 1.0, multiple To's
 *        or Cc's would display as multiple copies of the first email address.
 *        There was no problem in sending, just displaying.
 *    3.  Brackets are no longer printed for a single email address:
 *        Before: <bill@coolservlets.com>
 *        After:  bill@coolservlets.com
 *
 *  SMTP documentation (RFC821) can be found at:
 *    http://freesoft.org/CIE/RFC/821/index.htm
 *
 *  This class also checks for errors. An exception is thrown if a connection
 *  to the SMTP server cannot be established.
 *  The message itself must have the FROM and TO fields specified. (Acutally,
 *  to send an email through SMTP, you don't need a FROM field. I decided to
 *  require it to prevent totally anonymous emails. However, you could take
 *  that requirement out if you wanted to.)
 *
 *  Here's how this class should be used:
 *
 *  Message msg = new Message(); // fill this up, ie, FROM, TO's, etc... (see Message class)
 *  Transport tr = new Transport( "yourHost.com", 25 ); // SMTP ports are 25 by default
 *
 *  try {
 *      tr.send( msg );
 *  }
 *  catch( TransportExeception te ) {
 *      System.err.println( "TransportException! " + te );
 *  }
 *
 */

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *  Transport class
 *
 *  This class accepts a Message object and uses its data to construct and send an email message.
 */
public class Transport implements Transportable {

    private String  smtpHost;
    private String  smtpResponse;
    private int     smtpPort;

    /**
     *  Construct a Transport object by specifying a host and port.
     */
    public Transport( String smtpHost, int smtpPort ) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
    }
    /**
     *  Returns the SMTP host (ie, smtpServer.coolservlets.com).
     */
    public String getSmtpHost( ) {
        return this.smtpHost;
    }
    /**
     *  Returns the SMTP port number (usually 25).
     */
    public int getSmtpPort( ) {
        return this.smtpPort;
    }
    /**
     *  Sets the SMTP host.
     */
    public void setSmtpHost( String smtpHost ) {
        this.smtpHost = smtpHost;
    }
    /**
     *  Sets the SMTP port number.
     */
    public void setSmtpPort( int smtpPort ) {
        this.smtpPort = smtpPort;
    }

    /**
     *  send method
     * -------------------------------------------------------------------------
     *  Sends the message by talking to a SMTP port.
     *
     *  Here's a quick bit of SMTP info (you DON'T need to know this to use this class):
     *
     *    To send an email via SMTP, simply connect to the SMTP server on
     *    port 25. Here's the basic 5 or 6 commands to send an email:
     *    (The ">" lines will be what is written back from the SMTP server)
     *    Also note, that I've removed my own server info with "x"'s
     *
     *    > 220 xxx.xxx.net ESMTP Sendmail 8.9.1/8.9.1; Thu, 1 Apr 1999 12:55:27 -0600 (CST)
     *    HELO yourHost.com
     *    > 250 xxx.xxx.net Hello xxx.xxx.xxx.net [000.000.000.000], pleased to meet you
     *    MAIL FROM: <yourName@yourPlace.com>
     *    > 250 <yourName@yourPlace.com>... Sender ok
     *    RCPT TO: <someoneYouKnow@theirPlace.com>
     *    > 250 <someoneYouKnow@theirPlace.com>... Recipient ok
     *    DATA
     *    > 354 Enter mail, end with "." on a line by itself
     *    From: Your Name <yourName@yourPlace.com>
     *    To: Someone You Know <someoneYouKnow@theirPlace.com>
     *    Subject: Hi There
     *    Hi, how's it going?
     *    Later!
     *    .
     *    > 250 MAA19177 Message accepted for delivery
     *    QUIT
     *
     */
    public void send( Message msg ) throws TransportException {
        PrintWriter    out = null;
        BufferedReader in  = null;

        try {
            // Open a connection to the SMTP port:
            Socket s = new Socket( this.smtpHost, this.smtpPort );

            // The following line now includes a "true" in the PrintWriter constructor.
            // This makes the stream flush after every println by default. This fixes a bug
            // found by Jon Barber.
            out = new PrintWriter( s.getOutputStream(), true );

            // Create a BufferedReader so we can read back response codes from the SMTP port
            in = new BufferedReader( new InputStreamReader( s.getInputStream() ) );

            // Check to see that we connected the SMTP port correctly by reading a
            // line from the SMTP port and checking the error response code.
            // We use StringBuffers to maximize efficiency.  
            if( smtpErrorExists( in,"220" ) ) {
                throw new TransportException( "Can't connect to: " + this.smtpHost + ". Port: " + this.smtpPort + "\t" + this.smtpResponse );
            }
        }
        catch( IOException ioe ) {
            throw new TransportException( "Can't connect to: " + this.smtpHost + ". Port: " + this.smtpPort + "\t " );
        }

        sendMessage( msg, in, out );
    }
    
    private void sendMessage( Message msg, BufferedReader in, PrintWriter out ) throws TransportException {
        //Date timestamp = new Date();

        // Say hello to the SMTP port then check for the appropriate response code
        out.println( "HELO " + this.smtpHost );
        if( smtpErrorExists( in,"250" ) ) {
            throw new TransportException( "SMTP error: HELO failed.\t" + smtpResponse );
        }

        // Start writing our mail data to the SMTP port starting with the sender of the email.
        // Actually, to send an email via SMTP, you don't need to included the MAIL FROM field -- this
        // allows you to send emails completely anonymously. In this package, I make it a requirement
        // to include the MAIL FROM field just so this email package isn't spammer friendly 'out of the box'.

        Address[] from = msg.getFrom();
        if( from == null ) {
            throw new TransportException( "SMTP error: No FROM address specified, can't send email.\t " );
        }
        /* added new "else" statement in ver 1.1                    */
        /* This is necessary to make sure that the FROM is included */
        else {
            if( from[0].getAddress() != null && from[0].getAddress().length() > 0 ) {
                out.println( "MAIL FROM: <" + from[0].getAddress() + ">" );
                if( smtpErrorExists( in,"250" ) ) {
                    throw new TransportException( "SMTP error: adding a sender failed (FROM field).\t" + smtpResponse );
                }
            }
            else {
                throw new TransportException( "SMTP error: No FROM address specified, can't send email." );
            }
        }

        // TO, CC, BCC (all are added to the recipient list)
        // SMTP error checking is done after every recipient is added.
        Address[] to = msg.getRecipients( RecipientType.TO );
        if( to == null || to.length == 0 ) {
            throw new TransportException( "SMTP error: no RCPT TO (a recipient) specified." );
        }
        else {
            for( int i=0; i<to.length; i++ ) {
                out.println( "RCPT TO: <"+ to[i].getAddress() +">" );
                if( smtpErrorExists( in,"250" ) ) {
                    throw new TransportException( "SMTP error: adding a TO recipient failed. Error with this address: " + to[i].getAddress() + "\t" + smtpResponse );
                }
            }
        }

        Address[] cc = msg.getRecipients( RecipientType.CC );
        if( cc != null ) {
            for( int i=0; i<cc.length; i++ ) {
                out.println( "RCPT TO: <"+ cc[i].getAddress() +">" );
                if( smtpErrorExists( in,"250" ) ) {
                    throw new TransportException( "SMTP error: adding a CC recipient failed. Error with this address: " + cc[i].getAddress() + "\t" + smtpResponse );
                }
            }
        }

        Address[] bcc = msg.getRecipients( RecipientType.BCC );
        if( bcc != null ) {
            for( int i=0; i<bcc.length; i++ ) {
                out.println( "RCPT TO: <"+ bcc[i].getAddress() +">" );
                if( smtpErrorExists( in,"250" ) ) {
                    throw new TransportException( "SMTP error: adding a BCC recipient failed. Error with this address: " + bcc[i].getAddress() + "\t" + smtpResponse );
                }
            }
        }

        // Start of data section
        out.println( "DATA" );
        if( smtpErrorExists( in,"354" ) ) {
            throw new TransportException( "SMTP error: Writing DATA field of message failed.\t" + smtpResponse );
        }
        else {
            // From:
            if( from != null ) {
                out.println( "From: "+ ( (from[0].getPersonal() != null) ? from[0].getPersonal()+" " : "" ) + "<"+ from[0].getAddress() +">" );
            }
            // To:
            if( to != null && to.length > 0 ) {
                StringBuffer buf = new StringBuffer();
                buf.append( "To: " );
                for( int i=0; i<to.length; i++ ) {
                    buf.append( (to[i].getPersonal()!=null)
                             ? ( to[i].getPersonal() + " <" + to[i].getAddress() + ">" )
                             : ( to[i].getAddress() ) );
                    if( i < to.length-1 ) { // add commas between emails
                        buf.append( ", " );
                    }
                }
                out.println( buf.toString() );
            }
            // CC:
            if( cc != null && cc.length > 0 ) {
                StringBuffer buf = new StringBuffer();
                buf.append( "CC: " );
                for( int i=0; i<cc.length; i++ ) {
                    buf.append( (cc[i].getPersonal()!=null)
                             ? ( cc[i].getPersonal() + " <" + cc[i].getAddress() + ">" )
                             : ( cc[i].getAddress() ) );
                if( i < cc.length-1 ) // add commas between emails
                    buf.append( ", " );
                }
                out.println( buf.toString() );
            }
            // Reply-To: (there should only be one reply-to address)
            Address replyTo = msg.getReplyTo();
            if( replyTo != null ) {
                String rt = replyTo.getAddress();
                if( rt != null && rt.length() > 0 ) {
                    StringBuffer buf = new StringBuffer();
                    buf.append( "Reply-To: " );
                    buf.append( (replyTo.getPersonal()!=null)
                             ? ( replyTo.getPersonal() + " <" + replyTo.getAddress() + ">" )
                             : ( replyTo.getAddress() ) );
                    out.println( buf.toString() );
                }
            }
            // Subject:
            if( msg.getSubject() != null ) {
                out.println( "Subject: "+ msg.getSubject() );
            }
            // Email body:
            if( msg.getText() != null ) {
                out.println( msg.getText() );
            }
            // End:
            // Signal that we're done with the email by printing the DATA section with
            // a dot on a line by itself.
            out.println( "." );

            // Check to see if the message was successfully queued for delievery:
            if( smtpErrorExists( in,"250" ) ) {
                throw new TransportException( "Error: Message failed to be sent.\t" + smtpResponse );
            }

            // Exit the SMTP port
            out.println( "QUIT" );
        } // else

        out.close();

    } // end "send" method

    /**
     *  smtpErrorExists( BufferedReader in, String errorCode )
     *  Checks for errors in the SMTP response.
     */
    private boolean smtpErrorExists( BufferedReader in, String errorCode ) {
        try {
            smtpResponse = in.readLine();
            if( !smtpResponse.startsWith( errorCode ) ) {
                return true;
            }
            return false;
        }
        catch( IOException ioe ) {
            return true;
        }
    }
}