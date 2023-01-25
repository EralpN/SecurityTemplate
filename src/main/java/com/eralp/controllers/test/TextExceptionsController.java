package com.eralp.controllers.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/exceptions")
@RequiredArgsConstructor
public class TextExceptionsController {

    @GetMapping
    public ResponseEntity<String> test() {
        throw new RuntimeException("Exception Test.");
    }
}
