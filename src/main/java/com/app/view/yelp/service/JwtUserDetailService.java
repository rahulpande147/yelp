package com.app.view.yelp.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String iUserName) throws UsernameNotFoundException {
        if (iUserName != null && iUserName.equalsIgnoreCase("User")) {
            return new User(iUserName, "", new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User Not Found");
        }
    }
}