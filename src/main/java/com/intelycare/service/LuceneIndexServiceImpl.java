package com.intelycare.service;

import com.intelycare.model.DocumentModel;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;

import java.io.*;

public class LuceneIndexServiceImpl implements IndexService {

    private Analyzer analyzer = new StandardAnalyzer();

    public static Directory memoryIndex = new ByteBuffersDirectory();

    public LuceneIndexServiceImpl() {
        analyzer = new StandardAnalyzer();
    }

    public void saveIndex(DocumentModel document) {

            Document luceneDoc = new Document();

            StringField id = new StringField("ID", Integer.toString(document.getDocumentId()), Field.Store.YES);
            TextField content = new TextField("CONTENT", String.join(" ", document.getTokens()), Field.Store.YES);

            luceneDoc.add(id);
            luceneDoc.add(content);

            try(IndexWriter writer = new IndexWriter(memoryIndex, new IndexWriterConfig(analyzer))) {
                Query query = new QueryParser("ID", analyzer)
                        .parse(Integer.toString(document.getDocumentId()));
                writer.deleteDocuments(query);
                writer.addDocument(luceneDoc);
                writer.commit();
            } catch (IOException | ParseException e) {
                System.out.println(e);
            }
    }
}
