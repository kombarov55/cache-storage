package com.company.filesystemimpl;

import com.company.memoryimpl.ObjectSizeChecker;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * Маршализация / демаршализация объектов
 */
public class Marshaller {

    private Marshaller() { }

    public static String marshalize(Object object) {
        StringBuilder sb = new StringBuilder();

        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                sb
                        .append(field.getName())
                        .append("=")
                        .append(field.get(object));

                sb.append("\t");
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static <T> T demarshalize(String str, Class<T> type) throws Exception {
        if (str == null || str.isEmpty()) return null;

        T t = type.newInstance();

        // разложить строку в мап
        String[] pairs = str.split("\t");

        Map<String, String> fieldnameToValue = new HashMap<>(pairs.length / 2);

        for (String pair : pairs) {
            String[] parts = pair.split("=");
            String fieldName = parts[0];
            String fieldValue = parts[1];

            fieldnameToValue.put(fieldName, fieldValue);
        }

        // у каждого поля выставить значение

        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);

            String value = fieldnameToValue.get(field.getName());
            Object castedValue = castValue(field.getType(), value);

            field.set(t, castedValue);
        }


        return t;
    }


    private static Object castValue(Class<?> type, String value) {
        if (value == null) return null;

        switch (type.getSimpleName()) {
            case "String": return value;
            case "boolean": return Boolean.parseBoolean(value);
            case "byte": return Byte.parseByte(value);
            case "short": return Short.parseShort(value);
            case "char": return value.charAt(0);
            case "int": return Integer.parseInt(value);
            case "long": return Long.parseLong(value);
            case "float": return Float.parseFloat(value);
            case "double": return Double.parseDouble(value);

            case "Boolean": return Boolean.parseBoolean(value);
            case "Byte": return Byte.parseByte(value);
            case "Short": return Short.parseShort(value);
            case "Character": return value.charAt(0);
            case "Integer": return Integer.parseInt(value);
            case "Long": return Long.parseLong(value);
            case "Float": return Float.parseFloat(value);
            case "Double": return Double.parseDouble(value);

            default:
                return null;

        }
    }

}
