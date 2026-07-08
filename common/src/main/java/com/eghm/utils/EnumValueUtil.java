package com.eghm.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Enum value helper used by outer adapters without polluting domain enums.
 */
public final class EnumValueUtil {

    private EnumValueUtil() {
    }

    public static Object value(Enum<?> value) {
        if (value == null) {
            return null;
        }
        Object resolved = invokeNoArg(value, "getValue");
        if (resolved != null) {
            return resolved;
        }
        resolved = invokeNoArg(value, "getCode");
        if (resolved != null) {
            return resolved;
        }
        return value.name();
    }

    @SuppressWarnings("unchecked")
    public static <E extends Enum<E>> E fromValue(Class<E> enumType, Object value) {
        if (value == null) {
            return null;
        }
        E fromFactory = fromFactory(enumType, value);
        if (fromFactory != null) {
            return fromFactory;
        }
        String source = String.valueOf(value);
        for (E candidate : enumType.getEnumConstants()) {
            Object enumValue = value(candidate);
            if (enumValue != null && source.equals(String.valueOf(enumValue))) {
                return candidate;
            }
            if (candidate.name().equals(source)) {
                return candidate;
            }
        }
        return null;
    }

    private static <E extends Enum<E>> E fromFactory(Class<E> enumType, Object value) {
        for (Method method : enumType.getDeclaredMethods()) {
            if (!"of".equals(method.getName()) || !Modifier.isStatic(method.getModifiers()) || method.getParameterCount() != 1) {
                continue;
            }
            try {
                method.setAccessible(true);
                Object converted = convert(value, method.getParameterTypes()[0]);
                Object result = method.invoke(null, converted);
                if (result != null) {
                    return enumType.cast(result);
                }
            } catch (Exception ignored) {
                return null;
            }
        }
        return null;
    }

    private static Object invokeNoArg(Enum<?> value, String methodName) {
        try {
            Method method = value.getClass().getMethod(methodName);
            return method.invoke(value);
        } catch (Exception ignored) {
            return null;
        }
    }

    private static Object convert(Object value, Class<?> targetType) {
        if (targetType.isInstance(value)) {
            return value;
        }
        String source = String.valueOf(value);
        if (targetType == String.class) {
            return source;
        }
        if (targetType == Integer.class || targetType == int.class) {
            return Integer.valueOf(source);
        }
        if (targetType == Long.class || targetType == long.class) {
            return Long.valueOf(source);
        }
        if (targetType == Byte.class || targetType == byte.class) {
            return Byte.valueOf(source);
        }
        return value;
    }
}
