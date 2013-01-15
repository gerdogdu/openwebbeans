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
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Decorator;

import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.intercept.InterceptorData;

/**
 * Managed bean implementation of the {@link javax.enterprise.inject.spi.Bean}.
 * 
 * @version $Rev$ $Date$
 */
public class ManagedBean<T> extends InjectionTargetBean<T> implements InterceptedMarker
{
    /** Constructor of the web bean component */
    private Constructor<T> constructor;
    
    protected boolean isAbstractDecorator;
    
    public ManagedBean(WebBeansContext webBeansContext,
                       WebBeansType webBeansType,
                       AnnotatedType<T> annotatedType,
                       Set<Type> types,
                       Set<Annotation> qualifiers,
                       Class<? extends Annotation> scope,
                       Class<T> beanClass,
                       Set<Class<? extends Annotation>> stereotypes)
    {
        super(webBeansContext, webBeansType, annotatedType, types, qualifiers, scope, beanClass, stereotypes);
    }

    public ManagedBean(WebBeansContext webBeansContext,
                       WebBeansType webBeansType,
                       AnnotatedType<T> annotated,
                       Set<Type> types,
                       Set<Annotation> qualifiers,
                       Class<? extends Annotation> scope,
                       String name,
                       Class<T> beanClass,
                       Set<Class<? extends Annotation>> stereotypes,
                       boolean alternative)
    {
        super(webBeansContext, webBeansType, annotated, types, qualifiers, scope, name, beanClass, stereotypes, alternative);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected T createComponentInstance(CreationalContext<T> creationalContext)
    {
        return getInjectionTarget().produce(creationalContext);
    }

    /**
     * Get constructor.
     * 
     * @return constructor
     */
    public Constructor<T> getConstructor()
    {
        return constructor;
    }

    /**
     * Set constructor.
     * 
     * @param constructor constructor instance
     */
    public void setConstructor(Constructor<T> constructor)
    {
        this.constructor = constructor;
    }
    
    public boolean isPassivationCapable()
    {
        if (isPassivationCapable != null)
        {
            return isPassivationCapable.booleanValue();
        }
        if(Serializable.class.isAssignableFrom(getReturnType()))
        {
            for(Decorator<?> dec : decorators)
            {
                if(dec.getBeanClass() != null && !Serializable.class.isAssignableFrom(dec.getBeanClass()))
                {
                    isPassivationCapable = Boolean.FALSE;
                    return false;
                }
            }

            for(InterceptorData interceptorData : interceptorStack)
            {
                if(interceptorData.isDefinedInInterceptorClass())
                {
                    Class<?> interceptor = interceptorData.getInterceptorClass();
                    if(!Serializable.class.isAssignableFrom(interceptor))
                    {
                        isPassivationCapable = Boolean.FALSE;
                        return false;
                    }
                }
            }

            isPassivationCapable = Boolean.TRUE;
            return true;
        }

        isPassivationCapable = Boolean.FALSE;
        return false;
    }

    /** cache previously calculated result */
    private Boolean isPassivationCapable = null;
    
    public void setIsAbstractDecorator(boolean flag)
    {
        isAbstractDecorator = flag;
    }
}
