package com.example.spring.security.multipleauthentications.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidApikeyException extends AuthenticationException
{
    // ============================== [Fields] ==============================

    // -------------------- [Private Fields] --------------------

    // ============================== [Construction / Destruction] ==============================

    // -------------------- [Public Construction / Destruction] --------------------

    public InvalidApikeyException(String msg, Throwable cause)
    {
        super(msg, cause);
    }

    public InvalidApikeyException(String msg)
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
