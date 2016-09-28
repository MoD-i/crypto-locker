package com.psiphiglobal.proto.endpoints;

import com.psiphiglobal.proto.model.Node;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("health-check")
public class HealthEndpoint extends AbstractEndpoint
{
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void healthCheck(@Suspended AsyncResponse asyncResponse)
    {
        workerPool.execute(() -> {
            Node node = blockchainApiManager.getNodeApi().getInfo();
            Map<String, Object> response = new HashMap<>();
            response.put("node_info", node);
            asyncResponse.resume(buildSuccessJsonResponse(response));
        });
    }
}

