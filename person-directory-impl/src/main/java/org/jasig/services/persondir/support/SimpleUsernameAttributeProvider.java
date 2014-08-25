/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jasig.services.persondir.support;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.jasig.services.persondir.IPersonAttributeDao;

/**
 * Provides the username attribute based on a pre-configured string. Determines the username from a query Map based
 * on the configured attribute, {@link StringUtils#trimToNull(String)}, and if the username value does not contain a
 * wildcard.
 * 
 * @author Eric Dalquist
 * @version $Revision$
 */
public class SimpleUsernameAttributeProvider implements IUsernameAttributeProvider {
    private String usernameAttribute = "username";
    
    public SimpleUsernameAttributeProvider() {
    }
    
    public SimpleUsernameAttributeProvider(String usernameAttribute) {
        this.setUsernameAttribute(usernameAttribute);
    }
    
    /**
     * The usernameAttribute to use
     */
    public void setUsernameAttribute(String usernameAttribute) {
        Validate.notNull(usernameAttribute);
        this.usernameAttribute = usernameAttribute;
    }

    /* (non-Javadoc)
     * @see org.jasig.services.persondir.support.IUsernameAttributeProvider#getUsernameAttribute()
     */
    public String getUsernameAttribute() {
        return this.usernameAttribute;
    }
    
    /* (non-Javadoc)
     * @see org.jasig.services.persondir.support.IUsernameAttributeProvider#getUsernameFromQuery(java.util.Map)
     */
    public String getUsernameFromQuery(Map<String, List<Object>> query) {
        final List<Object> usernameAttributeValues = query.get(this.usernameAttribute);
        
        if (usernameAttributeValues == null || usernameAttributeValues.size() == 0) {
            return null;
        }

        final Object firstValue = usernameAttributeValues.get(0);
        if (firstValue == null) {
            return null;
        }
        
        final String username = StringUtils.trimToNull(String.valueOf(firstValue));
        if (username == null || username.contains(IPersonAttributeDao.WILDCARD)) {
            return null;
        }
        
        return username;
    }
}