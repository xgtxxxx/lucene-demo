package com.xgt.lucene;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;

@SpringBootApplication
public class LuceneApp implements EmbeddedServletContainerCustomizer {
    public static void main(final String[] args) throws Exception {
        SpringApplication.run(LuceneApp.class, args);
    }

    @Override
    public void customize(final ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
        configurableEmbeddedServletContainer.setPort(8000);
    }
}
