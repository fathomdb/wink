package org.apache.wink.guice;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.google.inject.Injector;
import com.google.inject.Key;

public class GuiceInjectorContext implements InjectorContext {

    final List<Class<?>> classes;

    @Inject
    public GuiceInjectorContext(Injector injector) {
        this.classes = new ArrayList<Class<?>>();

        walk(injector);
    }

    @Override
    public List<Class<?>> getClasses() {
        return classes;
    }

    private void walk(Injector injector) {
        for (Key<?> key : injector.getBindings().keySet()) {
            Type type = key.getTypeLiteral().getType();

            if (type instanceof Class) {
                Class<?> c = (Class) type;

                classes.add(c);
            }
        }

        if (injector.getParent() != null) {
            walk(injector.getParent());
        }
    }
}
