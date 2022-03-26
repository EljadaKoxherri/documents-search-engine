package com.intelycare.service;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static com.intelycare.service.LuceneIndexServiceImpl.memoryIndex;

public class LuceneSearchServiceImpl implements SearchService {

    private Analyzer analyzer = new StandardAnalyzer();

    public List<Integer> searchDocuments(String queryStr) {
        queryStr = queryStr.replace("|", " OR ").replace("&" , " AND ");

        try {
            Query query = new QueryParser("CONTENT", analyzer)
                    .parse(queryStr);

            IndexReader indexReader = DirectoryReader.open(memoryIndex);
            IndexSearcher searcher = new IndexSearcher(indexReader);
            TopDocs topDocs = searcher.search(query, 10);

            List<Integer> docIds = new ArrayList<>();
            ScoreDoc[] hits = topDocs.scoreDocs;
            for (ScoreDoc hit: hits) {
                Document doc = searcher.doc(hit.doc);
                int id = Integer.parseInt(doc.get("ID"));
                docIds.add(id);
            }
            return docIds;
        } catch (ParseException | IOException e) {
            System.out.println(e);
        }

        return null;
    }
}