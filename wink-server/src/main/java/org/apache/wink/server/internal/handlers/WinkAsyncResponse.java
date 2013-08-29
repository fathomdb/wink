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
package org.apache.wink.server.internal.handlers;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.AsyncResponse;

import org.apache.wink.common.internal.runtime.RuntimeContextTLS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WinkAsyncResponse implements AsyncResponse {
    private static final Logger log = LoggerFactory.getLogger(WinkAsyncResponse.class);

    final AsyncContext asyncContext;
    final HttpServletResponse httpServletResponse;
    final HttpServletRequest httpServletRequest;
    final ServerMessageContext runtimeContext;

    public WinkAsyncResponse(ServerMessageContext runtimeContext, AsyncContext asyncContext,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.runtimeContext = runtimeContext;
        this.asyncContext = asyncContext;
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
    }

    @Override
    public boolean resume(Object entity) {
        assert RuntimeContextTLS.getRuntimeContext() == null;

        RuntimeContextTLS.setRuntimeContext(runtimeContext);
        try {
            try {
                runtimeContext.setResponseEntity(entity);
                runtimeContext.processResponse();
            } catch (Throwable e) {
                log.error("Unhandled error while sending async response", e);
            }
            try {
                asyncContext.complete();
            } catch (Throwable e) {
                log.error("Error while completing async response", e);
            }
        } finally {
            RuntimeContextTLS.setRuntimeContext(null);
        }

        return true;
    }

}
