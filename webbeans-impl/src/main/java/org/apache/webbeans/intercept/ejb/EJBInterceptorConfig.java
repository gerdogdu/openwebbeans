/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.webbeans.intercept.ejb;


import javax.enterprise.inject.spi.AnnotatedType;

import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.util.Asserts;

/**
 * Configures the EJB related interceptors.
 *
 * @author <a href="mailto:gurkanerdogdu@yahoo.com">Gurkan Erdogdu</a>
 * @since 1.0
 * @deprecated This is not needed at all. EJB interceptors are _solely_ treated by the EJB container!
 */
public final class EJBInterceptorConfig
{

    private final WebBeansContext webBeansContext;

    public EJBInterceptorConfig(WebBeansContext webBeansContext)
    {
        this.webBeansContext = webBeansContext;
    }

    /**
     * Configures the given class for applicable interceptors.
     *
     * @param annotatedType to configure interceptors for
     */
    public void configure(AnnotatedType<?> annotatedType)
    {
        Asserts.assertNotNull(annotatedType);


    }

}
