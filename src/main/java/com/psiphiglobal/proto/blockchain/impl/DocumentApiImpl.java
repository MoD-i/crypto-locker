package com.psiphiglobal.proto.blockchain.impl;

import com.psiphiglobal.proto.blockchain.api.DocumentApi;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class DocumentApiImpl implements DocumentApi
{
    private static Logger LOG = LogManager.getLogger();
    private JsonRpcClient jsonRpcClient;

    public static final String STREAM_DOCUMENT = "documents";
    public static final String STREAM_USER_DOCUMENTS = "user_documents";

    public DocumentApiImpl(JsonRpcClient jsonRpcClient)
    {
        this.jsonRpcClient = jsonRpcClient;
    }

    @Override
    public void create(Document document)
    {
        try
        {
            PublishToStreamResponse publishToStreamResponse = PublishToStreamResponse.parse(jsonRpcClient.sendRequest(new PublishToStreamRequest(STREAM_DOCUMENT, document.getId(), DocumentHelper.serialize(document))));
            LOG.info("[CREATE DOCUMENT] Transaction ID: " + publishToStreamResponse.getTransactionId());

            DocumentSummary documentSummary = DocumentHelper.summarize(document);
            PublishToStreamResponse response = PublishToStreamResponse.parse(jsonRpcClient.sendRequest(new PublishToStreamRequest(STREAM_USER_DOCUMENTS, document.getCreator(), DocumentHelper.serialize(documentSummary))));
            LOG.info("[CREATE DOCUMENT SUMMARY] Transaction ID: " + response.getTransactionId());
        }
        catch (JsonRpcException e)
        {
            throw new UnknownException();
        }
        catch (Exception e)
        {
            LOG.error("Unable to create document", e);
            throw new UnknownException();
        }
    }

    @Override
    public Document get(String id)
    {
        try
        {
            RetrieveFromStreamResponse response = RetrieveFromStreamResponse.parse(jsonRpcClient.sendRequest(new RetrieveFromStreamRequest(STREAM_DOCUMENT, id)));
            if (response.getResults().size() == 0)
            {
                return null;
            }
            return DocumentHelper.deserializeDocument(response.getResult(0).getData());
        }
        catch (JsonRpcException e)
        {
            throw new UnknownException();
        }
        catch (Exception e)
        {
            LOG.error("Unable to fetch document", e);
            throw new UnknownException();
        }
    }

    @Override
    public List<DocumentSummary> getAll(String username)
    {
        try
        {
            RetrieveFromStreamResponse response = RetrieveFromStreamResponse.parse(jsonRpcClient.sendRequest(new RetrieveFromStreamRequest(STREAM_USER_DOCUMENTS, username)));

            List<DocumentSummary> documentSummaryList = new ArrayList<>();
            for (RetrieveFromStreamResponse.Result result : response.getResults())
            {
                documentSummaryList.add(DocumentHelper.deserializeDocumentSummary(result.getData()));
            }

            return documentSummaryList;
        }
        catch (JsonRpcException e)
        {
            throw new UnknownException();
        }
        catch (Exception e)
        {
            LOG.error("Unable to fetch document summaries", e);
            throw new UnknownException();
        }
    }
}
