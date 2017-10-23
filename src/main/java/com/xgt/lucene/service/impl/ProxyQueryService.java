package com.xgt.lucene.service.impl;

import com.xgt.lucene.model.Pager;
import com.xgt.lucene.model.QueryRequest;
import com.xgt.lucene.model.QueryType;
import com.xgt.lucene.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service("proxyQueryService")
public class ProxyQueryService implements QueryService {

    @Autowired
    private Map<String, QueryService> services;

    @Override
    public Pager query(final QueryRequest request) {
        final String service = Optional
            .ofNullable(request.getQueryType())
            .orElse(QueryType.TermQuery)
            .getService();

        return services.get(service).query(request);
    }
}
