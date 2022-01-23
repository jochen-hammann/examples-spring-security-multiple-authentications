package com.example.spring.security.multipleauthentications.authn.bearer;

import com.example.spring.security.multipleauthentications.authn.apikey.ApikeyAuthenticationToken;
import com.example.spring.security.multipleauthentications.exceptions.InvalidApikeyException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BearerTokenAuthenticationProvider implements AuthenticationProvider
{
    // ============================== [Fields] ==============================

    // -------------------- [Private Fields] --------------------

    private Map<String, List<GrantedAuthority>> bearerTokenAuthorityMapping;

    // ============================== [Construction / Destruction] ==============================

    // -------------------- [Public Construction / Destruction] --------------------

    public BearerTokenAuthenticationProvider()
    {
        this.bearerTokenAuthorityMapping = new HashMap<>();
        this.bearerTokenAuthorityMapping.put("4811", List.of(new SimpleGrantedAuthority("ADMIN")));
        this.bearerTokenAuthorityMapping.put("4812", List.of(new SimpleGrantedAuthority("PRIVILEGE1"), new SimpleGrantedAuthority("PRIVILEGE2")));
        this.bearerTokenAuthorityMapping.put("4813", List.of(new SimpleGrantedAuthority("PRIVILEGE1")));
    }

    // ============================== [Getter/Setter] ==============================

    // -------------------- [Private Getter/Setter] --------------------

    // -------------------- [Public Getter/Setter] --------------------

    // ============================== [Methods] ==============================

    // -------------------- [Private Methods] --------------------

    private boolean validateBearerToken(String bearerToken)
    {
        return this.bearerTokenAuthorityMapping.keySet().stream().anyMatch(a -> a.equals(bearerToken));
    }

    // -------------------- [Public Methods] --------------------

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        // Get the bearer authentication token.
        BearerTokenAuthenticationToken bearerTokenAuthenticationToken = (BearerTokenAuthenticationToken) authentication;
        String bearerToken = bearerTokenAuthenticationToken.getBearerToken();

        // Validate the API key.
        if (!this.validateBearerToken(bearerToken))
            throw new InvalidApikeyException("The bearer token is invalid.");

        // Get the API key's authorities (privileges).
        List<GrantedAuthority> grantedAuthorities = this.bearerTokenAuthorityMapping.get(bearerToken);

        // Internally sets the authenticated flag to true;
        BearerTokenAuthenticationToken authenticatedToken = new BearerTokenAuthenticationToken(bearerToken, grantedAuthorities);

        return authenticatedToken;
    }

    @Override
    public boolean supports(Class<?> aClass)
    {
        return BearerTokenAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
