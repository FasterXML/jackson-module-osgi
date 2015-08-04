package com.fasterxml.jackson.module.osgi;

import org.osgi.framework.BundleContext;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A Jackson Module to inject OSGI services in deserialized objects.
 * @see OsgiInjectableValues
 */
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
        return getClass().getSimpleName();
    }

    @Override
    public Version version()
    {
        return PackageVersion.VERSION;
    }

    @Override
    public void setupModule(SetupContext context)
    {
        ObjectMapper mapper = context.getOwner();
        mapper.setInjectableValues(new OsgiInjectableValues(bundleContext));
    }
}
