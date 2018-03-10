
/**
 *  MalformedAddressException.java
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
 *  MalformedAddressException class
 *
 *  Public Methods:
 *    MalformedAddressException()
 *    MalformedAddressException( String msg )
 *
 *  Changes in ver 1.2:
 *    Improved the documentation for the MalformedAddressException(String msg) method.
 */
public class MalformedAddressException extends Exception {

    /**
     *  Default constructor.
     */
    public MalformedAddressException() {
        super();
    }

    /**
     *  Do something with the "error" parameter! It provides useful information
     *  about each error.
     *
     *  For instance, the "msg" parameter can contain information about the
     *  malformed email -- I improved the error message strings in the Address
     *  class (see setPersonal() and setAddress()). Looking at those error strings
     *  should help you determine which email address or name is throwing an error.
     */
    public MalformedAddressException( String msg ) {
        super( msg );
    }
}