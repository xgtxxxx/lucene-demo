package com.xgt.lucene.controller;

import com.xgt.lucene.exception.LuceneException;
import com.xgt.lucene.model.Pager;
import com.xgt.lucene.model.QueryRequest;
import com.xgt.lucene.model.QueryType;
import com.xgt.lucene.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class LuceneController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LuceneController.class);

    @Autowired
    @Qualifier("proxyQueryService")
    private QueryService queryService;

    @GetMapping("/list-types")
    public List<QueryType> getQueryTypes() {

        return Arrays.asList(QueryType.values());
    }

    @GetMapping("/search")
    public Pager search(
        @RequestParam final String term,
        @RequestParam final int index,
        @RequestParam(required = false) final QueryType type) {

        return queryService.query(new QueryRequest(term, index, type));
    }

    @ExceptionHandler(LuceneException.class)
    public void handleException(final LuceneException e) {
        LOGGER.error(e.getMessage(), e);
    }
}
