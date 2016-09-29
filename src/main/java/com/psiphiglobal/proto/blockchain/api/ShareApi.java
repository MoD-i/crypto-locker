package com.psiphiglobal.proto.blockchain.api;

import com.psiphiglobal.proto.model.DocumentSummary;

import java.util.List;

public interface ShareApi
{
    void share(String username, DocumentSummary document);

    List<DocumentSummary> getSharedDocuments(String username);
}
