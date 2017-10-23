package com.xgt.lucene;

import com.xgt.lucene.service.WriteService;
import com.xgt.lucene.service.impl.WriteOnStartService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LuceneAppConfiguration {
    @Bean
    public Directory directory() {
        return new RAMDirectory();
    }

    @Bean
    public Analyzer analyzer() {
        return new StandardAnalyzer();
    }

    @Bean(initMethod = "write")
    public WriteService writeService() {
        return new WriteOnStartService();
    }
}
