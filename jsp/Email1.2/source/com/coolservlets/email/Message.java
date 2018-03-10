
/**
 *  Message.java
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

import java.util.*;

/**
 *  Message class
 *
 *  This class is designed to build an email message. You should use this class
 *  to create a message object which is passed to a Transport object which sends
 *  the email.
 *
 *  Here's what I would do to set up a simple Message object:
 *
 *  Message msg = new Message();
 *  msg.setFrom( new Address( "Bill", "bill@coolservlets.com" ) );
 *  msg.setRecipient( RecipientType.TO, new Address( "Matt", "matt@coolservlets.com" ) );
 *  msg.setSubject( "Hello!" );
 *  msg.setText( "Hey Matt. Hope everything is going well...." );
 *
 *  To add a CC recipient, do this:
 *  msg.setRecipient( RecipientType.CC, new Address( "A Friend", "someone@someplace.com" ) );
 *
 */
public class Message {

    private Address   from;
    private Address[] recipientTO;
    private Address[] recipientCC;
    private Address[] recipientBCC;
    private Address   replyTo;
    private String    subject;
    private String    msgText;

    /**
     *  Message()
     *  Initialize all arrays to size zero.
     */
    public Message() {
        recipientTO = new Address[0];
        recipientCC = new Address[0];
        recipientBCC = new Address[0];
    }

    /**
     *  Adds one email address to the existing list of addresses.
     */
    public void addRecipient( int type, Address address ) {
        Address[] tempAddr = new Address[1];
        tempAddr[0] = address;
        addRecipients( type, tempAddr );
    }

    /**
     *  Adds multiple email addresses to the existing list of addresses.
     */
    public void addRecipients( int type, Address[] addresses ) {
        if( type == RecipientType.TO ) {
            recipientTO  = addArray( RecipientType.TO, recipientTO, addresses );
        }
        else if( type  == RecipientType.CC ) {
            recipientCC  = addArray( RecipientType.CC, recipientCC, addresses );
        }
        else if( type  == RecipientType.BCC ) {
            recipientBCC = addArray( RecipientType.BCC, recipientBCC, addresses );
        }
    }

    /**
     *  Combines two address arrays. (Only used by addRecipients(...) method.)
     */
    private Address[] addArray( int type, Address[] array1, Address[] array2 ) {
        int i,j;
        Address[] tempAddr = new Address[array1.length + array2.length];
        for( i=0; i<array1.length; i++ ) {
            tempAddr[i] = array1[i];
        }
        for( j=i; j<(array2.length+i); j++ ) {
            tempAddr[j] = array2[j-i];
        }
        Address[] a = tempAddr;
        return a;
    }

    /**
     *  Sets the Reply-To address.
     */
    public void setReplyTo( Address replyToAddress ) {
        replyTo = replyToAddress;
    }

    /**
     *  Returns the Reply-To address.
     */
    public Address getReplyTo( ) {
        return replyTo;
    }

    /**
     *  Returns the specified recipient array.
     */
    public Address[] getRecipients( int type ) {
        if( type == RecipientType.TO ) {
            return this.recipientTO;
        }
        else if( type == RecipientType.CC ) {
            return this.recipientCC;
        }
        else { //if( type == RecipientType.BCC )
            return this.recipientBCC;
        }
    }

    /**
     *  Returns the FROM address in an array of addresses.
     *  If no FROM address has been specified, the method returns null.
     */
    public Address[] getFrom( ) {
        if( from != null ) {
            Address[] tempAddr = new Address[1];
            tempAddr[0] = from;
            return tempAddr;
        } else {
            return null;
        }
    }

    /**
     *  Returns the message subject.
     */
    public String getSubject( ) {
        return subject;
    }

    /**
     *  Returns the message text.
     */
    public String getText( ) {
        return msgText;
    }

    /**
     *  Sets the FROM email address. (This will replace the current FROM address since there
     *  can be only one FROM address.)
     */
    public void setFrom( Address address ) {
        from = address;
    }

    /**
     *  This method will set one recipient and will override anything
     *  that has been previously stored.
     */
    public void setRecipient( int type, Address address ) {
        Address[] tempAddr = new Address[1];
        tempAddr[0] = address;
        setRecipients( type, tempAddr );
    }

    /**
     *  This method will sets multiple recipients and will override anything
     *  that has been previously stored.
     */
    public void setRecipients( int type, Address[] addresses ) {
        if( type == RecipientType.TO ) {
            this.recipientTO = addresses;
        }
        else if( type == RecipientType.CC ) {
            this.recipientCC = addresses;
        }
        else if( type == RecipientType.BCC ) {
            this.recipientBCC = addresses;
        }
    }

    /**
     *  Sets the SUBJECT of the email.
     */
    public void setSubject( String subject ) {
        this.subject = subject;
        if( this.subject == null ) {
            this.subject = " ";
        }
    }

    /**
     *  Sets the TEXT of the email.
     */
    public void setText( String msgText ) {
        this.msgText = msgText;
    }
}
