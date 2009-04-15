/*
 * Copyright (C) 2005-2009 Alfresco Software Limited.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.

 * As a special exception to the terms and conditions of version 2.0 of 
 * the GPL, you may redistribute this Program in connection with Free/Libre 
 * and Open Source Software ("FLOSS") applications as described in Alfresco's 
 * FLOSS exception.  You should have recieved a copy of the text describing 
 * the FLOSS exception, and it is also available here: 
 * http://www.alfresco.com/legal/licensing"
 */
package org.alfresco.repo.lock;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.service.namespace.QName;

/**
 * Exception generated when a lock cannot be acquired.
 * 
 * @author Derek Hulley
 * @since 3.2
 */
public class LockAcquisitionException extends AlfrescoRuntimeException
{
    private static final long serialVersionUID = 8215858379509645862L;

    /**
     * <ul>
     *   <li>1: the qname</li>
     *   <li>2: the lock token</li>
     * </ul>
     */
    public static final String ERR_FAILED_TO_ACQUIRE_LOCK = "system.locks.err.failed_to_acquire_lock";
    /**
     * <ul>
     *   <li>1: the qname</li>
     *   <li>2: the lock token</li>
     * </ul>
     */
    public static final String ERR_LOCK_RESOURCE_MISSING = "system.locks.err.lock_resource_missing";
    /**
     * <ul>
     *   <li>1: the qname</li>
     *   <li>2: the lock token</li>
     *   <li>3: the actual update count</li>
     *   <li>4: the expected update count</li>
     * </ul>
     */
    public static final String ERR_LOCK_UPDATE_COUNT = "system.locks.err.lock_update_count";
    /**
     * <ul>
     *   <li>1: the qname</li>
     *   <li>2: the lock token</li>
     * </ul>
     */
    public static final String ERR_EXCLUSIVE_LOCK_EXISTS = "system.locks.err.excl_lock_exists";
    
    /**
     * @param lockQName             the lock that was sought
     * @param lockToken             the lock token being used
     */
    public LockAcquisitionException(QName lockQName, String lockToken)
    {
        super(ERR_FAILED_TO_ACQUIRE_LOCK, new Object[] {lockQName, lockToken});
    }

    /**
     * 
     * @param msgId                 one of the message IDs defined
     * @param args                  the arguments that apply
     */
    public LockAcquisitionException(String msgId, Object ... args)
    {
        super(msgId, args);
    }
}
