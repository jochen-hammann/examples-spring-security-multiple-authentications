package com.example.spring.security.multipleauthentications.authn.apikey;

import com.example.spring.security.multipleauthentications.exceptions.InvalidApikeyException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ApikeyAuthenticationProvider implements AuthenticationProvider
{
    // ============================== [Fields] ==============================

    // -------------------- [Private Fields] --------------------

    // ============================== [Construction / Destruction] ==============================

    // -------------------- [Public Construction / Destruction] --------------------

    // ============================== [Getter/Setter] ==============================

    // -------------------- [Private Getter/Setter] --------------------

    // -------------------- [Public Getter/Setter] --------------------

    // ============================== [Methods] ==============================

    // -------------------- [Private Methods] --------------------

    private boolean validateApikey(String apikey)
    {
        return "4711".equals(apikey);
    }

    // -------------------- [Public Methods] --------------------

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        // Get the API key authentication token.
        ApikeyAuthenticationToken apikeyAuthenticationToken = (ApikeyAuthenticationToken) authentication;
        String apikey = apikeyAuthenticationToken.getApikey();

        // Validate the API key.
        if (!this.validateApikey(apikey))
            throw new InvalidApikeyException("The API key is invalid.");

        // Internally sets the authenticated flag to true;
        ApikeyAuthenticationToken authenticatedToken = new ApikeyAuthenticationToken(apikey, new ArrayList<>());

        return authenticatedToken;
    }

    @Override
    public boolean supports(Class<?> aClass)
    {
        return ApikeyAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
