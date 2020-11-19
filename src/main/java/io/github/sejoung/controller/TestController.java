package io.github.sejoung.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/")
@RestController
public class TestController {

    @GetMapping("test")
    public ResponseEntity<String> getTest(Authentication principal) {

        var userName = principal.getName();
        log.debug("test ok {}", principal.getDetails());

        return ResponseEntity.ok(userName);
    }
}
