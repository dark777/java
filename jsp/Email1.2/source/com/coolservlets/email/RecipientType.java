
/**
 *  RecipientType.java
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
 *  RecipientType class
 *
 *  This class is small and simple. It just holds recipient type values.
 *
 *  This class is primarily used like so:
 *
 *    if( type == RecipientType.TO )
 *      // do TO stuff
 *    else if( type == RecipientType.CC )
 *      // do CC stuff
 *
 *  Private Methods/Fields:
 *    static final int TO  = 1;
 *    static final int CC  = 2;
 *    static final int BCC = 3;
 *
 */

public class RecipientType {
    public static final int TO  = 1;
    public static final int CC  = 2;
    public static final int BCC = 3;
}