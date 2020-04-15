package com.app.view.yelp.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class MDCFilter extends OncePerRequestFilter {
    public String appName = "yelp";
    public final static String TXNID = "TXNID";
    public Logger logger = LoggerFactory.getLogger(MDCFilter.class);

    public MDCFilter() {}

    public MDCFilter(String iAppName) {
        appName = iAppName;
    }
    /**
     * @param iHttpServletRequest
     * @param iHttpServletResponse
     * @param iFilterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest iHttpServletRequest, HttpServletResponse iHttpServletResponse, FilterChain iFilterChain) throws ServletException, IOException {
        String txn_id = iHttpServletRequest.getHeader(TXNID);
        String logger_txnid = "";
        if (StringUtils.isEmpty(txn_id)) {
            logger_txnid = appName + UUID.randomUUID();
        } else {
            logger_txnid =  appName + txn_id;
        }

        MDC.put(TXNID, logger_txnid);
        logger.info(String.format("REQUEST_URI - %s QUERY_PARAMS - %s" ,iHttpServletRequest.getRequestURI(), iHttpServletRequest.getQueryString()));
        iHttpServletRequest.setAttribute(TXNID, logger_txnid);
        iFilterChain.doFilter(iHttpServletRequest, iHttpServletResponse);
        MDC.clear();
    }
}
