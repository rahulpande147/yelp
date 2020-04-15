package com.app.view.yelp.controller;

import com.app.view.yelp.constant.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping(AppConstants.HEALTH_URL)
    public ResponseEntity<String> checkHealth() {
        return new ResponseEntity<String>("Success", HttpStatus.ACCEPTED) ;
    }
}
