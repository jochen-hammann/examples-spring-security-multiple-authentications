package com.example.spring.security.multipleauthentications.config;

import com.example.spring.security.multipleauthentications.authn.apikey.ApikeyAuthenticationFilter;
import com.example.spring.security.multipleauthentications.authn.apikey.ApikeyAuthenticationProvider;
import com.example.spring.security.multipleauthentications.authn.bearer.BearerTokenAuthenticationFilter;
import com.example.spring.security.multipleauthentications.authn.bearer.BearerTokenAuthenticationProvider;
import com.example.spring.security.multipleauthentications.errorhandling.CustomAccessDeniedHandler;
import com.example.spring.security.multipleauthentications.errorhandling.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Order(2)
public class BearerSecurityConfig extends WebSecurityConfigurerAdapter
{
    // ============================== [Fields] ==============================

    // -------------------- [Private Fields] --------------------

    @Autowired
    private BearerTokenAuthenticationProvider bearerTokenAuthenticationProvider;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    // ============================== [Construction / Destruction] ==============================

    // -------------------- [Public Construction / Destruction] --------------------

    // ============================== [Getter/Setter] ==============================

    // -------------------- [Private Getter/Setter] --------------------

    // -------------------- [Public Getter/Setter] --------------------

    // ============================== [Spring Beans] ==============================

    // -------------------- [Public Spring Beans] --------------------

    // ============================== [Methods] ==============================

    // -------------------- [Private Methods] --------------------

    // -------------------- [Protected Methods] --------------------

    // -------------------- [Public Methods] --------------------

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.authenticationProvider(bearerTokenAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        // @formatter:off
        http
                .antMatcher("/bearer/**")

                .addFilterBefore(new BearerTokenAuthenticationFilter(this.customAuthenticationEntryPoint), BasicAuthenticationFilter.class)
                .authorizeRequests(authz ->
                        authz
                                .mvcMatchers(HttpMethod.GET, "/bearer/hello")
                                .access("hasAuthority('ADMIN') or (hasAuthority('PRIVILEGE1') and hasAnyAuthority('PRIVILEGE2', 'PRIVILEGE3'))")
                )
                .exceptionHandling(error -> error.authenticationEntryPoint(customAuthenticationEntryPoint).accessDeniedHandler(customAccessDeniedHandler))

                .httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // @formatter:on
    }
}
