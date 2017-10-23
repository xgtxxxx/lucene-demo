package com.xgt.lucene.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Article {
    private final String title;
    private final String path;
    private final String content;
    private final Long length;

    public Article(final String title, final String content, final String path, final long length) {
        this.title = title;
        this.path = path;
        this.content = content;
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public String getContent() {
        return content;
    }

    public Long getLength() {
        return length;
    }
}
