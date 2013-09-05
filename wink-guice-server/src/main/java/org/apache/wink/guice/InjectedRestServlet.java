/*
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
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */

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
