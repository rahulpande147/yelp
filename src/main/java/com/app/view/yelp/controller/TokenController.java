package com.app.view.yelp.controller;

import com.app.view.yelp.service.JwtUserDetailService;
import com.app.view.yelp.util.JWTTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.app.view.yelp.constant.AppConstants.TOKEN_URL;

@RestController
public class TokenController {

    @Autowired
    private JWTTokenUtil jWTTokenUtil;

    @Autowired
    private JwtUserDetailService jwtUserDetailService;

    @GetMapping(TOKEN_URL)
    public String generateToken(@RequestParam(value = "userName") String userName) {
        return jWTTokenUtil.generateToken(jwtUserDetailService.loadUserByUsername(userName));
    }
}
