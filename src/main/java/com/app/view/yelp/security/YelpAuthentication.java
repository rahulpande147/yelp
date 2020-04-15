package com.app.view.yelp.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class YelpAuthentication extends AbstractAuthenticationToken {

    private List<? extends GrantedAuthority> authorities;
    private Object credentials;
    private Object principles;


    public YelpAuthentication(List<? extends GrantedAuthority> iAuthorities, Object iCredentials, Object iPrinciples) {
        super(iAuthorities);
        authorities = iAuthorities;
        credentials = iCredentials;
        principles = iPrinciples;
    }


    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principles;
    }
}
