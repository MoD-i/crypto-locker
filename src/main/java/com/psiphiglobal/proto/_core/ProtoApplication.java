package com.psiphiglobal.proto._core;


import com.psiphiglobal.proto.endpoints.HealthEndpoint;
import com.psiphiglobal.proto.logging.RequestLogger;
import com.psiphiglobal.proto.logging.ResponseLogger;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath(Constants.BASE_URI)
public class ProtoApplication extends Application
{
    public ProtoApplication()
    {
    }

    @Override
    public Set<Class<?>> getClasses()
    {
        Set<Class<?>> resources = new HashSet<>();

        /* Logger */
        resources.add(RequestLogger.class);
        resources.add(ResponseLogger.class);

        /* Endpoints */
        resources.add(HealthEndpoint.class);

        return resources;
    }
}