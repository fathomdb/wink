package org.apache.wink.jaxrs2;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.container.ContainerRequestFilter;

import org.apache.wink.common.RuntimeContext;
import org.apache.wink.common.internal.lifecycle.ObjectFactory;
import org.apache.wink.common.internal.registry.ProvidersRegistry;
import org.apache.wink.server.handlers.RequestHandler;
import org.apache.wink.server.internal.DeploymentConfiguration;

public class Jaxrs2DeploymentConfiguration extends DeploymentConfiguration {

    @Override
    protected List<RequestHandler> initRequestPreHandlers() {
        ProvidersRegistry providersRegistry = getProvidersRegistry();

        List<RequestHandler> requestHandlers = new ArrayList<RequestHandler>();

        for (ObjectFactory<?> factory : providersRegistry.getAllProviders()) {
            Class<?> c = factory.getInstanceClass();

            if (ContainerRequestFilter.class.isAssignableFrom(c)) {
                RuntimeContext context = null;
                ContainerRequestFilter filter = (ContainerRequestFilter) factory.getInstance(context);
                requestHandlers.add(new WinkContainerRequestFilterHandler(filter));
            }
        }

        return requestHandlers;
    }

}
