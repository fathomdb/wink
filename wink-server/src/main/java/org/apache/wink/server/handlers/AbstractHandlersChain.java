/*******************************************************************************
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
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *  
 *******************************************************************************/

package org.apache.wink.server.handlers;

import java.util.LinkedList;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractHandlersChain<T extends Handler> implements HandlersChain {

    private static Logger logger = LoggerFactory.getLogger(AbstractHandlersChain.class);
    
    final T handler;
    protected final HandlersChain tail;

    public AbstractHandlersChain(T handler, HandlersChain tail) {
        this.handler = handler;
        this.tail = tail;
    }

    public void run(MessageContext context) throws Throwable {
        doChain(context);
    }

    public void doChain(MessageContext context) throws Throwable {
        if (handler == null) {
            return;
        }
        // invoke the handler
        if(logger.isTraceEnabled()) {
            logger.trace("Invoking handler: {}", handler.getClass().getName()); //$NON-NLS-1$
        }
        handle(handler, context);
    }

    protected abstract void handle(T handler, MessageContext context) throws Throwable;

    @Override
    public String toString() {
        return String.format("%s:%s", handler, tail); //$NON-NLS-1$
    }
}
