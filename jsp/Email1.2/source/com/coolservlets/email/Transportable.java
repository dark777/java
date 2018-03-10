
/**
 *  Transportable.java
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

import java.io.PrintWriter;

/**
 *  Transportable Interface
 *
 *  I thought it would be a good idea to make an interface that would
 *  be used by the Transport class for a couple of reasons:
 *
 *    1) Other apps, servlets, etc, rely on this email package. When the package
 *       is updated, it should be that the users of the email package will just
 *       have to update their email classes without any other code recompiles
 *       elsewhere.
 *    2) I'm going to implement the Email Package using JavaMail some day so
 *       writing an interface makes switching between my implementation of
 *       sending emails or using JavaMail painless.
 */
public interface Transportable {

    public void     send        ( Message msg ) throws TransportException;
    public String   getSmtpHost ( );
    public int      getSmtpPort ( );
    public void     setSmtpHost ( String smtpHost );
    public void     setSmtpPort ( int smtpPort );

}