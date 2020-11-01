package com.barbulescu.springsecurity.db;

import lombok.extern.log4j.Log4j2;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Log4j2
public class GenerateCodeUtil {

    public static String generateCode() {
        try {
            return generateCodeInternal();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Problem when generating the random code.");
        }
    }

    private static String generateCodeInternal() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstanceStrong();
        int c = random.nextInt(9000) + 1000;
        String code = String.valueOf(c);
        log.info("Generated {} code", code);
        return code;
    }
}