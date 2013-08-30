package org.apache.wink.guice;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.wink.common.WinkApplication;
import org.apache.wink.common.internal.registry.metadata.ProviderMetadataCollector;
import org.apache.wink.common.internal.registry.metadata.ResourceMetadataCollector;

@Singleton
public class GuiceJaxrsApplication extends WinkApplication {
    private final InjectorContext injector;

    final Set<Class<?>> classes = new HashSet<Class<?>>();

    @Inject
    public GuiceJaxrsApplication(InjectorContext injector) {
        this.injector = injector;

        for (Class<?> c : injector.getClasses()) {
            if (ProviderMetadataCollector.isProvider(c)) {
                classes.add(c);
            } else if (ResourceMetadataCollector.isResource(c)) {
                classes.add(c);
            }
        }
    }

    @Override
    public double getPriority() {
        return super.getPriority();
    }

    @Override
    public Set<Object> getInstances() {
        return super.getInstances();
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        return super.getSingletons();
    }

}
