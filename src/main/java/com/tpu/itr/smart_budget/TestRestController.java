package com.tpu.itr.smart_budget;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestRestController {

    //тестовый мапинг
    @GetMapping("/profile")
    public ResponseEntity<?> profile(Authentication authentication) {

        Long userId = (Long) authentication.getPrincipal();

        return ResponseEntity.ok("user id = " + userId);
    }

}
