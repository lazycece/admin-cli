package com.lazycece.admin.server.auth;

import com.lazycece.admin.server.auth.custom.CustomPermissionEvaluator;
import com.lazycece.admin.server.auth.filter.AuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author lazycece
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true, proxyTargetClass = true)
public class AuthConfigure extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //support spring security's cors, which configs in WebMvcConfig.class
                .cors().and()
                //use JWT，disable the csrf
                .csrf().disable()
                //not need session when using token
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //permit if action is login
                .antMatchers("/u/login").permitAll()
                .anyRequest().authenticated()
                .and()
                //disable http cache
                .headers().cacheControl();

        http
                .addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilter() {
        return new AuthenticationTokenFilter();
    }

    @Bean
    public CustomPermissionEvaluator permissionEvaluator() {
        return new CustomPermissionEvaluator();
    }

}
