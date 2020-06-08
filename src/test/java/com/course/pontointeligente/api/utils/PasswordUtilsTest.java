package com.course.pontointeligente.api.utils;

import com.course.pontointeligente.utils.PasswordUtils;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordUtilsTest {
    private static final String PASSWORD = "12356";
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Test
    public void testNullPassword() {
        assertNull(PasswordUtils.generateBCrypt(null));
    }

    @Test
    public void testGenerateHashPassword() throws Exception {
        String hash = PasswordUtils.generateBCrypt(PASSWORD);

        assertTrue(bCryptPasswordEncoder.matches(PASSWORD, hash));
    }
}
