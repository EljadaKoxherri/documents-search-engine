package com.intelycare.service;

import com.intelycare.model.DocumentModel;

import java.util.List;

public interface SearchEngineService {
    void indexDocument(DocumentModel documentModel);
    List<Integer> queryDocuments(String content);
}
