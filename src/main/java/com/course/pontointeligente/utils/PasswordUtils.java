package com.course.pontointeligente.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
    private static final Logger log = LoggerFactory.getLogger(PasswordUtils.class);

    /**
     * Generates password hash using bCrypt package
     * @param password User's password
     * @return hashed password
     */
    public static String generateBCrypt(String password) {
        if (password == null) {
            return null;
        }

        log.info("Generating password hash...");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }
}
