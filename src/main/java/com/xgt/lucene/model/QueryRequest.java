package com.xgt.lucene.model;

public class QueryRequest {
    private final String keyword;
    private final int index;
    private final QueryType queryType;

    public QueryRequest(final String keyword, final int index, final QueryType queryType) {
        this.keyword = keyword;
        this.index = index;
        this.queryType = queryType;
    }

    public String getKeyword() {
        return keyword;
    }

    public int getIndex() {
        return index;
    }

    public QueryType getQueryType() {
        return queryType;
    }
}
