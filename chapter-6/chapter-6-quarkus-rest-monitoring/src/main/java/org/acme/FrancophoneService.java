package org.acme;

import jakarta.enterprise.context.ApplicationScoped;

import io.opentelemetry.instrumentation.annotations.WithSpan;

@ApplicationScoped
public class FrancophoneService {

    @WithSpan
    public String bonjour() {
        return "bonjour";
    }
}