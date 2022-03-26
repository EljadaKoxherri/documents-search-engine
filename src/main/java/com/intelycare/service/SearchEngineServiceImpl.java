package com.intelycare.service;

import com.intelycare.model.DocumentModel;

import java.util.List;

public class SearchEngineServiceImpl implements SearchEngineService {

    private LuceneIndexServiceImpl indexer;
    private LuceneSearchServiceImpl searcher;

    public SearchEngineServiceImpl(LuceneIndexServiceImpl indexer, LuceneSearchServiceImpl searcher) {
        this.indexer = indexer;
        this.searcher = searcher;
    }

    @Override
    public void indexDocument(DocumentModel documentModel) {
        indexer.saveIndex(documentModel);
    }

    @Override
    public List<Integer> queryDocuments(String content) {
        return searcher.searchDocuments(content);
    }
}
