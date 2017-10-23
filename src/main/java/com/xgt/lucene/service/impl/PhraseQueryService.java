package com.xgt.lucene.service.impl;

import com.xgt.lucene.model.ArticleAttributes;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.springframework.stereotype.Service;

@Service("phraseQueryService")
public class PhraseQueryService extends BaseQueryService {
    @Override
    protected Query getQuery(final Analyzer analyzer, final String keyword) throws ParseException {
        final Query query = new PhraseQuery(ArticleAttributes.CONTENT.name(), keyword.split(" "));

        return query;
    }
}
