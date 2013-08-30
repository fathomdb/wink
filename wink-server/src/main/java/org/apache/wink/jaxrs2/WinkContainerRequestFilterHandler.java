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
