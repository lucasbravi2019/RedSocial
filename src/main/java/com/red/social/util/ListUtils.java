package com.red.social.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListUtils {

    @SafeVarargs
    public static <T> List<T> toArrayList(T... elements) {
        return Arrays.stream(elements).collect(Collectors.toList());
    }

}
