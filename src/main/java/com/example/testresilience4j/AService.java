package com.example.testresilience4j;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.vavr.control.Try;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.concurrent.CompletableFuture;

@Service
@Log4j2
public class AService {
    private static final String SERVICE_A = "serviceA";

    @CircuitBreaker(name = SERVICE_A)
    @Retry(name = SERVICE_A)
    public String failure() {
        log.info("failure method called");
        throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "This is a remote exception");
    }

    @CircuitBreaker(name = SERVICE_A)
    @Retry(name = SERVICE_A)
    public String success() {
        log.info("success method called");
        return "Hello World from backend A";
    }

    @CircuitBreaker(name = SERVICE_A)
    @Bulkhead(name = SERVICE_A, type = Bulkhead.Type.THREADPOOL)
    @Retry(name = SERVICE_A)
    @TimeLimiter(name = SERVICE_A)
    public CompletableFuture<String> timeout() {
        log.info("timeout method called");
        Try.run(() -> Thread.sleep(5000));
        return CompletableFuture.completedFuture("Timeout");
    }

    @CircuitBreaker(name = SERVICE_A, fallbackMethod = "fallback")
    @Retry(name = SERVICE_A)
    public String fallback() {
        log.info("fallback method called");
        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "This is not a remote exception");
    }

    public String fallback(Exception e) {
        return "Recovered: " + e.getMessage();
    }
}
