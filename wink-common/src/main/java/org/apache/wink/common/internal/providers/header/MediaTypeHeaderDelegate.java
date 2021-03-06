/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *  
 *   http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *  
 *******************************************************************************/
package org.apache.wink.common.internal.providers.header;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.RuntimeDelegate.HeaderDelegate;

import org.apache.wink.common.internal.i18n.Messages;
import org.apache.wink.common.internal.utils.SoftConcurrentMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MediaTypeHeaderDelegate implements HeaderDelegate<MediaType> {

    private static final Logger                               logger    =
                                                                            LoggerFactory
                                                                                .getLogger(MediaTypeHeaderDelegate.class);
    private static final Pattern                              EQUALS    = Pattern.compile("=");                           //$NON-NLS-1$
    private static final Pattern                              SEMICOLON = Pattern.compile(";");                           //$NON-NLS-1$
    private static final Pattern                              SLASH     = Pattern.compile("/");                           //$NON-NLS-1$
    private static final SoftConcurrentMap<String, MediaType> cache     =
                                                                            new SoftConcurrentMap<String, MediaType>();

    public MediaType fromString(String value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException(Messages.getMessage("mediaTypeHeaderNull")); //$NON-NLS-1$
        }

        MediaType cached = cache.get(value);
        if (cached != null) {
            return cached;
        }

        String type = "*"; //$NON-NLS-1$
        String subType = "*"; //$NON-NLS-1$
        Map<String, String> paramsMap = null;
        String[] all = SEMICOLON.split(value);

        try {
            // type and subType
            String main = all[0];
            String[] mainArray = SLASH.split(main);
            type = mainArray[0];
            if ("".equals(type)) { //$NON-NLS-1$
                String errMsg = Messages.getMessage("mediaTypeWrongFormat", value); //$NON-NLS-1$
                logger.error(errMsg);
                throw new IllegalArgumentException(errMsg);
            }
            if (mainArray.length == 1 && MediaType.MEDIA_TYPE_WILDCARD.equals(type)) {
                subType = MediaType.MEDIA_TYPE_WILDCARD;
            } else {
                subType = mainArray[1];
            }

            // parameters
            if (all.length > 1) {
                paramsMap = new LinkedHashMap<String, String>();
                for (int i = 1; i < all.length; i++) {
                    if (all[i] != null && all[i].trim().length() > 0) {
                        String[] param = EQUALS.split(all[i]);
                        if(param.length == 2) {
                            paramsMap.put(param[0].trim(), param[1].trim());
                        }
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            String errMsg = Messages.getMessage("mediaTypeWrongFormat", value); //$NON-NLS-1$
            logger.error(errMsg, e);
            throw new IllegalArgumentException(errMsg, e);
        }

        return cache.put(value, new MediaType(type, subType, paramsMap));
    }

    public String toString(MediaType value) {
        if (value == null) {
            throw new IllegalArgumentException(Messages.getMessage("mediaTypeHeaderNull")); //$NON-NLS-1$
        }

        StringBuilder result = new StringBuilder();
        result.append(value.getType()).append("/").append(value.getSubtype()); //$NON-NLS-1$
        Map<String, String> params = value.getParameters();
        for (String key : params.keySet()) {
            result.append(";").append(key).append("=").append(params.get(key)); //$NON-NLS-1$ //$NON-NLS-2$
        }
        return result.toString();

    }

}
