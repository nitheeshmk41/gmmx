package com.gmmx.mvp.util;

public final class SlugUtil {

    private SlugUtil() {}

    public static String normalize(String slug) {
        return slug == null ? "" : slug.trim().toLowerCase();
    }
}
