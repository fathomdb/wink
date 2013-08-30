package org.apache.wink.guice;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.ws.rs.core.Application;

import org.apache.wink.server.internal.DeploymentConfiguration;
import org.apache.wink.server.internal.servlet.RestServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class InjectedRestServlet extends RestServlet {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(InjectedRestServlet.class);

    private static final long serialVersionUID = 1L;

    @Inject
    Provider<InjectorDeploymentConfiguration> configProvider;

    @Inject
    Provider<GuiceJaxrsApplication> applicationProvider;

    @Override
    protected DeploymentConfiguration createDeploymentConfiguration() throws ClassNotFoundException,
            InstantiationException, IllegalAccessException {
        return configProvider.get();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Application getApplication(DeploymentConfiguration configuration) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException {
        // Class<? extends Application> appClass = null;
        // String initParameter = getInitParameter(APPLICATION_INIT_PARAM);
        // if (initParameter != null) {
        // if (logger.isInfoEnabled()) {
        //                logger.info(Messages.getMessage("restServletJAXRSApplicationInitParam", //$NON-NLS-1$
        // initParameter,
        // APPLICATION_INIT_PARAM));
        // }
        // // use ClassUtils.loadClass instead of Class.forName so we have
        // // classloader visibility into the Web module in J2EE environments
        // appClass = (Class<? extends
        // Application>)ClassUtils.loadClass(initParameter);
        //
        // // let the lifecycle manager create the instance and process fields
        // // for injection
        // ObjectFactory of =
        // configuration.getOfFactoryRegistry().getObjectFactory(appClass);
        // configuration.addApplicationObjectFactory(of);
        //
        // return (Application)of.getInstance(null);
        // }
        // String appLocationParameter = getInitParameter(APP_LOCATION_PARAM);
        // if (appLocationParameter == null) {
        // if (logger.isWarnEnabled()) {
        //                logger.warn(Messages.getMessage("propertyNotDefined", APP_LOCATION_PARAM)); //$NON-NLS-1$
        // }
        // }
        // if (logger.isInfoEnabled()) {
        //            logger.info(Messages.getMessage("restServletWinkApplicationInitParam", //$NON-NLS-1$
        // appLocationParameter,
        // APP_LOCATION_PARAM));
        // }
        // return new ServletWinkApplication(getServletContext(),
        // appLocationParameter);
        return applicationProvider.get();
    }

}
