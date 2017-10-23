package com.xgt.lucene.util;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;

public class HtmlParser {
//    private static final Pattern SCRIPT = Pattern.compile("<script[^>]*?>[\\s\\S]*?<\\/script>",Pattern.CASE_INSENSITIVE);
//    private static final Pattern STYLE = Pattern.compile("<style[^>]*?>[\\s\\S]*?<\\/style>",Pattern.CASE_INSENSITIVE);
//    private static final Pattern TAG = Pattern.compile("<[^>]+>",Pattern.CASE_INSENSITIVE);

    public static String parseToText(final File file) throws IOException {
        return Jsoup.parse(file, "UTF-8").text();
//        html = SCRIPT.matcher(html).replaceAll("");
//        html = STYLE.matcher(html).replaceAll("");
//        html = TAG.matcher(html).replaceAll("");
    }
}
