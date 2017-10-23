package com.xgt.lucene.service.impl;

import com.xgt.lucene.model.ArticleAttributes;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.springframework.stereotype.Service;

@Service("booleanQueryService")
class BooleanQueryService extends BaseQueryService {
    @Override
    protected Query getQuery(final Analyzer analyzer, final String keyword) throws ParseException {
        final Query titleQuery = new TermQuery(new Term(ArticleAttributes.TITLE.name(), keyword));
        final Query contentQuery = new TermQuery(new Term(ArticleAttributes.CONTENT.name(), keyword));

        final BooleanClause bc1 = new BooleanClause(titleQuery, BooleanClause.Occur.MUST);
        final BooleanClause bc2 = new BooleanClause(contentQuery, BooleanClause.Occur.MUST);
        //Both title and content should match the keyword
        final BooleanQuery booleanQuery = new BooleanQuery.Builder()
            .add(bc1)
            .add(bc2)
            .build();

        return booleanQuery;
    }
}
