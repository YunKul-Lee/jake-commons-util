package com.jake.common.util;

import java.util.function.Function;

public class NullUtils {

    public static <I, O> O executeViaNullSafer(I value, Function<I, O> executorFunction) {
        return value != null ? executorFunction.apply(value) : null;
    }

    public static <I, O> O executeOrDefault(I value, Function<I, O> executorFunction, O defaultValue) {
        return value != null ? executorFunction.apply(value) : defaultValue;
    }
}
