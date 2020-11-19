package io.github.sejoung.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@Configuration
public class SecurityConfigruation extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("**").authenticated().and().formLogin()
            .disable().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).and()
            .exceptionHandling()
            .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
            .accessDeniedHandler(new AccessDeniedHandlerImpl());
    }

}
