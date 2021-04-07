package com.example.testresilience4j;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
public class AController {
    private final AService aService;

    @GetMapping("failure")
    public String failure(){
        return aService.failure();
    }

    @GetMapping("success")
    public String success(){
        return aService.success();
    }

    @GetMapping("timeout")
    public CompletableFuture<String> timeout() {
        return aService.timeout();
    }

    @GetMapping("fallback")
    public String fallback() {
        return aService.fallback();
    }
}
