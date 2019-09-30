package org.lucas.example.framework.web.spring.source.core;

import org.junit.jupiter.api.Test;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.core.ResolvableType;

public class ResolvableTypeTests {

    @Test
    public void getCauseType() {
        Class<?> clazz = ResolvableType.forClass(AbstractFailureAnalyzer.class, getClass()).resolveGeneric();
        System.out.println(clazz);
    }

}
