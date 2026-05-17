package com.electro.store.api.shared.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public final class CodeGenerator {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmss");
    private static final int RANDOM_LENGTH = 5;
    private static final int PREFIX_LENGTH = 3;
    private static final String PREFIX_REGEX = "[A-Z]{" + PREFIX_LENGTH + "}";
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private CodeGenerator() {
    }

    public static String next(String prefix) {
        verifyPrefix(prefix);
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String random = randomString();
        return prefix + timestamp + random;
    }

    private static String randomString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < RANDOM_LENGTH; i++) {
            int index = ThreadLocalRandom.current().nextInt(CHARACTERS.length());
            builder.append(CHARACTERS.charAt(index));
        }
        return builder.toString();
    }

    private static void verifyPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException(
                    "Prefix must not be null"
            );
        }
        if (prefix.isBlank()) {
            throw new IllegalArgumentException(
                    "Prefix must not be blank"
            );
        }
        if (!prefix.matches(PREFIX_REGEX)) {
            throw new IllegalArgumentException(
                    "Prefix must contain only " + PREFIX_LENGTH + " uppercase letters"
            );
        }
    }

}
