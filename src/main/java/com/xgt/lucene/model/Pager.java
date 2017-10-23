package com.xgt.lucene.model;

import java.util.List;

public class Pager {
    private final long total;
    private final int currentIndex;
    private final List<Article> documents;

    public Pager(final long total, final int currentIndex, final List<Article> documents) {
        this.total = total;
        this.currentIndex = currentIndex;
        this.documents = documents;
    }

    public long getTotal() {
        return total;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public List<Article> getDocuments() {
        return documents;
    }
}
