package com.psiphiglobal.proto.endpoints;

import com.psiphiglobal.proto._core.ServerContext;
import com.psiphiglobal.proto.util.GsonProvider;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;

public class AbstractEndpoint
{
    protected ExecutorService workerPool;

    public AbstractEndpoint()
    {
        workerPool = ServerContext.getInstance().getWorkerPool();
    }

    protected Response buildSuccessJsonResponse(Object o)
    {
        return Response.ok(GsonProvider.get().toJson(o), MediaType.APPLICATION_JSON_TYPE).build();
    }

    protected Response buildEmptySuccessResponse()
    {
        return Response.ok().build();
    }

    protected Response redirect(String url)
    {
        try
        {
            return Response.seeOther(new URI(url)).build();
        }
        catch (URISyntaxException e)
        {
            return Response.serverError().build();
        }
    }
}
