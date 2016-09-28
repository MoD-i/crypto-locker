package com.psiphiglobal.proto;

import com.psiphiglobal.proto._core.Constants;
import com.psiphiglobal.proto.blockchain.impl._core.JsonRpcClient;
import com.psiphiglobal.proto.blockchain.impl._core.JsonRpcException;
import com.psiphiglobal.proto.blockchain.impl.request.PublishToStreamRequest;
import com.psiphiglobal.proto.blockchain.impl.request.RetrieveFromStreamRequest;
import com.psiphiglobal.proto.blockchain.impl.response.PublishToStreamResponse;
import com.psiphiglobal.proto.blockchain.impl.response.RetrieveFromStreamResponse;
import com.psiphiglobal.proto.exception.UnknownException;
import com.psiphiglobal.proto.model.Document;
import com.psiphiglobal.proto.model.DocumentSummary;
import com.psiphiglobal.proto.model.helper.DocumentHelper;
import com.psiphiglobal.proto.util.GsonProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harsh on 28/09/16.
 */
public class Main {

    public static void main(String[] args) {

        JsonRpcClient.init(GsonProvider.get(), Constants.BLOCKCHAIN_RPC_URL, Constants.BLOCKCHAIN_RPC_USERNAME, Constants.BLOCKCHAIN_RPC_PASSWORD);
        JsonRpcClient jsonRpcClient = JsonRpcClient.getInstance();

        Document document = new Document();
        document.setCreatedAt(System.currentTimeMillis());
        document.setCreator("ABC");
        document.setEncryptedContent("This is encrypted. Do not open or close");
        document.setEncryptedKey("Key");
        document.setId("ID");
        document.setName("Doc Name");
        document.setSignature("Signature");


        try
        {
            RetrieveFromStreamResponse response = RetrieveFromStreamResponse.parse(jsonRpcClient.sendRequest(new RetrieveFromStreamRequest("user_documents", "ABC")));

            System.out.println(response.getResults().size());
            System.out.println(GsonProvider.get().toJson(response.getResults().get(1)));

            List<DocumentSummary> documentSummaryList = new ArrayList<>();
            for(RetrieveFromStreamResponse.Result result: response.getResults())
            {
                documentSummaryList.add(DocumentHelper.deserializeDocumentSummary(result.getData()));
            }

            System.out.println(documentSummaryList.size());
        }
        catch (JsonRpcException e)
        {

        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new UnknownException();
        }
    }

    private static DocumentSummary getDocumentSummary(Document document) {

        DocumentSummary documentSummary = new DocumentSummary();
        documentSummary.setCreatedAt(document.getCreatedAt());
        documentSummary.setEncryptedKey(document.getEncryptedKey());
        documentSummary.setId(document.getId());
        documentSummary.setName(document.getName());

        return documentSummary;
    }
}
