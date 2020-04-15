package com.app.view.yelp.controller;

import com.app.view.yelp.constant.AppConstants;
import com.app.view.yelp.model.User;
import com.app.view.yelp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
public class SearchController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(AppConstants.SEARCH_URL)
    public ResponseEntity<User> searchBusiness(@RequestHeader(value = "zipCode") String zipCode) {
        return new ResponseEntity<User>(userRepository.findByUsername("User"), HttpStatus.ACCEPTED);
    }

}
