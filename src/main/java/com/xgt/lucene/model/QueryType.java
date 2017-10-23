package com.xgt.lucene.model;

public enum QueryType {
    TermQuery("termQueryService"),
    MultiField("multiFieldQueryService"),
    FuzzyQuery("fuzzyQueryService"),
    WildCard("wildCardQueryService"),
    Phrase("phraseQueryService"),
    QueryParser("queryParserService"),
    Boolean("booleanQueryService"),
    Filter("booleanFilterService");

    private final String service;

    QueryType(final String service) {
        this.service = service;
    }

    public String getService() {
        return this.service;
    }
}
