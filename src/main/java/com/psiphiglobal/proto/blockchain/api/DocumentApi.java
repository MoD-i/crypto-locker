package com.psiphiglobal.proto.blockchain.api;

import com.psiphiglobal.proto.model.Document;
import com.psiphiglobal.proto.model.DocumentSummary;

import java.util.List;

public interface DocumentApi
{
    void create(Document document);

    Document get(String id);

    List<DocumentSummary> getAll(String username);
}
