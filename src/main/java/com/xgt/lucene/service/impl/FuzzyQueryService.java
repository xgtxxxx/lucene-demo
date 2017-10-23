package com.xgt.lucene.service.impl;

import com.xgt.lucene.model.ArticleAttributes;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Query;
import org.springframework.stereotype.Service;

@Service("fuzzyQueryService")
public class FuzzyQueryService extends BaseQueryService {
    @Override
    protected Query getQuery(final Analyzer analyzer, final String keyword) throws ParseException {
        final Query query = new FuzzyQuery(new Term(ArticleAttributes.CONTENT.name(), keyword));
        //@Also see: public FuzzyQuery(Term term, int maxEdits, int prefixLength, int maxExpansions, boolean transpositions)
        //maxEdits default is 2, if more than 2, An exception will be raised.
        //prefixLength: default is 0
        //maxExpansitions: default is 50
        //transpositions: default is true, if need to start Damerau-Levenshtein, should set the value as false.
        return query;
    }
}
