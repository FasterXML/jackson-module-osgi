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

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OsgiJacksonModule extends Module
{
    private final BundleContext bundleContext;
    
    public OsgiJacksonModule(BundleContext bundleContext)
    {
        this.bundleContext = bundleContext;
    }
    
    @Override
    public String getModuleName()
    {
        return "osgi-module";
    }

    @Override
    public Version version()
    {
        return Version.unknownVersion();
    }

    @Override
    public void setupModule(SetupContext context)
    {
        ObjectMapper mapper = context.getOwner();
        mapper.setInjectableValues(new OsgiInjectableValues(bundleContext));
    }

}
