package com.example.testresilience4j;

import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.micrometer.tagged.TaggedBulkheadMetrics;
import io.github.resilience4j.micrometer.tagged.TaggedCircuitBreakerMetrics;
import io.github.resilience4j.micrometer.tagged.TaggedRetryMetrics;
import io.github.resilience4j.micrometer.tagged.TaggedTimeLimiterMetrics;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class MeterConfiguration {
    @Autowired
    MeterRegistry meterRegistry;

    @Autowired
    CircuitBreakerRegistry circuitBreakerRegistry;

    @Autowired
    BulkheadRegistry bulkheadRegistry;

    @Autowired
    RetryRegistry retryRegistry;

    @Autowired
    TimeLimiterRegistry timeLimiterRegistry;

    @PostConstruct
    public void init() {
        MeterRegistry meterRegistry = new SimpleMeterRegistry();

        TaggedCircuitBreakerMetrics
                .ofCircuitBreakerRegistry(circuitBreakerRegistry)
                .bindTo(meterRegistry);

        TaggedRetryMetrics
                .ofRetryRegistry(retryRegistry)
                .bindTo(meterRegistry);

        TaggedBulkheadMetrics
                .ofBulkheadRegistry(bulkheadRegistry)
                .bindTo(meterRegistry);

        TaggedTimeLimiterMetrics
                .ofTimeLimiterRegistry(timeLimiterRegistry)
                .bindTo(meterRegistry);
    }
}
