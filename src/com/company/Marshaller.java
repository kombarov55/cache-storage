package com.company;

import java.lang.reflect.Field;

/**
 * Маршализация / демаршализация объектов
 */
public class Marshaller {

    public String marshalize(Object object) {
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

    public Object demarshalize(String str) {
        return new Object();
    }

    public int pickId(Object obj) {
        return obj.hashCode();
    }



}
