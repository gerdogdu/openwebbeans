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
package org.apache.webbeans.component;

import java.io.Serializable;
import java.lang.reflect.Modifier;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.PassivationCapable;
import javax.enterprise.inject.spi.Producer;

import org.apache.webbeans.component.spi.BeanAttributes;
import org.apache.webbeans.component.spi.ProducerFactory;
import org.apache.webbeans.config.WebBeansContext;


/**
 * Abstract class for producer components.
 * 
 * @version $Rev$ $Date$
 * @param <T> bean type info
 */
public class AbstractProducerBean<T> extends AbstractOwbBean<T> implements PassivationCapable
{
    private Class<T> returnType;
    private Producer<T> producer;

    /**
     * Create a new instance.
     * 
     * @param returnType bean type info
     * @param ownerComponent owner bean
     */
    public AbstractProducerBean(Class<?> ownerBeanClass,
            WebBeansContext webBeansContext,
            WebBeansType webBeansType,
            BeanAttributes<T> beanAttributes,
            Class<T> returnType,
            ProducerFactory<?> producerFactory)
    {
        super(webBeansContext, webBeansType, beanAttributes, ownerBeanClass, !returnType.isPrimitive());
        this.returnType = returnType;
        this.producer = producerFactory.createProducer(this);
    }

    @Override
    public Producer<T> getProducer()
    {
        return producer;
    }

    @Override
    public Class<T> getReturnType()
    {
        return returnType;
    }

    /**
     * {@inheritDoc}
     */
    public void dispose(T instance, CreationalContext<T> creationalContext)
    {
        // Do nothing
    }

    protected boolean isPassivationCapable(Class<?> returnType, Integer modifiers)
    {
        if(Modifier.isFinal(modifiers) && !(Serializable.class.isAssignableFrom(returnType)))
        {
            return false;
        }
        
        if(returnType.isPrimitive() || Serializable.class.isAssignableFrom(returnType))
        {
            return true;
        }
        
        return false;
    }
}
