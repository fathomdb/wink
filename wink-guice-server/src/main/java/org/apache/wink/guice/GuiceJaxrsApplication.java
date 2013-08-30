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
