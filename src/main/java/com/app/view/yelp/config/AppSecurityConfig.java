package com.app.view.yelp.config;

import com.app.view.yelp.constant.AppConstants;
import com.app.view.yelp.filter.AuthFilter;
import com.app.view.yelp.filter.MDCFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import static com.app.view.yelp.constant.AppConstants.APPNAME;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@ComponentScan("com.app.view.yelp")
@PropertySource("classpath:application.properties")
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String REALM="YELP_REALM";

    @Autowired
    private AuthFilter authFilter;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //auth.inMemoryAuthentication().withUser("Admin").password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("Admin")).roles("ADMIN");
//        //auth.inMemoryAuthentication().withUser("User").password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("User")).roles("ADMIN","USER");
//        //auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
//        auth.inMemoryAuthentication()
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.
        http.addFilterBefore(getMDCFilter(), ChannelProcessingFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .addFilterAfter(authFilter, BasicAuthenticationFilter.class)
                .csrf().disable().headers()
                .and().httpBasic().realmName(REALM);;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/health","/token");
    }

    @Bean
    public MDCFilter getMDCFilter(){
        return new MDCFilter(APPNAME);
    }

    @Bean
    public AuthFilter getAuthFilter() {
        return new AuthFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
