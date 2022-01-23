package com.example.spring.security.multipleauthentications.authn.apikey;

import com.example.spring.security.multipleauthentications.constants.ConstHttpHeaders;
import com.example.spring.security.multipleauthentications.errorhandling.CustomAuthenticationEntryPoint;
import com.example.spring.security.multipleauthentications.exceptions.InvalidApikeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Caution: If multiple filter chains are used, the filter should not be a Spring Bean (@Component).
//          In unit tests, both filters (ApikeyAuthenticationFilter and BearerTokenAuthenticationFilter) have been called, even if only one is
//          specified in each filter chain. Debugging showed some kind mocked (filter chain?) classes. Maybe this is caused by the use of MockMvc.
//          To circumvent this problem the filters are specified via 'new ...AuthenticationFilter()' within the respective filter chain.
// Hint: The logging of a normal start of the Web Service showed that each filter is contained in its own filter chain only. Thus the filter
//       chains look correct. Even if the filters are specified as Spring Beans (@Component).
public class ApikeyAuthenticationFilter extends OncePerRequestFilter
{
    // ============================== [Fields] ==============================

    // -------------------- [Private Fields] --------------------

    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    // ============================== [Construction / Destruction] ==============================

    // -------------------- [Public Construction / Destruction] --------------------

    public ApikeyAuthenticationFilter(CustomAuthenticationEntryPoint customAuthenticationEntryPoint)
    {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    // ============================== [Getter/Setter] ==============================

    // -------------------- [Private Getter/Setter] --------------------

    // -------------------- [Public Getter/Setter] --------------------

    // ============================== [Methods] ==============================

    // -------------------- [Private Methods] --------------------

    // -------------------- [Protected Methods] --------------------

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException
    {
        // Get the API key header from the request.
        String apikey = httpServletRequest.getHeader(ConstHttpHeaders.APIKEY_HEADER);

        // Verify, if an API key header is specified.
        if (apikey == null)
        {
            this.customAuthenticationEntryPoint.commence(httpServletRequest, httpServletResponse,
                    new InvalidApikeyException("The API key is missing."));

            return;
        }

        // Create the authentication token.
        ApikeyAuthenticationToken apikeyAuthenticationToken = new ApikeyAuthenticationToken(apikey);

        // Let Spring know about the authentication token.
        SecurityContextHolder.getContext().setAuthentication(apikeyAuthenticationToken);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    // -------------------- [Public Methods] --------------------

}
