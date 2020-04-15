package com.app.view.yelp.filter;

import com.app.view.yelp.security.YelpAuthentication;
import com.app.view.yelp.security.YelpAuthenticationManager;
import com.app.view.yelp.service.JwtUserDetailService;
import com.app.view.yelp.util.JWTTokenUtil;
import com.fasterxml.jackson.annotation.JacksonInject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter extends GenericFilterBean {

    public static final Logger log = LoggerFactory.getLogger(AuthFilter.class);
    public static final String TOKEN_HEADER = "Authorization";

    @Autowired
    private JwtUserDetailService jwtUserDetailService;

    @Autowired
    private JWTTokenUtil jWTTokenUtil;

    @Autowired
    private YelpAuthenticationManager yelpAuthenticationManager;

    @Override
    public void doFilter(ServletRequest iServletRequest, ServletResponse iServletResponse, FilterChain iFilterChain) throws IOException, ServletException {
        HttpServletRequest aHttpServletRequest = ((HttpServletRequest) iServletRequest);
        HttpServletResponse aHttpServletResponse = ((HttpServletResponse) iServletResponse);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String token = aHttpServletRequest.getHeader(TOKEN_HEADER);
        if (!StringUtils.isEmpty(token) && token.startsWith("Bearer ")){
            String jwtToken = token.substring(7);
            String userName = jWTTokenUtil.getUserNameFromToken(jwtToken);
            auth = yelpAuthenticationManager.authenticate(new YelpAuthentication(null, "User", null));
            //Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
            if (!jWTTokenUtil.validateToken(jwtToken, jwtUserDetailService.loadUserByUsername(userName))){
                log.error("Invalid Token");
                throw new ServletException("JWT Token Invalid");
            }
            auth.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(auth);
            aHttpServletResponse.setHeader(TOKEN_HEADER, jWTTokenUtil.generateToken(jwtUserDetailService.loadUserByUsername(userName)));
        } else {
            log.error("Invalid Token");
        }
        iFilterChain.doFilter(iServletRequest,iServletResponse);
    }
}
