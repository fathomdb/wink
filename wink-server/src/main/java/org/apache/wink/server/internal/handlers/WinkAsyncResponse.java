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
            asyncContext.complete();
        } finally {
            RuntimeContextTLS.setRuntimeContext(null);
        }

        return true;
    }

}
