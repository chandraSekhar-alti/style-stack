package com.example.stylestackapp.common.util;

public final class SlugUtil {

    private SlugUtil() {
    }

    public static String toSlug(String input) {

        /**
         * Example :
         * Men's Wear becomes -> mens-wear
         */

        return input
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-");
    }
}