package org.apache.wink.guice;

import java.util.Properties;

import javax.inject.Inject;

import org.apache.wink.common.internal.lifecycle.LifecycleManagersRegistry;
import org.apache.wink.guice.server.internal.lifecycle.GuiceInjectorLifeCycleManager;
import org.apache.wink.jaxrs2.Jaxrs2DeploymentConfiguration;
import org.apache.wink.server.handlers.Handler;

import com.google.inject.Injector;

public class InjectorDeploymentConfiguration extends Jaxrs2DeploymentConfiguration {

    private final Injector injector;

    @Inject
    public InjectorDeploymentConfiguration(Injector injector) {
        LifecycleManagersRegistry lifecycleManagersRegistry = new LifecycleManagersRegistry();
        setOfFactoryRegistry(lifecycleManagersRegistry);
        this.injector = injector;
        lifecycleManagersRegistry.addFactoryFactory(new GuiceInjectorLifeCycleManager(injector));
    }

    @Override
    public void init() {
        Properties properties = getProperties();
        if (properties == null) {
            properties = new Properties();
            setProperties(properties);
        }

        // properties.setProperty("wink.handlersFactoryClass",
        // GuiceHandlersFactory.class.getName());

        super.init();
    }

    @Override
    protected <T extends Handler> T createHandler(Class<T> cls) {
        try {
            return injector.getInstance(cls);
        } catch (Throwable th) {
            return super.createHandler(cls);
        }
    }
}
