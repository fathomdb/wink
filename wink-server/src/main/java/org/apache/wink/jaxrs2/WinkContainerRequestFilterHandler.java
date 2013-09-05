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

import java.util.Properties;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import org.apache.wink.server.handlers.HandlersChain;
import org.apache.wink.server.handlers.MessageContext;
import org.apache.wink.server.handlers.RequestHandler;

public class WinkContainerRequestFilterHandler implements RequestHandler {

    private final ContainerRequestFilter containerRequestFilter;

    public WinkContainerRequestFilterHandler(ContainerRequestFilter containerRequestFilter) {
        this.containerRequestFilter = containerRequestFilter;
    }

    @Override
    public void init(Properties props) {

    }

    @Override
    public void handleRequest(MessageContext context, HandlersChain chain) throws Throwable {
        ContainerRequestContext requestContext = new WinkContainerRequestContext(context);
        containerRequestFilter.filter(requestContext);

        chain.doChain(context);
    }

}
