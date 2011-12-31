// Copyright 2010, 2011 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.apache.tapestry5.internal.services;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.internal.EmptyEventContext;
import org.apache.tapestry5.internal.InternalConstants;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.*;

import java.io.IOException;

/**
 * Used to trigger the rendering of a particular page without causing a redirect to that page.
 * The content of the page is just streamed to the client.
 *
 * @since 5.2.0
 */
public class StreamPageContentResultProcessor implements ComponentEventResultProcessor<StreamPageContent>
{
    private final PageRenderRequestHandler handler;

    private final ComponentClassResolver resolver;

    private final TypeCoercer typeCoercer;

    private final RequestGlobals requestGlobals;

    private final Request request;

    public StreamPageContentResultProcessor(PageRenderRequestHandler handler, ComponentClassResolver resolver, TypeCoercer typeCoercer, RequestGlobals requestGlobals, Request request)
    {
        this.handler = handler;
        this.resolver = resolver;
        this.typeCoercer = typeCoercer;
        this.requestGlobals = requestGlobals;
        this.request = request;
    }

    public void processResultValue(StreamPageContent value) throws IOException
    {
        Class<?> pageClass = value.getPageClass();
        Object[] activationContext = value.getPageActivationContext();

        String pageName = pageClass == null
                ? requestGlobals.getActivePageName()
                : resolver.resolvePageClassNameToPageName(pageClass.getName());

        EventContext context = activationContext == null
                ? new EmptyEventContext()
                : new ArrayEventContext(typeCoercer, activationContext);

        if (value.isBypassActivation())
        {
            request.setAttribute(InternalConstants.BYPASS_ACTIVATION, true);
        }

        handler.handle(new PageRenderRequestParameters(pageName, context, false));
    }
}
