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
