package com.czechp.DistillationServiceBackend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration()
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable();

        //Allow Angular to check allowed http method request
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll();

        //User account handler endpoints
        http.authorizeRequests()
                .antMatchers("/api/login", "/api/register").permitAll();

        //Endpoint to testing connection
        http.authorizeRequests()
                .antMatchers("/api/home").permitAll();

        //Any others request should be authorization
        http.authorizeRequests()
                .anyRequest().authenticated();
    }

    @Bean()
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
