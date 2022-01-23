package com.example.spring.security.multipleauthentications.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidBearerTokenException extends AuthenticationException
{
    // ============================== [Fields] ==============================

    // -------------------- [Private Fields] --------------------

    // ============================== [Construction / Destruction] ==============================

    // -------------------- [Public Construction / Destruction] --------------------

    public InvalidBearerTokenException(String msg, Throwable cause)
    {
        super(msg, cause);
    }

    public InvalidBearerTokenException(String msg)
    {
        super(msg);
    }

    // ============================== [Getter/Setter] ==============================

    // -------------------- [Private Getter/Setter] --------------------

    // -------------------- [Public Getter/Setter] --------------------

    // ============================== [Methods] ==============================

    // -------------------- [Private Methods] --------------------

    // -------------------- [Public Methods] --------------------

}
