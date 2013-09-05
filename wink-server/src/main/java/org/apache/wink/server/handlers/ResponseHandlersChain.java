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

import java.util.List;

public class ResponseHandlersChain extends AbstractHandlersChain<ResponseHandler> {

    public ResponseHandlersChain(ResponseHandler handler, HandlersChain tail) {
        super(handler, tail);
    }

    @Override
    protected void handle(ResponseHandler handler, MessageContext context) throws Throwable {
        handler.handleResponse(context, tail);
    }

    public static ResponseHandlersChain build(List<ResponseHandler> handlers) {
        if (handlers.isEmpty()) {
            return new ResponseHandlersChain(null, null);
        }

        return new ResponseHandlersChain(handlers.get(0), build(handlers.subList(1, handlers.size())));
    }

}
