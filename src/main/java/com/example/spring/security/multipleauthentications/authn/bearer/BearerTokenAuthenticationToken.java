package com.example.spring.security.multipleauthentications.authn.bearer;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class BearerTokenAuthenticationToken extends AbstractAuthenticationToken
{
    // ============================== [Fields] ==============================

    // -------------------- [Private Fields] --------------------

    private String bearerToken;

    // ============================== [Construction / Destruction] ==============================

    // -------------------- [Public Construction / Destruction] --------------------

    public BearerTokenAuthenticationToken(String bearerToken)
    {
        super(null);

        this.bearerToken = bearerToken;
        super.setAuthenticated(false);
    }

    public BearerTokenAuthenticationToken(String bearerToken, Collection<? extends GrantedAuthority> authorities)
    {
        super(authorities);

        this.bearerToken = bearerToken;
        super.setAuthenticated(true);
    }

    // ============================== [Getter/Setter] ==============================

    // -------------------- [Private Getter/Setter] --------------------

    // -------------------- [Public Getter/Setter] --------------------

    @Override
    public Object getCredentials()
    {
        return this.bearerToken;
    }

    @Override
    public Object getPrincipal()
    {
        return this.bearerToken;
    }

    // ============================== [Methods] ==============================

    // -------------------- [Private Methods] --------------------

    // -------------------- [Public Methods] --------------------

}
