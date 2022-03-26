package com.intelycare.service;

import com.intelycare.model.DocumentModel;

import java.util.List;

public interface SearchService {
    List<Integer> searchDocuments(String queryString);
}
