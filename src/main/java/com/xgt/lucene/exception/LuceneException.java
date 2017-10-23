package com.xgt.lucene.exception;

public class LuceneException extends RuntimeException {
    public LuceneException(final String message, final Exception e) {
        super(message, e);
    }
}
