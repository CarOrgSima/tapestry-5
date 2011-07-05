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

import java.util.Locale;

import org.apache.tapestry5.internal.structure.Page;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.services.dynamic.DynamicTemplate;
import org.apache.tapestry5.services.pageload.ComponentRequestSelectorAnalyzer;
import org.apache.tapestry5.services.pageload.ComponentResourceSelector;

/**
 * Access to localized page instances (which are now shared singletons, starting in release 5.2).
 * This service is a wrapper around the {@link PageLoader} that caches the loaded pages.
 * 
 * @since 5.2.0
 */
public interface PageSource
{
    /**
     * Clears the source's cache of loaded pages. This occurs when an outside change to the world invalidates
     * created page instances. Introduced to handle the case where a page has a {@link DynamicTemplate}, but the
     * underlying {@link Resource} is noticed to have changed.
     * 
     * @since 5.3
     */
    void clearCache();

    /**
     * Returns a loaded instance of the indicated page, using the Locale and other information
     * from the {@link ComponentResourceSelector} obtained from the {@link ComponentRequestSelectorAnalyzer}.
     * 
     * @param canonicalPageName
     * @return existing, or newly created, page instance
     */
    Page getPage(String canonicalPageName);
}
