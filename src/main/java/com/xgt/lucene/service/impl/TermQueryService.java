package com.xgt.lucene.service.impl;

import com.xgt.lucene.model.ArticleAttributes;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.springframework.stereotype.Service;

@Service("termQueryService")
public class TermQueryService extends BaseQueryService {

    @Override
    protected Query getQuery(final Analyzer analyzer, final String keyword) throws ParseException {
        //create a term to search file name
        final Term term = new Term(ArticleAttributes.CONTENT.name(), keyword);
        //create the term query object
        final Query query = new TermQuery(term);

        return query;
    }
}
