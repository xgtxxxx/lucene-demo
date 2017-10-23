package com.xgt.lucene.service.impl;

import com.xgt.lucene.model.ArticleAttributes;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;
import org.springframework.stereotype.Service;

@Service("multiFieldQueryService")
public class MultiFieldQueryService extends BaseQueryService {

    @Override
    protected Query getQuery(final Analyzer analyzer, final String keyword) throws ParseException {
        final String[] fields = {ArticleAttributes.TITLE.name(), ArticleAttributes.CONTENT.name()};
        //MUST -> and，MUST_NOT -> not，SHOULD -> or
        final BooleanClause.Occur[] clauses = {BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD};
        final Query query = MultiFieldQueryParser.parse(keyword, fields, clauses, analyzer);

        return query;
    }
}
