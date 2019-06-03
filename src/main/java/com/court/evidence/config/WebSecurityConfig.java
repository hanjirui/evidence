package com.court.evidence.config;

import com.court.evidence.support.CustomAuthenticationFailureHandler;
import com.court.evidence.support.CustomAuthenticationSuccessHandler;
import com.court.evidence.support.Http401EntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .mvcMatchers("/login").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().successHandler(new CustomAuthenticationSuccessHandler()).failureHandler(new CustomAuthenticationFailureHandler())
                .and()
                .exceptionHandling().authenticationEntryPoint(new Http401EntryPoint())
                .and()
                .httpBasic();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder().username("admin").password("admin").roles("admin").build());
        return manager;
    }
}
