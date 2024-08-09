package com.example.config;

import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyAutoConfigImportSelector implements DeferredImportSelector {

    private final ClassLoader classLoader;

    public MyAutoConfigImportSelector(final ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public String[] selectImports(final AnnotationMetadata importingClassMetadata) {
        return ImportCandidates.load(MyAutoConfiguration.class, classLoader)
                .getCandidates()
                .toArray(String[]::new);
    }

}
