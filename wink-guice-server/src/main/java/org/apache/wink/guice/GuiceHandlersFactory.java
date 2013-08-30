//package org.apache.wink.guice;
//
//import java.util.List;
//
//import javax.inject.Inject;
//import javax.ws.rs.container.ContainerRequestFilter;
//
//import org.apache.wink.jaxrs2.WinkContainerRequestFilterHandler;
//import org.apache.wink.server.handlers.HandlersFactory;
//import org.apache.wink.server.handlers.RequestHandler;
//import org.apache.wink.server.handlers.ResponseHandler;
//
//import com.google.common.collect.Lists;
//
//public class GuiceHandlersFactory extends HandlersFactory {
//
//    private final InjectorContext context;
//
//    final List<? extends RequestHandler> requestHandlers = Lists.newArrayList();
//
//    @Inject
//    public GuiceHandlersFactory(InjectorContext context) {
//        this.context = context;
//
//        for (Class<?> c : context.getClasses()) {
//            if (ContainerRequestFilter.class.isAssignableFrom(c)) {
//                ContainerRequestFilter filter = (ContainerRequestFilter) context.getInstance(c);
//                requestHandlers.add(new WinkContainerRequestFilterHandler(filter));
//            }
//        }
//    }
//
//    @Override
//    public List<? extends RequestHandler> getRequestHandlers() {
//        return requestHandlers;
//    }
//
//    @Override
//    public List<? extends ResponseHandler> getResponseHandlers() {
//        return super.getResponseHandlers();
//    }
//
//    @Override
//    public List<? extends ResponseHandler> getErrorHandlers() {
//        return super.getErrorHandlers();
//    }
//
// }
