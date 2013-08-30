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
package org.apache.wink.jaxrs2;

import java.io.InputStream;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.apache.wink.server.handlers.MessageContext;
import org.apache.wink.server.internal.contexts.UriInfoImpl;

public class WinkContainerRequestContext implements ContainerRequestContext {

    private final MessageContext context;

    public WinkContainerRequestContext(MessageContext context) {
        this.context = context;
    }

    @Override
    public UriInfo getUriInfo() {
        return context.getUriInfo();
    }

    @Override
    public void setRequestUri(URI uri) {
        UriInfoImpl uriInfoImpl = context.getAttribute(UriInfoImpl.class);
        uriInfoImpl.setRequestUri(uri);
    }

    @Override
    public Object getProperty(String name) {
        throw new UnsupportedOperationException();

    }

    @Override
    public Collection<String> getPropertyNames() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setProperty(String name, Object object) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void removeProperty(String name) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setRequestUri(URI baseUri, URI requestUri) {
        throw new UnsupportedOperationException();

    }

    @Override
    public Request getRequest() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getMethod() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMethod(String method) {
        throw new UnsupportedOperationException();

    }

    @Override
    public MultivaluedMap<String, String> getHeaders() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getHeaderString(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Date getDate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Locale getLanguage() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLength() {
        throw new UnsupportedOperationException();
    }

    @Override
    public MediaType getMediaType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<MediaType> getAcceptableMediaTypes() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Locale> getAcceptableLanguages() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, Cookie> getCookies() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasEntity() {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream getEntityStream() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setEntityStream(InputStream input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SecurityContext getSecurityContext() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSecurityContext(SecurityContext context) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void abortWith(Response response) {
        throw new UnsupportedOperationException();
    }
}
