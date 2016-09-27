package com.psiphiglobal.proto.endpoints;

import com.psiphiglobal.proto._core.StatusCodes;
import com.psiphiglobal.proto.blockchain.data.exceptions.JsonRpcException;
import com.psiphiglobal.proto.blockchain.api.model.Node;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

@Path("health-check")
public class HealthEndpoint extends AbstractEndpoint
{
    @GET
    public void healthCheck(@Suspended AsyncResponse asyncResponse)
    {
        workerPool.execute(() -> {

            Node node = null;

            try {
                node = BLockchainApiManager.getNodeApi().getInfo();
            } catch (JsonRpcException e) {
                e.printStackTrace();
                System.exit(StatusCodes.JSON_RPC_EXCEPTION);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                System.exit(StatusCodes.MALFORMED_URL);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("node_info", node);
            asyncResponse.resume(buildSuccessJsonResponse(response));
        });
    }
}

