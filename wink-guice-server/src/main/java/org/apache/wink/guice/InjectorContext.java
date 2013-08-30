package org.apache.wink.guice;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(GuiceInjectorContext.class)
public interface InjectorContext {

    public List<Class<?>> getClasses();

}
