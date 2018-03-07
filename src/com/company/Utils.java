package com.company;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    private Utils() {
    }

    /**
     * Получить значение. Если value равна null, возвращает значение по умолчанию.
     *
     * @return value если оно действительно, иначе def.
     */
    public static <T> T getWithDefault(Object value, T def) {
        return value != null ?
                (T) value :
                def;
    }

    private static Map<String, Integer> sizeSuffixToMultiplier = new HashMap<>();

    static {
        sizeSuffixToMultiplier.put("b", 1);
        sizeSuffixToMultiplier.put("kb", 1024);
        sizeSuffixToMultiplier.put("mb", 1024 * 1024);
        sizeSuffixToMultiplier.put("gb", 1024 * 1024 * 1024);
    }

    /**
     * Получить количество байтов по переданной строке.
     *
     * @param sizeStr строка описывающая размер файла, например: 5Mb, 126Kb, 1Gb. Суффикс может быть
     *                один из [b, mb, kb, gb] без учёта регистра.
     * @return размер в байтах.
     */
    public static long resolveSize(String sizeStr) {
        long size = Long.parseLong(takeDigitPart(sizeStr));

        String suffix = takeSuffixPart(sizeStr);
        int multiplier = sizeSuffixToMultiplier.get(suffix.toLowerCase());

        return size * multiplier;
    }

    private static String takeDigitPart(String str) {
        StringBuilder sb = new StringBuilder();

        char[] chars = str.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (Character.isAlphabetic(ch)) break;

            sb.append(ch);

        }

        return sb.toString();
    }

    private static String takeSuffixPart(String str) {

        char[] chars = str.toCharArray();

        int suffixIndex = -1;

        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (Character.isAlphabetic(ch)) {
                suffixIndex = i;
                break;
            }
        }

        if (suffixIndex == -1) return "";
        else return str.substring(suffixIndex, str.length());

    }
}
