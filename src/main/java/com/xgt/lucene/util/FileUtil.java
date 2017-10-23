package com.xgt.lucene.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtil {
    public static List<File> listHtmls(final String path) {
        final File root = new File(path);
        final List<File> files = listFiles(root);

        return files
            .stream()
            .filter(file -> file.getName().endsWith(".html"))
            .collect(Collectors.toList());
    }

    private static List<File> listFiles(final File parent) {
        final List<File> files = new ArrayList<>();
        for(final File file: parent.listFiles()) {
            if(file.isFile()) {
                files.add(file);
            } else {
                files.addAll(listFiles(file));
            }
        }

        return files;
    }
}
