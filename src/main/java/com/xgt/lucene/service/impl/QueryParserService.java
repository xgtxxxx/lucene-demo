package com.xgt.lucene.service.impl;

import com.xgt.lucene.model.ArticleAttributes;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.springframework.stereotype.Service;

@Service("queryParserService")
public class QueryParserService extends BaseQueryService {
    @Override
    protected Query getQuery(final Analyzer analyzer, final String keyword) throws ParseException {
        final QueryParser queryParser = new QueryParser(ArticleAttributes.CONTENT.name(), analyzer);
        //the keyword will be split automatically by analyzer.
        final Query query = queryParser.parse(keyword);

        return query;
    }
}
