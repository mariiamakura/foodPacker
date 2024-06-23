package com.foody.config;
import io.github.cdimascio.dotenv.Dotenv;

/**
 * Class for storing JWT-related constants.
 */
public class JwtConstant {
    private static final Dotenv dotenv = Dotenv.load();

    public static final String SECRET_KEY = dotenv.get("SECRET_KEY");
    public static final String JWT_HEADER="Authorization";
}
