package com.app.view.yelp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class YelpAuthenticationManager implements AuthenticationManager {


    @Override
    public Authentication authenticate(Authentication iAuthentication) throws AuthenticationException {
        return new YelpAuthentication(null, "User", null);
    }
}
