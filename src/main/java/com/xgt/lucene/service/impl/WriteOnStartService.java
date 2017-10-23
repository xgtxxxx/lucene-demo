package com.xgt.lucene.service.impl;

import com.xgt.lucene.exception.LuceneException;
import com.xgt.lucene.model.Constants;
import com.xgt.lucene.model.ArticleAttributes;
import com.xgt.lucene.service.DataStore;
import com.xgt.lucene.service.WriteService;
import com.xgt.lucene.util.FileUtil;
import com.xgt.lucene.util.HtmlParser;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queries.function.valuesource.LongFieldSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WriteOnStartService implements WriteService {

    @Autowired
    private DataStore dataStore;

    @Override
    public void write() {
        final IndexWriterConfig indexWriterConfig = new IndexWriterConfig(dataStore.getAnalyzer());
        final List<File> files = FileUtil.listHtmls(Constants.FILE_PATH);
        final List<Document> documents = Optional
            .ofNullable(files)
            .orElse(Collections.emptyList())
            .stream()
            .map(file -> transformToDocument(file))
            .collect(Collectors.toList());
        try(final IndexWriter indexWriter = new IndexWriter(dataStore.getDirectory(), indexWriterConfig)) {
            indexWriter.deleteAll();
            indexWriter.addDocuments(documents);
            indexWriter.commit();
        } catch (final IOException e) {
            throw new LuceneException(e.getMessage(), e);
        }
    }

    private Document transformToDocument(final File file) {
        try{
            final Document document = new Document();
            final String content = HtmlParser.parseToText(file);
            document.add(new Field(ArticleAttributes.CONTENT.name(), content, TextField.TYPE_STORED));
            document.add(new Field(ArticleAttributes.TITLE.name(), file.getName(), TextField.TYPE_STORED));
            final String path = file.getPath();
            final String relativePath = path.substring(Constants.FILE_PATH_PRE.length());
            document.add(new Field(ArticleAttributes.PATH.name(), relativePath, TextField.TYPE_STORED));
            document.add(new Field(ArticleAttributes.LENGTH.name(), String.valueOf(content.length()), TextField.TYPE_STORED));
            //Must to be NumericDocValuesField or SortedNumericDocValuesField, then you can sort by this field.
            document.add(new NumericDocValuesField(ArticleAttributes.LENGTH_SORT.name(), content.length()));

            return document;
        } catch (final IOException e) {
            throw new LuceneException(e.getMessage(), e);
        }
    }
}
