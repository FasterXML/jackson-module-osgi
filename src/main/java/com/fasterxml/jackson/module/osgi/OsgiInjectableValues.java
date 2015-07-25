/*
 * Copyright (c) 2015 by Axway Software
 * All brand or product names are trademarks or registered trademarks
 * of their respective holders.
 * This document and the software described in this document are the property 
 * of Axway Software and are protected as Axway Software trade secrets.
 * No part of this work may be reproduced or disseminated in any form or 
 * by any means, without the prior written permission of Axway Software.
 */
package com.fasterxml.jackson.module.osgi;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.InjectableValues;

public class OsgiInjectableValues extends InjectableValues
{
    private final BundleContext bundleContext;
    
    public OsgiInjectableValues(BundleContext bundleContext)
    {
        this.bundleContext = bundleContext;
    }
    
    @Override
    public Object findInjectableValue(Object valueId, DeserializationContext ctxt, BeanProperty forProperty, Object beanInstance)
    {
        String type = serviceType(forProperty);
        String filter = serviceFilter(valueId);
        return findService(type, filter);
    }

    private Object findService(String type, String filter)
    {
        try
        {
            ServiceReference<?>[] srs = bundleContext.getServiceReferences(type, filter);
            if (srs == null || srs.length == 0)
            {
                return null;
            }
            else
            {
                return bundleContext.getService(srs[0]);
            }
        }
        catch (InvalidSyntaxException e)
        {
            // this will never happen as the filter was checked before
            return null;
        }
    }

    private static String serviceType(BeanProperty forProperty)
    {
        return forProperty.getType().toCanonical();
    }

    private String serviceFilter(Object valueId)
    {
        try
        {
            return bundleContext.createFilter(valueId.toString()).toString();
        }
        catch (InvalidSyntaxException e)
        {
            return null;
        }
    }

}
