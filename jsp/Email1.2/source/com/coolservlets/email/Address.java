
/**
 *  Address.java
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
 *  Address class
 *
 *  This class is designed to store an email address.
 *
 *  An email address is defined in the following way:
 *    a <name> and an <address>  (ie, "Bill Lynch" <bill@coolservlets.com>)
 *    or just an <address>  (ie, <bill@coolservlets.com)
 *
 *  A <name> is any string that does NOT contain the ASCII characters, 0-31,
 *  127 and 34. (Control characters, DEL, and quotes).
 *
 *  An <address> is any string that does NOT contain the ASCII character, 0-31,
 *  127 and 34. (Control characters, DEL, and quotes). Additionally, it must
 *  have a "@" symbol and it must not have any spaces.
 *
 *  When an email address or email name is added, it's checked for validity. A
 *  MalformedAddressException will be thrown if something isn't right.
 *  Additionally, the email will not be stored.
 *
 *  Changes in ver 1.2:
 *    Improved some of the documentation and changed the error strings in the
 *    setAddress() and setPersonal() methods.
 *
 */
public class Address {

    private String address;
    private String personal;

    /**
     *  Constructs an Address object with an email name and address specified.
     *  ie, Address a = new Address( "Joe Bloe", "jbloe@bloe.net" );
     */
    public Address( String personal, String address ) {
        try {
            setPersonal( personal );
            setAddress( address );
        }
        catch( MalformedAddressException mae ) {
            // uncomment the following line to do something if an address
            // is invalid. Right now, if this class encounters a bad address,
            // it ignores it and goes on.
            //System.out.println( "MalformedAddressException: " + mae );
        }
    }

    /**
     *  Constructs an Address object with an email address specified.
     *  ie, Address a = new Address( "jbloe@bloe.net" );
     */
    public Address( String address ) {
        this.personal = null;
        try {
            setAddress( address );
        }
        catch( MalformedAddressException mae ) {
            System.out.println( "MalformedAddressException: " + mae );
        }
    }

    /**
     *  Returns the email address.
     *  ie, will return "jbloe@bloe.net" (without quotes)
     */
    public String getAddress() {
        return address;
    }

    /**
     *  Returns the email name.
     *  ie, will return "Joe Bloe" (without quotes)
     */
    public String getPersonal() {
        return personal;
    }

    /**
     *  This method sets the address field of an address object -- first,
     *  it checks the given string and makes sure that
     *  it's a valid email address. (ie, has an "@" symbol, etc).
     *  If it's not valid, an exception is thrown.
     *  This is done to ensure that email addresses being sent (via
     *  the transport class) are valid.
     *  You should notice that if the address is invalid, it does not get
     *  added.
     *
     *  Added in ver 1.2 -- better documentation for this method and changed
     *    the error string.
     */
    public void setAddress( String address ) throws MalformedAddressException {
        if( checkAddress( address ) ) {
            this.address = address;
        }
        else {
            String msg = "The email address \""+address+"\" is invalid. ";
            msg       += "It was not added to the address object.";
            throw new MalformedAddressException( msg );
        }
    }

    /**
     *  Sets the personal email name (much like setAddress(...) sets the email address ).
     *
     *  Added in ver 1.2 -- changed the error string.
     */
    public void setPersonal( String name ) throws MalformedAddressException {
        if( checkPersonal( name ) ) {
            this.personal = name;
        }
        else {
            String msg = "The email name \""+name+"\" is invalid. ";
            msg       += "It was not added to the address object.";
            throw new MalformedAddressException( msg );
        }
    }

    /**
     *  The "parse()" method will parse a string of email address. When an
     *  unresovable email address is encountered, it is skipped and the method
     *  moves on to the next item in the list.
     *
     *  Use:
     *    Passing in "Bill,bill@place.com,Matt,matt@place.com" results in an array
     *    of two Address objects. (One has "Bill" and "bill@place.com" and the
     *    other has "Matt" and "matt@place.com".)
     *
     *    Passing in "Bill,bill@place.com,matt@place.com" results in an array
     *    of two Address objects. (This is exactly similiar to the last example,
     *    only the second object only has "matt@place.com" stored -- NO email
     *    name.)
     *
     *    Passing in "Bill,Bob,Ben,bill@place.com" results in an array with ONE
     *    Address object. It stores "Ben" as the name and "bill@place.com" as the
     *    email address. (It ignores the first 2 names because they're not
     *    associated with an email address, therefore not added.)
     */
    public static Address[] parse( String addressList ) {
        Vector addresses = new Vector();
        StringTokenizer st = new StringTokenizer( addressList, "," );
        String s = null, personal = null, addr = null;

        int numTokens = st.countTokens();
        while( st.hasMoreTokens() ) {
            s = st.nextToken();
            numTokens--;
            personal = null;
            addr = null;
            if( s.indexOf("@") == -1 ) { // Not an email address, so it's a name
                numTokens--;
                personal = s;
                if( numTokens >= 0 ) {
                    addr = st.nextToken();
                    if( addr.indexOf( "@" ) > 0 ) {
                        addresses.addElement( new Address( personal, addr ) );
                    }
                }
            }
            else { // it's an email address
                if( s.indexOf( "@" ) > 0 ) { // make sure that it really is an email address
                    addresses.addElement( new Address( s ) );
                }
            }
        }
        Address[] a = new Address[addresses.size()];
        addresses.copyInto( a );
        return a;
    }

    /**
     *  Checks the personal email name for errors.
     */
    private boolean checkPersonal( String name ) {
        char ch;
        for( int i=0; i<name.length(); i++ ) {
            ch = name.charAt(i);
            if( (ch>=0 && ch<=31) || (ch==34) || (ch==127) ) {
                return false;
            }
        }
        return true;
        /*  // This would actually fix the email address
            // by replacing invalid characters with spaces.
            // Uncomment if you want
            StringBuffer sb = new StringBuffer();
            for( int i=0; i<name.length(); i++ ) {
                char ch = name.charAt(i);
                if( (ch>=0 && ch<=31) || (ch==34) || (ch==127) ) {
                    sb.append( " " );
                } else {
                    sb.append( ch );
                }
            }
        */
    }

    /**
     *  Checks the email address for errors.
     */
    private boolean checkAddress( String address ) {
        boolean ok = false;
        int atPos = address.indexOf( "@" );
        // Check for the "@" symbol. It should not be at the
        // beginning or end of the email address string.
        if( (atPos <= 0) || (atPos == address.length()-1) ) {
            ok = false;
        } else {
            if( address.indexOf( " " ) > -1 ) { // No spaces allowed in an email address
                ok = false;
            } else {
                ok = true;
            }
        }
        // One final check for invalid characters:
        char ch;
        for( int i=0; i<address.length(); i++ ) {
            ch = address.charAt(i);
            if( (ch>=0 && ch<=31) || (ch==34) || (ch==127) ) {
                ok = false;
                break;
            }
        }
        return ok;
    }
}