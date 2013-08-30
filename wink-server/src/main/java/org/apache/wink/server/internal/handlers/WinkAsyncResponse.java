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

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.TimeoutHandler;

import org.apache.wink.common.internal.runtime.RuntimeContextTLS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WinkAsyncResponse implements AsyncResponse {
    private static final Logger log = LoggerFactory.getLogger(WinkAsyncResponse.class);

    final AsyncContext asyncContext;
    final HttpServletResponse httpServletResponse;
    final HttpServletRequest httpServletRequest;
    final ServerMessageContext runtimeContext;

    private TimeoutHandler timeoutHandler;
    private long timeout = -1;

    public WinkAsyncResponse(ServerMessageContext runtimeContext, AsyncContext asyncContext,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.runtimeContext = runtimeContext;
        this.asyncContext = asyncContext;
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;

        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                synchronized (WinkAsyncResponse.this) {
                    if (timeoutHandler != null) {
                        timeoutHandler.handleTimeout(WinkAsyncResponse.this);
                    }
                }
            }

            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
            }

            @Override
            public void onError(AsyncEvent event) throws IOException {
            }

            @Override
            public void onComplete(AsyncEvent event) throws IOException {
            }
        });
    }

    @Override
    public boolean resume(Throwable t) {
        return sendResponse(t);
    }

    @Override
    public boolean resume(Object entity) {
        return sendResponse(entity);
    }

    private boolean sendResponse(Object entity) {
        assert RuntimeContextTLS.getRuntimeContext() == null;

        RuntimeContextTLS.setRuntimeContext(runtimeContext);
        try {
            try {
                runtimeContext.setResponseEntity(entity);
                runtimeContext.processResponse();
            } catch (Throwable e) {
                // TODO: i18n
                log.error("Unhandled error while sending async response", e); //$NON-NLS-1$
            }
            try {
                asyncContext.complete();
            } catch (Throwable e) {
                // TODO: i18n
                log.error("Error while completing async response", e); //$NON-NLS-1$
            }
        } finally {
            RuntimeContextTLS.setRuntimeContext(null);
        }

        return true;
    }

    @Override
    public void setTimeoutHandler(TimeoutHandler timeoutHandler) {
        this.timeoutHandler = timeoutHandler;
    }

    @Override
    public boolean setTimeout(long duration, TimeUnit timeUnit) {
        this.timeout = TimeUnit.MILLISECONDS.convert(duration, timeUnit);
        asyncContext.setTimeout(this.timeout);
        return true;
    }

}
