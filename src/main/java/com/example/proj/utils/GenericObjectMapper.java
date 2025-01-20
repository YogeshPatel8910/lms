package com.example.proj.utils;

import java.lang.reflect.Field;

public class GenericObjectMapper {

    public static <S, T> T map(S source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            copyFields(source, target, source.getClass(), targetClass);
            return target;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static <S, T> void copyFields(S source, T target, Class<?> sourceClass, Class<?> targetClass) throws IllegalAccessException {
        if (sourceClass == null || targetClass == null) {
            return;
        }
        Field[] sourceFields = sourceClass.getDeclaredFields();
        Field[] targetFields = targetClass.getDeclaredFields();
        for (Field sourceField : sourceFields) {
            sourceField.setAccessible(true);
            for (Field targetField : targetFields) {
                targetField.setAccessible(true);
                if (sourceField.getName().equals(targetField.getName()) &&
                        sourceField.getType().equals(targetField.getType())) {
                    targetField.set(target, sourceField.get(source));
                }
            }
        }
        copyFields(source, target, sourceClass.getSuperclass(), targetClass.getSuperclass());
    }
}
