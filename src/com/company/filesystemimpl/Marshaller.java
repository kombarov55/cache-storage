package com.company.filesystemimpl;

import com.google.gson.Gson;

/**
 * Маршализация / демаршализация объектов
 */
public class Marshaller {

    private static Gson gson = new Gson();

    private Marshaller() { }

    public static String marshalize(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T demarshalize(String data, Class<T> type) {
        return gson.fromJson(data, type);
    }

}
