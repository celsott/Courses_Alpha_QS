package com.qs.courses_alpha.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String ALUNO = "ROLE_ALUNO";

    public static final String SECRETARIA = "ROLE_SECRETARIA";

    public static final String PROFESSOR = "ROLE_PROFESSOR";

    private AuthoritiesConstants() {
    }
}
