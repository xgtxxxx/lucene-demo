package com.xgt.lucene.service.impl;

import com.xgt.lucene.exception.LuceneException;
import com.xgt.lucene.model.*;
import com.xgt.lucene.service.DataStore;
import com.xgt.lucene.service.QueryService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class BaseQueryService implements QueryService {
    @Autowired
    private DataStore dataStore;

    protected abstract Query getQuery(final Analyzer analyzer, final String keyword) throws ParseException;

    @Override
    public Pager query(final QueryRequest request) {
        final String keyword = request.getKeyword();
        final int lastIndex = request.getIndex();

        try (final DirectoryReader directoryReader = DirectoryReader.open(dataStore.getDirectory())) {
            final IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
            final Query query = getQuery(dataStore.getAnalyzer(), keyword);

            final Long totalHits = indexSearcher.search(query, Integer.MAX_VALUE).totalHits;
            final Sort sort = getSort();
            final ScoreDoc lastScoreDoc = getLastScoreDoc(lastIndex, query, indexSearcher, sort);
            final TopDocs topDocs = indexSearcher.searchAfter(lastScoreDoc, query, Constants.PER_PAGE_SIZE, sort);
            final Highlighter highlighter = getHighLighter(query);

            final List<Article> documents = new ArrayList<>();
            for (final ScoreDoc scoreDoc : topDocs.scoreDocs) {
                final Document doc = indexSearcher.doc(scoreDoc.doc);
                final String title = doc.get(ArticleAttributes.TITLE.name());
                final String content = doc.get(ArticleAttributes.CONTENT.name());
                final String path = doc.get(ArticleAttributes.PATH.name());
                final String length = doc.get(ArticleAttributes.LENGTH.name());
                //Get highlighted text fragments
                final String highLightTitle =
                    highlighter.getBestFragment(dataStore.getAnalyzer(), ArticleAttributes.TITLE.name(), title);
                final String highLightContent =
                    highlighter.getBestFragment(dataStore.getAnalyzer(), ArticleAttributes.CONTENT.name(), content);

                final int contentLength =
                    content.length() > Constants.CONTENT_LENGTH ? Constants.CONTENT_LENGTH : content.length();
                documents.add(
                    new Article(
                        Optional.ofNullable(highLightTitle).orElse(title),
                        //                        Optional.ofNullable(highLightContent).orElse(content.substring(0,
                        // Constants.CONTENT_LENGTH)),
                        //orElse will be called every times.
                        Objects.isNull(highLightContent) ? content.substring(0, contentLength) : highLightContent,
                        path,
                        Long.parseLong(length)));
            }

            return new Pager(totalHits, lastIndex + documents.size(), documents);
        } catch (final IOException | ParseException | InvalidTokenOffsetsException e) {
            throw new LuceneException(e.getMessage(), e);
        }
    }

    private Highlighter getHighLighter(final Query query) {
        /** Highlighter Code Start ****/
        //Uses HTML &lt;B&gt;&lt;/B&gt; tag to highlight the searched terms
        final Formatter formatter = new SimpleHTMLFormatter(Constants.PRE_HIGH_TAG, Constants.POST_HIGH_TAG);
        //It scores text fragments by the number of unique query terms found
        //Basically the matching score in layman terms
        final QueryScorer scorer = new QueryScorer(query);
        //used to markup highlighted terms found in the best sections of a text
        final Highlighter highlighter = new Highlighter(formatter, scorer);
        //breaks text up into same-size fragments with no concerns over spotting sentence boundaries.
        //The size of the string.
        final Fragmenter fragmenter = new SimpleFragmenter(Constants.CONTENT_LENGTH);
        //set fragmenter to highlighter
        highlighter.setTextFragmenter(fragmenter);

        return highlighter;
    }

    private ScoreDoc getLastScoreDoc(
        final int lastIndex,
        final Query query,
        final IndexSearcher indexSearcher,
        final Sort sort) throws IOException {

        if (lastIndex == 0) {
            return null;
        }
        //without sort
        //        final TopDocs tds = indexSearcher.search(query, lastIndex);


        final TopDocs tds = indexSearcher.search(query, lastIndex, sort);

        return tds.scoreDocs[lastIndex - 1];
    }

    private Sort getSort() {
        return new Sort(new SortField(ArticleAttributes.LENGTH_SORT.name(), SortField.Type.LONG, false));
    }
}
