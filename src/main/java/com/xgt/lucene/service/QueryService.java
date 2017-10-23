package com.xgt.lucene.service;

import com.xgt.lucene.model.Pager;
import com.xgt.lucene.model.QueryRequest;

public interface QueryService {
    Pager query(QueryRequest request);
}
