package com.xgt.lucene.service.impl;

import com.xgt.lucene.model.ArticleAttributes;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.WildcardQuery;
import org.springframework.stereotype.Service;

@Service("wildCardQueryService")
public class WildCardQueryService extends BaseQueryService {
    @Override
    protected Query getQuery(final Analyzer analyzer, final String keyword) throws ParseException {
        //?: must match one word, and the result must to be ?keyword.
        //*: match any words
        final Term term = new Term(ArticleAttributes.CONTENT.name(),"?" +keyword+ "*");
        final WildcardQuery query=new WildcardQuery(term);

        return query;
    }
}
