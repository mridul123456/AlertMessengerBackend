package com.accolite.alertMessenger.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .antMatchers(HttpMethod.GET,"/accolite/alertmessenger/fetchData")
                .hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"/accolite/alertmessenger/saveData")
                .hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/accolite/alertmessenger/deleteData/{id}")
                .hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT,"/accolite/alertmessenger/updateData/{id}")
                .hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/accolite/alertmessenger/home")
                .hasAuthority("USER")
                .and()
                .httpBasic();
    }
}