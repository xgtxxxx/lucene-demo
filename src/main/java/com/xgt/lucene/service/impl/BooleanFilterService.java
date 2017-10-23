package com.xgt.lucene.service.impl;

import com.xgt.lucene.model.ArticleAttributes;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.util.BytesRef;
import org.springframework.stereotype.Service;

@Service("booleanFilterService")
class BooleanFilterService extends BaseQueryService {
    @Override
    protected Query getQuery(final Analyzer analyzer, final String keyword) throws ParseException {
        final Query titleQuery = new TermQuery(new Term(ArticleAttributes.TITLE.name(), keyword));
        final Query contentQuery = new TermQuery(new Term(ArticleAttributes.CONTENT.name(), keyword));
        //find content length between 1000 and 5000;
        final Query sizeFilter = new TermRangeQuery(ArticleAttributes.LENGTH.name(), new BytesRef("1000"), new BytesRef("5000"), true, true);

        final BooleanClause bc1 = new BooleanClause(titleQuery, BooleanClause.Occur.MUST);
        final BooleanClause bc2 = new BooleanClause(contentQuery, BooleanClause.Occur.MUST);
        final BooleanClause filter = new BooleanClause(sizeFilter, BooleanClause.Occur.FILTER);
        //Both title and content should match the keyword
        final BooleanQuery booleanQuery = new BooleanQuery.Builder()
            .add(bc1)
            .add(bc2)
            .add(filter)
            .build();

        return booleanQuery;
    }
}
